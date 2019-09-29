package net.eanfang.client.ui.activity.worksapce.monitor.device;

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

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.JumpItent;
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
import net.eanfang.client.databinding.ActivityMonitorDeviceDetailBinding;
import net.eanfang.client.util.ScreenOrientationHelper;
import net.eanfang.client.viewmodel.device.MonitorDeviceDetailViewModle;

import static com.videogo.openapi.EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_FAIL;
import static com.videogo.openapi.EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_SUCCESS;

/**
 * @author guanluocang
 * @data 2019/9/11  16:27
 * @description 摄像机详情页
 */

public class MonitorDeviceDetailActivity extends BaseActivity implements Handler.Callback, SurfaceHolder.Callback {
    public static final String LEAVE_MODLE = "LEAVE_MODLE";
    private ActivityMonitorDeviceDetailBinding monitorDeviceDetailBinding;

    private MonitorDeviceDetailViewModle monitorDeviceDetailViewModle;
    private String mDeviceName;


    private EZPlayer ezPlayer;
    private ScreenOrientationHelper mScreenOrientationHelper;
    private Handler mHandler = null;
    private LocalInfo mLocalInfo = null;
    private SurfaceHolder mRealPlaySh = null;
    private int mStatus = RealPlayStatus.STATUS_INIT;
    public static final int MSG_PLAY_UI_UPDATE = 200;
    public static final int MSG_AUTO_START_PLAY = 202;
    private int mOrientation = Configuration.ORIENTATION_PORTRAIT;
    private float mRealRatio = Constant.LIVE_VIEW_RATIO;
    private EZConstants.EZVideoLevel mCurrentQulityMode = EZConstants.EZVideoLevel.VIDEO_LEVEL_HD;
    private int mForceOrientation = 0;
    private PopupWindow mQualityPopupWindow = null;
    private View view;
    /**
     * 设备序列号
     */
    private String mDeviceSerial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDeviceDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        mDeviceSerial = getIntent().getStringExtra("deviceSerial");
        mDeviceName = getIntent().getStringExtra("mDeviceName");
        setTitle(mDeviceName);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mScreenOrientationHelper = new ScreenOrientationHelper(this, monitorDeviceDetailBinding.llPlayControl.fullscreenButton, null);
        view = findViewById(R.id.layout_include);
        initListener();
        startRealPlay();
        monitorDeviceDetailViewModle.init();
    }

    @Override
    protected ViewModel initViewModel() {
        monitorDeviceDetailViewModle = LViewModelProviders.of(this, MonitorDeviceDetailViewModle.class);
        monitorDeviceDetailViewModle.setMonitorDeviceDetailBinding(monitorDeviceDetailBinding);
        return monitorDeviceDetailViewModle;
    }

    private void initListener() {
        setRightOther(R.mipmap.ic_monitor_detail_list, (v) -> {
            JumpItent.jump(this, MonitorDeviceListActivity.class);
        });
        setRightClick(R.mipmap.ic_monitor_detail_setting, (v) -> {
            JumpItent.jump(this, MonitorDeviceManagerActivity.class);
        });
        // 暂停播放按钮
        monitorDeviceDetailBinding.llPlayControl.realplayPlayBtn.setOnClickListener((v) -> {
            if (mStatus != RealPlayStatus.STATUS_STOP) {
                stopRealPlay();
//                setRealPlayStopUI();
            } else {
                startRealPlay();
            }
        });
        // 暂停后 播放
        monitorDeviceDetailBinding.llInclued.realplayPlayIv.setOnClickListener((v) -> {
            if (mStatus == RealPlayStatus.STATUS_STOP) {
                startRealPlay();
            }
        });
        // 清晰度
        monitorDeviceDetailBinding.llPlayControl.realplayQualityBtn.setOnClickListener((v) -> {
            openQualityPopupWindow(monitorDeviceDetailBinding.llPlayControl.realplayQualityBtn);
        });
        // 声音
        monitorDeviceDetailBinding.llPlayControl.realplaySoundBtn.setOnClickListener((v) -> {
            if (mLocalInfo.isSoundOpen()) {
                mLocalInfo.setSoundOpen(false);
                monitorDeviceDetailBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_off_selector);
            } else {
                mLocalInfo.setSoundOpen(true);
                monitorDeviceDetailBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_selector);
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
        monitorDeviceDetailBinding.llPlayControl.realplayPlayBtn.setBackgroundResource(R.drawable.play_stop_selector);
        // 视频中的播放按钮
        monitorDeviceDetailBinding.llInclued.realplayPlayIv.setVisibility(View.GONE);
        mStatus = RealPlayStatus.STATUS_START;
        mHandler = new Handler(this);
        mRealPlaySh = monitorDeviceDetailBinding.realplaySv.getHolder();
        mRealPlaySh.addCallback(this);
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
        monitorDeviceDetailBinding.llInclued.realplayLoadingRl.setVisibility(View.INVISIBLE);
        monitorDeviceDetailBinding.llInclued.realplayLoading.setVisibility(View.GONE);
        setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        mStatus = RealPlayStatus.STATUS_PLAY;
    }

    private void stopRealPlay() {
        monitorDeviceDetailBinding.llPlayControl.realplayPlayBtn.setBackgroundResource(R.drawable.play_play_selector);
        mStatus = RealPlayStatus.STATUS_STOP;
        if (ezPlayer != null) {
            ezPlayer.stopLocalRecord();
            ezPlayer.stopRealPlay();
        }
        // 视频中的播放按钮
        monitorDeviceDetailBinding.llInclued.realplayPlayIv.setVisibility(View.VISIBLE);
        monitorDeviceDetailBinding.llInclued.realplayLoadingRl.setVisibility(View.VISIBLE);
        setForceOrientation(0);
    }

    private void initUI() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        monitorDeviceDetailBinding.llPlayControl.realplaySoundBtn.setVisibility(View.VISIBLE);
        if (mLocalInfo.isSoundOpen()) {
            monitorDeviceDetailBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_selector);
        } else {
            monitorDeviceDetailBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_off_selector);
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
                monitorDeviceDetailBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_selector);
            } else {
                ezPlayer.closeSound();
                monitorDeviceDetailBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_off_selector);
            }
        }
    }

    private void updateRealPlayUI() {
        monitorDeviceDetailBinding.llInclued.realplayLoading.setVisibility(View.INVISIBLE);
        monitorDeviceDetailBinding.llPlayControl.realplayPlayBtn.setBackgroundResource(R.drawable.play_stop_selector);
    }

    private void setRealPlaySvLayout() {
        final int screenWidth = mLocalInfo.getScreenWidth();
        final int screenHeight = (mOrientation == Configuration.ORIENTATION_PORTRAIT) ? (mLocalInfo.getScreenHeight() - mLocalInfo
                .getNavigationBarHeight()) : mLocalInfo.getScreenHeight();
        final RelativeLayout.LayoutParams realPlaySvlp = Utils.getPlayViewLp(mRealRatio, mOrientation,
                mLocalInfo.getScreenWidth(), (int) (mLocalInfo.getScreenWidth() * Constant.LIVE_VIEW_RATIO),
                screenWidth, screenHeight);
        RelativeLayout.LayoutParams svLp = new RelativeLayout.LayoutParams(realPlaySvlp.width, realPlaySvlp.height);
        monitorDeviceDetailBinding.vgPlayWindow.setLayoutParams(svLp);
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
        } else {
            WindowManager.LayoutParams lp2 = getWindow().getAttributes();
            lp2.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp2);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            view.setVisibility(View.VISIBLE);
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
            monitorDeviceDetailBinding.llPlayControl.realplayQualityBtn.setText(R.string.quality_flunet);
        } else if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_BALANCED.getVideoLevel()) {
            monitorDeviceDetailBinding.llPlayControl.realplayQualityBtn.setText(R.string.quality_balanced);
        } else if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_HD.getVideoLevel()) {
            monitorDeviceDetailBinding.llPlayControl.realplayQualityBtn.setText(R.string.quality_hd);
        } else {
            monitorDeviceDetailBinding.llPlayControl.realplayQualityBtn.setText("unknown");
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

        if (sensor == ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
            mScreenOrientationHelper.enableSensorOrientation();
        } else {
            mScreenOrientationHelper.disableSensorOrientation();
        }
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
        if (!ConnectionDetector.isNetworkAvailable(MonitorDeviceDetailActivity.this)) {
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
