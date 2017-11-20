package net.eanfang.client.ui.activity.worksapce;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Stream;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.Config;
import net.eanfang.client.ui.activity.SelectAddressActivity;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.InstallOrderConfirmBean;
import net.eanfang.client.ui.model.SelectAddressItem;
import net.eanfang.client.ui.model.User;
import net.eanfang.client.util.PickerSelectUtil;
import net.eanfang.client.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  13:35
 * @email houzhongzhou@yeah.net
 * @desc 我要报装
 */

public class InstallActivity extends BaseActivity {
    @BindView(R.id.et_company)
    EditText etCompany;
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
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.ll_business)
    LinearLayout llBusiness;
    @BindView(R.id.tv_project_time)
    TextView tvProjectTime;
    @BindView(R.id.LL_project_time)
    LinearLayout LLProjectTime;
    @BindView(R.id.tv_budget)
    TextView tvBudget;
    @BindView(R.id.ll_budget)
    LinearLayout llBudget;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.iv_left)
    ImageView ivLeft;

    private String latitude;
    private String longitude;
    //选中的业务大类的uid
    private String bugOneUid;

    private String city;
    private String zone;

    public static void jumpActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, InstallActivity.class);
        ((BaseActivity) context).startAnimActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);
        ButterKnife.bind(this);

        initView();
        initData();
        setListener();
    }


    private void initView() {

        setTitle("我要报装");
        setRightTitle("下一步");
        ivLeft.setOnClickListener(v -> giveUp());
        //下一步事件
        setRightTitleOnClickListener((v) -> {
            submit();
        });

    }

    private void initData() {
        User user = EanfangApplication.getApplication().getUser();
        String name = "";
        if (StringUtils.isEmpty(user.getCompanyName()))
            name = user.getName();
        else
            name = user.getCompanyName();
        //如果公司名称为空 则取当前登陆人的公司
        if (StringUtils.isEmpty(etCompany.getText()))
            etCompany.setText(name);
        etContact.setText(user.getName());
        etPhone.setText(user.getAccount());
    }

    private void setListener() {
        //选择地址
        llAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(InstallActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, 10011);
        });
        //回复时限选择
        llTime.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "回复时限", tvTime, Stream.of(Config.getConfig().getReplyLimit()).toList());
        });
        //业务类型一级
        llBusiness.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", Config.getConfig().getBudgetLimit(), (index, item) -> {
                tvBusiness.setText(Config.getConfig().getBusinessOneList().get(index).getName());
                bugOneUid = Config.getConfig().getBusinessOneList().get(index).getCode();
            });
        });
        //预计工期
        LLProjectTime.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", Config.getConfig().getPlanLimit(), (index, item) -> {
                tvProjectTime.setText(item);
            });
        });
        //预算范围
        llBudget.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", Config.getConfig().getBudgetLimit(), (index, item) -> {
                tvBudget.setText(item);
            });
        });

    }

    private void submit() {
        // validate
        String company = etCompany.getText().toString().trim();
        if (TextUtils.isEmpty(company)) {
            Toast.makeText(this, "单位名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String bigAddress = tvAddress.getText().toString().trim();
        if (TextUtils.isEmpty(bigAddress)) {
            Toast.makeText(this, "报装地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = etDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String contact = etContact.getText().toString().trim();
        if (TextUtils.isEmpty(contact)) {
            Toast.makeText(this, "联系人不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String business = tvBusiness.getText().toString().trim();
        if (TextUtils.isEmpty(business)) {
            Toast.makeText(this, "业务类型不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        String desc = etDesc.getText().toString().trim();
        if (TextUtils.isEmpty(desc)) {
            Toast.makeText(this, "需求描述不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (desc.length() > 50) {
            showToast("需求描述不能超过50个字");
            return;
        }

        InstallOrderConfirmBean installOrderConfirmBean = new InstallOrderConfirmBean();
        installOrderConfirmBean.setLatitude(latitude);
        installOrderConfirmBean.setLongitude(longitude);
        installOrderConfirmBean.setCity(city);
        installOrderConfirmBean.setZone(zone);
        installOrderConfirmBean.setArrivetime(tvTime.getText().toString().trim());
        installOrderConfirmBean.setBudget(tvBudget.getText().toString().trim());
        installOrderConfirmBean.setBugone(bugOneUid);
        installOrderConfirmBean.setBugonename(business);
        installOrderConfirmBean.setClientconnector(etContact.getText().toString().trim());
        installOrderConfirmBean.setClientphone(etPhone.getText().toString().trim());
        installOrderConfirmBean.setDetailplace(etDetailAddress.getText().toString().trim());
        installOrderConfirmBean.setDescription(etDesc.getText().toString().trim());
        installOrderConfirmBean.setPredicttime(tvProjectTime.getText().toString().trim());
        installOrderConfirmBean.setClientcompanyname(company);
//        installOrderConfirmBean.setWorkercompanyuid();

        Intent intent = new Intent(InstallActivity.this, SelectCompanyActivity.class);
        intent.putExtra("bean", installOrderConfirmBean);
        startActivity(intent);

    }

    /**
     * 是否放弃修改
     */
    private void giveUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否放弃报装？");
        builder.setTitle("提示");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            giveUp();
        }
        return false;
    }

    /**
     * 地图选址 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode == 10011) {
            SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
            Log.e("address", item.toString());
            latitude = item.getLatitude().toString();
            longitude = item.getLongitude().toString();
            city = item.getCity();
            zone = item.getAddress();
            tvAddress.setText(item.getProvince() + "-" + item.getCity() + "-" + item.getAddress());
            //地图选址 取 显示值
            etDetailAddress.setText(item.getName());
        }
    }


}
