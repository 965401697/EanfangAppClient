package net.eanfang.client.ui.activity.worksapce.openshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.ETimeUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.ToastUtil;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpenShopLogWriteActivity extends BaseClientActivity {

    @BindView(R.id.et_company_name)
    TextView etCompanyName;
    @BindView(R.id.et_section_name)
    TextView etSectionName;
    @BindView(R.id.tv_staff_in_time)
    TextView tvStaffInTime;
    @BindView(R.id.tv_staff_out_time)
    TextView tvStaffOutTime;
    @BindView(R.id.tv_client_in_time)
    TextView tvClientInTime;
    @BindView(R.id.tv_client_out_time)
    TextView tvClientOutTime;
    @BindView(R.id.tv_open_time)
    TextView tvOpenTime;
    @BindView(R.id.tv_close_time)
    TextView tvCloseTime;
    @BindView(R.id.tv_depend_person)
    TextView tvDependPerson;
    @BindView(R.id.et_phone_num)
    TextView etPhoneNum;
    @BindView(R.id.ev_faultDescripte)
    EditText evFaultDescripte;

    private int posistion;
    private Long assigneeUserId;
    private String assigneeOrgCode;
    private Long assigneeTopCompanyId;
    private Long assigneeCompanyId;
    private OptionsPickerView pvOptions_NoLink;
    private List<UserEntity> userlist = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();

    private TimePickerView mTimeYearMonthDayHMS;
    private TextView currentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop_log_write);
        ButterKnife.bind(this);
        setTitle("开店日志");
        setLeftBack();
        initViews();
    }

    private void initViews() {
        etCompanyName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
        etSectionName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());

        getData();
        doSelectYearMonthDayHMS();
    }


    @OnClick({R.id.ll_staff_in_time, R.id.ll_staff_out_time, R.id.ll_client_in_time, R.id.ll_client_out_time, R.id.ll_open_time, R.id.ll_close_time, R.id.ll_depend_person, R.id.ll_comit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_staff_in_time:
                currentTextView = tvStaffInTime;
                mTimeYearMonthDayHMS.setDate(Calendar.getInstance());
                mTimeYearMonthDayHMS.show();
                break;
            case R.id.ll_staff_out_time:
                currentTextView = tvStaffOutTime;
                mTimeYearMonthDayHMS.setDate(Calendar.getInstance());
                mTimeYearMonthDayHMS.show();
                break;
            case R.id.ll_client_in_time:
                currentTextView = tvClientInTime;
                mTimeYearMonthDayHMS.setDate(Calendar.getInstance());
                mTimeYearMonthDayHMS.show();
                break;
            case R.id.ll_client_out_time:
                currentTextView = tvClientOutTime;
                mTimeYearMonthDayHMS.setDate(Calendar.getInstance());
                mTimeYearMonthDayHMS.show();
                break;
            case R.id.ll_open_time:
                currentTextView = tvOpenTime;
                mTimeYearMonthDayHMS.setDate(Calendar.getInstance());
                mTimeYearMonthDayHMS.show();
                break;
            case R.id.ll_close_time:
                currentTextView = tvCloseTime;
                mTimeYearMonthDayHMS.setDate(Calendar.getInstance());
                mTimeYearMonthDayHMS.show();
                break;
            case R.id.ll_depend_person:
//                showDependPerson();
                Intent intent = new Intent(this, SelectOrganizationActivity.class);
                intent.putExtra("isRadio", "isRadio");
                startActivity(intent);
                break;
            case R.id.ll_comit:
                sub();
                break;
        }
    }


    /**
     * 选择年月日时分秒
     */
    public void doSelectYearMonthDayHMS() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(2000, 0, 1, 0, 0, 0);
        endDate.set(2040, 11, 31, 0, 0, 0);
        mTimeYearMonthDayHMS = new TimePickerBuilder(OpenShopLogWriteActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                currentTextView.setText(ETimeUtils.getTimeByYearMonthDayHourMinSec(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText(" ")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
    }


    /**
     * 提交数据
     * {
     * "empEntryTime":"2018-05-21 17:30:30",
     * "empExitTime":"2018-05-21 17:30:30",
     * "cusEntryTime":"2018-05-21 17:30:30",
     * "cusExitTime":"2018-05-21 17:30:30",
     * "recYardStaTime":"2018-05-21 17:30:30",
     * "recYardEndTime":"2018-05-21 17:30:30",
     * "remarkInfo":"备注1",
     * "ownerUserId":"958222608418070530",
     * "ownerCompanyId":"1000",
     * "ownerTopCompanyId":"1000",
     * "ownerOrgCode":"c.4",
     * "assigneeUserId":"958601236116574210",
     * "assigneeCompanyId":"958601236116574209",
     * "assigneeTopCompanyId":"958601236116574209",
     * "assigneeOrgCode":"c.1",
     * "assigneePhone":"6666666666"
     * }
     */
    private void sub() {

        if (!checkInfo()) return;

        JSONObject object = new JSONObject();
        try {
            object.put("empEntryTime", tvStaffInTime.getText().toString().trim());
            object.put("empExitTime", tvStaffOutTime.getText().toString().trim());

            if (GetDateUtils.getTimeStamp(tvStaffOutTime.getText().toString().trim(), "yyyy-MM-dd hh:mm:ss") <= GetDateUtils.getTimeStamp(tvStaffInTime.getText().toString().trim(), "yyyy-MM-dd hh:mm:ss")) {
                Toast.makeText(this, "员工的退场时间不能小于进场时间", Toast.LENGTH_SHORT).show();
                return;
            }

            object.put("cusEntryTime", tvClientInTime.getText().toString().trim());
            object.put("cusExitTime", tvClientOutTime.getText().toString().trim());

            if (GetDateUtils.getTimeStamp(tvClientOutTime.getText().toString().trim(), "yyyy-MM-dd hh:mm:ss") <= GetDateUtils.getTimeStamp(tvClientInTime.getText().toString().trim(), "yyyy-MM-dd hh:mm:ss")) {
                Toast.makeText(this, "顾客的退场时间不能小于进场时间", Toast.LENGTH_SHORT).show();
                return;
            }

            object.put("recYardStaTime", tvOpenTime.getText().toString().trim());
            object.put("recYardEndTime", tvCloseTime.getText().toString().trim());

            if (GetDateUtils.getTimeStamp(tvCloseTime.getText().toString().trim(), "yyyy-MM-dd hh:mm:ss") <= GetDateUtils.getTimeStamp(tvOpenTime.getText().toString().trim(), "yyyy-MM-dd hh:mm:ss")) {
                Toast.makeText(this, "收货区的关闭时间不能小于进场时间", Toast.LENGTH_SHORT).show();
                return;
            }

            object.put("ownerUserId", EanfangApplication.getApplication().getUserId());
            object.put("ownerCompanyId", EanfangApplication.getApplication().getCompanyId());
            object.put("ownerTopCompanyId", EanfangApplication.getApplication().getTopCompanyId());
            object.put("ownerOrgCode", EanfangApplication.getApplication().getOrgCode())
            ;
            object.put("assigneeUserId", assigneeUserId);
            object.put("assigneeCompanyId", assigneeCompanyId);
            object.put("assigneeTopCompanyId", assigneeTopCompanyId);
            object.put("assigneeOrgCode", assigneeOrgCode);
            object.put("assigneePhone", tvDependPerson.getText().toString().trim());
            if (!TextUtils.isEmpty(evFaultDescripte.getText().toString().trim())) {
                object.put("remarkInfo", evFaultDescripte.getText().toString().trim());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        EanfangHttp.post(NewApiService.OA_SUB_OPEN_SHOP)
                .upJson(object)
                .execute(new EanfangCallback(this, true, com.alibaba.fastjson.JSONObject.class, (bean) -> {
                    ToastUtil.get().showToast(OpenShopLogWriteActivity.this, "创建成功");
                    finish();
                }));
    }

    private boolean checkInfo() {
        if (TextUtils.isEmpty(tvStaffInTime.getText().toString().trim())) {
            showToast("员工进场时间不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvStaffOutTime.getText().toString().trim())) {
            showToast("员工退场时间不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvClientInTime.getText().toString().trim())) {
            showToast("顾客退场时间不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvClientOutTime.getText().toString().trim())) {
            showToast("顾客退场时间不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvOpenTime.getText().toString().trim())) {
            showToast("收货区开启时间不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvCloseTime.getText().toString().trim())) {
            showToast("收货区关闭时间不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvDependPerson.getText().toString().trim())) {
            showToast("联系人不能为空");
            return false;
        }
        return true;
    }

    /**
     * 获取公司部门员工信息
     */
    private void getData() {

        EanfangHttp.get(NewApiService.GET_COLLEAGUE)
                .tag(this)
                .params("id", EanfangApplication.getApplication().getUserId())
                .params("companyId", EanfangApplication.getApplication().getCompanyId())
                .execute(new EanfangCallback<UserEntity>(OpenShopLogWriteActivity.this, true, UserEntity.class, true, (list) -> {
                    userlist = list;
                    userNameList.addAll(Stream.of(userlist).map((user) -> user.getAccountEntity().getRealName()).toList());
                }));
    }


    /**
     * 责任人
     */

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {


        if (presonList.size() > 0) {
            TemplateBean.Preson bean = (TemplateBean.Preson) presonList.get(0);

            etPhoneNum.setText(bean.getMobile());
            tvDependPerson.setText(bean.getName());

            assigneeUserId = Long.parseLong(bean.getUserId());
            if (bean.getOrgCode() != null && !TextUtils.isEmpty(bean.getOrgCode())) {
                assigneeOrgCode = bean.getOrgCode();
            } else {
                assigneeOrgCode = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgCode();
            }
            assigneeTopCompanyId = EanfangApplication.getApplication().getTopCompanyId();
            assigneeCompanyId = EanfangApplication.getApplication().getCompanyId();
        }
    }

//    private void showDependPerson() {
//        if (userlist == null || userlist.isEmpty()) {
//            showToast("暂无其他员工可选");
//            return;
//        }
//        pvOptions_NoLink = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                posistion = options1;
//                etPhoneNum.setText(userlist.get(posistion).getAccountEntity().getMobile());
//                tvDependPerson.setText(userlist.get(posistion).getAccountEntity().getRealName());
//
//
//                assigneeUserId = userlist.get(posistion).getUserId();
//                assigneeOrgCode = userlist.get(posistion).getDepartmentEntity().getOrgCode();
//                assigneeTopCompanyId = userlist.get(posistion).getCompanyEntity().getTopCompanyId();
//                assigneeCompanyId = userlist.get(posistion).getCompanyId();
//            }
//        }).build();
//        pvOptions_NoLink.setPicker(userNameList);
//        pvOptions_NoLink.show();
//    }
}
