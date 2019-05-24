package com.eanfang.ui.adapter;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.biz.model.SecurityCompanyDetailsBean;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;




/**
 * @author wq
 * @data 2018/10/23
 */

public class ZzyRyAdapter extends BaseQuickAdapter<SecurityCompanyDetailsBean.AptitudeListBean, BaseViewHolder> {
    private boolean isDelete;

    public ZzyRyAdapter(boolean mDelete) {
        super(R.layout.layout_item_jsa);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityCompanyDetailsBean.AptitudeListBean item) {
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
        if (item.getEndTime() != null) {
            helper.setText(R.id.tv_school_time, "有效截止期：" + item.getEndTime());
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
