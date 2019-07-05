package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.biz.model.QuotationBean;
import com.eanfang.ui.base.BaseDialog;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.base.kit.V.v;

/**
 * Created by MrHou
 *
 * @on 2018/1/17  11:27
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class QuotationServiceView extends BaseDialog {
    QuotationBean.QuoteServicesBean servicesBean;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_serviceName)
    TextView etServiceName;
    @BindView(R.id.et_serviceContent)
    TextView etServiceContent;
    @BindView(R.id.et_servicePrice)
    TextView etServicePrice;
    @BindView(R.id.et_serviceTimes)
    TextView etServiceTimes;
    @BindView(R.id.et_serviceValue)
    TextView etServiceValue;

    public QuotationServiceView(Activity context, QuotationBean.QuoteServicesBean servicesBean) {
        super(context);
        this.servicesBean = servicesBean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_quotation_service);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("服务明细");
        etServiceName.setText(servicesBean.getServiceName());
        etServiceContent.setText(servicesBean.getServiceContent());
        etServicePrice.setText(v(() -> (servicesBean.getServicePrice() / 100)) + "");
        etServiceTimes.setText(servicesBean.getServiceTime() + "");
        etServiceValue.setText(servicesBean.getServiceValue());
    }
}
