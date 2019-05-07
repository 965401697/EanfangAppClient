package net.eanfang.worker.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.customview.LeftRightTextEditText;
import com.eanfang.model.JxSbZzNlListBean;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description adapter
 */

public class MechanicalOrganizationAdapter extends BaseQuickAdapter<JxSbZzNlListBean.ListBean, BaseViewHolder> {

    public MechanicalOrganizationAdapter() {
        super(R.layout.layout_item_mechanical_organization);
    }

    @Override
    protected void convert(BaseViewHolder helper, JxSbZzNlListBean.ListBean item) {
        LeftRightTextEditText leftRightTextEditText = helper.getView(R.id.lr_tv);
        leftRightTextEditText.setTag(R.id.lr_tv, item.getDataId());
        if (!"0".equals(item.getCompany2baseDataEntity().getRemark())) {
            leftRightTextEditText.setText(item.getCompany2baseDataEntity().getRemark());
        }
        leftRightTextEditText.setLeftText(item.getDataName());
        leftRightTextEditText.setRightText(item.getCompany2baseDataEntity().getUnits());
        leftRightTextEditText.setSelection(leftRightTextEditText.getText().toString().trim().length());
        leftRightTextEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("wq656", "afterTextChanged: " + editable.toString() + "  ===   " + leftRightTextEditText.getText().toString().trim());
                if ("".equals(editable.toString().trim())) {
                    item.getCompany2baseDataEntity().setRemark("0");
                } else {
                    item.getCompany2baseDataEntity().setRemark(editable.toString().trim());
                }
            }
        });
    }
}
