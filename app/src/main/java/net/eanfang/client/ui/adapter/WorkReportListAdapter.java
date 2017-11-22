package net.eanfang.client.ui.adapter;

import android.graphics.Color;
import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.config.EanfangConst;
import net.eanfang.client.ui.model.WorkReportListBean;
import net.eanfang.client.util.StringUtils;

import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:44
 * @email houzhongzhou@yeah.net
 * @desc 汇报item适配
 */

public class WorkReportListAdapter extends BaseQuickAdapter<WorkReportListBean.AllBean, BaseViewHolder> {
    public WorkReportListAdapter(List<WorkReportListBean.AllBean> data) {
        super(R.layout.item_work_report_layout, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, WorkReportListBean.AllBean item) {
        helper.setText(R.id.tv_company_name, item.getCompanyName());
        helper.setText(R.id.tv_depart_name, "部门：" + item.getDepartmentName());
        helper.setText(R.id.tv_type, "类型：" + item.getType());
        helper.setText(R.id.tv_pub_person, "发布人：" + item.getCreatUserName());
        helper.setText(R.id.tv_rev_person, "接收人：" + item.getReceiveUserName());
        helper.setText(R.id.tv_pub_time,"提交时间："+item.getCreateDate());
        if (item.getStatus().equals(EanfangConst.WORK_TASK_STATUS_READ)) {
            helper.setText(R.id.tv_read_ns, "已读");
        } else {
            helper.setText(R.id.tv_read_ns, "未读");
            helper.setTextColor(R.id.tv_read_ns, Color.parseColor("#0000ff"));
        }

        SimpleDraweeView head_pic=helper.getView(R.id.img_head);
        if (!StringUtils.isEmpty(item.getPic1()))
            head_pic.setImageURI(Uri.parse(item.getPic1()));

    }
}
