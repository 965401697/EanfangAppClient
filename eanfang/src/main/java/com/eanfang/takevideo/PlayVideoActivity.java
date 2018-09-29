package com.eanfang.takevideo;

import android.os.Bundle;
import android.util.Log;

import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.application.CustomeApplication;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.FileUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.eanfang.witget.takavideo.MediaController;
import com.eanfang.witget.takavideo.ToastUtils;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnAudioFrameListener;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnVideoFrameListener;
import com.pili.pldroid.player.PLOnVideoSizeChangedListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.MediaStore.Images.Thumbnails.MINI_KIND;

/**
 * @author guanluocang
 * @data 2018/9/21
 * @description 播放视频
 */

public class PlayVideoActivity extends BaseActivity implements PLOnInfoListener, PLOnVideoSizeChangedListener, PLOnErrorListener, PLOnCompletionListener, PLOnBufferingUpdateListener, PLOnVideoFrameListener, PLOnAudioFrameListener, MediaController.OnClickSpeedAdjustListener {

    private static final String TAG = "PlayVideoActivity";

    /**
     * 播放View
     */
    @BindView(R2.id.plVideoTextureView)
    PLVideoTextureView plVideoTextureView;
    /**
     * 视频路径
     */
    private String mVideoPath = "";

    /**
     * 缩略图路径
     */
    private String mThumbnailName = "";

    private String mThumbnailPath = "";

    /**
     * 图片和视频公用一个UUID
     */
    private String uploadKey = "biz/repair/video/" + UuidUtil.getUUID();

    /**
     * 拍摄 还是 播放
     */
    private boolean isTake = false;

    /**
     * 技师端一个页面，多处拍摄视频 进行区分
     */
    private String mWorkType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("视频详情");
        setRightTitle("上传");
        mVideoPath = getIntent().getStringExtra("videoPath");
        isTake = getIntent().getBooleanExtra("isTake", false);
        mWorkType = getIntent().getStringExtra("worker_add");
        // 录制视频
        if (isTake) {
            setRightVisible();
            // 获取第一帧
            if (!StringUtils.isEmpty(mVideoPath)) {
//            PhotoUtils.getVideoThumbnail(mVideoPath, 100, 100, MINI_KIND);
                mThumbnailName = "pic_addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                mThumbnailPath = FileUtils.bitmapToFile(PhotoUtils.getVideoThumbnail(mVideoPath, 100, 100, MINI_KIND), mThumbnailName);
            }
        } else {
            setRightGone();
        }

        plVideoTextureView.setLooping(true);
        plVideoTextureView.setAVOptions(new AVOptions());
        plVideoTextureView.setVideoPath(mVideoPath);
        MediaController mediaController = new MediaController(this, true, false);
        mediaController.setOnClickSpeedAdjustListener(this);
        plVideoTextureView.setMediaController(mediaController);

        plVideoTextureView.setOnInfoListener(this);
        plVideoTextureView.setOnVideoSizeChangedListener(this);
        plVideoTextureView.setOnBufferingUpdateListener(this);
        plVideoTextureView.setOnCompletionListener(this);
        plVideoTextureView.setOnErrorListener(this);
        /**
         * 获取视频数据回调的对象
         */
        plVideoTextureView.setOnVideoFrameListener(this);
        /**
         * 获取音频数据回调的对象
         */
        plVideoTextureView.setOnAudioFrameListener(this);

        setRightTitleOnClickListener((v) -> doCommitThumbnail());
    }

    /**
     * 上传拍摄视频缩略图
     */
    private void doCommitThumbnail() {
        if (!StringUtils.isEmpty(mThumbnailPath)) {
            OSSUtils.initOSS(PlayVideoActivity.this).asyncPutImage(uploadKey + ".jpg", mThumbnailPath, new OSSCallBack(PlayVideoActivity.this, false) {
                @Override
                public void onOssSuccess() {
                    showToast("上传缩略图成功");
                    doCommitVideo();
                    super.onOssSuccess();
                }
            });
        }
    }

    /**
     * 进行上传视频
     */
    private void doCommitVideo() {
        if (!StringUtils.isEmpty(mVideoPath)) {
            OSSUtils.initOSS(PlayVideoActivity.this).asyncPutVideo(uploadKey + ".mp4", mVideoPath, new OSSCallBack(PlayVideoActivity.this, true) {
                @Override
                public void onOssSuccess() {
                    showToast("上传视频成功");
                    CustomeApplication.get().closeActivity(TakeVideoActivity.class.getName());
                    TakeVdideoMode takeVdideoMode = new TakeVdideoMode();
                    runOnUiThread(() -> {
                        takeVdideoMode.setMImagePath(mVideoPath);
                        takeVdideoMode.setMKey(uploadKey);
                        takeVdideoMode.setMType(mWorkType);
                        EventBus.getDefault().post(takeVdideoMode);
                        finishSelf();
                    });
                    super.onOssSuccess();
                }
            });
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        plVideoTextureView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        plVideoTextureView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        plVideoTextureView.stopPlayback();
    }


    /**
     * OnClickSpeedAdjustListener
     */
    @Override
    public void onClickNormal() {
        plVideoTextureView.setPlaySpeed(0X00010001);
    }

    @Override
    public void onClickFaster() {
        plVideoTextureView.setPlaySpeed(0X00020001);
    }

    @Override
    public void onClickSlower() {
        plVideoTextureView.setPlaySpeed(0X00010002);
    }

    /**
     * OnAudioFrameListener 获取音频数据回调的对象
     */
    @Override
    public void onAudioFrameAvailable(byte[] data, int size, int samplerate, int channels, int datawidth, long ts) {
//        Log.i(TAG, "onAudioFrameAvailable: " + size + ", " + samplerate + ", " + channels + ", " + datawidth + ", " + ts);
    }

    /**
     * OnBufferingUpdateListener 用于监听当前播放器已经缓冲的数据量占整个视频时长的百分比，在播放直播流中无效，仅在播放文件和回放时才有效。
     */
    @Override
    public void onBufferingUpdate(int precent) {
//        Log.i(TAG, "onBufferingUpdate: " + precent);
    }

    /**
     * OnCompletionListener 用于监听播放结束的消息
     */
    @Override
    public void onCompletion() {
//        Log.i(TAG, "Play Completed !");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.s(PlayVideoActivity.this, "Play Completed !");
            }
        });
        finish();
    }

    /**
     *
     * */
    @Override
    public boolean onError(int errorCode) {
//        Log.e(TAG, "Error happened, errorCode = " + errorCode);
        final String errorTip;
        switch (errorCode) {
            case ERROR_CODE_IO_ERROR:
                /**
                 * SDK will do reconnecting automatically
                 */
                Log.e(TAG, "IO Error!");
                return false;
            case ERROR_CODE_OPEN_FAILED:
                errorTip = "failed to open player !";
                break;
            case ERROR_CODE_SEEK_FAILED:
                errorTip = "failed to seek !";
                break;
            default:
                errorTip = "unknown error !";
                break;
        }
        if (errorTip != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.s(PlayVideoActivity.this, errorTip);
                }
            });
        }

        finish();
        return true;
    }

    @Override
    public void onInfo(int what, int extra) {
//        Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
        switch (what) {
            case MEDIA_INFO_BUFFERING_START:
                break;
            case MEDIA_INFO_BUFFERING_END:
                break;
            case MEDIA_INFO_VIDEO_RENDERING_START:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.s(PlayVideoActivity.this, "first video render time: " + extra + "ms");
                    }
                });
                break;
            case MEDIA_INFO_AUDIO_RENDERING_START:
                break;
            case MEDIA_INFO_VIDEO_FRAME_RENDERING:
//                Log.i(TAG, "video frame rendering, ts = " + extra);
                break;
            case MEDIA_INFO_AUDIO_FRAME_RENDERING:
//                Log.i(TAG, "audio frame rendering, ts = " + extra);
                break;
            case MEDIA_INFO_VIDEO_GOP_TIME:
//                Log.i(TAG, "Gop Time: " + extra);
                break;
            case MEDIA_INFO_SWITCHING_SW_DECODE:
//                Log.i(TAG, "Hardware decoding failure, switching software decoding!");
                break;
            case MEDIA_INFO_METADATA:
                Log.i(TAG, plVideoTextureView.getMetadata().toString());
                break;
            case MEDIA_INFO_VIDEO_BITRATE:
            case MEDIA_INFO_VIDEO_FPS:
//                Log.i(TAG, "FPS: " + extra);
                break;
            case MEDIA_INFO_CONNECTED:
//                Log.i(TAG, "Connected !");
                break;
            case MEDIA_INFO_VIDEO_ROTATION_CHANGED:
//                Log.i(TAG, "Rotation Changed: " + extra);
                plVideoTextureView.setDisplayOrientation(360 - extra);
                break;
            default:
                break;
        }
    }

    /**
     * PLOnVideoSizeChangedListener 该回调用于监听当前播放的视频流的尺寸信息，在 SDK 解析出视频的尺寸信息后，会触发该回调
     */
    @Override
    public void onVideoSizeChanged(int width, int height) {
//        Log.i(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height);
    }

    @Override
    public void onVideoFrameAvailable(byte[] data, int size, int width, int height, int format, long ts) {
//        Log.i(TAG, "onVideoFrameAvailable: " + size + ", " + width + " x " + height + ", " + format + ", " + ts);
    }

}
