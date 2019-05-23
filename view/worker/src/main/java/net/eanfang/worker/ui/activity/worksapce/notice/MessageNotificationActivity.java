package net.eanfang.worker.ui.activity.worksapce.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AllMessageBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;

public class MessageNotificationActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_official)
    TextView tvOfficial;
    @BindView(R.id.tv_official_info)
    TextView tvOfficialInfo;
    @BindView(R.id.ll_official)
    LinearLayout llOfficial;
    @BindView(R.id.tv_sys_msg)
    TextView tvSysMsg;
    @BindView(R.id.tv_sys_msg_info)
    TextView tvSysMsgInfo;
    @BindView(R.id.ll_system_notice)
    LinearLayout llSystemNotice;
    @BindView(R.id.tv_bus_msg)
    TextView tvBusMsg;
    @BindView(R.id.tv_bus_msg_info)
    TextView tvBusMsgInfo;
    @BindView(R.id.ll_msg_list)
    LinearLayout llMsgList;

    private QBadgeView qBadgeViewSys = new QBadgeView(WorkerApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewBiz = new QBadgeView(WorkerApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewCam = new QBadgeView(WorkerApplication.get().getApplicationContext());

    // 消息数量
    private int mMessageCount = 0;
    private int mStystemCount = 0;
    private int mCmpCount = 0;
    private final int REQUST_REFRESH_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_notification);
        ButterKnife.bind(this);
        init();
        initView();
        initData();
    }

    private void initData() {
        doHttpNoticeCount();
    }

    private void initView() {
        qBadgeViewCam.bindTarget(findViewById(R.id.tv_official))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 0, true)
                .setBadgeTextSize(11, true);
        qBadgeViewBiz.bindTarget(findViewById(R.id.tv_bus_msg))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 0, true)
                .setBadgeTextSize(11, true);
        qBadgeViewSys.bindTarget(findViewById(R.id.tv_sys_msg))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 0, true)
                .setBadgeTextSize(11, true);
    }

    private void init() {
        setLeftBack();
    }

    @OnClick({R.id.ll_official, R.id.ll_system_notice, R.id.ll_msg_list})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.ll_official://官方通知
                bundle.putInt("mCamCount", mCmpCount);
                JumpItent.jump(MessageNotificationActivity.this, OfficialListActivity.class, bundle, REQUST_REFRESH_CODE);
                break;
            case R.id.ll_system_notice://系统通知
                bundle.putInt("mStystemCount", mStystemCount);
                JumpItent.jump(MessageNotificationActivity.this, SystemNoticeActivity.class, bundle, REQUST_REFRESH_CODE);
                break;
            case R.id.ll_msg_list://业务通知
                bundle.putInt("mMessageCount", mMessageCount);
                JumpItent.jump(MessageNotificationActivity.this, MessageListActivity.class, bundle, REQUST_REFRESH_CODE);
                break;
        }
    }


    private void doHttpNoticeCount() {
        EanfangHttp.get(UserApi.ALL_MESSAGE)
                .execute(new EanfangCallback<AllMessageBean>(MessageNotificationActivity.this, true, AllMessageBean.class, bean -> {
                    if (bean.getSys() > 0) {// 系统消息
                        initSysCount(bean.getSys());
                        mStystemCount = bean.getSys();
                    } else {
                        initSysCount(0);
                        mStystemCount = 0;
                    }
                    if (bean.getBiz() > 0) {// 业务通知
                        initBizCount(bean.getBiz());
                        mMessageCount = bean.getBiz();
                    } else {
                        initBizCount(0);
                        mMessageCount = 0;
                    }
                    if (bean.getCmp() > 0) {// 官方通知
                        initCmpCount(bean.getCmp());
                        mCmpCount = 0;
                    } else {
                        initCmpCount(0);
                        mCmpCount = 0;
                    }
                    /**
                     * 底部红点更新
                     * */
                    EventBus.getDefault().post(bean);
                }));
    }

    private void initCmpCount(Integer cam) {
        if (cam > 0) {
            ((TextView)findViewById(R.id.tv_official_info)).setText("新通知");
        } else {
            ((TextView)findViewById(R.id.tv_official_info)).setText("没有新通知");
        }
        qBadgeViewCam.setBadgeNumber(cam);
    }

    private void initBizCount(Integer biz) {
        if (biz > 0) {
            ((TextView) findViewById(R.id.tv_bus_msg_info)).setText("新消息");
        } else {
            ((TextView) findViewById(R.id.tv_bus_msg_info)).setText("没有新消息");
        }
        qBadgeViewBiz.setBadgeNumber(biz);
    }

    private void initSysCount(Integer sys) {
        if (sys > 0) {
            ((TextView) findViewById(R.id.tv_sys_msg_info)).setText("新消息");
        } else {
            ((TextView) findViewById(R.id.tv_sys_msg_info)).setText("没有新消息");
        }
        qBadgeViewSys.setBadgeNumber(sys);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUST_REFRESH_CODE) {
            doHttpNoticeCount();
        }
    }
}
