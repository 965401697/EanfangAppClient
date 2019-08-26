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
import net.eanfang.client.databinding.FragmentMoreAbilityBinding;
import net.eanfang.client.ui.adapter.MoreCapabilityAdapter;
import net.eanfang.client.viewmodel.SecurityCompanyDetailViewModel;

import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/8/24  13:27
 * @description 更多能力
 */

public class MoreAbilityFragment extends BaseFragment {

    private FragmentMoreAbilityBinding fragmentMoreAbilityBinding;
    @Setter
    @Accessors(chain = true)
    private SecurityCompanyDetailViewModel securityCompanyDetailViewModel;

    private MoreCapabilityAdapter moreCapabilityAdapter;
    private MoreCapabilityAdapter moreToolAdapter;

    private boolean isShowAbility = false;
    private boolean isShowTool = false;

    public static MoreAbilityFragment getInstance(SecurityCompanyDetailViewModel mSecurityCompanyDetailViewModel) {
        return new MoreAbilityFragment().setSecurityCompanyDetailViewModel(mSecurityCompanyDetailViewModel);
    }


    @Override
    protected ViewModel initViewModel() {
        fragmentMoreAbilityBinding = FragmentMoreAbilityBinding.inflate(getLayoutInflater());
        securityCompanyDetailViewModel.setFragmentMoreAbilityBinding(fragmentMoreAbilityBinding);
        securityCompanyDetailViewModel.getSecurityCompanyDetailBeanMutableLiveData().observe(this, this::setData);
        return securityCompanyDetailViewModel;
    }

    private void setData(SecurityCompanyDetailBean companyDetailBean) {
        if (companyDetailBean == null) {
            return;
        }
        // 施工
        if (companyDetailBean.getAbilityList() != null && companyDetailBean.getAbilityList().size() > 0) {
            moreCapabilityAdapter.setNewData(companyDetailBean.getAbilityList());
        }
        // 工具
        if (companyDetailBean.getToolList() != null && companyDetailBean.getToolList().size() > 0) {
            moreToolAdapter.setNewData(companyDetailBean.getToolList());
        }
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        LinearLayoutManager layoutManager_ability = new LinearLayoutManager(fragmentMoreAbilityBinding.getRoot().getContext());
        fragmentMoreAbilityBinding.rvAblitity.setLayoutManager(layoutManager_ability);
        moreCapabilityAdapter = new MoreCapabilityAdapter(false);
        fragmentMoreAbilityBinding.rvAblitity.setNestedScrollingEnabled(false);
        moreCapabilityAdapter.bindToRecyclerView(fragmentMoreAbilityBinding.rvAblitity);

        LinearLayoutManager layoutManager_tool = new LinearLayoutManager(fragmentMoreAbilityBinding.getRoot().getContext());
        fragmentMoreAbilityBinding.rvTool.setLayoutManager(layoutManager_tool);
        moreToolAdapter = new MoreCapabilityAdapter(false);
        fragmentMoreAbilityBinding.rvTool.setNestedScrollingEnabled(false);
        moreToolAdapter.bindToRecyclerView(fragmentMoreAbilityBinding.rvTool);
        initListener();
        return fragmentMoreAbilityBinding.getRoot();
    }

    private void initListener() {
        //施工能力
        fragmentMoreAbilityBinding.rlAblitity.setOnClickListener((v) -> {
            showRvView(isShowAbility, moreCapabilityAdapter.getData(), fragmentMoreAbilityBinding.rvAblitity, fragmentMoreAbilityBinding.ivAblitity);
            isShowAbility = !isShowAbility;
        });
        // 工具
        fragmentMoreAbilityBinding.rlTool.setOnClickListener((v) -> {
            showRvView(isShowTool, moreToolAdapter.getData(), fragmentMoreAbilityBinding.rvTool, fragmentMoreAbilityBinding.ivTool);
            isShowTool = !isShowTool;
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
