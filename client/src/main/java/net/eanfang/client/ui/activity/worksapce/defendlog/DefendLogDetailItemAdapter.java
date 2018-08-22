package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.GetConstDataUtils;
import com.yaf.base.entity.LogDetailsEntity;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/5/23.
 */

public class DefendLogDetailItemAdapter extends BaseQuickAdapter<LogDetailsEntity, BaseViewHolder> {
    public DefendLogDetailItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, LogDetailsEntity item) {

//        helper.setText(R.id.tv_desc, (helper.getAdapterPosition() + 1) + "," + item.getPlayLocaltion());


        if (item.getLogType() == 1) {
            helper.setText(R.id.tv_desc, GetConstDataUtils.getThroughCause().get(item.getAlarmReason()) + "次数：");
            helper.setText(R.id.tv_cause, String.valueOf(item.getAlarmNum()));
        } else if (item.getLogType() == 2) {
            if (GetConstDataUtils.getFlaseCause().get(item.getAlarmReason()).equals("动物闯防")) {
                helper.getConvertView().setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_desc, GetConstDataUtils.getFlaseCause().get(item.getAlarmReason()) + "误报次数：");
                helper.setText(R.id.tv_cause, String.valueOf(item.getAlarmNum()));
            } else if (GetConstDataUtils.getFlaseCause().get(item.getAlarmReason()).equals("设备故障")) {
                helper.getConvertView().setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_desc, GetConstDataUtils.getFlaseCause().get(item.getAlarmReason()) + "误报次数：");
                helper.setText(R.id.tv_cause, String.valueOf(item.getAlarmNum()));
            } else if (GetConstDataUtils.getFlaseCause().get(item.getAlarmReason()).equals("其他误报")) {
                helper.getConvertView().setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_desc, GetConstDataUtils.getFlaseCause().get(item.getAlarmReason()) + "误报次数：");
                helper.setText(R.id.tv_cause, String.valueOf(item.getAlarmNum()));
            } else {
                helper.getConvertView().setVisibility(View.GONE);
            }

        } else {
            helper.setText(R.id.tv_desc, GetConstDataUtils.getBypassCause().get(item.getAlarmReason()) + "旁路次数：");
            helper.setText(R.id.tv_cause, String.valueOf(item.getAlarmNum()));
        }
    }
}
