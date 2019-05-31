package net.eanfang.client.ui.activity.worksapce.online;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;


/**
 * Created by Our on 2019/1/24.
 */

public class UserAppraiseAdapter extends BaseQuickAdapter<AnswerExpertMoreDetailsBean.EvaluateListBean, BaseViewHolder> {
    public UserAppraiseAdapter() {
        super(R.layout.item_user_appraise);
    }

    @Override
    protected void convert(BaseViewHolder helper,AnswerExpertMoreDetailsBean.EvaluateListBean item) {
        GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER +item.getCreateAccount().getAvatar()),helper.getView(R.id.iv_user_header));
        helper.setText(R.id.tv_user_name, item.getCreateAccount().getNickName());
        helper.setText(R.id.tv_user_appraise, item.getDescribe());
        if (item.getFavorableRate()==1){
            helper.setText(R.id.tv_appraise_status, "非常满意");
        }else if(item.getFavorableRate()==2){
            helper.setText(R.id.tv_appraise_status, "满意");
        }else if(item.getFavorableRate()==3){
            helper.setText(R.id.tv_appraise_status, "一般");
        }else if(item.getFavorableRate()==4){
            helper.setText(R.id.tv_appraise_status, "不满意");
        }else {
            helper.setText(R.id.tv_appraise_status, "非常不满意");
        }
    }
}
