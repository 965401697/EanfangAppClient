package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.CollectActivity;
import net.eanfang.client.ui.activity.EvaluateActivity;
import net.eanfang.client.ui.activity.PersonInfoActivity;
import net.eanfang.client.ui.activity.SettingActivity;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.widget.InviteView;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:08
 * @email houzhongzhou@yeah.net
 * @desc 我的
 */

public class MyFragment extends BaseFragment {


    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_config;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        setTitle("我的");
        setLeftVisible(View.GONE);

        findViewById(R.id.iv_user_header).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), PersonInfoActivity.class));
        });
        findViewById(R.id.rel_message).setOnClickListener((v) -> {
            ToastUtil.get().showToast(getContext(), "待定");
        });
        findViewById(R.id.rel_evaluate).setOnClickListener((v) -> {
          startActivity(new Intent(getActivity(),EvaluateActivity.class));
        });
        findViewById(R.id.rel_collect).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(),CollectActivity.class));
        });
        findViewById(R.id.rel_invite).setOnClickListener((v) -> {
            InviteView inviteView = new InviteView(getActivity(), true);
            inviteView.show();
        });
        findViewById(R.id.rel_setting).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        });

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
