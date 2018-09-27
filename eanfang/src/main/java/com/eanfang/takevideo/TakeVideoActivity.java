package com.eanfang.takevideo;

import android.content.DialogInterface;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.config.Config;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.ObjectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.witget.takavideo.CustomProgressDialog;
import com.eanfang.witget.takavideo.FocusIndicator;
import com.eanfang.witget.takavideo.SectionProgressBar;
import com.eanfang.witget.takavideo.SquareGLSurfaceView;
import com.eanfang.witget.takavideo.ToastUtils;
import com.qiniu.pili.droid.shortvideo.PLAudioEncodeSetting;
import com.qiniu.pili.droid.shortvideo.PLCameraSetting;
import com.qiniu.pili.droid.shortvideo.PLFaceBeautySetting;
import com.qiniu.pili.droid.shortvideo.PLFocusListener;
import com.qiniu.pili.droid.shortvideo.PLMicrophoneSetting;
import com.qiniu.pili.droid.shortvideo.PLRecordSetting;
import com.qiniu.pili.droid.shortvideo.PLRecordStateListener;
import com.qiniu.pili.droid.shortvideo.PLShortVideoRecorder;
import com.qiniu.pili.droid.shortvideo.PLVideoEncodeSetting;
import com.qiniu.pili.droid.shortvideo.PLVideoSaveListener;

import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2018/9/20
 * @description 拍摄小视频
 */

public class TakeVideoActivity extends BaseActivity implements PLRecordStateListener,
        PLVideoSaveListener, PLFocusListener {

    private static final String TAG = "TakeVideoActivity";
    public static final long DEFAULT_MIN_RECORD_DURATION = 3 * 1000;
    /**
     * 画布
     */
    @BindView(R2.id.surfaceview)
    SquareGLSurfaceView surfaceview;
    /**
     * 删除片段
     */
    @BindView(R2.id.iv_delete)
    ImageView ivDelete;
    /**
     * 保存拍摄
     */
    @BindView(R2.id.iv_confim)
    ImageView ivConfim;
    /**
     * 开始拍摄
     */
    @BindView(R2.id.iv_begin)
    View ivBegin;
    @BindView(R2.id.focus_indicator)
    FocusIndicator mFocusIndicator;
    /**
     * // close / open 闪光灯
     */
    @BindView(R2.id.iv_switch_flash)
    ImageView ivSwitchFlash;
    private boolean mFlashEnabled;
    /**
     * 录制百分之多少
     */
    @BindView(R2.id.tv_recording_percentage)
    TextView tvRecordingPercentage;

    private long mLastRecordingPercentageViewUpdateTime = 0;


    /**
     * 美颜
     */
    private PLFaceBeautySetting mFaceBeautySetting;
    /**
     * 拍摄完毕diaolog
     */
    private CustomProgressDialog mProcessingDialog;
    /**
     * 拍摄进度条
     */
    @BindView(R2.id.record_progressbar)
    SectionProgressBar recordProgressbar;

    private PLShortVideoRecorder mShortVideoRecorder;
    //录制选项
    private PLRecordSetting recordSetting;
    // 视频编码选项
    private PLVideoEncodeSetting videoEncodeSetting;
    //摄像头采集选项
    private PLCameraSetting cameraSetting;
    //麦克风采集选项
    private PLMicrophoneSetting microphoneSetting;
    //音频编码选项
    private PLAudioEncodeSetting audioEncodeSetting;

    /**
     * 是否开始录制
     */
    private boolean mSectionBegan;

    /**
     * 默认拍摄速度
     */
    private double mRecordSpeed = 1;

    private Stack<Long> mDurationRecordStack = new Stack();
    private Stack<Double> mDurationVideoStack = new Stack();

    private GestureDetector mGestureDetector;

    private int mFocusIndicatorX;
    private int mFocusIndicatorY;

    private OrientationEventListener mOrientationListener;

    // 拍摄后的命名
    private String mVideoPahth = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_video);
        ButterKnife.bind(this);
        initView();
        initListener();
    }


    private void initView() {
        setLeftBack();
        setTitle("视频录制");
        mShortVideoRecorder = new PLShortVideoRecorder();
        mShortVideoRecorder.setRecordStateListener(this);
        mShortVideoRecorder.setFocusListener(this);

        mVideoPahth = getIntent().getStringExtra("videoPath");
        if (StringUtils.isEmpty(mVideoPahth)) {
            showToast("视频拍摄路径为空");
            finishSelf();
            return;
        }

        /**
         * 摄像头采集选项
         * */
        cameraSetting = new PLCameraSetting();
        // 后摄像头
        cameraSetting.setCameraId(PLCameraSetting.CAMERA_FACING_ID.CAMERA_FACING_BACK);
        // 预览比例
        cameraSetting.setCameraPreviewSizeRatio(PLCameraSetting.CAMERA_PREVIEW_SIZE_RATIO.RATIO_4_3);
        // 预览尺寸
        cameraSetting.setCameraPreviewSizeLevel(PLCameraSetting.CAMERA_PREVIEW_SIZE_LEVEL.PREVIEW_SIZE_LEVEL_720P);

        /**
         * 麦克风采集选项
         * */
        microphoneSetting = new PLMicrophoneSetting();
        // 设置声道  单声道：CHANNEL_IN_MONO 立体声：CHANNEL_IN_STEREO
        microphoneSetting.setChannelConfig(AudioFormat.CHANNEL_IN_MONO);
        // 视频编码选项
        videoEncodeSetting = new PLVideoEncodeSetting(TakeVideoActivity.this);
        // 编码尺寸
        videoEncodeSetting.setEncodingSizeLevel(PLVideoEncodeSetting.VIDEO_ENCODING_SIZE_LEVEL.VIDEO_ENCODING_SIZE_LEVEL_480P_1); // 480x480
        // 编码码率
        videoEncodeSetting.setEncodingBitrate(1000 * 1024); // 1000kbps
        videoEncodeSetting.setEncodingFps(25);
        videoEncodeSetting.setConstFrameRateEnabled(true);
        videoEncodeSetting.setHWCodecEnabled(true); // true:硬编 false:软编
        /**
         *  音频编码选项
         * */
        audioEncodeSetting = new PLAudioEncodeSetting();
        audioEncodeSetting.setHWCodecEnabled(true); // true:硬编 false:软编
        // 设置声道  单声道：1 双声道：1
        audioEncodeSetting.setChannels(1);
        /**
         * 录制选项
         * */
        recordSetting = new PLRecordSetting();
        recordSetting.setMaxRecordDuration(10 * 1000); // 10s
        recordSetting.setRecordSpeedVariable(true);
        /**
         * 拍摄视频存储目录
         * */
        recordSetting.setVideoCacheDir(Config.VIDEO_STORAGE_DIR);
        /**
         * 拍摄视频存储根目录
         * */
        recordSetting.setVideoFilepath(Config.VIDEO_STORAGE_DIR + mVideoPahth + ".mp4");

        mFaceBeautySetting = new PLFaceBeautySetting(1.0f, 0.5f, 0.5f);

        mShortVideoRecorder.prepare(surfaceview, cameraSetting, microphoneSetting,
                videoEncodeSetting, audioEncodeSetting, null, recordSetting);

        //正常拍摄速度：正常  极慢0.25, 慢0.5, 正常1, 快2, 极快4
        mShortVideoRecorder.setRecordSpeed(1);

        recordProgressbar.setFirstPointTime(DEFAULT_MIN_RECORD_DURATION);
        recordProgressbar.setProceedingSpeed(1);
        recordProgressbar.setTotalTime(this, recordSetting.getMaxRecordDuration());

        onSectionCountChanged(0, 0);
        /**
         * 拍摄完毕dialog
         * */
        mProcessingDialog = new CustomProgressDialog(this);
        mProcessingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShortVideoRecorder.cancelConcat();
            }
        });


    }


    private void initListener() {
        /**
         * 开始录制
         * */
        ivBegin.setOnTouchListener(new View.OnTouchListener() {
            private long mSectionBeginTSMs;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    if (!mSectionBegan && mShortVideoRecorder.beginSection()) {
                        mSectionBegan = true;
                        mSectionBeginTSMs = System.currentTimeMillis();
                        recordProgressbar.setCurrentState(SectionProgressBar.State.START);
                        updateRecordingBtns(true);
                    } else {
                        showToast("无法开始视频段录制");
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    if (mSectionBegan) {
                        long sectionRecordDurationMs = System.currentTimeMillis() - mSectionBeginTSMs;
                        long totalRecordDurationMs = sectionRecordDurationMs + (mDurationRecordStack.isEmpty() ? 0 : mDurationRecordStack.peek().longValue());
                        double sectionVideoDurationMs = sectionRecordDurationMs / mRecordSpeed;
                        double totalVideoDurationMs = sectionVideoDurationMs + (mDurationVideoStack.isEmpty() ? 0 : mDurationVideoStack.peek().doubleValue());
                        mDurationRecordStack.push(new Long(totalRecordDurationMs));
                        mDurationVideoStack.push(new Double(totalVideoDurationMs));
                        if (recordSetting.IsRecordSpeedVariable()) {
                            Log.d(TAG, "SectionRecordDuration: " + sectionRecordDurationMs + "; sectionVideoDuration: " + sectionVideoDurationMs + "; totalVideoDurationMs: " + totalVideoDurationMs + "Section count: " + mDurationVideoStack.size());
                            recordProgressbar.addBreakPointTime((long) totalVideoDurationMs);
                        } else {
                            recordProgressbar.addBreakPointTime(totalRecordDurationMs);
                        }

                        recordProgressbar.setCurrentState(SectionProgressBar.State.PAUSE);
                        mShortVideoRecorder.endSection();
                        mSectionBegan = false;
                    }
                }

                return false;
            }
        });

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                mFocusIndicatorX = (int) e.getX() - mFocusIndicator.getWidth() / 2;
                mFocusIndicatorY = (int) e.getY() - mFocusIndicator.getHeight() / 2;
                mShortVideoRecorder.manualFocus(mFocusIndicator.getWidth(), mFocusIndicator.getHeight(), (int) e.getX(), (int) e.getY());
                return false;
            }
        });

        surfaceview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
        mOrientationListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                int rotation = ObjectUtil.getScreenRotation(TakeVideoActivity.this, orientation);
                if (!recordProgressbar.isRecorded() && !mSectionBegan) {
                    videoEncodeSetting.setRotationInMetadata(rotation);
                }
            }
        };
        if (mOrientationListener.canDetectOrientation()) {
            mOrientationListener.enable();
        }

    }

    /**
     * 删除已经拍摄片段
     */
    public void onClickDelete(View v) {
        if (!mShortVideoRecorder.deleteLastSection()) {
            showToast("回删视频段失败");
        }
    }

    /**
     * 确认拍摄完毕
     */
    public void onClickConcat(View v) {
        mProcessingDialog.show();
        showChooseDialog();
    }

    /**
     * 拍摄进度条
     */
    private void onSectionCountChanged(final int count, final long totalTime) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ivDelete.setEnabled(count > 0);
                ivConfim.setEnabled(totalTime >= (DEFAULT_MIN_RECORD_DURATION));
            }
        });
    }

    /**
     * 修改拍摄按钮状态
     */
    private void updateRecordingBtns(boolean isRecording) {
        ivBegin.setActivated(isRecording);
    }

    /**
     * 拍摄完毕 确认dioalog
     */
    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否拍摄完毕");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mShortVideoRecorder.concatSections(TakeVideoActivity.this);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mProcessingDialog.dismiss();
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    /**
     * 闪光灯按钮的状态背景切换
     */
    public void onClickSwitchFlash(View v) {
        mFlashEnabled = !mFlashEnabled;
        mShortVideoRecorder.setFlashEnabled(mFlashEnabled);
        ivSwitchFlash.setActivated(mFlashEnabled);
    }

    private void updateRecordingPercentageView(long currentDuration) {
        final int per = (int) (100 * currentDuration / recordSetting.getMaxRecordDuration());
        final long curTime = System.currentTimeMillis();
        if ((mLastRecordingPercentageViewUpdateTime != 0) && (curTime - mLastRecordingPercentageViewUpdateTime < 100)) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvRecordingPercentage.setText((per > 100 ? 100 : per) + "%");
                mLastRecordingPercentageViewUpdateTime = curTime;
            }
        });
    }

    //-----------------------------相关复写方法---------------------------------

    /**
     * 1、PLRecordStateListener
     * 记录状态该接口用于回调录制相关的事件
     */

    /**
     * 当准备完毕可以进行录制时触发
     */
    @Override
    public void onReady() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 是否开启闪光灯
                mFlashEnabled = false;
                ivSwitchFlash.setActivated(mFlashEnabled);
                ivBegin.setEnabled(true);
                showToast("可以进行拍摄");
            }
        });
    }

    @Override
    public void onError(int code) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.toastErrorCode(TakeVideoActivity.this, code);
            }
        });
    }

    /**
     * 当录制的片段过短时触发
     */
    @Override
    public void onDurationTooShort() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast("该视频段太短了");
            }
        });
    }

    /**
     * 录制开始
     */
    @Override
    public void onRecordStarted() {
        Log.i(TAG, "record start time: " + System.currentTimeMillis());
    }

    /**
     * 实时返回录制视频时长
     */
    @Override
    public void onSectionRecording(long sectionDurationMs, long videoDurationMs, int sectionCount) {
        Log.d(TAG, "sectionDurationMs: " + sectionDurationMs + "; videoDurationMs: " + videoDurationMs + "; sectionCount: " + sectionCount);
        // 底部提示 录制百分之多少
        updateRecordingPercentageView(videoDurationMs);
    }

    /**
     * 录制结束
     */
    @Override
    public void onRecordStopped() {
        Log.i(TAG, "record stop time: " + System.currentTimeMillis());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateRecordingBtns(false);
            }
        });
    }

    /**
     * 当有新片段录制完成时触发
     *
     * @param incDuration   增加的时长
     * @param totalDuration 总时长
     * @param sectionCount  当前的片段总数
     */
    @Override
    public void onSectionIncreased(long incDuration, long totalDuration, int sectionCount) {
        double videoSectionDuration = mDurationVideoStack.isEmpty() ? 0 : mDurationVideoStack.peek().doubleValue();
        if ((videoSectionDuration + incDuration / mRecordSpeed) >= recordSetting.getMaxRecordDuration()) {
            videoSectionDuration = recordSetting.getMaxRecordDuration();
        }
        Log.d(TAG, "videoSectionDuration: " + videoSectionDuration + "; incDuration: " + incDuration);
        onSectionCountChanged(sectionCount, (long) videoSectionDuration);
    }

    /**
     * 当有片段被删除时触发
     *
     * @param decDuration   减少的时长
     * @param totalDuration 总时长
     * @param sectionCount  当前的片段总数
     */
    @Override
    public void onSectionDecreased(long decDuration, long totalDuration, int sectionCount) {
        recordProgressbar.removeLastBreakPoint();
        if (!mDurationVideoStack.isEmpty()) {
            mDurationVideoStack.pop();
        }
        if (!mDurationRecordStack.isEmpty()) {
            mDurationRecordStack.pop();
        }
        double currentDuration = mDurationVideoStack.isEmpty() ? 0 : mDurationVideoStack.peek().doubleValue();
        onSectionCountChanged(sectionCount, (long) currentDuration);
        updateRecordingPercentageView((long) currentDuration);
    }

    /**
     * 当录制全部完成时触发
     */
    @Override
    public void onRecordCompleted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast("已达到拍摄总时长");
            }
        });
    }


    /**
     * 2、PLVideoSaveListener
     * 该接口用于回调视频保存相关的事件
     */

    /**
     * 保存成功时触发
     */
    @Override
    public void onSaveVideoSuccess(String filePath) {
        Log.i(TAG, "concat sections success filePath: " + filePath);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProcessingDialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("videoPath", filePath);
                JumpItent.jump(TakeVideoActivity.this, PlayVideoActivity.class, bundle);
            }
        });
    }

    /**
     * 保存失败时触发
     * 错误码，在 PLErrorCode 中定义
     */
    @Override
    public void onSaveVideoFailed(int errorCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProcessingDialog.dismiss();
                showToast("拼接视频段失败: " + errorCode);
            }
        });
    }

    /**
     * 保存取消时触发
     */
    @Override
    public void onSaveVideoCanceled() {
        mProcessingDialog.dismiss();
    }

    /**
     * 保存进度更新时触发
     * 进度百分比
     */
    @Override
    public void onProgressUpdate(float percentage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProcessingDialog.setProgress((int) (100 * percentage));
            }
        });
    }

    /**
     * 3、PLFocusListener
     * 该接口用于监听录制时对焦的事件
     */

    /**
     * 启动手动对焦是否成功
     * 启动手动对焦结果
     */
    @Override
    public void onManualFocusStart(boolean result) {
        if (result) {
            Log.i(TAG, "manual focus begin success");
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFocusIndicator.getLayoutParams();
            lp.leftMargin = mFocusIndicatorX;
            lp.topMargin = mFocusIndicatorY;
            mFocusIndicator.setLayoutParams(lp);
            mFocusIndicator.focus();
        } else {
            mFocusIndicator.focusCancel();
            Log.i(TAG, "manual focus not supported");
        }
    }

    /**
     * 手动对焦是否成功
     * 手动对焦结果
     */
    @Override
    public void onManualFocusStop(boolean result) {
        Log.i(TAG, "manual focus end result: " + result);
        if (result) {
            mFocusIndicator.focusSuccess();
        } else {
            mFocusIndicator.focusFail();
        }
    }

    /**
     * 当前手动对焦被取消，因另一个手动对焦事件被触发了
     */
    @Override
    public void onManualFocusCancel() {
        Log.i(TAG, "manual focus canceled");
        mFocusIndicator.focusCancel();
    }

    /**
     * 连续自动对焦启动事件
     * 只有在 PLCameraParamSelectListener#onFocusModeSelected(List) 中选择了
     * FOCUS_MODE_CONTINUOUS_VIDEO 或 FOCUS_MODE_CONTINUOUS_PICTURE 的对焦方式才会被触发
     */
    @Override
    public void onAutoFocusStart() {

    }

    /**
     * 连续自动对焦停止事件
     * 只有在 PLCameraParamSelectListener#onFocusModeSelected(List) 中选择了
     * FOCUS_MODE_CONTINUOUS_VIDEO 或 FOCUS_MODE_CONTINUOUS_PICTURE 的对焦方式才会被触发
     */
    @Override
    public void onAutoFocusStop() {

    }

    /**
     * PLFocusListener  end
     */

    //-----------------------------相关复写方法 结束---------------------------------
    @Override
    protected void onResume() {
        super.onResume();
        mShortVideoRecorder.resume();
        ivBegin.setEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShortVideoRecorder.pause();
        updateRecordingBtns(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShortVideoRecorder.destroy();
        mOrientationListener.disable();
    }


}
