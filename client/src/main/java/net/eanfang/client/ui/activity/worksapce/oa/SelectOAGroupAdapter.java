package net.eanfang.client.ui.activity.worksapce.oa;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Constant;
import com.eanfang.model.TemplateBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/8/6.
 */

public class SelectOAGroupAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {
    public SelectOAGroupAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
        if (item.getProtraivat().contains("http")) {
            ((SimpleDraweeView) helper.getView(R.id.iv_header)).setImageURI(item.getProtraivat());
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_header)).setImageURI(BuildConfig.OSS_SERVER + item.getProtraivat());
        }
        helper.setText(R.id.tv_name, item.getName());
    }
}
