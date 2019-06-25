package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDetailBean;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :岗位详情
 */
public class LeavePostDetailAdapter extends BaseQuickAdapter<LeavePostDetailBean, LeavePostDetailAdapter.LeavePostViewHolder> {

    public LeavePostDetailAdapter() {
        super(R.layout.item_leave_post_manage_detail);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    protected void convert(LeavePostViewHolder helper, LeavePostDetailBean item) {
        GlideUtil.intoImageView(mContext, BuildConfig.OSS_SERVER + item.getImg(), helper.imgItemLeavePostManageDetail);
        helper.tvItemLeavePostManageDetailName.setText(mContext.getString(R.string.text_leave_post_detail_post_name, item.getName()));
        helper.tvItemLeavePostManageDetailArea.setText(mContext.getString(R.string.text_leave_post_detail_area, item.getAreaCode()));
        if (item.getPageType() == 0) {
            helper.tvItemLeavePostManageDetailPosition.setText(mContext.getString(R.string.text_leave_post_detail_position, item.getPosition()));
            helper.tvItemLeavePostManageDetailStatus.setText(mContext.getString(R.string.text_leave_post_detail_status, item.getStatus()));
        } else if (item.getPageType() == 1) {
            helper.tvItemLeavePostManageDetailUse.setVisibility(View.VISIBLE);
            helper.tvItemLeavePostManageDetailPosition.setVisibility(View.GONE);
            helper.imgItemLeavePostManageDetailNext.setVisibility(View.GONE);
            helper.tvItemLeavePostManageDetailName.setText(mContext.getString(R.string.text_leave_post_detail_device_name, item.getName()));
            helper.tvItemLeavePostManageDetailArea.setText(mContext.getString(R.string.text_leave_post_detail_serial_num, item.getSerialNum()));
            helper.tvItemLeavePostManageDetailUse.setText(mContext.getString(R.string.text_leave_post_detail_use, item.getUse()));
            helper.tvItemLeavePostManageDetailStatus.setText(mContext.getString(R.string.text_leave_post_detail_status, item.getStatus()));
        } else {
            helper.tvItemLeavePostManageDetailCount.setVisibility(View.VISIBLE);
            helper.tvItemLeavePostManageDetailTime.setVisibility(View.VISIBLE);
            helper.tvItemLeavePostManageDetailStatus.setVisibility(View.GONE);
            helper.tvItemLeavePostManageDetailPosition.setText(mContext.getString(R.string.text_leave_post_detail_position, item.getPosition()));
            helper.tvItemLeavePostManageDetailCount.setText(mContext.getString(R.string.text_leave_post_detail_count, item.getCount()));
            helper.tvItemLeavePostManageDetailTime.setText(mContext.getString(R.string.text_leave_post_detail_time, item.getTime()));
        }


    }

    class LeavePostViewHolder extends BaseViewHolder {
        private ImageView imgItemLeavePostManageDetail;
        private TextView tvItemLeavePostManageDetailName;
        private TextView tvItemLeavePostManageDetailArea;
        private TextView tvItemLeavePostManageDetailPosition;
        private TextView tvItemLeavePostManageDetailStatus;
        private TextView tvItemLeavePostManageDetailUse;
        private TextView tvItemLeavePostManageDetailCount;
        private TextView tvItemLeavePostManageDetailTime;
        private ImageView imgItemLeavePostManageDetailNext;

        public LeavePostViewHolder(View view) {
            super(view);
            imgItemLeavePostManageDetail = view.findViewById(R.id.img_item_leave_post_manage_detail);
            tvItemLeavePostManageDetailName = view.findViewById(R.id.tv_item_leave_post_manage_detail_name);
            tvItemLeavePostManageDetailArea = view.findViewById(R.id.tv_item_leave_post_manage_detail_area);
            tvItemLeavePostManageDetailPosition = view.findViewById(R.id.tv_item_leave_post_manage_detail_position);
            tvItemLeavePostManageDetailStatus = view.findViewById(R.id.tv_item_leave_post_manage_detail_status);
            tvItemLeavePostManageDetailUse = view.findViewById(R.id.tv_item_leave_post_manage_detail_use);
            tvItemLeavePostManageDetailCount = view.findViewById(R.id.tv_item_leave_post_manage_detail_count);
            tvItemLeavePostManageDetailTime = view.findViewById(R.id.tv_item_leave_post_manage_detail_time);
            imgItemLeavePostManageDetailNext = view.findViewById(R.id.img_item_leave_post_manage_detail_next);
        }
    }
}
