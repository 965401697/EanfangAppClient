package net.eanfang.client.ui.adapter;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.base.kit.V;
import com.eanfang.biz.model.entity.GloryCertificateEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

import cn.qqtheme.framework.util.DateUtils;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description 安防公司详情荣誉证书adapter
 */

public class SecurityCompanyGloryAdapter extends BaseQuickAdapter<GloryCertificateEntity, BaseViewHolder> {
    private boolean isDelete;

    public SecurityCompanyGloryAdapter(boolean mDelete) {
        super(R.layout.layout_item_jsa);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, GloryCertificateEntity item) {
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
            GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + urls[0]), helper.getView(R.id.iv_pic));
        } else {
            GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER), helper.getView(R.id.iv_pic));
        }
    }
}
