package net.eanfang.worker.ui.adapter;

import android.graphics.Color;
import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.EanfangConst;
import com.eanfang.model.WorkReportListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:44
 * @email houzhongzhou@yeah.net
 * @desc 汇报item适配
 */

public class WorkReportListAdapter extends BaseQuickAdapter<WorkReportListBean.ListBean, BaseViewHolder> {

    private int mType;

    public WorkReportListAdapter(int type) {
        super(R.layout.item_work_report_layout);
        this.mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkReportListBean.ListBean item) {
        // 订单是否 已读 未读 1：新订单 0 已读
        if (item.getNewOrder() == 1) {
            helper.getView(R.id.tv_order_read).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_order_read).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_company_name, item.getCreateCompany().getOrgName());
        helper.setText(R.id.tv_depart_name, "部门            " + item.getCreateOrg().getOrgName());
        helper.setText(R.id.tv_type, "类型            " + GetConstDataUtils.getWorkReportTypeList().get(item.getType()));
        helper.setText(R.id.tv_pub_person, "发布人        " + item.getCreateUser().getAccountEntity().getRealName());
//        helper.setText(R.id.tv_rev_person, "接收人：" + item.getAssigneeUser().getAccountEntity().getRealName());
        helper.setText(R.id.tv_pub_time, "提交时间    " + item.getCreateTime());
        if (mType == 2) {

            helper.getView(R.id.tv_read_ns).setVisibility(View.VISIBLE);

            if (item.getStatus() == EanfangConst.WORK_TASK_STATUS_READ) {
                helper.setText(R.id.tv_read_ns, "已阅");
                helper.setTextColor(R.id.tv_read_ns, Color.parseColor("#999999"));
            } else {
                helper.setText(R.id.tv_read_ns, "待阅");
                helper.setTextColor(R.id.tv_read_ns, Color.parseColor("#FF4A4A"));
            }
        } else {
            helper.getView(R.id.tv_read_ns).setVisibility(View.INVISIBLE);
        }
        SimpleDraweeView head_pic = helper.getView(R.id.img_head);
        if (!StringUtils.isEmpty(item.getWorkReportDetail().getPictures())) {
            String[] urls = item.getWorkReportDetail().getPictures().split(",");
            head_pic.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
        } else {
            head_pic.setImageURI(Uri.parse(BuildConfig.OSS_SERVER));//默认值
        }

    }
}
