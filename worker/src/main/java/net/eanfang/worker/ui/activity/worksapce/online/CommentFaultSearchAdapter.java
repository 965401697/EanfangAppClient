package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

/**
 * Created by Our on 2019/1/26.
 */

public class CommentFaultSearchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CommentFaultSearchAdapter() {
        super(R.layout.item_comment_fault_search);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (TextUtils.isEmpty("")) {
            ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + ""));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(""));
        }
        helper.setText(R.id.tv_fault_desc, "");
        helper.setText(R.id.tv_expert_name, "");
        helper.setText(R.id.tv_desc, "好评率");

    }
}
