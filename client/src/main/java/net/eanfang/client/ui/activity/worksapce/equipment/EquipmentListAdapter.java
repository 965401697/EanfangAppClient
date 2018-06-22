package net.eanfang.client.ui.activity.worksapce.equipment;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.CustDeviceEntity;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/6/20.
 */

public class EquipmentListAdapter extends BaseQuickAdapter<CustDeviceEntity, BaseViewHolder> {


    public EquipmentListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustDeviceEntity item) {

        ((SimpleDraweeView) helper.getView(R.id.iv_equipment_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getPicture().split(",")[0]));

        helper.setText(R.id.tv_equipment_name, item.getDeviceName());

//        状态(0出厂,1仓储运输，2正常运行，3故障待修复，4备用，5禁用，6报废)
        if (item.getStatus() == 0) {
            helper.setText(R.id.tv_equipment_status, "出厂");
        } else if (item.getStatus() == 1) {
            helper.setText(R.id.tv_equipment_status, "仓储运输");
        } else if (item.getStatus() == 2) {
            helper.setText(R.id.tv_equipment_status, "正常运行");
        } else if (item.getStatus() == 3) {
            helper.setText(R.id.tv_equipment_status, "故障待修复");
        } else if (item.getStatus() == 4) {
            helper.setText(R.id.tv_equipment_status, "备用");
        } else if (item.getStatus() == 5) {
            helper.setText(R.id.tv_equipment_status, "禁用");
        } else {
            helper.setText(R.id.tv_equipment_status, "报废");
        }

        helper.setText(R.id.tv_position_num, item.getLocationNumber());
        helper.setText(R.id.tv_position, item.getLocation());
        helper.setText(R.id.tv_setup_time, GetDateUtils.dateToFormatString(item.getInstallDate(), "yyyy-MM-dd"));
        //1:保外 0：保内
        if (item.getWarrantyStatus() == 0) {
            helper.setText(R.id.tv_repair_status, "保内");
        } else {
            helper.setText(R.id.tv_repair_status, "保外");
        }

        helper.setText(R.id.tv_change_num, String.valueOf(item.getDeviceVersion()));
    }
}
