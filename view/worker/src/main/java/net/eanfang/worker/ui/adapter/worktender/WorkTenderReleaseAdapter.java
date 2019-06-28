package net.eanfang.worker.ui.adapter.worktender;

import android.widget.BaseAdapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.config.Config;
import com.eanfang.util.GetDateUtils;

import net.eanfang.worker.R;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/6/24
 * @description 我发布的
 */
public class WorkTenderReleaseAdapter extends BaseQuickAdapter<TaskPublishEntity, BaseViewHolder> {

    /**
     * 右上角招标状态
     */
    private int[] mTenderStatus = {R.mipmap.ic_worker_tender_close,
            R.mipmap.ic_worker_tender_bindding, R.mipmap.ic_worker_tender_win, R.mipmap.ic_worker_tender_fail};

    public WorkTenderReleaseAdapter() {
        super(R.layout.layout_item_personal_tender_release);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskPublishEntity item) {
        //名称
        helper.setText(R.id.tv_tender_name, Config.get().getBusinessNameByCode(item.getSystemType(), 1));
        //类型
        helper.setText(R.id.tv_tender_type, Config.get().getBaseNameByCode(item.getBusinessOneCode(), 2));
        // 时间
        helper.setText(R.id.tv_tender_time, GetDateUtils.dateToDateTimeString(item.getCreateTime()));
        //地点
        helper.setText(R.id.tv_tender_address, item.getProvince() + item.getCity() + item.getCounty());
        //工期
        helper.setText(R.id.tv_tender_limit_time, item.getPredicttime() + "天");
        // 预算
        helper.setText(R.id.tv_tender_budget, item.getBudget() + "元" + "/" + item.getBudgetUnit());
        // 用工要求
        helper.setText(R.id.tv_tender_require, item.getLaborRequirements());
        // 投标数
        helper.setText(R.id.tv_tender_num, item.getOfferCount() + "");
        /**
         * 状态
         *
         * 0关闭发包(只有在status是待确认状态才能关闭发包)，1在发布==招标中，2已被接受==已中标3已流标
         */
        helper.setImageResource(R.id.iv_status, mTenderStatus[item.getPublishStatus()]);
        helper.setGone(R.id.tv_release, item.getPublishStatus() == 0 ? true : false);
        helper.setGone(R.id.tv_close, item.getPublishStatus() == 1 ? true : false);
        helper.setGone(R.id.tv_offer, item.getPublishStatus() == 1 ? true : false);
        helper.setGone(R.id.tv_contact, item.getPublishStatus() == 2 ? true : false);
        helper.addOnClickListener(R.id.tv_release);
        helper.addOnClickListener(R.id.tv_close);
        helper.addOnClickListener(R.id.tv_offer);
        helper.addOnClickListener(R.id.tv_contact);
    }
}
