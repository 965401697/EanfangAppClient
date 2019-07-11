package net.eanfang.client.ui.adapter;


import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :脱岗监测首页
 */
public class LeavePostChooseAlertTimeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private String mAlertTime;
    public LeavePostChooseAlertTimeAdapter(String alertTime) {
        super(R.layout.item_choose_alert_time);
        this.mAlertTime = alertTime;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text, item);
        ((RadioButton)helper.getView(R.id.checkbox_alert)).setChecked(item.equals(mAlertTime));
    }

}
