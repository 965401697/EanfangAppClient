package net.eanfang.client.ui.fragment.securitydetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.bean.SecurityCompanyDetailBean;

import net.eanfang.client.R;
import net.eanfang.client.databinding.FragmentGloryBinding;
import net.eanfang.client.ui.adapter.SecurityCompanyAptitudeAdapter;
import net.eanfang.client.ui.adapter.SecurityCompanyGloryAdapter;
import net.eanfang.client.viewmodel.SecurityCompanyDetailViewModel;

import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/8/24  13:27
 * @description 资质与荣誉
 */

public class GloryFragment extends BaseFragment {

    private FragmentGloryBinding fragmentGloryBinding;
    @Setter
    @Accessors(chain = true)
    private SecurityCompanyDetailViewModel securityCompanyDetailViewModel;
    /**
     * 荣誉证书
     */
    private SecurityCompanyGloryAdapter workDetailGloryAdapter;
    /**
     * 资质证书
     */
    private SecurityCompanyAptitudeAdapter workDetailAptitudeAdapter;
    private boolean isShowHonor = false;
    private boolean isShowQualification = false;

    public static GloryFragment getInstance(SecurityCompanyDetailViewModel mSecurityCompanyDetailViewModel) {
        return new GloryFragment().setSecurityCompanyDetailViewModel(mSecurityCompanyDetailViewModel);
    }

    @Override
    protected ViewModel initViewModel() {
        fragmentGloryBinding = FragmentGloryBinding.inflate(getLayoutInflater());
        securityCompanyDetailViewModel.setFragmentGloryBinding(fragmentGloryBinding);
        securityCompanyDetailViewModel.getSecurityCompanyDetailBeanMutableLiveData().observe(this, this::setData);
        return securityCompanyDetailViewModel;
    }

    private void setData(SecurityCompanyDetailBean companyDetailBean) {
        if (companyDetailBean == null) {
            return;
        }
        //荣誉
        if (companyDetailBean.getGloryList() != null && companyDetailBean.getGloryList().size() > 0) {
            workDetailGloryAdapter.setNewData(companyDetailBean.getGloryList());
        }
        //资质
        if (companyDetailBean.getAptitudeList() != null && companyDetailBean.getAptitudeList().size() > 0) {
            workDetailAptitudeAdapter.setNewData(companyDetailBean.getAptitudeList());
        }
        //资质 判断是否与资质
        companyDetailBean.getOrderCounts().getAptitudePics();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        LinearLayoutManager layoutManager_honor = new LinearLayoutManager(fragmentGloryBinding.getRoot().getContext());
        fragmentGloryBinding.rvHonor.setLayoutManager(layoutManager_honor);
        workDetailGloryAdapter = new SecurityCompanyGloryAdapter(false);
        fragmentGloryBinding.rvHonor.setNestedScrollingEnabled(false);
        workDetailGloryAdapter.bindToRecyclerView(fragmentGloryBinding.rvHonor);
        LinearLayoutManager layoutManager_qualification = new LinearLayoutManager(fragmentGloryBinding.getRoot().getContext());
        fragmentGloryBinding.rvQualification.setLayoutManager(layoutManager_qualification);
        workDetailAptitudeAdapter = new SecurityCompanyAptitudeAdapter(fragmentGloryBinding.getRoot().getContext(), false);
        fragmentGloryBinding.rvQualification.setNestedScrollingEnabled(false);
        workDetailAptitudeAdapter.bindToRecyclerView(fragmentGloryBinding.rvQualification);
        initListener();
        return fragmentGloryBinding.getRoot();
    }

    private void initListener() {
        //荣誉
        fragmentGloryBinding.rlHonor.setOnClickListener((v) -> {
            showRvView(isShowHonor, workDetailGloryAdapter.getData(), fragmentGloryBinding.rvHonor, fragmentGloryBinding.ivHonor);
            isShowHonor = !isShowHonor;
        });
        // 资质
        fragmentGloryBinding.rlQualification.setOnClickListener((v) -> {
            showRvView(isShowQualification, workDetailAptitudeAdapter.getData(), fragmentGloryBinding.rvQualification, fragmentGloryBinding.ivQualification);
            isShowQualification = !isShowQualification;
        });
    }

    private void showRvView(boolean isV, List mList, RecyclerView recyclerView, ImageView imageView) {
        if (!isV) {
            if (mList != null && mList.size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.mipmap.ic_client_down);
            } else {
                showToast("暂无更多");
            }
        } else {
            recyclerView.setVisibility(View.GONE);
            imageView.setImageResource(R.mipmap.ic_client_up);
        }
    }

}
