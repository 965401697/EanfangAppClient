package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.QualifyCertificafeListBean;
import com.eanfang.biz.model.WorkerVerifySkillBean;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SkillInfoDetailActivity extends BaseWorkerActivity {
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.tv_ability)
    TextView tvAbility;
    @BindView(R.id.recycler_view_classfiy)
    RecyclerView recyclerViewBusiness;
    @BindView(R.id.recycler_view_kind)
    RecyclerView recyclerViewOs;

    /**
     * 获取系统类别
     */
    List<BaseDataEntity> systemTypeList = Config.get().getBusinessList(1);
    /**
     * 业务类型
     */
    List<BaseDataEntity> businessTypeList = Config.get().getServiceList(1);
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    private SkillTypeAdapter businessCooperationAddAdapter;
    private SkillTypeAdapter osCooperationAddAdapter;
    private WorkerVerifySkillBean mWorkerVerifySkillBean;
    private QualificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_info_detail);
        ButterKnife.bind(this);
        setTitle("技能资质详情");
        setLeftBack();
        getSkillInfo();

    }

    private void getSkillInfo() {

        EanfangHttp.post(UserApi.TECH_WORKER_DETAIL_V2)
                .params("accId", String.valueOf(WorkerApplication.get().getAccId()))
                .execute(new EanfangCallback<WorkerVerifySkillBean>(this, true, WorkerVerifySkillBean.class, bean -> {
                    mWorkerVerifySkillBean = bean;
                    getData();
                    List<BaseDataEntity> SystemBusinessList = bean.getBaseData2userList();
                    // 系统类别
                    for (BaseDataEntity checkedS : SystemBusinessList) {
                        for (BaseDataEntity s : systemTypeList) {
                            if (s.getDataType() == 1 && (s.getDataId().equals(checkedS.getDataId()))) {
                                s.setCheck(true);
                                break;
                            } else {
                                for (BaseDataEntity checkedB : businessTypeList) {
                                    if (checkedB.getDataId().equals(checkedS.getDataId())) {
                                        checkedB.setCheck(true);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }));
    }

    private void initViews() {

        tvLimit.setText(GetConstDataUtils.getWorkingYearList().get(mWorkerVerifySkillBean.getWorkerVerify().getWorkingYear()));
        if (mWorkerVerifySkillBean.getWorkerVerify() != null && mWorkerVerifySkillBean.getWorkerVerify().getWorkingLevel() != null) {
            tvAbility.setText(GetConstDataUtils.getWorkingLevelList().get(mWorkerVerifySkillBean.getWorkerVerify().getWorkingLevel()));
        }

        recyclerViewBusiness.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerViewOs.setLayoutManager(new GridLayoutManager(this, 4));

        businessCooperationAddAdapter = new SkillTypeAdapter(R.layout.item_cooperation_add, 1);
        osCooperationAddAdapter = new SkillTypeAdapter(R.layout.item_cooperation_add, 1);

        businessCooperationAddAdapter.bindToRecyclerView(recyclerViewBusiness);
        osCooperationAddAdapter.bindToRecyclerView(recyclerViewOs);

        businessCooperationAddAdapter.setNewData(businessTypeList);
        osCooperationAddAdapter.setNewData(systemTypeList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QualificationAdapter(1);
        adapter.bindToRecyclerView(recyclerView);
    }

    @OnClick(R.id.tv_area)
    public void onViewClicked() {
        startActivity(new Intent(SkillInfoDetailActivity.this, AreaSelectionActivity.class).putExtra("isLook", true));
    }

    private void getData() {
        QueryEntry queryEntry = new QueryEntry();

        queryEntry.getEquals().put("accId", String.valueOf(WorkerApplication.get().getAccId()));
        queryEntry.getEquals().put("type", "0");
        EanfangHttp.post(UserApi.TECH_WORKER_LIST_QUALIFY)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<QualifyCertificafeListBean>(this, true, QualifyCertificafeListBean.class) {
                    @Override
                    public void onSuccess(QualifyCertificafeListBean bean) {
                        initViews();
                        if (bean.getList().size() > 0) {
                            adapter.setNewData(bean.getList());
                        }
                    }

                    @Override
                    public void onNoData(String message) {
                    }

                    @Override
                    public void onCommitAgain() {
                    }
                });
    }
}
