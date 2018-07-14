package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.DefendLogDetailBean;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.ETimeUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.ToastUtil;
import com.yaf.base.entity.LogDetailsEntity;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DefendLogWriteActivity extends BaseClientActivity implements View.OnClickListener {

    @BindView(R.id.tv_open_time)
    TextView tvOpenTime;
    @BindView(R.id.tv_close_time)
    TextView tvCloseTime;
    @BindView(R.id.tv_depend_person)
    TextView tvDependPerson;
    @BindView(R.id.et_phone_num)
    TextView etPhoneNum;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_company_name)
    TextView etCompanyName;
    @BindView(R.id.et_section_name)
    TextView etSectionName;
    @BindView(R.id.ll_open_time)
    LinearLayout llOpenTime;
    @BindView(R.id.ll_close_time)
    LinearLayout llCloseTime;
    @BindView(R.id.ll_depend_person)
    LinearLayout llDependPerson;
    @BindView(R.id.ll_comit)
    Button llComit;
    private List<String> mTitleList = new ArrayList<>();
    private List<DefendLogItemAdapter> mAdapterList;

    private DefendLogItemAdapter currentItemAdapter;

    private OptionsPickerView pvOptions_NoLink;
    private List<UserEntity> userlist = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();
    private int posistion;

    private Long assigneeUserId;
    private String assigneeOrgCode;
    private Long assigneeTopCompanyId;
    private Long assigneeCompanyId;

    private final int ADD_CAUSE = 1;


    private TimePickerView mTimeYearMonthDayHMS;
    private TextView currentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defend_log_write);
        ButterKnife.bind(this);
        setTitle("布防日志");
        setLeftBack();
        initViews();

    }

    private void initViews() {


        llOpenTime.setOnClickListener(this);
        llCloseTime.setOnClickListener(this);
        llDependPerson.setOnClickListener(this);
        llComit.setOnClickListener(this);
        etCompanyName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
        etSectionName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());


        getData();


        mTitleList.add("旁路");
        mTitleList.add("闯防");
        mTitleList.add("误报");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DefendLogAdapter defendLogAdapter = new DefendLogAdapter(R.layout.item_defend_log, this, 1);
        defendLogAdapter.setNewData(mTitleList);
        mAdapterList = defendLogAdapter.getmList();//如果是添加数据  这里为空


        defendLogAdapter.bindToRecyclerView(recyclerView);

        defendLogAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_defend_add) {
                    currentItemAdapter = mAdapterList.get(position);
                    Intent intent = new Intent(DefendLogWriteActivity.this, DefendLogItemWriteAndDetailActivity.class);
                    intent.putExtra("title", mTitleList.get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, ADD_CAUSE);

                }
            }
        });

        doSelectYearMonthDayHMS();
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
        mTimeYearMonthDayHMS = new TimePickerBuilder(DefendLogWriteActivity.this, new OnTimeSelectListener() {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.ll_phone_num:
                break;
            case R.id.ll_comit:
                sub();
                break;
        }
    }


    /**
     * 获取公司部门员工信息
     */
    private void getData() {

        EanfangHttp.get(NewApiService.GET_COLLEAGUE)
                .tag(this)
                .params("id", EanfangApplication.getApplication().getUserId())
                .params("companyId", EanfangApplication.getApplication().getCompanyId())
                .execute(new EanfangCallback<UserEntity>(DefendLogWriteActivity.this, true, UserEntity.class, true, (list) -> {
                    userlist = list;
                    userNameList.addAll(Stream.of(userlist).map((user) -> user.getAccountEntity().getRealName()).toList());
                }));
    }


    private void sub() {

        if (!checkInfo()) return;


        if (GetDateUtils.getTimeStamp(tvCloseTime.getText().toString().trim(), "yyyy-MM-dd hh:mm:ss") <= GetDateUtils.getTimeStamp(tvOpenTime.getText().toString().trim(), "yyyy-MM-dd hh:mm:ss")) {
            Toast.makeText(this, "关闭时间不能小于开启时间", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject object = new JSONObject();
        try {
            object.put("openTime", tvOpenTime.getText().toString().trim());
            object.put("closeTime", tvCloseTime.getText().toString().trim());


            object.put("ownerUserId", EanfangApplication.getApplication().getUserId());
            object.put("ownerCompanyId", EanfangApplication.getApplication().getCompanyId());
            object.put("ownerTopCompanyId", EanfangApplication.getApplication().getTopCompanyId());
            object.put("ownerOrgCode", EanfangApplication.getApplication().getOrgCode());

            object.put("createUserId", EanfangApplication.getApplication().getUserId());

            object.put("assigneeUserId", assigneeUserId);
            object.put("assigneeCompanyId", assigneeCompanyId);
            object.put("assigneeTopCompanyId", assigneeTopCompanyId);
            object.put("assigneeOrgCode", assigneeOrgCode);
            String[] item = {"bypassList", "throughList", "falseList"};
            for (int i = 0; i < mAdapterList.size(); i++) {
                List<LogDetailsEntity> beanList = (List<LogDetailsEntity>) mAdapterList.get(i).getData();

                for (LogDetailsEntity bean : beanList) {
                    bean.setAssigneeUserId(assigneeUserId);
                    bean.setAssigneeCompanyId(assigneeCompanyId);
                    bean.setAssigneeTopCompanyId(assigneeTopCompanyId);
                    bean.setAssigneeOrgCode(assigneeOrgCode + "");
                }

                JSONArray array = new JSONArray();
                array.addAll(beanList);
                object.put(item[i], array);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        EanfangHttp.post(NewApiService.OA_SUB_DEFEND_LOG)
                .upJson(object.toJSONString())
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    ToastUtil.get().showToast(DefendLogWriteActivity.this, "提交成功");
                    finish();
                }));
    }

    /**
     * 责任人
     */

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
//                assigneeUserId = userlist.get(posistion).getUserId();
//                assigneeOrgCode = userlist.get(posistion).getDepartmentEntity().getOrgCode();
//                assigneeTopCompanyId = userlist.get(posistion).getCompanyEntity().getTopCompanyId();
//                assigneeCompanyId = userlist.get(posistion).getCompanyId();
//            }
//        }).build();
//        pvOptions_NoLink.setPicker(userNameList);
//        pvOptions_NoLink.show();
//    }

    private boolean checkInfo() {

        if (TextUtils.isEmpty(tvOpenTime.getText().toString().trim())) {
            showToast("开启时间不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvCloseTime.getText().toString().trim())) {
            showToast("关闭时间不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvDependPerson.getText().toString().trim())) {
            showToast("联系人不能为空");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_CAUSE) {
                LogDetailsEntity bean = (LogDetailsEntity) data.getSerializableExtra("bean");


                bean.setOwnerCompanyId(EanfangApplication.getApplication().getCompanyId());
                bean.setOwnerOrgCode(EanfangApplication.getApplication().getOrgCode());
                bean.setOwnerTopCompanyId(EanfangApplication.getApplication().getTopCompanyId());
                bean.setOwnerUserId(EanfangApplication.getApplication().getUserId());

                currentItemAdapter.addData(bean);
            }
        }

    }

}
