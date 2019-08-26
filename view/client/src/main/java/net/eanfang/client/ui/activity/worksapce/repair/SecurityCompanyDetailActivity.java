package net.eanfang.client.ui.activity.worksapce.repair;

import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.BuildConfig;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.SecurityCompanyDetailBean;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivitySecurityCompanyDetailBinding;
import net.eanfang.client.ui.adapter.SecuityCompanyDetailCustomAdapter;
import net.eanfang.client.ui.fragment.securitydetail.GloryFragment;
import net.eanfang.client.ui.fragment.securitydetail.MoreAbilityFragment;
import net.eanfang.client.ui.fragment.securitydetail.ServiceFragment;
import net.eanfang.client.viewmodel.SecurityCompanyDetailViewModel;

/**
 * @author guanluocang
 * @data 2019/8/23  16:49
 * @description 安防公司详情
 */

public class SecurityCompanyDetailActivity extends BaseActivity {

    private ActivitySecurityCompanyDetailBinding securityCompanyDetailBinding;
    private SecurityCompanyDetailViewModel securityCompanyDetailViewModel;
    private String mOrgId = "";
    /**
     * 正在公告 0  已过期 1
     */
    private String[] mTitles = {"服务信息", "资质与荣誉", "更多能力"};
    private MyPagerAdapter mAdapter;

    private SecuityCompanyDetailCustomAdapter secuityCompanyDetailCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        securityCompanyDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_security_company_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("安防公司详情");
        setLeftBack(true);
        mOrgId = getIntent().getStringExtra("mOrgId");

        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mTitles);
        mAdapter.getFragments().add(ServiceFragment.getInstance(securityCompanyDetailViewModel));
        mAdapter.getFragments().add(GloryFragment.getInstance(securityCompanyDetailViewModel));
        mAdapter.getFragments().add(MoreAbilityFragment.getInstance(securityCompanyDetailViewModel));
        securityCompanyDetailBinding.vpCompanyInfo.setCurrentItem(0);
        securityCompanyDetailBinding.tlCompanyInfo.setViewPager(securityCompanyDetailBinding.vpCompanyInfo, mTitles, this, mAdapter.getFragments());
        securityCompanyDetailBinding.vpCompanyInfo.setOffscreenPageLimit(1);
        securityCompanyDetailViewModel.getData(mOrgId);
    }

    @Override
    protected ViewModel initViewModel() {
        securityCompanyDetailViewModel = LViewModelProviders.of(this, SecurityCompanyDetailViewModel.class);
        securityCompanyDetailViewModel.setSecurityCompanyDetailBinding(securityCompanyDetailBinding);
        securityCompanyDetailViewModel.getSecurityCompanyDetailBeanMutableLiveData().observe(this, this::setData);
        return securityCompanyDetailViewModel;
    }

    private void setData(SecurityCompanyDetailBean companyDetailBean) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(securityCompanyDetailBinding.getRoot().getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        securityCompanyDetailBinding.rvCustom.setLayoutManager(layoutManager);
        secuityCompanyDetailCustomAdapter = new SecuityCompanyDetailCustomAdapter();
        secuityCompanyDetailCustomAdapter.bindToRecyclerView(securityCompanyDetailBinding.rvCustom);

        //头像
        GlideUtil.intoImageView(securityCompanyDetailBinding.getRoot().getContext(), Uri.parse(BuildConfig.OSS_SERVER + companyDetailBean.getOrgUnit().getLogoPic()), securityCompanyDetailBinding.ivHeader);
        // 公司名
        securityCompanyDetailBinding.tvRealname.setText(companyDetailBean.getOrgUnit().getName());
        // 公司简介
        securityCompanyDetailBinding.tvCompanyIntro.setText(companyDetailBean.getOrgUnit().getIntro());
        //设计订单数量
        securityCompanyDetailBinding.tvDesignOrder.setText(String.valueOf(companyDetailBean.getOrderCounts().getDesignCount()));
        //评价数量
        securityCompanyDetailBinding.tvGoodGrade.setText(SplitAndRound((companyDetailBean.getOrderCounts().getEvaluateNum() * 0.01), 2) + "%");
        //好评率
        securityCompanyDetailBinding.tvDesignOrder.setText(String.valueOf(companyDetailBean.getOrderCounts().getGoodRate()));
        securityCompanyDetailBinding.arcprogressview.setProgress(companyDetailBean.getOrderCounts().getGoodRate());
        //施工订单数量
        securityCompanyDetailBinding.tvWorkOrder.setText(String.valueOf(companyDetailBean.getOrderCounts().getInstallCount()));
        //口碑值
        securityCompanyDetailBinding.tvDesignOrder.setText(String.valueOf(companyDetailBean.getOrderCounts().getPublicPraise()));
        //维修订单数量
        securityCompanyDetailBinding.tvRepairOrder.setText(String.valueOf(companyDetailBean.getOrderCounts().getRepairCount()));

        // 客户案例
        secuityCompanyDetailCustomAdapter.setNewData(companyDetailBean.getCases().getList());
    }

    /**
     * 保留几位小数
     *
     * @param a
     * @param n
     * @return
     */
    public double SplitAndRound(double a, int n) {
        a = a * Math.pow(10, n);
        return (Math.round(a)) / (Math.pow(10, n));
    }


}
