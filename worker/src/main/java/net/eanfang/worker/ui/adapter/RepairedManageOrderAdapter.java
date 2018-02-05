package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.worker.R;

import java.util.List;


/**
 * Created by wen on 2017/5/12.
 */

public class RepairedManageOrderAdapter extends BaseQuickAdapter<RepairOrderEntity, BaseViewHolder> {


    private final boolean[] isShowFirstBtnWorker = {
            false, false, true, false, false, true
    };
    private String[] doSomethingWorker = {
            "联系客户", "马上回电", "上门签到"
            , "完工", "查看故障处理", "评价客户"
    };
    private String[] doSomething;
    private boolean[] isShowFirstBtn;

    public RepairedManageOrderAdapter(List<RepairOrderEntity> data) {
        super(R.layout.item_workspace_order_list, data);
        doSomething = doSomethingWorker;
        isShowFirstBtn = isShowFirstBtnWorker;

    }

    @Override
    protected void convert(BaseViewHolder helper, RepairOrderEntity item) {
        String str = "";
//        if (!StringUtils.isEmpty(item.getOriginordernum())) {
//            str = "（挂）";
//        }

        helper.setText(R.id.tv_company_name, item.getOwnerOrg().getBelongCompany().getOrgName()
                + "  (" + item.getOwnerUser().getAccountEntity().getRealName() + ")");
        helper.setText(R.id.tv_person_name, "技师：" + item.getAssigneeUser().getAccountEntity().getRealName());

        helper.setText(R.id.tv_order_id, "单号：" + item.getOrderNum() + str);
        helper.setText(R.id.tv_create_time, "下单：" + GetDateUtils.dateToDateString(item.getCreateTime()));
//        helper.setText(R.id.tv_count_money, "" + item.getDoorfee());
        helper.setText(R.id.tv_state, GetConstDataUtils.getRepairStatus().get(item.getStatus()));
//        helper.setText(R.id.tv_bug_one, "类别：" + item.getBugonename());
        helper.setText(R.id.tv_do_second, doSomething[item.getStatus()]);
        helper.setVisible(R.id.tv_do_first, isShowFirstBtn[item.getStatus()]);

        if (item.getStatus() == 2) {
            helper.setText(R.id.tv_do_first, "改约");
        } else if (item.getStatus() == 5) {
            helper.setText(R.id.tv_do_first, "查看故障处理");
            if (item.getClientEvaluateId() == null || item.getClientEvaluateId().longValue() <= 0) {
                helper.setVisible(R.id.tv_do_second, true);
            } else {
                helper.setVisible(R.id.tv_do_second, false);
            }
        }


        //将业务类型的图片显示到列表
//        if (!StringUtils.isEmpty(item.getBugEntityList().get(helper.getPosition()).getPictures())) {
//            String[] urls = item.getBugEntityList().get(helper.getPosition()).getPictures().split(",");
//            //将业务类型的图片显示到列表
//            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
//        }

        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);

    }
}
