package net.eanfang.client.ui.activity.worksapce.online;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.ExpertVerifySkillBean;
import com.eanfang.biz.model.GrantChange;
import com.yaf.base.entity.ExpertsCertificationEntity;
import com.eanfang.biz.model.entity.BaseDataEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.my.AddBrandActivity;
import net.eanfang.client.ui.activity.my.SkillTypeAdapter;
import net.eanfang.client.ui.activity.my.SpecialistBrandAdapter;
import net.eanfang.client.ui.activity.worksapce.repair.SelectDeviceTypeActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindExpertActivity extends BaseClientActivity implements View.OnClickListener {
    @BindView(R.id.recycler_view_kind)
    RecyclerView recyclerViewOs;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.choose_brand)
    RelativeLayout chooseBrand;
    @BindView(R.id.pin_name)
    TextView pinName;
    // 设备信息 RequestCode
    private static final int REQUEST_FAULTDEVICEINFO = 100;
//    @BindView(R.id.my_information)
//    Button myInformation;
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
    private SharedPreferences sp;
    private String team;
    private String brandID;
    private SharedPreferences.Editor edit;
    private SharedPreferences basis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_find_expert);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        chooseBrand.setOnClickListener(this);
        basis = getSharedPreferences("basis", MODE_PRIVATE);
        pinName.setText(basis.getString("DataName", "海康威视 >"));
        setTitle("找专家");
        setLeftBack();
        startTransaction(true);
        mStatus = getIntent().getIntExtra("status", -1);
        initViews();
        getSkillInfo();
        tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindExpertActivity.this, SelectDeviceTypeActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        pinName.setText(basis.getString("DataName", "海康威视") + "  >  ");
    }

    private void initViews() {
        recyclerViewOs.setLayoutManager(new GridLayoutManager(this, 3));
        brandAdapter = new SpecialistBrandAdapter(this);
        osCooperationAddAdapter = new SkillTypeAdapter(R.layout.item_cooperation_add);
        osCooperationAddAdapter.bindToRecyclerView(recyclerViewOs);
        for (BaseDataEntity s : systemTypeList) {
            s.setCheck(false);
        }
        osCooperationAddAdapter.setNewData(systemTypeList);
        brandAdapter.setOnCheckClickListener(new SpecialistBrandAdapter.onCheckClickListener() {
            @Override
            public void onAddClickListener() {
                Intent intent = new Intent(FindExpertActivity.this, AddBrandActivity.class);
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
    }


    private void doVerify() {
        grantChange_system.setAddIds(osCooperationAddAdapter.getScheckedId());
        grantChange_system.setDelIds(osCooperationAddAdapter.getUnSCheckedId());
        grantChange_brand.setAddIds(mCheckedBrandIds);
        grantChange_brand.setDelIds(mDeldBrandIds);
        expertsCertificationEntity = new ExpertsCertificationEntity();
        expertsCertificationEntity.setAccId(ClientApplication.get().getAccId());
    }

    private void getSkillInfo() {

        EanfangHttp.post(UserApi.EXPERT_DETAIL_VERIFY)
                .params("accId", String.valueOf(ClientApplication.get().getAccId()))
                .execute(new EanfangCallback<ExpertVerifySkillBean>(this, true, ExpertVerifySkillBean.class, (ExpertVerifySkillBean bean) -> {
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
        if (bean.getExpertVerify().getApproveType() == 1) {
            imPowerList.add(BuildConfig.OSS_SERVER + bean.getExpertVerify().getImpowerUrl());
            impowerUrl = bean.getExpertVerify().getImpowerUrl();
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
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(FindExpertActivity.this, ManufacturerAfterSaleActivity.class);
        intent.putExtra("find", 2);
        startActivity(intent);
    }

    @OnClick({ R.id.tv_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.my_information:
//                Intent intent = new Intent(FindExpertActivity.this, MyInformationActivity.class);
//                intent.putExtra("find", 2);
//                startActivity(intent);
//                break;
            case R.id.tv_go:
                doVerify();
                break;
        }
    }

}
