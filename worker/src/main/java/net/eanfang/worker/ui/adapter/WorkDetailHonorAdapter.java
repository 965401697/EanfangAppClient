package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.HonorCertificateEntity;

import net.eanfang.worker.R;


/**
 * @author guanluocang
 * @data 2018/12/4
 * @description
 */

public class WorkDetailHonorAdapter extends BaseQuickAdapter<HonorCertificateEntity, BaseViewHolder> {
    public WorkDetailHonorAdapter() {
        super(R.layout.layout_worker_detail_honor);
    }

    @Override
    protected void convert(BaseViewHolder helper, HonorCertificateEntity item) {
        ((SimpleDraweeView) helper.getView(R.id.iv_pic)).setImageURI(BuildConfig.OSS_SERVER + item.getHonorPics());
    }
}