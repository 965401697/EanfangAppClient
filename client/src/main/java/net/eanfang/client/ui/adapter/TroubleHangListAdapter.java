package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.TransferLogEntity;

import net.eanfang.client.R;

import java.util.List;

/**
 * 描述：
 * 转单
 *
 * @author Guanluocang
 * @date on 2018/5/22$  15:29$
 */
public class TroubleHangListAdapter extends BaseQuickAdapter<TransferLogEntity, BaseViewHolder> {
    public TroubleHangListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransferLogEntity item) {
        if (!StringUtils.isEmpty(item.getOriginalUserEntity().getAccountEntity().getRealName()) && item.getCause() != null && item.getCreateTime() != null
                && !StringUtils.isEmpty(item.getReceiveUserEntity().getAccountEntity().getRealName())) {

            helper.setText(R.id.tv_hangContnet, item.getOriginalUserEntity().getAccountEntity().getRealName() + "因" +
                    GetConstDataUtils.getTransferCauseList().get(item.getCause()) + "在" +
                    item.getCreateTime() + "转给" + item.getReceiveUserEntity().getAccountEntity().getRealName()

            );
        }

    }
}
