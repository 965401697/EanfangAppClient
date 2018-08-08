package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.TemplateBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/8/1.
 */

public class SendPersonAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {
    public SendPersonAdapter() {
        super(R.layout.item_send_person);
    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
        if (item.getProtraivat().contains("http")) {
            ((SimpleDraweeView) helper.getView(R.id.iv_user_header)).setImageURI(item.getProtraivat());
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_user_header)).setImageURI(BuildConfig.OSS_SERVER + item.getProtraivat());
        }

        helper.setText(R.id.tv_name, item.getName());
    }
}
