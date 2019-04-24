package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ProjectListBean;
import com.eanfang.model.RepairOpenAreaBean;
import com.eanfang.model.reapair.RepairPersonalInfoEntity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.ProjectEntity;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.OrderConfirmActivity;
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.RepairSelectTimePop;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @on 2019年4月15日 16:46:24
 * @email guanluocang
 * @desc 我要报修
 */
public class RepairActivity extends BaseClientActivity {


    /**
     * 到达时限
     */
    @BindView(R.id.tv_time)
    TextView tvTime;
    /**
     * 下一步
     */
    @BindView(R.id.tv_next)
    TextView tvNext;

    @BindView(R.id.iv_left)
    ImageView ivLeft;

    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.et_notice)
    EditText etNotice;
    @BindView(R.id.ll_personalInfoTop)
    LinearLayout llPersonalInfoTop;
    @BindView(R.id.tv_createPersonalInfo)
    TextView tvCreatePersonalInfo;
    @BindView(R.id.ll_noPersonalInfo)
    LinearLayout llNoPersonalInfo;

    /**
     * 选择时限 Popwindow
     */
    private RepairSelectTimePop repairSelectTimePop;

    /**
     * 扫码选择技师 传递的值
     */
    private RepairOrderEntity repairOrderEntity;
    private String isScan = "";

    /**
     * 项目的projectid 默认值
     */
    private int currentIndex = -1;
    private List<ProjectEntity> mProjectList;

    private Long mOwnerOrgId = null;

    /**
     * 个人信息
     */
    private RepairPersonalInfoEntity repairPersonalInfoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
//        getProjectList();
    }

    private void initView() {
        setTitle("我要报修");
        // 扫码报修
        repairOrderEntity = (RepairOrderEntity) getIntent().getSerializableExtra("repairbean");
        isScan = getIntent().getStringExtra("qrcode");
        mOwnerOrgId = getIntent().getLongExtra("mOwnerOrgId", 0);
        repairPersonalInfoEntity = (RepairPersonalInfoEntity) getIntent().getSerializableExtra("infoEntity");
    }

    private void initData() {
        doChekInfo();
    }


    private void initListener() {
    }

    /**
     * 判断是否有无个人信息
     */
    private void doChekInfo() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("accId", EanfangApplication.get().getAccId() + "");
        EanfangHttp.post(NewApiService.REPAIR_PERSONAL_INFO_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<RepairPersonalInfoEntity>(this, true, RepairPersonalInfoEntity.class, bean -> {

                }));
    }


    /**
     * 字段填写检查约束
     */
    private boolean checkInfo() {

        if (StringUtils.isEmpty(tvTime.getText().toString().trim())) {
            showToast("请选择到达时限");
            return false;
        }
        return true;
    }

    /**
     * 填充数据
     */
    private RepairOrderEntity fillBean() {
        RepairOrderEntity bean = new RepairOrderEntity();
        bean.setLatitude(repairPersonalInfoEntity.getLatitude());
        bean.setLongitude(repairPersonalInfoEntity.getLongitude());
        bean.setAddress(repairPersonalInfoEntity.getAddress());
        bean.setPlaceCode(Config.get().getAreaCodeByName(repairPersonalInfoEntity.getCity(), repairPersonalInfoEntity.getCounty()));
        bean.setPlaceId(Config.get().getBaseIdByCode(bean.getPlaceCode(), 3, Constant.AREA) + "");
        bean.setRepairCompany(repairPersonalInfoEntity.getConmpanyName());
        if (currentIndex != -1) {
            bean.setProjectId(String.valueOf(mProjectList.get(currentIndex).getId()));
        }
//        bean.setProjectName(et_project_name.getText().toString().trim());
        if (!StringUtils.isEmpty(etNotice.getText().toString().trim())) {
            bean.setRemarkInfo(etNotice.getText().toString().trim());
        }

        bean.setRepairContactPhone(repairPersonalInfoEntity.getPhone());
//        bean.setRepairContacts(etContact.getText().toString().trim());
        bean.setArriveTimeLimit(GetConstDataUtils.getArriveList().indexOf(tvTime.getText().toString().trim()));
        bean.setOwnerUserId(EanfangApplication.getApplication().getUserId());
        bean.setOwnerCompanyId(EanfangApplication.getApplication().getCompanyId());
        bean.setOwnerTopCompanyId(EanfangApplication.getApplication().getTopCompanyId());
        bean.setOwnerOrgCode(EanfangApplication.getApplication().getOrgCode());
        bean.setSex(repairPersonalInfoEntity.getGender());
        bean.setRepairWay(0);
        return bean;
    }

    /**
     * 扫码报修 填充数据
     */
    private RepairOrderEntity doQrFillBean() {
        repairOrderEntity.setLatitude(repairPersonalInfoEntity.getLatitude());
        repairOrderEntity.setLongitude(repairOrderEntity.getLongitude());
        repairOrderEntity.setAddress(repairPersonalInfoEntity.getAddress());
        repairOrderEntity.setPlaceCode(Config.get().getAreaCodeByName(repairPersonalInfoEntity.getCity(), repairPersonalInfoEntity.getCounty()));
        repairOrderEntity.setPlaceId(Config.get().getBaseIdByCode(repairOrderEntity.getPlaceCode(), 3, Constant.AREA) + "");
        repairOrderEntity.setRepairCompany(repairPersonalInfoEntity.getConmpanyName());
        repairOrderEntity.setRepairContactPhone(repairPersonalInfoEntity.getPhone());
//        repairOrderEntity.setRepairContacts(etContact.getText().toString().trim());
        repairOrderEntity.setArriveTimeLimit(GetConstDataUtils.getArriveList().indexOf(tvTime.getText().toString().trim()));
        repairOrderEntity.setOwnerUserId(EanfangApplication.getApplication().getUserId());
        repairOrderEntity.setOwnerCompanyId(EanfangApplication.getApplication().getCompanyId());
        repairOrderEntity.setOwnerTopCompanyId(EanfangApplication.getApplication().getTopCompanyId());
        repairOrderEntity.setOwnerOrgCode(EanfangApplication.getApplication().getOrgCode());
        repairOrderEntity.setSex(repairPersonalInfoEntity.getGender());
        repairOrderEntity.setRepairWay(0);
        return repairOrderEntity;
    }


    /**
     * 项目列表名称
     */

    private void getProjectList() {

        QueryEntry queryEntry = new QueryEntry();

        EanfangHttp.post(NewApiService.REPAIR_PROJECT_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<ProjectListBean>(RepairActivity.this, false, ProjectListBean.class, (bean) -> {
                    mProjectList = bean.getList();

                }));
    }

    /**
     * 到达时限
     */

    public void onArriveTimeOptionPicker(View view) {
        List<String> timeList = GetConstDataUtils.getArriveList();
        if (timeList != null) {
            repairSelectTimePop = new RepairSelectTimePop(this, timeList, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    tvTime.setText(timeList.get(i).toString());
                    repairSelectTimePop.dismiss();
                }
            });
        }
        repairSelectTimePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                repairSelectTimePop.backgroundAlpha(1.0f);
            }
        });
        repairSelectTimePop.showAtLocation(findViewById(R.id.ll_repair), Gravity.BOTTOM, 0, 0);

//        PickerSelectUtil.singleTextPicker(this, "到达时限", tvTime, );
    }


    @OnClick({R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                goSelectWorker();
                break;
            default:
                break;
        }
    }

    /**
     * 选择技师
     */
    private void goSelectWorker() {
        int mAreaId = Config.get().getBaseIdByCode(Config.get().getAreaCodeByName(repairPersonalInfoEntity.getCity(), repairPersonalInfoEntity.getAddress()), 3, Constant.AREA);
        if (!checkInfo()) {
            return;
        }
        // 查找上门费 判断当前城市是否开通  如果没有开通，提示 您报修的城市暂未开通服务。不能继续选技师
        EanfangHttp.post(RepairApi.GET_REAPIR_PAY_PRICE)
                .params("baseDataId", mAreaId)
                .execute(new EanfangCallback<RepairOpenAreaBean>(RepairActivity.this, true, RepairOpenAreaBean.class, bean -> {
                    /**
                     * status   0：停用，1启用
                     * */
                    if (bean.getStatus() == 1) {
                        // 扫码已经选择完技师 ，直接确认
                        if (!StringUtils.isEmpty(isScan) && isScan.equals("scaning")) {
                            Intent intent_scan = new Intent(RepairActivity.this, OrderConfirmActivity.class);
                            intent_scan.putExtra("bean", doQrFillBean());
                            intent_scan.putExtra("doorFee", bean.getDoorFee());
                            startActivity(intent_scan);
                        } else {
                            Intent intent = new Intent(RepairActivity.this, SelectWorkerActivity.class);
                            intent.putExtra("bean", fillBean());
                            intent.putExtra("doorFee", bean.getDoorFee());
                            intent.putExtra("mOwnerOrgId", mOwnerOrgId);
//                            intent.putStringArrayListExtra("businessIds", (ArrayList<String>) Stream.of(beanList).map(beans -> Config.get().getBusinessIdByCode(beans.getBusinessThreeCode(), 1) + "").distinct().toList());
                            startActivity(intent);
                        }
                    } else {
                        showToast("所在城市暂未开通服务");
                    }
                }));
    }

}
