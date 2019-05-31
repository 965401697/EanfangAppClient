package net.eanfang.client.ui.activity.worksapce.oa.check;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.AddWorkInspectDetailBean;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.WorkInspectEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.oa.OAPersonAdaptet;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

/**
 * @author guanluocang
 * @data 2018/9/14
 * @description 设备点检之 - 添加处理明细
 */


public class AddDealwithInfoActivity extends BaseClientActivity {

    @BindView(R.id.et_input_check_content)
    EditText etInputCheckContent;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.iv_takevideo_work)
    ImageView ivTakevideoWork;
    @BindView(R.id.rl_thumbnail_work)
    RelativeLayout rlThumbnailWork;
    @BindView(R.id.snpl_photos_work)
    BGASortableNinePhotoLayout snplPhotosWork;
    @BindView(R.id.tv_add_video)
    ImageView tvAddVideo;
    @BindView(R.id.rv_team_work)
    RecyclerView rvTeamWork;
    @BindView(R.id.iv_dealwith_content)
    ImageView ivDealwithContent;
    @BindView(R.id.iv_note)
    ImageView ivNote;

    private Map<String, String> uploadMap = new HashMap<>();
    private AddWorkInspectDetailBean addWorkInspectDetailBean = new AddWorkInspectDetailBean();

    private WorkInspectEntity detailsBean;

    /**
     * 拍摄照片
     */
    private Map<String, String> mUploadMap = new HashMap<>();
    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    /**
     * 视频上传key
     */
    private String mUploadKey = "";
    /**
     * 视频路径
     */
    private String mVieoPath = "";
    public int mFlag;//人员选择器的标志位
    //协同人员
    private OAPersonAdaptet oaPersonAdaptet;
    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealwith_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("添加处理明细");
        setLeftBack();
        detailsBean = (WorkInspectEntity) getIntent().getSerializableExtra("bean");
        snplPhotosWork.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        //协同人员
        rvTeamWork.setLayoutManager(new GridLayoutManager(this, 5));
        oaPersonAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 1);
        rvTeamWork.setAdapter(oaPersonAdaptet);
    }


    private boolean checkInfo() {
        if (TextUtils.isEmpty(etInputCheckContent.getText().toString().trim())) {
            showToast("请填写处理明细");
            return false;
        }
        if (TextUtils.isEmpty(etRemark.getText().toString().trim())) {
            showToast("请填写处理明细");
            return false;
        }
        return true;
    }

    private void submit() {
        if (!checkInfo()) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();

        for (int j = 0; j < oaPersonAdaptet.getData().size(); j++) {
            TemplateBean.Preson preson = oaPersonAdaptet.getData().get(j);
            if (j == oaPersonAdaptet.getData().size() - 1) {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName());
            } else {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName() + ",");
            }
        }
        addWorkInspectDetailBean.setCollaborativeUser(stringBuffer.toString());
        addWorkInspectDetailBean.setDisposeInfo(etInputCheckContent.getText().toString().trim());
        addWorkInspectDetailBean.setRemarkInfo(etRemark.getText().toString().trim());
        String ursStr = PhotoUtils.getPhotoUrl("oa/workCheck", snplPhotosWork, uploadMap, true);
        addWorkInspectDetailBean.setPictures(ursStr);
        addWorkInspectDetailBean.setOaWorkInspectId(detailsBean.getId());
        addWorkInspectDetailBean.setMp4Path(mUploadKey);

        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        doHttp(JSON.toJSONString(addWorkInspectDetailBean));
                    });
                }
            });
        } else {
            doHttp(JSON.toJSONString(addWorkInspectDetailBean));
        }
    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(NewApiService.ADD_WORK_CHECK_DETAIL)
                .upJson(jsonString)
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    showToast("处理成功");
                    closeBeforeActivity();
                }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_1:
                snplPhotosWork.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplPhotosWork.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            default:
                break;
        }
    }

    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            rlThumbnailWork.setVisibility(View.VISIBLE);
            mVieoPath = takeVdideoMode.getMImagePath();
            mUploadKey = takeVdideoMode.getMKey();
            if (!StringUtils.isEmpty(mVieoPath)) {

                if (ivTakevideoWork.getVisibility() == View.INVISIBLE) {
                    ivTakevideoWork.setVisibility(View.VISIBLE);
                }
                GlideUtil.intoImageView(this,PhotoUtils.getVideoThumbnail(mVieoPath, 100, 100, MINI_KIND),ivTakevideoWork);
            }
        }
    }

    public void closeBeforeActivity() {
        ClientApplication.get().closeActivity(DealWithFirstActivity.class);
        EventBus.getDefault().post("addDealWithInfoSuccess");
        finishSelf();
    }

    @OnClick({R.id.iv_dealwith_content, R.id.iv_note, R.id.iv_takevideo_work, R.id.tv_add_video, R.id.tv_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_dealwith_content:
                inputVoice(etInputCheckContent);
                break;
            case R.id.iv_note:
                inputVoice(etRemark);
                break;
            case R.id.iv_takevideo_work:
                Bundle bundle_takevideo = new Bundle();
                bundle_takevideo.putString("videoPath", mVieoPath);
                JumpItent.jump(AddDealwithInfoActivity.this, PlayVideoActivity.class, bundle_takevideo);
                break;
            case R.id.tv_add_video:
                Bundle bundle_addvideo = new Bundle();
                bundle_addvideo.putString("videoPath", "addcheck_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JumpItent.jump(AddDealwithInfoActivity.this, TakeVideoActivity.class, bundle_addvideo);
                break;
            case R.id.tv_sub:
                submit();
                break;
            default:
                break;

        }
    }

    private void inputVoice(EditText editText) {
        PermissionUtils.get(this).getVoicePermission(() -> {
            RecognitionManager.getSingleton().startRecognitionWithDialog(AddDealwithInfoActivity.this, editText);
        });
    }

    public void setFlag(int flag) {
        this.mFlag = flag;
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (presonList.size() > 0) {
            Set hashSet = new HashSet();
            if (mFlag == 1) {
                hashSet.addAll(oaPersonAdaptet.getData());
            }
            hashSet.addAll(presonList);
            newPresonList.clear();
            newPresonList.addAll(hashSet);
            if (mFlag == 1) {
                oaPersonAdaptet.setNewData(newPresonList);
            }
        }

    }

}
