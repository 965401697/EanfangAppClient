package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.model.DesignOrderInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  20:13
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LookDesignOrderInfoView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Activity mContext;
    private int id;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_detail_address)
    TextView tv_detail_address;
    @BindView(R.id.tv_receive_user_name)
    TextView tv_receive_user_name;
    @BindView(R.id.tv_receive_phone)
    TextView tv_receive_phone;
    @BindView(R.id.tv_reply_limit)
    TextView tv_reply_limit;
    @BindView(R.id.tv_business_one)
    TextView tv_business_one;
    @BindView(R.id.tv_plan_limit)
    TextView tv_plan_limit;
    @BindView(R.id.tv_budget_limit)
    TextView tv_budget_limit;
    @BindView(R.id.tv_remark)
    TextView tv_remark;

    public LookDesignOrderInfoView(Activity context, boolean isfull, int id) {
        super(context, isfull);
        this.mContext = context;
        this.id = id;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_look_design_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("设计明细");
        ivLeft.setOnClickListener(v -> dismiss());
        getData();
    }

    private void getData() {
        doHttp(id);
    }

    private void fillData(DesignOrderInfoBean bean) {
        tv_user_name.setText(bean.getBean().getUserName());
        tv_address.setText(bean.getBean().getProvince() + bean.getBean().getCity() + bean.getBean().getCounty());
        tv_detail_address.setText(bean.getBean().getAddress());
        tv_receive_user_name.setText(bean.getBean().getReceiveUserName());
        tv_receive_phone.setText(bean.getBean().getReceivePhone());
        tv_reply_limit.setText(bean.getBean().getReplyLimit());
        tv_business_one.setText(bean.getBean().getBusinessOne());
        tv_plan_limit.setText(bean.getBean().getPlanLimit());
        tv_budget_limit.setText(bean.getBean().getBudgetLimit());
        tv_remark.setText(bean.getBean().getRemark());


    }

    private void doHttp(int id) {
        EanfangHttp.get(ApiService.GET_DESIGN_ORDER_INF)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<DesignOrderInfoBean>(mContext, true) {
                    @Override
                    public void onSuccess(DesignOrderInfoBean bean) {
                        fillData(bean);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
    }
}
