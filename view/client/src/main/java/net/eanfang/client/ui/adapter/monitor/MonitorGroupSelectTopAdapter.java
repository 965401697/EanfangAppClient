package net.eanfang.client.ui.adapter.monitor;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.monitor.RealTimeGroupEntity;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/9/9
 * @description 实时监控 上级分组管理
 */
public class MonitorGroupSelectTopAdapter extends BaseQuickAdapter<RealTimeGroupEntity, BaseViewHolder> {


    public OnFirstItemClickListener onFirstItemClickListener;
    private RealTimeGroupEntity mRealTimeGroupEntity;

    public MonitorGroupSelectTopAdapter(OnFirstItemClickListener mOnFirstItemClickListener) {
        super(R.layout.layout_monitor_group_select_top_item);
        this.onFirstItemClickListener = mOnFirstItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, RealTimeGroupEntity item) {
        RealTimeGroupEntity realTimeGroupEntity = item;
        TextView mName = helper.getView(R.id.tv_groupName);
        RelativeLayout rlItem = helper.getView(R.id.rl_item);
        View mWhite = helper.getView(R.id.view_white);
        CheckBox cbFirst = helper.getView(R.id.cb_first);
        /**
         * 是一级
         * */
        if (item.isHaveSubGroup()) {
            cbFirst.setVisibility(View.VISIBLE);
            mWhite.setVisibility(View.GONE);
        } else {
            cbFirst.setVisibility(View.GONE);
            mWhite.setVisibility(View.VISIBLE);
        }
        if (realTimeGroupEntity.equals(mRealTimeGroupEntity)) {
            cbFirst.setChecked(true);
        } else {
            cbFirst.setChecked(false);
        }
        mName.setText(item.getGroupName());
        rlItem.setOnClickListener((v) -> {
            if (item.isHaveSubGroup()) {
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
