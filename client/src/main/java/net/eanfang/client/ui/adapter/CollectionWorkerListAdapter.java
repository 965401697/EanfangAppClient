package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.CollectionWorkerListBean;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;

import java.util.List;


/**
 * 我的-收藏的技师的adapter
 * Created by Administrator on 2017/3/15.
 */

public class CollectionWorkerListAdapter extends BaseQuickAdapter<CollectionWorkerListBean.ListBean, BaseViewHolder> {
    public CollectionWorkerListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionWorkerListBean.ListBean item) {
        SimpleDraweeView iv_header = helper.getView(R.id.iv_header);
        if (!StringUtils.isEmpty(item.getAssigneeUserEntity().getAccountEntity().getAvatar())) {
            iv_header.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getAssigneeUserEntity().getAccountEntity().getAvatar()));
        }
        helper.setText(R.id.tv_name, item.getAssigneeUserEntity().getAccountEntity().getRealName());
        helper.setText(R.id.tv_time, item.getCreateTime());
        if (item.getWorkerEntity() != null) {
            if (item.getWorkerEntity().getPublicPraise() != 0) {
                helper.setText(R.id.tv_koubei, item.getWorkerEntity().getPublicPraise() / 100 + "分");
            }
            if (item.getWorkerEntity().getGoodRate() != 0) {
                helper.setText(R.id.tv_haopinglv, item.getWorkerEntity().getGoodRate() + "%");
            }
        }
    }
}
