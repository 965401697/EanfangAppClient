package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.WorkTaskInfoBean;
import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.im.SelectIMContactActivity;
import net.eanfang.worker.ui.adapter.LookTaskDetailAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  17:53
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkTaskInfoView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.et_company_name)
    TextView etCompanyName;
    @BindView(R.id.et_department_name)
    TextView etDepartmentName;
    @BindView(R.id.et_task_name)
    TextView etTaskName;
    @BindView(R.id.task_detial_list)
    RecyclerView taskDetialList;
    @BindView(R.id.tv_pub_name)
    TextView tvPubName;
    @BindView(R.id.tv_depend_person)
    TextView tvDependPerson;
    @BindView(R.id.et_phone_num)
    TextView etPhoneNum;
    @BindView(R.id.et_pub_time)
    TextView etPubTime;
    @BindView(R.id.ll_depend_person)
    LinearLayout llDependPerson;
    @BindView(R.id.ll_phone_num)
    LinearLayout llPhoneNum;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private Activity mContext;
    private Long id;
    private LookTaskDetailAdapter detailAdapter;
    private List<WorkTaskInfoBean.WorkTaskDetailsBean> mDataList;
    private boolean isVisible;

    public WorkTaskInfoView(Activity context, boolean isfull, Long id, boolean isVisible) {
        super(context, isfull);
        this.mContext = context;
        this.id = id;
        this.isVisible = isVisible;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_work_task_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("任务");
        getData();
    }

    private void initAdapter() {
        detailAdapter = new LookTaskDetailAdapter(R.layout.item_quotation_detail, mDataList);
        taskDetialList.setLayoutManager(new LinearLayoutManager(mContext));
        taskDetialList.setAdapter(detailAdapter);
    }

    private void getData() {
        EanfangHttp.get(NewApiService.GET_WORK_TASK_INFO)
                .params("id", id)
                .execute(new EanfangCallback<WorkTaskInfoBean>(mContext, true, WorkTaskInfoBean.class, (bean) -> {
                    share(bean);
                    etCompanyName.setText(bean.getCreateCompany().getOrgName());
                    etDepartmentName.setText(bean.getCreateOrg().getOrgName());
                    etTaskName.setText(bean.getTitle());
                    tvDependPerson.setText(bean.getAssigneeUser().getAccountEntity().getRealName());
                    etPhoneNum.setText(bean.getAssigneeUser().getAccountEntity().getMobile());
                    etPubTime.setText(bean.getCreateTime());
                    tvPubName.setText(bean.getCreateUser().getAccountEntity().getRealName());
                    mDataList = bean.getWorkTaskDetails();
                    initAdapter();
                    taskDetialList.addOnItemTouchListener(new OnItemClickListener() {
                        @Override
                        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                            new LookTaskInfoView(mContext, true, bean.getWorkTaskDetails().get(position)).show();
                        }
                    });
                }));
    }

    private void share(WorkTaskInfoBean bean) {
        if (!isVisible) {
            tvRight.setText("分享");
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //分享聊天

                    Intent intent = new Intent(mContext, SelectIMContactActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("id", String.valueOf(bean.getId()));
                    bundle.putString("orderNum", bean.getCreateOrg().getOrgName());
                    if (bean.getWorkTaskDetails() != null && !TextUtils.isEmpty(bean.getWorkTaskDetails().get(0).getPictures())) {
                        bundle.putString("picUrl", bean.getWorkTaskDetails().get(0).getPictures().split(",")[0]);
                    }
                    bundle.putString("creatTime", bean.getTitle());
                    bundle.putString("workerName", bean.getCreateUser().getAccountEntity().getRealName());
                    bundle.putString("status", String.valueOf(bean.getStatus()));
                    bundle.putString("shareType", "4");

                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
