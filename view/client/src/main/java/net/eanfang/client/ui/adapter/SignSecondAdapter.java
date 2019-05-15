package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.biz.model.SignListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;


/**
 * @author guanluocang
 * @data 2018/10/26
 * @description 足迹二级列表
 */

public class SignSecondAdapter extends BaseQuickAdapter<SignListBean.ListBean, BaseViewHolder> {
    public SignSecondAdapter() {
        super(R.layout.layout_item_sign_second);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignListBean.ListBean item) {
        SimpleDraweeView iv_header = helper.getView(R.id.iv_header);

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
