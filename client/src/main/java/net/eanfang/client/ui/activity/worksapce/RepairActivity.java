package net.eanfang.client.ui.activity.worksapce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.model.LoginBean;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.RepairBugEntity;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.ToRepairAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  13:33
 * @email houzhongzhou@yeah.net
 * @desc 我要报修
 */

public class RepairActivity extends BaseClientActivity {

    //报修地址回调 code
    private final int REPAIR_ADDRESS_CALLBACK_CODE = 1;
    //添加故障明细回调
    private final int ADD_TROUBLE_CALLBACK_CODE = 2;

    @BindView(R.id.et_company_name)
    EditText etCompanyName;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.btn_add_trouble)
    TextView btnAddTrouble;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private List<RepairBugEntity> beanList = new ArrayList<>();


    private String latitude;
    private String longitude;
    private String province;
    private String city;
    private String county;
    private String address;
    private ToRepairAdapter evaluateAdapter = null;


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
        setTitle("我要报修");
        registerListener();
        initAdapter();
    }


    private void registerListener() {
        setRightTitle("下一步");

        setRightTitleOnClickListener(v -> {
            goSelectWorker();
        });
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(v -> giveUp());
        btnAddTrouble.setOnClickListener(v -> addTouble());
        llAddress.setOnClickListener(new MultiClickListener(this, () -> {
            address();
        }));
    }


    private void goSelectWorker() {
        if (!checkInfo()) {
            return;
        }
        Intent intent = new Intent(RepairActivity.this, SelectWorkerActivity.class);
        intent.putExtra("bean", fillBean());
        intent.putStringArrayListExtra("businessIds", (ArrayList<String>) Stream.of(beanList).map(bean -> Config.get().getBusinessIdByCode(bean.getBusinessThreeCode(), 1) + "").toList());
        startActivity(intent);
    }

    private boolean checkInfo() {

        if (StringUtils.isEmpty(etCompanyName.getText().toString().trim())) {
            showToast("请输入报修单位");
            return false;
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
        if (StringUtils.isEmpty(tvAddress.getText().toString().trim())) {
            showToast("请选择地址");
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
            showToast("请填写明细");
            return false;
        }
        return true;
    }


    private RepairOrderEntity fillBean() {
        RepairOrderEntity bean = new RepairOrderEntity();
        bean.setBugEntityList(beanList);
        bean.setLatitude(latitude);
        bean.setLongitude(longitude);
        bean.setAddress(etDetailAddress.getText().toString().trim());
        bean.setPlaceCode(Config.get().getAreaCodeByName(city, county));
        bean.setPlaceId(Config.get().getBaseIdByCode(bean.getPlaceCode(), 3, Constant.AREA) + "");
        bean.setRepairCompany(etCompanyName.getText().toString().trim());

        bean.setRepairContactPhone(etContact.getText().toString().trim());
        bean.setRepairContacts(etPhone.getText().toString().trim());
        bean.setArriveTimeLimit(GetConstDataUtils.getArriveList().indexOf(tvTime.getText().toString().trim()));
        bean.setOwnerUserId(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getUserId());
        bean.setOwnerTopCompanyId(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getTopCompanyId());
        bean.setOwnerOrgCode(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getDepartmentEntity().getOrgCode());
        bean.setRepairWay(0);
        return bean;
    }

    private void giveUp() {
        new TrueFalseDialog(this, "系统提示", "是否放弃报修？", () -> {
            finish();
        }).showDialog();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            giveUp();
        }
        return false;
    }


    private void initData() {
        LoginBean user = EanfangApplication.getApplication().getUser();
        String name;
        String area = "";
        String address = "";
        //个人客户
        if (user.getAccount().getDefaultUser().getCompanyId() <= 0) {
            name = user.getAccount().getRealName();
            area = Config.get().getAddressByCode(user.getAccount().getAreaCode());
            address = user.getAccount().getAddress();
        } else {
            name = user.getAccount().getDefaultUser().getCompanyEntity().getOrgName();
            //getOrgUnitEntity() 为空
//            area = Config.get().getAddressByCode(user.getAccount().getDefaultUser().getCompanyEntity().getOrgUnitEntity().getAreaCode());
//            address = user.getAccount().getDefaultUser().getCompanyEntity().getOrgUnitEntity().getOfficeAddress();
        }
        //如果公司名称为空 则取当前登陆人的公司
        if (StringUtils.isEmpty(etCompanyName.getText())) {
            etCompanyName.setText(name);
            tvAddress.setText(area);
            etDetailAddress.setText(address);
        }
        if (StringUtils.isEmpty(etContact.getText())) {
            etContact.setText(user.getAccount().getRealName());
            etPhone.setText(user.getAccount().getMobile());
        }

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
                tvAddress.setText(province + "-" + city + "-" + county);
                //将选择的地址 取 显示值
                etDetailAddress.setText(address);
                break;
            case ADD_TROUBLE_CALLBACK_CODE:
                RepairBugEntity repairBugEntity = (RepairBugEntity) data.getSerializableExtra("bean");
                beanList.add(repairBugEntity);

                evaluateAdapter.notifyDataSetChanged();
//                initData();
                break;
            default:
                break;

        }
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
     * 故障明细
     */
    public void addTouble() {
        Intent intent = new Intent(this, AddTroubleActivity.class);
        startActivityForResult(intent, ADD_TROUBLE_CALLBACK_CODE);
    }

    /**
     * 到达时限
     */
    public void onArriveTimeOptionPicker(View view) {
        PickerSelectUtil.singleTextPicker(this, "到达时限", tvTime, GetConstDataUtils.getArriveList());
    }


}
