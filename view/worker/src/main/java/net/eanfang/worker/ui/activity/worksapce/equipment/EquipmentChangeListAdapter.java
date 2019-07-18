package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.yaf.base.entity.CustDeviceChangeLogEntity;

import net.eanfang.worker.R;

import cn.hutool.core.date.DateUtil;


/**
 * Created by O u r on 2018/6/20.
 */

public class EquipmentChangeListAdapter extends BaseQuickAdapter<CustDeviceChangeLogEntity, BaseViewHolder> {


    public EquipmentChangeListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustDeviceChangeLogEntity item) {
        GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + item.getLocationPictures().split(",")[0]),helper.getView(R.id.iv_equipment_pic));
        helper.setText(R.id.tv_company_person_name, item.getRepairCompany() + "        " + item.getRepairUser());


        helper.setText(R.id.tv_position_num, item.getLocationNumber());
        helper.setText(R.id.tv_setup_time, DateUtil.date(item.getInstallDate()).toDateStr());
        helper.setText(R.id.tv_position, item.getLocation());
        //1:保外 0：保内
        if (item.getWarrantyStatus() == 0) {
            helper.setText(R.id.tv_repair_status, "保内");
        } else {
            helper.setText(R.id.tv_repair_status, "保外");
        }

        helper.setText(R.id.tv_keep_year, String.valueOf(item.getWarrantyPeriod()));
    }

}

