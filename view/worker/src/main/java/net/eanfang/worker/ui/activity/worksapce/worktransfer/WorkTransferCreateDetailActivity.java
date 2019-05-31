package net.eanfang.worker.ui.activity.worksapce.worktransfer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.biz.model.WorkTransferDetailBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.widget.WorkTrancferCreateSelectClassListView;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

/**
 * @author Guanluocang
 * @date on 2018/7/27  15:45
 * @decision 创建交接班详情
 */
public class WorkTransferCreateDetailActivity extends BaseActivity {


    @BindView(R.id.iv_select_status)
    ImageView ivSelectStatus;
    private String mSwitch = "";
    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    private int RESQULT_CODE = 0;

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    @BindView(R.id.rl_select_status)
    RelativeLayout rlSelectStatus;
    @BindView(R.id.et_input_describe)
    EditText etInputDescribe;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    // 照片和短视频
    @BindView(R.id.tv_addViedeo)
    TextView tvAddViedeo;
    @BindView(R.id.iv_takevideo)
    ImageView ivTakevideo;
    @BindView(R.id.rl_thumbnail)
    RelativeLayout rlThumbnail;
    @BindView(R.id.et_input_note)
    EditText etInputNote;
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;
    //详细描述
    @BindView(R.id.iv_desc_voice)
    ImageView ivDescVoice;
    // 备注信息
    @BindView(R.id.iv_remark_voice)
    ImageView ivRemarkVoice;

    private HashMap<String, String> uploadMap = new HashMap<>();

    private WorkTransferDetailBean.ChangeGoodEntityListBean changeGoodDetail;

    private WorkTransferDetailBean.FinishWorkEntityListBean finishDetail;

    private WorkTransferDetailBean.NotDidEntityListBean unFinishDetail;

    private WorkTransferDetailBean.FollowUpEntityListBean followThingDetail;

    private WorkTransferDetailBean.NoticeEntityListBean attentionDetail;

    /**
     * 附件信息(3张)
     */
    private ArrayList<String> picList4 = new ArrayList<>();
    /**
     * 状态ID
     */
    private int mStatusID = 100;
    /**
     * 视频上传key
     */
    private String mUploadKey = "";
    /**
     * 视频路径
     */
    private String mVieoPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_transfer_create_detail);
        ButterKnife.bind(this);
        initView();
        initPhoto();
        initListener();
    }

    private void initPhoto() {
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
    }

    private void initView() {
        setLeftBack();
        setTitle("交接班");
        mSwitch = getIntent().getStringExtra("switch");
        if ("ADD_HAND".equals(mSwitch)) {
            RESQULT_CODE = 1000;
            changeGoodDetail = new WorkTransferDetailBean.ChangeGoodEntityListBean();
        } else if ("ADD_FINISH_WORK".equals(mSwitch)) {
            RESQULT_CODE = 2000;
            finishDetail = new WorkTransferDetailBean.FinishWorkEntityListBean();
        } else if ("ADD_UNFINISH_THINGS".equals(mSwitch)) {
            RESQULT_CODE = 3000;
            unFinishDetail = new WorkTransferDetailBean.NotDidEntityListBean();
        } else if ("ADD_FLOW_THINGS".equals(mSwitch)) {
            RESQULT_CODE = 4000;
            followThingDetail = new WorkTransferDetailBean.FollowUpEntityListBean();
        } else if ("ADD_ATTENTION_THINGS".equals(mSwitch)) {
            RESQULT_CODE = 5000;
            attentionDetail = new WorkTransferDetailBean.NoticeEntityListBean();
        }
        if (mSwitch == null) {
            changeGoodDetail = (WorkTransferDetailBean.ChangeGoodEntityListBean) getIntent().getSerializableExtra("changeGoodDetail");
            finishDetail = (WorkTransferDetailBean.FinishWorkEntityListBean) getIntent().getSerializableExtra("finishDetail");
            unFinishDetail = (WorkTransferDetailBean.NotDidEntityListBean) getIntent().getSerializableExtra("unFinishDetail");
            followThingDetail = (WorkTransferDetailBean.FollowUpEntityListBean) getIntent().getSerializableExtra("followThingDetail");
            attentionDetail = (WorkTransferDetailBean.NoticeEntityListBean) getIntent().getSerializableExtra("attentionDetail");
            ivDescVoice.setVisibility(View.GONE);
            ivRemarkVoice.setVisibility(View.GONE);
            tvAddViedeo.setVisibility(View.GONE);
            initData();
        }
    }

    private void initListener() {
        // 拍摄视频
        tvAddViedeo.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            JumpItent.jump(WorkTransferCreateDetailActivity.this, TakeVideoActivity.class, bundle);
        });
        //视频展示
        ivTakevideo.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", mVieoPath);
            JumpItent.jump(WorkTransferCreateDetailActivity.this, PlayVideoActivity.class, bundle);
        });
    }

    @OnClick({R.id.rl_select_status, R.id.rl_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 选择状态
            case R.id.rl_select_status:
                doGetClass();
                break;
            // 确定提交
            case R.id.rl_confirm:
                doSubmit();
                break;
        }
    }

    /**
     * 获取班次
     */
    private void doGetClass() {
        new WorkTrancferCreateSelectClassListView(WorkTransferCreateDetailActivity.this, "status", name -> {
            tvStatus.setText(name);
            mStatusID = GetConstDataUtils.getWokrTransferDetailStatus().indexOf(name);
        }).show();
    }

    /**
     * 提交
     */
    private void doSubmit() {
        // 交接内容
        String mContent = etInputContent.getText().toString().trim();
        // 当前状态
        String mStatus = tvStatus.getText().toString().trim();
        // 详细说明
        String mDescribe = etInputDescribe.getText().toString().trim();
        // 备注信息
        String mNote = etInputNote.getText().toString().trim();
        if (StringUtils.isEmpty(mContent)) {
            showToast("请输入交接内容");
            return;
        }
        if (StringUtils.isEmpty(mStatus)) {
            showToast("请输入当前状态");
            return;
        }
        if (StringUtils.isEmpty(mDescribe)) {
            showToast("请输入详细说明");
            return;
        }
        if (StringUtils.isEmpty(mNote)) {
            showToast("请输入备注信息");
            return;
        }
        // 添加照片
        String presentationPic = PhotoUtils.getPhotoUrl("oa/transfer/", snplMomentAddPhotos, uploadMap, true);
        if ("ADD_HAND".equals(mSwitch)) {
            changeGoodDetail.setPicture(presentationPic);
            //备注
            changeGoodDetail.setContext(mNote);
            // 交接内容
            changeGoodDetail.setContent(mContent);
            // 当前状态
            changeGoodDetail.setStatus(GetConstDataUtils.getWokrTransferDetailStatus().indexOf(mStatus));
            // 详细描述
            changeGoodDetail.setDescription(mDescribe);
            changeGoodDetail.setType(1);
            changeGoodDetail.setMp4_path(mUploadKey);
        } else if ("ADD_FINISH_WORK".equals(mSwitch)) {
            finishDetail.setPicture(presentationPic);
            //备注
            finishDetail.setContext(mNote);
            // 交接内容
            finishDetail.setContent(mContent);
            // 当前状态
            finishDetail.setStatus(GetConstDataUtils.getWokrTransferDetailStatus().indexOf(mStatus));
            // 详细描述
            finishDetail.setDescription(mDescribe);
            finishDetail.setType(2);
            finishDetail.setMp4_path(mUploadKey);
        } else if ("ADD_UNFINISH_THINGS".equals(mSwitch)) {
            unFinishDetail.setPicture(presentationPic);
            //备注
            unFinishDetail.setContext(mNote);
            // 交接内容
            unFinishDetail.setContent(mContent);
            // 当前状态
            unFinishDetail.setStatus(GetConstDataUtils.getWokrTransferDetailStatus().indexOf(mStatus));
            // 详细描述
            unFinishDetail.setDescription(mDescribe);
            unFinishDetail.setType(3);
            unFinishDetail.setMp4_path(mUploadKey);
        } else if ("ADD_FLOW_THINGS".equals(mSwitch)) {
            followThingDetail.setPicture(presentationPic);
            //备注
            followThingDetail.setContext(mNote);
            // 交接内容
            followThingDetail.setContent(mContent);
            // 当前状态
            followThingDetail.setStatus(GetConstDataUtils.getWokrTransferDetailStatus().indexOf(mStatus));
            // 详细描述
            followThingDetail.setDescription(mDescribe);
            followThingDetail.setType(4);
            followThingDetail.setMp4_path(mUploadKey);
        } else if ("ADD_ATTENTION_THINGS".equals(mSwitch)) {
            attentionDetail.setPicture(presentationPic);
            //备注
            attentionDetail.setContext(mNote);
            // 交接内容
            attentionDetail.setContent(mContent);
            // 当前状态
            attentionDetail.setStatus(GetConstDataUtils.getWokrTransferDetailStatus().indexOf(mStatus));
            // 详细描述
            attentionDetail.setDescription(mDescribe);
            attentionDetail.setType(5);
            attentionDetail.setMp4_path(mUploadKey);
        }
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        doFinish();
                    });
                }
            });
        } else {
            doFinish();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_1:
                snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
        }
    }

    public void doFinish() {
        Intent intent = new Intent();
        if ("ADD_HAND".equals(mSwitch)) {
            intent.putExtra("detail", changeGoodDetail);
        } else if ("ADD_FINISH_WORK".equals(mSwitch)) {
            intent.putExtra("detail", finishDetail);
        } else if ("ADD_UNFINISH_THINGS".equals(mSwitch)) {
            intent.putExtra("detail", unFinishDetail);
        } else if ("ADD_FLOW_THINGS".equals(mSwitch)) {
            intent.putExtra("detail", followThingDetail);
        } else if ("ADD_ATTENTION_THINGS".equals(mSwitch)) {
            intent.putExtra("detail", attentionDetail);
        }
        setResult(RESQULT_CODE, intent);
        finishSelf();
    }

    private void initData() {
        if (changeGoodDetail != null) {
            etInputContent.setText("交接内容:  " + changeGoodDetail.getContent());
            etInputContent.setFocusable(false);
            tvStatus.setText("当前状态:  " + GetConstDataUtils.getWokrTransferDetailStatus().get(changeGoodDetail.getStatus()));
            etInputDescribe.setText(changeGoodDetail.getDescription());
            etInputDescribe.setFocusable(false);
            etInputNote.setText(changeGoodDetail.getContext());
            etInputNote.setFocusable(false);
            rlConfirm.setVisibility(View.GONE);
            ivSelectStatus.setVisibility(View.GONE);
            if (!StringUtils.isEmpty(changeGoodDetail.getPicture())) {
                snplMomentAddPhotos.setVisibility(View.VISIBLE);
                String[] invoicesPic = changeGoodDetail.getPicture().split(",");
                picList4.addAll(Stream.of(Arrays.asList(invoicesPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
            } else {
                snplMomentAddPhotos.setVisibility(View.GONE);
            }
            snplMomentAddPhotos.setData(picList4);
            snplMomentAddPhotos.setEditable(false);
            snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
            if (!StringUtils.isEmpty(changeGoodDetail.getMp4_path())) {
                rlThumbnail.setVisibility(View.VISIBLE);
                GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + changeGoodDetail.getMp4_path() + ".jpg"),ivTakevideo);
                mVieoPath = BuildConfig.OSS_SERVER + changeGoodDetail.getMp4_path() + ".mp4";
            }
        }
        if (finishDetail != null) {
            etInputContent.setText("交接内容:  " + finishDetail.getContent());
            etInputContent.setFocusable(false);
            tvStatus.setText("当前状态:  " + GetConstDataUtils.getWokrTransferDetailStatus().get(finishDetail.getStatus()));
            etInputDescribe.setText(finishDetail.getDescription());
            etInputDescribe.setFocusable(false);
            etInputNote.setText(finishDetail.getContext());
            etInputNote.setFocusable(false);
            rlConfirm.setVisibility(View.GONE);
            ivSelectStatus.setVisibility(View.GONE);
            if (!StringUtils.isEmpty(finishDetail.getPicture())) {
                snplMomentAddPhotos.setVisibility(View.VISIBLE);
                String[] invoicesPic = finishDetail.getPicture().split(",");
                picList4.addAll(Stream.of(Arrays.asList(invoicesPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
            } else {
                snplMomentAddPhotos.setVisibility(View.GONE);
            }
            snplMomentAddPhotos.setData(picList4);
            snplMomentAddPhotos.setEditable(false);
            snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
            if (!StringUtils.isEmpty(finishDetail.getMp4_path())) {
                rlThumbnail.setVisibility(View.VISIBLE);
                GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + finishDetail.getMp4_path() + ".jpg"),ivTakevideo);
                mVieoPath = BuildConfig.OSS_SERVER + finishDetail.getMp4_path() + ".mp4";
            }
        }
        if (unFinishDetail != null) {
            etInputContent.setText("交接内容:  " + unFinishDetail.getContent());
            etInputContent.setFocusable(false);
            tvStatus.setText("当前状态:  " + GetConstDataUtils.getWokrTransferDetailStatus().get(unFinishDetail.getStatus()));
            etInputDescribe.setText(unFinishDetail.getDescription());
            etInputDescribe.setFocusable(false);
            etInputNote.setText(unFinishDetail.getContext());
            etInputNote.setFocusable(false);
            rlConfirm.setVisibility(View.GONE);
            ivSelectStatus.setVisibility(View.GONE);
            if (!StringUtils.isEmpty(unFinishDetail.getPicture())) {
                snplMomentAddPhotos.setVisibility(View.VISIBLE);
                String[] invoicesPic = unFinishDetail.getPicture().split(",");
                picList4.addAll(Stream.of(Arrays.asList(invoicesPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
            } else {
                snplMomentAddPhotos.setVisibility(View.GONE);
            }
            snplMomentAddPhotos.setData(picList4);
            snplMomentAddPhotos.setEditable(false);
            snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
            if (!StringUtils.isEmpty(unFinishDetail.getMp4_path())) {
                rlThumbnail.setVisibility(View.VISIBLE);
                GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + unFinishDetail.getMp4_path() + ".jpg"),ivTakevideo);
                mVieoPath = BuildConfig.OSS_SERVER + unFinishDetail.getMp4_path() + ".mp4";
            }
        }
        if (followThingDetail != null) {
            etInputContent.setText("交接内容:  " + followThingDetail.getContent());
            etInputContent.setFocusable(false);
            tvStatus.setText("当前状态:  " + GetConstDataUtils.getWokrTransferDetailStatus().get(followThingDetail.getStatus()));
            etInputDescribe.setText(followThingDetail.getDescription());
            etInputDescribe.setFocusable(false);
            etInputNote.setText(followThingDetail.getContext());
            etInputNote.setFocusable(false);
            rlConfirm.setVisibility(View.GONE);
            ivSelectStatus.setVisibility(View.GONE);
            if (!StringUtils.isEmpty(followThingDetail.getPicture())) {
                snplMomentAddPhotos.setVisibility(View.VISIBLE);
                String[] invoicesPic = followThingDetail.getPicture().split(",");
                picList4.addAll(Stream.of(Arrays.asList(invoicesPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
            } else {
                snplMomentAddPhotos.setVisibility(View.GONE);
            }
            snplMomentAddPhotos.setData(picList4);
            snplMomentAddPhotos.setEditable(false);
            snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
            if (!StringUtils.isEmpty(followThingDetail.getMp4_path())) {
                rlThumbnail.setVisibility(View.VISIBLE);
                GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + followThingDetail.getMp4_path() + ".jpg"),ivTakevideo);
                mVieoPath = BuildConfig.OSS_SERVER + followThingDetail.getMp4_path() + ".mp4";
            }
        }
        if (attentionDetail != null) {
            etInputContent.setText("交接内容:  " + attentionDetail.getContent());
            etInputContent.setFocusable(false);
            tvStatus.setText("当前状态:  " + GetConstDataUtils.getWokrTransferDetailStatus().get(attentionDetail.getStatus()));
            etInputDescribe.setText(attentionDetail.getDescription());
            etInputDescribe.setFocusable(false);
            etInputNote.setText(attentionDetail.getContext());
            etInputNote.setFocusable(false);
            rlConfirm.setVisibility(View.GONE);
            ivSelectStatus.setVisibility(View.GONE);
            if (!StringUtils.isEmpty(attentionDetail.getPicture())) {
                snplMomentAddPhotos.setVisibility(View.VISIBLE);
                String[] invoicesPic = attentionDetail.getPicture().split(",");
                picList4.addAll(Stream.of(Arrays.asList(invoicesPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
            } else {
                snplMomentAddPhotos.setVisibility(View.GONE);
            }
            snplMomentAddPhotos.setData(picList4);
            snplMomentAddPhotos.setEditable(false);
            snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
            if (!StringUtils.isEmpty(attentionDetail.getMp4_path())) {
                rlThumbnail.setVisibility(View.VISIBLE);
                GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + attentionDetail.getMp4_path() + ".jpg"),ivTakevideo);
                mVieoPath = BuildConfig.OSS_SERVER + attentionDetail.getMp4_path() + ".mp4";
            }
        }

    }

    @OnClick({R.id.iv_desc_voice, R.id.iv_remark_voice})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_desc_voice:
                inputVoice(etInputDescribe);
                break;
            case R.id.iv_remark_voice:
                inputVoice(etInputNote);
                break;
        }
    }

    private void inputVoice(EditText editText) {
        PermissionUtils.get(this).getVoicePermission(() -> {
            RecognitionManager.getSingleton().startRecognitionWithDialog(WorkTransferCreateDetailActivity.this, editText);
        });
    }

    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            rlThumbnail.setVisibility(View.VISIBLE);
            mVieoPath = takeVdideoMode.getMImagePath();
            mUploadKey = takeVdideoMode.getMKey();
            if (!StringUtils.isEmpty(mVieoPath)) {
                GlideUtil.intoImageView(this,PhotoUtils.getVideoThumbnail(mVieoPath, 100, 100, MINI_KIND),ivTakevideo);
            }
            tvAddViedeo.setText("重新拍摄");
        }
    }
}
