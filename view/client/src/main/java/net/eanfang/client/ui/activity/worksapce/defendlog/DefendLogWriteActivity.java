package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.loading.LoadKit;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.biz.model.entity.LogDetailsEntity;
import com.eanfang.biz.model.entity.ProtectionLogEntity;
import com.eanfang.biz.model.entity.UserEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.im.CreateGroupOrganizationActivity;
import net.eanfang.client.ui.activity.worksapce.oa.SelectOAGroupActivity;
import net.eanfang.client.ui.adapter.SendPersonAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.util.SendContactUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

public class DefendLogWriteActivity extends BaseClientActivity implements View.OnClickListener, SelectTimeDialogFragment.SelectTimeListener {

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
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.rv_team)
    RecyclerView rvTeam;
    @BindView(R.id.tv_send_group)
    TextView tvSendGroup;
    @BindView(R.id.rv_group)
    RecyclerView rvGroup;

    private List<String> mTitleList = new ArrayList<>();
    private List<DefendLogItemAdapter> mAdapterList;

    private DefendLogItemAdapter currentItemAdapter;

    private List<UserEntity> userlist = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();

//    private Long assigneeUserId;
//    private String assigneeOrgCode;
//    private Long assigneeTopCompanyId;
//    private Long assigneeCompanyId;

    private final int ADD_CAUSE = 1;


    private TextView currentTextView;

    private final int REQUEST_CODE_GROUP = 102;
    private ArrayList<TemplateBean.Preson> newGroupList = new ArrayList<>();
    private SendPersonAdapter sendGroupAdapter;
    private int isSend = -1;

    private SendPersonAdapter sendPersonAdapter;
    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            ToastUtil.get().showToast(DefendLogWriteActivity.this, "发送成功");
            finishSelf();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_defend_log_write);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("布防日志");
        setLeftBack();
        initViews();

    }

    private void initViews() {


        llOpenTime.setOnClickListener(this);
        llCloseTime.setOnClickListener(this);
        llDependPerson.setOnClickListener(this);
        llComit.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        tvSendGroup.setOnClickListener(this);
        etCompanyName.setText(ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
        etSectionName.setText(ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());


        GridLayoutManager layoutManage = new GridLayoutManager(this, 5);
        rvTeam.setLayoutManager(layoutManage);

        GridLayoutManager manage = new GridLayoutManager(this, 5);
        rvGroup.setLayoutManager(manage);


        sendPersonAdapter = new SendPersonAdapter();
        sendPersonAdapter.bindToRecyclerView(rvTeam);
        sendPersonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TemplateBean.Preson preson = (TemplateBean.Preson) adapter.getData().get(position);
                adapter.getData().remove(preson);
                adapter.notifyDataSetChanged();
            }
        });

        sendGroupAdapter = new SendPersonAdapter();
        sendGroupAdapter.bindToRecyclerView(rvGroup);
        sendGroupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TemplateBean.Preson preson = (TemplateBean.Preson) adapter.getData().get(position);
                adapter.getData().remove(preson);
                adapter.notifyDataSetChanged();
            }
        });

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

//        doSelectYearMonthDayHMS();
        String targetId = getIntent().getStringExtra("targetId");
        if (!TextUtils.isEmpty(targetId)) {
            getGroupDetail(targetId);
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
//        mTimeYearMonthDayHMS = new TimePickerBuilder(DefendLogWriteActivity.this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                currentTextView.setText(ETimeUtils.getTimeByYearMonthDayHourMinSec(date));
//            }
//        })
//                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
//                .setCancelText("取消")//取消按钮文字
//                .setSubmitText("确定")//确认按钮文字
//                .setContentTextSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleText(" ")//标题文字
//                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(false)//是否显示为对话框样式
//                .build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_open_time:

                currentTextView = tvOpenTime;
                if (!SelectTimeDialogFragment.getInstance().isAdded()) {
                    SelectTimeDialogFragment.getInstance().show(getSupportFragmentManager(), R.string.app_name + "");
                }
//                mTimeYearMonthDayHMS.setDate(Calendar.getInstance());
//                mTimeYearMonthDayHMS.show();

                break;
            case R.id.ll_close_time:

                currentTextView = tvCloseTime;
                if (!SelectTimeDialogFragment.getInstance().isAdded()) {
                    SelectTimeDialogFragment.getInstance().show(getSupportFragmentManager(), R.string.app_name + "");
                }
//                mTimeYearMonthDayHMS.setDate(Calendar.getInstance());
//                mTimeYearMonthDayHMS.show();

                break;
            case R.id.ll_depend_person:
//                showDependPerson();

                isSend = 0;

                Intent intent = new Intent(this, SelectOrganizationActivity.class);
                intent.putExtra("isRadio", "isRadio");
                startActivity(intent);
                break;
            case R.id.tv_send:
                isSend = 1;

//                startActivity(new Intent(DefendLogWriteActivity.this, SelectOAPresonActivity.class));
                Intent in = new Intent(this, CreateGroupOrganizationActivity.class);
                in.putExtra("isFrom", "OA");
                in.putExtra("companyId", String.valueOf(ClientApplication.get().getCompanyId()));
                in.putExtra("companyOrgCode", ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgCode());
                in.putExtra("companyName", ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
                Bundle b = new Bundle();
                b.putSerializable("list", (Serializable) sendPersonAdapter.getData());
                in.putExtras(b);
                startActivity(in);
                break;
            case R.id.tv_send_group:
                isSend = 2;
                startActivityForResult(new Intent(DefendLogWriteActivity.this, SelectOAGroupActivity.class), REQUEST_CODE_GROUP);
                break;
            case R.id.ll_comit:
                sub();
                break;
            default:
                break;
        }
    }


    /**
     * 获取公司部门员工信息
     */
    private void getData() {

        EanfangHttp.get(NewApiService.GET_COLLEAGUE)
                .tag(this)
                .params("id", ClientApplication.get().getUserId())
                .params("companyId", ClientApplication.get().getCompanyId())
                .execute(new EanfangCallback<UserEntity>(DefendLogWriteActivity.this, true, UserEntity.class, true, (list) -> {
                    userlist = list;
                    userNameList.addAll(Stream.of(userlist).map((user) -> user.getAccountEntity().getRealName()).toList());
                }));
    }

    /**
     * 获取当前的群组信息
     *
     * @param targetId
     */
    private void getGroupDetail(String targetId) {

        EanfangHttp.post(UserApi.POST_GROUP_DETAIL_RY)
                .params("ryGroupId", targetId)
                .execute(new EanfangCallback<GroupDetailBean>(this, true, GroupDetailBean.class, (bean) -> {

                    TemplateBean.Preson preson = new TemplateBean.Preson();
                    preson.setName(bean.getGroup().getGroupName());
                    preson.setProtraivat(bean.getGroup().getHeadPortrait());
                    preson.setId(bean.getGroup().getRcloudGroupId());
                    newGroupList.add(preson);
                    sendGroupAdapter.setNewData(newGroupList);
                }));
    }

    private void sub() {

        if (!checkInfo()) {
            return;
        }


        if (DateUtil.parse(tvCloseTime.getText().toString().trim()).getTime() <= DateUtil.parse(tvOpenTime.getText().toString().trim()).getTime()) {
            Toast.makeText(this, "关闭时间不能小于开启时间", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject object = new JSONObject();
        try {
            object.put("openTime", tvOpenTime.getText().toString().trim());
            object.put("closeTime", tvCloseTime.getText().toString().trim());


            object.put("ownerUserId", ClientApplication.get().getUserId());
            object.put("ownerCompanyId", ClientApplication.get().getCompanyId());
            object.put("ownerTopCompanyId", ClientApplication.get().getTopCompanyId());
            object.put("ownerOrgCode", ClientApplication.get().getOrgCode());

            object.put("createUserId", ClientApplication.get().getUserId());
            if (newPresonList.size() == 0) {
                //工作协同默认值
                object.put("assigneeUserId", ClientApplication.get().getUserId());
                object.put("assigneeOrgCode", ClientApplication.get().getOrgCode());
            } else {
                //工作协同默认值
                object.put("assigneeUserId", newPresonList.get(0).getUserId());
                object.put("assigneeOrgCode", newPresonList.get(0).getOrgCode());
            }

            object.put("assigneeCompanyId", (ClientApplication.get().getCompanyId()));
            object.put("assigneeTopCompanyId", ClientApplication.get().getTopCompanyId());

            String[] item = {"bypassList", "throughList", "falseList"};
            for (int i = 0; i < mAdapterList.size(); i++) {
                List<LogDetailsEntity> beanList = mAdapterList.get(i).getData();

                for (LogDetailsEntity bean : beanList) {

                    if (newPresonList.size() == 0) {
                        //工作协同默认值
                        bean.setAssigneeUserId(ClientApplication.get().getUserId());
                        bean.setAssigneeOrgCode(ClientApplication.get().getOrgCode());
                        bean.setAssigneeCompanyId(ClientApplication.get().getCompanyId());
                        bean.setAssigneeTopCompanyId(ClientApplication.get().getTopCompanyId());
                    } else {
                        //工作协同默认值
                        bean.setAssigneeUserId(Long.parseLong(newPresonList.get(0).getUserId()));
                        bean.setAssigneeOrgCode(newPresonList.get(0).getOrgCode());
                        bean.setAssigneeCompanyId(ClientApplication.get().getCompanyId());
                        bean.setAssigneeTopCompanyId(ClientApplication.get().getTopCompanyId());
                    }
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
                .execute(new EanfangCallback<ProtectionLogEntity>(DefendLogWriteActivity.this, true, ProtectionLogEntity.class, bean -> {
                    EventBus.getDefault().post("addDefendLogSuccess");
                    //分享
                    if (newPresonList.size() == 0 && newGroupList.size() == 0) {
                        finishSelf();
                        return;
                    }
                    if (newGroupList.size() > 0) {


                        Set hashSet = new HashSet();
                        hashSet.addAll(sendGroupAdapter.getData());
                        hashSet.addAll(sendPersonAdapter.getData());

                        if (newGroupList.size() > 0) {
                            newGroupList.clear();
                        }

                        newGroupList.addAll(hashSet);
                    } else {
                        newGroupList.addAll(newPresonList);
                    }

                    Bundle b = new Bundle();

                    b.putString("id", String.valueOf(bean.getId()));
                    b.putString("orderNum", bean.getOrderNumber());

                    b.putString("creatTime", DateUtil.date(bean.getCreateTime()).toString("yyyy年MM月dd日 HH:mm:ss"));
//                    b.putString("workerName", bean.getCreateTime());
                    b.putString("status", "0");
                    b.putString("shareType", "9");


                    new SendContactUtils(b, handler, newGroupList, LoadKit.dialog(DefendLogWriteActivity.this), "布防日志").send();
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
        if (isSend == 1) {

//                    Set hashSet = new HashSet();
//                    hashSet.addAll(sendPersonAdapter.getData());
//                    hashSet.addAll(presonList);

//                    if (newPresonList.size() > 0) {
//                        newPresonList.clear();
//                    }
//                    newPresonList.addAll(hashSet);
            newPresonList.clear();
            newPresonList.addAll(presonList);
            sendPersonAdapter.setNewData(newPresonList);
            return;
        }

        if (presonList.size() > 0) {
//            if (isSend == 1) {
//
//                Set hashSet = new HashSet();
//                hashSet.addAll(sendPersonAdapter.getData());
//                hashSet.addAll(presonList);
//
//                if (newPresonList.size() > 0) {
//                    newPresonList.clear();
//                }
//                newPresonList.addAll(hashSet);
//                sendPersonAdapter.setNewData(newPresonList);
//
//            } else
            if (isSend == 0) {
//                        TemplateBean.Preson bean = (TemplateBean.Preson) presonList.get(0);
//
//                        etPhoneNum.setText(bean.getMobile());
//                        tvDependPerson.setText(bean.getName());
//
//                        assigneeUserId = Long.parseLong(bean.getUserId());
//                        if (bean.getOrgCode() != null && !TextUtils.isEmpty(bean.getOrgCode())) {
//                            assigneeOrgCode = bean.getOrgCode();
//                        } else {
//                            assigneeOrgCode = ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgCode();
//                        }
            } else {

                Set hashSet = new HashSet();
                hashSet.addAll(sendGroupAdapter.getData());
                hashSet.addAll(presonList);

                if (newGroupList.size() > 0) {
                    newGroupList.clear();
                }
                newGroupList.addAll(hashSet);

                sendGroupAdapter.setNewData(newGroupList);
            }

        }
    }

    private boolean checkInfo() {

        if (TextUtils.isEmpty(tvOpenTime.getText().toString().trim())) {
            showToast("开启时间不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvCloseTime.getText().toString().trim())) {
            showToast("关闭时间不能为空");
            return false;
        }
//        if (TextUtils.isEmpty(tvDependPerson.getText().toString().trim())) {
//            showToast("联系人不能为空");
//            return false;
//        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_CAUSE) {
                LogDetailsEntity bean = (LogDetailsEntity) data.getSerializableExtra("bean");


                bean.setOwnerCompanyId(ClientApplication.get().getCompanyId());
                bean.setOwnerOrgCode(ClientApplication.get().getOrgCode());
                bean.setOwnerTopCompanyId(ClientApplication.get().getTopCompanyId());
                bean.setOwnerUserId(ClientApplication.get().getUserId());

                currentItemAdapter.addData(bean);
            } else if (requestCode == REQUEST_CODE_GROUP) {
                TemplateBean.Preson preson = (TemplateBean.Preson) data.getSerializableExtra("bean");
                if (sendGroupAdapter.getData().size() > 0) {
                    if (!sendGroupAdapter.getData().contains(preson)) {
                        sendGroupAdapter.addData(preson);
                        newGroupList.add(preson);
                    }
                } else {

                    sendGroupAdapter.addData(preson);
                    newGroupList.add(preson);
                }
            }
        }

    }

    @Override
    public void getData(String time) {
        if (StrUtil.isEmpty(time) || " ".equals(time)) {
            currentTextView.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            currentTextView.setText(time);
        }
    }
}
