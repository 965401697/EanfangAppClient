package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.model.Message;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.model.TaskPublishBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/27  19:43
 * @email houzhongzhou@yeah.net
 * @desc 发包
 */

public class TaskPublishActivity extends BaseActivity {
    /**
     * 选择地址 回调 code
     */
    private final int ADDRESS_CALLBACK_CODE = 1;
    @BindView(R.id.et_task_company)
    EditText etTaskCompany;
    @BindView(R.id.et_task_uname)
    EditText etTaskUname;
    @BindView(R.id.et_task_phone)
    EditText etTaskPhone;
    @BindView(R.id.tv_project_info)
    TextView tvProjectInfo;
    @BindView(R.id.et_project_company)
    EditText etProjectCompany;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.tv_project_type)
    TextView tvProjectType;
    @BindView(R.id.ll_project_type)
    LinearLayout llProjectType;
    @BindView(R.id.tv_business_type)
    TextView tvBusinessType;
    @BindView(R.id.ll_business_type)
    LinearLayout llBusinessType;
    @BindView(R.id.tv_project_time)
    TextView tvProjectTime;
    @BindView(R.id.ll_project_time)
    LinearLayout llProjectTime;
    @BindView(R.id.tv_budget)
    TextView tvBudget;
    @BindView(R.id.ll_budget)
    LinearLayout llBudget;
    @BindView(R.id.tv_login_time)
    TextView tvLoginTime;
    @BindView(R.id.ll_login_time)
    LinearLayout llLoginTime;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private String longitude;
    private String latitude;
    private String itemcity;
    private String itemzone;
    private TimePickerView pvTime;
    private Map<String, String> uploadMap = new HashMap<>();
    private TaskPublishBean bean = new TaskPublishBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_publish);
        ButterKnife.bind(this);
        initView();
        initData();
        setListener();
        initStartTimePicker();

    }

    private void initView() {
        mPhotosSnpl.setDelegate(new BGASortableDelegate(this));
        setTitle("找工人");
        setLeftBack();
    }


    private void initData() {
        LoginBean user = EanfangApplication.getApplication().getUser();
        String name = "";
        if (StringUtils.isEmpty(user.getAccount().getDefaultUser().getCompanyEntity().getOrgName())) {
            name = user.getAccount().getRealName();
        } else {
            name = user.getAccount().getDefaultUser().getCompanyEntity().getOrgName();
        }
        //如果公司名称为空 则取当前登陆人的公司
        if (StringUtils.isEmpty(etTaskCompany.getText())) {
            etTaskCompany.setText(name);
        }
        etTaskUname.setText(user.getAccount().getRealName());
        etTaskPhone.setText(user.getAccount().getMobile());
    }


    private void setListener() {

        llAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(TaskPublishActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, ADDRESS_CALLBACK_CODE);
        });
        llLoginTime.setOnClickListener((v) -> {
            if (pvTime != null) {
                pvTime.show();
                pvTime.show();
            }
        });
        llBusinessType.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvBusinessType, Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList());
        });
        llProjectTime.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvProjectTime, GetConstDataUtils.getPredictList());
        });
        llBudget.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvBudget, GetConstDataUtils.getBudgetList());
        });
        llProjectType.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvProjectType, GetConstDataUtils.getTaskPublishTypeList());
            Log.e("zzw", tvProjectType.getText().toString());

        });
        btnConfirm.setOnClickListener((v) -> {
            summit();
        });
    }


    private void summit() {
        bean.setPublishCompanyName(etTaskCompany.getText().toString().trim());
        bean.setContacts(etTaskUname.getText().toString().trim());
        bean.setContactsPhone(etTaskPhone.getText().toString().trim());
        bean.setProjectCompanyName(etProjectCompany.getText().toString().trim());
        bean.setZoneCode(Config.get().getAreaCodeByName(itemcity, itemzone));
        bean.setZone_id(Long.valueOf(Config.get().getBaseIdByCode(bean.getZoneCode(), 3, Constant.AREA)));
        bean.setDetailPlace(etDetailAddress.getText().toString().trim());
        bean.setLatitude(latitude);
        bean.setLongitude(longitude);
        bean.setType(GetConstDataUtils.getTaskPublishTypeList().indexOf(tvProjectType.getText().toString().trim()));
        bean.setBusinessOneCode(Config.get().getBusinessCodeByName(tvBusinessType.getText().toString().trim(), 1));
        bean.setBusiness_one_id(V.v(() -> Long.valueOf(Config.get().getBusinessIdByCode(bean.getBusinessOneCode(), 1))));
        bean.setPredicttime(GetConstDataUtils.getPredictList().indexOf(tvProjectTime.getText().toString().trim()));
        bean.setBudget(GetConstDataUtils.getBudgetList().indexOf(tvBudget.getText().toString().trim()));
        bean.setToDoorTime(tvLoginTime.getText().toString().trim());
        bean.setDescription(etDesc.getText().toString().trim());
        String ursStr = PhotoUtils.getPhotoUrl(mPhotosSnpl, uploadMap, true);
        bean.setPictures(ursStr);
        String json = JSONObject.toJSONString(bean);
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        doHttpSubmit(json);
                    });

                }
            });
        } else {
            doHttpSubmit(json);
        }
    }


    private void doHttpSubmit(String jsonString) {
        EanfangHttp.post(NewApiService.TASK_PUBLISH_ADD)
                .upJson(jsonString)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    submitSuccess();
                }));

    }

    private void submitSuccess() {

        Intent intent = new Intent(this, StateChangeActivity.class);
        Message message = new Message();
        message.setTitle("找工人成功");
        message.setMsgTitle("您的任务已经发布成功");
        message.setMsgContent("请耐心等待技师接单。");
        message.setShowLogo(true);
        message.setShowOkBtn(true);
        Bundle bundle = new Bundle();
        bundle.putSerializable("message", message);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
//            }
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        } else if (requestCode == ADDRESS_CALLBACK_CODE) {
            if (data == null || data.getExtras().size() <= 0) {
                return;
            }
            SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
            latitude = item.getLatitude().toString();
            longitude = item.getLongitude().toString();
            itemcity = item.getCity();
            itemzone = item.getAddress();
            tvAddress.setText(item.getProvince() + "-" + item.getCity() + "-" + item.getAddress());
            //将选择的地址 取 显示值
            etDetailAddress.setText(item.getName());
        }

    }

    private void initStartTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        String currDateStr = GetDateUtils.dateToDateTimeString(GetDateUtils.getDateNow());

        Calendar startDate = Calendar.getInstance();
        //startDate.set(2017, 5, 24);
        //修改预约时间选择限制，不能早过当前时间
        startDate.set(GetDateUtils.getYear(currDateStr), GetDateUtils.getMonth(currDateStr), GetDateUtils.getDay(currDateStr), GetDateUtils.getHour(currDateStr), GetDateUtils.getMinute(currDateStr));

        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 11, 31);
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                tvLoginTime.setText(GetDateUtils.dateToDateTimeString(date));
            }
        })
                .setTitleText("踏勘时间")
                .setType(new boolean[]{true, true, true, true, true, true})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build();
    }
}
