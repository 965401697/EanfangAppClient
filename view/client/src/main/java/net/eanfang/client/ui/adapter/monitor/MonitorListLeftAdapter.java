package net.eanfang.client.ui.adapter.monitor;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.bean.monitor.MonitorGroupListBean;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/9/9
 * @description 实时监控 列表 左边的
 */
public class MonitorListLeftAdapter extends BaseQuickAdapter<MonitorGroupListBean, BaseViewHolder> {


    public OnFirstItemClickListener onFirstItemClickListener;
    private MonitorGroupListBean monitorGroupListBean;
    private boolean isClick = true;

    public MonitorListLeftAdapter(OnFirstItemClickListener mOnFirstItemClickListener) {
        super(R.layout.layout_monitor_left_one);
        this.onFirstItemClickListener = mOnFirstItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, MonitorGroupListBean item) {
        MonitorGroupListBean monitorGroupList = item;

        TextView mName = helper.getView(R.id.tv_monitorLevOne);
        ImageView imageView = helper.getView(R.id.iv_icon);
        LinearLayout ll_second = helper.getView(R.id.ll_second);
        View mWhite = helper.getView(R.id.tv_white);
        mName.setText(item.getGroupName());
        if (!item.isHaveSubGroup()) {
            mWhite.setVisibility(View.VISIBLE);
        } else {
            mWhite.setVisibility(View.GONE);
        }
        if (monitorGroupListBean != null && monitorGroupList.equals(monitorGroupListBean)) {
            mName.setTextColor(ContextCompat.getColor(BaseApplication.get().getApplicationContext(), R.color.colorPrimary));
            imageView.setImageResource(R.mipmap.ic_monitor_item_orange);
            ll_second.setBackgroundColor(ContextCompat.getColor(BaseApplication.get().getApplicationContext(), R.color.color_white));
        } else {
            mName.setTextColor(ContextCompat.getColor(BaseApplication.get().getApplicationContext(), R.color.color_client_neworder));
            imageView.setImageResource(R.mipmap.ic_monitor_item_black);
            ll_second.setBackgroundColor(ContextCompat.getColor(BaseApplication.get().getApplicationContext(), R.color.rc_input_bg));
        }
        if (item.isChecked() && isClick) {
            mName.setTextColor(ContextCompat.getColor(BaseApplication.get().getApplicationContext(), R.color.colorPrimary));
            imageView.setImageResource(R.mipmap.ic_monitor_item_orange);
            ll_second.setBackgroundColor(ContextCompat.getColor(BaseApplication.get().getApplicationContext(), R.color.color_white));
        }


        ll_second.setOnClickListener((v) -> {
            if (!item.isHaveSubGroup() || item.isFirstHaveDevice()) {
                isClick = false;
                monitorGroupListBean = monitorGroupList;
                onFirstItemClickListener.onItemClick(helper.getLayoutPosition(), item.getGroupName());
                notifyDataSetChanged();
            }
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
