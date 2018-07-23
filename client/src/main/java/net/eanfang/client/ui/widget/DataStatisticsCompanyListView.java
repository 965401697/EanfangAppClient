package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.datastatistics.DataStatisticsCompany;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;


import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.datastatistics.SwitchCompanyDataStatisticsListAdapter;

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

public class DataStatisticsCompanyListView extends BaseDialog {
    @BindView(R.id.rev_company_list)
    RecyclerView revCompanyList;
    private Activity mContext;
    private setCheckItemCompany itemCompany;
    private String mOrgId = "";

    public DataStatisticsCompanyListView(Activity context, String orgId, setCheckItemCompany itemCompany) {
        super(context);
        this.mContext = context;
        mOrgId = orgId;
        this.itemCompany = itemCompany;

    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_company_list);
        ButterKnife.bind(this);
        getCompanyAllList(mOrgId);
    }

    /**
     * Get the list of Companies
     */
    private void getCompanyAllList(String orgId) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("topCompanyId", orgId + "");
        queryEntry.getEquals().put("companyId", orgId + "");
        EanfangHttp.post(NewApiService.REPAIR_DATA_COMPANGY)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<DataStatisticsCompany>(mContext, false, DataStatisticsCompany.class, bean -> {
                    if (bean.getList().size() > 0) {
                        List<DataStatisticsCompany.ListBean> companyEntityBeanList = bean.getList();
                        initAdapter(companyEntityBeanList);
                    } else {
                        dismiss();
                        showToast("暂无子公司");
                    }

                }));
    }

    private void initAdapter(List<DataStatisticsCompany.ListBean> beanList) {
        SwitchCompanyDataStatisticsListAdapter adapter = new SwitchCompanyDataStatisticsListAdapter(R.layout.item_quotation_detail, beanList);
        revCompanyList.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        revCompanyList.setLayoutManager(new LinearLayoutManager(mContext));
        revCompanyList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String companyName = "";
                if (beanList.get(position).getCompanyEntity().getName() != null) {
                    companyName = beanList.get(position).getCompanyEntity().getName();
                }
                String orgId = "";
                if (beanList.get(position).getCompanyEntity().getOrgId() != null && beanList.get(position).getCompanyEntity().getOrgId() != null) {
                    orgId = beanList.get(position).getCompanyEntity().getOrgId();
                }
                String sonId = "";
                if (beanList.get(position).getCompanyEntity().getSon() != null && beanList.get(position).getCompanyEntity().getSon() != null) {
                    sonId = beanList.get(position).getCompanyEntity().getSon();
                }
                itemCompany.getItemName(companyName, orgId, sonId);
                dismiss();
            }
        });
        revCompanyList.setAdapter(adapter);
    }


    public interface setCheckItemCompany {
        void getItemName(String name, String orgId, String sonId);
    }

}
