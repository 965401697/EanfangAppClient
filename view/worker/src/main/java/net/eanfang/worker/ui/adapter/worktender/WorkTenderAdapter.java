package net.eanfang.worker.ui.adapter.worktender;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.config.Config;
import com.eanfang.util.DateKit;

import net.eanfang.worker.R;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

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
        String endTime = null;
        if (item.getIfbFileEndTime() != null) {
            endTime = DateUtil.date(item.getIfbFileEndTime()).toString();
        }
        // 公告名称
        helper.setText(R.id.tv_tender_name, item.getAnnouncementName());
        //招标单位
        helper.setText(R.id.tv_tender_unit, item.getIfbCompanyName());
        //系统类别
        helper.setText(R.id.tv_tender_info, Config.get().getBusinessNameByCode(item.getBusinessOneCode(), 1));
        //项目地区
        helper.setText(R.id.tv_tender_address, Config.get().getAddressByCode(item.getProjectArea()));
        //发布时间
        if (item.getReleaseTime() != null) {
            helper.setText(R.id.tv_tender_update_time, DateUtil.date(item.getReleaseTime()).toString());
        }
        //截止时间
        if (!StrUtil.isEmpty(endTime)) {
            if (DateUtil.parse(endTime).getTime() - DateUtil.date().getTime() > 0) {
                int day = (int) DateUtil.date().between(DateUtil.parse(endTime), DateUnit.DAY);
                int hour = (int) DateUtil.date().between(DateKit.get(endTime).offset(DateField.DAY_OF_YEAR, -day).date, DateUnit.HOUR);
                int min = (int) DateUtil.date().between(DateKit.get(endTime).offset(DateField.DAY_OF_YEAR, -day).offset(DateField.HOUR, -hour).date, DateUnit.MINUTE);

                helper.setText(R.id.tv_cutoff_time, mContext.getString(R.string.text_tender_count_down, day, hour, min));
            } else {
                helper.setText(R.id.tv_cutoff_time, "已过期");
            }
        }
    }
}
