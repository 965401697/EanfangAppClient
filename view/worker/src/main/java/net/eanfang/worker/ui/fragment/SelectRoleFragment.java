package net.eanfang.worker.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseFragment;

import net.eanfang.worker.databinding.FragmentSelectRoleBinding;
import net.eanfang.worker.viewmodle.CompanySelectViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/8/20  18:55
 * @description 分配角色
 */

public class SelectRoleFragment extends BaseFragment {

    private FragmentSelectRoleBinding fragmentSelectRoletypeBinding;
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
        fragmentSelectRoletypeBinding = FragmentSelectRoleBinding.inflate(getLayoutInflater());
        fragmentSelectRoletypeBinding.setCompanySelectViewModle(companySelectViewModle);
        companySelectViewModle.setFragmentSelectRoletypeBinding(fragmentSelectRoletypeBinding);
        companySelectViewModle.initRoleTypeListener();
        return fragmentSelectRoletypeBinding.getRoot();
    }
}
