package com.eanfang.sys.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseFragment;
import com.eanfang.databinding.FragmentVerifyEanfangBinding;
import com.eanfang.sys.viewmodel.LoginViewModel;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class VerifyFragment extends BaseFragment {

    private FragmentVerifyEanfangBinding binding;

    @Setter
    @Accessors(chain = true)
    private LoginViewModel loginViewModel;

    public static VerifyFragment getInstance(LoginViewModel loginViewModel) {
        return new VerifyFragment().setLoginViewModel(loginViewModel);
    }

    @Override
    protected ViewModel initViewModel() {
        return loginViewModel;
    }

    @SuppressLint("CheckResult")
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentVerifyEanfangBinding.inflate(getLayoutInflater());
        binding.setVm(loginViewModel);
        binding.setApp(getApp());
        loginViewModel.setVerifyBinding(binding);
        loginViewModel.LegalText(mActivity);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginViewModel.getTimer().cancel();
    }

}
