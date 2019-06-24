package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.HomeNewOrderBean;

import net.eanfang.client.R;

/**
 * @author liangkailun
 * Date ：2019-06-18
 * Describe :首页最新订单adapter
 */
public class FragmentHomeNewOrderAdapter extends BaseQuickAdapter<HomeNewOrderBean, FragmentHomeNewOrderAdapter.FragmentHomeNewOrderViewHolder> {
    @Override
    public int getItemCount() {
        return 6;
    }

    public FragmentHomeNewOrderAdapter() {
        super(R.layout.item_home_new_order);
    }

    @Override
    protected void convert(FragmentHomeNewOrderViewHolder helper, HomeNewOrderBean item) {
        helper.itemHomeNewOrderUser.setText(item.getName());
        helper.itemHomeNewOrderArea.setText(item.getPlaceName());
        helper.itemHomeNewOrderDescribe.setText(item.getContent());
        helper.itemHomeNewOrderType.setText(item.getSysName());
    }

    class FragmentHomeNewOrderViewHolder extends BaseViewHolder {
        private TextView itemHomeNewOrderUser;
        private TextView itemHomeNewOrderArea;
        private TextView itemHomeNewOrderType;
        private TextView itemHomeNewOrderDescribe;

        public FragmentHomeNewOrderViewHolder(View view) {
            super(view);
            itemHomeNewOrderUser = view.findViewById(R.id.item_home_new_order_user);
            itemHomeNewOrderArea = view.findViewById(R.id.item_home_new_order_area);
            itemHomeNewOrderType = view.findViewById(R.id.item_home_new_order_type);
            itemHomeNewOrderDescribe = view.findViewById(R.id.item_home_new_order_describe);
        }
    }
}
