package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.HonorCertificateEntity;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2018/12/4
 * @description 技师详情 荣誉证书
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
                ((SimpleDraweeView) helper.getView(R.id.iv_pic)).setImageURI(BuildConfig.OSS_SERVER + info[i]);
            }
        } else {
            //一条
            ((SimpleDraweeView) helper.getView(R.id.iv_pic)).setImageURI(BuildConfig.OSS_SERVER + info[0]);
        }
    }
}