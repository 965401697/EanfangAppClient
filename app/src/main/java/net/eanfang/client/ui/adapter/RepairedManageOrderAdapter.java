package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.config.Config;
import net.eanfang.client.ui.model.RepairedOrderBean;
import net.eanfang.client.util.GetConstDataUtils;
import net.eanfang.client.util.StringUtils;

import java.util.List;


/**
 * Created by wen on 2017/5/12.
 */

public class RepairedManageOrderAdapter extends BaseQuickAdapter<RepairedOrderBean.AllBean, BaseViewHolder> {

    private List<String> mStatus = Config.getConfig().getRepairStatusWorker();

    private String[] doSomethingClient = {
            "立即支付", "联系技师", "联系技师",
            "联系技师", "确认完工", "评价技师", "查看故障处理"
    };


    private final boolean[] isShowFirstBtnClient = {
            false, false, false, false, true, true, false
    };

    private String[] doSomething;
    private boolean[] isShowFirstBtn;

    public RepairedManageOrderAdapter(List<RepairedOrderBean.AllBean> data) {
        super(R.layout.item_workspace_order_list, data);
        doSomething = doSomethingClient;
        isShowFirstBtn = isShowFirstBtnClient;

    }

    @Override
    protected void convert(BaseViewHolder helper, RepairedOrderBean.AllBean item) {
        String str = "";
        if (!StringUtils.isEmpty(item.getOriginordernum()))
            str = "（挂）";

        if (BuildConfig.APP_TYPE == 0) {
            //报修单列表 公司名称后面，增加联系人姓名
            helper.setText(R.id.tv_company_name, item.getWorkerCompanyName() + "  (" + item.getWorkerName() + ")");
            helper.setText(R.id.tv_person_name, "负责：" + item.getClientName());

        } else {
            helper.setText(R.id.tv_company_name, item.getClientCompanyName() + "  (" + item.getClientName() + ")");
            helper.setText(R.id.tv_person_name, "技师：" + item.getWorkerName());
        }
        helper.setText(R.id.tv_order_id, "单号：" + item.getOrdernum() + str);
        helper.setText(R.id.tv_create_time, "下单：" + item.getCreatetime());
        helper.setText(R.id.tv_count_money, "" + item.getDoorfee());
        helper.setText(R.id.tv_state, GetConstDataUtils.getRepairStatusByStatus(item.getStatus()));
        helper.setText(R.id.tv_bug_one, "类别：" + item.getBugonename());
        helper.setText(R.id.tv_do_second, doSomething[item.getStatus()]);
        helper.setVisible(R.id.tv_do_first, isShowFirstBtn[item.getStatus()]);

        if (item.getStatus() == 2)
            helper.setText(R.id.tv_do_first, "改约");
        else if (item.getStatus() == 5)
            helper.setText(R.id.tv_do_first, "查看故障处理");

        else if (item.getStatus() == 6)
            helper.setText(R.id.tv_do_first, "评价客户");


        //将业务类型的图片显示到列表
        ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(item.getPic1());


        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);

    }
}
