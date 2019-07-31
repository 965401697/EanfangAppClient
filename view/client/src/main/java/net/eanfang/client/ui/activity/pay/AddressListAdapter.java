package net.eanfang.client.ui.activity.pay;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.ReceiveAddressEntity;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/9/4.
 */

public class AddressListAdapter extends BaseQuickAdapter<ReceiveAddressEntity, BaseViewHolder> {
    public AddressListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReceiveAddressEntity item) {
        if (item.getIsDefault() == 1) {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(true);
            helper.setVisible(R.id.tv_status, true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(false);
            helper.setVisible(R.id.tv_status, false);
        }

        helper.setText(R.id.tv_address, item.getProvince() + item.getCity() + item.getCounty() + item.getAddress());
        helper.setText(R.id.tv_preson, item.getName() + "        " + item.getPhone());

        helper.addOnClickListener(R.id.cb_checked);
        helper.addOnClickListener(R.id.iv_edit);
    }
}
