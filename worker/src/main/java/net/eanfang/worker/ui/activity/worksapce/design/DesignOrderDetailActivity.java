package net.eanfang.worker.ui.activity.worksapce.design;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.DesignOrderInfoBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.contacts.CooperactionRelationSubActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DesignOrderDetailActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_businss_classfiy)
    TextView tvBusinssClassfiy;
    @BindView(R.id.tv_work_time)
    TextView tvWorkTime;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_reply)
    TextView tvReply;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_order_detail);
        ButterKnife.bind(this);
        setTitle("设计明细");
        setLeftBack();
        id = getIntent().getStringExtra("id");
        initData();
    }

    private void initData() {
        EanfangHttp.get(NewApiService.GET_WORK_DESIGN_INFO)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<DesignOrderInfoBean>(this, true, DesignOrderInfoBean.class, (bean) -> {
                            initViews(bean);
                        })
                );
    }

    private void initViews(DesignOrderInfoBean bean) {
        tvCompanyName.setText(bean.getCompanyEntity().getName());
        tvContacts.setText(bean.getContactUser());
        tvPhone.setText(bean.getContactPhone());
        tvAddress.setText(Config.get().getAddressByCode(bean.getZoneCode()) + bean.getDetailPlace());

        tvBusinssClassfiy.setText(Config.get().getBusinessNameByCode(bean.getBusinessOneCode(), 1));
        tvWorkTime.setText(GetConstDataUtils.getPredictList().get(bean.getPredictTime()));
        tvDesc.setText(bean.getRemarkInfo());

        tvOrderNum.setText(String.valueOf(bean.getOrderNum()));
        tvOrderTime.setText(bean.getCreateTime());
        tvReply.setText(GetConstDataUtils.getBudgetList().get(bean.getBudgetLimit()));
    }
}
