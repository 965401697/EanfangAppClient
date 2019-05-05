package com.eanfang.ui.activity;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.customview.TwoTextPicText;
import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.SecurityCompanyDetailsBean;
import com.eanfang.ui.adapter.SecurityAdapter;
import com.eanfang.ui.adapter.SlidingTabAdapter;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.fragment.FwXxFragment;
import com.eanfang.ui.fragment.GdNlFragment;
import com.eanfang.ui.fragment.ZzyRyFragment;
import com.eanfang.util.ViewFindUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author wq
 * @description 安防公司详情
 */

public class SecurityCompanyDetailsActivity extends BaseActivity {


    @BindView(R2.id.gs_log_sdv)
    SimpleDraweeView gsLogSdv;
    @BindView(R2.id.gs_xq_iv)
    ImageView gsXqIv;
    @BindView(R2.id.gs_name_tv)
    TextView gsNameTv;
    @BindView(R2.id.xyz_s_tv)
    TextView xyzSTv;
    @BindView(R2.id.xyz_tv)
    TextView xyzTv;
    @BindView(R2.id.bfz_tv)
    TextView bfzTv;
    @BindView(R2.id.bq_rv)
    RecyclerView bqRv;
    @BindView(R2.id.sj_dd_iv)
    TwoTextPicText sjDdIv;
    @BindView(R2.id.wx_dd_iv)
    TwoTextPicText wxDdIv;
    @BindView(R2.id.sg_dd_iv)
    TwoTextPicText sgDdIv;
    @BindView(R2.id.pl_iv)
    TwoTextPicText plIv;
    @BindView(R2.id.gs_jj_tv)
    TextView gsJjTv;
    @BindView(R2.id.kh_al_rv)
    RecyclerView khAlRv;
    @BindView(R2.id.slidingtablayout)
    SlidingTabLayout slidingtablayout;
    @BindView(R2.id.vp_service)
    ViewPager vpService;
    private SecurityAdapter adapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"服务信息", "资质与荣誉", "更多能力"};
    private SecurityCompanyDetailsBean securityBean = new SecurityCompanyDetailsBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_company_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("安防公司详情");
        setLeftBack();
        initData();
    }

    @SuppressLint("LongLogTag")
    private void initData() {
        EanfangHttp.post(UserApi.CK_AN_GS_DLS).params("orgId", "979995434422681602").execute(new EanfangCallback<SecurityCompanyDetailsBean>(this, true, SecurityCompanyDetailsBean.class, (bean) -> {
            Log.d("ExpertDetailsActivity_wq", bean.toString());
            securityBean = bean;
            setData();
        }));
    }

    private void setData() {
        gsLogSdv.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(securityBean.getOrgUnit().getLogoPic()));
        gsNameTv.setText(securityBean.getOrgUnit().getName());
        xyzSTv.setText(securityBean.getOrderCounts().getPublicPraise() + "");
        bfzTv.setText("好评率：" + securityBean.getOrderCounts().getGoodRate() + "%");
        sjDdIv.setTestBstring(securityBean.getOrderCounts().getDesignCount() + "");
        wxDdIv.setTestBstring(securityBean.getOrderCounts().getRepairCount() + "");
        sgDdIv.setTestBstring(securityBean.getOrderCounts().getInstallCount() + "");
        plIv.setTestBstring(securityBean.getOrderCounts().getEvaluateNum() + "");
        gsJjTv.setText(securityBean.getOrgUnit().getIntro() + "");
        initRecyclerView(securityBean.getCases().getList());
        setViewPager();
    }

    private void setViewPager() {
        mFragments.add(FwXxFragment.getInstance(securityBean.getBizList(),securityBean.getSysList(),securityBean.getAreaList()));
        mFragments.add(ZzyRyFragment.getInstance(securityBean.getAptitudeList(),securityBean.getGloryList()));
        mFragments.add(GdNlFragment.getInstance(securityBean.getAbilityList(),securityBean.getToolList()));
        vpService = ViewFindUtils.find(getWindow().getDecorView(), R.id.vp_service);
        vpService.setAdapter(new SlidingTabAdapter(getSupportFragmentManager(), mFragments, mTitles));
        ((SlidingTabLayout) ViewFindUtils.find(getWindow().getDecorView(), R.id.slidingtablayout)).setViewPager(vpService);
    }

    private void initRecyclerView(List<SecurityCompanyDetailsBean.CasesBean.ListBean> list) {
        khAlRv.setLayoutManager(new LinearLayoutManager(this));
        khAlRv.setNestedScrollingEnabled(false);
        adapter = new SecurityAdapter(true);
        adapter.bindToRecyclerView(khAlRv);
        adapter.setNewData(list);
    }
}
