package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.QualificationCertificateEntity;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description adapter
 */

public class JsaQualificationsAndAbilitiesAdapter extends BaseQuickAdapter<QualificationCertificateEntity, BaseViewHolder> {
    private boolean isDelete;

    public JsaQualificationsAndAbilitiesAdapter(boolean mDelete) {
        super(R.layout.layout_item_jsa);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, QualificationCertificateEntity item) {

        if (!TextUtils.isEmpty(item.getCertificateName())) {
            helper.setText(R.id.tv_school_name, "资质名称：" + item.getCertificateName());
        } else {
            helper.setText(R.id.tv_school_name, "资质名称：");

        }
        if (!TextUtils.isEmpty(item.getAwardOrg())) {
            helper.setText(R.id.tv_school_major, "颁发机构：" + item.getAwardOrg());
        } else {
            helper.setText(R.id.tv_school_major, "颁发机构：");
        }
        if (item.getBeginTime() != null && item.getEndTime() != null) {
            helper.setText(R.id.tv_school_time, "有效截止期：" + DateUtils.formatDate(item.getEndTime(), "yyyy-MM-dd"));
        } else {
            helper.setText(R.id.tv_school_time, "有效截止期：");

        }

        if (item.getCertificatePics() != null) {
            String[] urls = V.v(() -> item.getCertificatePics().split(","));
            //将业务类型的图片显示到列表
            ((SimpleDraweeView) helper.getView(R.id.iv_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER));
        }
    }
}
