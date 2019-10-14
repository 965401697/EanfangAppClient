package net.eanfang.client.ui.adapter.monitor;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.monitor.MonitorGroupListBean;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/9/9
 * @description 设备修改上级分组管理
 */
public class MonitorDeviceUpdateGroupAdapter extends BaseQuickAdapter<MonitorGroupListBean, BaseViewHolder> {


    public OnFirstItemClickListener onFirstItemClickListener;
    private MonitorGroupListBean mRealTimeGroupEntity;

    public MonitorDeviceUpdateGroupAdapter(OnFirstItemClickListener mOnFirstItemClickListener) {
        super(R.layout.layout_monitor_group_select_top_item);
        this.onFirstItemClickListener = mOnFirstItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, MonitorGroupListBean item) {
        MonitorGroupListBean realTimeGroupEntity = item;
        TextView mName = helper.getView(R.id.tv_groupName);
        RelativeLayout rlItem = helper.getView(R.id.rl_item);
        View mWhite = helper.getView(R.id.view_white);
        CheckBox cbFirst = helper.getView(R.id.cb_first);
        /**
         * 是一级
         * */
        if (item.isHaveSubGroup()) {
            cbFirst.setVisibility(View.GONE);
        } else {
            cbFirst.setVisibility(View.VISIBLE);
        }
        if (!item.isFirstHaveDevice() && !item.isHaveSubGroup()) {
            mWhite.setVisibility(View.VISIBLE);
        } else {
            mWhite.setVisibility(View.GONE);
        }
        if (realTimeGroupEntity.equals(mRealTimeGroupEntity)) {
            cbFirst.setChecked(true);
        } else {
            cbFirst.setChecked(false);
        }
        mName.setText(item.getGroupName());
        rlItem.setOnClickListener((v) -> {
            if (!item.isHaveSubGroup()) {
                mRealTimeGroupEntity = realTimeGroupEntity;
                onFirstItemClickListener.onItemClick(helper.getLayoutPosition(), item.getGroupName(), item.getGroupId());
                notifyDataSetChanged();
            }
        });
    }

    public interface OnFirstItemClickListener {
        /**
         * @param posititon
         * @param mGroupName
         * @param mGroupId
         */
        void onItemClick(int posititon, String mGroupName, Long mGroupId);
    }
}
