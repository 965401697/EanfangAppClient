package net.eanfang.worker.ui.adapter.worktender;

import android.widget.BaseAdapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/6/24
 * @description 我投标的
 */
public class WorkTenderBidAdapter extends BaseQuickAdapter<TaskPublishEntity, BaseViewHolder> {
    public WorkTenderBidAdapter(int layoutResId, @Nullable List<TaskPublishEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskPublishEntity item) {

    }
}
