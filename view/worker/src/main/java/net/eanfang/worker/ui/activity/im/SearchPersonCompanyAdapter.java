package net.eanfang.worker.ui.activity.im;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.TemplateBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;


/**
 * @Date: 2018/12/21 17:42
 * @Author: O u r
 * @QQ: 373946991
 * @Description: desc...
 */
public class SearchPersonCompanyAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {

    public SearchPersonCompanyAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
        ((SimpleDraweeView) helper.getView(R.id.iv_user_header)).setImageURI(BuildConfig.OSS_SERVER + item.getProtraivat());
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_section, item.getOrgName());

        if (item.isChecked()) {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(false);
        }
        helper.addOnClickListener(R.id.cb_check);
    }
}
