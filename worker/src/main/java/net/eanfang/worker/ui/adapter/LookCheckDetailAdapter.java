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

public class LookCheckDetailAdapter extends BaseQuickAdapter<WorkCheckInfoBean.WorkInspectDetailsBean, BaseViewHolder> {
    public LookCheckDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkCheckInfoBean.WorkInspectDetailsBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getTitle());
        helper.setVisible(R.id.tv_detai_status, true);
        if (EanfangConst.WORK_INSPECT_STATUS_CREATE == item.getStatus()) {
            helper.setText(R.id.tv_detai_status, EanfangConst.WORK_INSPECT_STATUS_CREATE_STR);
        } else if (EanfangConst.WORK_INSPECT_STATUS_REVIEW == item.getStatus()) {
            helper.setText(R.id.tv_detai_status, EanfangConst.WORK_INSPECT_STATUS_REVIEW_STR);
        } else if (EanfangConst.WORK_INSPECT_STATUS_FAIL == item.getStatus()) {
            helper.setText(R.id.tv_detai_status, EanfangConst.WORK_INSPECT_STATUS_FAIL_STR);
        } else if (EanfangConst.WORK_INSPECT_STATUS_FINISH == item.getStatus()) {
            helper.setText(R.id.tv_detai_status, EanfangConst.WORK_INSPECT_STATUS_FINISH_STR);
        }
        helper.addOnClickListener(R.id.tv_delete);
    }
}