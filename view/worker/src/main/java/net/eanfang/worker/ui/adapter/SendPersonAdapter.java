package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

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
            GlideUtil.intoImageView(mContext, item.getProtraivat(),helper.getView(R.id.iv_user_header));
        } else {
            GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + item.getProtraivat(),helper.getView(R.id.iv_user_header));
        }
        helper.setText(R.id.tv_name, item.getName());
    }
}
