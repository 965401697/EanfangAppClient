package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.V;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.HonorCertificateEntity;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description 资质证书adapter
 */

public class JsbQualificationsAndAbilitiesAdapter extends BaseQuickAdapter<HonorCertificateEntity, BaseViewHolder> {
    private boolean isDelete;

    public JsbQualificationsAndAbilitiesAdapter(boolean mDelete) {
        super(R.layout.layout_item_jsb);
        this.isDelete = mDelete;
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
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + urls[0]),helper.getView(R.id.iv_pic));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER),helper.getView(R.id.iv_pic));
        }
    }
}
