package net.eanfang.client.ui.fragment.securitydetail;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.bean.SecurityCompanyDetailBean;
import com.eanfang.util.JumpItent;
import com.eanfang.witget.CustomRadioGroup;

import net.eanfang.client.R;
import net.eanfang.client.databinding.FragmentServiceBinding;
import net.eanfang.client.ui.activity.worksapce.SecurityCompanyAllAreaActivity;
import net.eanfang.client.viewmodel.SecurityCompanyDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/8/24  11:39
 * @description 服务信息
 */

public class ServiceFragment extends BaseFragment {

    private FragmentServiceBinding fragmentServiceBinding;
    @Setter
    @Accessors(chain = true)
    private SecurityCompanyDetailViewModel securityCompanyDetailViewModel;

    private List<String> mAreaSmall = new ArrayList<>();
    private List<String> mAreaAll = new ArrayList<>();

    public static ServiceFragment getInstance(SecurityCompanyDetailViewModel mSecurityCompanyDetailViewModel) {
        return new ServiceFragment().setSecurityCompanyDetailViewModel(mSecurityCompanyDetailViewModel);
    }


    @Override
    protected ViewModel initViewModel() {
        fragmentServiceBinding = FragmentServiceBinding.inflate(getLayoutInflater());
        securityCompanyDetailViewModel.setFragmentServiceBinding(fragmentServiceBinding);
        securityCompanyDetailViewModel.getSecurityCompanyDetailBeanMutableLiveData().observe(this, this::setData);
        return securityCompanyDetailViewModel;
    }

    private void setData(SecurityCompanyDetailBean companyDetailBean) {
        // 业务类型
        addView(fragmentServiceBinding.getRoot().getContext(), fragmentServiceBinding.rgType, companyDetailBean.getBizList());
        // 业务领域
        addView(fragmentServiceBinding.getRoot().getContext(), fragmentServiceBinding.rgArea, companyDetailBean.getSysList());
        // 服务区域
        mAreaAll = companyDetailBean.getAreaList();
        if (mAreaAll != null && mAreaAll.size() > 4) {
            mAreaSmall.clear();
            mAreaSmall.add(mAreaAll.get(0));
            mAreaSmall.add(mAreaAll.get(1));
            mAreaSmall.add(mAreaAll.get(2));
            mAreaSmall.add(mAreaAll.get(3));
        } else {
            mAreaSmall.clear();
            mAreaSmall.addAll(mAreaAll);
        }
        addView(fragmentServiceBinding.getRoot().getContext(), fragmentServiceBinding.rgServiceArea, mAreaSmall);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        initListener();
        return fragmentServiceBinding.getRoot();
    }

    private void initListener() {
        fragmentServiceBinding.tvAllArea.setOnClickListener((v) -> {
            if (mAreaAll != null && mAreaAll.size() > 4) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("areaList", (ArrayList<String>) mAreaAll);
                JumpItent.jump(getActivity(), SecurityCompanyAllAreaActivity.class, bundle);
            } else {
                showToast("暂无更多");
            }
        });
    }


    public static void addView(final Context context, CustomRadioGroup
            parent, List<String> list) {
        parent.removeAllViews();
        RadioGroup.LayoutParams pa = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < list.size(); i++) {
            final RadioButton radioButton = new RadioButton(context);
            pa.setMargins(22, 22, 22, 30);
            radioButton.setLayoutParams(pa);
            radioButton.setText(list.get(i));
            radioButton.setTag(i);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextSize(12);
            radioButton.setPadding(20, 20, 20, 20);
            radioButton.setBackground(null);
            radioButton.setButtonDrawable(null);
            radioButton.setTextColor(Color.parseColor("#666666"));
            radioButton.setBackgroundResource(R.drawable.bg_worker_detail_type);
            parent.addView(radioButton);
        }
    }

}
