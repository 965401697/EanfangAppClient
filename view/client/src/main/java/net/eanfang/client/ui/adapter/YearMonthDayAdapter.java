package net.eanfang.client.ui.adapter;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;

import java.util.List;


/**
 * @author liangkailun
 * Date ：2019-07-03
 * Describe :年月日选择
 */
public class YearMonthDayAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public YearMonthDayAdapter(@Nullable List<String> data) {
        super(R.layout.item_year_month_day, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_year_month_day_name, item);
    }
}
