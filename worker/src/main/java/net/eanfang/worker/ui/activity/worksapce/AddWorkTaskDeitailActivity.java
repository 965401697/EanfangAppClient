package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.model.TemplateBean;
import com.eanfang.model.WorkTaskBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.activity.SelectOAPresonActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.ToastUtil;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.maintenance.MaintenanceTeamAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.hou
 *
 * @on 2017/8/28  15:39
 * @email houzhongzhou@yeah.net
 * @desc 任务明细
 */

public class AddWorkTaskDeitailActivity extends BaseWorkerActivity implements View.OnClickListener {


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

    private WorkTaskBean.WorkTaskDetailsBean bean;
    private Map<String, String> uploadMap = new HashMap<>();

    private MaintenanceTeamAdapter teamAdapter;
    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detial);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        bean = new WorkTaskBean.WorkTaskDetailsBean();
        llOrder.setOnClickListener(this);
        llEndTimes.setOnClickListener(this);
        llFirstFrequency.setOnClickListener(this);
        llSecondFrequency.setOnClickListener(this);
        llThirdFrequency.setOnClickListener(this);
        mPhotosSnpl.setDelegate(new BGASortableDelegate(this));

        setTitle("任务明细");
        setRightTitle("添加");
        setLeftBack();
        setRightTitleOnClickListener((v) -> {
            submit();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //优先级
            case R.id.ll_order:
                PickerSelectUtil.singleTextPicker(this, "", tvOrders, GetConstDataUtils.getInstancyList());
                break;
            //首次响应
            case R.id.ll_first_frequency:
                PickerSelectUtil.singleTextPicker(this, "", tvFirstFrequency, GetConstDataUtils.getFirstLookList());
                break;
            //首次汇报
            case R.id.ll_second_frequency:
                PickerSelectUtil.singleTextPicker(this, "", tvSecondFrequency, GetConstDataUtils.getFirstCallbackList());
                break;
            //跟踪汇报
            case R.id.ll_third_frequency:
                PickerSelectUtil.singleTextPicker(this, "", tvThirdFrequency, GetConstDataUtils.getThenCallbackList());
                break;
            //截至日期
            case R.id.ll_end_times:
                PickerSelectUtil.onUpYearMonthDayPicker(this, tvEndTimes);
                break;
            default:
                break;
        }
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

        String ursStr = PhotoUtils.getPhotoUrl(mPhotosSnpl, uploadMap, true);
        bean.setPictures(ursStr);

        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    Intent intent = new Intent();
                    intent.putExtra("result", bean);
                    setResult(1, intent);
                    finish();
                }
            });
        } else {
            Intent intent = new Intent();
            intent.putExtra("result", bean);
            setResult(1, intent);
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }


    @Override
    protected void onNavigationOnClicked() {
        finishSelf();
    }

    private void inputVoice(EditText editText) {
        PermissionUtils.get(this).getVoicePermission(() -> {
            RecognitionManager.getSingleton().startRecognitionWithDialog(AddWorkTaskDeitailActivity.this, new RecognitionManager.onRecognitionListen() {
                @Override
                public void result(String msg) {
                    editText.setText(msg + "");
                    //获取焦点
                    editText.requestFocus();
                    //将光标定位到文字最后，以便修改
                    editText.setSelection(msg.length());
                }

                @Override
                public void error(String errorMsg) {
                    showToast(errorMsg);
                }
            });
        });
    }

    @OnClick({R.id.iv_content_voice, R.id.iv_target_voice, R.id.iv_standard_voice, R.id.tv_add_team})
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
}
