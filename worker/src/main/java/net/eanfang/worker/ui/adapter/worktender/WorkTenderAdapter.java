package net.eanfang.worker.ui.adapter.worktender;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.model.IfbOrderEntity;
import com.eanfang.util.GetDateUtils;

import net.eanfang.worker.R;

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
        // 公告名称
        helper.setText(R.id.tv_tender_unit, item.getAnnouncementName());
        //招标单位
        helper.setText(R.id.tv_tender_name, item.getIfbCompanyName());
        //系统类别
        helper.setText(R.id.tv_tender_info, Config.get().getBusinessNameByCode(item.getBusinessOneCode(), 1));
        //项目地区
        helper.setText(R.id.tv_tender_address, Config.get().getAddressByCode(item.getProjectArea()));
        //发布时间
        helper.setText(R.id.tv_tender_update_time, GetDateUtils.dateToDateTimeStringForChinse(item.getReleaseTime()));
    }
}
