package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.CollectionCompanyListBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;


import net.eanfang.client.R;

import java.util.List;


/**
 * 我的-收藏的公司的adapter
 * Created by Administrator on 2017/3/15.
 */

public class CollectionCompanyListAdapter extends BaseQuickAdapter<CollectionCompanyListBean.AllBean, BaseViewHolder> {
    public CollectionCompanyListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionCompanyListBean.AllBean item) {
        if (!StringUtils.isEmpty(item.getLogopic())) {
            GlideUtil.intoImageView(mContext,Uri.parse(item.getLogopic()),helper.getView(R.id.iv_header));
        }
        helper.setText(R.id.tv_name, item.getCompanyname());
        if (!StringUtils.isEmpty(item.getPraise())) {
            helper.setText(R.id.tv_koubei, item.getPraise() + "分");
        }
        if (!StringUtils.isEmpty(item.getGoodpercent())) {
            helper.setText(R.id.tv_haopinglv, Double.parseDouble(item.getGoodpercent()) * 100 + "%");
        }
    }
}
