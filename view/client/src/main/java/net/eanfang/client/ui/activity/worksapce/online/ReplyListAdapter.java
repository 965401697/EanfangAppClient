package net.eanfang.client.ui.activity.worksapce.online;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;


/**
 * Created by on 2019/2/25.
 */

public class ReplyListAdapter extends BaseQuickAdapter<MyReplyListBean.ReplyListBean, BaseViewHolder> {

    public ReplyListAdapter() {
        super(R.layout.item_user_appraise);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyReplyListBean.ReplyListBean item) {
        GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER +item.getReplyUser().getAvatar()),helper.getView(R.id.iv_user_header));
        helper.setText(R.id.tv_user_name, item.getReplyUser().getNickName());
        helper.setText(R.id.tv_user_appraise, item.getReplyContent());
        helper.setVisible(R.id.tv_appraise_status,false);
    }
}
