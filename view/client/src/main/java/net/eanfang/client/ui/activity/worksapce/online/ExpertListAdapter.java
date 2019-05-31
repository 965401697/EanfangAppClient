package net.eanfang.client.ui.activity.worksapce.online;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.ExpertListBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;


/**
 * Created by Our on 2019/1/23.
 */

public class ExpertListAdapter extends BaseQuickAdapter<ExpertListBean.ListBean, BaseViewHolder> {

    public ExpertListAdapter() {
        super(R.layout.item_expert_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpertListBean.ListBean item) {
        GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER +item.getAvatarPhoto()),helper.getView(R.id.iv_expert_header));
        helper.setText(R.id.tv_expert, item.getExpertName());
        helper.setText(R.id.tv_good, "好评率:  "+item.getFavorableRate()*100+"%");
        if (!TextUtils.isEmpty(item.getSystemType())){
            helper.setText(R.id.tv_major, "擅长专业:  "+item.getSystemType());
        }else {
            helper.setText(R.id.tv_major, "无");
        }

        if (item.getPrice()>0) {
            helper.setText(R.id.tv_money, item.getPrice()+"元一次");
        }else{
            helper.setText(R.id.tv_money, "免费");
        }

        helper.addOnClickListener(R.id.tv_ask);
    }

}
