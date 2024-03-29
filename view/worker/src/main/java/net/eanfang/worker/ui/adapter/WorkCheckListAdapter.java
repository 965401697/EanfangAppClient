package net.eanfang.worker.ui.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.WorkCheckListBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

import cn.hutool.core.util.StrUtil;


/**
 * Created by Mr.hou
 *
 * @on 2017/8/30  15:47
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkCheckListAdapter extends BaseQuickAdapter<WorkCheckListBean.ListBean, BaseViewHolder> {

    public WorkCheckListAdapter() {
        super(R.layout.item_check_list_layout);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, WorkCheckListBean.ListBean item) {
        /**
         *
         *  ("待处理",0),
         *  ("待审核",1),
         *  ("已拒绝",2),
         *  ("处理完成",3);
         */
        if (item.getStatus() == 0) {
            helper.setText(R.id.tv_status, "待处理");
            helper.setTextColor(R.id.tv_company_name, R.color.colorPrimary);
        } else if (item.getStatus() == 1) {
            helper.setText(R.id.tv_status, "待审核");
            helper.setTextColor(R.id.tv_company_name, R.color.color_bottom);
        } else if (item.getStatus() == 2) {
            helper.setText(R.id.tv_status, "已拒绝");
            helper.setTextColor(R.id.tv_company_name, R.color.color_bottom);
        } else if (item.getStatus() == 3) {
            helper.setText(R.id.tv_status, "处理完成");
            helper.setTextColor(R.id.tv_company_name, R.color.color_bottom);
        }
        // 订单是否 已读 未读 1：新订单 0 已读
        if (item.getNewOrder() == 1) {
            helper.getView(R.id.tv_order_read).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_order_read).setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_company_name, item.getCompanyName());
        helper.setText(R.id.tv_check_person, "检查人：" + item.getCreateUser().getAccountEntity().getRealName());
//        helper.setText(R.id.tv_duty_person, "负责人：" + item.getAssigneeUser().getAccountEntity().getRealName());
        helper.setText(R.id.tv_check_time, "创建时间：" + item.getCreateTime());
        helper.setText(R.id.tv_change_tiem, "整改期限：" + item.getChangeDeadlineTime());

        ImageView head_pic = helper.getView(R.id.img_head);
        if (item.getWorkInspectDetail() != null) {
            if (!StrUtil.isEmpty(item.getWorkInspectDetail().getPictures())) {
                String[] urls = item.getWorkInspectDetail().getPictures().split(",");
                GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + urls[0]),head_pic);
            } else {
                GlideUtil.intoImageView(mContext,"",head_pic);
            }
        }

    }

}
