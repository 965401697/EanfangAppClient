package net.eanfang.client.ui.activity.worksapce.worktransfer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkTransferDetailBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date on 2018/7/27  16:11
 * @decision 交接班详情
 */
public class WorkTransferDetailActivity extends BaseActivity {

    @BindView(R.id.iv_report_header)
    SimpleDraweeView ivReportHeader;
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
    @BindView(R.id.rv_hand_item)
    RecyclerView rvHandItem;
    @BindView(R.id.rv_finsh_work)
    RecyclerView rvFinshWork;
    @BindView(R.id.rv_unfinish_things)
    RecyclerView rvUnfinishThings;
    @BindView(R.id.rv_follow_things)
    RecyclerView rvFollowThings;
    @BindView(R.id.rv_attention_things)
    RecyclerView rvAttentionThings;
    private String mItemId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_transfer_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("日志详情");
        mItemId = getIntent().getStringExtra("itemId");
    }

    private void initData() {
        EanfangHttp.post(NewApiService.WORK_TRANSFER_DETAIL + mItemId)
                .execute(new EanfangCallback<WorkTransferDetailBean>(WorkTransferDetailActivity.this, true, WorkTransferDetailBean.class, bean -> {
                    initContent(bean);
                }));
    }

    private void initContent(WorkTransferDetailBean bean) {
        ivReportHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getOwnerUserEntity().getAccountEntity().getAvatar()));
        // 创建人
        tvTalkerName.setText(bean.getOwnerUserEntity().getAccountEntity().getNickName() + "(汇报人)");
        // 部门
        tvDepartment.setText(bean.getOwnerUserEntity().getDepartmentEntity().getOrgName());
        //时间
        String[] dataOne = bean.getCreateTime().split("-");
        String[] dateTwo = dataOne[2].split(" ");
        tvYear.setText(dataOne[0] + "-" + dataOne[1]);
        tvWeek.setText(GetDateUtils.dateToWeek(dataOne[0] + "-" + dataOne[1] + "-" + dateTwo[0]));
        tvData.setText(dateTwo[0]);
        //单位名称
        tvCompanyName.setText(bean.getOwnerUserEntity().getCompanyEntity().getOrgName());
        // 单位电话
        tvCompanyPhone.setText(bean.getOwnerUserEntity().getAccountEntity().getMobile());
        // 接收人
        tvAcceptPreson.setText(bean.getAssigneeUserEntity().getAccountEntity().getNickName());
        //接收人电话
        tvAcceptPhone.setText(bean.getAssigneeUserEntity().getAccountEntity().getMobile());
    }
}
