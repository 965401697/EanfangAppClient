package net.eanfang.worker.ui.adapter.worktender;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.config.Config;
import com.eanfang.util.DateKit;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

import java.util.Calendar;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

/**
 * @author guanluocang
 * @data 2019/1/11
 * @description 招标信息列表
 */

public class WorkTenderAdapter extends BaseQuickAdapter<IfbOrderEntity, BaseViewHolder> {
    public WorkTenderAdapter() {
        super(R.layout.layout_item_worktender);
    }

    @Override
    protected void convert(BaseViewHolder helper, IfbOrderEntity item) {
        String endTime = DateUtil.date(item.getIfbFileEndTime()).toString();
        // 公告名称
        helper.setText(R.id.tv_tender_name, item.getAnnouncementName());
        //招标单位
        helper.setText(R.id.tv_tender_unit, item.getIfbCompanyName());
        //系统类别
        helper.setText(R.id.tv_tender_info, Config.get().getBusinessNameByCode(item.getBusinessOneCode(), 1));
        //项目地区
        helper.setText(R.id.tv_tender_address, Config.get().getAddressByCode(item.getProjectArea()));
        //发布时间
        helper.setText(R.id.tv_tender_update_time, DateUtil.date(item.getReleaseTime()).toString());
        //截止时间
        if (!StringUtils.isEmpty(endTime)) {
            if (DateUtil.parse(endTime).getTime() - DateUtil.date().getTime() > 0) {
                int day = (int) DateUtil.date().between(DateUtil.parse(endTime), DateUnit.DAY);
                int hour = (int) DateUtil.date().between(DateKit.get(endTime).offset(DateField.DAY_OF_YEAR, -day).date, DateUnit.HOUR);
                int min = (int) DateUtil.date().between(DateKit.get(endTime).offset(DateField.DAY_OF_YEAR, -day).offset(DateField.HOUR, -hour).date, DateUnit.MINUTE);

                helper.setText(R.id.tv_cutoff_time, mContext.getString(R.string.text_tender_count_down, day, hour, min));
            } else {
                helper.setText(R.id.tv_cutoff_time, "已过期");
            }
//
//            //剩余时间
//            long currentTime = System.currentTimeMillis() / 1000;
//            long remainTime = DateUtil.parse(endTime).getTime() - currentTime;
//            if (remainTime > 0) {
//                int oneDay = 24 * 60 * 60;
//                int day = (int) (remainTime / oneDay);
//                int oneHour = 60 * 60;
//                int hour = (int) ((remainTime % oneDay) / oneHour);
//                int oneMin = 60;
//                int min = (int) (((remainTime % oneDay) % oneHour)) / oneMin;
//                helper.setText(R.id.tv_cutoff_time, mContext.getString(R.string.text_tender_count_down, day, hour, min));
//            }
        }
    }
}
