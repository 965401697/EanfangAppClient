package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.ShopMaintenanceOrderEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

/**
 * Created by O u r on 2018/7/16.
 */

public class MaintenanceListAdapter extends BaseQuickAdapter<ShopMaintenanceOrderEntity, BaseViewHolder> {
    public MaintenanceListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopMaintenanceOrderEntity item) {


        if (item.getOwnerOrgEntity() != null && item.getOwnerUserEntity() != null && item.getOwnerUserEntity().getAccountEntity() != null) {
            // 公司名称
            helper.setText(R.id.tv_company_name, item.getOwnerOrgEntity().getOrgName());
            // 客户名称
            helper.setText(R.id.tv_real_name, item.getOwnerUserEntity().getAccountEntity().getRealName());
        } else if (item.getOwnerOrgEntity() == null) {
//            helper.setText(R.id.tv_company_name, item.getOwnerUserEntity().getAccountEntity().getRealName());
        }

        if (item.getOrderNum() != null) {
            helper.setText(R.id.tv_order_id, "订单编号：" + item.getOrderNum());
        }
        helper.setText(R.id.tv_plan_time, "规划时间：" + GetDateUtils.dateToDateTimeString(item.getCreateTime()));

        if (item.getPeriod() == 0) {
            helper.setText(R.id.tv_period, "维保周期：" + "周检");
        } else if (item.getPeriod() == 1) {
            helper.setText(R.id.tv_period, "维保周期：" + "月检");
        } else if (item.getPeriod() == 2) {
            helper.setText(R.id.tv_period, "维保周期：" + "季检");
        } else if (item.getPeriod() == 3) {
            helper.setText(R.id.tv_period, "维保周期：" + "年检");
        }

//        1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
        if (item.getStatus() == 1) {

            helper.setText(R.id.tv_state, "待预约");
            helper.setText(R.id.tv_do_second, "马上回电");
            helper.getView(R.id.tv_do_first).setVisibility(View.GONE);
            helper.getView(R.id.tv_do_second).setVisibility(View.VISIBLE);

        } else if (item.getStatus() == 2) {
            helper.setText(R.id.tv_state, "待上门");
            helper.setText(R.id.tv_do_first, "改约");
            helper.setText(R.id.tv_do_second, "签到");
            helper.getView(R.id.tv_do_first).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_do_second).setVisibility(View.VISIBLE);
        } else if (item.getStatus() == 3) {
            helper.setText(R.id.tv_state, "维修中");
            helper.setText(R.id.tv_do_first, "联系客户");
            helper.setText(R.id.tv_do_second, "完工");
            helper.getView(R.id.tv_do_first).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_do_second).setVisibility(View.VISIBLE);
        } else if (item.getStatus() == 4) {
            helper.setText(R.id.tv_state, "待验收");
            helper.setText(R.id.tv_do_second, "查看故障处理");
            helper.getView(R.id.tv_do_first).setVisibility(View.GONE);
            helper.getView(R.id.tv_do_second).setVisibility(View.VISIBLE);
        } else if (item.getStatus() == 5) {
            helper.setText(R.id.tv_state, "订单完成");
            helper.setText(R.id.tv_do_second, "查看故障处理");
            helper.getView(R.id.tv_do_first).setVisibility(View.GONE);
            helper.getView(R.id.tv_do_second).setVisibility(View.VISIBLE);
        }


//        将业务类型的图片显示到列表
        String imgUrl = V.v(() -> item.getExamDeviceEntityList().get(0).getPicture().split(",")[0]);
        if (!StringUtils.isEmpty(imgUrl) && imgUrl.length() > 10) {
            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + imgUrl));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER));
        }

        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);

    }
}