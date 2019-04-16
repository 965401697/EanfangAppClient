package net.eanfang.client.ui.activity.worksapce.maintenance;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.ShopMaintenanceExamDeviceEntity;

import net.eanfang.client.R;


/**
 * Created by O u r on 2018/7/19.
 */

public class MaintenanceHandleEditAdapter extends BaseQuickAdapter<ShopMaintenanceExamDeviceEntity, BaseViewHolder> {
    public MaintenanceHandleEditAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopMaintenanceExamDeviceEntity item) {

        if (item.getMaintenanceDetailEntity() == null) {
            helper.setText(R.id.tv_empasis_status, "去完善");
            helper.getView(R.id.tv_empasis_status).setBackground(helper.getView(R.id.tv_empasis_status).getResources().getDrawable(R.drawable.select_worker));
            ((TextView) helper.getView(R.id.tv_empasis_status)).setTextColor(helper.getView(R.id.tv_empasis_status).getResources().getColor(R.color.client));
        } else {
            helper.setText(R.id.tv_empasis_status, "已完善");
            helper.getView(R.id.tv_empasis_status).setBackground(null);
            ((TextView) helper.getView(R.id.tv_empasis_status)).setTextColor(helper.getView(R.id.tv_empasis_status).getResources().getColor(R.color.color_bottom));
        }


        helper.setText(R.id.tv_os_name, Config.get().getBaseNameByCode(Constant.SYS_TYPE, item.getBusinessThreeCode(), 3));
        if (!TextUtils.isEmpty(item.getPicture())) {
            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getPicture().split(",")[0]));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER));
        }
        helper.setText(R.id.tv_location_num, "位置编号    " + item.getLocationNumber());
        helper.setText(R.id.tv_position_location, "安装位置    " + item.getLocation());
        if (item.getStatus() == 0) {
            helper.setText(R.id.tv_status, "维保状态    保内");
        } else {
            helper.setText(R.id.tv_status, "维保状态    保外");
        }

        helper.setText(R.id.tv_history, "维修历史    " + String.valueOf(item.getRepairCount()));

    }
}
