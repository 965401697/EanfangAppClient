package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.EanfangConst;
import com.eanfang.model.WorkCheckInfoBean;

import net.eanfang.worker.R;

import java.util.List;


/**
 * Created by wen on 2017/4/23.
 */

public class AddCheckInfoDetailAdapter extends BaseQuickAdapter<WorkCheckInfoBean.WorkInspectDetailsBean.WorkInspectDetailDisposesBean, BaseViewHolder> {
    public AddCheckInfoDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkCheckInfoBean.WorkInspectDetailsBean.WorkInspectDetailDisposesBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getDisposeInfo());
        helper.setVisible(R.id.tv_detai_status, true);
        if ( 0 == item.getStatus()) {
            helper.setText(R.id.tv_detai_status, EanfangConst.WORK_INSPECT_STATUS_REVIEW_STR);
        } else if (1 == item.getStatus()) {
            helper.setText(R.id.tv_detai_status, EanfangConst.WORK_INSPECT_STATUS_FAIL_STR);
        } else if (2 == item.getStatus()) {
            helper.setText(R.id.tv_detai_status, EanfangConst.WORK_INSPECT_STATUS_FINISH_STR);
        }
        helper.addOnClickListener(R.id.tv_delete);
    }
}
