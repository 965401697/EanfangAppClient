package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.eanfang.biz.model.entity.OrgEntity;

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
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.iv_company_logo);
        if (item.getOrgName() != null) {
            helper.setText(R.id.tv_company_name, item.getOrgName());
            simpleDraweeView.setImageURI(Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + item.getOrgUnitEntity().getLogoPic()));
        } else {
            helper.setVisible(R.id.rel_company, false);
        }

    }
}
