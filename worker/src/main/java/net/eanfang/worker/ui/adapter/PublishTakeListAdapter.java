package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.model.MineTaskListBean;
import com.eanfang.util.GetConstDataUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import java.util.List;


/**
 * Created by wen on 2017/5/12.
 */

public class PublishTakeListAdapter extends BaseQuickAdapter<MineTaskListBean.ListBean, BaseViewHolder> {
    //private final int type;
    private String[] mTitles = {
            "待确认", "待支付", "待完工", "待验收", "已完成"
    };

    public PublishTakeListAdapter(List<MineTaskListBean.ListBean> data) {
        super(R.layout.item_task_list, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, MineTaskListBean.ListBean item) {
        helper.setText(R.id.tv_company_name, item.getProjectCompanyName());

        helper.setText(R.id.tv_type, GetConstDataUtils.getCooperationTypeList().get(item.getType()));

        int status = item.getStatus();
        helper.setText(R.id.tv_state, mTitles[status]);

        //发包
        switch (status) {
            case 0://待确认，指的是提交了发包，等待确认接包申请
                helper.setText(R.id.tv_do_first, "联系发包人");
                helper.setText(R.id.tv_do_second, "查看详情");
                break;
            case 1://待支付，指的是确认了接包，等待支付费用
                helper.setText(R.id.tv_do_first, "联系发包人");
                helper.setText(R.id.tv_do_second, "查看详情");
                break;
            case 2://带完工，指的是等待接包方完工
                helper.setText(R.id.tv_do_first, "联系接包人");
                helper.setText(R.id.tv_do_second, "申请验收");
                break;
            case 3://待验收，指的是等到发包方验收工作
                helper.setText(R.id.tv_do_first, "联系接包人");
                helper.setText(R.id.tv_do_second, "查看详情");
                break;
            case 4://已完成 ，就是订单已完成
                helper.setVisible(R.id.tv_do_first, false);
                helper.setText(R.id.tv_do_second, "查看申请");
                break;
            default:
                break;
        }


        helper.setText(R.id.tv_appointment_time, item.getToDoorTime());
        helper.setText(R.id.tv_project_time, GetConstDataUtils.getPredictList().get(item.getPredicttime()));
        helper.setText(R.id.tv_business_type, Config.get().getBusinessNameByCode(item.getBusinessOneCode(), 1));
        helper.setText(R.id.tv_project_address, item.getDetailPlace());
        helper.setText(R.id.tv_count_money, GetConstDataUtils.getBudgetList().get(item.getBudget()));

        if (item.getPictures() != null) {
            String[] urls = item.getPictures().split(",");
            if (urls.length >= 1) {
                ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(BuildConfig.OSS_SERVER + urls[0]);
            }
        }


        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);
    }


}
