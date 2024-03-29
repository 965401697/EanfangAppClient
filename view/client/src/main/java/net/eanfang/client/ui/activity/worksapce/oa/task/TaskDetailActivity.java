package net.eanfang.client.ui.activity.worksapce.oa.task;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.WorkTaskInfoBean;
import com.eanfang.util.DateKit;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.im.SelectIMContactActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskDetailActivity extends BaseClientActivity {

    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_section)
    TextView tvSection;
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
    @BindView(R.id.tv_check_title)
    TextView tvCheckTitle;
    private long mId;
    private WorkTaskInfoBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("任务详情");
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finishSelf();
            }
        });
        setRightTitle("分享");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享聊天

                Intent intent = new Intent(TaskDetailActivity.this, SelectIMContactActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", String.valueOf(mBean.getId()));
                bundle.putString("orderNum", mBean.getCreateOrg().getOrgName());
                if (mBean.getWorkTaskDetails() != null && !TextUtils.isEmpty(mBean.getWorkTaskDetails().get(0).getPictures())) {
                    bundle.putString("picUrl", mBean.getWorkTaskDetails().get(0).getPictures().split(",")[0]);
                }
                bundle.putString("creatTime", mBean.getTitle());
                bundle.putString("workerName", mBean.getCreateUser().getAccountEntity().getRealName());
                bundle.putString("status", String.valueOf(mBean.getStatus()));
                bundle.putString("shareType", "4");

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mId = getIntent().getLongExtra("id", 0);
        getData();
    }

    private void getData() {
        EanfangHttp.get(NewApiService.GET_WORK_TASK_INFO)
                .tag(this)
                .params("id", mId)
                .execute(new EanfangCallback<WorkTaskInfoBean>(this, true, WorkTaskInfoBean.class, (bean) -> {
                            mBean = bean;
                            //头像
                            GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + bean.getCreateUser().getAccountEntity().getAvatar()), ivHeader);
                            tvCompany.setText(bean.getCreateCompany().getOrgName());
                            tvSection.setText(bean.getCreateOrg().getOrgName());
                            tvDate.setText(bean.getCreateTime());
                            tvName.setText(bean.getCreateUser().getAccountEntity().getRealName());
                            tvWeek.setText(DateKit.get(bean.getCreateTime()).weekName());
                            tvCheckTitle.setText(bean.getTitle());

                            for (int i = 0; i < bean.getWorkTaskDetails().size(); i++) {
                                WorkTaskInfoBean.WorkTaskDetailsBean detialBean = bean.getWorkTaskDetails().get(i);
                                detialBean.setItemType(1);

                            }
                            if (bean.getWorkTaskDetails().size() == 0) {
                                tvTask.setVisibility(View.VISIBLE);
                            } else {
                                initAdapter(bean);
                            }

                        })
                );
    }


    private void initAdapter(WorkTaskInfoBean workTaskInfoBean) {
        TaskDetailAdapter taskDeatilAdapter = new TaskDetailAdapter(workTaskInfoBean.getWorkTaskDetails(), this);
        rvTaskList.setLayoutManager(new LinearLayoutManager(this));
        rvTaskList.setAdapter(taskDeatilAdapter);

        rvTaskList.setNestedScrollingEnabled(false);
        taskDeatilAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                WorkTaskInfoBean.WorkTaskDetailsBean b = (WorkTaskInfoBean.WorkTaskDetailsBean) adapter.getData().get(position);
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
