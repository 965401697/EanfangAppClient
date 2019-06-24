package net.eanfang.worker.ui.adapter.worktender;

import android.widget.BaseAdapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;

import net.eanfang.worker.R;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/6/24
 * @description 我发布的
 */
public class WorkTenderReleaseAdapter extends BaseQuickAdapter<TaskPublishEntity, BaseViewHolder> {

    public WorkTenderReleaseAdapter() {
        super( R.layout.layout_item_personal_tender_release);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskPublishEntity item) {

    }
}
