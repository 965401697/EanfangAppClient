package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :报警详情事件adapter
 */
public class LeavePostDetailInfoAdapter extends BaseQuickAdapter<String, LeavePostDetailInfoAdapter.LeavePostHomeViewHolder> {

    public LeavePostDetailInfoAdapter() {
        super(R.layout.item_leave_post_detail_info);
    }

    @Override
    protected void convert(LeavePostHomeViewHolder helper, String item) {
        helper.tvLeavePostDetailInfo.setText(item);
    }

    class LeavePostHomeViewHolder extends BaseViewHolder {
        private TextView tvLeavePostDetailInfo;

        public LeavePostHomeViewHolder(View view) {
            super(view);
            tvLeavePostDetailInfo = view.findViewById(R.id.tv_leave_post_detail_info);
        }
    }
}
