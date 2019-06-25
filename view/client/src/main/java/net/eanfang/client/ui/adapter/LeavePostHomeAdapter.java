package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeUnHandledAlertBean;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :脱岗监测首页
 */
public class LeavePostHomeAdapter extends BaseQuickAdapter<LeavePostHomeUnHandledAlertBean.UnhandledAlertListBean.ListBean, LeavePostHomeAdapter.LeavePostHomeViewHolder> {

    public LeavePostHomeAdapter(int rec) {
        super(rec);
    }

    @Override
    protected void convert(LeavePostHomeViewHolder helper, LeavePostHomeUnHandledAlertBean.UnhandledAlertListBean.ListBean item) {
        helper.itemLeavePostDate.setText(item.getAlertTime());
        helper.itemLeavePostType.setText(item.getAlertName());
        helper.itemLeavePostName.setText(item.getStationsEntity().getStationName());
        helper.tvLeavePostHomeCount.setText(String.valueOf(helper.getLayoutPosition() + 1));
    }

    class LeavePostHomeViewHolder extends BaseViewHolder {
        private TextView itemLeavePostType;
        private TextView itemLeavePostDate;
        private TextView itemLeavePostName;
        private TextView tvLeavePostHomeCount;
//        private TextView itemLeavePostName1;

        public LeavePostHomeViewHolder(View view) {
            super(view);
            itemLeavePostType = view.findViewById(R.id.item_leave_post_type);
            itemLeavePostDate = view.findViewById(R.id.item_leave_post_date);
            itemLeavePostName = view.findViewById(R.id.item_leave_post_name);
            tvLeavePostHomeCount = view.findViewById(R.id.tv_leave_post_home_count);
//            itemLeavePostName1 = view.findViewById(R.id.item_leave_post_name1);
        }
    }
}
