package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostManageListBean;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :岗位管理详情
 */
public class LeavePostManageListAdapter extends BaseQuickAdapter<LeavePostManageListBean.ListBean, LeavePostManageListAdapter.LeavePostManageViewHolder> {

    public LeavePostManageListAdapter() {
        super(R.layout.item_leave_post_manage_list);
    }

    @Override
    protected void convert(LeavePostManageViewHolder helper, LeavePostManageListBean.ListBean item) {
        if (item == null) {
            return;
        }
        helper.imgItemLeavePostManageAreaname.setText(item.getPlaceName());
        helper.imgItemLeavePostManageCount.setText(String.valueOf(item.getTotal()));
        if (item.getStationList() == null){
            return;
        }
        int totalPost = Math.min(item.getStationList().size(), helper.items.length);
        for (int i = 0; i < totalPost; i++) {
            helper.items[i].setText(item.getStationList().get(i).getStationName());
            helper.items[i].setVisibility(View.VISIBLE);
        }
    }

    class LeavePostManageViewHolder extends BaseViewHolder {
        private TextView imgItemLeavePostManageAreaname;
        private TextView imgItemLeavePostManageCount;
        private TextView imgItemLeavePostManageItem1;
        private TextView imgItemLeavePostManageItem2;
        private TextView imgItemLeavePostManageItem3;
        private TextView imgItemLeavePostManageItem4;
        private TextView[] items = new TextView[4];

        public LeavePostManageViewHolder(View view) {
            super(view);
            imgItemLeavePostManageAreaname = view.findViewById(R.id.img_item_leave_post_manage_areaName);
            imgItemLeavePostManageCount = view.findViewById(R.id.img_item_leave_post_manage_count);
            imgItemLeavePostManageItem1 = view.findViewById(R.id.img_item_leave_post_manage_item1);
            imgItemLeavePostManageItem2 = view.findViewById(R.id.img_item_leave_post_manage_item2);
            imgItemLeavePostManageItem3 = view.findViewById(R.id.img_item_leave_post_manage_item3);
            imgItemLeavePostManageItem4 = view.findViewById(R.id.img_item_leave_post_manage_item4);
            items[0] = imgItemLeavePostManageItem1;
            items[1] = imgItemLeavePostManageItem2;
            items[2] = imgItemLeavePostManageItem3;
            items[3] = imgItemLeavePostManageItem4;

        }
    }
}
