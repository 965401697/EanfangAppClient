package net.eanfang.client.ui.activity.im;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.sys.entity.OrgEntity;

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
            ((SimpleDraweeView) helper.getView(R.id.iv_company)).setImageURI(com.eanfang.BuildConfig.OSS_SERVER + item.getOrgUnitEntity().getLogoPic());
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_company)).setImageURI(com.eanfang.BuildConfig.OSS_SERVER);
        }
        helper.setText(R.id.tv_company_name, item.getOrgName());
    }
}
