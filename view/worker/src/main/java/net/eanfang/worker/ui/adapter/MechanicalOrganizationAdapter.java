package net.eanfang.worker.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.JxSbZzNlListBean;

import net.eanfang.worker.R;

/**
 * @author wq
 * @data 2018/10/23
 * @description adapter
 */

public class MechanicalOrganizationAdapter extends BaseQuickAdapter<JxSbZzNlListBean.ListBean, BaseViewHolder> {

    public MechanicalOrganizationAdapter() {
        super(R.layout.layout_item_mechanical_organization_b);
    }

    @Override
    protected void convert(BaseViewHolder helper, JxSbZzNlListBean.ListBean item) {
        EditText leftRightTextEditText = helper.getView(R.id.lr_tv);
        leftRightTextEditText.setTag(R.id.lr_tv, item.getDataId());
        if (!"0".equals(item.getCompany2baseDataEntity().getRemark())) {
            leftRightTextEditText.setText(item.getCompany2baseDataEntity().getRemark());
        }
        helper.setText(R.id.le_tv,item.getDataName());
        helper.setText(R.id.rt_tv,item.getCompany2baseDataEntity().getUnits());
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
