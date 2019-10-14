package net.eanfang.client.ui.adapter.monitor;

import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.base.BaseApplication;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/10/10
 * @description 摄像机详情 时间
 */
public class MonitorDeviceDetailTimeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public OnItemClickListener mOnItemClickListener;
    private String mClickItem;

    public MonitorDeviceDetailTimeAdapter(OnItemClickListener onItemClickListener) {
        super(R.layout.layout_item_monitor_detail_time);
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        String clickItem = item;
        TextView mTime = helper.getView(R.id.tv_time);
        mTime.setText(item);

        if (mClickItem != null && clickItem.equals(mClickItem)) {
            mTime.setTextColor(ContextCompat.getColor(BaseApplication.get().getApplicationContext(), R.color.color_white));
            mTime.setBackgroundResource(R.drawable.bg_monitor_time_back);
        } else {
            mTime.setTextColor(ContextCompat.getColor(BaseApplication.get().getApplicationContext(), R.color.color_monitor_text));
            mTime.setBackground(null);
        }
        mTime.setOnClickListener((v) -> {
            mClickItem = clickItem;
            mOnItemClickListener.onItemClick(helper.getLayoutPosition(), item);
            notifyDataSetChanged();
        });

    }


    public interface OnItemClickListener {
        /**
         * 点击
         * @param posititon
         * @param mTime
         */
        void onItemClick(int posititon, String mTime);
    }

}
