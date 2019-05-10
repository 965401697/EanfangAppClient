package net.eanfang.client.ui.activity.worksapce.maintenance;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.ShopMaintenanceExamDeviceEntity;

import net.eanfang.client.R;


/**
 * Created by O u r on 2018/7/16.
 */

public class MaintenanceOrderDetailEmphasisDeviceAdapter extends BaseQuickAdapter<ShopMaintenanceExamDeviceEntity, BaseViewHolder> {

    private Integer isSelect;

    public MaintenanceOrderDetailEmphasisDeviceAdapter(int layoutResId) {
        super(layoutResId);
    }
    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopMaintenanceExamDeviceEntity item) {
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


        helper.setText(R.id.tv_history, "维修历史    " + item.getRepairCount());

        if (isSelect==4||isSelect==5){
            helper.setVisible(R.id.iv_arrow, true);
        }else{
            helper.setVisible(R.id.iv_arrow, false);
        }

    }

}
