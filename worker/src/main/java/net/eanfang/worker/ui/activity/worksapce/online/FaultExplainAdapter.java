package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

/**
 * Created by Our on 2019/1/24.
 */

public class FaultExplainAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public FaultExplainAdapter() {
        super(R.layout.item_fault_explain);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (TextUtils.isEmpty("")) {
            ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + ""));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(""));
        }
        helper.setText(R.id.tv_expert_name, "");
        helper.setText(R.id.tv_major, "");
        helper.setText(R.id.tv_time, "");
        helper.setText(R.id.tv_answer, "");
        helper.addOnClickListener(R.id.ll_zan);
        helper.addOnClickListener(R.id.ll_comment);

    }
}
