package net.eanfang.worker.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.V;
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
    private Context context;

    public JsaQualificationsAndAbilitiesAdapter(Context context, boolean mDelete) {
        super(R.layout.layout_item_jsa);
        this.context = context;
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
            intoImage(BuildConfig.OSS_SERVER + urls[0], helper.getView(R.id.iv_pic));
        } else {
            intoImage(BuildConfig.OSS_SERVER, helper.getView(R.id.iv_pic));
        }
    }

    private void intoImage(String path, ImageView imageView) {
        GlideUtil.intoImageView(context, Uri.parse(path), imageView);
    }
}
