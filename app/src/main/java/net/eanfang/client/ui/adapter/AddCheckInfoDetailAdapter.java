package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.config.EanfangConst;
import net.eanfang.client.ui.model.WorkCheckInfoBean;

import java.util.List;



/**
 * Created by wen on 2017/4/23.
 */

public class AddCheckInfoDetailAdapter extends BaseQuickAdapter<WorkCheckInfoBean.BeanBean.DetailsBeanX.DetailsBean, BaseViewHolder> {
    public AddCheckInfoDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkCheckInfoBean.BeanBean.DetailsBeanX.DetailsBean item) {
        helper.setText(R.id.tv_detail_name, helper.getPosition() + 1 + "." + item.getInspectDetailTitle());
        helper.setVisible(R.id.tv_detai_status, true);
        if (EanfangConst.WORK_INSPECT_STATUS_REVIEW.equals(item.getStatus())) {
            helper.setText(R.id.tv_detai_status, EanfangConst.WORK_INSPECT_STATUS_REVIEW_STR);
        } else if (EanfangConst.WORK_INSPECT_STATUS_FAIL.equals(item.getStatus())) {
            helper.setText(R.id.tv_detai_status, EanfangConst.WORK_INSPECT_STATUS_FAIL_STR);
        } else if (EanfangConst.WORK_INSPECT_STATUS_FINISH.equals(item.getStatus())) {
            helper.setText(R.id.tv_detai_status, EanfangConst.WORK_INSPECT_STATUS_FINISH_STR);
        }
        helper.addOnClickListener(R.id.tv_delete);
    }
}
