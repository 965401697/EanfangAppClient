package net.eanfang.worker.ui.activity.my.certification;

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
 * Created by O u r on 2018/10/25.
 */

public class QualificationAdapter extends BaseQuickAdapter<QualificationCertificateEntity, BaseViewHolder> {
    private int mFlag;

    public QualificationAdapter() {
        super(R.layout.item_certificate_list);
    }

    public QualificationAdapter(int flag) {
        super(R.layout.item_certificate_list);
        this.mFlag = flag;

    }

    @Override
    protected void convert(BaseViewHolder helper, QualificationCertificateEntity item) {
        if (!TextUtils.isEmpty(item.getCertificateName())) {
            helper.setText(R.id.tv_school_name, "证书名称：" + item.getCertificateName());
        } else {
            helper.setText(R.id.tv_school_name, "证书名称：");

        }
        if (!TextUtils.isEmpty(item.getCertificateLevel())) {
            helper.setText(R.id.tv_school_major, "资质等级：" + item.getCertificateLevel());
        } else {
            helper.setText(R.id.tv_school_major, "资质等级：");
        }
        if (item.getBeginTime() != null && item.getEndTime() != null) {
            helper.setText(R.id.tv_school_time, "起止时间：" + DateUtils.formatDate(item.getBeginTime(), "yyyy-MM-dd") + " 至 " + DateUtils.formatDate(item.getEndTime(), "yyyy-MM-dd"));
        } else {
            helper.setText(R.id.tv_school_time, "起止时间：" + " 至");

        }

        if (item.getCertificatePics() != null) {
            String[] urls = V.v(() -> item.getCertificatePics().split(","));
            //将业务类型的图片显示到列表
            ((SimpleDraweeView) helper.getView(R.id.iv_certificate_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_certificate_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER));
        }

        helper.addOnClickListener(R.id.tv_delete);

        if (mFlag != 0) {
            helper.setVisible(R.id.tv_delete, false);
        } else {
            helper.setVisible(R.id.tv_delete, true);
        }
    }
}

