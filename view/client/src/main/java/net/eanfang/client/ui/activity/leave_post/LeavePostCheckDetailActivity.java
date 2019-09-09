package net.eanfang.client.ui.activity.leave_post;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.videogo.constant.Constant;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZConstants.EZVideoLevel;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;
import com.videogo.realplay.RealPlayStatus;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.LocalInfo;
import com.videogo.util.Utils;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostCheckDetailBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostCheckDetailViewModel;
import net.eanfang.client.ui.adapter.LeavePostCheckDetailAdapter;
import net.eanfang.client.util.ScreenOrientationHelper;

import java.text.MessageFormat;
import java.util.ArrayList;

import static com.videogo.openapi.EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_FAIL;
import static com.videogo.openapi.EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_SUCCESS;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :图像查岗详情
 */
public class LeavePostCheckDetailActivity extends BaseActivity implements Handler.Callback, SurfaceHolder.Callback {
    public static final String LEAVE_MODLE = "LEAVE_MODLE";
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
    public static final int MSG_AUTO_START_PLAY = 202;
    private int mOrientation = Configuration.ORIENTATION_PORTRAIT;
    private float mRealRatio = Constant.LIVE_VIEW_RATIO;
    private EZConstants.EZVideoLevel mCurrentQulityMode = EZConstants.EZVideoLevel.VIDEO_LEVEL_HD;
    private ScreenOrientationHelper mScreenOrientationHelper;
    private int mForceOrientation = 0;
    private PopupWindow mQualityPopupWindow = null;
    private View view;

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
            //保持屏幕常亮 Keep the screen on
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            mScreenOrientationHelper = new ScreenOrientationHelper(this, mBinding.llPlayControl.fullscreenButton, null);
            startRealPlay();
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
        view = findViewById(R.id.layout_include);
    }

    private void initListener() {
        // 暂停播放按钮
        mBinding.llPlayControl.realplayPlayBtn.setOnClickListener((v) -> {
            if (mStatus != RealPlayStatus.STATUS_STOP) {
                stopRealPlay();
//                setRealPlayStopUI();
            } else {
                startRealPlay();
            }
        });
        // 暂停后 播放
        mBinding.llInclued.realplayPlayIv.setOnClickListener((v) -> {
            if (mStatus == RealPlayStatus.STATUS_STOP) {
                startRealPlay();
            }
        });
        // 清晰度
        mBinding.llPlayControl.realplayQualityBtn.setOnClickListener((v) -> {
            openQualityPopupWindow(mBinding.llPlayControl.realplayQualityBtn);
        });
        // 声音
        mBinding.llPlayControl.realplaySoundBtn.setOnClickListener((v) -> {
            if (mLocalInfo.isSoundOpen()) {
                mLocalInfo.setSoundOpen(false);
                mBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_off_selector);
            } else {
                mLocalInfo.setSoundOpen(true);
                mBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_selector);
            }
            if (ezPlayer != null) {
                if (mLocalInfo.isSoundOpen()) {
                    ezPlayer.openSound();
                } else {
                    ezPlayer.closeSound();
                }
            }
        });
    }

    private void startRealPlay() {
        if (mStatus == RealPlayStatus.STATUS_START || mStatus == RealPlayStatus.STATUS_PLAY) {
            return;
        }
        mBinding.llPlayControl.realplayPlayBtn.setBackgroundResource(R.drawable.play_stop_selector);
        // 视频中的播放按钮
        mBinding.llInclued.realplayPlayIv.setVisibility(View.GONE);
        mStatus = RealPlayStatus.STATUS_START;
        mHandler = new Handler(this);
        mRealPlaySh = mBinding.realplaySv.getHolder();
        mRealPlaySh.addCallback(this);
        mViewModel.getDeviceListData(mStationId);
        ezPlayer = EZOpenSDK.getInstance().createPlayer(mDeviceSerial, 1);
        mLocalInfo = LocalInfo.getInstance();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mLocalInfo.setScreenWidthHeight(metric.widthPixels, metric.heightPixels);
        mLocalInfo.setNavigationBarHeight((int) Math.ceil(25 * getResources().getDisplayMetrics().density));
        if (ezPlayer == null) {
            return;
        }
        //该handler将被用于从播放器向handler传递消息
        ezPlayer.setHandler(mHandler);
        //设置播放器的显示Surface
        ezPlayer.setSurfaceHold(mRealPlaySh);
        //开启直播
        ezPlayer.startRealPlay();
        mBinding.llInclued.realplayLoadingRl.setVisibility(View.INVISIBLE);
        mBinding.llInclued.realplayLoading.setVisibility(View.GONE);
        setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        mStatus = RealPlayStatus.STATUS_PLAY;
    }

    private void stopRealPlay() {
        mBinding.llPlayControl.realplayPlayBtn.setBackgroundResource(R.drawable.play_play_selector);
        mStatus = RealPlayStatus.STATUS_STOP;
        if (ezPlayer != null) {
            ezPlayer.stopLocalRecord();
            ezPlayer.stopRealPlay();
        }
        // 视频中的播放按钮
        mBinding.llInclued.realplayPlayIv.setVisibility(View.VISIBLE);
        mBinding.llInclued.realplayLoadingRl.setVisibility(View.VISIBLE);
        setForceOrientation(0);
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

        setRealPlaySound();
        startRealPlay();
        setVideoLevel();
    }

    /**
     * 声音
     */
    private void setRealPlaySound() {
        if (ezPlayer != null) {
            if (mLocalInfo.isSoundOpen()) {
                ezPlayer.openSound();
                mBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_selector);
            } else {
                ezPlayer.closeSound();
                mBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_off_selector);
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
        // 横屏
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            view.setVisibility(View.GONE);
            mBinding.tvLeavePostCheckDetailTitle.setVisibility(View.GONE);
            mBinding.recLeavePostCheckDetailPerson.setVisibility(View.GONE);
        } else {
            WindowManager.LayoutParams lp2 = getWindow().getAttributes();
            lp2.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp2);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            view.setVisibility(View.VISIBLE);
            mBinding.tvLeavePostCheckDetailTitle.setVisibility(View.VISIBLE);
            mBinding.recLeavePostCheckDetailPerson.setVisibility(View.VISIBLE);
        }
        setRealPlaySvLayout();
        super.onConfigurationChanged(newConfig);
    }


    private void setVideoLevel() {
        if (ezPlayer == null) {
            return;
        }
        /**
         * 清晰度
         * */
        if (CacheKit.get().get(LEAVE_MODLE, EZVideoLevel.class) != null) {
            mCurrentQulityMode = CacheKit.get().get(LEAVE_MODLE, EZVideoLevel.class);
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
            case MSG_AUTO_START_PLAY:
                startRealPlay();
                break;
            // 102 播放声音 初始化ui 成功
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:
                initUI();
                break;
            // 103
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_FAIL:
                handlePlayFail(msg.obj);
                break;
            case MSG_SET_VEDIOMODE_SUCCESS:
                handleSetVedioModeSuccess();
                break;
            case MSG_SET_VEDIOMODE_FAIL:
                handleSetVedioModeFail(msg.arg1);
                break;
            default:
                // do nothing
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            mScreenOrientationHelper.portrait();
            return;
        }
        stopRealPlay();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            return;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mScreenOrientationHelper != null) {
            mScreenOrientationHelper.postOnStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ezPlayer != null) {
            ezPlayer.release();
        }
        if (mHandler != null) {
            mHandler.removeMessages(MSG_AUTO_START_PLAY);
            mHandler = null;
        }
        mScreenOrientationHelper = null;
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

    private void setOrientation(int sensor) {
        if (mForceOrientation != 0) {
            Log.e("GG", "setOrientation mForceOrientation:" + mForceOrientation);
            return;
        }

        if (sensor == ActivityInfo.SCREEN_ORIENTATION_SENSOR)
            mScreenOrientationHelper.enableSensorOrientation();
        else
            mScreenOrientationHelper.disableSensorOrientation();
    }

    public void setForceOrientation(int orientation) {
        if (mForceOrientation == orientation) {
            Log.e("GG", "setForceOrientation no change");
            return;
        }
        mForceOrientation = orientation;
        if (mForceOrientation != 0) {
            if (mForceOrientation != mOrientation) {
                if (mForceOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    mScreenOrientationHelper.portrait();
                } else {
                    mScreenOrientationHelper.landscape();
                }
            }
            mScreenOrientationHelper.disableSensorOrientation();
        } else {
            updateOrientation();
        }
    }

    private void updateOrientation() {
        if (mStatus == RealPlayStatus.STATUS_PLAY) {
            setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else {
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
        }
    }

    private void openQualityPopupWindow(View anchor) {
        if (ezPlayer == null) {
            return;
        }
        closeQualityPopupWindow();
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup layoutView = (ViewGroup) layoutInflater.inflate(R.layout.realplay_quality_items, null, true);

        Button qualityHdBtn = (Button) layoutView.findViewById(R.id.quality_hd_btn);
        qualityHdBtn.setOnClickListener(mOnPopWndClickListener);
        Button qualityBalancedBtn = (Button) layoutView.findViewById(R.id.quality_balanced_btn);
        qualityBalancedBtn.setOnClickListener(mOnPopWndClickListener);
        Button qualityFlunetBtn = (Button) layoutView.findViewById(R.id.quality_flunet_btn);
        qualityFlunetBtn.setOnClickListener(mOnPopWndClickListener);

        // 清晰度 0-流畅，1-均衡，2-高清，3-超清
        mQualityPopupWindow = new PopupWindow(layoutView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        mQualityPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mQualityPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mQualityPopupWindow = null;
                closeQualityPopupWindow();
            }
        });
        try {
            int widthMode = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int heightMode = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            mQualityPopupWindow.getContentView().measure(widthMode, heightMode);
            int yOffset = -(anchor.getHeight() + mQualityPopupWindow.getContentView().getMeasuredHeight());
            mQualityPopupWindow.showAsDropDown(anchor, 0, yOffset);
        } catch (Exception e) {
            e.printStackTrace();
            closeQualityPopupWindow();
        }
    }

    private void closeQualityPopupWindow() {
        if (mQualityPopupWindow != null) {
            dismissPopWindow(mQualityPopupWindow);
            mQualityPopupWindow = null;
        }
    }

    private View.OnClickListener mOnPopWndClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.quality_hd_btn:
                    setQualityMode(EZVideoLevel.VIDEO_LEVEL_HD);
                    break;
                case R.id.quality_balanced_btn:
                    setQualityMode(EZVideoLevel.VIDEO_LEVEL_BALANCED);
                    break;
                case R.id.quality_flunet_btn:
                    setQualityMode(EZVideoLevel.VIDEO_LEVEL_FLUNET);
                    break;
                default:
                    break;
            }
        }
    };

    private void setQualityMode(final EZVideoLevel mode) {
        // 检查网络是否可用 Check if the network is available
        if (!ConnectionDetector.isNetworkAvailable(LeavePostCheckDetailActivity.this)) {
            // 提示没有连接网络 Prompt not to connect to the network
            showToast("设置失败，请检查您的网络");
            return;
        }
        if (ezPlayer != null) {
            try {
                EZOpenSDK.getInstance().setVideoLevel(mDeviceSerial, 1, mode.getVideoLevel());
                mCurrentQulityMode = mode;
                Message msg = Message.obtain();
                msg.what = MSG_SET_VEDIOMODE_SUCCESS;
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                mCurrentQulityMode = EZVideoLevel.VIDEO_LEVEL_FLUNET;
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = MSG_SET_VEDIOMODE_FAIL;
                mHandler.sendMessage(msg);
            }
        }

    }

    /**
     * 切换分辨率成功
     */
    private void handleSetVedioModeSuccess() {
        closeQualityPopupWindow();
        CacheKit.get().put(LEAVE_MODLE, mCurrentQulityMode, CacheMod.SharePref);
        setVideoLevel();
        if (mStatus == RealPlayStatus.STATUS_PLAY) {
            // 停止播放 Stop play
            stopRealPlay();
            SystemClock.sleep(500);
            // 开始播放 start play
            startRealPlay();
        }
    }

    /**
     * 切换分辨率失败
     */
    private void handleSetVedioModeFail(int errorCode) {
        closeQualityPopupWindow();
        setVideoLevel();
        showToast("视频质量切换失败");
    }

    private void dismissPopWindow(PopupWindow popupWindow) {
        if (popupWindow != null && !isFinishing()) {
            try {
                popupWindow.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}
