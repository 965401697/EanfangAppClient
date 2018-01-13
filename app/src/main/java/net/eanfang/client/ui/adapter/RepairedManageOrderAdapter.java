package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.GetDateUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.repair.RepairOrderEntity;
import net.eanfang.client.util.GetConstDataUtils;
import net.eanfang.client.util.StringUtils;

import java.util.List;


/**
 * Created by wen on 2017/5/12.
 */

public class RepairedManageOrderAdapter extends BaseQuickAdapter<RepairOrderEntity, BaseViewHolder> {


    private String[] doSomethingClient = {
            "立即支付", "联系技师", "联系技师",
            "联系技师", "确认完工", "评价技师"
    };


    private final boolean[] isShowFirstBtnClient = {
            false, false, false, false, true, true
    };

    private String[] doSomething;
    private boolean[] isShowFirstBtn;

    public RepairedManageOrderAdapter(List<RepairOrderEntity> data) {
        super(R.layout.item_workspace_order_list, data);
        doSomething = doSomethingClient;
        isShowFirstBtn = isShowFirstBtnClient;

    }

    @Override
    protected void convert(BaseViewHolder helper, RepairOrderEntity item) {
        String str = "";
//        if (!StringUtils.isEmpty(item.getOriginordernum())) {
//            str = "（挂）";
//        }

        //报修单列表 公司名称后面，增加联系人姓名
        if (!StringUtils.isEmpty(item.getAssigneeOrg().getBelongCompany().getOrgName())) {
            helper.setText(R.id.tv_company_name, item.getAssigneeOrg().getBelongCompany().getOrgName()
                    + "  (" + item.getAssigneeUser().getAccountEntity().getRealName() + ")");
        }

        helper.setText(R.id.tv_person_name, "负责：" + item.getOwnerUser().getAccountEntity().getRealName());

        helper.setText(R.id.tv_order_id, "单号：" + item.getOrderNum() + str);
        helper.setText(R.id.tv_create_time, "下单：" + GetDateUtils.dateToDateString(item.getCreateTime()));
//        helper.setText(R.id.tv_count_money, "" + item.getDoorfee());
        helper.setText(R.id.tv_state, GetConstDataUtils.getRepairStatusByStatus(item.getStatus()));
//        helper.setText(R.id.tv_bug_one, "类别：" + Config.getConfig().
//                getBusinessName(item.getBugEntityList().get(helper.getPosition()).getBusinessThreeCode()));
        helper.setText(R.id.tv_do_second, doSomething[item.getStatus()]);
        helper.setVisible(R.id.tv_do_first, isShowFirstBtn[item.getStatus()]);

        if (item.getStatus() == 2) {
            helper.setText(R.id.tv_do_first, "改约");
        } else if (item.getStatus() == 5) {
            helper.setText(R.id.tv_do_first, "查看故障处理");
            if (item.getWorkerEvaluateId() == null || item.getWorkerEvaluateId().longValue() <= 0) {
                helper.setVisible(R.id.tv_do_second, true);
            }else {
                helper.setVisible(R.id.tv_do_second, false);
            }
        }
//        if (!StringUtils.isEmpty(item.getBugEntityList().get(helper.getPosition()).getPictures())){
//            String[] urls = item.getBugEntityList().get(helper.getPosition()).getPictures().split(",");
//            //将业务类型的图片显示到列表
//            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(Uri.parse(urls[0]));
//        }
        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);

    }
}
