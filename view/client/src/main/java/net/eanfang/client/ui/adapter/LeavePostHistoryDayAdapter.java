package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHistoryDayBean;

import java.text.MessageFormat;

import cn.hutool.core.date.DateUtil;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :每日报警
 */
public class LeavePostHistoryDayAdapter extends BaseQuickAdapter<LeavePostHistoryDayBean.AlertListBean, LeavePostHistoryDayAdapter.LeavePostHomeViewHolder> {

    public LeavePostHistoryDayAdapter(int rec) {
        super(rec);
    }

    @Override
    protected void convert(LeavePostHomeViewHolder helper, LeavePostHistoryDayBean.AlertListBean item) {
        if (item == null) {
            return;
        }
        if (mData.indexOf(item) == 0) {
            helper.imgItemLeavePostHistoryDetailLineTop.setVisibility(View.GONE);
        }
        if (mData.indexOf(item) == getItemCount() - 1) {
            helper.imgItemLeavePostHistoryDetailLineBottom.setVisibility(View.GONE);
        }
        helper.tvItemLeavePostHistoryDetailName.setText(item.getAlertName());

        helper.tvItemLeavePostHistoryDetailDate.setText(MessageFormat.format("{0}\t-\t{1}", DateUtil.parse(item.getLeaveTime()).toString("HH:mm"), DateUtil.parse(item.getBackTime()).toString("HH:mm")));
        int hour = 60;
        helper.imgItemLeavePostHistoryDetailBar.post(() -> {
            int height = helper.imgItemLeavePostHistoryDetailBar.getHeight();
            int width = 0;
            if (item.getAbsencePeriod() < hour * 2) {
                width = (height * 30 * item.getAbsencePeriod()) / 120;
                if (item.getAbsencePeriod() > hour) {
                    helper.imgItemLeavePostHistoryDetailBar.setSelected(true);
                    helper.tvItemLeavePostHistoryDetailTime.setSelected(true);
                }
            } else {
                width = height * 30;
                helper.imgItemLeavePostHistoryDetailBar.setSelected(true);
                helper.tvItemLeavePostHistoryDetailTime.setSelected(true);
            }
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width, height);
            params.topToBottom = R.id.tv_item_leave_post_history_detail_date;
            params.bottomToBottom = R.id.img_content;
            params.leftToLeft = R.id.img_content;
            params.leftMargin = 20;
            helper.imgItemLeavePostHistoryDetailBar.setLayoutParams(params);
        });
        int h = item.getAbsencePeriod() / hour;
        int min = item.getAbsencePeriod() % hour;
        if (h > 0) {
            helper.tvItemLeavePostHistoryDetailTime.setText(MessageFormat.format("{0}h\t{1}min", h, min));
        } else {
            helper.tvItemLeavePostHistoryDetailTime.setText(MessageFormat.format("{0}min", min));
        }
    }

    class LeavePostHomeViewHolder extends BaseViewHolder {
        private TextView tvItemLeavePostHistoryDetailName;
        private ImageView imgItemLeavePostHistoryDetailLineTop;
        private ImageView imgItemLeavePostHistoryDetailLineBottom;
        private TextView tvItemLeavePostHistoryDetailDate;
        private ImageView imgItemLeavePostHistoryDetailBar;
        private TextView tvItemLeavePostHistoryDetailTime;

        public LeavePostHomeViewHolder(View view) {
            super(view);
            tvItemLeavePostHistoryDetailName = view.findViewById(R.id.tv_item_leave_post_history_detail_name);
            imgItemLeavePostHistoryDetailLineTop = view.findViewById(R.id.img_item_leave_post_history_detail_line_top);
            imgItemLeavePostHistoryDetailLineBottom = view.findViewById(R.id.img_item_leave_post_history_detail_line_bottom);
            tvItemLeavePostHistoryDetailDate = view.findViewById(R.id.tv_item_leave_post_history_detail_date);
            imgItemLeavePostHistoryDetailBar = view.findViewById(R.id.img_item_leave_post_history_detail_bar);
            tvItemLeavePostHistoryDetailTime = view.findViewById(R.id.tv_item_leave_post_history_detail_time);
        }
    }
}
