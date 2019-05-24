package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.biz.model.QuotationBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;

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

        if (!checkInfo()) {
            return;
        }

        quotePartsBean = new QuotationBean.QuotePartsBean();
        int count = Integer.parseInt(etAmount.getText().toString().trim());
        quotePartsBean.setCount(count);
        quotePartsBean.setUnit(GetConstDataUtils.getDeviceUnitList().indexOf(tvUnit.getText().toString().trim()));
        int unitPrice = Integer.valueOf(etPrice.getText().toString().trim());
        quotePartsBean.setUnitPrice(unitPrice * 100);
        quotePartsBean.setSum((unitPrice * count) * 100);
        quotePartsBean.setPartSpeciication(etPartSpeciication.getText().toString().trim());
        quotePartsBean.setPartName(etPartsName.getText().toString().trim());
        setResult(3, getIntent().putExtra("quoteparts", quotePartsBean));
        finish();
    }

    public boolean checkInfo() {


        if (StringUtils.isEmpty(etPartsName.getText().toString().trim())) {
            showToast("请输入配件名称");
            return false;
        }
        if (StringUtils.isEmpty(etAmount.getText().toString().trim())) {
            showToast("请输入数量");
            return false;
        }
        if (tvUnit.getText().toString().trim().equals("请选择")) {
            showToast("请先选择单位");
            return false;
        }
        if (StringUtils.isEmpty(etPrice.getText().toString().trim())) {
            showToast("请输入价钱");
            return false;
        }
        if (StringUtils.isEmpty(etPartSpeciication.getText().toString().trim())) {
            showToast("请输入配件规格");
            return false;
        }

        return true;

    }
}
