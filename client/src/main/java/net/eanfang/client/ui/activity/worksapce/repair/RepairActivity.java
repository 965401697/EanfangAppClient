package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.model.ProjectListBean;
import com.eanfang.model.RepairOpenAreaBean;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.ui.activity.SelectAddressActivity;
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

import java.util.ArrayList;
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
     * 报修地址回调 code
     */
    private final int REPAIR_ADDRESS_CALLBACK_CODE = 1;
    /**
     * 地址信息 省市区 详细地址
     */
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    /**
     * 选择定位
     */
    @BindView(R.id.tv_selectAdress)
    TextView tvSelectAdress;
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
    TextView tv_project_name;
    @BindView(R.id.et_notice)
    EditText et_notice;

    /**
     * 选择时限 Popwindow
     */
    private RepairSelectTimePop repairSelectTimePop;

    private String latitude = "";
    private String longitude = "";
    private String province = "";
    private String city = "";
    private String county = "";
    private String address = "";

    /**
     * 扫码选择技师 传递的值
     */
    private RepairOrderEntity repairOrderEntity;
    private String isScan = "";

    /**
     * 区县ID
     */
    private int mAreaId;

    /**
     * 项目的projectid 默认值
     */
    private int currentIndex = -1;
    private List<ProjectEntity> mProjectList;

    public static void jumpToActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RepairActivity.class);
        ((BaseClientActivity) context).startAnimActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);
        ButterKnife.bind(this);
        initData();
        initListener();
//        initScanRepair();

        getProjectList();
    }


    private void initData() {
        setTitle("我要报修");
        // 扫码 报修
        repairOrderEntity = (RepairOrderEntity) getIntent().getSerializableExtra("repairbean");
        isScan = getIntent().getStringExtra("qrcode");
    }

    private void initListener() {
        tvSelectAdress.setOnClickListener(new MultiClickListener(this, () -> {
            address();
        }));
    }


    /**
     * 选择地址
     */
    public void address() {
//        if (ConnectivityChangeReceiver.isNetConnected(getApplicationContext())) {
        Intent intent = new Intent(this, SelectAddressActivity.class);
        startActivityForResult(intent, REPAIR_ADDRESS_CALLBACK_CODE);
//        } else {
//            showToast("网络异常，请检查网络");
//        }
    }

    /**
     * 字段填写检查约束
     */
    private boolean checkInfo() {

        if ("请选择地址".equals(tvAddress.getText().toString().trim())) {
            showToast("请选择地址");
            return false;
        }
        String placeCode = Config.get().getAreaCodeByName(city, county);
        if (StringUtils.isEmpty(placeCode)) {
            showToast("请重新选择地址");
            return false;
        }
        if (StringUtils.isEmpty(Config.get().getBaseIdByCode(placeCode, 3, Constant.AREA) + "")) {
            showToast("请重新选择地址");
            return false;
        }

        if (StringUtils.isEmpty(etDetailAddress.getText().toString().trim())) {
            showToast("请输入详细地址");
            return false;
        }

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
//        bean.setBugEntityList(beanList);
        bean.setLatitude(latitude);
        bean.setLongitude(longitude);
        bean.setAddress(etDetailAddress.getText().toString().trim());
        bean.setPlaceCode(Config.get().getAreaCodeByName(city, county));
        bean.setPlaceId(Config.get().getBaseIdByCode(bean.getPlaceCode(), 3, Constant.AREA) + "");
        bean.setRepairCompany(tvRepairCompanyName.getText().toString().trim());
        if (currentIndex != -1) {
            bean.setProjectId(String.valueOf(mProjectList.get(currentIndex).getId()));
        }
        bean.setProjectName(et_project_name.getText().toString().trim());
        if (!StringUtils.isEmpty(et_notice.getText().toString().trim())) {
            bean.setRemarkInfo(et_notice.getText().toString().trim());
        }

        bean.setRepairContactPhone(etPhone.getText().toString().trim());
        bean.setRepairContacts(etContact.getText().toString().trim());
        bean.setArriveTimeLimit(GetConstDataUtils.getArriveList().indexOf(tvTime.getText().toString().trim()));
        bean.setOwnerUserId(EanfangApplication.getApplication().getUserId());
        bean.setOwnerCompanyId(EanfangApplication.getApplication().getCompanyId());
        bean.setOwnerTopCompanyId(EanfangApplication.getApplication().getTopCompanyId());
        bean.setOwnerOrgCode(EanfangApplication.getApplication().getOrgCode());
        bean.setSex(mSex);
        bean.setRepairWay(0);
        return bean;
    }

    /**
     * 扫码报修 填充数据
     */
    private RepairOrderEntity doQrFillBean() {
        // 扫码已经选择完技师 ，直接确认
//        repairOrderEntity.setBugEntityList(beanList);
        repairOrderEntity.setLatitude(latitude);
        repairOrderEntity.setLongitude(longitude);
        repairOrderEntity.setAddress(etDetailAddress.getText().toString().trim());
        repairOrderEntity.setPlaceCode(Config.get().getAreaCodeByName(city, county));
        repairOrderEntity.setPlaceId(Config.get().getBaseIdByCode(repairOrderEntity.getPlaceCode(), 3, Constant.AREA) + "");
        repairOrderEntity.setRepairCompany(etCompanyName.getText().toString().trim());
        repairOrderEntity.setRepairContactPhone(etPhone.getText().toString().trim());
        repairOrderEntity.setRepairContacts(etContact.getText().toString().trim());
        repairOrderEntity.setArriveTimeLimit(GetConstDataUtils.getArriveList().indexOf(tvTime.getText().toString().trim()));
        repairOrderEntity.setOwnerUserId(EanfangApplication.getApplication().getUserId());
        repairOrderEntity.setOwnerCompanyId(EanfangApplication.getApplication().getCompanyId());
        repairOrderEntity.setOwnerTopCompanyId(EanfangApplication.getApplication().getTopCompanyId());
        repairOrderEntity.setOwnerOrgCode(EanfangApplication.getApplication().getOrgCode());
        repairOrderEntity.setSex(mSex);
        repairOrderEntity.setRepairWay(0);
        return repairOrderEntity;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REPAIR_ADDRESS_CALLBACK_CODE:
                SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
                Log.e("address", item.toString());
                latitude = item.getLatitude().toString();
                longitude = item.getLongitude().toString();
                province = item.getProvince();
                city = item.getCity();
                county = item.getAddress();
                address = item.getName();
                mAreaId = Config.get().getBaseIdByCode(Config.get().getAreaCodeByName(item.getCity(), item.getAddress()), 3, Constant.AREA);
                tvAddress.setText(province + "-" + city + "-" + county);

                //将选择的地址 取 显示值
                etDetailAddress.setText(address);
                break;
            default:
                break;

        }
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


    @OnClick({R.id.tv_selectAdress, R.id.tv_next})
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
                            intent.putStringArrayListExtra("businessIds", (ArrayList<String>) Stream.of(beanList).map(beans -> Config.get().getBusinessIdByCode(beans.getBusinessThreeCode(), 1) + "").distinct().toList());
                            startActivity(intent);
                        }
                    } else {
                        showToast("所在城市暂未开通服务");
                    }
                }));
    }

}
