package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.listener.MultiClickListener;
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

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.maintenance.MaintenanceTeamAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * @on 2017/9/4  11:45
 * @email houzhongzhou@yeah.net
 * @desc 添加发现问题
 */

public class AddReportFindActivity extends BaseWorkerActivity {
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    //    @BindView(R.id.et_input_jion)
//    EditText etInputJion;
    @BindView(R.id.et_input_handle)
    EditText etInputHandle;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.rv_team)
    RecyclerView rvTeam;
    // 照片和短视频
    @BindView(R.id.tv_addViedeo)
    TextView tvAddViedeo;
    @BindView(R.id.iv_takevideo)
    ImageView ivTakevideo;
    @BindView(R.id.rl_thumbnail)
    RelativeLayout rlThumbnail;

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
        setContentView(R.layout.activity_add_find_info);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否要保存
                giveUp();
            }
        });
        setTitle("发现问题");
        setRightTitle("提交");
        bean = new WorkAddReportBean.WorkReportDetailsBean();
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
        setRightTitleOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));


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

    private boolean checkInfo() {
        if (TextUtils.isEmpty(etInputContent.getText().toString().trim())) {

            showToast("请填写工作内容");
            return false;
        }
//        if (teamAdapter == null || teamAdapter.getData().size() <= 0) {
//            ToastUtil.get().showToast(this, "请添加责任人");
//            return false;
//        }
        if (TextUtils.isEmpty(etInputHandle.getText().toString().trim())) {
            showToast("请填写处理措施");
            return false;
        }
        return true;
    }

    private void submit() {

        StringBuffer stringBuffer = new StringBuffer();

        for (int j = 0; j < teamAdapter.getData().size(); j++) {
            TemplateBean.Preson preson = teamAdapter.getData().get(j);
            if (j == teamAdapter.getData().size() - 1) {
                stringBuffer.append(preson.getName() + "(" + preson.getMobile() + ")");
            } else {
                stringBuffer.append(preson.getName() + "(" + preson.getMobile() + "),");
            }
        }

        bean.setType(1);
        //工作内容
        bean.setField1(etInputContent.getText().toString().trim());
        //同事协同
        bean.setField2(stringBuffer.toString());
        //处理
        bean.setField3(etInputHandle.getText().toString().trim());
        String ursStr = PhotoUtils.getPhotoUrl("oa/findquestion/", snplMomentAddPhotos, uploadMap, true);
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
        setResult(2, intent);
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

    private void inputVoice(EditText editText) {
         RxPerm.get(this).voicePerm((isSuccess)->{
            RecognitionManager.getSingleton().startRecognitionWithDialog(AddReportFindActivity.this, editText);
        });
    }

    @OnClick({R.id.iv_content_voice, R.id.iv_handle_voice, R.id.tv_add_team, R.id.tv_addViedeo, R.id.iv_takevideo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_content_voice:
                inputVoice(etInputContent);
                break;
            case R.id.iv_handle_voice:
                inputVoice(etInputHandle);
                break;
            case R.id.tv_add_team:
                startActivity(new Intent(AddReportFindActivity.this, SelectOAPresonActivity.class));
                break;
            // 添加视频
            case R.id.tv_addViedeo:
                Bundle bundle_addvideo = new Bundle();
                bundle_addvideo.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JumpItent.jump(AddReportFindActivity.this, TakeVideoActivity.class, bundle_addvideo);
                break;
            // 查看视频
            case R.id.iv_takevideo:
                Bundle bundle_takevideo = new Bundle();
                bundle_takevideo.putString("videoPath", mVieoPath);
                JumpItent.jump(AddReportFindActivity.this, PlayVideoActivity.class, bundle_takevideo);
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


    /**
     * 监听 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            giveUp();
        }
        return false;
    }

    /**
     * 放弃新建汇报
     */
    private void giveUp() {
        new TrueFalseDialog(this, "系统提示", "是否放弃本条发现问题？", () -> {
            finish();
        }).showDialog();
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

