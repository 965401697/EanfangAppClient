package net.eanfang.worker.ui.adapter.worktender;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;

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
        //名称
//        helper.setText(R.id.tv_tender_name, item.get());
        //时间
//        helper.setText(R.id.tv_tender_name, item.getAnnouncementName());
        //地点
//        helper.setText(R.id.tv_tender_name, item.getAnnouncementName());
        //工期
//        helper.setText(R.id.tv_tender_name, item.getAnnouncementName());
        //预算
//        helper.setText(R.id.tv_tender_name, item.getAnnouncementName());
        //要求
//        helper.setText(R.id.tv_tender_name, item.getAnnouncementName());
        //截止时间
//        helper.setText(R.id.tv_tender_name, "距截止还剩："+ item.getAnnouncementName());
        helper.addOnClickListener(R.id.tv_offer);
    }
}
