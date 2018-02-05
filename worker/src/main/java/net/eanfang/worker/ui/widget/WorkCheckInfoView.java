package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkCheckInfoBean;
import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.LookCheckDetailAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  16:43
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkCheckInfoView extends BaseDialog {
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
    private Activity mContext;
    private Long id;
    private LookCheckDetailAdapter detailAdapter;
    private List<WorkCheckInfoBean.WorkInspectDetailsBean> mDataList;

    public WorkCheckInfoView(Activity context, boolean isfull, Long id) {
        super(context, isfull);
        this.mContext = context;
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_work_check_info);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("检查工作");
        getData();
    }

    /**
     * 填充
     */
    private void initAdapter() {
        detailAdapter = new LookCheckDetailAdapter(R.layout.item_quotation_detail, mDataList);
        taskDetialList.setLayoutManager(new LinearLayoutManager(mContext));
        taskDetialList.setAdapter(detailAdapter);
    }

    /**
     * 加载数据
     */
    private void getData() {
        EanfangHttp.get(NewApiService.GET_WORK_CHECK_INFO)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<WorkCheckInfoBean>(mContext, true, WorkCheckInfoBean.class, (bean) -> {
                    fillDta(bean);
                    initAdapter();
                    taskDetialList.addOnItemTouchListener(new OnItemClickListener() {
                        @Override
                        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                            new LookWorkCheckInfoView(mContext, true,
                                    bean,
                                    bean.getWorkInspectDetails().get(position),
                                    bean.getWorkInspectDetails().get(position).getId()
                            ).show();
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

}
