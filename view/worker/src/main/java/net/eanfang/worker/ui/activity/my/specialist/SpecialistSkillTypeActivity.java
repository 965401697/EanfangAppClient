package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ExpertVerifySkillBean;
import com.eanfang.model.GrantChange;
import com.eanfang.model.sys.BaseDataEntity;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.ExpertsCertificationEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.certification.SkillTypeAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SpecialistSkillTypeActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.ll_limit)
    LinearLayout llLimit;
    @BindView(R.id.recycler_brand)
    RecyclerView recyclerViewBrand;
    @BindView(R.id.recycler_view_kind)
    RecyclerView recyclerViewOs;
    @BindView(R.id.et_factory_name)
    EditText etFactoryName;
    @BindView(R.id.ll_factory)
    LinearLayout llFactory;
    @BindView(R.id.snpl_impower)
    BGASortableNinePhotoLayout snplImpower;
    @BindView(R.id.btn_auth_type_expert)
    Button mBtnAuthTypeExpert;
    @BindView(R.id.btn_auth_type_service)
    Button mBtnAuthTypeService;
    private SpecialistBrandAdapter brandAdapter;
    private SkillTypeAdapter osCooperationAddAdapter;
    private final int ADD_BRAND_REQESET_CODE = 100;
    private final int IMPOWER_PHOTO_REQUST = 101;
    private final int IMPOWER_PHOTO_CHOSE = 102;
    private HashMap<String, String> uploadMap = new HashMap<>();
    private ArrayList<String> imPowerList = new ArrayList<>();
    // 获取系统类别

    List<BaseDataEntity> systemTypeList = Config.get().getBusinessList(1);

    //品牌

    private List<BaseDataEntity> mDeviceBrandList = Stream.of(Config.get().getModelList(2)).toList();

    //品牌默认数据

    List<BaseDataEntity> brandIdList = new ArrayList<>();

    // 系统类别

    private GrantChange grantChange_system = new GrantChange();

    // 品牌

    private GrantChange grantChange_brand = new GrantChange();

    //选择的品牌id

    List<Integer> mCheckedBrandIds = new ArrayList<>();

    //删除的品牌id

    List<Integer> mDeldBrandIds = new ArrayList<>();

    private ExpertsCertificationEntity expertsCertificationEntity;
    private int mStatus;
    private String impowerUrl;
    /**
     * 认证类型
     */
    private String mAbility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_skill_type);
        ButterKnife.bind(this);
        setTitle("专家认证");
        setLeftBack();
        startTransaction(true);
        mStatus = getIntent().getIntExtra("status", -1);
        initViews();
        getSkillInfo();

    }

    private void initViews() {
        recyclerViewBrand.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerViewOs.setLayoutManager(new GridLayoutManager(this, 4));
        brandAdapter = new SpecialistBrandAdapter(this);
        osCooperationAddAdapter = new SkillTypeAdapter(R.layout.item_cooperation_add);
        recyclerViewBrand.setAdapter(brandAdapter);
        osCooperationAddAdapter.bindToRecyclerView(recyclerViewOs);
        // TODO: 2018/11/30  今天集合的对象引用 有待优化
        for (BaseDataEntity s : systemTypeList) {
            s.setCheck(false);
        }
        osCooperationAddAdapter.setNewData(systemTypeList);

        brandAdapter.setOnCheckClickListener(new SpecialistBrandAdapter.onCheckClickListener() {
            @Override
            public void onAddClickListener() {
                Intent intent = new Intent(SpecialistSkillTypeActivity.this, AddBrandActivity.class);
                intent.putExtra("list", (Serializable) brandAdapter.getData());
                intent.putExtra("data", (Serializable) mDeviceBrandList);
                startActivityForResult(intent, ADD_BRAND_REQESET_CODE);
            }

            @Override
            public void onSubClickListener(BaseDataEntity data) {
                if (brandIdList.contains(data)) {
                    mDeldBrandIds.add(data.getDataId());
                } else {
                    mCheckedBrandIds.remove(data.getDataId());
                }
                data.setCheck(false);
                brandAdapter.remove(data);
            }
        });
        snplImpower.setDelegate(new BGASortableDelegate(this, IMPOWER_PHOTO_CHOSE, IMPOWER_PHOTO_REQUST));
        mBtnAuthTypeExpert.setText(GetConstDataUtils.getExpertTypeList().get(0));
        if (GetConstDataUtils.getExpertTypeList().size() > 1) {
            mBtnAuthTypeService.setText(GetConstDataUtils.getExpertTypeList().get(1));
        }
        mBtnAuthTypeExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAuthType(true);
                mAbility = mBtnAuthTypeExpert.getText().toString().trim();
            }
        });
        mBtnAuthTypeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAuthType(false);
                mAbility = mBtnAuthTypeService.getText().toString().trim();
            }
        });

        mAbility = mBtnAuthTypeExpert.getText().toString().trim();
        chooseAuthType(true);
    }


    private void doVerify() {
        if (llFactory.getVisibility() == View.VISIBLE) {
            if (StringUtils.isEmpty(etFactoryName.getText().toString().trim())) {
                showToast("请输入厂商名称");
                return;
            }
            impowerUrl = PhotoUtils.getPhotoUrl("", snplImpower, uploadMap, false);
            if (StringUtils.isEmpty(impowerUrl)) {
                showToast("请选择授权书");
                return;
            }
        }
        grantChange_system.setAddIds(osCooperationAddAdapter.getScheckedId());
        grantChange_system.setDelIds(osCooperationAddAdapter.getUnSCheckedId());
        grantChange_brand.setAddIds(mCheckedBrandIds);
        grantChange_brand.setDelIds(mDeldBrandIds);
        expertsCertificationEntity = new ExpertsCertificationEntity();
        expertsCertificationEntity.setApproveType(GetConstDataUtils.getExpertTypeList().indexOf(mAbility));
        expertsCertificationEntity.setAccId(EanfangApplication.get().getAccId());
        if (llFactory.getVisibility() == View.VISIBLE) {
            expertsCertificationEntity.setBrandName(etFactoryName.getText().toString().trim());
            expertsCertificationEntity.setImpowerUrl(impowerUrl);
            if (uploadMap.size() != 0) {
                OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                    @Override
                    public void onOssSuccess() {
                        super.onOssSuccess();
                        commitData();
                    }
                });
            } else {
                commitData();
            }

        } else {
            commitData();
        }
    }

    private void commitData() {
        HashMap hashMapData = new HashMap();
        hashMapData.put("expertsCertification", expertsCertificationEntity);
        hashMapData.put("expertSysGrantChange", grantChange_system);
        hashMapData.put("expertModelGrantChange", grantChange_brand);
        String requestContent = com.alibaba.fastjson.JSONObject.toJSONString(hashMapData);
        EanfangHttp.post(UserApi.EXPERT_VERIFY).upJson(requestContent).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
            osCooperationAddAdapter.getScheckedId().clear();
            osCooperationAddAdapter.getUnSCheckedId().clear();
            startActivity(new Intent(this, SpecialistSkillCertificafeListActivity.class).putExtra("status", mStatus));
            finish();
        }));
    }

    private void getSkillInfo() {
        EanfangHttp.post(UserApi.EXPERT_DETAIL_VERIFY).params("accId", String.valueOf(EanfangApplication.getApplication().getAccId())).execute(new EanfangCallback<ExpertVerifySkillBean>(this, true, ExpertVerifySkillBean.class, bean -> {
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
                        for (BaseDataEntity checkedB : mDeviceBrandList) {
                            if (String.valueOf(checkedB.getDataId()).equals(String.valueOf(checkedS.getDataId()))) {
                                checkedB.setCheck(true);
                                brandIdList.add(checkedB);
                                break;
                            }
                        }
                    }
                }

                osCooperationAddAdapter.setNewData(systemTypeList);
                brandAdapter.setNewData(brandIdList);

                fillData(bean);
            }
        }));
    }

    private void fillData(ExpertVerifySkillBean bean) {
        if (bean.getExpertVerify().getApproveType() != null) {
            if (bean.getExpertVerify().getApproveType() == 1) {
                etFactoryName.setText(bean.getExpertVerify().getBrandName());
                imPowerList.add(BuildConfig.OSS_SERVER + bean.getExpertVerify().getImpowerUrl());
                snplImpower.setData(imPowerList);
                llFactory.setVisibility(View.VISIBLE);
                impowerUrl = bean.getExpertVerify().getImpowerUrl();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADD_BRAND_REQESET_CODE) {
            List<BaseDataEntity> list = (ArrayList<BaseDataEntity>) data.getSerializableExtra("list");
            for (BaseDataEntity b : list) {
                if (!brandIdList.contains(b)) {
                    mCheckedBrandIds.add(b.getDataId());
                }
            }
            brandAdapter.setData(list);
        }

        switch (requestCode) {
            case IMPOWER_PHOTO_CHOSE:
                snplImpower.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            default:
        }

    }


    @OnClick(R.id.tv_go)
    public void onViewClicked() {
        doVerify();
//        startAnimActivity(new Intent(this, SpecialistSkillCertificafeListActivity.class).putExtra("status", mStatus));
    }

    private void chooseAuthType(boolean isExport) {
        mBtnAuthTypeExpert.setSelected(isExport);
        mBtnAuthTypeService.setSelected(!isExport);
        //厂商售后显示专家隐藏
        llFactory.setVisibility(isExport ? View.GONE : View.VISIBLE);
    }
}
