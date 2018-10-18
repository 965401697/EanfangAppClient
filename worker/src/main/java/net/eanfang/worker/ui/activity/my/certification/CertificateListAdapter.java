package net.eanfang.worker.ui.activity.my.certification;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.HonorCertificateEntity;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/10/16.
 */

public class CertificateListAdapter extends BaseQuickAdapter<HonorCertificateEntity, BaseViewHolder> {
    public CertificateListAdapter() {
        super(R.layout.item_certificate_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, HonorCertificateEntity item) {
        helper.setText(R.id.tv_school_name, "荣誉名称：" + item.getHonorName());
        helper.setText(R.id.tv_school_major, "颁发机构：" + item.getAwardOrg());
        helper.setText(R.id.tv_school_time, "颁发时间：" + DateUtils.formatDate(item.getAwardTime(), "yyyy-MM-dd"));

        if (item.getHonorPics() != null) {
            String[] urls = V.v(() -> item.getHonorPics().split(","));
            //将业务类型的图片显示到列表
            ((SimpleDraweeView) helper.getView(R.id.iv_certificate_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_certificate_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER));
        }

        helper.addOnClickListener(R.id.tv_delete);
    }
}
