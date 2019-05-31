package net.eanfang.client.ui.activity.im;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;


/**
 * Created by O u r on 2018/10/30.
 */

public class CompanyListAdapter extends BaseQuickAdapter<OrgEntity, BaseViewHolder> {
    public CompanyListAdapter() {
        super(R.layout.item_all_company_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrgEntity item) {
        if (item.getOrgUnitEntity() != null && !TextUtils.isEmpty(item.getOrgUnitEntity().getLogoPic())) {
            GlideUtil.intoImageView(mContext,com.eanfang.BuildConfig.OSS_SERVER + item.getOrgUnitEntity().getLogoPic(), helper.getView(R.id.iv_company));
        } else {
            GlideUtil.intoImageView(mContext,R.mipmap.ic_nodata, helper.getView(R.id.iv_company));
        }
        if (item.getOrgType() == 1) {
            helper.setVisible(R.id.iv_filiale_company,true);
        } else {
            helper.setVisible(R.id.iv_filiale_company,false);
        }

        helper.setText(R.id.tv_company_name, item.getOrgName());
    }
}
