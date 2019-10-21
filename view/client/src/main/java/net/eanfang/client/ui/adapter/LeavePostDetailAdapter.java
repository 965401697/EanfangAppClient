package net.eanfang.client.ui.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.LeavePostDetailBean;
import com.eanfang.config.Config;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

import cn.hutool.core.util.StrUtil;


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
    protected void convert(LeavePostViewHolder helper, LeavePostDetailBean item) {
        if (item == null) {
            return;
        }
        GlideUtil.intoImageView(mContext, BuildConfig.OSS_SERVER + item.getImg(), helper.imgItemLeavePostManageDetail);
        helper.tvItemLeavePostManageDetailName.setText(mContext.getString(R.string.text_leave_post_detail_post_name, item.getName()));
        String area = StrUtil.isEmpty(Config.get().getAddressByCode(item.getAreaCode())) ?  item.getAreaCode() : Config.get().getAddressByCode(item.getAreaCode());
        helper.tvItemLeavePostManageDetailArea.setText(mContext.getString(R.string.text_leave_post_detail_area, area));
        if (item.getPageType() == 0) {
            helper.tvItemLeavePostManageDetailPosition.setText(mContext.getString(R.string.text_leave_post_detail_position, item.getPosition()));
            String postStatus = mContext.getString(R.string.text_leave_post_detail_status, item.getStatus() == 0 ? "不可用" : "启用");
            SpannableString spannableString = new SpannableString(postStatus);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6419")), 4, postStatus.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            helper.tvItemLeavePostManageDetailStatus.setText(spannableString);
        } else if (item.getPageType() == 1) {
            helper.tvItemLeavePostManageDetailUse.setVisibility(View.VISIBLE);
            helper.tvItemLeavePostManageDetailPosition.setVisibility(View.GONE);
            helper.imgItemLeavePostManageDetailNext.setVisibility(View.GONE);
            helper.checkboxItemLeavePostManageDetail.setVisibility(View.VISIBLE);
            helper.tvItemLeavePostManageDetailName.setText(mContext.getString(R.string.text_leave_post_detail_device_name, item.getName()));
            helper.tvItemLeavePostManageDetailArea.setText(mContext.getString(R.string.text_leave_post_detail_serial_num, item.getSerialNum()));
            helper.tvItemLeavePostManageDetailUse.setText(mContext.getString(R.string.text_leave_post_detail_use, item.getUse() == 0 ? "未占用" : "已占用"));
            String postStatus = mContext.getString(R.string.text_leave_post_detail_device_status, item.getStatus() == 0 ? "不可用" : "启用");
            SpannableString spannableString = new SpannableString(postStatus);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6419")), 4, postStatus.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            helper.tvItemLeavePostManageDetailStatus.setText(spannableString);
            helper.checkboxItemLeavePostManageDetail.setChecked(item.getChoosePosition() == mData.indexOf(item));
        } else {
            helper.tvItemLeavePostManageDetailCount.setVisibility(View.VISIBLE);
            helper.tvItemLeavePostManageDetailTime.setVisibility(View.VISIBLE);
            helper.tvItemLeavePostManageDetailStatus.setVisibility(View.GONE);
            helper.tvItemLeavePostManageDetailPosition.setText(mContext.getString(R.string.text_leave_post_detail_position, item.getPosition()));
            String count = mContext.getString(R.string.text_leave_post_detail_count, item.getCount());
            SpannableString spannableString = new SpannableString(count);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6419")), 4, count.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            helper.tvItemLeavePostManageDetailCount.setText(spannableString);
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
        private CheckBox checkboxItemLeavePostManageDetail;

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
            checkboxItemLeavePostManageDetail = view.findViewById(R.id.checkbox_item_leave_post_manage_detail);
            checkboxItemLeavePostManageDetail.setEnabled(false);
            checkboxItemLeavePostManageDetail.setClickable(false);
        }

    }
}
