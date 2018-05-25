package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.DefendLogDetailBean;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.V;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.DefendLogAdapter;
import net.eanfang.client.ui.adapter.DefendLogItemAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DefendLogWriteAndDetailActivity extends BaseClientActivity implements View.OnClickListener {

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
    private int flag = 1;//1是添加操作
    private String mIsAdd;


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

        mIsAdd = getIntent().getStringExtra("add");

        if (!TextUtils.isEmpty(mIsAdd)) {
            llOpenTime.setOnClickListener(this);
            llCloseTime.setOnClickListener(this);
            llDependPerson.setOnClickListener(this);
            llComit.setOnClickListener(this);
            etCompanyName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
            etSectionName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());


            getData();
        } else {
            flag = 2;
            String mId = getIntent().getStringExtra("id");
            llComit.setVisibility(View.GONE);
            getDetail(mId);
        }

        mTitleList.add("旁路");
        mTitleList.add("闯防");
        mTitleList.add("误报");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DefendLogAdapter defendLogAdapter = new DefendLogAdapter(R.layout.item_defend_log, flag, this);
        defendLogAdapter.setNewData(mTitleList);
        mAdapterList = defendLogAdapter.getmList();//如果是添加数据  这里为空


        defendLogAdapter.bindToRecyclerView(recyclerView);

        defendLogAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!checkInfo()) return;
                if (view.getId() == R.id.tv_defend_add) {
                    currentItemAdapter = mAdapterList.get(position);
                    Intent intent = new Intent(DefendLogWriteAndDetailActivity.this, DefendLogItemWriteAndDetailActivity.class);
                    intent.putExtra("title", mTitleList.get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, ADD_CAUSE);

                }
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_open_time:
                PickerSelectUtil.onYearMonthDayTimePicker(DefendLogWriteAndDetailActivity.this, "", tvOpenTime);
                break;
            case R.id.ll_close_time:
                PickerSelectUtil.onYearMonthDayTimePicker(DefendLogWriteAndDetailActivity.this, "", tvCloseTime);
                break;
            case R.id.ll_depend_person:
                showDependPerson();
                break;
            case R.id.ll_phone_num:
                break;
            case R.id.ll_comit:
                sub();
                break;
        }
    }

    /**
     * 获取布防日志详情
     *
     * @param
     */
    private void getDetail(String id) {

        EanfangHttp.get(NewApiService.OA_DEFEND_LOG_DETAIL)
                .params("protectionLogId", id)
                .execute(new EanfangCallback<DefendLogDetailBean>(DefendLogWriteAndDetailActivity.this, true, DefendLogDetailBean.class, bean -> {

                    etCompanyName.setText(bean.getCreateUser().getCompanyEntity().getOrgName());
                    etSectionName.setText(bean.getCreateUser().getTopCompanyEntity().getOrgName());

                    tvOpenTime.setText(bean.getOpenTime());
                    tvCloseTime.setText(bean.getCloseTime());

                    tvDependPerson.setText(bean.getAssigneeUser().getAccountEntity().getRealName());
                    etPhoneNum.setText(bean.getAssigneeUser().getAccountEntity().getMobile());

                    List<List<DefendLogDetailBean.ListBean>> listBeans = new ArrayList<>();
                    listBeans.add(bean.getBypassList());
                    listBeans.add(bean.getThroughList());
                    listBeans.add(bean.getFalseList());

                    for (int i = 0; i < mAdapterList.size(); i++) {
                        mAdapterList.get(i).setNewData((List<DefendLogDetailBean.ListBean>) listBeans.get(i));
                    }


                }));
    }


    /**
     * 获取公司部门员工信息
     */
    private void getData() {

        EanfangHttp.get(NewApiService.GET_COLLEAGUE)
                .tag(this)
                .params("id", EanfangApplication.getApplication().getUserId())
                .params("companyId", EanfangApplication.getApplication().getCompanyId())
                .execute(new EanfangCallback<UserEntity>(DefendLogWriteAndDetailActivity.this, true, UserEntity.class, true, (list) -> {
                    userlist = list;
                    userNameList.addAll(Stream.of(userlist).map((user) -> user.getAccountEntity().getRealName()).toList());
                }));
    }


    private void sub() {

        if (!checkInfo()) return;

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
                List<DefendLogDetailBean.ListBean> beanList = (List<DefendLogDetailBean.ListBean>) mAdapterList.get(i).getData();
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
                    ToastUtil.get().showToast(DefendLogWriteAndDetailActivity.this, "提交成功");
                    finish();
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
                DefendLogDetailBean.ListBean bean = (DefendLogDetailBean.ListBean) data.getSerializableExtra("bean");

                bean.setAssigneeUserId(assigneeUserId + "");
                bean.setAssigneeCompanyId(assigneeCompanyId);
                bean.setAssigneeOrgCode(assigneeOrgCode + "");
//                bean.setAssigneeTopCompanyId(assigneeTopCompanyId);

                bean.setOwnerCompanyId(EanfangApplication.getApplication().getCompanyId());
                bean.setOwnerOrgCode(EanfangApplication.getApplication().getOrgCode());
                bean.setOwnerTopCompanyId(EanfangApplication.getApplication().getTopCompanyId());
                bean.setOwnerUserId(EanfangApplication.getApplication().getUserId() + "");

                currentItemAdapter.addData(bean);
            }
        }

    }

}
