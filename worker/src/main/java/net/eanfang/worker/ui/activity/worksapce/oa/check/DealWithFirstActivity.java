package net.eanfang.worker.ui.activity.worksapce.oa.check;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkCheckInfoBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.im.SelectIMContactActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/11/9
 * @description 查看设备点检详情 需要处理的
 */

public class DealWithFirstActivity extends BaseActivity {

    @BindView(R.id.iv_header)
    SimpleDraweeView ivHeader;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_task)
    TextView tvTask;
    @BindView(R.id.rv_task_list)
    RecyclerView rvTaskList;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;


    private long mId;
    private WorkCheckInfoBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_with_first);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("检查任务详情");
        setLeftBack();
        setRightTitle("分享");

        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享聊天

                Intent intent = new Intent(DealWithFirstActivity.this, SelectIMContactActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", String.valueOf(mBean.getId()));
                bundle.putString("orderNum", mBean.getCompanyName());
                if (mBean.getWorkInspectDetails() != null && !TextUtils.isEmpty(mBean.getWorkInspectDetails().get(0).getPictures())) {
                    bundle.putString("picUrl", mBean.getWorkInspectDetails().get(0).getPictures().split(",")[0]);
                }
                bundle.putString("creatTime", mBean.getTitle());
                bundle.putString("workerName", mBean.getCreateUser().getAccountEntity().getRealName());
                bundle.putString("status", "0");
                bundle.putString("shareType", "5");

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mId = getIntent().getLongExtra("id", 0);
        getData();
    }

    private void getData() {
        EanfangHttp.get(NewApiService.GET_WORK_CHECK_INFO)
                .tag(this)
                .params("id", mId)
                .execute(new EanfangCallback<WorkCheckInfoBean>(this, true, WorkCheckInfoBean.class, (bean) -> {
                            mBean = bean;
                            //头像
                            ivHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getAssigneeUser().getAccountEntity().getAvatar()));
                            tvCompany.setText(bean.getCompanyName());
                            tvDate.setText(bean.getCreateTime());
                            tvTime.setText(bean.getChangeDeadlineTime());
                            tvTitle.setText(bean.getTitle());
                            tvName.setText("接收人" + bean.getAssigneeUser().getAccountEntity().getRealName());
                            tvWeek.setText(GetDateUtils.dateToWeek(bean.getCreateTime()));

                            for (int i = 0; i < bean.getWorkInspectDetails().size(); i++) {
                                WorkCheckInfoBean.WorkInspectDetailsBean detialBean = bean.getWorkInspectDetails().get(i);
                                detialBean.setItemType(1);

                            }
                            if (bean.getWorkInspectDetails().size() == 0) {
                                tvTask.setVisibility(View.VISIBLE);
                            } else {
                                initAdapter(bean);
                            }

                        })
                );
    }


    private void initAdapter(WorkCheckInfoBean workCheckInfoBean) {
        CheckDetailAdapter taskDeatilAdapter = new CheckDetailAdapter(workCheckInfoBean.getWorkInspectDetails(), this);
        rvTaskList.setLayoutManager(new LinearLayoutManager(this));
        rvTaskList.setAdapter(taskDeatilAdapter);

        rvTaskList.setNestedScrollingEnabled(false);
        taskDeatilAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                WorkCheckInfoBean.WorkInspectDetailsBean b = (WorkCheckInfoBean.WorkInspectDetailsBean) adapter.getData().get(position);
                if (view.getId() == R.id.rl_show) {
                    b.setItemType(2);
                    adapter.notifyItemChanged(position);
                } else if (view.getId() == R.id.iv_pack) {
                    b.setItemType(1);
                    adapter.notifyItemChanged(position);
                }
            }
        });
    }

    @OnClick(R.id.tv_sub)
    public void onViewClicked() {
        // 处理详情
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", mBean);
    }
}
