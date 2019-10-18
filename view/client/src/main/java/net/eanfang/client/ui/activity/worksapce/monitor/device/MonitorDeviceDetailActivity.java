package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.config.Config;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JumpItent;
import com.videogo.constant.Constant;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.exception.InnerException;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZConstants.EZVideoLevel;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZOpenSDKListener;
import com.videogo.openapi.EZPlayer;
import com.videogo.realplay.RealPlayStatus;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.LocalInfo;
import com.videogo.util.MediaScanner;
import com.videogo.util.RotateViewUtil;
import com.videogo.util.SDCardUtil;
import com.videogo.util.Utils;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityMonitorDeviceDetailBinding;
import net.eanfang.client.ui.adapter.monitor.MonitorDeviceDetailTimeAdapter;
import net.eanfang.client.ui.widget.SlidingRuleView;
import net.eanfang.client.util.AudioPlayUtil;
import net.eanfang.client.util.EZUtils;
import net.eanfang.client.util.ScreenOrientationHelper;
import net.eanfang.client.viewmodel.device.MonitorDeviceDetailViewModle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import static com.videogo.openapi.EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_FAIL;
import static com.videogo.openapi.EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_SUCCESS;

/**
 * @author guanluocang
 * @data 2019/9/11  16:27
 * @description 摄像机详情页
 */

public class MonitorDeviceDetailActivity extends BaseActivity implements Handler.Callback, SurfaceHolder.Callback, SlidingRuleView.DoGetValueListener {
    public static final String LEAVE_MODLE = "LEAVE_MODLE";
    private static final int REQUEST_UPDATE_NAME = 1001;
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
    private AudioPlayUtil mAudioPlayUtil = null;
    private PopupWindow mQualityPopupWindow = null;
    private String mCurrentRecordPath = null;
    private boolean isRecording = false;
    private RotateViewUtil mRecordRotateViewUtil = null;
    private boolean mIsOnStop = false;
    private int mRecordSecond = 0;

    private Timer mUpdateTimer = null;
    private TimerTask mUpdateTimerTask = null;
    private String mRecordTime = null;
    private View view;
    private boolean isEdit = false;
    /**
     * 设备序列号
     */
    private String mDeviceSerial;

    /**
     * 设备position
     */
    private int mDevicePosition = 1000;
    private List<Ys7DevicesEntity> mDeviceList = new ArrayList<>();

    /**
     * 是否回放
     */
    private boolean isPlayBack = false;
    private int mHour;
    public String minute;
    public Calendar mPlayTime;
    public Calendar mPlayEndTime;

    public MonitorDeviceDetailTimeAdapter monitorDeviceDetailTimeAdapter;
    private List<String> mTimeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDeviceDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        mAudioPlayUtil = AudioPlayUtil.getInstance(ClientApplication.get());
        mRecordRotateViewUtil = new RotateViewUtil();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mScreenOrientationHelper = new ScreenOrientationHelper(this, monitorDeviceDetailBinding.llPlayControl.fullscreenButton, null);
        view = findViewById(R.id.layout_include);
        mDevicePosition = getIntent().getIntExtra("position", 1000);
        mDeviceList = (List<Ys7DevicesEntity>) getIntent().getSerializableExtra("deviceList");
        mDeviceSerial = getIntent().getStringExtra("deviceSerial");
        mDeviceName = getIntent().getStringExtra("mDeviceName");
        init(mDeviceName, getIntent().getLongExtra("mDeviceId", 0));
        monitorDeviceDetailBinding.viewTimeLine.setOnScrollListener(this);
        initTimeAdapter();
        initListener();
    }


    private void init(String mDeviceName, Long mDeviceId) {
        monitorDeviceDetailViewModle.mDeviceId = mDeviceId;
        monitorDeviceDetailViewModle.init();
        setTitle(mDeviceName);
        startRealPlay();
    }

    @Override
    protected ViewModel initViewModel() {
        monitorDeviceDetailViewModle = LViewModelProviders.of(this, MonitorDeviceDetailViewModle.class);
        monitorDeviceDetailViewModle.setMonitorDeviceDetailBinding(monitorDeviceDetailBinding);
        return monitorDeviceDetailViewModle;
    }

    private void initTimeAdapter() {
        monitorDeviceDetailTimeAdapter = new MonitorDeviceDetailTimeAdapter(onItemClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(monitorDeviceDetailBinding.getRoot().getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        monitorDeviceDetailBinding.rvTime.setLayoutManager(manager);
        monitorDeviceDetailTimeAdapter.bindToRecyclerView(monitorDeviceDetailBinding.rvTime);
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                mTimeList.add("0" + i + "时");
            } else {
                mTimeList.add(i + "时");
            }
        }
        monitorDeviceDetailTimeAdapter.setNewData(mTimeList);
    }

    MonitorDeviceDetailTimeAdapter.OnItemClickListener onItemClickListener = new MonitorDeviceDetailTimeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int posititon, String mTime) {
            monitorDeviceDetailBinding.llInclued.realplayLoading.setVisibility(View.GONE);
            isPlayBack = true;
            stopRealPlay();
            mHour = posititon;
        }
    };

    @Override
    public void doGetValue(String mValue) {
        Log.e("GG", "value" + mValue);
        minute = mValue;
    }

    private void initListener() {
        setRightOther(R.mipmap.ic_monitor_detail_list, (v) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("mLeftGroupId", getIntent().getLongExtra("mLeftGroupId", 0));
            bundle.putString("mChangeCompanyId", getIntent().getStringExtra("mChangeCompanyId"));
            JumpItent.jump(this, MonitorDeviceListActivity.class, bundle);
        });
        setRightClick(R.mipmap.ic_monitor_detail_setting, (v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("imagePath", monitorDeviceDetailViewModle.mImagePath);
            bundle.putString("deviceName", monitorDeviceDetailViewModle.mDeviceName);
            bundle.putString("deviceGroupName", monitorDeviceDetailViewModle.mDeviceGroupName);
            bundle.putString("deviceSerial", monitorDeviceDetailViewModle.mDeviceSerial);
            bundle.putString("deviceId", String.valueOf(monitorDeviceDetailViewModle.mDeviceId));
            bundle.putString("groupId", String.valueOf(monitorDeviceDetailViewModle.mGroupId));
            bundle.putString("companyId", String.valueOf(monitorDeviceDetailViewModle.mCompanyId));
            JumpItent.jump(this, MonitorDeviceManagerActivity.class, bundle, REQUEST_UPDATE_NAME);
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
                if (isPlayBack) {
                    if (StrUtil.isEmpty(minute)) {
                        mPlayTime = DateUtil.parseTimeToday(mHour + ":00").toCalendar();
                        mPlayEndTime = DateUtil.parse(DateUtil.today() + " " + mHour + ":10:00", DatePattern.NORM_DATETIME_MINUTE_PATTERN).toCalendar();
                    } else {
                        mPlayTime = DateUtil.parseTimeToday(mHour + ":" + minute).toCalendar();
                        mPlayEndTime = DateUtil.parse(DateUtil.today() + " " + mHour + ":" + (Double.parseDouble(minute) + 10), DatePattern.NORM_DATETIME_MINUTE_PATTERN).toCalendar();
                    }
                    try {
                        if (EZOpenSDK.getInstance().searchRecordFileFromDevice(mDeviceSerial, 1, mPlayTime, mPlayEndTime).size() <= 0) {
                            showToast("该时间段没有录像片段");
                            return;
                        }
                    } catch (BaseException e) {
                        showToast("该时间段没有录像片段");
                        e.printStackTrace();
                        return;
                    }
                    monitorDeviceDetailBinding.llInclued.realplayPlayIv.setVisibility(View.GONE);
                    monitorDeviceDetailBinding.llPlayControl.realplayPlayBtn.setBackgroundResource(R.drawable.play_stop_selector);
                    mStatus = RealPlayStatus.STATUS_START;
                    mHandler = new Handler(this);
                    monitorDeviceDetailBinding.realplaySv.setVisibility(View.VISIBLE);
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
                    setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                    monitorDeviceDetailBinding.llInclued.realplayLoading.setVisibility(View.VISIBLE);
                    boolean isSuccess = ezPlayer.startPlayback(mPlayTime, DateUtil.parseTimeToday("23:59").toCalendar());
                    Log.e("GG", "playback" + isSuccess);
                    isPlayBack = false;
                } else {
                    startRealPlay();
                }

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
        // 滑动冲突
        monitorDeviceDetailBinding.viewTimeLine.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                monitorDeviceDetailBinding.nestedView.requestDisallowInterceptTouchEvent(false);
            } else {
                monitorDeviceDetailBinding.nestedView.requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });
        // 截图
        monitorDeviceDetailBinding.tvScreenHot.setOnClickListener((v) -> {
            isEdit = false;
            onScreenHot();
        });
        // 录像
        monitorDeviceDetailBinding.llVideoTape.setOnClickListener((v) -> {
            onVideoTape();
        });
        //回放
        monitorDeviceDetailBinding.tvPlayBack.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("shopName", monitorDeviceDetailViewModle.mShopName);
            bundle.putString("deviceSerial", mDeviceSerial);
            JumpItent.jump(MonitorDeviceDetailActivity.this, MonitorDevicePlayBackActivity.class, bundle);
        });
        // 编辑
        monitorDeviceDetailBinding.tvEdit.setOnClickListener((v) -> {
            isEdit = true;
            onScreenHot();
        });

        // 联系
        monitorDeviceDetailBinding.tvContact.setOnClickListener((v) -> {
            CallUtils.call(MonitorDeviceDetailActivity.this, monitorDeviceDetailViewModle.mobile);
        });

        //上一个
        monitorDeviceDetailBinding.rlSelectLeft.setOnClickListener((v) -> {
            if (mDevicePosition == 0) {
                showToast("已是第一个");
            } else {
                monitorDeviceDetailBinding.realplaySv.setVisibility(View.GONE);
                --mDevicePosition;
                onPlayerStop();
                mDeviceSerial = mDeviceList.get(mDevicePosition).getYs7DeviceSerial();
                init(mDeviceList.get(mDevicePosition).getDeviceName(), mDeviceList.get(mDevicePosition).getDeviceId());
            }
        });
        // 下一个
        monitorDeviceDetailBinding.rlSelectRight.setOnClickListener((v) -> {
            ++mDevicePosition;
            if (mDevicePosition <= mDeviceList.size() - 1) {
                monitorDeviceDetailBinding.realplaySv.setVisibility(View.GONE);
                onPlayerStop();
                mDeviceSerial = mDeviceList.get(mDevicePosition).getYs7DeviceSerial();
                init(mDeviceList.get(mDevicePosition).getDeviceName(), mDeviceList.get(mDevicePosition).getDeviceId());
            } else {
                showToast("已是最后一个");
                --mDevicePosition;
            }

        });
        //云存储
        monitorDeviceDetailBinding.tvSeviceClound.setOnClickListener((V) -> {
            Bundle bundle = new Bundle();
            bundle.putString("type", "clound");
            JumpItent.jump(this, MonitorDetailServiceActivity.class, bundle);
        });
        //人脸识别
        monitorDeviceDetailBinding.tvSeviceFace.setOnClickListener((V) -> {
            Bundle bundle = new Bundle();
            bundle.putString("type", "face");
            JumpItent.jump(this, MonitorDetailServiceActivity.class, bundle);
        });
        //脱岗检测
        monitorDeviceDetailBinding.tvSeviceLeave.setOnClickListener((V) -> {
            Bundle bundle = new Bundle();
            bundle.putString("type", "leave");
            JumpItent.jump(this, MonitorDetailServiceActivity.class, bundle);
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
        monitorDeviceDetailBinding.realplaySv.setVisibility(View.VISIBLE);
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
        if (isRecording) {
            updateRecordTime();
        }
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
            monitorDeviceDetailBinding.rlTimeLine.setVisibility(View.GONE);
            monitorDeviceDetailBinding.viewTimeLine.setVisibility(View.GONE);
            monitorDeviceDetailBinding.llControl.setVisibility(View.GONE);
            monitorDeviceDetailBinding.llService.setVisibility(View.GONE);
            monitorDeviceDetailBinding.llPlayControl.realplayControlRl.setVisibility(View.GONE);
        } else {
            WindowManager.LayoutParams lp2 = getWindow().getAttributes();
            lp2.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp2);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            view.setVisibility(View.VISIBLE);
            monitorDeviceDetailBinding.rlTimeLine.setVisibility(View.VISIBLE);
            monitorDeviceDetailBinding.viewTimeLine.setVisibility(View.VISIBLE);
            monitorDeviceDetailBinding.llControl.setVisibility(View.VISIBLE);
            monitorDeviceDetailBinding.llService.setVisibility(View.VISIBLE);
            monitorDeviceDetailBinding.llPlayControl.realplayControlRl.setVisibility(View.VISIBLE);
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
            case EZConstants.EZPlaybackConstants.MSG_REMOTEPLAYBACK_PLAY_SUCCUSS:
                startRealPlay();
                monitorDeviceDetailBinding.llInclued.realplayLoadingRl.setVisibility(View.GONE);
                showToast("回访成功");
                break;
            // 回访失败
            case EZConstants.EZPlaybackConstants.MSG_REMOTEPLAYBACK_PLAY_FAIL:
                showToast("回访失败");
                Log.e("GG", "回访失败 " + msg.arg1);
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
        if (ezPlayer != null) {
            initUI();
        }
    }

    private void onPlayerStop() {
        if (mScreenOrientationHelper != null) {
            mScreenOrientationHelper.postOnStop();
        }
        if (ezPlayer != null) {
            mIsOnStop = true;
            mStatus = RealPlayStatus.STATUS_STOP;
            ezPlayer.stopLocalRecord();
            ezPlayer.stopPlayback();
            ezPlayer.release();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        onPlayerStop();
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

    /**
     * 截图
     */
    private void onScreenHot() {
        if (!SDCardUtil.isSDCardUseable()) {
            // 提示SD卡不可用
            showToast("存储卡不可用");
            return;
        }

        if (SDCardUtil.getSDCardRemainSize() < SDCardUtil.PIC_MIN_MEM_SPACE) {
            // 提示内存不足
            showToast("录像中断,存储空间已满");
            return;
        }

        if (ezPlayer != null) {
            Thread thr = new Thread() {
                @Override
                public void run() {
                    Bitmap bmp = ezPlayer.capturePicture();
                    if (bmp != null) {
                        try {
                            mAudioPlayUtil.playAudioFile(AudioPlayUtil.CAPTURE_SOUND);
                            final String strCaptureFile = Config.Cache.IMG_STORAGE_DIR + System.currentTimeMillis() + ".jpg";
                            Log.e("GG", "captured picture file path is " + strCaptureFile);

                            if (TextUtils.isEmpty(strCaptureFile)) {
                                bmp.recycle();
                                bmp = null;
                                return;
                            }
                            EZUtils.saveCapturePictrue(strCaptureFile, bmp);
                            MediaScanner mMediaScanner = new MediaScanner(MonitorDeviceDetailActivity.this);
                            mMediaScanner.scanFile(strCaptureFile, "jpg");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MonitorDeviceDetailActivity.this, "已保存至相册" + strCaptureFile, Toast.LENGTH_SHORT).show();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("imagePath", strCaptureFile);
                                    bundle.putString("shopName", monitorDeviceDetailViewModle.mShopName);
                                    if (isEdit) {
                                        JumpItent.jump(MonitorDeviceDetailActivity.this, MonitorDeviceReportActivity.class, bundle);
                                    } else {
                                        JumpItent.jump(MonitorDeviceDetailActivity.this, MonitorDeviceScreenHotActivity.class, bundle);
                                    }
                                }
                            });
                        } catch (InnerException e) {
                            e.printStackTrace();
                        } finally {
                            if (bmp != null) {
                                bmp.recycle();
                                bmp = null;
                                return;
                            }
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "抓图失败, 检查是否开启了硬件解码",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    super.run();
                }
            };
            thr.start();
        }
    }


    /**
     * 录像
     */
    private void onVideoTape() {
        if (isRecording) {
            stopRealPlayRecord();
            return;
        }

        if (!SDCardUtil.isSDCardUseable()) {
            // 提示SD卡不可用
            showToast("存储卡不可用");
            return;
        }

        if (SDCardUtil.getSDCardRemainSize() < SDCardUtil.PIC_MIN_MEM_SPACE) {
            // 提示内存不足
            showToast("录像中断,存储空间已满");
            return;
        }

        if (ezPlayer != null) {
            startUpdateTimer();
            final String strRecordFile = Config.Cache.VIDEO_STORAGE_DIR + "/" + System.currentTimeMillis() + ".mp4";
            Log.e("GG", "recorded video file path is " + strRecordFile);
            ezPlayer.setStreamDownloadCallback(new EZOpenSDKListener.EZStreamDownloadCallback() {
                @Override
                public void onSuccess(String filepath) {
                    Log.e("GG", "EZStreamDownloadCallback onSuccess " + filepath);
                }

                @Override
                public void onError(EZOpenSDKListener.EZStreamDownloadError code) {

                }
            });
            if (ezPlayer.startLocalRecordWithFile(strRecordFile)) {
                isRecording = true;
                mCurrentRecordPath = strRecordFile;
                mAudioPlayUtil.playAudioFile(AudioPlayUtil.RECORD_SOUND);
                handleRecordSuccess(strRecordFile);
            } else {
                handleRecordFail();
            }
        }
    }

    private void handleRecordFail() {
        showToast("录像失败");
        if (isRecording) {
            stopRealPlayRecord();
        }
    }

    private void stopRealPlayRecord() {
        if (ezPlayer == null || !isRecording) {
            return;
        }
        dialog("Record result", "saved to " + mCurrentRecordPath);
        // 设置录像按钮为check状态
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!mIsOnStop) {
                mRecordRotateViewUtil.applyRotation(monitorDeviceDetailBinding.llVideoTape, monitorDeviceDetailBinding.ivVideoTapeStop,
                        monitorDeviceDetailBinding.ivVideoTapeStart, 0, 90);
            } else {
                monitorDeviceDetailBinding.ivVideoTapeStop.setVisibility(View.GONE);
                monitorDeviceDetailBinding.ivVideoTapeStart.setVisibility(View.VISIBLE);
            }
        } else {
            monitorDeviceDetailBinding.ivVideoTapeStop.setVisibility(View.GONE);
            monitorDeviceDetailBinding.ivVideoTapeStart.setVisibility(View.VISIBLE);
        }
        mAudioPlayUtil.playAudioFile(AudioPlayUtil.RECORD_SOUND);
        ezPlayer.stopLocalRecord();

        // 计时按钮不可见
        //The timed button is not visible
        monitorDeviceDetailBinding.rlVideoTape.setVisibility(View.GONE);
        isRecording = false;
    }

    private void handleRecordSuccess(String recordFilePath) {

        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!mIsOnStop) {
                mRecordRotateViewUtil.applyRotation(monitorDeviceDetailBinding.llVideoTape, monitorDeviceDetailBinding.ivVideoTapeStart,
                        monitorDeviceDetailBinding.ivVideoTapeStop, 0, 90);
            } else {
                monitorDeviceDetailBinding.ivVideoTapeStart.setVisibility(View.GONE);
                monitorDeviceDetailBinding.ivVideoTapeStop.setVisibility(View.VISIBLE);
            }
        } else {
            monitorDeviceDetailBinding.ivVideoTapeStart.setVisibility(View.GONE);
            monitorDeviceDetailBinding.ivVideoTapeStop.setVisibility(View.VISIBLE);
        }
        isRecording = true;
        monitorDeviceDetailBinding.rlVideoTape.setVisibility(View.VISIBLE);
        monitorDeviceDetailBinding.tvRealplayRecord.setText("00:00");

        mRecordSecond = 0;
    }

    private AlertDialog mLastDialog = null;

    protected void dialog(final String title, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLastDialog != null && mLastDialog.isShowing()) {
                    mLastDialog.dismiss();
                }
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MonitorDeviceDetailActivity.this);
                dialogBuilder.setTitle(title);
                ViewGroup msgLayoutVg = (ViewGroup) LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_dialog_tip, null);
                TextView msgTv = (TextView) msgLayoutVg.findViewById(R.id.tv_tip);
                if (msgTv != null) {
                    msgTv.setText(msg);
                }
                dialogBuilder.setView(msgLayoutVg);
                dialogBuilder.setPositiveButton(getApplicationContext().getString(R.string.btn_ensure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.setCancelable(false);
                mLastDialog = dialogBuilder.show();
            }
        });
    }

    private void updateRecordTime() {

        int leftSecond = mRecordSecond % 3600;
        int minitue = leftSecond / 60;
        int second = leftSecond % 60;

        String recordTime = String.format("%02d:%02d", minitue, second);
        monitorDeviceDetailBinding.tvRealplayRecord.setText(recordTime);
    }

    private void startUpdateTimer() {
        stopUpdateTimer();
        mUpdateTimer = new Timer();
        mUpdateTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (ezPlayer != null && isRecording) {
                    Calendar OSDTime = ezPlayer.getOSDTime();
                    if (OSDTime != null) {
                        String playtime = Utils.OSD2Time(OSDTime);
                        if (!TextUtils.equals(playtime, mRecordTime)) {
                            mRecordSecond++;
                            mRecordTime = playtime;
                        }
                    }
                }
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(MSG_PLAY_UI_UPDATE);
                }
            }
        };
        mUpdateTimer.schedule(mUpdateTimerTask, 0, 1000);
    }

    private void stopUpdateTimer() {
        mHandler.removeMessages(MSG_PLAY_UI_UPDATE);
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }

        if (mUpdateTimerTask != null) {
            mUpdateTimerTask.cancel();
            mUpdateTimerTask = null;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_UPDATE_NAME:
                mDeviceName = data.getStringExtra("deviceName");
                setTitle(mDeviceName);
                break;
            default:
                break;
        }
    }

}
