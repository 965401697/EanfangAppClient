package net.eanfang.client.ui.fragment.customservice;

import android.os.Bundle;

import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;

import net.eanfang.client.R;

/**
 * @author Guanluocang
 * @date on 2018/4/23  11:57
 * @decision 个人用户
 */
public class PersonalServiceFragment extends BaseFragment {


    public static PersonalServiceFragment getInstance() {
        PersonalServiceFragment personalServiceFragment = new PersonalServiceFragment();
        return personalServiceFragment;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setListener() {
        findViewById(R.id.tv_service_phone).setOnClickListener((view) -> CallUtils.call(getContext(), "010-58778731"));
    }
}
