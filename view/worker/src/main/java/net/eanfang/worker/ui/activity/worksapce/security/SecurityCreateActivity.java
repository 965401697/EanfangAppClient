package net.eanfang.worker.ui.activity.worksapce.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.security.SecurityCreateBean;
import com.eanfang.biz.model.security.SecurityFoucsListBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.witget.TakeVideoPopWindow;
import com.eanfang.witget.mentionedittext.edit.MentionEditText;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.im.VideoSelectedActivity;
import net.eanfang.worker.ui.activity.worksapce.online.FreeAskActivity;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

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
    MentionEditText etContent;
    @BindView(R.id.iv_show_video)
    SimpleDraweeView ivShowVideo;
    @BindView(R.id.rl_video)
    RelativeLayout rlVideo;

    /**
     * 上传图片路径
     */
    private String mPic = "";

    private HashMap<String, String> uploadMap = new HashMap<>();

    private SecurityCreateBean securityCreateBean = new SecurityCreateBean();
    private TakeVideoPopWindow takeVideoPopWindow;

    /**
     * 视频上传key
     */
    private String mUploadKey = "";
    /**
     * 视频路径
     */
    private String mVieoPath = "";

    /**
     * 相册选取视频
     */
    private static final int REQUEST_CODE_CHOOSE_VIDEO = 1000;
    /**
     * 艾特人
     */
    private static final int REQUEST_CODE_ABOUT = 1010;
    private StringBuffer mAtUserId = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_create);
        ButterKnife.bind(this);
        initView();
        initListener();
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

    private void initListener() {
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1 && !TextUtils.isEmpty(s)) {
                    char mentionChar = s.toString().charAt(start);
                    int selectionStart = etContent.getSelectionStart();
                    if (mentionChar == '@') {
                        Bundle bundle_foucus = new Bundle();
                        bundle_foucus.putString("type", "foucs");
                        bundle_foucus.putBoolean("create", true);
                        JumpItent.jump(SecurityCreateActivity.this, SecurityPersonalPublicListActivity.class, bundle_foucus, REQUEST_CODE_CHOOSE_VIDEO);
                        etContent.getText().delete(selectionStart - 1, selectionStart);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    public void doCommit() {
        mAtUserId = StringUtils.getSecurityId(etContent.getFormatCharSequence().toString());
        String mContent = etContent.getText().toString().trim();
        mPic = PhotoUtils.getPhotoUrl("sercurity/", snplAddPhoto, uploadMap, false);
        if (!StringUtils.isEmpty(mContent) || !StringUtils.isEmpty(mPic)) {
            securityCreateBean.setSpcContent(mContent);
            securityCreateBean.setSpcImg(mPic);
            securityCreateBean.setSpcVideo(mUploadKey);
            securityCreateBean.setAtUserId(mAtUserId.toString());
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
            //相册选取视频
            case REQUEST_CODE_CHOOSE_VIDEO:
                mVieoPath = data.getStringExtra("videoPath");
                break;
            // 艾特人
            case REQUEST_CODE_ABOUT:
                SecurityFoucsListBean.ListBean.UserEntityBean accountEntityBean =
                        (SecurityFoucsListBean.ListBean.UserEntityBean) data.getSerializableExtra("foucsAccountEntity");
                etContent.insert(accountEntityBean);
                break;
            default:
                break;
        }
    }

    View.OnClickListener takeVideoSelectItemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // 拍摄
                case R.id.tv_take:
                    Bundle bundle_addvideo = new Bundle();
                    bundle_addvideo.putString("videoPath", "addsecurity_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    JumpItent.jump(SecurityCreateActivity.this, TakeVideoActivity.class, bundle_addvideo);
                    takeVideoPopWindow.dismiss();
                    break;
                // 相册选择
                case R.id.tv_select:
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "addSecurity");
                    JumpItent.jump(SecurityCreateActivity.this, VideoSelectedActivity.class, bundle, REQUEST_CODE_CHOOSE_VIDEO);
                    takeVideoPopWindow.dismiss();
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

    @OnClick({R.id.iv_video, R.id.iv_about, R.id.iv_question, R.id.iv_workers, R.id.rl_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_video:
                hideKeyboard();
                //添加视频
                takeVideoPopWindow.showAtLocation(SecurityCreateActivity.this.findViewById(R.id.rl_security_create), Gravity.BOTTOM, 0, 0);
                takeVideoPopWindow.backgroundAlpha(0.5f);
                break;
            // 艾特人
            case R.id.iv_about:
                Bundle bundle_foucus = new Bundle();
                bundle_foucus.putString("type", "foucs");
                bundle_foucus.putBoolean("create", true);
                JumpItent.jump(SecurityCreateActivity.this, SecurityPersonalPublicListActivity.class, bundle_foucus, REQUEST_CODE_ABOUT);
                break;
            case R.id.iv_question:
                JumpItent.jump(SecurityCreateActivity.this, FreeAskActivity.class);
                finishSelf();
                break;
            case R.id.iv_workers:
                break;
            // 查看视频
            case R.id.rl_video:
                Bundle bundle = new Bundle();
                bundle.putString("videoPath", mVieoPath);
                JumpItent.jump(SecurityCreateActivity.this, PlayVideoActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    @Subscribe()
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            rlVideo.setVisibility(View.VISIBLE);
            mVieoPath = takeVdideoMode.getMImagePath();
            mUploadKey = takeVdideoMode.getMKey();
            if (!StringUtils.isEmpty(mVieoPath)) {
                if (ivShowVideo.getVisibility() == View.INVISIBLE) {
                    ivShowVideo.setVisibility(View.VISIBLE);
                }
                ivShowVideo.setImageBitmap(PhotoUtils.getVideoThumbnail(mVieoPath, 100, 100, MINI_KIND));
            }
        }
    }

    /**
     * 隐藏键盘与布局
     */
    private void hideKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
