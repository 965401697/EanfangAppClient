package net.eanfang.worker.ui.activity.worksapce.oa.workreport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.model.TemplateBean;
import com.eanfang.model.WorkAddReportBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
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

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

/**
 * 创建工作汇报
 */
public class CreationWorkReportActivity extends BaseWorkerActivity {

    @BindView(R.id.et_task_name)
    TextView etTaskName;
    @BindView(R.id.rv_complete_work)
    RecyclerView rvCompleteWork;
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    @BindView(R.id.et_input_legacy)
    EditText etInputLegacy;
    @BindView(R.id.et_input_reason)
    EditText etInputReason;
    @BindView(R.id.et_input_handle)
    EditText etInputHandle;
    @BindView(R.id.rv_team_work)
    RecyclerView rvTeamWork;
    @BindView(R.id.tv_addViedeo_work)
    TextView tvAddViedeoWork;
    @BindView(R.id.snpl_photos_work)
    BGASortableNinePhotoLayout snplPhotosWork;
    @BindView(R.id.iv_takevideo_work)
    SimpleDraweeView ivTakevideoWork;
    @BindView(R.id.rl_thumbnail_work)
    RelativeLayout rlThumbnailWork;
    @BindView(R.id.tv_complete_work)
    TextView tvCompleteWork;
    @BindView(R.id.tv_cancle_work)
    TextView tvCancleWork;
    @BindView(R.id.ll_complete_work)
    LinearLayout llCompleteWork;

    //    private MaintenanceTeamAdapter teamAdapter;
    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();
    private Map<String, String> workUploadMap = new HashMap<>();


    private boolean isTrue = true;
    /**
     * 视频上传key
     */
    private String mUploadKey = "";
    /**
     * 视频路径
     */
    private String mVieoPath = "";
    private List<WorkAddReportBean.WorkReportDetailsBean> mWorkDataList;
    private WorkCompleteAdapter mWorkCompleteAdapter;
    private OAPersonAdaptet oaPersonAdaptet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_work_report);
        ButterKnife.bind(this);
        setTitle("新建汇报");
        setLeftBack();

        initViews();
    }

    private void initViews() {

        rvTeamWork.setLayoutManager(new GridLayoutManager(this, 5));

//        teamAdapter = new MaintenanceTeamAdapter();
//        teamAdapter.bindToRecyclerView(rvTeamWork);
//        teamAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                teamAdapter.remove(position);
//            }
//        });

        oaPersonAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>());
        rvTeamWork.setAdapter(oaPersonAdaptet);

        snplPhotosWork.setDelegate(new BGASortableDelegate(this));
    }


    /**
     * 完成工作
     *
     * @param view
     */
    @OnClick({R.id.ll_report_type, R.id.tv_add_complete, R.id.iv_content_voice, R.id.iv_question_voice, R.id.iv_reason_voice, R.id.iv_handle_voice, R.id.tv_addViedeo_work, R.id.iv_takevideo_work, R.id.tv_complete_work, R.id.tv_cancle_work})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_report_type:
                PickerSelectUtil.singleTextPicker(this, "", etTaskName, GetConstDataUtils.getWorkReportTypeList());
                break;
            case R.id.tv_add_complete:
                llCompleteWork.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_content_voice:
                inputVoice(etInputContent);
                break;
            case R.id.iv_question_voice:
                inputVoice(etInputLegacy);
                break;
            case R.id.iv_reason_voice:
                inputVoice(etInputReason);
                break;
            case R.id.iv_handle_voice:
                inputVoice(etInputHandle);
                break;
//            case R.id.tv_add_team_work:
//                startActivity(new Intent(CreationWorkReportActivity.this, SelectOAPresonActivity.class));
//                break;
            case R.id.tv_addViedeo_work:
                Bundle bundle_addvideo = new Bundle();
                bundle_addvideo.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JumpItent.jump(CreationWorkReportActivity.this, TakeVideoActivity.class, bundle_addvideo);
                break;
            // 查看视频
            case R.id.iv_takevideo_work:
                Bundle bundle_takevideo = new Bundle();
                bundle_takevideo.putString("videoPath", mVieoPath);
                JumpItent.jump(CreationWorkReportActivity.this, PlayVideoActivity.class, bundle_takevideo);
                break;
            case R.id.tv_complete_work:
                llCompleteWork.setVisibility(View.GONE);
                break;
            case R.id.tv_cancle_work:
                //保存 并 下一条
                addDataToWrok();
                break;
        }
    }


    private void clearWorkData() {
        etInputContent.setText("");
        etInputLegacy.setText("");
        etInputReason.setText("");
        etInputHandle.setText("");
        oaPersonAdaptet.getData().clear();
        oaPersonAdaptet.notifyDataSetChanged();
        workUploadMap.clear();
        snplPhotosWork.setData(new ArrayList<String>());

    }

    private void addDataToWrok() {

        if (!checkWorkInfo()) return;

        WorkAddReportBean.WorkReportDetailsBean bean = new WorkAddReportBean.WorkReportDetailsBean();

        StringBuffer stringBuffer = new StringBuffer();

        for (int j = 0; j < oaPersonAdaptet.getData().size(); j++) {
            TemplateBean.Preson preson = oaPersonAdaptet.getData().get(j);
            if (j == oaPersonAdaptet.getData().size() - 1) {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName());
            } else {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName() + ",");
            }
        }

        bean.setType(0);

        //工作内容
        bean.setField1(etInputContent.getText().toString().trim());

        //同事协同
        bean.setField2(stringBuffer.toString());

        //遗留问题
        bean.setField3(etInputLegacy.getText().toString().trim());

        //未完成原因
        bean.setField4(etInputReason.getText().toString().trim());

        //处理
        bean.setField5(etInputHandle.getText().toString().trim());
        String ursStr = PhotoUtils.getPhotoUrl("oa/report/", snplPhotosWork, workUploadMap, true);
        bean.setPictures(ursStr);
        // 短视频
        bean.setMp4_path(mUploadKey);
        //设置布局类型
        bean.setItemType(1);

        isTrue = true;//重置状态

        if (workUploadMap.size() != 0) {


            OSSUtils.initOSS(this).asyncPutImages(workUploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    if (isTrue) {
                        subWorkData(bean);
                        isTrue = false;
                    }
                }

            });
        } else {
            subWorkData(bean);
        }
    }

    private void subWorkData(WorkAddReportBean.WorkReportDetailsBean bean) {
        if (mWorkDataList == null) {
            mWorkDataList = new ArrayList<>();
        }

        mWorkDataList.add(bean);

        if (mWorkCompleteAdapter == null) {
            rvCompleteWork.setLayoutManager(new LinearLayoutManager(this));
            mWorkCompleteAdapter = new WorkCompleteAdapter(mWorkDataList);
            mWorkCompleteAdapter.bindToRecyclerView(rvCompleteWork);
            mWorkCompleteAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    WorkAddReportBean.WorkReportDetailsBean b = (WorkAddReportBean.WorkReportDetailsBean) adapter.getData().get(position);
                    if (view.getId() == R.id.tv_look) {
                        b.setItemType(2);
                        adapter.notifyItemChanged(position);
                    } else if (view.getId() == R.id.tv_delete) {
                        adapter.remove(position);
                    } else if (view.getId() == R.id.tv_pack) {
                        b.setItemType(1);
                        adapter.notifyItemChanged(position);
                    }
                }
            });
        } else {
            mWorkCompleteAdapter.setNewData(mWorkDataList);
        }

        clearWorkData();//清空数据
    }

    private boolean checkWorkInfo() {
        if (TextUtils.isEmpty(etInputContent.getText().toString().trim())) {

            showToast("请填写工作内容");
            return false;
        }

        return true;
    }


    private void inputVoice(EditText editText) {
        PermissionUtils.get(this).getVoicePermission(() -> {
            RecognitionManager.getSingleton().startRecognitionWithDialog(CreationWorkReportActivity.this, editText);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            snplPhotosWork.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_PHOTO_PREVIEW) {
            snplPhotosWork.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (presonList.size() > 0) {


            Set hashSet = new HashSet();
            hashSet.addAll(oaPersonAdaptet.getData());
            hashSet.addAll(presonList);

            if (newPresonList.size() > 0) {
                newPresonList.clear();
            }
            newPresonList.addAll(hashSet);
            oaPersonAdaptet.setNewData(newPresonList);
        }

    }

    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            rlThumbnailWork.setVisibility(View.VISIBLE);
            mVieoPath = takeVdideoMode.getMImagePath();
            mUploadKey = takeVdideoMode.getMKey();
            if (!StringUtils.isEmpty(mVieoPath)) {
                ivTakevideoWork.setImageBitmap(PhotoUtils.getVideoThumbnail(mVieoPath, 100, 100, MINI_KIND));
            }
            tvAddViedeoWork.setText("重新拍摄");
        }
    }

}
