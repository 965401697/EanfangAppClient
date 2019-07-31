package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.biz.model.entity.HonorCertificateEntity;

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
        String[] info = item.getHonorPics().split(",");
        if (info.length > 0) {
            //多条
            for (int i = 0; i < info.length; i++) {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + info[i],helper.getView(R.id.iv_pic));
            }
        } else {
            //一条
            GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + info[0],helper.getView(R.id.iv_pic));
        }
    }
}
