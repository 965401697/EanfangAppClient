package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceListBean;

import java.text.MessageFormat;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :图像查岗
 */
public class LeavePostCheckListAdapter extends BaseQuickAdapter<LeavePostDeviceListBean.ListBean, LeavePostCheckListAdapter.LeavePostViewHolder> {

    public LeavePostCheckListAdapter(int rec) {
        super(rec);
    }

    @Override
    protected void convert(LeavePostViewHolder helper, LeavePostDeviceListBean.ListBean item) {
        helper.tvItemLeavePostCheck.setText(MessageFormat.format("{0}\t({1})\t{2}", item.getStationName(), item.getStationCode(), item.getDeviceName()));

        GlideUtil.intoImageView(mContext, BuildConfig.OSS_SERVER + item.getDeviceEntity().getLivePic(), helper.imgItemLeavePostCheck);
    }

    class LeavePostViewHolder extends BaseViewHolder {
        private TextView tvItemLeavePostCheck;
        private ImageView imgItemLeavePostCheck;

        public LeavePostViewHolder(View view) {
            super(view);
            tvItemLeavePostCheck = view.findViewById(R.id.tv_item_leave_post_check);
            imgItemLeavePostCheck = view.findViewById(R.id.img_item_leave_post_check);
        }
    }
}
