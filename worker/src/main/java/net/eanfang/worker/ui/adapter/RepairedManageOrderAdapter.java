package net.eanfang.worker.ui.adapter;

import android.net.Uri;

import com.annimon.stream.Optional;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.worker.R;

import java.util.List;


/**
 * Created by wen on 2017/5/12.
 */

public class RepairedManageOrderAdapter extends BaseQuickAdapter<RepairOrderEntity, BaseViewHolder> {


    private final boolean[] isShowFirstBtnWorker = {
            false, false, true, true, false, true, false
    };
    private String[] doSomethingWorker = {
            "联系客户", "马上回电", "签到"
            , "完工", "查看故障处理", "评价客户", "联系技师"
    };
    private String[] doSomething;
    private boolean[] isShowFirstBtn;

    public RepairedManageOrderAdapter() {
//    public RepairedManageOrderAdapter(List<RepairOrderEntity> data) {
        super(R.layout.item_workspace_order_list);
        doSomething = doSomethingWorker;
        isShowFirstBtn = isShowFirstBtnWorker;

    }

    @Override
    protected void convert(BaseViewHolder helper, RepairOrderEntity item) {
        String str = "";

        if (item.getOwnerOrg() != null && item.getOwnerOrg().getBelongCompany() != null && item.getOwnerUser() != null && item.getOwnerUser().getAccountEntity() != null) {
            // 公司名称
            helper.setText(R.id.tv_company_name, item.getOwnerOrg().getBelongCompany().getOrgName());
            // 客户名称
            helper.setText(R.id.tv_real_name, item.getOwnerUser().getAccountEntity().getRealName());
        } else if (item.getOwnerOrg() == null) {
            helper.setText(R.id.tv_company_name, item.getOwnerUser().getAccountEntity().getRealName());
        }

        if (item.getAssigneeUser() != null && item.getAssigneeUser().getAccountEntity() != null) {
            helper.setText(R.id.tv_person_name, "技师：" + item.getAssigneeUser().getAccountEntity().getRealName());
        }
        if (item.getOrderNum() != null) {
            helper.setText(R.id.tv_order_id, "订单编号：" + item.getOrderNum() + str);
        }
        helper.setText(R.id.tv_create_time, "下单时间：" + GetDateUtils.dateToDateString(item.getCreateTime()));
        helper.setText(R.id.tv_arriveTime, "到达时限：" + GetConstDataUtils.getArriveList().get(item.getArriveTimeLimit()));
        helper.setText(R.id.tv_state, GetConstDataUtils.getRepairStatus().get(item.getStatus()));
        //( 0:待支付，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
        helper.setText(R.id.tv_do_second, doSomething[item.getStatus()]);
        helper.setVisible(R.id.tv_do_first, isShowFirstBtn[item.getStatus()]);

        //订单金额
        if (item.getPayLogEntity() != null) {
            if (item.getPayLogEntity().getPayPrice() != null) {
//                helper.setText(R.id.tv_count_money, item.getPayLogEntity().getPayPrice());
            }
        }
        //( 0:待支付，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
        if (item.getStatus() == 2) {
            helper.setText(R.id.tv_do_first, "改约");
        } else if (item.getStatus() == 3) {
            helper.setText(R.id.tv_do_first, "联系客户");
        } else if (item.getStatus() == 4) {
            helper.setText(R.id.tv_do_first, "联系客户");
        } else if (item.getStatus() == 5) {
            helper.setText(R.id.tv_do_first, "查看故障处理");
            if (item.getClientEvaluateId() == null || item.getClientEvaluateId().longValue() <= 0) {
                helper.setVisible(R.id.tv_do_second, true);
            } else {
                helper.setVisible(R.id.tv_do_second, false);
            }
        }


        //将业务类型的图片显示到列表
        String imgUrl = V.v(() -> item.getFailureEntity().getPictures().split(",")[0]);
        if (!StringUtils.isEmpty(imgUrl) && imgUrl.length() > 10) {
            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + imgUrl));
        }

        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);
    }
}
