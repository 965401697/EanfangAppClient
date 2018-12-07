package net.eanfang.client.ui.adapter;

import android.graphics.Color;
import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.EanfangConst;
import com.eanfang.model.WorkTaskListBean;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;

import static com.eanfang.util.V.v;


/**
 * Created by Mr.hou
 *
 * @on 2017/8/30  15:47
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkTaskListAdapter extends BaseQuickAdapter<WorkTaskListBean.ListBean, BaseViewHolder> {
    private int mType;

    public WorkTaskListAdapter(int type) {
        super(R.layout.item_work_list_layout);

        this.mType = type;
    }

    public WorkTaskListAdapter() {
        super(R.layout.item_work_list_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTaskListBean.ListBean item) {
        // 订单是否 已读 未读 1：新订单 0 已读
        if (item.getNewOrder() == 1) {
            helper.getView(R.id.tv_order_read).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_order_read).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_company_name, v(() -> item.getCreateCompany().getOrgName()));
        helper.setText(R.id.tv_depart_name, "部门            " + v(() -> item.getCreateOrg().getOrgName()));


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

        helper.setText(R.id.tv_title_name, "标题            " + v(() -> item.getTitle()));
        helper.setText(R.id.tv_pub_time, "发布时间    " + v(() -> item.getCreateTime()));
        helper.setText(R.id.tv_pub_person, "发布人        " + v(() -> item.getCreateUser().getAccountEntity().getRealName()));
//        helper.setText(R.id.tv_rev_person, "接收人：" + v(() -> item.getAssigneeUser().getAccountEntity().getRealName()));


        SimpleDraweeView head_pic = helper.getView(R.id.img_head);
        if (item.getWorkTaskDetail() != null && !StringUtils.isEmpty(item.getWorkTaskDetail().getPictures())) {
            String[] urls = item.getWorkTaskDetail().getPictures().split(",");
            head_pic.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
        } else {
            head_pic.setImageURI(Uri.parse(BuildConfig.OSS_SERVER));//默认值
        }

    }

}
