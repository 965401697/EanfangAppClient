package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.LookCheckDetailAdapter;
import net.eanfang.client.ui.model.WorkCheckInfoBean;

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
    private Activity mContext;
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


    private int id;
    private LookCheckDetailAdapter detailAdapter;
    private List<WorkCheckInfoBean.BeanBean.DetailsBeanX> mDataList;

    public WorkCheckInfoView(Activity context, boolean isfull, int id) {
        super(context, isfull);
        this.mContext = context;
        this.id = id;
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
        EanfangHttp.get(ApiService.GET_WORK_INSPECT_INFO)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<WorkCheckInfoBean>(mContext, true) {

                    @Override
                    public void onSuccess(final WorkCheckInfoBean bean) {
                        fillDta(bean);
                        initAdapter();
                        onItemClick(bean);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
    }

    /**
     * 填充数据数据
     */
    private void fillDta(WorkCheckInfoBean bean) {
        etCompanyName.setText(bean.getBean().getCompanyName());
        etCheckTime.setText(bean.getBean().getCreateDate());
        etCheckPerson.setText(bean.getBean().getCreateUserName());
        etDeadlineTime.setText(bean.getBean().getChangeDeadline());
        etTaskRequset.setText(bean.getBean().getChangeRequire());
        tvDependPerson.setText(bean.getBean().getReceiveUserName());
        etPhoneNum.setText(bean.getBean().getReceivePhone());
        mDataList = bean.getBean().getDetails();

    }

    /**
     * recyclerView item点击事件
     */
    private void onItemClick(WorkCheckInfoBean bean) {
        taskDetialList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new LookWorkCheckInfoView(mContext, true,
                        bean.getBean().getDetails().get(position),
                        bean.getBean().getReceiveUser(),
                        bean.getBean().getId()
                ).show();
            }
        });
    }
}
