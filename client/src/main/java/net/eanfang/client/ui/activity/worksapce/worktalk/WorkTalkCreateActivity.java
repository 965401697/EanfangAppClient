package net.eanfang.client.ui.activity.worksapce.worktalk;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.witget.ScrollEditText;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/7/11  16:38
 * @decision 面谈员工 创建
 */
public class WorkTalkCreateActivity extends BaseActivity {

    @BindView(R.id.et_company_name)
    EditText etCompanyName;
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
    ScrollEditText etWrokTalkOne;
    @BindView(R.id.et_wrok_talk_two)
    ScrollEditText etWrokTalkTwo;
    @BindView(R.id.et_wrok_talk_three)
    ScrollEditText etWrokTalkThree;
    @BindView(R.id.et_wrok_talk_four)
    ScrollEditText etWrokTalkFour;
    @BindView(R.id.et_wrok_talk_five)
    ScrollEditText etWrokTalkFive;
    @BindView(R.id.et_wrok_talk_six)
    ScrollEditText etWrokTalkSix;
    @BindView(R.id.et_wrok_talk_seven)
    ScrollEditText etWrokTalkSeven;
    @BindView(R.id.et_wrok_talk_eight)
    ScrollEditText etWrokTalkEight;
    @BindView(R.id.et_wrok_talk_nine)
    ScrollEditText etWrokTalkNine;
    @BindView(R.id.et_wrok_talk_ten)
    ScrollEditText etWrokTalkTen;
    @BindView(R.id.et_wrok_talk_eleven)
    ScrollEditText etWrokTalkEleven;
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;

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
    }

    @OnClick({R.id.ll_department, R.id.ll_talk_object, R.id.ll_receiver_person, R.id.rl_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //部门名称F
            case R.id.ll_department:
                break;
            // 面谈对象
            case R.id.ll_talk_object:
                break;
            // 接收人
            case R.id.ll_receiver_person:
                break;
            // 提交
            case R.id.rl_confirm:
                break;
        }
    }
}
