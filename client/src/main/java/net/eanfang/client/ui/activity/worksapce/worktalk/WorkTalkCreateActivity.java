package net.eanfang.client.ui.activity.worksapce.worktalk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
     * 数据对象
     */
    private JSONObject object = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_talk_create);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("面谈员工");
        mUserId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getUserId();
        mCompanyId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId();
        tv_company_name.setText(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
    }

    @OnClick({R.id.ll_department, R.id.ll_talk_object, R.id.ll_receiver_person, R.id.rl_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 面谈对象
            case R.id.ll_talk_object:
                isWhitch = false;
                Intent in_talk = new Intent(this, SelectOrganizationActivity.class);
                in_talk.putExtra("isRadio", "isRadio");
                startActivity(in_talk);
                break;
            // 接收人
            case R.id.ll_receiver_person:
                Intent in_receiver = new Intent(this, SelectOrganizationActivity.class);
                in_receiver.putExtra("isRadio", "isRadio");
                startActivity(in_receiver);
                isWhitch = true;
                break;
            // 提交
            case R.id.rl_confirm:
                doSubmit();
                break;
        }
    }

    private void doSubmit() {
        if (!doCheckInfo())
            return;
        EanfangHttp.post(NewApiService.WORK_TALK_ADD)
                .upJson(object)
                .execute(new EanfangCallback<JSONObject>(WorkTalkCreateActivity.this, true, JSONObject.class, (bean) -> {
                    showToast("添加完毕");
                    finishSelf();
                }));
    }

    /**
     * 返回面谈对象
     */
    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {
        if (presonList.size() > 0) {
            TemplateBean.Preson bean = (TemplateBean.Preson) presonList.get(0);
            if (!isWhitch) {// 面谈对象
                tvTalkObject.setText(bean.getName());
                mTalkId = bean.getUserId();
                if (!TextUtils.isEmpty(bean.getOrgCode())) {
                    mDepartmentId = bean.getOrgCode();
                }
            } else {// 接收人
                tvReceiverName.setText(bean.getName());
                mReceiverId = bean.getUserId();
                tvTelphone.setText(bean.getMobile());
            }
        }
    }

    public boolean doCheckInfo() {
        try {
            if (StringUtils.isEmpty(mUserId + "")) {
                showToast("用户id为空");
                return false;
            } else {
                object.put("owner_user_id", mUserId);
            }

            if (StringUtils.isEmpty(mCompanyId + "")) {
                showToast("公司id为空");
                return false;
            } else {
                object.put("owner_top_company_id", mCompanyId);
            }
            if (StringUtils.isEmpty(tvTalkObject.getText().toString().trim()) && StringUtils.isEmpty(mTalkId)) {
                showToast("请选择面谈对象");
                return false;
            } else {
                object.put("worker_user_id", mTalkId);
                object.put("owner_department_id", mTalkId);
            }
            if (StringUtils.isEmpty(tvReceiverName.getText().toString().trim()) && StringUtils.isEmpty(mReceiverId)) {
                showToast("请选择接收人");
                return false;
            } else {
                object.put("assignee_user_id", mReceiverId);
            }
            if (!StringUtils.isEmpty(etWrokTalkOne.getText().toString().trim())) {
                object.put("question1", etWrokTalkOne.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkTwo.getText().toString().trim())) {
                object.put("question2", etWrokTalkTwo.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkThree.getText().toString().trim())) {
                object.put("question3", etWrokTalkThree.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkFour.getText().toString().trim())) {
                object.put("question4", etWrokTalkFour.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkFive.getText().toString().trim())) {
                object.put("question5", etWrokTalkFive.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkSix.getText().toString().trim())) {
                object.put("question6", etWrokTalkSix.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkSeven.getText().toString().trim())) {
                object.put("question7", etWrokTalkSeven.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkEight.getText().toString().trim())) {
                object.put("question8", etWrokTalkEight.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkNine.getText().toString().trim())) {
                object.put("question9", etWrokTalkNine.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkTen.getText().toString().trim())) {
                object.put("question10", etWrokTalkTen.getText().toString().trim());
            }
            if (!StringUtils.isEmpty(etWrokTalkEleven.getText().toString().trim())) {
                object.put("question11", etWrokTalkEleven.getText().toString().trim());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

}
