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
import com.eanfang.biz.model.bean.WorkTaskBean;

import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.activity.SelectOAPresonActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.ToastUtil;

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
 * @on 2017/8/28  15:39
 * @email houzhongzhou@yeah.net
 * @desc 任务明细
 */

public class AddWorkTaskDeitailActivity extends BaseClientActivity {


    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_orders)
    TextView tvOrders;
    @BindView(R.id.ll_order)
    LinearLayout llOrder;
    @BindView(R.id.tv_end_times)
    TextView tvEndTimes;
    @BindView(R.id.ll_end_times)
    LinearLayout llEndTimes;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.et_goal)
    EditText etGoal;
    @BindView(R.id.et_standard)
    EditText etStandard;
    //    @BindView(R.id.et_worker)
//    EditText etWorker;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.tv_first_frequency)
    TextView tvFirstFrequency;
    @BindView(R.id.ll_first_frequency)
    LinearLayout llFirstFrequency;
    @BindView(R.id.tv_second_frequency)
    TextView tvSecondFrequency;
    @BindView(R.id.ll_second_frequency)
    LinearLayout llSecondFrequency;
    @BindView(R.id.tv_third_frequency)
    TextView tvThirdFrequency;
    @BindView(R.id.ll_third_frequency)
    LinearLayout llThirdFrequency;
    @BindView(R.id.rv_team)
    RecyclerView rvTeam;
    // 照片和短视频
    @BindView(R.id.tv_addViedeo)
    TextView tvAddViedeo;
    @BindView(R.id.iv_takevideo)
    ImageView ivTakevideo;
    @BindView(R.id.rl_thumbnail)
    RelativeLayout rlThumbnail;

    private WorkTaskBean.WorkTaskDetailsBean bean;
    private Map<String, String> uploadMap = new HashMap<>();
    private static int RESULT_CODE = 200;

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
        setContentView(R.layout.activity_task_detial);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        bean = new WorkTaskBean.WorkTaskDetailsBean();
        setTitle("任务明细");
        setRightTitle("添加");
        setLeftBack();
        setRightTitleOnClickListener((v) -> {
            submit();
        });
        llOrder.setOnClickListener((view) -> PickerSelectUtil.singleTextPicker(this, "", tvOrders, GetConstDataUtils.getInstancyList()));
        llEndTimes.setOnClickListener((view) -> PickerSelectUtil.onUpYearMonthDayPicker(this, tvEndTimes));
        llFirstFrequency.setOnClickListener((view) -> PickerSelectUtil.singleTextPicker(this, "", tvFirstFrequency, GetConstDataUtils.getFirstLookList()));
        llSecondFrequency.setOnClickListener((view) -> PickerSelectUtil.singleTextPicker(this, "", tvSecondFrequency, GetConstDataUtils.getFirstCallbackList()));
        llThirdFrequency.setOnClickListener((view) -> PickerSelectUtil.singleTextPicker(this, "", tvThirdFrequency, GetConstDataUtils.getThenCallbackList()));
        mPhotosSnpl.setDelegate(new BGASortableDelegate(this));

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
        String title = etTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("标题不能为空");
            return;
        }

        String orders = tvOrders.getText().toString().trim();
        if (TextUtils.isEmpty(orders)) {
            showToast("请选择优先级");
            return;
        }

        String first = tvFirstFrequency.getText().toString().trim();
        if (TextUtils.isEmpty(first)) {
            showToast("请选择首次响应时间");
            return;
        }

        String second = tvSecondFrequency.getText().toString().trim();
        if (TextUtils.isEmpty(second)) {
            showToast("请选择首次汇报时间");
            return;
        }
        String third = tvThirdFrequency.getText().toString().trim();
        if (TextUtils.isEmpty(third)) {
            showToast("请选择跟踪汇报时间");
            return;
        }

        String endtime = tvEndTimes.getText().toString().trim();
        if (TextUtils.isEmpty(endtime)) {
            showToast("请选择截止时间");
            return;
        }

        String conment = etComment.getText().toString().trim();
        if (TextUtils.isEmpty(conment)) {
            showToast("请输入内容");
            return;
        }

        String goal = etGoal.getText().toString().trim();
        if (TextUtils.isEmpty(goal)) {
            showToast("请输入目的");
            return;
        }

        String standard = etStandard.getText().toString().trim();
        if (TextUtils.isEmpty(standard)) {
            showToast("请输入标准");
            return;
        }


        if (teamAdapter == null || teamAdapter.getData().size() <= 0) {
            ToastUtil.get().showToast(this, "请填加参与人员");
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();

        for (int j = 0; j < teamAdapter.getData().size(); j++) {
            TemplateBean.Preson preson = teamAdapter.getData().get(j);
            if (j == teamAdapter.getData().size() - 1) {
                stringBuffer.append(preson.getName() + "(" + preson.getMobile() + ")");
            } else {
                stringBuffer.append(preson.getName() + "(" + preson.getMobile() + "),");
            }
        }


        bean.setTitle(title);
        bean.setInstancyLevel(GetConstDataUtils.getInstancyList().indexOf(orders));
        bean.setFirst_look(GetConstDataUtils.getFirstLookList().indexOf(first));
        bean.setFirst_callback(GetConstDataUtils.getFirstCallbackList().indexOf(second));
        bean.setThen_callback(GetConstDataUtils.getThenCallbackList().indexOf(third));
        bean.setEnd_time(endtime);
        bean.setInfo(conment);
        bean.setPurpose(goal);
        bean.setCriterion(standard);
        bean.setJoinPerson(stringBuffer.toString());
        // 短视频
        bean.setMp4_path(mUploadKey);
        String ursStr = PhotoUtils.getPhotoUrl("oa/task/", mPhotosSnpl, uploadMap, true);
        bean.setPictures(ursStr);

        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap,(isSuccess) -> {
                Intent intent = new Intent();
                intent.putExtra("result", bean);
                setResult(RESULT_CODE, intent);
                finish();
            });
        } else {
            Intent intent = new Intent();
            intent.putExtra("result", bean);
            setResult(RESULT_CODE, intent);
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }


    @Override
    protected void onNavigationOnClicked() {
        finishSelf();
    }

    private void inputVoice(EditText editText) {
         RxPerm.get(this).voicePerm((isSuccess)->{
            RecognitionManager.getSingleton().startRecognitionWithDialog(AddWorkTaskDeitailActivity.this, editText);
        });
    }

    @OnClick({R.id.iv_content_voice, R.id.iv_target_voice, R.id.iv_standard_voice, R.id.tv_add_team, R.id.tv_addViedeo, R.id.iv_takevideo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_content_voice:
                inputVoice(etComment);
                break;
            case R.id.iv_target_voice:
                inputVoice(etGoal);
                break;
            case R.id.iv_standard_voice:
                inputVoice(etStandard);
                break;
            case R.id.tv_add_team:
                startActivity(new Intent(AddWorkTaskDeitailActivity.this, SelectOAPresonActivity.class));
                break;
            // 添加视频
            case R.id.tv_addViedeo:
                Bundle bundle_addvideo = new Bundle();
                bundle_addvideo.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JumpItent.jump(AddWorkTaskDeitailActivity.this, TakeVideoActivity.class, bundle_addvideo);
                break;
            // 查看视频
            case R.id.iv_takevideo:
                Bundle bundle_takevideo = new Bundle();
                bundle_takevideo.putString("videoPath", mVieoPath);
                JumpItent.jump(AddWorkTaskDeitailActivity.this, PlayVideoActivity.class, bundle_takevideo);
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
