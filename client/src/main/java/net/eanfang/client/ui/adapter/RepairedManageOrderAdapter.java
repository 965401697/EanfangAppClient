package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.annimon.stream.Optional;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;


/**
 * Created by wen on 2017/5/12.
 */

public class RepairedManageOrderAdapter extends BaseQuickAdapter<RepairOrderEntity, BaseViewHolder> {


    private final boolean[] isShowFirstBtnClient = {
            false, false, false, false, true, true, false
    };
    private String[] doSomethingClient = {
            "立即付款", "联系技师", "联系技师",
            "联系技师", "确认完工", "评价技师", "联系技师"
    };
    private String[] doSomething;

    public RepairedManageOrderAdapter() {
        super(R.layout.item_workspace_order_list);
        doSomething = doSomethingClient;

    }

    @Override
    protected void convert(BaseViewHolder helper, RepairOrderEntity item) {
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
        helper.setText(R.id.tv_company_name, orgName);
        String userName = "";
        if (item.getOwnerUser() != null && item.getOwnerUser().getAccountEntity() != null) {
            userName = Optional.ofNullable(item.getOwnerUser().getAccountEntity().getRealName()).orElseGet(() -> "");
        }
        helper.setText(R.id.tv_person_name, "负责：" + userName);

        helper.setText(R.id.tv_order_id, "单号：" + item.getOrderNum() + str);
        helper.setText(R.id.tv_create_time, "下单时间：" + GetDateUtils.dateToDateTimeString(item.getCreateTime()));
//        helper.setText(R.id.tv_count_money, "" + item.getDoorfee());
        helper.setText(R.id.tv_state, GetConstDataUtils.getRepairStatus().get(item.getStatus()));

        helper.setVisible(R.id.tv_do_first, isShowFirstBtnClient[item.getStatus()]);
        helper.setText(R.id.tv_do_second, doSomething[item.getStatus()]);

        // 待验收
        if (item.getStatus() == 4) {
            helper.setVisible(R.id.tv_finish, true);

            helper.setVisible(R.id.tv_do_first, false);
        } else if (item.getStatus() == 5) {
            helper.setText(R.id.tv_do_first, "完工报告");
            if (item.getWorkerEvaluateId() == null || item.getWorkerEvaluateId().longValue() <= 0) {
                helper.setVisible(R.id.tv_do_second, true);
            } else {
                helper.setVisible(R.id.tv_do_second, false);
            }
        }
//        if (!StringUtils.isEmpty(item.getBugEntityList().get(helper.getPosition()).getPictures())){
//            String[] urls = item.getBugEntityList().get(helper.getPosition()).getPictures().split(",");
//            //将业务类型的图片显示到列表
//            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(Uri.parse(urls[0]));
//        }

        //将业务类型的图片显示到列表
        String imgUrl = V.v(() -> item.getFailureEntity().getPictures().split(",")[0]);
        if (!StringUtils.isEmpty(imgUrl) && imgUrl.length() > 10) {
            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + imgUrl));
        }
        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);
        helper.addOnClickListener(R.id.tv_finish);

    }
}
