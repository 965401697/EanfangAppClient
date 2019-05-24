package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.GrantChange;
import com.eanfang.biz.model.WorkerVerifySkillBean;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.TechWorkerVerifyEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 技能资质
 */
public class SkillTypeActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.ll_limit)
    LinearLayout llLimit;


    @BindView(R.id.recycler_view_classfiy)
    RecyclerView recyclerViewBusiness;
    @BindView(R.id.recycler_view_kind)
    RecyclerView recyclerViewOs;

    private SkillTypeAdapter businessCooperationAddAdapter;
    private SkillTypeAdapter osCooperationAddAdapter;


    // 获取系统类别
    List<BaseDataEntity> systemTypeList = Config.get().getBusinessList(1);
    // 业务类型
    List<BaseDataEntity> businessTypeList = Config.get().getServiceList(1);

    // 系统类别
    private GrantChange grantChange_system = new GrantChange();
    // 业务类别
    private GrantChange grantChange_business = new GrantChange();

    private TechWorkerVerifyEntity workerInfoBean;
    private int mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_type);
        ButterKnife.bind(this);
        setTitle("服务认证");
        setLeftBack();
        startTransaction(true);
        mStatus = getIntent().getIntExtra("status", -1);
        initViews();
        getSkillInfo();

    }

    private void initViews() {
        recyclerViewBusiness.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerViewOs.setLayoutManager(new GridLayoutManager(this, 4));

        businessCooperationAddAdapter = new SkillTypeAdapter(R.layout.item_cooperation_add);
        osCooperationAddAdapter = new SkillTypeAdapter(R.layout.item_cooperation_add);

        businessCooperationAddAdapter.bindToRecyclerView(recyclerViewBusiness);
        osCooperationAddAdapter.bindToRecyclerView(recyclerViewOs);
        // TODO: 2018/11/30  今天集合的对象引用 有待优化
        for (BaseDataEntity b : businessTypeList) {
            b.setCheck(false);
        }
        for (BaseDataEntity s : systemTypeList) {
            s.setCheck(false);
        }
        businessCooperationAddAdapter.setNewData(businessTypeList);
        osCooperationAddAdapter.setNewData(systemTypeList);
    }


    private void doVerify() {

        String mYear = tvLimit.getText().toString().trim();

        Log.i("mYear++mAbility", mYear + "++");

        if (StringUtils.isEmpty(mYear)) {
            showToast("请选择从业年限");
            return;
        }

        grantChange_system.setAddIds(osCooperationAddAdapter.getScheckedId());
        grantChange_system.setDelIds(osCooperationAddAdapter.getUnSCheckedId());
        grantChange_business.setAddIds(businessCooperationAddAdapter.getBcheckedId());
        grantChange_business.setDelIds(businessCooperationAddAdapter.getUnbCheckedId());
        workerInfoBean = new TechWorkerVerifyEntity();
        workerInfoBean.setWorkingYear(GetConstDataUtils.getWorkingYearList().indexOf(mYear));
        workerInfoBean.setAccId(WorkerApplication.get().getAccId());
        commitData();
    }

    private void commitData() {

        HashMap hashMapData = new HashMap();
        hashMapData.put("techWorkerVerify", workerInfoBean);
        hashMapData.put("workerSysGrantChange", grantChange_system);
        hashMapData.put("workerBizGrantChange", grantChange_business);

        String requestContent = com.alibaba.fastjson.JSONObject.toJSONString(hashMapData);
        Log.d("555654555555", requestContent);
        EanfangHttp.post(UserApi.TECH_WORKER_VERIFY).upJson(requestContent).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
            if (osCooperationAddAdapter.getScheckedId() != null) {
                osCooperationAddAdapter.getScheckedId().clear();
            }
            if (osCooperationAddAdapter.getUnSCheckedId() != null) {
                osCooperationAddAdapter.getUnSCheckedId().clear();
            }
            if (businessCooperationAddAdapter.getBcheckedId() != null) {
                businessCooperationAddAdapter.getBcheckedId().clear();
            }
            if (businessCooperationAddAdapter.getUnbCheckedId() != null) {
                businessCooperationAddAdapter.getUnbCheckedId().clear();
            }
            startAnimActivity(new Intent(this, SkillAreaActivity.class).putExtra("status", mStatus));
            finish();
        }));


    }

    private void getSkillInfo() {

        EanfangHttp.post(UserApi.TECH_WORKER_DETAIL).params("accId", String.valueOf(WorkerApplication.get().getAccId())).execute(new EanfangCallback<WorkerVerifySkillBean>(this, true, WorkerVerifySkillBean.class, bean -> {
            if (bean != null) {
                List<BaseDataEntity> SystemBusinessList = bean.getBaseData2userList();
                // 系统类别
                for (BaseDataEntity checkedS : SystemBusinessList) {
                    if (checkedS.getDataType() == 1) {
                        for (BaseDataEntity s : systemTypeList) {
                            if ((String.valueOf(s.getDataId()).equals(String.valueOf(checkedS.getDataId())))) {
                                s.setCheck(true);
                                break;
                            }
                        }
                    } else {
                        for (BaseDataEntity checkedB : businessTypeList) {
                            if (checkedB.getDataId() == checkedS.getDataId()) {
                                checkedB.setCheck(true);
                                break;
                            }
                        }
                    }
                }
                osCooperationAddAdapter.setNewData(systemTypeList);
                businessCooperationAddAdapter.setNewData(businessTypeList);
                fillData(bean);
            }
        }));
    }

    private void fillData(WorkerVerifySkillBean bean) {
        if (bean.getWorkerVerify().getWorkingYear() != null) {
            Log.d("555654", bean.getWorkerVerify().getWorkingYear().toString() + " ");
            Log.d("5556545", GetConstDataUtils.getWorkingYearList().get(bean.getWorkerVerify().getWorkingYear()) + " ");
            tvLimit.setText(GetConstDataUtils.getWorkingYearList().get(bean.getWorkerVerify().getWorkingYear()));
        }
    }

    @OnClick(R.id.tv_go)
    public void onViewClicked() {
        doVerify();
    }

    @OnClick({R.id.ll_limit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_limit:
                PickerSelectUtil.singleTextPicker(this, "", tvLimit, GetConstDataUtils.getWorkingYearList());
                break;
            default:
        }
    }
}
