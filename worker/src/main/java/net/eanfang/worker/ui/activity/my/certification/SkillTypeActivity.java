package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GrantChange;
import com.eanfang.model.SystypeBean;
import com.eanfang.model.WorkerInfoBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
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
    private SystypeBean byNetGrant_system = new SystypeBean();
    // 业务类别
    private GrantChange grantChange_business = new GrantChange();
    private SystypeBean byNetGrant_business = new SystypeBean();


    private WorkerInfoBean workerInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_type);
        ButterKnife.bind(this);
        setTitle("技能资质");
        setLeftBack();

        initViews();
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

        // 系统类别
        List<Integer> checkList_system = Stream.of(systemTypeList)
                .filter(beans -> beans.isCheck() == true && Stream.of(byNetGrant_business.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() == 0)
                .map(beans -> beans.getDataId()).toList();
        List<Integer> unCheckList_system = Stream.of(systemTypeList)
                .filter(beans -> beans.isCheck() == false && Stream.of(byNetGrant_business.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() > 0)
                .map(beans -> beans.getDataId()).toList();

        grantChange_system.setAddIds(checkList_system);
        grantChange_system.setDelIds(unCheckList_system);
        if ((unCheckList_system.size() == 0) && (checkList_system.size() == 0) && (byNetGrant_business.getList().size() <= 0)) {
            showToast("请选择一种系统类别");
            return;
        }

        // 业务类别
        List<Integer> checkList_business = Stream.of(businessTypeList)
                .filter(beans -> beans.isCheck() == true && Stream.of(byNetGrant_business.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() == 0)
                .map(beans -> beans.getDataId())
                .toList();
        List<Integer> unCheckList_business = Stream.of(businessTypeList)
                .filter(beans -> beans.isCheck() == false && Stream.of(byNetGrant_business.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() > 0)
                .map(beans -> beans.getDataId()).toList();

        grantChange_business.setAddIds(checkList_business);
        grantChange_business.setDelIds(unCheckList_business);

        if ((unCheckList_business.size() == 0) && (checkList_business.size() == 0) && (byNetGrant_business.getList().size() <= 0)) {
            showToast("请至少选择一种业务类别");
            return;
        }


        workerInfoBean = new WorkerInfoBean();
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
//                    jump();

                    startAnimActivity(new Intent(this, SkillAreaActivity.class));
                }));


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
