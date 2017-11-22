package net.eanfang.client.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.LookTaskDetailAdapter;
import net.eanfang.client.ui.model.WorkTaskInfoBean;
import net.eanfang.client.ui.widget.LookTaskInfoView;

import java.util.List;

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
    private Activity mContext;
    private int id;
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

    private LookTaskDetailAdapter detailAdapter;
    private List<WorkTaskInfoBean.BeanBean.DetailsBean> mDataList;


    public WorkTaskInfoView(Activity context, boolean isfull, int id) {
        super(context, isfull);
        this.mContext = context;
        this.id = id;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_work_task_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("任务详情");
        getData();
    }

    private void initAdapter() {
        detailAdapter = new LookTaskDetailAdapter(R.layout.item_quotation_detail, mDataList);
        taskDetialList.setLayoutManager(new LinearLayoutManager(mContext));
        taskDetialList.setAdapter(detailAdapter);
    }

    private void getData() {
        EanfangHttp.get(ApiService.GET_WORK_TASK_INFO)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<WorkTaskInfoBean>(mContext, true) {

                    @Override
                    public void onSuccess(final WorkTaskInfoBean bean) {
                        etCompanyName.setText(bean.getBean().getCompanyName());
                        etDepartmentName.setText(bean.getBean().getDepartmentName());
                        etTaskName.setText(bean.getBean().getTitle());
                        tvDependPerson.setText(bean.getBean().getReceiveUserName());
                        etPhoneNum.setText(bean.getBean().getReceivePhone());
                        etPubTime.setText(bean.getBean().getCreateDate());
                        tvPubName.setText(bean.getBean().getCreateUserName());
                        mDataList = bean.getBean().getDetails();
                        initAdapter();
                        taskDetialList.addOnItemTouchListener(new OnItemClickListener() {
                            @Override
                            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                new LookTaskInfoView(mContext, true, bean.getBean().getDetails().get(position)).show();
                            }
                        });
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
    }
}
