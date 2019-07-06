package net.eanfang.client.ui.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :图像查岗详情
 */
public class LeavePostCheckDetailAdapter extends BaseQuickAdapter<LeavePostDeviceInfoBean.ChargeStaffListBean, BaseViewHolder> {

    public LeavePostCheckDetailAdapter() {
        super(null);
        setMultiTypeDelegate(new MultiTypeDelegate<LeavePostDeviceInfoBean.ChargeStaffListBean>() {
            @Override
            protected int getItemType(LeavePostDeviceInfoBean.ChargeStaffListBean listBean) {
                return listBean.getType();
            }
        });
        getMultiTypeDelegate().registerItemType(0, R.layout.item_leave_post_check_detail_post).registerItemType(1, R.layout.item_leave_post_person);

    }

    @Override
    protected void convert(BaseViewHolder helper, LeavePostDeviceInfoBean.ChargeStaffListBean item) {
        if (item == null) {
            return;
        }
        if (0 == helper.getItemViewType()) {
            helper.setText(R.id.tv_item_leave_post_check_detail_post, item.getTitle());
        } else {
            GlideUtil.intoImageView(mContext, BuildConfig.OSS_SERVER + item.getAccountEntity().getAvatar(), helper.getView(R.id.cirImg_item_leave_post_check_detail_header));
            helper.setText(R.id.tv_item_leave_post_check_detail_name, item.getName());
            if (item.getAccountEntity() != null) {
                helper.setText(R.id.tv_item_leave_post_check_detail_tel, item.getAccountEntity().getMobile());
            }
            helper.addOnClickListener(R.id.btn_item_leave_post_check_detail_call);
        }
    }

}
