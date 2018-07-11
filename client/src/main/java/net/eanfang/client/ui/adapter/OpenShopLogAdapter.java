package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.OpenShopLogBean;
import com.eanfang.util.GetDateUtils;
import com.yaf.base.entity.OpenShopLogEntity;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/5/22.
 */

public class OpenShopLogAdapter extends BaseQuickAdapter<OpenShopLogEntity, BaseViewHolder> {
    private int mType;

    public OpenShopLogAdapter(int type, int layoutResId) {
        super(layoutResId);
        this.mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, OpenShopLogEntity item) {
        if (item.getOwnerCompany() != null) {
            helper.setText(R.id.tv_company_name, item.getOwnerCompany().getOrgName() + "(" + item.getOwnerUser().getAccountEntity().getRealName() + ")");
        } else {
            helper.setText(R.id.tv_company_name, "");

        }
        if (item.getStatus() == 0) {
            helper.setText(R.id.tv_state, "未读");
        } else {
            helper.setText(R.id.tv_state, "已读");
        }
        helper.setText(R.id.tv_order_id, item.getOrderNumber());
        helper.setText(R.id.tv_create_time, GetDateUtils.dateToFormatString(item.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        helper.setText(R.id.tv_person_name, item.getAssigneeUser().getAccountEntity().getRealName());
        helper.setText(R.id.tv_preson_phone, item.getAssigneeUser().getAccountEntity().getMobile());

        helper.addOnClickListener(R.id.tv_do_first);
        if (mType == 1) {//汇报人
            helper.setText(R.id.tv_do_first, "联系汇报人");
        } else {
            helper.setText(R.id.tv_do_first, "联系创建人");
        }
        helper.addOnClickListener(R.id.tv_detail);
    }
}
