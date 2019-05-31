package net.eanfang.client.ui.activity.im;

import android.net.Uri;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;


/**
 * Created by O u r on 2018/11/15.
 */

public class OrganizationPersonAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {


    public OrganizationPersonAdapter(int layoutResId) {
        super(R.layout.item_two_level);

    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
        helper.setText(R.id.tv_name, item.getName());
        if (item.getProtraivat().startsWith("http:")) {
            GlideUtil.intoImageView(mContext,item.getProtraivat(),helper.getView(R.id.iv_user_header));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + item.getProtraivat()),helper.getView(R.id.iv_user_header));
        }
        if (item.isChecked()) {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(false);
        }

    }
}
