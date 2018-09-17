package net.eanfang.client.ui.activity.pay;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/8/29.
 */

public class MoneyAdapter extends BaseQuickAdapter<NewPayActivity.MoneyBean, BaseViewHolder> {

    public MoneyAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewPayActivity.MoneyBean item) {
        helper.setText(R.id.tv_title, item.title);
        helper.setText(R.id.tv_money, String.valueOf(item.money) + "元");
    }
}
