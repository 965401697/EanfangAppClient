package net.eanfang.client.ui.activity.leave_post;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;
import com.videogo.realplay.RealPlayStatus;
import com.videogo.util.LocalInfo;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostCheckDetailBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostCheckDetailViewModel;
import net.eanfang.client.ui.adapter.LeavePostCheckDetailAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :图像查岗详情
 */
public class LeavePostCheckDetailActivity extends BaseActivity implements Handler.Callback, SurfaceHolder.Callback {
    private ActivityLeavePostCheckDetailBinding mBinding;
    private LeavePostCheckDetailViewModel mViewModel;
    private LeavePostCheckDetailAdapter mAdapter;
    private boolean showTopContent;
    /**
     * 岗位id
     */
    private int mStationId;

    /**
     * 报警id
     */
    private int mAlertId;

    private EZPlayer ezPlayer;
    /**
     * 设备序列号
     */
    private String mDeviceSerial;

    private Handler mHandler = null;
    private LocalInfo mLocalInfo = null;
    private SurfaceHolder mRealPlaySh = null;
    private int mStatus = RealPlayStatus.STATUS_INIT;
    public static final int MSG_PLAY_UI_UPDATE = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_check_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setLeftBack(true);
        mStationId = getIntent().getIntExtra("stationId", 0);
        mAlertId = getIntent().getIntExtra("mAlertId", 0);
        mDeviceSerial = getIntent().getStringExtra("deviceSerial");
        showTopContent = getIntent().getBooleanExtra("isShowTopContent", true);
        if (showTopContent) {
            setTitle("图像查岗");
            mHandler = new Handler(this);
            mRealPlaySh = mBinding.realplaySv.getHolder();
            mRealPlaySh.addCallback(this);
            mViewModel.getDeviceListData(mStationId);
            EZOpenSDK.getInstance().setAccessToken("ra.812fdm4f2i71n8zcbxkx9ojo86e0dr9p-4jm1670lc2-1epu9vg-ikac74qei");
            ezPlayer = EZOpenSDK.getInstance().createPlayer(mDeviceSerial, 1);
            mLocalInfo = LocalInfo.getInstance();
            //该handler将被用于从播放器向handler传递消息
            ezPlayer.setHandler(mHandler);
            //设置播放器的显示Surface
            ezPlayer.setSurfaceHold(mRealPlaySh);
            mStatus = RealPlayStatus.STATUS_START;
            //开启直播
            ezPlayer.startRealPlay();
        } else {
            setTitle("联系责任人");
            mBinding.tvLeavePostCheckDetailTitle.setVisibility(View.GONE);
            mBinding.llPlayer.setVisibility(View.GONE);
            mViewModel.getContactsListData(mAlertId);
        }
        mAdapter = new LeavePostCheckDetailAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> mViewModel.callToPerson(LeavePostCheckDetailActivity.this, adapter, position));
        mBinding.recLeavePostCheckDetailPerson.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mBinding.recLeavePostCheckDetailPerson);
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostCheckDetailViewModel.class);
        mViewModel.getLeavePostDeviceDetail().observe(this, this::initData);
        return mViewModel;
    }

    private void initData(LeavePostDeviceInfoBean leavePostDeviceInfoBean) {
        if (leavePostDeviceInfoBean == null) {
            return;
        }
        if (showTopContent) {
            mBinding.tvLeavePostCheckDetailTitle.setText(MessageFormat.format("{0}\t({1})\t{2}", leavePostDeviceInfoBean.getStationName(), leavePostDeviceInfoBean.getStationCode(), leavePostDeviceInfoBean.getDeviceEntity().getDeviceName()));
        }

        ArrayList<LeavePostDeviceInfoBean.ChargeStaffListBean> beans = new ArrayList<>(leavePostDeviceInfoBean.getChargeStaffList());
        LeavePostDeviceInfoBean.ChargeStaffListBean bean1 = new LeavePostDeviceInfoBean.ChargeStaffListBean();
        bean1.setType(0);
        bean1.setTitle("负责人");
        beans.add(0, bean1);
        if (leavePostDeviceInfoBean.getDutyStaffList().size() > 0) {
            LeavePostDeviceInfoBean.ChargeStaffListBean bean2 = new LeavePostDeviceInfoBean.ChargeStaffListBean();
            bean2.setType(0);
            bean2.setTitle("值班人");
            beans.add(leavePostDeviceInfoBean.getChargeStaffList().size() + 1, bean2);
            beans.addAll(leavePostDeviceInfoBean.getDutyStaffList());
        }
        mAdapter.setNewData(beans);
    }


    /**
     * 声音
     */
    private void setRealPlaySound() {
        if (ezPlayer != null) {
            if (mLocalInfo.isSoundOpen()) {
                ezPlayer.openSound();
            } else {
                ezPlayer.closeSound();
            }
        }
    }

    private void updateRealPlayUI() {
        mBinding.llInclued.realplayLoading.setVisibility(View.INVISIBLE);
        mBinding.llPlayControl.realplayPlayBtn.setBackgroundResource(R.drawable.play_stop_selector);
    }


    @SuppressLint("NewApi")
    @Override
    public boolean handleMessage(Message msg) {
        if (this.isFinishing()) {
            return false;
        }
        switch (msg.what) {
            case MSG_PLAY_UI_UPDATE:
                updateRealPlayUI();
                break;
//            case MSG_AUTO_START_PLAY:
//                startRealPlay();
//                break;
            // 102 声音
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:
//                setRealPlaySound();
//                break;
            default:
                // do nothing
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
//        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
//            mScreenOrientationHelper.portrait();
//            return;
//        }
        if (ezPlayer != null) {
            ezPlayer.stopRealPlay();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ezPlayer != null) {
            ezPlayer.release();
        }
//        mHandler.removeMessages(MSG_AUTO_START_PLAY);
//        mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
//        mHandler.removeMessages(MSG_CLOSE_PTZ_PROMPT);
//        mHandler.removeMessages(MSG_HIDE_PAGE_ANIM);
        mHandler = null;
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (ezPlayer != null) {
            ezPlayer.setSurfaceHold(surfaceHolder);
        }
        mRealPlaySh = surfaceHolder;
        if (mStatus == RealPlayStatus.STATUS_INIT) {
            // 开始播放
            ezPlayer.startRealPlay();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (ezPlayer != null) {
            ezPlayer.setSurfaceHold(surfaceHolder);
        }
        mRealPlaySh = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (ezPlayer != null) {
            ezPlayer.setSurfaceHold(null);
        }
        mRealPlaySh = null;
    }
}
