package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

import java.util.List;

/**
 * @author guanluocang
 * @data 2018/12/12
 * @description 子公司Adapter
 */


public class ConstactsAdapter extends BaseQuickAdapter<OrgEntity, BaseViewHolder> {
    public ConstactsAdapter(List data) {
        super(R.layout.item_contact_group_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrgEntity item) {
        if (item.getOrgName() != null) {
            helper.setText(R.id.tv_company_name, item.getOrgName());
            GlideUtil.intoImageView(mContext,Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + item.getOrgUnitEntity().getLogoPic()),helper.getView(R.id.iv_company_logo));
        } else {
            helper.setVisible(R.id.rel_company, false);
        }

    }
}
