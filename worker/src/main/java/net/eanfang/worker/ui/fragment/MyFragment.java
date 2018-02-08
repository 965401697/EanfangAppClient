package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.model.WorkerInfoBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.AuthWorkerInfoActivity;
import net.eanfang.worker.ui.activity.my.EvaluateActivity;
import net.eanfang.worker.ui.activity.my.MessageListActivity;
import net.eanfang.worker.ui.activity.my.PersonInfoActivity;
import net.eanfang.worker.ui.activity.my.SettingActivity;
import net.eanfang.worker.ui.widget.InviteView;


/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:08
 * @email houzhongzhou@yeah.net
 * @desc 我的
 */

public class MyFragment extends BaseFragment {
    private TextView tv_user_name, tvVerfiy, tvWorkerStatus;
    private RelativeLayout rlWorkingStatus;
    private SimpleDraweeView iv_header;


    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_config;
    }

    @Override
    protected void initData(Bundle arguments) {
        getWorkInfo();
    }

    private void getWorkInfo() {
        EanfangHttp.get(UserApi.GET_WORKER_INFO)
                .execute(new EanfangCallback<WorkerInfoBean>(getActivity(), true, WorkerInfoBean.class, (bean) -> {
                    setOnClick(bean);
                }));
    }

    private void setOnClick(WorkerInfoBean bean) {

        //status 0草稿1认证中2认证通过3认证拒绝
        if (bean.getStatus() == 0) {
            tvVerfiy.setText("技师未认证，待认证");
        } else if (bean.getStatus() == 1) {
            tvVerfiy.setText("认证中");
        } else if (bean.getStatus() == 2) {
            tvVerfiy.setText("已认证");
        } else if (bean.getStatus() == 3) {
            tvVerfiy.setText("认证失败，请重新认证");
        }
        tvVerfiy.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), AuthWorkerInfoActivity.class);
            intent.putExtra("bean", bean);
            startActivity(intent);
        });
    }


    @Override
    protected void initView() {
        setTitle("我的");
        setLeftVisible(View.GONE);
        tvVerfiy = (TextView) findViewById(R.id.tv_verfiy);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        iv_header = (SimpleDraweeView) findViewById(R.id.iv_user_header);
        tvWorkerStatus = (TextView) findViewById(R.id.tv_worker_status);
        rlWorkingStatus = (RelativeLayout) findViewById(R.id.rel_working);
        findViewById(R.id.iv_user_header).setOnClickListener((v) -> {
            PersonInfoActivity.jumpToActivity(getActivity());
        });
        findViewById(R.id.rel_message).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), MessageListActivity.class));
        });
        findViewById(R.id.rel_evaluate).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), EvaluateActivity.class));
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
        rlWorkingStatus.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(getActivity(), "", GetConstDataUtils.getWorkerStatus(), (index, item) -> {
                tvWorkerStatus.setText(item);
                setWorkStatus(Config.get().getConstBean().getData().getShopConstant().get(Constant.WORK_STATUS).indexOf(item));
            });

        });
    }

    /**
     * 更改技师工作状态
     */
    private void setWorkStatus(int status) {
        EanfangHttp.post(UserApi.GET_WORKER_CHANGE)
                .params("accId", EanfangApplication.getApplication().getAccId())
                .params("status", status)
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {

                }));
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
