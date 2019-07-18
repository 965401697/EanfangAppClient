package net.eanfang.client.ui.adapter;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.annimon.stream.Optional;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.NumberUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.base.kit.V;
import com.yaf.base.entity.ClientOrderOerationEntity;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;

import cn.hutool.core.date.DateUtil;


/**
 * @author wen on 2017/5/12.
 */

public class RepairedManageOrderAdapter extends BaseQuickAdapter<RepairOrderEntity, BaseViewHolder> {
    private static final String ORG_NAME = "个人";
    /**
     * 请求地址的最短长度
     */
    private static final int LESS_URL_LENGTH = 10;
    public RepairedManageOrderAdapter() {
        super(R.layout.item_workspace_order_list);

    }

    @Override
    protected void convert(BaseViewHolder helper, RepairOrderEntity item) {
        if (item == null) {
            return;
        }
        //创建订单操作类( 0:待支付，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
        int status = item.getStatus();
        boolean custom = item.getOwnerUserId() != null && item.getOwnerUserId().
                equals(ClientApplication.get().getUserId());
        ClientOrderOerationEntity orderOerationEntity = new ClientOrderOerationEntity(status, custom);

        // 订单是否 已读 未读 1：新订单 0 已读
        helper.getView(R.id.tv_order_read).setVisibility(
                item.getNewOrder() == 1 ? View.VISIBLE : View.GONE);
        String str = "";
//        if (!StringUtils.isEmpty(item.getOriginordernum())) {
//            str = "（挂）";
//        }

        //报修单列表 公司名称后面，增加联系人姓名
        String orgName = "";
        if (item.getAssigneeOrg() != null && item.getAssigneeOrg().getBelongCompany() != null) {
            orgName = Optional.ofNullable(item.getAssigneeOrg().getBelongCompany().getOrgName()).orElseGet(() -> "");

        }
        if (item.getAssigneeUser() != null && item.getAssigneeUser().getAccountEntity() != null) {
            orgName += " " + Optional.ofNullable(item.getAssigneeUser().getAccountEntity().getRealName()).orElseGet(() -> "");
        }

        if (item.getOwnerUser() != null && item.getOwnerUser().getAccountEntity() != null) {
            helper.setText(R.id.tv_person_name, "报修人:" + item.getOwnerUser().getAccountEntity().getRealName());
        }

        if (TextUtils.isEmpty(orgName)) {
            helper.setVisible(R.id.tv_company_name, false);
        } else {
            helper.setVisible(R.id.tv_company_name, true);
            helper.setText(R.id.tv_company_name, orgName);
        }

        helper.setText(R.id.tv_order_id, "单号：" + item.getOrderNum() + str);
        helper.setText(R.id.tv_create_time, "下单时间：" + DateUtil.date(item.getCreateTime()).toString());

        if (item.getOwnerOrg() != null && item.getOwnerOrg().getBelongCompany() != null && ORG_NAME.equals(item.getOwnerOrg().getBelongCompany().getOrgName())) {

            helper.setVisible(R.id.tv_count_money, true);
            helper.setVisible(R.id.tv_total, true);

            if (item.getPayLogEntity() != null) {
                if (item.getPayLogEntity().getPayPrice() != null) {
                    helper.setText(R.id.tv_count_money, "¥" + NumberUtil.getEndTwoNum(item.getPayLogEntity().getPayPrice() / 100.00));
                } else {
                    helper.setText(R.id.tv_count_money, "0.00");
                }
            } else {
                helper.setText(R.id.tv_count_money, "0.00");
            }
        } else {
            helper.setVisible(R.id.tv_count_money, false);
            helper.setVisible(R.id.tv_total, false);
        }


        helper.setText(R.id.tv_state, GetConstDataUtils.getRepairStatus().get(item.getStatus()));
        helper.setText(R.id.tv_do_first, orderOerationEntity.getOperationLeft());
        helper.setText(R.id.tv_do_second, orderOerationEntity.getOperationRight());
        helper.setVisible(R.id.tv_do_first, orderOerationEntity.isShowOperationLeft());
        //原代码判断status为5的时候判断item.getClientEvaluateId()<=0展示否则不展示第二按钮
        helper.setVisible(R.id.tv_do_second, orderOerationEntity.isShowOperationRight() ||
                (custom && status == 5 &&
                        (item.getWorkerEvaluateId() == null || item.getWorkerEvaluateId() <= 0)));
        helper.setVisible(R.id.iv_finish, orderOerationEntity.isFinished());
        helper.setVisible(R.id.tv_state, !orderOerationEntity.isFinished());
        // 待验收
        helper.setVisible(R.id.tv_finish, status == 4);

        //将业务类型的图片显示到列表
        String imgUrl = V.v(() -> item.getFailureEntity().getPictures().split(",")[0]);
        if (!StringUtils.isEmpty(imgUrl) && imgUrl.length() > LESS_URL_LENGTH) {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + imgUrl),helper.getView(R.id.iv_upload));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + ""),helper.getView(R.id.iv_upload));
        }
        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);
        helper.addOnClickListener(R.id.tv_finish);

    }
}
