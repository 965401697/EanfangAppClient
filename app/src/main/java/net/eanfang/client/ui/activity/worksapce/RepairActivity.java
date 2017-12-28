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
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.util.ConnectivityChangeReceiver;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.Config;
import net.eanfang.client.config.Constant;
import net.eanfang.client.ui.activity.SelectAddressActivity;
import net.eanfang.client.ui.adapter.ToRepairAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.SelectAddressItem;
import net.eanfang.client.ui.model.repair.RepairBugEntity;
import net.eanfang.client.ui.model.repair.RepairOrderEntity;
import net.eanfang.client.util.PickerSelectUtil;
import net.eanfang.client.util.StringUtils;

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

public class RepairActivity extends BaseActivity {

    //报修地址回调 code
    private final int REPAIR_ADDRESS_CALLBACK_CODE = 1;
    //添加故障明细回调
    private final int ADD_TROUBLE_CALLBACK_CODE = 2;
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


    //选中的业务大类的位置
    private int position;
    private List<RepairBugEntity> beanList = new ArrayList<>();

    private String latitude;
    private String longitude;
    private String province;
    private String city;
    private String county;
    private String address;

    public static void jumpToActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RepairActivity.class);
        ((BaseActivity) context).startAnimActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);
        ButterKnife.bind(this);
//        initData();
        setTitle("我要报修");
        registerListener();
//        initAdapter();
    }


    private void registerListener() {
        setRightTitle("选择技师");
        setRightTitleOnClickListener(v -> {
            goSelectWorker();
        });
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(v -> giveUp());
        btnAddTrouble.setOnClickListener(v -> addTouble());
    }


    private void goSelectWorker() {
        Intent intent = new Intent(RepairActivity.this, SelectWorkerActivity.class);
        intent.putExtra("bean", fillBean());
        startActivity(intent);
    }

    private boolean checkInfo() {

        if (StringUtils.isEmpty(tvAddress.getText().toString().trim())) {
            showToast("请选择地址");
            return false;
        }
        if (StringUtils.isEmpty(etDetailAddress.getText().toString().trim())) {
            showToast("请输入详细地址");
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
        if (StringUtils.isEmpty(tvTime.getText())) {
            showToast("请选择到达时限");
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
        bean.setPlaceCode(Config.getConfig().getRegCode(city, county));
        bean.setRepairContactPhone(EanfangApplication.getApplication().getUser().getAccount().getMobile());
        bean.setRepairContacts(EanfangApplication.getApplication().getUser().getAccount().getRealName());
        bean.setArriveTimeLimit(Config.getConfig().getConstBean().getConst().get(Constant.ARRIVE_LIMIT).indexOf(tvTime.getText().toString().trim()));
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


//    private void initData() {
//        mDataList.clear();
//        for (int i = 0; i < beanList.size(); i++) {
//            ToRepairItem item = new ToRepairItem();
//            item.setName(i + 1 + "." + beanList.get(i).getBugtwoname() + "-" + beanList.get(i).getBugthreename() + "(" + beanList.get(i).getBugposition() + ")");
//            mDataList.add(item);
//        }
//        User user = EanfangApplication.getApplication().getUser();
//        String name = "";
//        if (StringUtils.isEmpty(user.getCompanyName())) {
//            name = user.getName();
//        } else {
//            name = user.getCompanyName();
//        }
//        //如果公司名称为空 则取当前登陆人的公司
//        if (StringUtils.isEmpty(et_company.getText())) {
//            et_company.setText(name);
//        }
//
//        et_contact.setText(user.getName());
//        et_phone.setText(user.getAccount());
//        if (StringUtils.isEmpty(et_phone.getText())) {
//            et_phone.setText(user.getAccount());
//        }
//
//        businessOneList = Config.getConfig().getBusinessOneList();
//
//
//    }


    private void initAdapter() {
        ToRepairAdapter evaluateAdapter = new ToRepairAdapter(R.layout.item_trouble, beanList);
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
                RepairBugEntity bean = (RepairBugEntity) data.getSerializableExtra("bean");
                beanList.add(bean);

                initAdapter();
//                initData();
                break;
            default:
                break;

        }
    }

    /**
     * 选择地址
     */
    public void address(View view) {
        if (ConnectivityChangeReceiver.isNetConnected(getApplicationContext()) == true) {
            Intent intent = new Intent(this, SelectAddressActivity.class);
            startActivityForResult(intent, REPAIR_ADDRESS_CALLBACK_CODE);
        } else {
            showToast("网络异常，请检查网络");
        }
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
        PickerSelectUtil.singleTextPicker(this, "到达时限", tvTime, Stream.of(Config.getConfig().getConstBean().getConst().get(Constant.ARRIVE_LIMIT)).toList());
    }


}
