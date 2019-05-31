package com.eanfang.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.util.GlideUtil;

/**
 * @Date: 2018/12/21 15:53
 * @Author: O u r
 * @QQ: 373946991
 * @Description: desc...
 */
public class SearchPersonByOrgannizationAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {

    public SearchPersonByOrgannizationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
        GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + item.getProtraivat(),helper.getView(R.id.iv_user_header));
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_section, item.getOrgName());

    }
}
