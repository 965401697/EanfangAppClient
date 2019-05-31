package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.WorkTaskListBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

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


//        if (mType == 2) {
//
//            helper.getView(R.id.tv_read_ns).setVisibility(View.VISIBLE);
//
//            if (item.getStatus() == EanfangConst.WORK_TASK_STATUS_READ) {
//                helper.setText(R.id.tv_read_ns, "已阅");
//                helper.setTextColor(R.id.tv_read_ns, Color.parseColor("#999999"));
//            } else {
//                helper.setText(R.id.tv_read_ns, "待阅");
//                helper.setTextColor(R.id.tv_read_ns, Color.parseColor("#FF4A4A"));
//            }
//        } else {
//            helper.getView(R.id.tv_read_ns).setVisibility(View.INVISIBLE);
//        }

        helper.setText(R.id.tv_title_name, "标题            " + v(() -> item.getTitle()));
        helper.setText(R.id.tv_pub_time, "发布时间    " + v(() -> item.getCreateTime()));
        helper.setText(R.id.tv_pub_person, "发布人        " + v(() -> item.getCreateUser().getAccountEntity().getRealName()));

        if (item.getWorkTaskDetail().getPictures() != null && !StringUtils.isEmpty(item.getWorkTaskDetail().getPictures())) {
            String[] urls = item.getWorkTaskDetail().getPictures().split(",");
            GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + urls[0], helper.getView(R.id.img_head));
        } else {
            GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER , helper.getView(R.id.img_head));//默认值
        }

    }

}
