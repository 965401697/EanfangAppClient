package net.eanfang.worker.ui.activity.worksapce.sign;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.model.SectionBean;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.fragment.SelectTimeDialogFragment;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.oa.workreport.OAPersonAdaptet;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author guanluocang
 * @data 2018/11/14
 * @description 筛选足迹
 */

public class SignFiltrateActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, SelectTimeDialogFragment.SelectTimeListener {

    @BindView(R.id.titles_bar)
    RelativeLayout titlesBar;
    @BindView(R.id.rb_signin)
    RadioButton rbSignin;
    @BindView(R.id.rb_signout)
    RadioButton rbSignout;
    @BindView(R.id.rg_status)
    RadioGroup rgStatus;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.rl_select_department)
    RelativeLayout rlSelectDepartment;
    @BindView(R.id.rv_send_who)
    RecyclerView rvSendWho;
    @BindView(R.id.tv_sign_in_time)
    TextView tvSignInTime;
    @BindView(R.id.rl_signin_time)
    RelativeLayout rlSigninTime;
    @BindView(R.id.tv_sign_out_time)
    TextView tvSignOutTime;
    @BindView(R.id.rl_sign_out_time)
    RelativeLayout rlSignOutTime;
    @BindView(R.id.tv_complete_work)
    TextView tvCompleteWork;
    @BindView(R.id.tv_cancle_work)
    TextView tvCancleWork;

    private OAPersonAdaptet sendPersonAdapter;
    private List<TemplateBean.Preson> whoList = new ArrayList<>();
    public int mFlag;//人员选择器的标志位

    // 选择的部门
    private String mOrgCode;
    // 选择时间
    private TextView currentTextView;

    private static final int RESULT_FILTRATE = 1001;

    private int mSignStatus = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_filtrate);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("查找");
        rvSendWho.setLayoutManager(new GridLayoutManager(this, 5));
        sendPersonAdapter = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 2);
        rvSendWho.setAdapter(sendPersonAdapter);

        rgStatus.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.rl_select_department, R.id.rl_signin_time, R.id.rl_sign_out_time, R.id.tv_complete_work, R.id.tv_cancle_work})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_select_department:
                Intent intent = new Intent(SignFiltrateActivity.this, SelectOrganizationActivity.class);
                intent.putExtra("isSection", "isRadio");//是否是选择部门单选
                startActivity(intent);
                break;
            case R.id.rl_signin_time:
                currentTextView = tvSignInTime;
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.rl_sign_out_time:
                currentTextView = tvSignOutTime;
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.tv_complete_work:
                doSubmit();
                break;
            case R.id.tv_cancle_work:
                finishSelf();
                break;
            default:
                break;
        }
    }

    public void doSubmit() {
        QueryEntry queryEntry = new QueryEntry();
        if (mSignStatus != 100) {
            queryEntry.getEquals().put("status", mSignStatus + "");
        }
        if (!StringUtils.isEmpty(mOrgCode)) {
            queryEntry.getEquals().put("createOrgCode", mOrgCode);
        }
        if (whoList.size() != 0) {

            if (queryEntry == null) {
                queryEntry = new QueryEntry();
            }
            if (whoList.size() > 1) {
                List<String> idList = new ArrayList<>();

                for (TemplateBean.Preson p : whoList) {
                    idList.add(p.getUserId());
                }
                queryEntry.getIsIn().put("createUserId", idList);
            } else {
                TemplateBean.Preson p = whoList.get(0);
                queryEntry.getEquals().put("createUserId", p.getUserId());
            }
        }
        // 开始时间
        if (!StringUtils.isEmpty(tvSignInTime.getText().toString().trim())) {
            queryEntry.getGtEquals().put("beginTime", tvSignInTime.getText().toString().trim());
        }
        // 结束时间
        if (!StringUtils.isEmpty(tvSignOutTime.getText().toString().trim())) {
            queryEntry.getLtEquals().put("endTime", tvSignOutTime.getText().toString().trim());
        }

        Intent intent_finish = new Intent();
        intent_finish.putExtra("query_foot", queryEntry);
        setResult(RESULT_FILTRATE, intent_finish);
        finishSelf();
    }

    /**
     * 人员选择
     */
    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (presonList.size() > 0) {
            Set hashSet = new HashSet();
            if (mFlag == 2) {
                hashSet.addAll(sendPersonAdapter.getData());
            }

            hashSet.addAll(presonList);
            if (mFlag == 2) {
                whoList.clear();
                whoList.addAll(hashSet);
            }

            if (mFlag == 2) {
                sendPersonAdapter.setNewData(whoList);
            }

        }

    }

    @Subscribe
    public void onEvent(Object o) {

        if (o instanceof SectionBean) {
            SectionBean sectionBean = (SectionBean) o;
            tvDepartment.setText(sectionBean.getOrgName());
            mOrgCode = sectionBean.getOrgCode();
        }
    }


    public void setFlag(int flag) {
        this.mFlag = flag;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            // 签到
            case R.id.rb_signin:
                mSignStatus = 0;
                break;
            // 签退F
            case R.id.rb_signout:
                mSignStatus = 1;
                break;
            // 全部
            case R.id.rb_all:
                mSignStatus = 100;
                break;
            default:
                break;
        }
    }

    @Override
    public void getData(String time) {
        if (StringUtils.isEmpty(time) || " ".equals(time)) {
            currentTextView.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            currentTextView.setText(time);
        }
    }

}
