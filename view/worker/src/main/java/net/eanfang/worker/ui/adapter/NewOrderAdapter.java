package net.eanfang.worker.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.model.NewOrderBean;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

/**
 * @author liangkailun
 * Date ：2019-05-28
 * Describe :最新订单adapter
 */
public class NewOrderAdapter extends BaseQuickAdapter<NewOrderBean.ListBean, NewOrderViewHolder> {
    public NewOrderAdapter() {
        super(R.layout.item_new_order);
    }

    @Override
    protected void convert(NewOrderViewHolder helper, NewOrderBean.ListBean item) {
        String typeMessage = "标讯";
        String typeJob = "用工找活";
        String typeRepair = "维修";
        String finishTime = item.getFinishTime();
        helper.getTvNewOrderPrice().setVisibility(View.GONE);
        helper.getTvNewOrdertimeRemaining().setVisibility(View.GONE);
        helper.getTvNewOrderProjectName().setVisibility(View.GONE);
        helper.getTvNewOrderTenderee().setVisibility(View.GONE);
        String newOrderPerson = item.getCompanyName() + " " + item.getPlaceOrderName();
        helper.getTvNewOrderTitle().setText(Config.get().getBaseNameByCode(item.getBussinessOneCode(), Constant.SYS_TYPE));
        helper.getTvNewOrderTypeLeft().setText(item.getOrderType());
        toShow(helper.getTvNewOrderArea(), Config.get().getAddressByCode(item.getPlaceCode()), R.string.text_new_order_area);
        toShow(helper.getTvNewOrderPerson(), newOrderPerson, R.string.text_new_order_person);
        toShow(helper.getTvNewOrderStartTime(), item.getCreateTime(), R.string.text_new_order_start_time);
        toShow(helper.getTvNewOrderEndTime(), finishTime, R.string.text_new_order_end_time);
        toShow(helper.getTvNewOrderWorker(), item.getTechnician(), R.string.text_new_order_worker);
        toShow(helper.getTvNewOrderType(), null, R.string.text_new_order_type_maintenance);

        if (typeMessage.equals(item.getOrderType())) {
            helper.getTvNewOrderPerson().setVisibility(View.GONE);
            toShow(helper.getTvNewOrderEndTime(), finishTime, R.string.text_new_order_time_end_get_file);
            toShow(helper.getTvNewOrderProjectName(), item.getProjectName(), R.string.text_new_order_name_program);
            toShow(helper.getTvNewOrderTenderee(), item.getTenderingUnit(), R.string.text_new_order_tenderee);
            toShow(helper.getTvNewOrderArea(), Config.get().getAddressByCode(item.getPlaceCode()), R.string.text_new_order_area1);
        } else if (typeJob.equals(item.getOrderType())) {
            helper.getTvNewOrderPrice().setVisibility(View.VISIBLE);
            toShow(helper.getTvNewOrderEndTime(), finishTime, R.string.text_new_order_time_reply_end);
            toShow(helper.getTvNewOrderWorker(), String.valueOf(item.getBudgetScale()), R.string.text_new_order_budget_scale);
        } else if ((typeRepair.equals(item.getOrderType()))) {
            helper.getTvNewOrderEndTime().setVisibility(View.GONE);
            helper.getTvNewOrderType().setVisibility(View.GONE);
            helper.getTvNewOrdertimeRemaining().setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(finishTime)) {
            //剩余时间
            long currentTime = System.currentTimeMillis() / 1000;
            long remainTime = GetDateUtils.convertDateToSecond(finishTime) - currentTime;
            if (remainTime > 0) {
                int oneDay = 24 * 60 * 60;
                int day = (int) (remainTime / oneDay);
                int oneHour = 60 * 60;
                int hour = (int) ((remainTime % oneDay) / oneHour);
                int oneMin = 60;
                int min = (int) (((remainTime % oneDay) % oneHour)) / oneMin;
                helper.getTvNewOrdertimeRemaining().setVisibility(View.VISIBLE);
                helper.getTvNewOrdertimeRemaining().setText(mContext.getString(R.string.text_new_order_time_remaining, day, hour, min));
            }
        }
    }

    /**
     * 内容为空不显示控件，否则展示内容
     *
     * @param textView
     * @param text
     * @param stringRes
     */
    private void toShow(TextView textView, String text, int stringRes) {
        if (StringUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setVisibility(View.VISIBLE);
        textView.setText(mContext.getString(stringRes, text));
    }
}
