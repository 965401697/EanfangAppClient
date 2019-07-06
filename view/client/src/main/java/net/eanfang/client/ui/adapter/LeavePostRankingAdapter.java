package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDefaultRankingBean;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :排名
 */
public class LeavePostRankingAdapter extends BaseQuickAdapter<LeavePostDefaultRankingBean, LeavePostRankingAdapter.LeavePostHomeViewHolder> {

    public LeavePostRankingAdapter() {
        super(R.layout.item_leave_post_ranking);
    }

    @Override
    protected void convert(LeavePostHomeViewHolder helper, LeavePostDefaultRankingBean item) {
        if (item == null) {
            return;
        }
        helper.tvRank.setText(String.valueOf(mData.indexOf(item) + 1));
        helper.tvPostName.setText(item.getAlertName());
        helper.tvAlertCount.setText(item.getAlertsCount());
        helper.tvAlertChange.setText(String.valueOf(item.getCompareValue()));
    }

    class LeavePostHomeViewHolder extends BaseViewHolder {
        private TextView tvRank;
        private TextView tvPostName;
        private TextView tvAlertCount;
        private TextView tvAlertChange;

        public LeavePostHomeViewHolder(View view) {
            super(view);
            tvRank = view.findViewById(R.id.tv_rank);
            tvPostName = view.findViewById(R.id.tv_post_name);
            tvAlertCount = view.findViewById(R.id.tv_alert_count);
            tvAlertChange = view.findViewById(R.id.tv_alert_change);
        }
    }
}
