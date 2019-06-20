package net.eanfang.worker.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.worker.R;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-05-28
 * Describe :
 */
@Getter
public class NewOrderViewHolder extends BaseViewHolder {
    private TextView tvNewOrderProjectName;
    private TextView tvNewOrderTenderee;
    private TextView tvNewOrderTitle;
    private TextView tvNewOrderTypeLeft;
    private TextView tvNewOrderArea;
    private TextView tvNewOrderPerson;
    private TextView tvNewOrderStartTime;
    private TextView tvNewOrderEndTime;
    private TextView tvNewOrderType;
    private TextView tvNewOrderWorker;
    private TextView tvNewOrdertimeRemaining;
    private TextView tvNewOrderPrice;

    public NewOrderViewHolder(View view) {
        super(view);
        tvNewOrderProjectName = view.findViewById(R.id.tv_newOrder_project_name);
        tvNewOrderTenderee = view.findViewById(R.id.tv_newOrder_tenderee);
        tvNewOrderTitle = view.findViewById(R.id.tv_newOrder_title);
        tvNewOrderTypeLeft = view.findViewById(R.id.tv_newOrder_type_left);
        tvNewOrderArea = view.findViewById(R.id.tv_newOrder_area);
        tvNewOrderPerson = view.findViewById(R.id.tv_newOrder_person);
        tvNewOrderStartTime = view.findViewById(R.id.tv_newOrder_start_time);
        tvNewOrderEndTime = view.findViewById(R.id.tv_newOrder_end_time);
        tvNewOrderType = view.findViewById(R.id.tv_newOrder_type);
        tvNewOrderWorker = view.findViewById(R.id.tv_newOrder_worker);
        tvNewOrdertimeRemaining = view.findViewById(R.id.tv_newOrder_time_remaining);
        tvNewOrderPrice = view.findViewById(R.id.tv_newOrder_price);
    }
}
