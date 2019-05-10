package com.eanfang.sys.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseFragment;
import com.eanfang.databinding.FragmentPasswordEanfangBinding;
import com.eanfang.sys.viewmodel.LoginViewModel;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class PasswordFragment extends BaseFragment {

    private FragmentPasswordEanfangBinding binding;

    @Setter
    @Accessors(chain = true)
    private LoginViewModel loginViewModel;

    public static PasswordFragment getInstance(LoginViewModel loginViewModel) {
        return new PasswordFragment().setLoginViewModel(loginViewModel);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentPasswordEanfangBinding.inflate(getLayoutInflater());
        binding.setVm(loginViewModel);
        binding.setApp(getApp());

        loginViewModel.setPasswordBinding(binding);
        loginViewModel.LegalText(mActivity);
        return binding.getRoot();
    }

    @Override
    protected ViewModel initViewModel() {
        return loginViewModel;
    }


}
