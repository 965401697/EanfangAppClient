package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.model.QuotationBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/15  22:59
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class QuotationPartsActivity extends BaseActivity {
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.rl_unit)
    RelativeLayout rlUnit;
    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_partSpeciication)
    EditText etPartSpeciication;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.titles_bar)
    LinearLayout titlesBar;
    @BindView(R.id.et_parts_name)
    EditText etPartsName;
    private QuotationBean.QuotePartsBean quotePartsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_parts);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("配件明细");
        setLeftBack();
        rlUnit.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvUnit, GetConstDataUtils.getDeviceUnitList());
        });
        tvCommit.setOnClickListener(v -> commit());

    }

    private void commit() {
        quotePartsBean = new QuotationBean.QuotePartsBean();
        int count = Integer.parseInt(etAmount.getText().toString().trim());
        quotePartsBean.setCount(count);
        quotePartsBean.setUnit(GetConstDataUtils.getDeviceUnitList().indexOf(tvUnit.getText().toString().trim()));
        int unitPrice = Integer.parseInt(etPrice.getText().toString().trim());
        quotePartsBean.setUnitPrice(Double.valueOf(unitPrice));
        quotePartsBean.setSum(Double.valueOf(unitPrice * count));
        quotePartsBean.setPartSpeciication(etPartSpeciication.getText().toString().trim());
        quotePartsBean.setPartName(etPartsName.getText().toString().trim());
        setResult(102, getIntent().putExtra("result", quotePartsBean));
        finish();
    }
}
