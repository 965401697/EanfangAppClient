package net.eanfang.client.ui.activity.worksapce.worktalk;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.WorkTalkDetailBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.DateKit;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/7/11  16:38
 * @decision 面谈员工 详情
 */
public class WorkTalkDetailActivity extends BaseActivity {

    @BindView(R.id.iv_report_header)
    CircleImageView ivReportHeader;
    @BindView(R.id.tv_talker_name)
    TextView tvTalkerName;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_company_phone)
    TextView tvCompanyPhone;
    @BindView(R.id.tv_accept_preson)
    TextView tvAcceptPreson;
    @BindView(R.id.tv_accept_phone)
    TextView tvAcceptPhone;
    @BindView(R.id.tv_talker_object)
    TextView tvTalkerObject;
    @BindView(R.id.tv_wrok_talk_one)
    TextView tvWrokTalkOne;
    @BindView(R.id.tv_wrok_talk_two)
    TextView tvWrokTalkTwo;
    @BindView(R.id.tv_wrok_talk_three)
    TextView tvWrokTalkThree;
    @BindView(R.id.tv_wrok_talk_four)
    TextView tvWrokTalkFour;
    @BindView(R.id.tv_wrok_talk_five)
    TextView tvWrokTalkFive;
    @BindView(R.id.tv_wrok_talk_six)
    TextView tvWrokTalkSix;
    @BindView(R.id.tv_wrok_talk_seven)
    TextView tvWrokTalkSeven;
    @BindView(R.id.tv_wrok_talk_eight)
    TextView tvWrokTalkEight;
    @BindView(R.id.tv_wrok_talk_nine)
    TextView tvWrokTalkNine;
    @BindView(R.id.tv_wrok_talk_ten)
    TextView tvWrokTalkTen;
    @BindView(R.id.tv_wrok_talk_eleven)
    TextView tvWrokTalkEleven;
    private String mItemId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_work_talk_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        EanfangHttp.post(NewApiService.WORK_TALK_DETAIL + mItemId)
                .execute(new EanfangCallback<WorkTalkDetailBean>(WorkTalkDetailActivity.this, true, WorkTalkDetailBean.class, bean -> {
                    initContent(bean);
                }));

    }

    private void initContent(WorkTalkDetailBean bean) {
        GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + bean.getOwnerUserEntity().getAccountEntity().getAvatar()), ivReportHeader);
        // 创建人
        tvTalkerName.setText(bean.getOwnerUserEntity().getAccountEntity().getRealName() + "(汇报人)");
        // 部门
        tvDepartment.setText(bean.getOwnerDepartmentEntity().getOrgName());
        //时间
        String[] dataOne = bean.getCreateTime().split("-");
        String[] dateTwo = dataOne[2].split(" ");
        tvYear.setText(dataOne[0] + "-" + dataOne[1]);
        tvWeek.setText(DateKit.get(bean.getCreateTime()).weekName());
        tvData.setText(dateTwo[0]);
        //单位名称
        tvCompanyName.setText(bean.getOwnerCompanyEntity().getOrgName());
        // 单位电话
        tvCompanyPhone.setText(bean.getOwnerUserEntity().getAccountEntity().getMobile());
        // 接收人
        tvAcceptPreson.setText(bean.getAssigneeUserEntity().getAccountEntity().getRealName());
        //接收人电话
        tvAcceptPhone.setText(bean.getAssigneeUserEntity().getAccountEntity().getMobile());
        //面谈对象
        tvTalkerObject.setText(bean.getWorkerUserEntity().getAccountEntity().getRealName());
        //问题
        tvWrokTalkOne.setText(bean.getQuestion1());
        tvWrokTalkTwo.setText(bean.getQuestion2());
        tvWrokTalkThree.setText(bean.getQuestion3());
        tvWrokTalkFour.setText(bean.getQuestion4());
        tvWrokTalkFive.setText(bean.getQuestion5());
        tvWrokTalkSix.setText(bean.getQuestion6());
        tvWrokTalkSeven.setText(bean.getQuestion7());
        tvWrokTalkEight.setText(bean.getQuestion8());
        tvWrokTalkNine.setText(bean.getQuestion9());
        tvWrokTalkTen.setText(bean.getQuestion10());
        tvWrokTalkEleven.setText(bean.getQuestion11());
    }


    private void initView() {
        setLeftBack();
        setTitle("面谈员工");
        mItemId = getIntent().getStringExtra("itemId");
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finishSelf();
            }
        });
    }

    /**
     * 监听 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(RESULT_OK);
            finishSelf();
        }
        return false;
    }
}
