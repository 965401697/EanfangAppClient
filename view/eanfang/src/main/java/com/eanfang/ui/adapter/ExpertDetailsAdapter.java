package com.eanfang.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.R;
import com.eanfang.biz.model.bean.ExpertDetailsBean;
import com.eanfang.util.GlideUtil;

/**
 * @Date: 2018/12/21 15:53
 * @Author: WQ
 * @Description: desc...
 */
public class ExpertDetailsAdapter extends BaseQuickAdapter<ExpertDetailsBean.EvaluateBean, BaseViewHolder> {

    public ExpertDetailsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpertDetailsBean.EvaluateBean item) {
        GlideUtil.intoImageView(mContext,com.eanfang.BuildConfig.OSS_SERVER +item.getCreateAccount().getAvatar(),helper.getView(R.id.gs_log_sdv));
        helper.setText(R.id.name_tv, item.getCreateAccount().getRealName()+"");
        String myString = "";
        switch (item.getFavorableRate()) {
            case 1:
                myString = "非常满意";
                break;
            case 2:
                myString = "满意";
                break;
            case 3:
                myString = "一般";
                break;
            case 4:
                myString = "不满意";
                break;
            case 5:
                myString = "非常不满意";
                break;
            default:
        }
        helper.setText(R.id.my_tv, myString);
        helper.setText(R.id.pl_tv, item.getDescribes() + "");

    }
}
