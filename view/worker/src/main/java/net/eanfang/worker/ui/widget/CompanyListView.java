package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.base.network.config.HttpConfig;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.PermKit;
import com.eanfang.witget.takavideo.ToastUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.adapter.SwitchCompanyListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.ScreenUtils;

/**
 * Created by MrHou
 *
 * @on 2017/12/7  14:26
 * @email houzhongzhou@yeah.net
 * @desc Company list
 */

public class CompanyListView extends PopupWindow {
    RecyclerView revCompanyList;
    private Activity mContext;
    private setCheckItemCompany itemCompany;
    private SwitchCompanyListAdapter adapter;

    public CompanyListView(Activity context, List<OrgEntity> mList, setCheckItemCompany itemCompany) {
        super(context);
        this.mContext = context;
        this.itemCompany = itemCompany;
        View view = LayoutInflater.from(context).inflate(R.layout.view_company_list, null);
        revCompanyList = view.findViewById(R.id.rev_company_list);
        adapter = new SwitchCompanyListAdapter();
        adapter.bindToRecyclerView(revCompanyList);
        revCompanyList.setLayoutManager(new LinearLayoutManager(mContext));
        setBackgroundDrawable(new ColorDrawable(0x70000000));
        int width = (ScreenUtils.widthPixels(context));
        int height = (ScreenUtils.heightPixels(context) * 3 / 4);
        setWidth(width);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        initAdapter(mList);
    }


    /**
     * Get the list of Companies
     */
    private void getCompanyAllList() {
        List<OrgEntity> orgEntityList = new ArrayList<>(WorkerApplication.get().getLoginBean().getAccount().getBelongCompanys());
//        排除默认公司 只取安防公司
        orgEntityList = Stream.of(orgEntityList).filter(bean -> bean.getOrgId() != 0 && bean.getOrgUnitEntity() != null && bean.getOrgUnitEntity().getUnitType() == 3).toList();
    }

    private void initAdapter(List<OrgEntity> beanList) {
        backgroundAlpha(0.7f);
        adapter.setNewData(beanList);
        revCompanyList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgId().equals(beanList.get(position).getOrgId())) {
                    ToastUtils.s(mContext, "无需切换公司");
                    dismiss();
                    return;
                }
                SwitchCompany(beanList, position, beanList.get(position).getOrgId());

            }
        });
    }

    /**
     * @param companyid Go to another company
     */
    private void SwitchCompany(List<OrgEntity> beanList, int position, Long companyid) {
        EanfangHttp.get(NewApiService.SWITCH_COMPANY_ALL_LIST)
                .params("companyId", companyid)
                .execute(new EanfangCallback<LoginBean>(mContext, false, LoginBean.class, (bean) -> {
                    if (bean != null) {
                        PermKit.permList.clear();
                        CacheKit.get().put(LoginBean.class.getName(), bean, CacheMod.All);
                        EanfangHttp.setToken(bean.getToken());
                        HttpConfig.get().setToken(bean.getToken());
                        EanfangHttp.setWorker();
                        String companyName = null;
                        dismiss();
                        if (beanList.get(position).getOrgName() != null) {
                            companyName = beanList.get(position).getOrgName();
                        }
                        String logpic = null;
                        if (beanList.get(position).getOrgUnitEntity() != null && beanList.get(position).getOrgUnitEntity().getLogoPic() != null) {
                            logpic = beanList.get(position).getOrgUnitEntity().getLogoPic();
                        }
                        itemCompany.getItemName(companyName, logpic);

                    }
                }));
    }

    public interface setCheckItemCompany {
        void getItemName(String name, String url);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0+
        mContext.getWindow().setAttributes(lp);
    }

}
