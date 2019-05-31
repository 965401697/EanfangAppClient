package net.eanfang.worker.ui.activity.worksapce.oa;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;


/**
 * Created by O u r on 2018/8/7.
 */

public class OASearchAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {
    public OASearchAdapter() {
        super(R.layout.item_oa_select_person);
    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
        if (item.isChecked()) {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(false);
        }

        GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + item.getProtraivat(),helper.getView(R.id.iv_friend_header));
        if (item.getProtraivat().contains("http")) {
            GlideUtil.intoImageView(mContext,item.getProtraivat(),helper.getView(R.id.iv_friend_header));
        } else {
            GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + item.getProtraivat(),helper.getView(R.id.iv_friend_header));
        }
        helper.setText(R.id.tv_friend_name, item.getName());
        helper.addOnClickListener(R.id.cb_checked);
    }

}
