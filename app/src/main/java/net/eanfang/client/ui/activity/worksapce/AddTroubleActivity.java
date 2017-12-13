package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.util.ConnectivityChangeReceiver;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.ToastUtil;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.config.EanfangConst;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.apiservice.DeviceFailureService;
import net.eanfang.client.network.apiservice.DeviceService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.oss.OSSCallBack;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.AddTroubleBean;
import net.eanfang.client.ui.model.BusinessWorkBean;
import net.eanfang.client.ui.model.FillRepairInfoBean;
import net.eanfang.client.ui.model.device.GetDeviceFailureOptionBean;
import net.eanfang.client.ui.model.device.GetDeviceOptionBean;
import net.eanfang.client.util.GetConstDataUtils;
import net.eanfang.client.util.OSSUtils;
import net.eanfang.client.util.PickerSelectUtil;
import net.eanfang.client.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  15:10
 * @email houzhongzhou@yeah.net
 * @desc 添加故障
 */

public class AddTroubleActivity extends BaseActivity {

    private static final Integer CLIENT_ADD_TROUBLE = 1;
    private static final Integer WORKER_ADD_TROUBLE = 2;

    private BusinessWorkBean businessBean;

    private BusinessWorkBean.BusinessBean.TwoBean twoBean;

    private String bugOneCode;
    private String companyUid;

    private LinearLayout ll_business;
    private TextView tv_business;
    private LinearLayout ll_equipment;
    private TextView tv_equipment;
    private LinearLayout ll_model;
    private TextView tv_model;
    private EditText et_location;
    private EditText et_code;
    private TextView tv_right;
    private EditText et_desc;

    @BindView(R.id.ll_deviceName)
    LinearLayout ll_deviceName;
    @BindView(R.id.tv_deviceName)
    TextView tv_deviceName;

    @BindView(R.id.ll_deviceFailure)
    LinearLayout ll_deviceFailure;

    /**
     * 拖拽排序九宫格控件
     */
    private BGASortableNinePhotoLayout mPhotosSnpl;

    private Map<String, String> uploadMap = new HashMap<>();

    private int devicePosition = -1;
    private GetDeviceOptionBean deviceOptionBean;
    private int failurePosition = -1;
    private GetDeviceFailureOptionBean failureOptionBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trouble);
        ButterKnife.bind(this);
        initView();


        initData();

        setListener();
    }

    private void initView() {
        ll_business = (LinearLayout) findViewById(R.id.ll_business);
        tv_business = (TextView) findViewById(R.id.tv_business);
        ll_equipment = (LinearLayout) findViewById(R.id.ll_equipment);
        tv_equipment = (TextView) findViewById(R.id.tv_equipment);
        ll_model = (LinearLayout) findViewById(R.id.ll_model);
        tv_model = (TextView) findViewById(R.id.tv_model);
        et_location = (EditText) findViewById(R.id.et_location);
        et_code = (EditText) findViewById(R.id.et_code);
        mPhotosSnpl = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_moment_add_photos);
        tv_right = (TextView) findViewById(R.id.tv_right);
        et_desc = (EditText) findViewById(R.id.et_desc);
        tv_right.setText("提交");
        setTitle("新增故障");
        setLeftBack();

//        //个人客户 不显示设备库选择
//        if (EanfangApplication.getApplication().getUser().getCompanyId() == null) {
//            ll_deviceName.setVisibility(View.GONE);
//        }
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bugOneCode = bundle.getString("bugOneCode");
        companyUid = bundle.getString("companyUid");
        if (StringUtils.isEmpty(bugOneCode)) {
            showToast("哎呀，参数好像出现了一点状况，重试一下吧。");
            finishSelf();
            return;
        }
        doHttpBusines();
    }

    /**
     * 获取业务类型
     */
    private void doHttpBusines() {
        EanfangHttp.get(ApiService.GET_BUSINESS_BY_CODE)
                .params("code", bugOneCode)
                .execute(new EanfangCallback<BusinessWorkBean>(this, true) {
                    @Override
                    public void onSuccess(BusinessWorkBean bean) {
                        businessBean = bean;
                        if (!StringUtils.isEmpty(companyUid)) {
                            runOnUiThread(() -> doHttpDevice());
                        }
                    }
                });

    }

    /**
     * 获取设备选项
     */
    private void doHttpDevice() {
        EanfangHttp.get(DeviceService.GET_OPTION)
                .params("companyUid", companyUid)
                .params("businessOne", GetConstDataUtils.getBugOneNameByCode(bugOneCode))
                .execute(new EanfangCallback<GetDeviceOptionBean>(this, true) {
                    @Override
                    public void onSuccess(GetDeviceOptionBean bean) {
                        deviceOptionBean = bean;
                    }

                    @Override
                    public void onNoData(String message) {

                    }
                });


    }

    private void doHttpDeviceFailure() {
        String deviceUid = "";
        if (devicePosition > 0) {
            deviceUid = deviceOptionBean.getBean().get(devicePosition).getDeviceUid();
        }
        EanfangHttp.get(DeviceFailureService.GET_OPTION)
                .params("deviceUid", deviceUid)
                .params("businessOne", businessBean.getBusiness().getOne().getName())
                .params("businessTwo", tv_business.getText().toString().trim())
                .params("businessThree", tv_equipment.getText().toString().trim())
                .execute(new EanfangCallback<GetDeviceFailureOptionBean>(this, true) {
                    @Override
                    public void onSuccess(GetDeviceFailureOptionBean bean) {
                        failureOptionBean = bean;
                    }

                    @Override
                    public void onNoData(String message) {
                        failureOptionBean = null;
                    }
                });
    }


    private void setListener() {
        ll_deviceName.setOnClickListener((v) -> {
            showDeviceOption();
        });

        ll_deviceFailure.setOnClickListener((v) -> {
            showFailure();
        });

        ll_business.setOnClickListener((v) -> {
            showBusinessTwo();
        });
        ll_equipment.setOnClickListener((v) -> {
            if (tv_business.getText().toString().isEmpty()) {
                ToastUtil.get().showToast(this, "请先选择设备类别");
                return;
            }
            showBusinessThree();
        });
        ll_model.setOnClickListener((v) -> {
            if (tv_business.getText().toString().isEmpty()) {
                ToastUtil.get().showToast(this, "请先选择设备类别");
                return;
            }
            if (tv_equipment.getText().toString().isEmpty()) {
                ToastUtil.get().showToast(this, "请先选择设备名称");
                return;
            }
            showModel();
        });

        if (BuildConfig.TYPE.equals(EanfangConst.PERSON_TYPE_CLIENT)) {
            tv_right.setOnClickListener(new MultiClickListener(this, this::checkInfo, this::onSubmitClient));
        } else {
            tv_right.setOnClickListener(new MultiClickListener(this, this::checkInfo, this::onSubmitWorker));
        }
        // 设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(new BGASortableDelegate(this));


    }

    private void showDeviceOption() {
        if (deviceOptionBean == null) {
            showToast("暂无可用的设备可选");
            return;
        }
        List<String> opts = Stream.of(deviceOptionBean.getBean()).map(beanBean -> beanBean.getCustomerDeviceName() + "(" + beanBean.getCustomerDeviceNo() + beanBean.getLocation() + ")").toList();

        PickerSelectUtil.singleTextPicker(this, "选择设备", opts, (index, item) -> {
            devicePosition = index;
            tv_deviceName.setText(deviceOptionBean.getBean().get(index).getCustomerDeviceName());
            tv_business.setText(deviceOptionBean.getBean().get(index).getBusinessTwo());
            tv_equipment.setText(deviceOptionBean.getBean().get(index).getBusinessThree());
            tv_model.setText(deviceOptionBean.getBean().get(index).getBrand() + " - " + deviceOptionBean.getBean().get(index).getModel());
            et_location.setText(deviceOptionBean.getBean().get(index).getLocation());
            et_code.setText(deviceOptionBean.getBean().get(index).getCustomerDeviceNo());
            doHttpDeviceFailure();
        });
    }

    private void showFailure() {
        if (failureOptionBean == null) {
            showToast("暂时没有可以参考的内容");
            return;
        }
        List<String> opts = Stream.of(failureOptionBean.getBean()).map(failure -> failure.getTitle()).toList();
        PickerSelectUtil.singleTextPicker(this, "参考故障", opts, (index, item) -> {
            failurePosition = index;
            et_desc.setText(failureOptionBean.getBean().get(index).getDescription());
        });
    }


    /**
     * 设备类别选择器
     */
    private void showBusinessTwo() {
        List<String> opts = Stream.of(businessBean.getBusiness().getTow()).map(bug -> bug.getName()).toList();
        PickerSelectUtil.singleTextPicker(this, "设备类型", opts, (index, item) -> {
            tv_business.setText(businessBean.getBusiness().getTow().get(index).getName());
            twoBean = businessBean.getBusiness().getTow().get(index);
            cleanBusiness("two");
        });
    }

    /**
     * 设备名称选择器
     */
    private void showBusinessThree() {
        if (twoBean == null) {
            return;
        }

        List<String> opts = Stream.of(twoBean.getThree()).map(bug -> bug.getName()).toList();
        PickerSelectUtil.singleTextPicker(this, "", opts, (index, item) -> {
            cleanBusiness("three");
            tv_equipment.setText(twoBean.getThree().get(index).getName());
        });
    }

    /**
     * 品牌型号选择器
     */
    private void showModel() {
        List<String> opts = Stream.of(businessBean.getBusiness().getBrand()).map(brand -> brand.getName()).toList();
        PickerSelectUtil.singleTextPicker(this, "品牌型号", opts, (index, item) -> {
            tv_model.setText(businessBean.getBusiness().getBrand().get(index).getName());
            doHttpDeviceFailure();
        });
    }

    /**
     * 清除业务类型联动
     *
     * @param type one一级  two二级 three三级
     */
    public void cleanBusiness(String type) {
        //清除选择的设备
        devicePosition = -1;
        tv_deviceName.setText("");
        if ("two".equals(type)) {
            tv_equipment.setText("");
            tv_model.setText("");
        } else {
            tv_model.setText("");
        }
    }

    private boolean checkInfo() {

        if (StringUtils.isEmpty(tv_business.getText().toString())) {
            showToast("请先选择类别");
            return false;
        }
        if (StringUtils.isEmpty(tv_equipment.getText().toString())) {
            showToast("请选先择设备分类");
            return false;
        }
        if (StringUtils.isEmpty(tv_model.getText().toString())) {
            showToast("请选先择品牌型号");
            return false;
        }

        if (StringUtils.isEmpty(et_desc.getText().toString())) {
            showToast("请输入故障描述");
            return false;
        }
        if (StringUtils.isEmpty(et_location.getText().toString())) {
            showToast("请输入故障设备位置");
            return false;
        }
        if (StringUtils.isEmpty(et_code.getText().toString())) {
            showToast("请输入设备编号");
            return false;
        }
        String etcode = et_code.getText().toString().trim();
        if (etcode.length() > 20) {
            showToast("设备号长度超长了");
            return false;
        }
        return true;

    }


    /**
     * 提交方法
     */
    private void onSubmitClient() {
        if (!ConnectivityChangeReceiver.isNetConnected(getApplicationContext())) {
            showToast("网络异常，请检查网络");
            return;
        }
        AddTroubleBean bean = new AddTroubleBean();
        bean.setBugtwoname(tv_business.getText().toString().trim());
        bean.setBugtwo("");
        bean.setBugtwoname(tv_business.getText().toString().trim());
        bean.setBugthree("");
        bean.setBugthreename(tv_equipment.getText().toString().trim());
        bean.setBugfour("");
        bean.setBugfourname(tv_model.getText().toString().trim());
        bean.setBugposition(et_location.getText().toString().trim());
        bean.setEquipnum(et_code.getText().toString().trim());
        bean.setBugdesc(et_desc.getText().toString().trim());
        //2017年9月29日
        if (failurePosition > 0) {
            bean.setDeviceFailureUid(failureOptionBean.getBean().get(failurePosition).getUid());
        }
        if (devicePosition > 0) {
            bean.setDeviceUid(deviceOptionBean.getBean().get(devicePosition).getDeviceUid());
            bean.setCustomerDeviceName(deviceOptionBean.getBean().get(devicePosition).getCustomerDeviceName());
            bean.setCustomerDeviceUid(deviceOptionBean.getBean().get(devicePosition).getUid());
        }

        //图片处理
        List<String> urls = PhotoUtils.getPhotoUrl(mPhotosSnpl, uploadMap,true);
        bean.setBugpic1(urls.get(0));
        bean.setBugpic2(urls.get(1));
        bean.setBugpic3(urls.get(2));

        if (mPhotosSnpl.getData().size() <= 0) {
            submitSuccess(bean, CLIENT_ADD_TROUBLE);
            return;
        }
        OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
            @Override
            public void onOssSuccess() {
                submitSuccess(bean, CLIENT_ADD_TROUBLE);
            }
        });

    }

    private void onSubmitWorker() {
        FillRepairInfoBean.BughandledetaillistBean bughandledetaillistBean = new FillRepairInfoBean.BughandledetaillistBean();

        bughandledetaillistBean.setBusinessOne(businessBean.getBusiness().getOne().getName());
        bughandledetaillistBean.setBusinessTwo(tv_business.getText().toString().trim());
        bughandledetaillistBean.setBusinessThree(tv_equipment.getText().toString().trim());
        //2017年9月29日
        if (failurePosition > 0) {
            bughandledetaillistBean.setDeviceFailureUid(failureOptionBean.getBean().get(failurePosition).getUid());
        }
        if (devicePosition > 0) {
            bughandledetaillistBean.setDeviceUid(deviceOptionBean.getBean().get(devicePosition).getDeviceUid());
            bughandledetaillistBean.setCustomerDeviceName(deviceOptionBean.getBean().get(devicePosition).getCustomerDeviceName());
            bughandledetaillistBean.setCustomerDeviceUid(deviceOptionBean.getBean().get(devicePosition).getUid());
        }
        bughandledetaillistBean.setInstrument(tv_equipment.getText().toString().trim());
        bughandledetaillistBean.setModelnum(tv_model.getText().toString().trim());
        bughandledetaillistBean.setEquipmentposition(et_location.getText().toString().trim());
        bughandledetaillistBean.setEquipmentcode(et_code.getText().toString().trim());
        bughandledetaillistBean.setDescription(et_desc.getText().toString().trim());

        //图片处理
        List<String> urls = PhotoUtils.getPhotoUrl(mPhotosSnpl, uploadMap,true);
        bughandledetaillistBean.setPic1(urls.get(0));
        bughandledetaillistBean.setPic2(urls.get(1));
        bughandledetaillistBean.setPic3(urls.get(2));

        if (mPhotosSnpl.getData().size() <= 0) {
            submitSuccess(bughandledetaillistBean, WORKER_ADD_TROUBLE);
            return;
        }
        OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
            @Override
            public void onOssSuccess() {
                submitSuccess(bughandledetaillistBean, WORKER_ADD_TROUBLE);
            }
        });

    }

    public void submitSuccess(Serializable serializable, Integer code) {
        Intent intent = new Intent();
        intent.putExtra("bean", serializable);
        setResult(code, intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }


}
