package net.eanfang.worker.ui.activity.worksapce.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityCreateBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.witget.TakeVideoPopWindow;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/2/14
 * @description 创建安防圈
 */

public class SecurityCreateActivity extends BaseActivity {

    private static final int REQUEST_CODE_CHOOSE_CERTIFICATE = 1;
    private static final int REQUEST_CODE_PHOTO_CERTIFICATE = 101;
    @BindView(R.id.snpl_add_photo)
    BGASortableNinePhotoLayout snplAddPhoto;
    @BindView(R.id.et_content)
    EditText etContent;

    /**
     * 上传图片路径
     */
    private String mPic = "";

    private HashMap<String, String> uploadMap = new HashMap<>();

    private SecurityCreateBean securityCreateBean = new SecurityCreateBean();
    private TakeVideoPopWindow takeVideoPopWindow;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_create);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("编辑");
        setLeftBack();
        setRightTitle("发布");
        snplAddPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_CERTIFICATE, REQUEST_CODE_PHOTO_CERTIFICATE));

        setRightTitleOnClickListener((v) -> {
            doCommit();
        });
        takeVideoPopWindow = new TakeVideoPopWindow(this, takeVideoSelectItemsOnClick);
        takeVideoPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                takeVideoPopWindow.backgroundAlpha(1.0f);
            }
        });
    }

    public void doCommit() {
        String mContent = etContent.getText().toString().trim();
        mPic = PhotoUtils.getPhotoUrl("sercurity/", snplAddPhoto, uploadMap, false);
        if (!StringUtils.isEmpty(mContent) || !StringUtils.isEmpty(mPic)) {
            securityCreateBean.setSpcContent(mContent);
            securityCreateBean.setSpcImg(mPic);
            if (uploadMap.size() != 0) {
                OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                    @Override
                    public void onOssSuccess() {
                        runOnUiThread(() -> {
                            doSubmit();
                        });
                    }
                });
            } else {
                doSubmit();
            }
        } else {
            showToast("请输入发布内容");
        }
    }

    public void doSubmit() {
        EanfangHttp.post(NewApiService.SERCURITY_CREATE)
                .upJson(JSONObject.toJSONString(securityCreateBean))
                .execute(new EanfangCallback<JSONObject>(SecurityCreateActivity.this, true, JSONObject.class, bean1 -> {
                    setResult(RESULT_OK);
                    finishSelf();
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_CERTIFICATE:
                snplAddPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_CERTIFICATE:
                snplAddPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
        }
    }

    View.OnClickListener takeVideoSelectItemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // 拍摄
                case R.id.tv_share_wx:
                    Bundle bundle_addvideo = new Bundle();
                    bundle_addvideo.putString("videoPath", "addsecurity_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    JumpItent.jump(SecurityCreateActivity.this, TakeVideoActivity.class, bundle_addvideo);
                    break;
                // 相册选择
                case R.id.tv_share_contact:
                    break;
                // 取消
                case R.id.btn_cancel:
                    takeVideoPopWindow.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick({R.id.iv_video, R.id.iv_about, R.id.iv_question, R.id.iv_workers})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_video:
                //分享聊天
                takeVideoPopWindow.showAtLocation(SecurityCreateActivity.this.findViewById(R.id.rl_security_create), Gravity.BOTTOM, 0, 0);
                takeVideoPopWindow.backgroundAlpha(0.5f);
                break;
            case R.id.iv_about:
                break;
            case R.id.iv_question:
                break;
            case R.id.iv_workers:
                break;
            default:
                break;
        }
    }

    //MAIN代表主线程
//    @Subscribe()
//    public void receivePath(TakeVdideoMode takeVdideoMode) {
//        if (takeVdideoMode != null) {
//            rlThumbnailCheck.setVisibility(View.VISIBLE);
//            mVieoPath = takeVdideoMode.getMImagePath();
//            mUploadKey = takeVdideoMode.getMKey();
//            if (!StringUtils.isEmpty(mVieoPath)) {
//
//                if (ivTakevideoCheck.getVisibility() == View.INVISIBLE) {
//                    ivTakevideoCheck.setVisibility(View.VISIBLE);
//                }
//
//                ivTakevideoCheck.setImageBitmap(PhotoUtils.getVideoThumbnail(mVieoPath, 100, 100, MINI_KIND));
//            }
//        }
//    }

}
