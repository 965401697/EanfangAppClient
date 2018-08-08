package net.eanfang.worker.ui.activity.worksapce.oa;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.TemplateBean;
import com.facebook.drawee.view.SimpleDraweeView;

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

        ((SimpleDraweeView) helper.getView(R.id.iv_friend_header)).setImageURI(BuildConfig.OSS_SERVER + item.getProtraivat());
        if (item.getProtraivat().contains("http")) {
            ((SimpleDraweeView) helper.getView(R.id.iv_friend_header)).setImageURI(item.getProtraivat());
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_friend_header)).setImageURI(BuildConfig.OSS_SERVER + item.getProtraivat());
        }
        helper.setText(R.id.tv_friend_name, item.getName());
        helper.addOnClickListener(R.id.cb_checked);
    }

}
