package net.eanfang.worker.ui.activity.worksapce.oa.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkCheckInfoBean;
import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.LookCheckDetailAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.fragment.WorkCheckListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2018/9/14
 * @description 设备点检详情
 */

public class WorkCheckInfoActivity extends BaseWorkerActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_company_name)
    TextView etCompanyName;
    @BindView(R.id.et_check_time)
    TextView etCheckTime;
    @BindView(R.id.et_check_person)
    TextView etCheckPerson;
    @BindView(R.id.et_deadline_time)
    TextView etDeadlineTime;
    @BindView(R.id.et_task_requset)
    TextView etTaskRequset;
    @BindView(R.id.task_detial_list)
    RecyclerView taskDetialList;
    @BindView(R.id.tv_depend_person)
    TextView tvDependPerson;
    @BindView(R.id.et_phone_num)
    TextView etPhoneNum;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.ll_depend_person)
    LinearLayout llDependPerson;
    @BindView(R.id.ll_phone_num)
    LinearLayout llPhoneNum;
    private Long id;
    private LookCheckDetailAdapter detailAdapter;
    private List<WorkCheckInfoBean.WorkInspectDetailsBean> mDataList;
    private int status;
    private int currrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_check_info);
        ButterKnife.bind(this);
        setTitle("检查工作");
        setLeftBack();
        id = getIntent().getLongExtra("id", 0);
        status = getIntent().getIntExtra("status", 0);
        getData();
    }

    /**
     * 填充
     */
    private void initAdapter() {
        detailAdapter = new LookCheckDetailAdapter(R.layout.item_quotation_detail, mDataList);
        taskDetialList.setLayoutManager(new LinearLayoutManager(this));
        taskDetialList.setAdapter(detailAdapter);
    }

    /**
     * 加载数据
     */
    private void getData() {
        EanfangHttp.get(NewApiService.GET_WORK_CHECK_INFO)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<WorkCheckInfoBean>(this, true, WorkCheckInfoBean.class, (bean) -> {
                    fillDta(bean);
                    initAdapter();
                    taskDetialList.addOnItemTouchListener(new OnItemClickListener() {
                        @Override
                        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                            new LookWorkCheckInfoView(WorkCheckInfoActivity.this, true,
//                                    bean,
//                                    bean.getWorkInspectDetails().get(position),
//                                    bean.getWorkInspectDetails().get(position).getId()
//                            ).show();
//                            if (status == ((WorkCheckInfoBean.WorkInspectDetailsBean) adapter.getData().get(position)).getStatus()) {
                            if (status == 0) {
                                if (!PermKit.get().getWorkInspectDisposePrem()) return;
                            } else if (status == 1) {
                                if (!PermKit.get().getWorkInspectVerifyPrem()) return;
                            }
                            currrentPosition = position;
                            Intent intent = getIntent();
                            intent.setClass(WorkCheckInfoActivity.this, LookWorkCheckInfoActivity.class);
                            intent.putExtra("infoBean", bean);
                            // TODO: 2018/8/22 待优化
                            intent.putExtra("detailsBean", detailAdapter.getData().get(position));
                            startActivityForResult(intent, WorkCheckListFragment.REQUST_REFRESH_CODE);
//                            } else {
//                                ToastUtil.get().showToast(WorkCheckInfoActivity.this, "已处理");
//                            }
                        }
                    });
                }));
    }

    /**
     * 填充数据数据
     */
    private void fillDta(WorkCheckInfoBean bean) {
        etCompanyName.setText(bean.getCompanyName());
        etCheckTime.setText(bean.getCreateTime());
        etCheckPerson.setText(bean.getCreateUser().getAccountEntity().getRealName());
        etDeadlineTime.setText(bean.getChangeDeadlineTime());
        etTaskRequset.setText(bean.getChangeInfo());
        tvDependPerson.setText(bean.getAssigneeUser().getAccountEntity().getRealName());
        etPhoneNum.setText(bean.getAssigneeUser().getAccountEntity().getMobile());
        mDataList = bean.getWorkInspectDetails();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            EanfangHttp.get(NewApiService.GET_WORK_CHECK_INFO)
                    .tag(this)
                    .params("id", id)
                    .execute(new EanfangCallback<WorkCheckInfoBean>(this, true, WorkCheckInfoBean.class, (bean) -> {
                        fillDta(bean);
                        initAdapter();

//                        for (WorkCheckInfoBean.WorkInspectDetailsBean b : detailAdapter.getData()) {
//                            if (b.getStatus() == status) {// TODO: 2018/8/23 待优化
//                                break;
//                            }
//                        }
                    }));
        }
    }
}
