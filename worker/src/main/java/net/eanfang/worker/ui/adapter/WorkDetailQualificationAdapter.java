package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.QualificationCertificateEntity;

import net.eanfang.worker.R;


/**
 * @author guanluocang
 * @data 2018/12/26
 * @description技师详情 资质证书
 */

public class WorkDetailQualificationAdapter extends BaseQuickAdapter<QualificationCertificateEntity, BaseViewHolder> {
    public WorkDetailQualificationAdapter() {
        super(R.layout.layout_worker_detail_honor);
    }

    @Override
    protected void convert(BaseViewHolder helper, QualificationCertificateEntity item) {
        if (item.getCertificatePics() != null) {
            String[] info = V.v(() -> item.getCertificatePics().split(","));
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
}
