package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.activity.SelectOAPresonActivity;
import com.eanfang.ui.base.BaseEvent;
import com.eanfang.util.ETimeUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.ToastUtil;
import com.yaf.base.entity.ShopBughandleMaintenanceConfirmEntity;
import com.yaf.base.entity.ShopMaintenanceExamDeviceEntity;
import com.yaf.base.entity.ShopMaintenanceExamResultEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 维保处理界面
 */
public class MaintenanceHandleActivity extends BaseWorkerActivity {
    //添加检查结果
    private final int ADD_HANDLE_RESULT = 101;
    //修改检查结果
    private final int EDIT_HANDLE_RESULT = 104;
    //修改重點處理設備
    private final int EXAM_DEVICE_RESULT = 102;
    //修改重點處理設備
    private final int ADD_PHOTO = 103;

    @BindView(R.id.ll_check_result)
    LinearLayout llCheckResult;
    @BindView(R.id.rv_check_result)
    RecyclerView rvCheckResult;
    @BindView(R.id.ll_device_handle)
    LinearLayout llDeviceHandle;
    @BindView(R.id.rv_device_handle)
    RecyclerView rvDeviceHandle;
    @BindView(R.id.tv_conclusion)
    TextView tvConclusion;
    @BindView(R.id.ll_conclusion)
    LinearLayout llConclusion;
    @BindView(R.id.et_suggest)
    EditText etSuggest;
    @BindView(R.id.et_question)
    EditText etQuestion;
    @BindView(R.id.ll_photo)
    LinearLayout llPhoto;
    @BindView(R.id.rv_team)
    RecyclerView rvTeam;
    @BindView(R.id.cb_video)
    CheckBox cbVideo;
    @BindView(R.id.cb_time)
    CheckBox cbTime;
    @BindView(R.id.cb_print)
    CheckBox cbPrint;
    @BindView(R.id.cb_host)
    CheckBox cbHost;
    @BindView(R.id.iv_device_handle)
    ImageView ivDeviceHandle;
    @BindView(R.id.tv_device_handle)
    TextView tvDeviceHandle;


    private long mId;
    private ArrayList<ShopMaintenanceExamDeviceEntity> examResultList;
    private Date mSignTime;
    private MaintenanceHandeCheckAdapter maintenanceHandeCheckAdapter;
    private ShopMaintenanceExamDeviceEntity examDeviceEntity;
    private int examDeviceEntityPosition;
    private MaintenanceHandleEditAdapter handleEditAdapter;

    private List<CheckBox> checkBoxList = new ArrayList<>();
    private ShopBughandleMaintenanceConfirmEntity confirmEntity;
    private MaintenanceTeamAdapter teamAdapter;
    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();
    private int examResultEntityPosition;
    private ShopMaintenanceExamResultEntity examResultEntity;

    /**
     * 扫码看设备
     */
    private String mType = "";
    private ShopMaintenanceExamResultEntity mScanExamResultEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_handle);
        ButterKnife.bind(this);
        setTitle("维保处理");
        setLeftBack();

        initData();
        initViews();
    }

    private void initData() {
        mId = getIntent().getLongExtra("orderId", 0);
        examResultList = (ArrayList<ShopMaintenanceExamDeviceEntity>) getIntent().getSerializableExtra("list");
 
        mSignTime = (Date) getIntent().getSerializableExtra("signTime");

        mType = getIntent().getStringExtra("type");
        mScanExamResultEntity = (ShopMaintenanceExamResultEntity) getIntent().getSerializableExtra("bean");

        checkBoxList.add(cbVideo);
        checkBoxList.add(cbTime);
        checkBoxList.add(cbPrint);
        checkBoxList.add(cbHost);

    }

    private void initViews() {
        //互动冲突的解决
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        rvCheckResult.setLayoutManager(linearLayoutManager1);
        rvDeviceHandle.setLayoutManager(linearLayoutManager2);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvTeam.setLayoutManager(manager);

        handleEditAdapter = new MaintenanceHandleEditAdapter(R.layout.item_maintenance_empasis_device_handle);
        handleEditAdapter.bindToRecyclerView(rvDeviceHandle);
        if (examResultList != null && examResultList.size() > 0) {
            handleEditAdapter.setNewData(examResultList);
        } else {
            tvDeviceHandle.setVisibility(View.VISIBLE);
            ivDeviceHandle.setVisibility(View.GONE);
        }

        handleEditAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                examDeviceEntityPosition = position;
                examDeviceEntity = (ShopMaintenanceExamDeviceEntity) adapter.getData().get(position);
                Intent intent = new Intent(MaintenanceHandleActivity.this, MaintenanceHandleEditActivity.class);
                intent.putExtra("bean", examDeviceEntity);
                startActivityForResult(intent, EXAM_DEVICE_RESULT);
            }
        });

        maintenanceHandeCheckAdapter = new MaintenanceHandeCheckAdapter(R.layout.item_maintenance_check_add, 0);
        maintenanceHandeCheckAdapter.bindToRecyclerView(rvCheckResult);
        maintenanceHandeCheckAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                examResultEntityPosition = position;
                examResultEntity = (ShopMaintenanceExamResultEntity) adapter.getData().get(position);
                Intent intent = new Intent(MaintenanceHandleActivity.this, MaintenanceAddCheckResultActivity.class);
                intent.putExtra("bean", examResultEntity);
                startActivityForResult(intent, EDIT_HANDLE_RESULT);
            }
        });

        maintenanceHandeCheckAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_delete) {
                    adapter.getData().remove(position);
                    adapter.notifyItemChanged(position);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 添加检查结果
            if (requestCode == ADD_HANDLE_RESULT) {
                doAddHandleResult((ShopMaintenanceExamResultEntity) data.getSerializableExtra("bean"));
            } else if (requestCode == EDIT_HANDLE_RESULT) {//修改已有检查结果
                ShopMaintenanceExamResultEntity resultEntity = (ShopMaintenanceExamResultEntity) data.getSerializableExtra("bean");
                maintenanceHandeCheckAdapter.setData(examResultEntityPosition, resultEntity);
                maintenanceHandeCheckAdapter.notifyItemChanged(examResultEntityPosition);
            } else if (requestCode == EXAM_DEVICE_RESULT) {
                ShopMaintenanceExamDeviceEntity deviceEntity = (ShopMaintenanceExamDeviceEntity) data.getSerializableExtra("bean");
                examDeviceEntity.setMaintenanceDetailEntity(deviceEntity.getMaintenanceDetailEntity());
                handleEditAdapter.notifyItemChanged(examDeviceEntityPosition);
            } else if (requestCode == ADD_PHOTO) {
                confirmEntity = (ShopBughandleMaintenanceConfirmEntity) data.getSerializableExtra("bean");
            }
        }

    }

    private void doAddHandleResult(ShopMaintenanceExamResultEntity data) {
        maintenanceHandeCheckAdapter.addData(data);
    }

    @OnClick({R.id.ll_add, R.id.ll_device_handle, R.id.ll_conclusion, R.id.ll_photo, R.id.tv_add_team, R.id.tv_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_add:
                startActivityForResult(new Intent(MaintenanceHandleActivity.this, MaintenanceAddCheckResultActivity.class), ADD_HANDLE_RESULT);
                break;
            case R.id.ll_device_handle:
                if (rvDeviceHandle.getVisibility() == View.GONE) {
                    rvDeviceHandle.setVisibility(View.VISIBLE);
                } else {
                    rvDeviceHandle.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_conclusion:
                //系统类别

                PickerSelectUtil.singleTextPicker(this, "", tvConclusion, GetConstDataUtils.getMaintainOsRuntimeList());

                break;
            case R.id.ll_photo:


                Intent intent = new Intent(MaintenanceHandleActivity.this, MeintenancePhotoActivity.class);
                if (confirmEntity != null) {
                    intent.putExtra("bean", confirmEntity);
                }
                startActivityForResult(intent, ADD_PHOTO);

                break;
            case R.id.tv_add_team:
//                startActivity(new Intent(MaintenanceHandleActivity.this, SelectOrganizationActivity.class));
                startActivity(new Intent(MaintenanceHandleActivity.this, SelectOAPresonActivity.class));
                break;
            case R.id.tv_sub:
                doSubData();
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (presonList.size() > 0) {

            if (teamAdapter == null) {
                teamAdapter = new MaintenanceTeamAdapter();
                teamAdapter.bindToRecyclerView(rvTeam);
            }

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

    private void doSubData() {

        boolean isNo = true;


        for (ShopMaintenanceExamDeviceEntity examDeviceEntity : handleEditAdapter.getData()) {
            if (examDeviceEntity.getMaintenanceDetailEntity() == null) {
                isNo = false;
                ToastUtil.get().showToast(this, "请完善重点设备处理");
                break;
            }
        }

        if (!isNo) return;


        boolean isAllCheck = true;
        int i = 0;
        for (CheckBox checkBox : checkBoxList) {
            if (!checkBox.isChecked()) {
                i++;
                if (i == 4) {
                    isAllCheck = false;
                    Log.e("zzw", "i=" + i + "==" + isAllCheck);
                }
            }
        }

        if (!isAllCheck) {
            ToastUtil.get().showToast(this, "请选择电视监控和报警监控");
            return;
        }

        if (TextUtils.isEmpty(tvConclusion.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请选择处理结论");
            return;
        }

        if (TextUtils.isEmpty(etSuggest.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请输入维保建议");
            return;
        }

        if (TextUtils.isEmpty(etQuestion.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请输入遗留问题");
            return;
        }
        if (confirmEntity == null) {
            ToastUtil.get().showToast(this, "请选择照片");
            return;
        }


        if (teamAdapter == null || teamAdapter.getData().size() <= 0) {
            ToastUtil.get().showToast(this, "请选择团队成员");
            return;
        }

        //================================================================================

        JSONObject jsonObject = new JSONObject();
        try {
            // TODO: 2018/11/8 对象为空

            jsonObject.put("id", mId);


            if (maintenanceHandeCheckAdapter.getData() != null) {
                JSONArray examResultEntity = new JSONArray();

                for (ShopMaintenanceExamResultEntity examResult : maintenanceHandeCheckAdapter.getData()) {
                    examResult.setShopMaintenanceOrderId(mId);
                    examResultEntity.add(examResult);
                }
                jsonObject.put("examResultEntityList", examResultEntity);
            }

//===========================================================================================


            JSONArray examDeviceArray = new JSONArray();

            for (ShopMaintenanceExamDeviceEntity deviceEntity : handleEditAdapter.getData()) {

                ShopMaintenanceExamDeviceEntity shopMaintenanceExamDeviceEntity = new ShopMaintenanceExamDeviceEntity();
                shopMaintenanceExamDeviceEntity.setMaintenanceDetailEntity(deviceEntity.getMaintenanceDetailEntity());
                shopMaintenanceExamDeviceEntity.getMaintenanceDetailEntity().setShopMaintenanceExamDeviceId(deviceEntity.getId());
                shopMaintenanceExamDeviceEntity.setShopMaintenanceOrderId(deviceEntity.getShopMaintenanceOrderId());

                examDeviceArray.add(shopMaintenanceExamDeviceEntity);

            }
            jsonObject.put("examDeviceEntityList", examDeviceArray);
//======================================================================================

            confirmEntity.setIsVcrStoreDayNormal(cbVideo.isChecked() ? 1 : 0);
            confirmEntity.setIsTimeRight(cbTime.isChecked() ? 1 : 0);
            confirmEntity.setIsAlarmPrinter(cbPrint.isChecked() ? 1 : 0);
            confirmEntity.setIsMachineDataRemote(cbHost.isChecked() ? 1 : 0);


            confirmEntity.setStatus(GetConstDataUtils.getMaintainOsRuntimeList().indexOf(tvConclusion.getText().toString().trim()));
            confirmEntity.setTeamWorker(etSuggest.getText().toString().trim());

            confirmEntity.setMaintenanceSuggest(etSuggest.getText().toString().trim());
            confirmEntity.setLeftoverProblem(etQuestion.getText().toString().trim());


            StringBuffer stringBuffer = new StringBuffer();

            for (int j = 0; j < teamAdapter.getData().size(); j++) {
                TemplateBean.Preson preson = teamAdapter.getData().get(j);
                if (j == teamAdapter.getData().size() - 1) {
                    stringBuffer.append(preson.getName() + "(" + preson.getMobile() + ")");
                } else {
                    stringBuffer.append(preson.getName() + "(" + preson.getMobile() + "),");
                }
            }


            if (mSignTime == null) {
                confirmEntity.setWorkHour("0小时0分钟");
            } else {
                Date finishDay = GetDateUtils.getDateNow();
                long day = GetDateUtils.getTimeDiff(finishDay, mSignTime, "day");
                long hours = GetDateUtils.getTimeDiff(finishDay, mSignTime, "hours");
                long minutes = GetDateUtils.getTimeDiff(finishDay, mSignTime, "minutes");
                if (day < 0) {
                    day = 0;
                }
                if (hours < 0) {
                    hours = 0;
                }
                if (minutes < 0) {
                    minutes = 0;
                }
                confirmEntity.setWorkHour((day * 24 + hours) + "小时" + minutes + "分钟");
            }


            confirmEntity.setTeamWorker(stringBuffer.toString());

            confirmEntity.setShopMaintenanceOrderId(mId);

            confirmEntity.setOverTime(GetDateUtils.getDate(ETimeUtils.getTimeByYearMonthDayHourMinSec(new Date(System.currentTimeMillis()))));
            jsonObject.put("confirmEntity", confirmEntity);


            EanfangHttp.post(NewApiService.MAINTENANCE_DISPOSE)
                    .upJson(JsonUtils.obj2String(jsonObject))
                    .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {

                        showToast("提交成功");

                        EventBus.getDefault().post(new BaseEvent());//刷新item

                        finish();

                    }));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
