package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostManageListBean;

import java.util.ArrayList;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :岗位管理详情
 */
public class LeavePostManageListAdapter extends BaseQuickAdapter<LeavePostManageListBean.ListBean, LeavePostManageListAdapter.LeavePostHomeViewHolder> {

    public LeavePostManageListAdapter(int rec) {
        super(rec);
    }

    @Override
    protected void convert(LeavePostHomeViewHolder helper, LeavePostManageListBean.ListBean item) {
        if (item == null) {
            return;
        }
        helper.imgItemLeavePostManageAreaname.setText(item.getPlaceName());
        int totalPost = Math.min(item.getTotal(), helper.items.size());
        helper.imgItemLeavePostManageCount.setText(String.valueOf(totalPost));

        for (int i = 0; i < totalPost; i++) {
            helper.items.get(i).setText(item.getStationList().get(i).getStationName());
            helper.items.get(i).setVisibility(View.VISIBLE);
        }
    }

    class LeavePostHomeViewHolder extends BaseViewHolder {
        private TextView imgItemLeavePostManageAreaname;
        private TextView imgItemLeavePostManageCount;
        private TextView imgItemLeavePostManageItem1;
        private TextView imgItemLeavePostManageItem2;
        private TextView imgItemLeavePostManageItem3;
        private TextView imgItemLeavePostManageItem4;
        private ArrayList<TextView> items;

        public LeavePostHomeViewHolder(View view) {
            super(view);
            items = new ArrayList<>();
            imgItemLeavePostManageAreaname = view.findViewById(R.id.img_item_leave_post_manage_areaName);
            imgItemLeavePostManageCount = view.findViewById(R.id.img_item_leave_post_manage_count);
            imgItemLeavePostManageItem1 = view.findViewById(R.id.img_item_leave_post_manage_item1);
            imgItemLeavePostManageItem2 = view.findViewById(R.id.img_item_leave_post_manage_item2);
            imgItemLeavePostManageItem3 = view.findViewById(R.id.img_item_leave_post_manage_item3);
            imgItemLeavePostManageItem4 = view.findViewById(R.id.img_item_leave_post_manage_item4);
            items.add(imgItemLeavePostManageItem1);
            items.add(imgItemLeavePostManageItem2);
            items.add(imgItemLeavePostManageItem3);
            items.add(imgItemLeavePostManageItem4);

        }
    }
}
