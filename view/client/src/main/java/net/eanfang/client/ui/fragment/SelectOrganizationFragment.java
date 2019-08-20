package net.eanfang.client.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseFragment;

import net.eanfang.client.databinding.FragmentSelectOrganizationBinding;

/**
 * @author guanluocang
 * @data 2019/8/20  15:49
 * @description 选择部门
 */

public class SelectOrganizationFragment extends BaseFragment {

    private FragmentSelectOrganizationBinding fragmentSelectOrganizationBinding;


    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        fragmentSelectOrganizationBinding = FragmentSelectOrganizationBinding.inflate(getLayoutInflater());
//        fragmentSelectOrganizationBinding.setVm(mViewModel);
//        fragmentSelectOrganizationBinding.setMBinding(mBinding);
        return fragmentSelectOrganizationBinding.getRoot();
    }
}
