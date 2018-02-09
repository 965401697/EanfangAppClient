package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.base.BaseDialog;
import com.okgo.OkGo;
import com.okgo.model.HttpHeaders;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.SwitchCompanyListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/12/7  14:26
 * @email houzhongzhou@yeah.net
 * @desc Company list
 */

public class CompanyListView extends BaseDialog {
    @BindView(R.id.rev_company_list)
    RecyclerView revCompanyList;
    private Activity mContext;
    private setCheckItemCompany itemCompany;

    public interface setCheckItemCompany {
        void getItemName(String name);
    }

    public CompanyListView(Activity context, setCheckItemCompany itemCompany) {
        super(context);
        this.mContext = context;
        this.itemCompany = itemCompany;

    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_company_list);
        ButterKnife.bind(this);
        getCompanyAllList();
    }

    /**
     * Get the list of Companies
     */
    private void getCompanyAllList() {
        List<OrgEntity> orgEntityList = new ArrayList<>(EanfangApplication.getApplication().getUser().getAccount().getBelongCompanys());
        orgEntityList = Stream.of(orgEntityList).filter(bean -> bean.getOrgId() != 0).toList();
        initAdapter(orgEntityList);
    }

    private void initAdapter(List<OrgEntity> beanList) {
        SwitchCompanyListAdapter adapter = new SwitchCompanyListAdapter(R.layout.item_quotation_detail, beanList);
        revCompanyList.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        revCompanyList.setLayoutManager(new LinearLayoutManager(mContext));
        revCompanyList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                SwitchCompany(beanList.get(position).getOrgId());
                itemCompany.getItemName(beanList.get(position).getOrgName());
            }
        });
        revCompanyList.setAdapter(adapter);
    }

    /**
     * @param companyid Go to another company
     */
    private void SwitchCompany(Long companyid) {
        EanfangHttp.get(NewApiService.SWITCH_COMPANY_ALL_LIST)
                .params("companyId", companyid)
                .execute(new EanfangCallback<LoginBean>(mContext, false, LoginBean.class, (bean) -> {
                    EanfangApplication.get().remove(LoginBean.class.getName());
                    EanfangApplication.get().set(LoginBean.class.getName(), JSONObject.toJSONString(bean, FastjsonConfig.config));
                    OkGo http = EanfangHttp.getHttp();
                    HttpHeaders headers = new HttpHeaders();
                    headers.put("YAF-Token", EanfangApplication.get().getUser().getToken());
                    headers.put("Request-From", "WORKER");
                    http.addCommonHeaders(headers);
                    dismiss();
                }));
    }

}
