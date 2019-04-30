package net.eanfang.worker.ui.activity.my.specialist;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ExpertVerifySkillBean;
import com.eanfang.model.QualifyCertificafeListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.certification.QualificationAdapter;
import net.eanfang.worker.ui.activity.my.certification.SkillTypeAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SpecialistSkillInfoDetailActivity extends BaseWorkerActivity {
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.tv_ability)
    TextView tvAbility;
    @BindView(R.id.recycler_view_kind)
    RecyclerView recyclerViewBusiness;
    @BindView(R.id.recycler_view_brand)
    RecyclerView recyclerViewBrand;

    // 获取系统类别
    List<BaseDataEntity> systemTypeList = Config.get().getBusinessList(1);
    // 业务类型
    List<BaseDataEntity> brandList = Stream.of(Config.get().getModelList(2)).toList();
    List<BaseDataEntity> myBrandList = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.ll_factory)
    LinearLayout llFactory;
    @BindView(R.id.et_factory_name)
    EditText etFactoryName;
    @BindView(R.id.snpl_impower)
    BGASortableNinePhotoLayout snplImpower;


    private SkillTypeAdapter businessCooperationAddAdapter;
    private SpecialistBrandInfoAdapter brandAdapter;
    private ExpertVerifySkillBean mExpertVerifySkillBean;
    private QualificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_skill_info_detail);
        ButterKnife.bind(this);
        setTitle("技能资质详情");
        setLeftBack();


        for (BaseDataEntity s : systemTypeList) {
            s.setCheck(false);
        }
        for (BaseDataEntity s : brandList) {
            s.setCheck(false);
        }

        getSkillInfo();

    }

    private void getSkillInfo() {

        EanfangHttp.post(UserApi.EXPERT_DETAIL_VERIFY)
                .params("accId", String.valueOf(EanfangApplication.getApplication().getAccId()))
                .execute(new EanfangCallback<ExpertVerifySkillBean>(this, true, ExpertVerifySkillBean.class, bean -> {

                    mExpertVerifySkillBean = bean;


                    List<BaseDataEntity> SystemBusinessList = bean.getBaseData2userList();

                    // 系统类别
                    for (BaseDataEntity checkedS : SystemBusinessList) {
                        if (checkedS.getDataType() == 1) {
                            for (BaseDataEntity s : systemTypeList) {
                                if ((((String.valueOf(s.getDataId()).equals(String.valueOf(checkedS.getDataId())))))) {
                                    s.setCheck(true);
                                    break;
                                }
                            }
                        } else {
                            for (BaseDataEntity checkedB : brandList) {
                                if (String.valueOf(checkedB.getDataId()).equals(String.valueOf(checkedS.getDataId()))) {
                                    checkedB.setCheck(true);
                                    myBrandList.add(checkedB);
                                    break;
                                }
                            }
                        }
                    }
                    runOnUiThread(() -> {
                        initViews();
                        getData();
                    });

                }));
    }

    private void initViews() {

        if (mExpertVerifySkillBean.getExpertVerify().getApproveType() == 1) {
            llFactory.setVisibility(View.VISIBLE);
            etFactoryName.setText(mExpertVerifySkillBean.getExpertVerify().getBrandName());
            List<String> list = new ArrayList<String>();
            list.add(BuildConfig.OSS_SERVER + mExpertVerifySkillBean.getExpertVerify().getImpowerUrl());
            snplImpower.setDeleteDrawableResId(0);
            snplImpower.setData(list);
        }


        tvLimit.setText(GetConstDataUtils.getWorkingYearList().get(mExpertVerifySkillBean.getExpertVerify().getWorkingAge()));
        tvAbility.setText(GetConstDataUtils.getExpertTypeList().get(mExpertVerifySkillBean.getExpertVerify().getApproveType()));

        recyclerViewBusiness.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewBrand.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewBusiness.setNestedScrollingEnabled(false);
        recyclerViewBrand.setNestedScrollingEnabled(false);

        businessCooperationAddAdapter = new SkillTypeAdapter(R.layout.item_cooperation_add, 1);
        brandAdapter = new SpecialistBrandInfoAdapter();

        businessCooperationAddAdapter.bindToRecyclerView(recyclerViewBusiness);
        brandAdapter.bindToRecyclerView(recyclerViewBrand);

        businessCooperationAddAdapter.setNewData(systemTypeList);
        brandAdapter.setNewData(myBrandList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new QualificationAdapter(1);
        adapter.bindToRecyclerView(recyclerView);
    }

    private void getData() {

        JSONObject object = new JSONObject();
        object.put("accId", String.valueOf(EanfangApplication.get().getAccId()));
        object.put("type", "1");
        EanfangHttp.post(UserApi.TECH_WORKER_LIST_QUALIFY)
                .upJson(JsonUtils.obj2String(object))
                .execute(new EanfangCallback<QualifyCertificafeListBean>(this, true, QualifyCertificafeListBean.class) {
                    @Override
                    public void onSuccess(QualifyCertificafeListBean bean) {


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
