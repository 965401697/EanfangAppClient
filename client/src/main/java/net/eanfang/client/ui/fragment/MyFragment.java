package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.application.EanfangApplication;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.my.CollectActivity;
import net.eanfang.client.ui.activity.my.EvaluateActivity;
import net.eanfang.client.ui.activity.my.PersonInfoActivity;
import net.eanfang.client.ui.activity.my.SettingActivity;
import net.eanfang.client.ui.widget.InviteView;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:08
 * @email houzhongzhou@yeah.net
 * @desc 我的
 */

public class MyFragment extends BaseFragment {
    private TextView tv_user_name;
    private SimpleDraweeView iv_header;


    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_config;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        iv_header = (SimpleDraweeView) findViewById(R.id.iv_user_header);
        findViewById(R.id.iv_user_header).setOnClickListener((v) -> {
            PersonInfoActivity.jumpToActivity(getActivity());
        });

        findViewById(R.id.rl_evaluate).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), EvaluateActivity.class));
        });
        findViewById(R.id.rl_collect).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CollectActivity.class));
        });
        findViewById(R.id.rl_ivite).setOnClickListener((v) -> {
            InviteView inviteView = new InviteView(getActivity(), true);
            inviteView.show();
        });
        findViewById(R.id.iv_setting).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        });

    }

    @Override
    protected void setListener() {

    }


    @Override
    public void onResume() {
        super.onResume();
        initDatas();
    }

    public void initDatas() {
        LoginBean user = EanfangApplication.getApplication().getUser();
        tv_user_name.setText(user.getAccount().getNickName());

        if (!StringUtils.isEmpty(user.getAccount().getAvatar())) {
            iv_header.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + user.getAccount().getAvatar()));
        }

    }
}
