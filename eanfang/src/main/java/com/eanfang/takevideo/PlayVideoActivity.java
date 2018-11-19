package com.eanfang.takevideo;

import android.os.Bundle;

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

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JzvdStd;

import static android.provider.MediaStore.Images.Thumbnails.MINI_KIND;

/**
 * @author guanluocang
 * @data 2018/9/21
 * @description 播放视频
 */

public class PlayVideoActivity extends BaseActivity {

    private static final String TAG = "PlayVideoActivity";

    /**
     * 播放View
     */
    @BindView(R2.id.videoPlayer)
    JzvdStd videoplayer;

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
        } else { // 播放视频
            setRightGone();
        }

        videoplayer.setUp(mVideoPath, "", JzvdStd.SCREEN_WINDOW_NORMAL);
        //自动播放
        videoplayer.startButton.performClick();
        videoplayer.startVideo();
        //加缩略载图
//        Glide.with(this).load(mThumbnailPath).into(videoplayer.thumbImageView);

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
//                    showToast("上传缩略图成功");
                    doCommitVideo();
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
//                    showToast("上传视频成功");
                    CustomeApplication.get().closeActivity(TakeVideoActivity.class.getName());
                    TakeVdideoMode takeVdideoMode = new TakeVdideoMode();
                    runOnUiThread(() -> {
                        takeVdideoMode.setMImagePath(mVideoPath);
                        takeVdideoMode.setMKey(uploadKey);
                        takeVdideoMode.setMType(mWorkType);
                        EventBus.getDefault().post(takeVdideoMode);
                        finishSelf();
                    });
                }
            });
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        JzvdStd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JzvdStd.backPress()) {
            return;
        }
        super.onBackPressed();
    }


}
