package net.eanfang.client.ui.activity.leave_post;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.videogo.constant.Constant;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZConstants.EZVideoLevel;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;
import com.videogo.realplay.RealPlayStatus;
import com.videogo.util.LocalInfo;
import com.videogo.util.Utils;

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
    private int mOrientation = Configuration.ORIENTATION_PORTRAIT;
    private float mRealRatio = Constant.LIVE_VIEW_RATIO;
    private EZConstants.EZVideoLevel mCurrentQulityMode = EZConstants.EZVideoLevel.VIDEO_LEVEL_HD;

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
        initListener();
    }

    private void initListener() {

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

    private void initUI() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mBinding.llPlayControl.realplaySoundBtn.setVisibility(View.VISIBLE);
        if (mLocalInfo.isSoundOpen()) {
            mBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_selector);
        } else {
            mBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_off_selector);
        }

//        setRealPlaySound();
        setVideoLevel();
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

    private void setRealPlaySvLayout() {
        final int screenWidth = mLocalInfo.getScreenWidth();
        final int screenHeight = (mOrientation == Configuration.ORIENTATION_PORTRAIT) ? (mLocalInfo.getScreenHeight() - mLocalInfo
                .getNavigationBarHeight()) : mLocalInfo.getScreenHeight();
        final RelativeLayout.LayoutParams realPlaySvlp = Utils.getPlayViewLp(mRealRatio, mOrientation,
                mLocalInfo.getScreenWidth(), (int) (mLocalInfo.getScreenWidth() * Constant.LIVE_VIEW_RATIO),
                screenWidth, screenHeight);
        RelativeLayout.LayoutParams svLp = new RelativeLayout.LayoutParams(realPlaySvlp.width, realPlaySvlp.height);
        mBinding.vgPlayWindow.setLayoutParams(svLp);
    }

    /**
     * 屏幕选择
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mOrientation = newConfig.orientation;
        super.onConfigurationChanged(newConfig);
    }

    private void setVideoLevel() {
        if (ezPlayer == null) {
            return;
        }

        /**
         *
         * 视频质量，2-高清，1-标清，0-流畅
         * Video quality, 2-HD, 1-standard, 0- smooth
         *
         */
        if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_FLUNET.getVideoLevel()) {
            mBinding.llPlayControl.realplayQualityBtn.setText(R.string.quality_flunet);
        } else if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_BALANCED.getVideoLevel()) {
            mBinding.llPlayControl.realplayQualityBtn.setText(R.string.quality_balanced);
        } else if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_HD.getVideoLevel()) {
            mBinding.llPlayControl.realplayQualityBtn.setText(R.string.quality_hd);
        } else if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_SUPERCLEAR.getVideoLevel()) {
            mBinding.llPlayControl.realplayQualityBtn.setText(R.string.quality_super_hd);
        } else {
            mBinding.llPlayControl.realplayQualityBtn.setText("unknown");
        }
    }

    private void handlePlayFail(Object obj) {
        int errorCode = 0;
        if (obj != null) {
            ErrorInfo errorInfo = (ErrorInfo) obj;
            errorCode = errorInfo.errorCode;
            Log.e("GG ezplayer", "handlePlayFail:" + errorInfo.errorCode);
        }

        mStatus = RealPlayStatus.STATUS_STOP;
        if (ezPlayer != null) {
            ezPlayer.stopRealPlay();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public boolean handleMessage(Message msg) {
        if (this.isFinishing()) {
            return false;
        }
        switch (msg.what) {
            //200
            case MSG_PLAY_UI_UPDATE:
                updateRealPlayUI();
                break;
//            case MSG_AUTO_START_PLAY:
//                startRealPlay();
//                break;
            // 102 播放声音 初始化ui 成功
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:
                initUI();
                break;
            // 103
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_FAIL:
                handlePlayFail(msg.obj);
                break;
            default:
                // do nothing
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
//            mScreenOrientationHelper.portrait();
//            return;
//        }
//        if (ezPlayer != null) {
//            ezPlayer.stopRealPlay();
//        }
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
