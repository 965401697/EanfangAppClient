package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.annimon.stream.Optional;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;

import java.util.List;


/**
 * Created by wen on 2017/5/12.
 */

public class RepairedManageOrderAdapter extends BaseQuickAdapter<RepairOrderEntity, BaseViewHolder> {

    private Config config = Config.get(mContext);
    private GetConstDataUtils constDataUtils = GetConstDataUtils.get(config);

    private final boolean[] isShowFirstBtnClient = {
            false, false, false, false, true, true, false
    };
    private String[] doSomethingClient = {
            "立即支付", "联系技师", "联系技师",
            "联系技师", "确认完工", "评价技师", "联系技师"
    };
    private String[] doSomething;

    public RepairedManageOrderAdapter(List<RepairOrderEntity> data) {
        super(R.layout.item_workspace_order_list, data);
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
            //头像
            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + Optional.ofNullable(item.getAssigneeUser().getAccountEntity().getAvatar()).orElseGet(() -> "")));
        }
        helper.setText(R.id.tv_company_name, orgName);
        String userName = "";
        if (item.getOwnerUser() != null && item.getOwnerUser().getAccountEntity() != null) {
            userName = Optional.ofNullable(item.getOwnerUser().getAccountEntity().getRealName()).orElseGet(() -> "");
        }
        helper.setText(R.id.tv_person_name, "负责：" + userName);

        helper.setText(R.id.tv_order_id, "单号：" + item.getOrderNum() + str);
        helper.setText(R.id.tv_create_time, "下单：" + GetDateUtils.dateToDateString(item.getCreateTime()));
//        helper.setText(R.id.tv_count_money, "" + item.getDoorfee());
        helper.setText(R.id.tv_state, constDataUtils.getRepairStatus().get(item.getStatus()));
//        helper.setText(R.id.tv_bug_one, "类别：" + Config.getConfig().
//                getBusinessName(item.getBugEntityList().get(helper.getPosition()).getBusinessThreeCode()));
        helper.setText(R.id.tv_do_second, doSomething[item.getStatus()]);
        helper.setVisible(R.id.tv_do_first, isShowFirstBtnClient[item.getStatus()]);

        if (item.getStatus() == 2) {
            helper.setText(R.id.tv_do_first, "改约");
        } else if (item.getStatus() == 5) {
            helper.setText(R.id.tv_do_first, "查看故障处理");
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
        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);

    }
}
