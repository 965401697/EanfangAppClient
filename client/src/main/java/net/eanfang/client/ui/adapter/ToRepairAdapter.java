package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairBugEntity;

import net.eanfang.client.R;

import java.util.List;


/**
 * 我要报修中的adapter
 * Created by Administrator on 2017/3/26.
 */

public class ToRepairAdapter extends BaseQuickAdapter<RepairBugEntity, BaseViewHolder> {

    public ToRepairAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairBugEntity item) {

        // 故障层级
        //String bugOneName = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 1);
        // String bugTwoName = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 2);
        String bugThreeName = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 3);

        helper.setText(R.id.tv_name, item.getSketch());
        helper.setText(R.id.tv_num, helper.getAdapterPosition() + 1 + "");
        helper.setText(R.id.tv_devicesNum, bugThreeName);// 设备编号
        helper.setText(R.id.tv_devicesAdress, item.getBugPosition());
        helper.addOnClickListener(R.id.tv_delete);
        SimpleDraweeView faultPic = helper.getView(R.id.iv_faultImg);
        if (!StringUtils.isEmpty(item.getPictures())) {
            String[] imgs = item.getPictures().split(",");
            faultPic.setImageURI(Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + imgs[0]));
        }

        //1:保外 0：保内
        if(item.getMaintenanceStatus()!=null) {
            if (item.getMaintenanceStatus() == 0) {
                helper.setText(R.id.tv_repair_status, "保内");
            } else {
                helper.setText(R.id.tv_repair_status, "保外");
            }
        }

        if(item.getRepairCount()!=null) {
            helper.setText(R.id.tv_repairHistory, String.valueOf(item.getRepairCount()));
        }

    }
}
