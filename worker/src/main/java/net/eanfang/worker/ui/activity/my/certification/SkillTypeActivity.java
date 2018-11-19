package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GrantChange;
import com.eanfang.model.WorkerVerifySkillBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.TechWorkerVerifyEntity;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONObject;

import java.util.ArrayList;
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
    @BindView(R.id.tv_ability)
    TextView tvAbility;
    @BindView(R.id.ll_ability)
    LinearLayout llAbility;
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
        setTitle("技能资质");
        setLeftBack();
         startTransaction(true);
        mStatus = getIntent().getIntExtra("status", -1);
        if (mStatus > 0) {
            getSkillInfo();
        }

        initViews();
//        initData();
    }

    private void initViews() {
        recyclerViewBusiness.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewOs.setLayoutManager(new GridLayoutManager(this, 3));

        businessCooperationAddAdapter = new SkillTypeAdapter(R.layout.item_cooperation_add);
        osCooperationAddAdapter = new SkillTypeAdapter(R.layout.item_cooperation_add);

        businessCooperationAddAdapter.bindToRecyclerView(recyclerViewBusiness);
        osCooperationAddAdapter.bindToRecyclerView(recyclerViewOs);

        businessCooperationAddAdapter.setNewData(businessTypeList);
        osCooperationAddAdapter.setNewData(systemTypeList);
    }


    private void doVerify() {

        String mYear = tvLimit.getText().toString().trim();
        String mAbility = tvAbility.getText().toString().trim();
        if (StringUtils.isEmpty(mYear)) {
            showToast("请选择从业年限");
            return;
        }
        if (StringUtils.isEmpty(mAbility)) {
            showToast("请选择能力等级");
            return;
        }

        grantChange_system.setAddIds(osCooperationAddAdapter.getScheckedId());
        grantChange_system.setDelIds(osCooperationAddAdapter.getUnSCheckedId());


        grantChange_business.setAddIds(businessCooperationAddAdapter.getBcheckedId());
        grantChange_business.setDelIds(businessCooperationAddAdapter.getUnbCheckedId());


        workerInfoBean = new TechWorkerVerifyEntity();
        workerInfoBean.setWorkingLevel(GetConstDataUtils.getWorkingLevelList().indexOf(mAbility));
        workerInfoBean.setWorkingYear(GetConstDataUtils.getWorkingYearList().indexOf(mYear));
        workerInfoBean.setAccId(EanfangApplication.get().getAccId());

        commitData();
    }

    private void commitData() {

        HashMap hashMapData = new HashMap();
        hashMapData.put("techWorkerVerify", workerInfoBean);
        hashMapData.put("workerSysGrantChange", grantChange_system);
        hashMapData.put("workerBizGrantChange", grantChange_business);

        String requestContent = com.alibaba.fastjson.JSONObject.toJSONString(hashMapData);
        EanfangHttp.post(UserApi.TECH_WORKER_VERIFY)
                .upJson(requestContent)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    startAnimActivity(new Intent(this, SkillAreaActivity.class).putExtra("status", mStatus));
                }));


    }

    private void getSkillInfo() {

        EanfangHttp.post(UserApi.TECH_WORKER_DETAIL)
                .params("accId", String.valueOf(EanfangApplication.getApplication().getAccId()))
                .execute(new EanfangCallback<WorkerVerifySkillBean>(this, true, WorkerVerifySkillBean.class, bean -> {
                    List<BaseDataEntity> SystemBusinessList = bean.getBaseData2userList();
                    List<BaseDataEntity> sList = new ArrayList<>();
                    List<BaseDataEntity> bList = new ArrayList<>();
                    sList.addAll(systemTypeList);
                    bList.addAll(businessTypeList);

                    // 系统类别
                    for (BaseDataEntity checkedS : SystemBusinessList) {
                        for (BaseDataEntity s : sList) {
                            if (s.getDataType() == 1 && (s.getDataId() == checkedS.getDataId())) {
                                s.setCheck(true);
                                break;
                            } else {
                                for (BaseDataEntity checkedB : bList) {
                                    if (checkedB.getDataId() == checkedS.getDataId()) {
                                        checkedB.setCheck(true);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    osCooperationAddAdapter.setNewData(sList);
                    businessCooperationAddAdapter.setNewData(bList);
                    fillData(bean);
                }));
    }

    private void fillData(WorkerVerifySkillBean bean) {
        tvLimit.setText(GetConstDataUtils.getWorkingYearList().get(bean.getWorkerVerify().getWorkingYear()));
        tvAbility.setText(GetConstDataUtils.getWorkingLevelList().get(bean.getWorkerVerify().getWorkingLevel()));
    }

    @OnClick(R.id.tv_go)
    public void onViewClicked() {
        doVerify();
    }

    @OnClick({R.id.ll_limit, R.id.ll_ability})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_limit:
                PickerSelectUtil.singleTextPicker(this, "", tvLimit, GetConstDataUtils.getWorkingYearList());
                break;
            case R.id.ll_ability:
                PickerSelectUtil.singleTextPicker(this, "", tvAbility, GetConstDataUtils.getWorkingLevelList());
                break;
        }
    }
}
