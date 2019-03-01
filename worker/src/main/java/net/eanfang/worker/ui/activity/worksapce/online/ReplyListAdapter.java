package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

/**
 * Created by 匹诺曹 on 2019/2/25.
 */

public class ReplyListAdapter extends BaseQuickAdapter<MyReplyListBean, BaseViewHolder> {

    public ReplyListAdapter() {
        super(R.layout.item_user_appraise);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyReplyListBean item) {
        ((SimpleDraweeView) helper.getView(R.id.iv_user_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER +item.getReplyUser().getAvatar()));
        helper.setText(R.id.tv_user_name, item.getReplyUser().getNickName());
        helper.setText(R.id.tv_user_appraise, item.getReplyContent());
        helper.setVisible(R.id.tv_appraise_status,false);
    }
}
