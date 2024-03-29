package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.biz.model.bean.WorkAddReportBean;

import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.activity.SelectOAPresonActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.maintenance.MaintenanceTeamAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import cn.hutool.core.util.StrUtil;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/4  11:46
 * @email houzhongzhou@yeah.net
 * @desc 添加后续计划
 */

public class AddReportPlanActivity extends BaseClientActivity implements View.OnClickListener {
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    @BindView(R.id.et_input_jion)
    EditText etInputJion;
    @BindView(R.id.et_input_legacy)
    EditText etInputLegacy;
    //    @BindView(R.id.et_input_reason)
//    EditText etInputReason;
    @BindView(R.id.tv_depend_person)
    TextView tvDependPerson;
    @BindView(R.id.ll_depend_person)
    LinearLayout llDependPerson;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    // 照片和短视频
    @BindView(R.id.tv_addViedeo)
    TextView tvAddViedeo;
    @BindView(R.id.iv_takevideo)
    ImageView ivTakevideo;
    @BindView(R.id.rl_thumbnail)
    RelativeLayout rlThumbnail;
    @BindView(R.id.rv_team)
    RecyclerView rvTeam;

    private WorkAddReportBean.WorkReportDetailsBean bean;
    private Map<String, String> uploadMap = new HashMap<>();

    private MaintenanceTeamAdapter teamAdapter;
    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();

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
        setContentView(R.layout.activity_add_plan_info);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("工作计划");
        setRightTitle("提交");
        llDependPerson.setOnClickListener(this);
        bean = new WorkAddReportBean.WorkReportDetailsBean();
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvTeam.setLayoutManager(manager);

        teamAdapter = new MaintenanceTeamAdapter();
        teamAdapter.bindToRecyclerView(rvTeam);
        teamAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                teamAdapter.remove(position);
            }
        });
    }

    private void submit() {
        bean.setType(2);

        //工作内容
        String content = etInputContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {

            showToast("请填写工作内容");
            return;
        }
        bean.setField1(content);

        //同事协同
        String join = etInputJion.getText().toString().trim();
        if (TextUtils.isEmpty(join)) {
            showToast("请填目的");
            return;
        }
        bean.setField2(join);

        //遗留问题
        String leave = etInputLegacy.getText().toString().trim();
        if (TextUtils.isEmpty(leave)) {
            showToast("请填写标准");
            return;
        }
        bean.setField3(leave);

//        if (teamAdapter == null || teamAdapter.getData().size() <= 0) {
//            ToastUtil.get().showToast(this, "请填加协同人员");
//            return;
//        }

        StringBuffer stringBuffer = new StringBuffer();

        for (int j = 0; j < teamAdapter.getData().size(); j++) {
            TemplateBean.Preson preson = teamAdapter.getData().get(j);
            if (j == teamAdapter.getData().size() - 1) {
                stringBuffer.append(preson.getName() + "(" + preson.getMobile() + ")");
            } else {
                stringBuffer.append(preson.getName() + "(" + preson.getMobile() + "),");
            }
        }

        bean.setField4(stringBuffer.toString());

        //处理
        String handle = tvDependPerson.getText().toString().trim();
        if (TextUtils.isEmpty(handle)) {
            showToast("请填写期限");
            return;
        }
        bean.setField5(handle);
        String ursStr = PhotoUtils.getPhotoUrl("oa/report/", snplMomentAddPhotos, uploadMap, true);
        bean.setPictures(ursStr);
        // 短视频
        bean.setMp4_path(mUploadKey);
        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap,(isSuccess) -> {
                runOnUiThread(() -> {
                    submitSuccess();
                });
            });
        } else {
            submitSuccess();
        }

    }

    private void submitSuccess() {
        Intent intent = new Intent();
        intent.putExtra("result", bean);
        setResult(3, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_PHOTO_PREVIEW) {
            snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_depend_person:
                PickerSelectUtil.onUpYearMonthDayPicker(this, tvDependPerson);
                break;
            default:
                break;
        }
    }

    private void inputVoice(EditText editText) {
         RxPerm.get(this).voicePerm((isSuccess)->{
            RecognitionManager.getSingleton().startRecognitionWithDialog(AddReportPlanActivity.this, editText);
        });
    }

    @OnClick({R.id.iv_content_voice, R.id.iv_target_voice, R.id.iv_standard_voice, R.id.tv_add_team, R.id.tv_addViedeo, R.id.iv_takevideo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_content_voice:
                inputVoice(etInputContent);
                break;
            case R.id.iv_target_voice:
                inputVoice(etInputJion);
                break;
            case R.id.iv_standard_voice:
                inputVoice(etInputLegacy);
                break;
            case R.id.tv_add_team:
                startActivity(new Intent(AddReportPlanActivity.this, SelectOAPresonActivity.class));
                break;
            // 添加视频
            case R.id.tv_addViedeo:
                Bundle bundle_addvideo = new Bundle();
                bundle_addvideo.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JumpItent.jump(AddReportPlanActivity.this, TakeVideoActivity.class, bundle_addvideo);
                break;
            // 查看视频
            case R.id.iv_takevideo:
                Bundle bundle_takevideo = new Bundle();
                bundle_takevideo.putString("videoPath", mVieoPath);
                JumpItent.jump(AddReportPlanActivity.this, PlayVideoActivity.class, bundle_takevideo);
                break;
        }
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (presonList.size() > 0) {


            Set hashSet = new HashSet();
            hashSet.addAll(teamAdapter.getData());
            hashSet.addAll(presonList);

            if (newPresonList.size() > 0) {
                newPresonList.clear();
            }
            newPresonList.addAll(hashSet);
            teamAdapter.setNewData(newPresonList);
        }

    }

    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            rlThumbnail.setVisibility(View.VISIBLE);
            mVieoPath = takeVdideoMode.getMImagePath();
            mUploadKey = takeVdideoMode.getMKey();
            if (!StrUtil.isEmpty(mVieoPath)) {
                GlideUtil.intoImageView(this,PhotoUtils.getVideoBitmap(mVieoPath),ivTakevideo);
            }
            tvAddViedeo.setText("重新拍摄");
        }
    }
}
