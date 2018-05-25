package net.eanfang.client.ui.fragment.customservice;

import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;
import com.eanfang.witget.ArcProgressView;

import net.eanfang.client.R;

/**
 * @author Guanluocang
 * @date on 2018/4/23  11:57
 * @decision 个人用户
 */
public class PersonalServiceFragment extends BaseFragment {

    private TextView tvServicePhone;

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
        tvServicePhone = findViewById(R.id.tv_service_phone);
    }

    @Override
    protected void setListener() {
        tvServicePhone.setOnClickListener((view) -> CallUtils.call(getContext(), "010-58778731"));
    }
}
