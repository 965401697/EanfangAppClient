package net.eanfang.client.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseFragment;

import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.FragmentSelectOrganizationBinding;
import net.eanfang.client.viewmodel.CompanySelectViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/8/20  15:49
 * @description 选择部门
 */

public class SelectOrganizationFragment extends BaseFragment {

    private FragmentSelectOrganizationBinding fragmentSelectOrganizationBinding;
    @Setter
    @Accessors(chain = true)
    private CompanySelectViewModle companySelectViewModle;

    public static SelectOrganizationFragment getInstance(CompanySelectViewModle companySelectViewModle) {
        return new SelectOrganizationFragment().setCompanySelectViewModle(companySelectViewModle);
    }


    @Override
    protected ViewModel initViewModel() {
        return companySelectViewModle;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        fragmentSelectOrganizationBinding = FragmentSelectOrganizationBinding.inflate(getLayoutInflater());
        fragmentSelectOrganizationBinding.setCompanySelectViewModle(companySelectViewModle);
        companySelectViewModle.setFragmentSelectOrganizationBinding(fragmentSelectOrganizationBinding);
        companySelectViewModle.initOrganiaztionListener(String.valueOf(ClientApplication.get().getCompanyId()));
        return fragmentSelectOrganizationBinding.getRoot();
    }


}
