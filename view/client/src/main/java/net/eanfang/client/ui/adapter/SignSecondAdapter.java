package net.eanfang.client.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.SignListBean;
import com.eanfang.config.Config;

import net.eanfang.client.R;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;


/**
 * @author guanluocang
 * @data 2018/10/26
 * @description 足迹二级列表
 */

public class SignSecondAdapter extends BaseQuickAdapter<SignListBean.ListBean, BaseViewHolder> {
    @Getter
    private Map<String, String> mTimeMap = new HashMap<>();

    public SignSecondAdapter() {
        super(R.layout.layout_item_sign_second);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignListBean.ListBean item) {
        if (mTimeMap.containsValue(item.getSignDay())) {
            if (mTimeMap.containsKey(item.getId())) {
                helper.setVisible(R.id.ll_singDay, true);
                String[] times = item.getSignDay().split("-");
                helper.setText(R.id.tv_month, times[1] + "月");
                helper.setText(R.id.tv_year, times[0] + "年");
            } else {
                helper.setVisible(R.id.ll_singDay, false);
            }
        } else {
            mTimeMap.put(item.getId(), item.getSignDay());
            helper.setVisible(R.id.ll_singDay, true);
            String[] times = item.getSignDay().split("-");
            helper.setText(R.id.tv_month, times[1] + "月");
            helper.setText(R.id.tv_year, times[0] + "年");
        }
        ImageView iv_header = helper.getView(R.id.iv_header);

        // status 0 签到 1  签退
        if (item.getStatus() == 0) {
            iv_header.setImageResource(R.mipmap.ic_footprint_sign_in);
        } else {
            iv_header.setImageResource(R.mipmap.ic_footprint_sign_back);
        }
        helper.setText(R.id.tv_sign_time, item.getSignTime());
        helper.setText(R.id.tv_address, Config.get().getAddressByCode(item.getZoneCode()) + item.getDetailPlace());
        helper.setText(R.id.tv_visit_name, item.getVisitorName());
        helper.setText(R.id.tv_sign_name, item.getCreateUser().getAccountEntity().getRealName() + "   ");
    }
}
