package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.NumberUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.base.kit.V;
import com.yaf.base.entity.WorkerOrderOerationEntity;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

import cn.hutool.core.date.DateUtil;


/**
 * @author wen on 2017/5/12.
 */

public class RepairedManageOrderAdapter extends BaseQuickAdapter<RepairOrderEntity, BaseViewHolder> {

    private static final String OEG_NAME = "个人";
    private static final int DEFAULT_URL_LENGTH = 10;

    public RepairedManageOrderAdapter() {
//    public RepairedManageOrderAdapter(List<RepairOrderEntity> data) {
        super(R.layout.item_workspace_order_list);
    }


    @Override
    protected void convert(BaseViewHolder helper, RepairOrderEntity item) {
        if (item == null) {
            return;
        }
        //创建订单操作类( 0:待支付，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
        int status = item.getStatus();
        boolean custom = item.getAssigneeUserId() != null && item.getAssigneeUserId().
                equals(WorkerApplication.get().getUserId());
        WorkerOrderOerationEntity orderOerationEntity = new WorkerOrderOerationEntity(status, custom);

        // 订单是否 已读 未读 1：新订单 0 已读
        helper.getView(R.id.tv_order_read).setVisibility(item.getNewOrder() == 1
                ? View.VISIBLE : View.GONE);
        String str = "";

        if (item.getOwnerOrg() != null && item.getOwnerOrg().getBelongCompany() != null && item.getOwnerUser() != null && item.getOwnerUser().getAccountEntity() != null) {
            // 公司名称
            helper.setText(R.id.tv_company_name, item.getOwnerOrg().getBelongCompany().getOrgName());
            // 客户名称
            helper.setText(R.id.tv_real_name, item.getOwnerUser().getAccountEntity().getRealName());
        } else {
            helper.setText(R.id.tv_company_name, "个人客户");
            if (item.getOwnerUser() == null||item.getOwnerUser().getAccountEntity() == null) {
                helper.setText(R.id.tv_real_name, "");
            } else {
                helper.setText(R.id.tv_real_name, item.getOwnerUser().getAccountEntity().getRealName());
            }
        }

        if (item.getAssigneeUser() != null && item.getAssigneeUser().getAccountEntity() != null) {
            helper.setText(R.id.tv_person_name, "技师：" + item.getAssigneeUser().getAccountEntity().getRealName());
        } else {
            helper.setText(R.id.tv_person_name, "技师：" + "");
        }
        if (item.getOrderNum() != null) {
            helper.setText(R.id.tv_order_id, "订单编号：" + item.getOrderNum() + str);
        } else {
            helper.setText(R.id.tv_order_id, "订单编号：");
        }
        helper.setText(R.id.tv_create_time, "下单时间：" + DateUtil.date(item.getCreateTime()).toString());
        if (item.getArriveTimeLimit() != null) {
            helper.setText(R.id.tv_arriveTime, "到达时限：" + GetConstDataUtils.getArriveList().get(item.getArriveTimeLimit()));
        } else {
            helper.setText(R.id.tv_arriveTime, "到达时限： 0 ");
        }
        helper.setText(R.id.tv_state, GetConstDataUtils.getRepairStatus().get(item.getStatus()));

        //订单金额
        if (item.getOwnerOrg() != null && item.getOwnerOrg().getBelongCompany() != null && OEG_NAME.equals(item.getOwnerOrg().getBelongCompany().getOrgName())) {

            helper.setGone(R.id.tv_count_money, true);
            helper.setGone(R.id.tv_total, true);

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
            helper.setGone(R.id.tv_count_money, false);
            helper.setGone(R.id.tv_total, false);
        }

        helper.setText(R.id.tv_do_first, orderOerationEntity.getOperationLeft());
        helper.setText(R.id.tv_do_second, orderOerationEntity.getOperationRight());
        helper.setGone(R.id.tv_do_first, orderOerationEntity.isShowOperationLeft());
        //原代码判断status为5的时候判断item.getClientEvaluateId()<=0展示否则不展示第二按钮
        helper.setGone(R.id.tv_do_second, orderOerationEntity.isShowOperationRight() || (custom && status == 5 &&
                (item.getClientEvaluateId() == null || item.getClientEvaluateId() <= 0)));
        helper.setGone(R.id.iv_finish, orderOerationEntity.isFinished());
        helper.setGone(R.id.tv_state, !orderOerationEntity.isFinished());

        //将业务类型的图片显示到列表
        String imgUrl = V.v(() -> item.getFailureEntity().getPictures().split(",")[0]);
        if (!StringUtils.isEmpty(imgUrl) && imgUrl.length() > DEFAULT_URL_LENGTH) {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + imgUrl),helper.getView(R.id.iv_upload));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER),helper.getView(R.id.iv_upload));
        }
        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);
    }
}
