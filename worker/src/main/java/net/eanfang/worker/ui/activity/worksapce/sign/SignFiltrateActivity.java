package net.eanfang.worker.ui.activity.worksapce.sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.model.OrganizationBean;
import com.eanfang.model.SectionBean;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.oa.workreport.OAPersonAdaptet;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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

public class SignFiltrateActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

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
    private String parentOrgId;
    private String topCompanyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_filtrate);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
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

                break;
            case R.id.rl_sign_out_time:
                break;
            case R.id.tv_complete_work:
                break;
            case R.id.tv_cancle_work:
                finishSelf();
                break;
        }
    }

    public void doSubmint() {
        if (whoList.size() == 0) {
            //工作协同默认值
            showToast("请选择发送给谁");
            return;
        }
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

        if (o instanceof OrganizationBean) {
            OrganizationBean organizationBean = (OrganizationBean) o;
            tvDepartment.setText(organizationBean.getOrgName());
            topCompanyId = organizationBean.getTopCompanyId();

        } else if (o instanceof SectionBean) {
            SectionBean sectionBean = (SectionBean) o;
            tvDepartment.setText(sectionBean.getOrgName());
            parentOrgId = sectionBean.getOrgId();
        }
    }


    public void setFlag(int flag) {
        this.mFlag = flag;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.rb_signin:
                break;
            case R.id.rb_signout:
                break;
        }
    }

}
