package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.CollectionCompanyListBean;
import net.eanfang.client.util.StringUtils;

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
        SimpleDraweeView iv_header = helper.getView(R.id.iv_header);
        if (!StringUtils.isEmpty(item.getLogopic())) {
            iv_header.setImageURI(Uri.parse(item.getLogopic()));
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
