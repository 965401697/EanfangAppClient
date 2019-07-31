package com.eanfang.ui.adapter;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.biz.model.bean.SecurityCompanyDetailsBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;


/**
 * @author wq
 * @data 2018/10/23
 */

public class ZzyRyBAdapter extends BaseQuickAdapter<SecurityCompanyDetailsBean.GloryListBean, BaseViewHolder> {
    private boolean isDelete;

    public ZzyRyBAdapter(boolean mDelete) {
        super(R.layout.layout_item_jsa);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityCompanyDetailsBean.GloryListBean item) {
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
            helper.setText(R.id.tv_school_time, "有效截止期：" + item.getAwardTime());
        } else {
            helper.setText(R.id.tv_school_time, "有效截止期：");
        }
        if (item.getHonorPics() != null) {
            String[] urls = V.v(() -> item.getHonorPics().split(","));
            //将业务类型的图片显示到列表
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + urls[0]),helper.getView(R.id.iv_pic));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER),helper.getView(R.id.iv_pic));
        }
    }

}
