package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.DesignOrderInfoBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.client.R;

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
    private Activity mContext;
    private Long id;

    public LookDesignOrderInfoView(Activity context, boolean isfull, Long id) {
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
        tv_user_name.setText(bean.getUserName());
        tv_address.setText(Config.get().getAddressByCode(bean.getZoneCode()));
        tv_detail_address.setText(bean.getDetailPlace());
        tv_receive_user_name.setText(bean.getContactUser());
        tv_receive_phone.setText(bean.getContactPhone());
        tv_reply_limit.setText(GetConstDataUtils.getRevertList().get(bean.getRevertTimeLimit()));
        tv_business_one.setText(Config.get().getBusinessNameByCode(bean.getBusinessOneCode(), 1));
        tv_plan_limit.setText(GetConstDataUtils.getPredictList().get(bean.getPredictTime()));
        tv_budget_limit.setText(GetConstDataUtils.getBudgetList().get(bean.getBudgetLimit()));
        tv_remark.setText(bean.getRemarkInfo());


    }

    private void doHttp(Long id) {
        EanfangHttp.get(NewApiService.GET_WORK_DESIGN_INFO)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<DesignOrderInfoBean>(mContext, true, DesignOrderInfoBean.class, (bean) -> {
                            fillData(bean);
                        })
//                {
//                    @Override
//                    public void onSuccess(DesignOrderInfoBean bean) {
//                        fillData(bean);
//                    }
//
//                    @Override
//                    public void onError(String message) {
//
//                    }
//                }
                );
    }
}
