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
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.util.ConnectivityChangeReceiver;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.Config;
import net.eanfang.client.network.apiservice.UserApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.activity.SelectAddressActivity;
import net.eanfang.client.ui.adapter.ToRepairAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.AddTroubleBean;
import net.eanfang.client.ui.model.BusinessOne;
import net.eanfang.client.ui.model.SelectAddressItem;
import net.eanfang.client.ui.model.ToRepairBean;
import net.eanfang.client.ui.model.ToRepairItem;
import net.eanfang.client.ui.model.User;
import net.eanfang.client.util.PickerSelectUtil;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    private EditText et_company;
    private TextView tv_address;
    private EditText et_detail_address;
    private EditText et_contact;
    private EditText et_phone;
    private TextView tv_time;
    private ImageView iv_address;
    private TextView tv_business;
    private RecyclerView mRecyclerView;
    private ArrayList<ToRepairItem> mDataList = new ArrayList<>();

    private String bugOneCode;
    private List<BusinessOne> businessOneList = new ArrayList<>();

    private BaseQuickAdapter evaluateAdapter;
    //选中的业务大类的位置
    private int position;
    private List<AddTroubleBean> beanList = new ArrayList<>();

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
        initView();
        initData();
        initAdapter();
        registerListener();

        setTitle("我要报修");
        setLeftBack();
    }

    private void registerListener() {
        setRightTitle("下一步");
        setRightTitleOnClickListener(v -> {
            if (checkInfo()) {
                doHttpCheckServiceRegion(province, city, county);
            }
        });
        ImageView iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(v -> giveUp());
    }


    private void goSelectWorker() {
        Intent intent = new Intent(RepairActivity.this, SelectWorkerActivity.class);
        intent.putExtra("bean", fillBean());
        startActivity(intent);
    }

    private boolean checkInfo() {
        if (StringUtils.isEmpty(et_company.getText().toString().trim())) {
            showToast("请输入用户名称");
            return false;
        }
        if (StringUtils.isEmpty(tv_address.getText().toString().trim())) {
            showToast("请选择地址");
            return false;
        }
        if (StringUtils.isEmpty(et_detail_address.getText().toString().trim())) {
            showToast("请输入详细地址");
            return false;
        }
        if (StringUtils.isEmpty(et_contact.getText())) {
            showToast("请输入联系人姓名");
            return false;
        }
        if (StringUtils.isEmpty(et_phone.getText())) {
            showToast("请输入联系人手机号");
            return false;
        }
        //电话号码是否符合格式
        if (!StringUtils.isMobileString(et_phone.getText().toString().trim())) {
            showToast("请输入正确手机号");
            return false;
        }
        if (StringUtils.isEmpty(tv_time.getText())) {
            showToast("请选择到达时限");
            return false;
        }
        if (StringUtils.isEmpty(tv_business.getText()) || StringUtils.isEmpty(bugOneCode)) {
            showToast("请选择故障分类");
            return false;
        }
        return true;
    }

    private void doHttpCheckServiceRegion(String province, String city, String county) {
        EanfangHttp.get(UserApi.CHECK_SERVICE_REGION)
                .tag(this)
                .params("province", province)
                .params("city", city)
                .params("county", county)
                .execute(new EanfangCallback(this, true) {
                    @Override
                    public void onSuccess(Object bean) {
                        //去选技师
                        goSelectWorker();
                    }
                });

    }

    private ToRepairBean fillBean() {
        ToRepairBean bean = new ToRepairBean();
        bean.setCompany(et_company.getText().toString().trim());
        bean.setProvince(province);
        bean.setCity(city);
        bean.setArea(county);
        bean.setDetailAddress(et_detail_address.getText().toString().trim());
        bean.setPhone(et_phone.getText().toString().trim());
        bean.setName(et_contact.getText().toString().trim());
        bean.setTime(tv_time.getText().toString().trim());
        bean.setBusiness(tv_business.getText().toString().trim());
        bean.setBugOneUid(bugOneCode);
        bean.setBeanList(beanList);
        bean.setLatitude(latitude);
        bean.setLongitude(longitude);

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

    private void initView() {
        et_company = (EditText) findViewById(R.id.et_company);
        tv_address = (TextView) findViewById(R.id.tv_address);
        et_detail_address = (EditText) findViewById(R.id.et_detail_address);
        et_contact = (EditText) findViewById(R.id.et_contact);
        et_phone = (EditText) findViewById(R.id.et_phone);
        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_address = (ImageView) findViewById(R.id.iv_address);
        tv_business = (TextView) findViewById(R.id.tv_business);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initData() {
        mDataList.clear();
        for (int i = 0; i < beanList.size(); i++) {
            ToRepairItem item = new ToRepairItem();
            item.setName(i + 1 + "." + beanList.get(i).getBugtwoname() + "-" + beanList.get(i).getBugthreename() + "(" + beanList.get(i).getBugposition() + ")");
            mDataList.add(item);
        }
        User user = EanfangApplication.getApplication().getUser();
        String name = "";
        if (StringUtils.isEmpty(user.getCompanyName())) {
            name = user.getName();
        } else {
            name = user.getCompanyName();
        }
        //如果公司名称为空 则取当前登陆人的公司
        if (StringUtils.isEmpty(et_company.getText())) {
            et_company.setText(name);
        }

        et_contact.setText(user.getName());
        et_phone.setText(user.getAccount());
        if (StringUtils.isEmpty(et_phone.getText())) {
            et_phone.setText(user.getAccount());
        }

        businessOneList = Config.getConfig().getBusinessOneList();


    }


    private void initAdapter() {
        evaluateAdapter = new ToRepairAdapter(R.layout.item_trouble, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                beanList.remove(position);
                initData();
                evaluateAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
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
                tv_address.setText(item.getProvince() + "-" + item.getCity() + "-" + item.getAddress());
                //将选择的地址 取 显示值
                et_detail_address.setText(item.getName());
                break;
            case ADD_TROUBLE_CALLBACK_CODE:
                AddTroubleBean bean = (AddTroubleBean) data.getSerializableExtra("bean");
                beanList.add(bean);
                initData();
                evaluateAdapter.notifyDataSetChanged();
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
    public void addTouble(View view) {
        if (tv_business.getText().toString().isEmpty()) {
            ToastUtil.get().showToast(this, "请先选择故障分类");
            return;
        }

        Intent intent = new Intent(this, AddTroubleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bugOneCode", bugOneCode);
        bundle.putString("companyUid", user().getCompanyId());
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_TROUBLE_CALLBACK_CODE);
    }

    /**
     * 到达时限
     */
    public void onArriveTimeOptionPicker(View view) {
        PickerSelectUtil.singleTextPicker(this, "到达时限", tv_time, Stream.of(Config.getConfig().getArriveTime()).toList());
    }

    /**
     * 故障分类
     */
    public void showBusinessOne(View view) {

        PickerSelectUtil.singleTextPicker(this, "系统类别", Stream.of(businessOneList).map(bus -> bus.getName()).toList(), (index, item) -> {
            position = index;
            bugOneCode = businessOneList.get(position).getCode();
            tv_business.setText(businessOneList.get(position).getName());
        });
    }

}
