package net.eanfang.client.ui.activity.worksapce.oa;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/8/6.
 */

public class SelectOAGroupAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {
    private Context context;
    public SelectOAGroupAdapter(Context context,int layoutResId) {
        super(layoutResId);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
        if (item.getProtraivat().contains("http")) {
            GlideUtil.intoImageView(context,item.getProtraivat(),helper.getView(R.id.iv_header));
        } else {
            GlideUtil.intoImageView(context,BuildConfig.OSS_SERVER + item.getProtraivat(),helper.getView(R.id.iv_header));
        }
        helper.setText(R.id.tv_name, item.getName());
    }
}
