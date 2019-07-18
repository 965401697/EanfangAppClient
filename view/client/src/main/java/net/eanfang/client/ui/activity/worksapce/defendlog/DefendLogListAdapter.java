package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaf.base.entity.ProtectionLogEntity;

import net.eanfang.client.R;

import cn.hutool.core.date.DateUtil;

/**
 * Created by O u r on 2018/5/22.
 */

public class DefendLogListAdapter extends BaseQuickAdapter<ProtectionLogEntity, BaseViewHolder> {
    private int mType;

    public DefendLogListAdapter(int type, int layoutResId) {
        super(layoutResId);
        this.mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProtectionLogEntity item) {
        if (item.getOwnerCompany() != null) {
            helper.setText(R.id.tv_company_name, item.getOwnerCompany().getOrgName() + "(" + item.getOwnerUser().getAccountEntity().getRealName() + ")");
        } else {
            helper.setText(R.id.tv_company_name, "");

        }
        // 订单是否 已读 未读 1：新订单 0 已读
        if (item.getNewOrder() == 1) {
            helper.getView(R.id.tv_state).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_state).setVisibility(View.GONE);
        }
//        if (item.getStatus() == 0) {
//            helper.setText(R.id.tv_state, "未读");
//        } else {
//            helper.setText(R.id.tv_state, "已读");
//        }
        helper.setText(R.id.tv_order_id, item.getOrderNumber());
        helper.setText(R.id.tv_create_time, DateUtil.date(item.getCreateTime()).toString());
        helper.setText(R.id.tv_person_name, item.getAssigneeUser().getAccountEntity().getRealName());
        helper.setText(R.id.tv_preson_phone, item.getAssigneeUser().getAccountEntity().getMobile());

//        helper.addOnClickListener(R.id.tv_do_first);
//        if (mType == 1) {//汇报人
//            helper.setText(R.id.tv_do_first, "联系汇报人");
//        } else {
//            helper.setText(R.id.tv_do_first, "联系创建人");
//        }
        helper.addOnClickListener(R.id.tv_detail);
    }
}
