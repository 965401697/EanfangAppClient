package net.eanfang.worker.ui.adapter.repair;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.TransferLogEntity;

import net.eanfang.worker.R;

import java.util.List;

/**
 * 描述：
 * 转单
 *
 * @author Guanluocang
 * @date on 2018/6/9$  16:43$
 */
public class TroubleHangListAdapter extends BaseQuickAdapter<TransferLogEntity, BaseViewHolder> {
    public TroubleHangListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransferLogEntity item) {
        if (!StringUtils.isEmpty(item.getOriginalUserEntity().getAccountEntity().getRealName())) {
            helper.setText(R.id.tv_hangOrgUser, item.getOriginalUserEntity().getAccountEntity().getRealName() + "因");
        }
        if (item.getCause() != null) {
            helper.setText(R.id.tv_hangResson, GetConstDataUtils.getTransferCauseList().get(item.getCause()) + "在");
        }
        if (item.getCreateTime() != null) {
            helper.setText(R.id.tv_hangTime, item.getCreateTime() + "");
        }
        if (!StringUtils.isEmpty(item.getReceiveUserEntity().getAccountEntity().getRealName())) {
            helper.setText(R.id.tv_hangReceiveUser, "转给" + item.getReceiveUserEntity().getAccountEntity().getRealName());
        }

    }
}
