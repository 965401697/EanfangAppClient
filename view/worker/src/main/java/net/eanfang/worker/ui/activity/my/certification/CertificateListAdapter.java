package net.eanfang.worker.ui.activity.my.certification;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;
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
        if (!TextUtils.isEmpty(item.getHonorName())) {
            helper.setText(R.id.tv_school_name, "荣誉名称：" + item.getHonorName());
        } else {
            helper.setText(R.id.tv_school_name, "荣誉名称：");
        }
        if (!TextUtils.isEmpty(item.getAwardOrg())) {
            helper.setText(R.id.tv_school_major, "颁发机构：" + item.getAwardOrg());
        } else {
            helper.setText(R.id.tv_school_major, "颁发机构：");
        }
        if (item.getAwardTime() != null) {
            helper.setText(R.id.tv_school_time, "颁发时间：" + DateUtils.formatDate(item.getAwardTime(), "yyyy-MM-dd"));
        } else {
            helper.setText(R.id.tv_school_time, "颁发时间：");
        }

        if (item.getHonorPics() != null) {
            String[] urls = V.v(() -> item.getHonorPics().split(","));
            //将业务类型的图片显示到列表
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + urls[0]),helper.getView(R.id.iv_certificate_pic));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER),helper.getView(R.id.iv_certificate_pic));
        }
        helper.addOnClickListener(R.id.tv_delete);
    }
}
