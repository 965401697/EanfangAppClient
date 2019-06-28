package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.ui.activity.InviteFriendActivity;
import com.eanfang.ui.activity.QrCodeShowActivity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.my.CollectActivity;
import net.eanfang.client.ui.activity.my.EvaluateActivity;
import net.eanfang.client.ui.activity.my.PersonInfoActivity;
import net.eanfang.client.ui.activity.my.SettingActivity;
import net.eanfang.client.ui.widget.InviteView;

import butterknife.BindView;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:08
 * @email houzhongzhou@yeah.net
 * @desc 我的
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.iv_user_header)
    CircleImageView ivUserHeader;
    private TextView tv_user_name;
    private ImageView mIvPersonalQRCode;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_config;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        headViewSize(ivUserHeader, (int) getResources().getDimension(com.eanfang.R.dimen.dimen_155));
        tv_user_name = findViewById(R.id.tv_user_name);
        mIvPersonalQRCode = findViewById(R.id.iv_personalQRCode);
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
        //邀请抢红包
        findViewById(R.id.img_invite).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), InviteFriendActivity.class));
        });
        findViewById(R.id.tv_personalQRCode).setOnClickListener(this::gotoQrPage);
    }

    @Override
    protected void setListener() {
        mIvPersonalQRCode.setOnClickListener(this::gotoQrPage);
    }


    @Override
    public void onResume() {
        super.onResume();
        initDatas();
    }

    public void initDatas() {
        LoginBean user = ClientApplication.get().getLoginBean();
        if (!StringUtils.isEmpty(user.getAccount().getNickName())) {
            tv_user_name.setText(user.getAccount().getNickName());
        }

        if (!StringUtils.isEmpty(user.getAccount().getAvatar())) {
            GlideUtil.intoImageView(getActivity(), Uri.parse(BuildConfig.OSS_SERVER + user.getAccount().getAvatar()), ivUserHeader);
        }

    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBar() {
        /**
         * 获取状态栏高度
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    public void setQrCode() {

    }

    /**
     * 跳转我的二维码
     *
     * @param v
     */
    private void gotoQrPage(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("qrcodeTitle", ClientApplication.get().getLoginBean().getAccount().getRealName());
        bundle.putString("qrcodeAddress", ClientApplication.get().getLoginBean().getAccount().getQrCode());
        bundle.putString("qrcodeMessage", "personal");
        JumpItent.jump(getActivity(), QrCodeShowActivity.class, bundle);
    }
}
