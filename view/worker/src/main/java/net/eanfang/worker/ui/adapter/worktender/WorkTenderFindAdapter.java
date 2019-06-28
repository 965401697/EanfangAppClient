package net.eanfang.worker.ui.adapter.worktender;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.config.Config;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/6/11
 * @description 用工找活
 */
public class WorkTenderFindAdapter extends BaseQuickAdapter<TaskPublishEntity, BaseViewHolder> {
    public WorkTenderFindAdapter() {
        super(R.layout.layout_item_tender_find);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskPublishEntity item) {
        String endTime = GetDateUtils.dateToDateTimeString(item.getEndTime());
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
        //截止时间
        if (!StringUtils.isEmpty(endTime)) {
            //剩余时间
            long currentTime = System.currentTimeMillis() / 1000;
            long remainTime = GetDateUtils.convertDateToSecond(endTime) - currentTime;
            if (remainTime > 0) {
                int oneDay = 24 * 60 * 60;
                int day = (int) (remainTime / oneDay);
                int oneHour = 60 * 60;
                int hour = (int) ((remainTime % oneDay) / oneHour);
                int oneMin = 60;
                int min = (int) (((remainTime % oneDay) % oneHour)) / oneMin;
                helper.setText(R.id.tv_cutoff_time, mContext.getString(R.string.text_tender_count_down, day, hour, min));
            }
        }
        helper.addOnClickListener(R.id.tv_offer);
    }
}
