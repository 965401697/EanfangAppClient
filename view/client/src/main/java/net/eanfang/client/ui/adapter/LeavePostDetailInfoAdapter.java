package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostKeyValueBean;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :报警详情事件adapter
 */
public class LeavePostDetailInfoAdapter extends BaseQuickAdapter<LeavePostKeyValueBean, LeavePostDetailInfoAdapter.LeavePostHomeViewHolder> {

    public LeavePostDetailInfoAdapter(int rec) {
        super(rec);
    }

    @Override
    protected void convert(LeavePostHomeViewHolder helper, LeavePostKeyValueBean item) {
        if (item == null) {
            return;
        }
        helper.tvLeavePostDetailInfoName.setText(item.getName());
        helper.tvLeavePostDetailInfoValue.setText(item.getValue());
    }

    class LeavePostHomeViewHolder extends BaseViewHolder {
        private TextView tvLeavePostDetailInfoName;
        private TextView tvLeavePostDetailInfoValue;

        public LeavePostHomeViewHolder(View view) {
            super(view);
            tvLeavePostDetailInfoValue = view.findViewById(R.id.tv_leave_post_detail_info_value);
            tvLeavePostDetailInfoName = view.findViewById(R.id.tv_leave_post_detail_info_name);
        }
    }
}
