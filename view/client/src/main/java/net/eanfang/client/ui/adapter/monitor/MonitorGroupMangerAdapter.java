package net.eanfang.client.ui.adapter.monitor;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.bean.monitor.RealTimeGroupEntity;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/9/9
 * @description 实时监控 F分组管理
 */
public class MonitorGroupMangerAdapter extends BaseQuickAdapter<RealTimeGroupEntity, BaseViewHolder> {


    public OnFirstItemClickListener onFirstItemClickListener;

    public MonitorGroupMangerAdapter(OnFirstItemClickListener mOnFirstItemClickListener) {
        super(R.layout.layout_monitor_group_manager_item);
        this.onFirstItemClickListener = mOnFirstItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, RealTimeGroupEntity item) {
        TextView mName = helper.getView(R.id.tv_groupName);
        TextView mDeviceCount = helper.getView(R.id.tv_deviceCount);
        LinearLayout llSecond = helper.getView(R.id.ll_item);
        View mWhite = helper.getView(R.id.view_white);
        /**
         * 是一级
         * */
        if (item.isHaveSubGroup()) {
            mName.setTextColor(ContextCompat.getColor(BaseApplication.get().getApplicationContext(), R.color.colorPrimary));
            mWhite.setVisibility(View.GONE);
        } else {
            mName.setTextColor(ContextCompat.getColor(BaseApplication.get().getApplicationContext(), R.color.color_client_neworder));
            mWhite.setVisibility(View.VISIBLE);
        }
        mName.setText(item.getGroupName());
        mDeviceCount.setText(item.getDeviceCount() + "个设备");
        llSecond.setOnClickListener((v) -> {
//            if (!item.isHaveSubGroup()) {
            onFirstItemClickListener.onItemClick(helper.getAdapterPosition(), item.getGroupName());
//            }
        });
    }

    public interface OnFirstItemClickListener {
        /**
         * @param posititon
         * @param mDeviceName
         */
        void onItemClick(int posititon, String mDeviceName);
    }
}
