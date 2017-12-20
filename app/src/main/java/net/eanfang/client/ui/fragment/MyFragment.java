package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.model.LoginBean;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.ui.activity.my.CollectActivity;
import net.eanfang.client.ui.activity.my.EvaluateActivity;
import net.eanfang.client.ui.activity.my.MessageListActivity;
import net.eanfang.client.ui.activity.my.PersonInfoActivity;
import net.eanfang.client.ui.activity.my.SettingActivity;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.widget.InviteView;
import net.eanfang.client.util.StringUtils;

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
        setTitle("我的");
        setLeftVisible(View.GONE);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        iv_header = (SimpleDraweeView) findViewById(R.id.iv_user_header);
        findViewById(R.id.iv_user_header).setOnClickListener((v) -> {
            PersonInfoActivity.jumpToActivity(getActivity());
        });
        findViewById(R.id.rel_message).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), MessageListActivity.class));
        });
        findViewById(R.id.rel_evaluate).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), EvaluateActivity.class));
        });
        findViewById(R.id.rel_collect).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CollectActivity.class));
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
    public void onResume() {
        super.onResume();
        initDatas();
    }

    public void initDatas() {
        LoginBean user = EanfangApplication.getApplication().getUser();
        tv_user_name.setText(user.getAccount().getNickName());

        if (!StringUtils.isEmpty(user.getAccount().getAvatar())) {
            iv_header.setImageURI(Uri.parse(user.getAccount().getAvatar()));
        }

    }
}
