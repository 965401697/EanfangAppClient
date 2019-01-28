package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

/**
 * Created by Our on 2019/1/24.
 */

public class UserAppraiseAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public UserAppraiseAdapter() {
        super(R.layout.item_user_appraise);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ((SimpleDraweeView) helper.getView(R.id.iv_user_header)).setImageURI(Uri.parse("http://"));
        helper.setText(R.id.tv_user_name, "子六");
        helper.setText(R.id.tv_user_appraise, "阿朵发送到发生的发生的发生发顺丰");
        helper.setText(R.id.tv_appraise_status, "非常满足");
        helper.setVisible(R.id.tv_appraise_status, true);
    }
}
