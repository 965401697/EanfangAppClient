package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.config.Config;
import com.eanfang.biz.model.QuotationBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.DeviceParamAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.base.kit.V.v;

/**
 * Created by MrHou
 *
 * @on 2018/1/17  10:25
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class QuotationDetailView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_business_type)
    TextView tvBusinessType;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.tv_brand_model)
    TextView tvBrandModel;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.et_amount)
    TextView etAmount;
    @BindView(R.id.et_price)
    TextView etPrice;
    @BindView(R.id.et_factory)
    TextView etFactory;
    @BindView(R.id.et_product_company)
    TextView etProductCompany;
    @BindView(R.id.rl_params)
    RecyclerView rlParams;
    @BindView(R.id.et_remark)
    TextView etRemark;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    QuotationBean.QuoteDevicesBean quoteDevicesBean;
    List<QuotationBean.QuoteDevicesBean.ParamsBean> paramsBeanList;
    private Activity mContext;
    private DeviceParamAdapter paramAdapter;

    public QuotationDetailView(Activity context, QuotationBean.QuoteDevicesBean quoteDevicesBean) {
        super(context);
        this.mContext = context;
        this.quoteDevicesBean = quoteDevicesBean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_quotation_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("设备明细");
        tvBusinessType.setText(Config.get().getBusinessNameByCode(quoteDevicesBean.getBusiness_three_code(), 1));
        tvDeviceType.setText(Config.get().getBusinessNameByCode(quoteDevicesBean.getBusiness_three_code(), 2));
        tvDeviceName.setText(Config.get().getBusinessNameByCode(quoteDevicesBean.getBusiness_three_code(), 3));
        tvBrandModel.setText(Config.get().getModelNameByCode(quoteDevicesBean.getModelCode(), 2));
        tvUnit.setText(GetConstDataUtils.getDeviceUnitList().get(quoteDevicesBean.getUnit()));
        etAmount.setText(v(() -> quoteDevicesBean.getCount() + ""));
        etPrice.setText(v(() -> (quoteDevicesBean.getUnitPrice() / 100)) + "");
        etFactory.setText(v(() -> quoteDevicesBean.getProducerPlace()));
        etRemark.setText(v(() -> quoteDevicesBean.getRemarkInfo()));
        etProductCompany.setText(v(() -> quoteDevicesBean.getProducerName()));
        paramsBeanList = quoteDevicesBean.getParams();
        if (paramsBeanList.size() != 0) {
            paramAdapter = new DeviceParamAdapter(R.layout.item_deveice_parm, (ArrayList) paramsBeanList);
            rlParams.setLayoutManager(new LinearLayoutManager(mContext));
            rlParams.setAdapter(paramAdapter);
        }
    }

}
