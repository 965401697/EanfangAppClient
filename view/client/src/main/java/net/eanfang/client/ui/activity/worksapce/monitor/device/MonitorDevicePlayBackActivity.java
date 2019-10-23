package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.eanfang.config.Config;
import com.eanfang.sdk.selecttime.SelectCalendarDialogFragment;
import com.eanfang.util.DateKit;
import com.eanfang.util.JumpItent;
import com.videogo.constant.Constant;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.exception.InnerException;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZOpenSDKListener;
import com.videogo.openapi.EZPlayer;
import com.videogo.realplay.RealPlayStatus;
import com.videogo.util.LocalInfo;
import com.videogo.util.MediaScanner;
import com.videogo.util.RotateViewUtil;
import com.videogo.util.SDCardUtil;
import com.videogo.util.Utils;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityMonitorDevicePlayBackBinding;
import net.eanfang.client.ui.adapter.monitor.MonitorDeviceDetailTimeAdapter;
import net.eanfang.client.ui.widget.SlidingRuleView;
import net.eanfang.client.util.AudioPlayUtil;
import net.eanfang.client.util.EZUtils;
import net.eanfang.client.util.ScreenOrientationHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author guanluocang
 * @data 2019/9/29  17:18
 * @description 会放
 */

public class MonitorDevicePlayBackActivity extends BaseActivity implements Handler.Callback, SurfaceHolder.Callback, SlidingRuleView.DoGetValueListener, SelectCalendarDialogFragment.SelectCalendarTimeListener {
    private ActivityMonitorDevicePlayBackBinding monitorDevicePlayBackBinding;

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
     * 是否回放
     */
    private boolean isPlayBack = false;
    /**
     * 是否滑动分钟
     */
    private boolean isScrollMinute = false;
    private String mHour = "1000";
    public String minute;
    public Calendar mPlayTime;
    public Calendar mPlayEndTime;
    public MonitorDeviceDetailTimeAdapter monitorDeviceDetailTimeAdapter;
    private List<String> mTimeList = new ArrayList<>();

    private String mShopName;
    private String mYearMonthDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDevicePlayBackBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_play_back);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("视频回放");
        setLeftBack(true);

        mAudioPlayUtil = AudioPlayUtil.getInstance(ClientApplication.get());
        mRecordRotateViewUtil = new RotateViewUtil();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mScreenOrientationHelper = new ScreenOrientationHelper(this, monitorDevicePlayBackBinding.llPlayControl.fullscreenButton, null);
        view = findViewById(R.id.layout_include);
        mShopName = getIntent().getStringExtra("shopName");
        mDeviceSerial = getIntent().getStringExtra("deviceSerial");
        monitorDevicePlayBackBinding.viewTimeLine.setOnScrollListener(this);
        startRealPlay();
        initTimeAdapter();
        initListener();
    }

    private void initTimeAdapter() {
        monitorDeviceDetailTimeAdapter = new MonitorDeviceDetailTimeAdapter(onItemClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(monitorDevicePlayBackBinding.getRoot().getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        monitorDevicePlayBackBinding.rvTime.setLayoutManager(manager);
        monitorDeviceDetailTimeAdapter.bindToRecyclerView(monitorDevicePlayBackBinding.rvTime);
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
            monitorDevicePlayBackBinding.llInclued.realplayLoading.setVisibility(View.GONE);
            isPlayBack = true;
            stopRealPlay();
            mHour = String.valueOf(posititon);
        }
    };

    @Override
    public void doGetValue(String mValue) {
        Log.e("GG", "value" + mValue);
        if (isScrollMinute) {
            isPlayBack = true;
            stopRealPlay();
        } else {
            isScrollMinute = true;
        }
        minute = mValue;
    }

    private void initListener() {
        // 暂停后 播放 视频中间的播放按钮
        monitorDevicePlayBackBinding.realplayPlayBtn.setOnClickListener((v) -> {
            if (mHour.equals(1000)) {
                showToast("请选择小时");
                return;
            }
            if (StrUtil.isEmpty(mYearMonthDay)) {
                showToast("请选择时间");
                return;
            } else {
                if (!StrUtil.isEmpty(mHour)) {
                    if (StrUtil.isEmpty(minute)) {
                        mPlayTime = DateKit.get(DateUtil.parse(mYearMonthDay + " " + mHour + ":00:00", DatePattern.NORM_DATETIME_MINUTE_PATTERN)).getCalendar();
                        mPlayEndTime = DateKit.get(DateUtil.parse(mYearMonthDay + " " + mHour + ":10:00", DatePattern.NORM_DATETIME_MINUTE_PATTERN)).getCalendar();
                    } else {
                        mPlayTime = DateKit.get(DateUtil.parse(mYearMonthDay + " " + mHour + ":" + minute + ":00", DatePattern.NORM_DATETIME_MINUTE_PATTERN)).getCalendar();
                        mPlayEndTime = DateKit.get(DateUtil.parse(mYearMonthDay + " " + mHour + ":" + (Double.parseDouble(minute) + 10) + ":00", DatePattern.NORM_DATETIME_MINUTE_PATTERN)).getCalendar();
                    }
                } else {
                    mPlayTime = DateKit.get(DateUtil.parse(mYearMonthDay + " 00:00:00", DatePattern.NORM_DATETIME_MINUTE_PATTERN)).getCalendar();
                    mPlayEndTime = DateKit.get(DateUtil.parse(mYearMonthDay + " 00:10:00", DatePattern.NORM_DATETIME_MINUTE_PATTERN)).getCalendar();
                }
            }
            try {
                if (EZOpenSDK.getInstance().searchRecordFileFromDevice(mDeviceSerial, 1, mPlayTime, mPlayEndTime).size() <= 0) {
                    showToast("该时间段没有录像片段");
                    return;
                }
            } catch (BaseException e) {
                showToast(e.getMessage());
                e.printStackTrace();
                return;
            }
            if (mStatus == RealPlayStatus.STATUS_STOP) {
                monitorDevicePlayBackBinding.llInclued.realplayPlayIv.setVisibility(View.GONE);
                monitorDevicePlayBackBinding.realplayPlayBtn.setBackgroundResource(R.mipmap.ic_monitor_back_play_pause);
                mStatus = RealPlayStatus.STATUS_START;
                mHandler = new Handler(this);
                monitorDevicePlayBackBinding.realplaySv.setVisibility(View.VISIBLE);
                mRealPlaySh = monitorDevicePlayBackBinding.realplaySv.getHolder();
                mRealPlaySh.addCallback(this);
                ezPlayer = null;
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
                monitorDevicePlayBackBinding.llInclued.realplayLoading.setVisibility(View.VISIBLE);
                boolean isSuccess = ezPlayer.startPlayback(mPlayTime, DateKit.get(DateUtil.parseTimeToday("23:59")).getCalendar());
                isPlayBack = false;
            } else {
                stopRealPlay();
            }
        });
        // 滑动冲突
        monitorDevicePlayBackBinding.viewTimeLine.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
//                monitorDevicePlayBackBinding.nestedView.requestDisallowInterceptTouchEvent(false);
            } else {
//                monitorDevicePlayBackBinding.nestedView.requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });
        // 截图
        monitorDevicePlayBackBinding.ivScreenHot.setOnClickListener((v) -> {
            isEdit = false;
            onScreenHot();
        });
        // 录像
        monitorDevicePlayBackBinding.llVideoTape.setOnClickListener((v) -> {
            onVideoTape();
        });
        // 选择时间
        monitorDevicePlayBackBinding.llTime.setOnClickListener((v) -> {
            new SelectCalendarDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
        });
        //倍速播放
        monitorDevicePlayBackBinding.ivSpeed.setOnClickListener((V) -> {
            onClickChangePlaybackSpeed(monitorDevicePlayBackBinding.ivSpeed);
        });
        // 回退十秒
        monitorDevicePlayBackBinding.ivReturn.setOnClickListener((v) -> {
            if (ezPlayer.getOSDTime() != null) {
                ezPlayer.stopPlayback();
                ezPlayer.seekPlayback(DateKit.get(DateUtil.date(ezPlayer.getOSDTime().getTime().getTime() - 10000)).getCalendar());
            }
        });
    }

    private void startRealPlay() {
        if (mStatus == RealPlayStatus.STATUS_START || mStatus == RealPlayStatus.STATUS_PLAY) {
            return;
        }
        monitorDevicePlayBackBinding.realplayPlayBtn.setBackgroundResource(R.mipmap.ic_monitor_back_play);
        // 视频中的播放按钮
//        monitorDevicePlayBackBinding.llInclued.realplayPlayIv.setVisibility(View.VISIBLE);
        mStatus = RealPlayStatus.STATUS_START;
        mHandler = new Handler(this);
        monitorDevicePlayBackBinding.realplaySv.setVisibility(View.VISIBLE);
        mRealPlaySh = monitorDevicePlayBackBinding.realplaySv.getHolder();
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
//        ezPlayer.startRealPlay();
        monitorDevicePlayBackBinding.llInclued.realplayLoadingRl.setVisibility(View.INVISIBLE);
        monitorDevicePlayBackBinding.llInclued.realplayLoading.setVisibility(View.GONE);
        setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        mStatus = RealPlayStatus.STATUS_PLAY;
    }

    private void stopRealPlay() {
        monitorDevicePlayBackBinding.realplayPlayBtn.setBackgroundResource(R.mipmap.ic_monitor_back_play);
        mStatus = RealPlayStatus.STATUS_STOP;
        if (ezPlayer != null) {
            ezPlayer.stopLocalRecord();
            ezPlayer.stopRealPlay();
        }
        // 视频中的播放按钮
        monitorDevicePlayBackBinding.llInclued.realplayLoading.setVisibility(View.GONE);
        monitorDevicePlayBackBinding.llInclued.realplayLoadingRl.setVisibility(View.VISIBLE);
        setForceOrientation(0);
    }

    private void initUI() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        monitorDevicePlayBackBinding.llPlayControl.realplaySoundBtn.setVisibility(View.VISIBLE);
        if (mLocalInfo.isSoundOpen()) {
            monitorDevicePlayBackBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_selector);
        } else {
            monitorDevicePlayBackBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_off_selector);
        }

        setRealPlaySound();
        startRealPlay();
    }

    /**
     * 声音
     */
    private void setRealPlaySound() {
        if (ezPlayer != null) {
            if (mLocalInfo.isSoundOpen()) {
                ezPlayer.openSound();
                monitorDevicePlayBackBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_selector);
            } else {
                ezPlayer.closeSound();
                monitorDevicePlayBackBinding.llPlayControl.realplaySoundBtn.setBackgroundResource(R.drawable.ezopen_vertical_preview_sound_off_selector);
            }
        }
    }

    private void updateRealPlayUI() {
        monitorDevicePlayBackBinding.llInclued.realplayLoading.setVisibility(View.INVISIBLE);
        monitorDevicePlayBackBinding.realplayPlayBtn.setBackgroundResource(R.mipmap.ic_monitor_back_play_pause);
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
        monitorDevicePlayBackBinding.vgPlayWindow.setLayoutParams(svLp);
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
                            MediaScanner mMediaScanner = new MediaScanner(MonitorDevicePlayBackActivity.this);
                            mMediaScanner.scanFile(strCaptureFile, "jpg");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MonitorDevicePlayBackActivity.this, "已保存至相册" + strCaptureFile, Toast.LENGTH_SHORT).show();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("imagePath", strCaptureFile);
                                    bundle.putString("shopName", mShopName);
                                    if (isEdit) {
                                        JumpItent.jump(MonitorDevicePlayBackActivity.this, MonitorDeviceReportActivity.class, bundle);
                                    } else {
                                        JumpItent.jump(MonitorDevicePlayBackActivity.this, MonitorDeviceScreenHotActivity.class, bundle);
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
                                Toast.makeText(getApplicationContext(), "截图失败",
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
            final String strRecordFile = Config.Cache.VIDEO_STORAGE_DIR + System.currentTimeMillis() + ".mp4";
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
        dialog("录像完成", "保存路径：" + mCurrentRecordPath);
        // 设置录像按钮为check状态
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!mIsOnStop) {
                mRecordRotateViewUtil.applyRotation(monitorDevicePlayBackBinding.llVideoTape, monitorDevicePlayBackBinding.ivVideoTapeStop,
                        monitorDevicePlayBackBinding.ivVideoTapeStart, 0, 90);
            } else {
                monitorDevicePlayBackBinding.ivVideoTapeStop.setVisibility(View.GONE);
                monitorDevicePlayBackBinding.ivVideoTapeStart.setVisibility(View.VISIBLE);
            }
        } else {
            monitorDevicePlayBackBinding.ivVideoTapeStop.setVisibility(View.GONE);
            monitorDevicePlayBackBinding.ivVideoTapeStart.setVisibility(View.VISIBLE);
        }
        mAudioPlayUtil.playAudioFile(AudioPlayUtil.RECORD_SOUND);
        ezPlayer.stopLocalRecord();

        // 计时按钮不可见
        //The timed button is not visible
        monitorDevicePlayBackBinding.rlVideoTape.setVisibility(View.GONE);
        isRecording = false;
    }

    private void handleRecordSuccess(String recordFilePath) {

        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!mIsOnStop) {
                mRecordRotateViewUtil.applyRotation(monitorDevicePlayBackBinding.llVideoTape, monitorDevicePlayBackBinding.ivVideoTapeStart,
                        monitorDevicePlayBackBinding.ivVideoTapeStop, 0, 90);
            } else {
                monitorDevicePlayBackBinding.ivVideoTapeStart.setVisibility(View.GONE);
                monitorDevicePlayBackBinding.ivVideoTapeStop.setVisibility(View.VISIBLE);
            }
        } else {
            monitorDevicePlayBackBinding.ivVideoTapeStart.setVisibility(View.GONE);
            monitorDevicePlayBackBinding.ivVideoTapeStop.setVisibility(View.VISIBLE);
        }
        isRecording = true;
        monitorDevicePlayBackBinding.rlVideoTape.setVisibility(View.VISIBLE);
        monitorDevicePlayBackBinding.tvRealplayRecord.setText("00:00");

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
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MonitorDevicePlayBackActivity.this);
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
        monitorDevicePlayBackBinding.tvRealplayRecord.setText(recordTime);
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
    protected ViewModel initViewModel() {
        return null;
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
            case EZConstants.EZPlaybackConstants.MSG_REMOTEPLAYBACK_PLAY_SUCCUSS:
                startRealPlay();
                monitorDevicePlayBackBinding.llInclued.realplayLoadingRl.setVisibility(View.GONE);
                showToast("回放成功");
                break;
            // 回访失败
            case EZConstants.EZPlaybackConstants.MSG_REMOTEPLAYBACK_PLAY_FAIL:
                showToast("回放失败");
                Log.e("GG", "回放失败 " + msg.arg1);
                break;
            default:
                // do nothing
                break;
        }
        return false;
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

    @Override
    public void getData(String time) {
        mYearMonthDay = time;
        monitorDevicePlayBackBinding.tvTime.setText(time);
        Log.e("GG", "time " + time);
    }

    // 用于防止重复点击
    private boolean isShowChangePlaybackRateWindow = false;

    public void onClickChangePlaybackSpeed(View view) {
        if (isShowChangePlaybackRateWindow) {
            return;
        }
        PopupWindow popupWindow = new PopupWindow(MonitorDevicePlayBackActivity.this);
        ViewGroup popupVg = (ViewGroup) LayoutInflater.from(MonitorDevicePlayBackActivity.this).inflate(R.layout.layout_change_playback_rate,
                (ViewGroup) getWindow().getDecorView(), false);
        popupWindow.setContentView(popupVg);
        popupWindow.getContentView().setTag(view);
        for (int i = 0; i < popupVg.getChildCount(); i++) {
            View childView = popupVg.getChildAt(i);
            if (childView instanceof Button) {
                Button changeRateBtn = (Button) childView;
                changeRateBtn.setOnClickListener(mChangePlaybackRateListener);
                changeRateBtn.setTag(popupWindow);
                if (view instanceof Button && ((Button) view).getText().toString().contains(((Button) childView).getText())) {
                    childView.setVisibility(View.GONE);
                }
            }
        }
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isShowChangePlaybackRateWindow = false;
            }
        });
        int widthMode = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMode = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        popupWindow.getContentView().measure(widthMode, heightMode);
        int yOffset = -(view.getHeight() + popupWindow.getContentView().getMeasuredHeight());
        popupWindow.showAsDropDown(view, 0, yOffset);
        isShowChangePlaybackRateWindow = true;
    }

    private View.OnClickListener mChangePlaybackRateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PopupWindow popupWindow = null;
            if (v.getTag() != null && v.getTag() instanceof PopupWindow) {
                popupWindow = ((PopupWindow) v.getTag());
                popupWindow.dismiss();
            }
            if (v instanceof Button) {
                String targetRateWithX = (String) ((Button) v).getText();
                String rate = targetRateWithX.replaceAll("x", "");
                rate = rate.replace("X", "");
                int rateInt = Integer.parseInt(rate);
                for (EZConstants.EZPlaybackRate rateEnum : EZConstants.EZPlaybackRate.values()) {
                    if (rateInt == rateEnum.speed) {
                        if (ezPlayer.setPlaybackRate(rateEnum)) {
                            if (popupWindow != null && popupWindow.getContentView() != null && popupWindow.getContentView().getTag() instanceof Button) {
                                ((Button) popupWindow.getContentView().getTag()).setText(targetRateWithX);
                            }
                        } else {
                            showToast("修改失败" + targetRateWithX);
                        }
                    }
                }
            }
        }
    };
}
