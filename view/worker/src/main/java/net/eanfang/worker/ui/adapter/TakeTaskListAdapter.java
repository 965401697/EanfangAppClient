package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.TakeTaskListBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by wen on 2017/5/12.
 */

public class TakeTaskListAdapter extends BaseQuickAdapter<TakeTaskListBean.AllBean, BaseViewHolder> {
    //private final int type;
    private String[] mTitles = {
            "待确认", "待支付", "待完工", "待验收", "已完成"
    };


    public TakeTaskListAdapter(List<TakeTaskListBean.AllBean> data) {
        super(R.layout.item_task_list, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, TakeTaskListBean.AllBean item) {
        helper.setText(R.id.tv_company_name, item.getClientcompanyname());
        if ("1".equals(item.getItemtype())) {
            helper.setText(R.id.tv_type, "安装");
        } else {
            helper.setText(R.id.tv_type, "维修");
        }
        int status = item.getStatus();
        helper.setText(R.id.tv_state, mTitles[status]);

        //接包
        switch (status) {
            case 0://待确认，指的是自己投过接单申请的待确认发包单
                helper.setText(R.id.tv_do_first, "查看详情");
                helper.setVisible(R.id.tv_do_second, false);
//                helper.setText(R.id.tv_do_second, "查看申请");
                break;
            case 1://待支付，指的是已选中自己接包的待支付发包单
                helper.setText(R.id.tv_do_first, "查看详情");
                helper.setText(R.id.tv_do_second, "查看申请");
                break;
            case 2://带完工，指的是自己接的发包单，待完工状态
                helper.setText(R.id.tv_do_first, "联系发包人");
                helper.setText(R.id.tv_do_second, "确认完工");
                break;
            case 3://待验收，指的是自己已经确认完工的发包单
                helper.setText(R.id.tv_do_first, "联系发包人");
                helper.setText(R.id.tv_do_second, "查看申请");
                break;
            case 4://已完成，指的是自己接的已完成的发包单
                helper.setText(R.id.tv_do_first, "查看详情");
                helper.setText(R.id.tv_do_second, "查看申请");
                break;

        }


        helper.setText(R.id.tv_company_name, item.getCreatetime());
        helper.setText(R.id.tv_appointment_time, item.getDoortodoortime());
        helper.setText(R.id.tv_project_time, item.getPreworkduration());
        helper.setText(R.id.tv_business_type, item.getServicename());
        helper.setText(R.id.tv_project_address, item.getItemdetaillocation());
        helper.setText(R.id.tv_count_money, item.getBudget());
        GlideUtil.intoImageView(mContext,item.getPic1(),helper.getView(R.id.iv_upload));

        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);
    }


}
