package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.biz.model.QuotationBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.util.V.v;

/**
 * Created by MrHou
 *
 * @on 2018/1/17  11:07
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class QuotationPartsView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_parts_name)
    TextView etPartsName;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.et_amount)
    TextView etAmount;
    @BindView(R.id.et_price)
    TextView etPrice;
    @BindView(R.id.et_partSpeciication)
    TextView etPartSpeciication;
    QuotationBean.QuotePartsBean quotePartsBean;
    private Activity mContext;

    public QuotationPartsView(Activity context, QuotationBean.QuotePartsBean quotePartsBean) {
        super(context);
        this.mContext = context;
        this.quotePartsBean = quotePartsBean;
    }


    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_quotation_parts);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("配件明细");
        etPartsName.setText(quotePartsBean.getPartName());
        tvUnit.setText(GetConstDataUtils.getDeviceUnitList().get(quotePartsBean.getUnit()));
        etAmount.setText(v(() -> (quotePartsBean.getCount() + "")));
        etPrice.setText(v(() -> (quotePartsBean.getUnitPrice() / 100)) + "");
        etPartSpeciication.setText(quotePartsBean.getPartSpeciication());
    }
}
