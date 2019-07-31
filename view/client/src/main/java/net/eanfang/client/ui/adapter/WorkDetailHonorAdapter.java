package net.eanfang.client.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.biz.model.entity.HonorCertificateEntity;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2018/12/4
 * @description 技师详情 荣誉证书
 */

public class WorkDetailHonorAdapter extends BaseQuickAdapter<HonorCertificateEntity, BaseViewHolder> {
    private Context context;
    public WorkDetailHonorAdapter(Context context) {
        super(R.layout.layout_worker_detail_honor);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HonorCertificateEntity item) {
        String[] info = item.getHonorPics().split(",");
        if (info.length > 0) {
            //多条
            for (int i = 0; i < info.length; i++) {
                GlideUtil.intoImageView(context,BuildConfig.OSS_SERVER + info[i],helper.getView(R.id.iv_pic));
            }
        } else {
            //一条
            GlideUtil.intoImageView(context,BuildConfig.OSS_SERVER + info[0],helper.getView(R.id.iv_pic));
        }
    }
}
