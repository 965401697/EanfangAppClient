package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.entity.BughandleDetailEntity;
import com.eanfang.config.Config;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;

import net.eanfang.client.R;

import java.util.List;

import cn.hutool.core.util.StrUtil;

import static com.eanfang.base.kit.V.v;


/**
 * Created by wen on 2017/4/23.
 */

public class TroubleDetailAdapter extends BaseQuickAdapter<BughandleDetailEntity, BaseViewHolder> {
    public TroubleDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BughandleDetailEntity item) {
        String bugOne = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 1);
        String bugTwo = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 2);
        String bugThree = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 3);
        // 故障信息
        // 故障简述
        helper.setText(R.id.tv_faultDescribe, item.getFailureEntity().getBugDescription());
        // 设备名称
        helper.setText(R.id.tv_devicesName, item.getFailureEntity().getDeviceName());
        // 设备状态
        //1:保外 0：保内
        if(item.getFailureEntity().getMaintenanceStatus()!=null) {
            if (item.getFailureEntity().getMaintenanceStatus() == 0) {
                helper.setText(R.id.tv_deviceStatus, "保内");
            } else {
                helper.setText(R.id.tv_deviceStatus, "保外");
            }
        }
        if(item.getFailureEntity().getRepairCount()!=null) {
            // 维修历史
            helper.setText(R.id.tv_devicesHistory, String.valueOf(item.getFailureEntity().getRepairCount()));
        }

        helper.setText(R.id.tv_num, helper.getAdapterPosition() + 1 + "");
        //设备位置
        helper.setText(R.id.tv_devicesAdress, v(() -> item.getFailureEntity().getBugPosition()));
        //将业务类型的图片显示到列表
        String imgUrl = V.v(() -> item.getFailureEntity().getPictures().split(",")[0]);
        if (!StrUtil.isEmpty(imgUrl) && imgUrl.length() > 10) {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + imgUrl),helper.getView(R.id.iv_faultImg));
        }

    }
}
