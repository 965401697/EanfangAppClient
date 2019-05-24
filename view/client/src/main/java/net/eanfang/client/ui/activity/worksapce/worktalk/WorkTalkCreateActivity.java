package net.eanfang.client.ui.activity.worksapce.worktalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.GroupDetailBean;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.biz.model.WorkTalkDetailBean;
import com.eanfang.biz.model.WorkTransferDetailBean;
import com.eanfang.ui.activity.SelectOAPresonActivity;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.DialogUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.im.CreateGroupOrganizationActivity;
import net.eanfang.client.ui.activity.worksapce.oa.SelectOAGroupActivity;
import net.eanfang.client.ui.adapter.SendPersonAdapter;
import net.eanfang.client.util.SendContactUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/7/11  16:38
 * @decision 面谈员工 创建
 */
public class WorkTalkCreateActivity extends BaseActivity {

    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_department_name)
    TextView tvDepartmentName;
    @BindView(R.id.ll_department)
    LinearLayout llDepartment;
    @BindView(R.id.tv_talk_object)
    TextView tvTalkObject;
    @BindView(R.id.ll_talk_object)
    LinearLayout llTalkObject;
    @BindView(R.id.tv_receiver_name)
    TextView tvReceiverName;
    @BindView(R.id.ll_receiver_person)
    LinearLayout llReceiverPerson;
    @BindView(R.id.tv_telphone)
    TextView tvTelphone;
    @BindView(R.id.et_wrok_talk_one)
    EditText etWrokTalkOne;
    @BindView(R.id.et_wrok_talk_two)
    EditText etWrokTalkTwo;
    @BindView(R.id.et_wrok_talk_three)
    EditText etWrokTalkThree;
    @BindView(R.id.et_wrok_talk_four)
    EditText etWrokTalkFour;
    @BindView(R.id.et_wrok_talk_five)
    EditText etWrokTalkFive;
    @BindView(R.id.et_wrok_talk_six)
    EditText etWrokTalkSix;
    @BindView(R.id.et_wrok_talk_seven)
    EditText etWrokTalkSeven;
    @BindView(R.id.et_wrok_talk_eight)
    EditText etWrokTalkEight;
    @BindView(R.id.et_wrok_talk_nine)
    EditText etWrokTalkNine;
    @BindView(R.id.et_wrok_talk_ten)
    EditText etWrokTalkTen;
    @BindView(R.id.et_wrok_talk_eleven)
    EditText etWrokTalkEleven;
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.rv_team)
    RecyclerView rvTeam;
    @BindView(R.id.tv_send_group)
    TextView tvSendGroup;
    @BindView(R.id.rv_group)
    RecyclerView rvGroup;

    /**
     * 判断哪个进行选择 人员选择器
     * 面向对象false
     * 接收人 true
     */
    private boolean isWhitch = false;
    /**
     * 用户ID
     */
    private Long mUserId;
    /**
     * 当前公司Id
     */
    private Long mCompanyId;
    /**
     * 当前顶级公司Id
     */
    private Long mTopCompanyId;
    /**
     * 部门ID
     */
    private String mDepartmentId = "";
    /**
     * 面谈对象Id
     */
    private String mTalkId = "";
    /**
     * 接收人Id
     */
    private String mReceiverId = "";
    /**
     * 接收人部门id
     */
    private String mReceiverDeparrmentID = "";
    /**
     * 数据对象
     */
    private WorkTalkDetailBean workTalkDetailBean = new WorkTalkDetailBean();
    private List<WorkTransferDetailBean.ChangeGoodEntityListBean> changeGoodList = new ArrayList<>();
    private List<WorkTransferDetailBean.FinishWorkEntityListBean> finishWorkList = new ArrayList<>();
    private List<WorkTransferDetailBean.NotDidEntityListBean> unFinishList = new ArrayList<>();
    private List<WorkTransferDetailBean.FollowUpEntityListBean> followList = new ArrayList<>();
    private List<WorkTransferDetailBean.NoticeEntityListBean> noticeList = new ArrayList<>();

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
            ToastUtil.get().showToast(WorkTalkCreateActivity.this, "发送成功");
            finishSelf();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_talk_create);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) tv_company_name.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(tv_company_name.getApplicationWindowToken(), 0);
                }
                finishSelf();
            }
        });
        setTitle("面谈员工");
        mUserId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getUserId();
        mCompanyId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId();
        mTopCompanyId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getTopCompanyId();
        tv_company_name.setText(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());

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
        String targetId = getIntent().getStringExtra("targetId");
        if (!TextUtils.isEmpty(targetId)) {
            getGroupDetail(targetId);
        }
    }

    @OnClick({R.id.ll_department, R.id.ll_talk_object, R.id.ll_receiver_person, R.id.tv_send, R.id.tv_send_group, R.id.rl_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 面谈对象
            case R.id.ll_talk_object:
                isWhitch = false;
                Bundle bundle = new Bundle();
                bundle.putString("isRadio", "isRadio");
                JumpItent.jump(this, SelectOAPresonActivity.class, bundle);
                break;
            // 接收人
            case R.id.ll_receiver_person:
                Intent in_receiver = new Intent(this, SelectOrganizationActivity.class);
                in_receiver.putExtra("isRadio", "isRadio");
                startActivity(in_receiver);
                isWhitch = true;
                break;
            case R.id.tv_send:
                isSend = 1;
                isWhitch = true;
                //                startActivity(new Intent(this, SelectOAPresonActivity.class));

                Intent intent = new Intent(this, CreateGroupOrganizationActivity.class);
                intent.putExtra("isFrom", "OA");
                intent.putExtra("companyId", String.valueOf(EanfangApplication.getApplication().getCompanyId()));
                intent.putExtra("companyOrgCode", EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgCode());
                intent.putExtra("companyName", EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
                Bundle b = new Bundle();
                b.putSerializable("list", (Serializable) sendPersonAdapter.getData());
                intent.putExtras(b);
                startActivity(intent);
                break;
            case R.id.tv_send_group:
                isSend = 2;
                isWhitch = true;
                startActivityForResult(new Intent(this, SelectOAGroupActivity.class), REQUEST_CODE_GROUP);
                break;
            // 提交
            case R.id.rl_confirm:
                doSubmit();
                break;
            default:
                break;
        }
    }

    private void doSubmit() {
        if (!doCheckInfo()) {
            return;
        }
        EanfangHttp.post(NewApiService.WORK_TALK_ADD)
                .upJson(JSONObject.toJSONString(workTalkDetailBean))
                .execute(new EanfangCallback<WorkTalkDetailBean>(WorkTalkCreateActivity.this, true, WorkTalkDetailBean.class, (bean) -> {
                    //分享
                    if (newPresonList.size() == 0 && newGroupList.size() == 0) {
                        showToast("添加完毕");
                        EventBus.getDefault().post("addTalkSuccess");
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
                    b.putString("orderNum", bean.getOrderNum());

                    b.putString("creatTime", bean.getCreateTime());
                    if (newPresonList.size() > 0 && !newPresonList.get(0).getUserId().equals(EanfangApplication.get().getUserId())) {

                        b.putString("workerName", "接收人：" + newPresonList.get(0).getName());
                    } else {

                        b.putString("workerName", "创建人：" + EanfangApplication.get().getUser().getAccount().getRealName());
                    }
                    b.putString("status", "0");
                    b.putString("shareType", "7");


                    new SendContactUtils(b, handler, newGroupList, DialogUtil.createLoadingDialog(WorkTalkCreateActivity.this), "面谈员工").send();
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

    /**
     * 返回面谈对象
     */
    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (isSend == 1 && isWhitch) {

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
            TemplateBean.Preson bean = presonList.get(0);
            if (!isWhitch) {// 面谈对象
                tvTalkObject.setText(bean.getName());
                mTalkId = bean.getUserId();
                tvDepartmentName.setText(bean.getOrgName());
                if (!TextUtils.isEmpty(bean.getOrgCode())) {
                    mDepartmentId = bean.getOrgCode();
                }
            } else {// 接收人
//                tvReceiverName.setText(bean.getName());
//                mReceiverId = bean.getUserId();
//                tvTelphone.setText(bean.getMobile());
//                if (!TextUtils.isEmpty(bean.getOrgCode())) {
//                    mReceiverDeparrmentID = bean.getOrgCode();
//                }
//                if (isSend == 1) {
//
////                    Set hashSet = new HashSet();
////                    hashSet.addAll(sendPersonAdapter.getData());
////                    hashSet.addAll(presonList);
//
////                    if (newPresonList.size() > 0) {
////                        newPresonList.clear();
////                    }
////                    newPresonList.addAll(hashSet);
//                    newPresonList.clear();
//                    newPresonList.addAll(presonList);
//                    sendPersonAdapter.setNewData(newPresonList);
//
//                } else
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
//                            assigneeOrgCode = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgCode();
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
    }

    public boolean doCheckInfo() {
        try {
            if (StringUtils.isEmpty(mUserId + "")) {
                showToast("用户id为空");
                return false;
            } else {
                workTalkDetailBean.setOwnerUserId(mUserId + "");
            }

            if (StringUtils.isEmpty(mCompanyId + "")) {
                showToast("公司id为空");
                return false;
            } else {
                workTalkDetailBean.setOwnerCompanyId(mCompanyId + "");
                workTalkDetailBean.setOwnerTopCompanyId(mTopCompanyId + "");
                workTalkDetailBean.setOwnerOrgCode(mDepartmentId);
            }
            if (StringUtils.isEmpty(tvTalkObject.getText().toString().trim()) && StringUtils.isEmpty(mTalkId)) {
                showToast("请选择面谈对象");
                return false;
            } else {
                workTalkDetailBean.setWorkerUserId(mTalkId);
            }
//            if (StringUtils.isEmpty(tvReceiverName.getText().toString().trim()) && StringUtils.isEmpty(mReceiverId)) {
//                showToast("请选择接收人");
//                return false;
//            } else {
//                workTalkDetailBean.setAssigneeUserId(mReceiverId);
//                //接收人所在公司
//                workTalkDetailBean.setAssigneeCompanyId(mCompanyId + "");
//                //接收人顶级公司
//                workTalkDetailBean.setAssigneeTopCompanyId(mTopCompanyId + "");
//                //接收人部门编码
//                workTalkDetailBean.setAssigneeOrgCode(mReceiverDeparrmentID);
//            }

            if (newPresonList.size() == 0) {
                //工作协同默认值
                workTalkDetailBean.setAssigneeUserId(String.valueOf(EanfangApplication.get().getUserId()));
                workTalkDetailBean.setAssigneeOrgCode(EanfangApplication.get().getOrgCode());
                workTalkDetailBean.setAssigneeCompanyId(String.valueOf(EanfangApplication.getApplication().getCompanyId()));
                workTalkDetailBean.setAssigneeTopCompanyId(String.valueOf(EanfangApplication.getApplication().getTopCompanyId()));
            } else {
                //工作协同默认值
                workTalkDetailBean.setAssigneeUserId(newPresonList.get(0).getUserId());
                workTalkDetailBean.setAssigneeOrgCode(newPresonList.get(0).getOrgCode());
                workTalkDetailBean.setAssigneeCompanyId(String.valueOf(EanfangApplication.getApplication().getCompanyId()));
                workTalkDetailBean.setAssigneeTopCompanyId(String.valueOf(EanfangApplication.getApplication().getTopCompanyId()));
            }

            if (!StringUtils.isEmpty(etWrokTalkOne.getText().toString().trim())) {
                workTalkDetailBean.setQuestion1(etWrokTalkOne.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkTwo.getText().toString().trim())) {
                workTalkDetailBean.setQuestion2(etWrokTalkTwo.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkThree.getText().toString().trim())) {
                workTalkDetailBean.setQuestion3(etWrokTalkThree.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkFour.getText().toString().trim())) {
                workTalkDetailBean.setQuestion4(etWrokTalkFour.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkFive.getText().toString().trim())) {
                workTalkDetailBean.setQuestion5(etWrokTalkFive.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkSix.getText().toString().trim())) {
                workTalkDetailBean.setQuestion6(etWrokTalkSix.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkSeven.getText().toString().trim())) {
                workTalkDetailBean.setQuestion7(etWrokTalkSeven.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkEight.getText().toString().trim())) {
                workTalkDetailBean.setQuestion8(etWrokTalkEight.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkNine.getText().toString().trim())) {
                workTalkDetailBean.setQuestion9(etWrokTalkNine.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkTen.getText().toString().trim())) {
                workTalkDetailBean.setQuestion10(etWrokTalkTen.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkEleven.getText().toString().trim())) {
                workTalkDetailBean.setQuestion11(etWrokTalkEleven.getText().toString().trim());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_GROUP) {
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

}
