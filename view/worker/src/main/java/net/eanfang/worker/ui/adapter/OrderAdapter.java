package net.eanfang.worker.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.worker.R;

import java.util.List;

import cn.hutool.core.date.DateUtil;


/**
 * 合作公司的adapter
 * Created by Administrator on 2017/3/15.
 */

public class OrderAdapter extends BaseQuickAdapter<RepairOrderEntity, BaseViewHolder> {
    public OrderAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairOrderEntity item) {
        String status = "";
        if (item.getStatus().equals("1")) {
            status = "待回电";
        } else if (item.getStatus().equals("2")) {
            status = "待上门";
        } else if (item.getStatus().equals("3")) {
            status = "待完工";
        } else if (item.getStatus().equals("4")) {
            status = "待确认";
        }

        helper.setText(R.id.tv_company, V.v(() -> item.getOwnerOrg().getBelongCompany().getOrgName())
                + "  (" + V.v(() -> item.getOwnerUser().getAccountEntity().getRealName()) + ")");
        helper.setText(R.id.tv_state, status);
        helper.setText(R.id.tv_ordernum, item.getOrderNum());
        if (item.getBookTime() != null) {
            helper.setText(R.id.tv_pretime, DateUtil.date(item.getBookTime()).toDateStr());
        }
        helper.setText(R.id.tv_bugonename, DateUtil.date(item.getCreateTime()).toDateStr());
        //将业务类型的图片显示到列表
        if (item.getFailureEntity() != null) {
            String[] urls = V.v(() -> item.getFailureEntity().getPictures().split(","));
            //将业务类型的图片显示到列表
            GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + urls[0]), helper.getView(R.id.sdv_pic));
        }


        helper.addOnClickListener(R.id.tv_select);
    }
}
