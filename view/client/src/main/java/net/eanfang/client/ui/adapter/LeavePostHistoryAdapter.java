package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertHistoryBean;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :历史报警记录
 */
public class LeavePostHistoryAdapter extends BaseQuickAdapter<LeavePostAlertHistoryBean.ListBean, LeavePostHistoryAdapter.LeavePostHomeViewHolder> {

    public LeavePostHistoryAdapter(int rec) {
        super(rec);
    }

    @Override
    protected void convert(LeavePostHomeViewHolder helper, LeavePostAlertHistoryBean.ListBean item) {
        if (item == null) {
            return;
        }
        helper.itemLeavePostHistoryName.setText(mContext.getString(R.string.text_leave_post_history_alert_name, item.getAlertName()));
        helper.itemLeavePostHistoryDate.setText(item.getAlertTime());
        if (item.getStationsEntity() != null) {
            helper.itemLeavePostHistoryPosition.setText(mContext.getString(R.string.text_leave_post_detail_position, item.getStationsEntity().getStationArea()));
        }
        helper.imgLeavePostHistoryRz.setSelected(item.getStatus() == 1);
    }

    class LeavePostHomeViewHolder extends BaseViewHolder {
        private TextView itemLeavePostHistoryName;
        private TextView itemLeavePostHistoryPosition;
        private TextView itemLeavePostHistoryDate;
        private ImageView imgLeavePostHistoryRz;

        public LeavePostHomeViewHolder(View view) {
            super(view);
            itemLeavePostHistoryName = view.findViewById(R.id.item_leave_post_history_name);
            itemLeavePostHistoryPosition = view.findViewById(R.id.item_leave_post_history_position);
            itemLeavePostHistoryDate = view.findViewById(R.id.item_leave_post_history_date);
            imgLeavePostHistoryRz = view.findViewById(R.id.img_leave_post_history_rz);
        }
    }
}
