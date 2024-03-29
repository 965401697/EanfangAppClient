package net.eanfang.worker.ui.adapter;

import android.net.Uri;

import com.annimon.stream.Optional;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.entity.RepairFailureEntity;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;

import net.eanfang.worker.R;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;


/**
 * Created by O u r on 2018/6/19.
 */

public class FaultRecordAdapter extends BaseQuickAdapter<RepairFailureEntity, BaseViewHolder> {
    public FaultRecordAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairFailureEntity item) {

        //将业务类型的图片显示到列表
        String imgUrl = V.v(() -> item.getPictures().split(",")[0]);
        if (!StrUtil.isEmpty(imgUrl) && imgUrl.length() > 10) {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + imgUrl),helper.getView(R.id.iv_fault_pic));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER),helper.getView(R.id.iv_fault_pic));
        }

        helper.setText(R.id.tv_fault_name, item.getDeviceName());
//         状态（0：待修复，1：修复完成，2：遗留）
        if (item.getStatus() == 0) {
            helper.setText(R.id.tv_fault_status, "待修复");
        } else if (item.getStatus() == 1) {
            helper.setText(R.id.tv_fault_status, "修复完成");
        } else {
            helper.setText(R.id.tv_fault_status, "遗留");
        }
        helper.setText(R.id.tv_position_num, item.getLocationNumber());
        helper.setText(R.id.tv_position, item.getBugPosition());
        helper.setText(R.id.tv_repairs_time, DateUtil.date(item.getCreateTime()).toString());


        if (item.getAssigneeOrgEntity() != null && item.getAssigneeOrgEntity().getBelongCompany() != null) {
            helper.setText(R.id.tv_repair_company, Optional.ofNullable(item.getAssigneeOrgEntity().getBelongCompany().getOrgName()).orElseGet(() -> ""));

        } else {
            helper.setText(R.id.tv_repair_company, "");
        }

        if (item.getAssigneeUserEntity() != null && item.getAssigneeUserEntity().getAccountEntity() != null) {
            helper.setText(R.id.tv_repair_preson, Optional.ofNullable(item.getAssigneeUserEntity().getAccountEntity().getRealName()).orElseGet(() -> ""));
        } else {
            helper.setText(R.id.tv_repair_preson, "");
        }


    }
}
