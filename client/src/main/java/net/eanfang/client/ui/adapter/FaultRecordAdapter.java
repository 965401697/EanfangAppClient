package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.annimon.stream.Optional;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairFailureEntity;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;

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
        if (!StringUtils.isEmpty(imgUrl) && imgUrl.length() > 10) {
            ((SimpleDraweeView) helper.getView(R.id.iv_fault_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + imgUrl));
        }else {
//            ((SimpleDraweeView) helper.getView(R.id.iv_fault_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + imgUrl));
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
        helper.setText(R.id.tv_repairs_time, GetDateUtils.dateToDateTimeString(item.getCreateTime()));


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
