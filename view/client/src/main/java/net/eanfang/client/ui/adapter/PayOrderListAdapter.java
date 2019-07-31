package net.eanfang.client.ui.adapter;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.PayOrderListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;


/**
 * 工作台--报价与支付列表的adapter
 * Created by Administrator on 2017/3/15.
 */

public class PayOrderListAdapter extends BaseQuickAdapter<PayOrderListBean.ListBean, BaseViewHolder> {
    public PayOrderListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayOrderListBean.ListBean item) {
        // 订单是否 已读 未读 1：新订单 0 已读
        if (item.getNewOrder() == 1) {
            helper.getView(R.id.tv_order_read).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_order_read).setVisibility(View.GONE);
        }
        if (item.getAssigneeCompanyOrg() != null) {
            helper.setText(R.id.tv_company_name, item.getOwnerCompanyOrg().getOrgName() + "(" + item.getOfferer().getAccountEntity().getRealName() + ")");
        } else {
            helper.setText(R.id.tv_company_name, item.getOfferer().getAccountEntity().getRealName());

        }
        helper.setText(R.id.tv_order_id, "单号:" + item.getOrderNum())
                .setText(R.id.tv_appointment_time, "下单:" + item.getCreateTime())
                .setText(R.id.tv_trouble_count, "项目:" + item.getProjectName())
                .setText(R.id.tv_count_money, "¥" + item.getTotalCost() / 100)
                .setText(R.id.tv_client_company_name_wr, "用户:" + item.getReportUser().getAccountEntity().getRealName())

                .setText(R.id.tv_worker_name, "技师:" + item.getOfferer().getAccountEntity().getRealName());
        helper.setText(R.id.tv_state, GetConstDataUtils.getQuoteStatus().get(item.getStatus()));
        if (item.getFailureEntity() != null) {
            String[] urls = item.getFailureEntity().getPictures().split(",");
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + urls[0]),helper.getView(R.id.iv_upload));
        }
        if (ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getVerifyStatus() == 2) {
            helper.setText(R.id.tv_do_first, "联系技师");
            if (item.getStatus() == 0) {
                helper.setText(R.id.tv_do_second, "同意报价");
            } else {
                helper.setGone(R.id.tv_do_second, false);
            }

        } else {
            helper.setText(R.id.tv_do_first, "联系技师");
            if (item.getStatus() == 0) {
                helper.setText(R.id.tv_do_second, "立即支付");
            } else {
                helper.setGone(R.id.tv_do_second, false);
            }

        }

        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);
    }
}
