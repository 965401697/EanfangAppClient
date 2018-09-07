package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.model.LoginBean;
import com.eanfang.model.RepairOpenAreaBean;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.CustDeviceEntity;
import com.yaf.base.entity.RepairBugEntity;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.OrderConfirmActivity;
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
import net.eanfang.client.ui.adapter.ToRepairAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.RepairSelectTimePop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  13:33
 * @email houzhongzhou@yeah.net
 * @desc 我要报修
 */
public class RepairActivity extends BaseClientActivity implements RadioGroup.OnCheckedChangeListener {

    //报修地址回调 code
    private final int REPAIR_ADDRESS_CALLBACK_CODE = 1;
    //添加故障明细回调
    private final int ADD_TROUBLE_CALLBACK_CODE = 2;

    // 公司名称
    @BindView(R.id.tv_repairCompanyName)
    TextView tvRepairCompanyName;
    @BindView(R.id.et_repairCompanyName)
    EditText etCompanyName;
    // 用户名
    @BindView(R.id.tv_repairUserName)
    TextView tvRepairUserName;
    // 用户电话
    @BindView(R.id.tv_repairUserPhone)
    TextView tvRepairUserPhone;
    //地址信息 省市区 详细地址
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    // 选择定位
    @BindView(R.id.tv_selectAdress)
    TextView tvSelectAdress;
    // 联系人行么
    @BindView(R.id.et_contact)
    EditText etContact;
    // 性别
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    // 联系人电话
    @BindView(R.id.et_phone)
    EditText etPhone;
    // 到达时限
    @BindView(R.id.tv_time)
    TextView tvTime;
    // 故障明细数量
    @BindView(R.id.tv_faultNum)
    TextView tvFaultNum;
    //添加故障
    @BindView(R.id.btn_add_trouble)
    TextView btnAddTrouble;
    // 故障列表
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    // 下一步
    @BindView(R.id.tv_next)
    TextView tvNext;

    @BindView(R.id.iv_left)
    ImageView ivLeft;

    //选择时限 Popwindow
    private RepairSelectTimePop repairSelectTimePop;
    private List<RepairBugEntity> beanList = new ArrayList<>();

    private String latitude = "";
    private String longitude = "";
    private String province = "";
    private String city = "";
    private String county = "";
    private String address = "";
    private ToRepairAdapter evaluateAdapter = null;

    // 扫码选择技师 传递的值
    private RepairOrderEntity repairOrderEntity;
    private String isScan = "";
    // 判断是否是个人还是公司用户
    private boolean mIsCompany = true;

    // 选择性别 默认是男
    private int mSex = 1;
    // 区县ID
    private int mAreaId;
    private Long mOwnerOrgId;

    // 扫码报修
    private CustDeviceEntity mDeviceBean;

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
        initAdapter();
    }

    private void initData() {
        setTitle("我要报修");
        LoginBean user = EanfangApplication.getApplication().getUser();
        String name;
        //个人客户 单位名称自己输入
        if (user.getAccount().getDefaultUser().getCompanyId() <= 0) {
            name = user.getAccount().getRealName();
            mIsCompany = true;// 个人用户为true
            tvRepairCompanyName.setVisibility(View.GONE);
            etCompanyName.setVisibility(View.VISIBLE);
        } else {
            mIsCompany = false;// 公司用户为false
            name = user.getAccount().getDefaultUser().getCompanyEntity().getOrgName();
            tvRepairCompanyName.setVisibility(View.VISIBLE);
            etCompanyName.setVisibility(View.GONE);
            tvRepairCompanyName.setText(name);
        }
        if (StringUtils.isEmpty(etContact.getText())) {
            tvRepairUserName.setText(user.getAccount().getRealName());
            tvRepairUserPhone.setText(user.getAccount().getMobile());
            etContact.setText(user.getAccount().getRealName());
            etPhone.setText(user.getAccount().getMobile());
        }

        // 扫码 报修
        repairOrderEntity = (RepairOrderEntity) getIntent().getSerializableExtra("repairbean");
        isScan = getIntent().getStringExtra("qrcode");
        mDeviceBean = (CustDeviceEntity) getIntent().getSerializableExtra("scan_repair");
    }

    private void initListener() {
        tvSelectAdress.setOnClickListener(new MultiClickListener(this, () -> {
            address();
        }));
        rgSex.setOnCheckedChangeListener(this);
    }

    private void initAdapter() {
        evaluateAdapter = new ToRepairAdapter(R.layout.item_trouble, beanList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                beanList.remove(position);
                evaluateAdapter.notifyDataSetChanged();
            }
        });
        rvList.setAdapter(evaluateAdapter);
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

    /**
     * 放弃报修
     */
    private void giveUp() {
        new TrueFalseDialog(this, "系统提示", "是否放弃报修？", () -> {
            finish();
        }).showDialog();
    }

    /**
     * 故障明细
     */
    public void addTouble() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("beanList", (Serializable) beanList);
        bundle.putSerializable("scan_repair", mDeviceBean);
        JumpItent.jump(this, AddTroubleActivity.class, bundle, ADD_TROUBLE_CALLBACK_CODE);
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

        if (!mIsCompany) {
            if (StringUtils.isEmpty(tvRepairCompanyName.getText().toString().trim())) {
                showToast("请输入报修单位");
                return false;
            }
        } else {
            if (StringUtils.isEmpty(etCompanyName.getText().toString().trim())) {
                showToast("请输入报修单位");
                return false;
            }
        }

        if (StringUtils.isEmpty(etContact.getText())) {
            showToast("请输入联系人姓名");
            return false;
        }
        if (StringUtils.isEmpty(etPhone.getText())) {
            showToast("请输入联系人手机号");
            return false;
        }
        //电话号码是否符合格式
        if (!StringUtils.isMobileString(etPhone.getText().toString().trim())) {
            showToast("请输入正确手机号");
            return false;
        }
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

        if (StringUtils.isEmpty(tvTime.getText())) {
            showToast("请选择到达时限");
            return false;
        }
        if (beanList.isEmpty()) {
            showToast("请填写故障明细");
            return false;
        }
        return true;
    }

    /**
     * 填充数据
     */
    private RepairOrderEntity fillBean() {
        RepairOrderEntity bean = new RepairOrderEntity();
        bean.setBugEntityList(beanList);
        bean.setLatitude(latitude);
        bean.setLongitude(longitude);
        bean.setAddress(etDetailAddress.getText().toString().trim());
        bean.setPlaceCode(Config.get().getAreaCodeByName(city, county));
        bean.setPlaceId(Config.get().getBaseIdByCode(bean.getPlaceCode(), 3, Constant.AREA) + "");
        if (mIsCompany) {
            bean.setRepairCompany(etCompanyName.getText().toString().trim());
        } else {
            bean.setRepairCompany(tvRepairCompanyName.getText().toString().trim());
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
        repairOrderEntity.setBugEntityList(beanList);
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

    /**
     * 监听 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            giveUp();
        }
        return false;
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
            case ADD_TROUBLE_CALLBACK_CODE:
                RepairBugEntity repairBugEntity = (RepairBugEntity) data.getSerializableExtra("bean");
                beanList.add(repairBugEntity);
                evaluateAdapter.notifyDataSetChanged();
                mOwnerOrgId = data.getLongExtra("mOwnerOrgId", 0);
//                initData();
                break;
            default:
                break;

        }
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


    @OnClick({R.id.iv_left, R.id.tv_selectAdress, R.id.btn_add_trouble, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                giveUp();
                break;
            case R.id.btn_add_trouble:
                addTouble();
                break;
            case R.id.tv_next:
                goSelectWorker();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_man:
                mSex = 1;
                break;
            case R.id.rb_woman:
                mSex = 0;
                break;
        }
    }
}
