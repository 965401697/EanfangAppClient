package net.eanfang.worker.ui.adapter;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.biz.model.bean.CooperationSearchBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/6/13.
 */

public class CooperationSearchAdapter extends BaseQuickAdapter<CooperationSearchBean.ListBean, BaseViewHolder> {

    public CooperationSearchAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CooperationSearchBean.ListBean item) {

        GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + item.getCompanyEntity().getLogoPic(),helper.getView(R.id.iv_company_logo));
        helper.setText(R.id.tv_company_name, item.getCompanyEntity().getName());
        helper.setText(R.id.tv_company_user, Config.get().getAddressByCode(item.getAccountEntity().getRealName() + " " + item.getAccountEntity().getMobile()));
        if (item.isChecked()) {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(false);
        }
        helper.addOnClickListener(R.id.cb_checked);
    }
}
