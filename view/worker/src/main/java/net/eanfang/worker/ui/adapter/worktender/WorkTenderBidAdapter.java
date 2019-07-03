package net.eanfang.worker.ui.adapter.worktender;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.tender.TaskApplyEntity;
import com.eanfang.config.Config;
import com.eanfang.util.GetDateUtils;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2019/6/24
 * @description 我投标的
 */
public class WorkTenderBidAdapter extends BaseQuickAdapter<TaskApplyEntity, BaseViewHolder> {

    /**
     * 右上角招标状态
     */
    private int[] mTenderStatus = {R.mipmap.ic_tender_bid_ignore, R.mipmap.ic_tender_bid_ignore,
            R.mipmap.ic_tender_bid_wait, R.mipmap.ic_tender_bid_win};

    public WorkTenderBidAdapter() {
        super(R.layout.layout_item_personal_tender_bid);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskApplyEntity item) {
        helper.setImageResource(R.id.iv_status, mTenderStatus[item.getStatus()]);
        //名称
        helper.setText(R.id.tv_tender_name, Config.get().getBusinessNameByCode(item.getTaskPublishEntity().getSystemType(), 1));
        //类型
        helper.setText(R.id.tv_tender_type, Config.get().getBaseNameByCode(item.getTaskPublishEntity().getBusinessOneCode(), 2));
        // 时间
        helper.setText(R.id.tv_tender_time, GetDateUtils.dateToDateTimeString(item.getCreateTime()));
        // 订单编号
        helper.setText(R.id.tv_tender_number, item.getApplyNum());
        //招标单位
        helper.setText(R.id.tv_tender_company, item.getApplyCompanyName());
        // 预算金额
        helper.setText(R.id.tv_tender_budget, item.getTaskPublishEntity().getBudget() + "元" + "/" + item.getBudgetUnit());
        // 施工方案
        helper.setText(R.id.tv_tender_require, item.getDescription());
        // 地区
        helper.setText(R.id.tv_tender_adress, item.getTaskPublishEntity().getProvince() + item.getTaskPublishEntity().getCity() + item.getTaskPublishEntity().getCounty());
        // 投标数
        helper.setText(R.id.tv_tender_num, item.getTaskPublishEntity().getOfferCount()+"");
        helper.setGone(R.id.tv_agagin, item.getStatus() == 1 || item.getStatus() == 2 ? true : false);
        helper.setGone(R.id.tv_contact, item.getStatus() == 3 ? true : false);
        helper.addOnClickListener(R.id.tv_agagin);
        helper.addOnClickListener(R.id.tv_contact);
    }
}
