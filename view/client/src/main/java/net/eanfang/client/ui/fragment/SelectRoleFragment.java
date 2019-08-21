package net.eanfang.client.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseFragment;

import net.eanfang.client.databinding.FragmentSelectOrganizationBinding;
import net.eanfang.client.viewmodel.CompanySelectViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/8/20  18:55
 * @description 分配角色
 */

public class SelectRoleFragment extends BaseFragment {

    private FragmentSelectOrganizationBinding fragmentSelectOrganizationBinding;
    @Setter
    @Accessors(chain = true)
    private CompanySelectViewModle companySelectViewModle;

    public static SelectRoleFragment getInstance(CompanySelectViewModle companySelectViewModle) {
        return new SelectRoleFragment().setCompanySelectViewModle(companySelectViewModle);
    }

    @Override
    protected ViewModel initViewModel() {
        return companySelectViewModle;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        fragmentSelectOrganizationBinding = FragmentSelectOrganizationBinding.inflate(getLayoutInflater());
        return fragmentSelectOrganizationBinding.getRoot();
    }

}
