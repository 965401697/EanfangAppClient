package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.bigkoo.pickerview.OptionsPickerView;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.ToastUtil;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    }


    @OnClick({R.id.ll_staff_in_time, R.id.ll_staff_out_time, R.id.ll_client_in_time, R.id.ll_client_out_time, R.id.ll_open_time, R.id.ll_close_time, R.id.ll_depend_person, R.id.ll_comit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_staff_in_time:
                PickerSelectUtil.onYearMonthDayTimePicker(OpenShopLogWriteActivity.this, "开始时间", tvStaffInTime);
                break;
            case R.id.ll_staff_out_time:
                PickerSelectUtil.onYearMonthDayTimePicker(OpenShopLogWriteActivity.this, "开始时间", tvStaffOutTime);
                break;
            case R.id.ll_client_in_time:
                PickerSelectUtil.onYearMonthDayTimePicker(OpenShopLogWriteActivity.this, "开始时间", tvClientInTime);
                break;
            case R.id.ll_client_out_time:
                PickerSelectUtil.onYearMonthDayTimePicker(OpenShopLogWriteActivity.this, "开始时间", tvClientOutTime);
                break;
            case R.id.ll_open_time:
                PickerSelectUtil.onYearMonthDayTimePicker(OpenShopLogWriteActivity.this, "开始时间", tvOpenTime);
                break;
            case R.id.ll_close_time:
                PickerSelectUtil.onYearMonthDayTimePicker(OpenShopLogWriteActivity.this, "开始时间", tvCloseTime);
                break;
            case R.id.ll_depend_person:
                showDependPerson();
                break;
            case R.id.ll_comit:
                sub();
                break;
        }
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
            object.put("cusEntryTime", tvClientInTime.getText().toString().trim());
            object.put("cusExitTime", tvClientOutTime.getText().toString().trim());
            object.put("recYardStaTime", tvOpenTime.getText().toString().trim());
            object.put("recYardEndTime", tvCloseTime.getText().toString().trim());

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
    private void showDependPerson() {
        if (userlist == null || userlist.isEmpty()) {
            showToast("暂无其他员工可选");
            return;
        }
        pvOptions_NoLink = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                posistion = options1;
                etPhoneNum.setText(userlist.get(posistion).getAccountEntity().getMobile());
                tvDependPerson.setText(userlist.get(posistion).getAccountEntity().getRealName());
                assigneeUserId = userlist.get(posistion).getUserId();
                assigneeOrgCode = userlist.get(posistion).getDepartmentEntity().getOrgCode();
                assigneeTopCompanyId = userlist.get(posistion).getCompanyEntity().getTopCompanyId();
                assigneeCompanyId = userlist.get(posistion).getCompanyId();
            }
        }).build();
        pvOptions_NoLink.setPicker(userNameList);
        pvOptions_NoLink.show();
    }
}
