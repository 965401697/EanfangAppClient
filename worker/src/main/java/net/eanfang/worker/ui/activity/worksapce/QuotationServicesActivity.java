package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.eanfang.model.QuotationBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/15  23:30
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class QuotationServicesActivity extends BaseActivity {
    @BindView(R.id.et_serviceName)
    EditText etServiceName;
    @BindView(R.id.et_serviceContent)
    EditText etServiceContent;
    @BindView(R.id.et_servicePrice)
    EditText etServicePrice;
    @BindView(R.id.et_serviceTimes)
    EditText etServiceTimes;
    @BindView(R.id.et_serviceValue)
    EditText etServiceValue;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private QuotationBean.QuoteServicesBean quoteServicesBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_services);
        ButterKnife.bind(this);
        setTitle("服务明细");
        setLeftBack();
        tvCommit.setOnClickListener(v -> commit());
    }

    private void commit() {

        if (!checkInfo()) {
            return;
        }

        quoteServicesBean = new QuotationBean.QuoteServicesBean();
        quoteServicesBean.setServiceContent(etServiceContent.getText().toString().trim());
        quoteServicesBean.setServiceName(etServiceName.getText().toString().trim());
        int servicePrice = Integer.valueOf(etServicePrice.getText().toString().trim());
        quoteServicesBean.setServicePrice(servicePrice * 100);
        int times = Integer.parseInt(etServiceTimes.getText().toString().trim());
        quoteServicesBean.setServiceTime(times);
        quoteServicesBean.setServiceValue(etServiceValue.getText().toString().trim());

        quoteServicesBean.setSum((servicePrice * times) * 100);
        setResult(4, getIntent().putExtra("quoteservices", quoteServicesBean));
        finish();
    }

    public boolean checkInfo() {


        if (StringUtils.isEmpty(etServiceName.getText().toString().trim())) {
            showToast("请输入服务名");
            return false;
        }
        if (StringUtils.isEmpty(etServiceContent.getText().toString().trim())) {
            showToast("请输入服务内容");
            return false;
        }

        if (StringUtils.isEmpty(etServicePrice.getText().toString().trim())) {
            showToast("请输入服务价格");
            return false;
        }
        if (StringUtils.isEmpty(etServiceTimes.getText().toString().trim())) {
            showToast("请输入服务次数");
            return false;
        }
        if (StringUtils.isEmpty(etServiceValue.getText().toString().trim())) {
            showToast("请输入服务标准");
            return false;
        }

        return true;

    }

}
