package net.eanfang.worker.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.TransferLogEntity;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.worker.R;

import java.util.List;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/6/12$  16:46$
 */
public class PutUpOrderRecordAdapter extends BaseQuickAdapter<TransferLogEntity, BaseViewHolder> {

    public PutUpOrderRecordAdapter(int layoutResId, List<TransferLogEntity> transferLogEntities) {
        super(layoutResId);
        this.mData = transferLogEntities;
    }

    @Override
    protected void convert(BaseViewHolder helper, TransferLogEntity item) {
        // 头像
        SimpleDraweeView iv_header = helper.getView(R.id.iv_header);
        if (!StringUtils.isEmpty(item.getReceiveUserEntity().getAccountEntity().getAvatar())) {
            iv_header.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getReceiveUserEntity().getAccountEntity().getAvatar()));
        }
        helper.setText(R.id.tv_order_num, item.getOrderId() + "");
        helper.setText(R.id.tv_order_time, item.getCreateTime() + "");
        helper.setText(R.id.tv_order_reason, GetConstDataUtils.getTransferCauseList().get(item.getCause()));
    }

}
