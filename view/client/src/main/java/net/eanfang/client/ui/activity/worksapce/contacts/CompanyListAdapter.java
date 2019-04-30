package net.eanfang.client.ui.activity.worksapce.contacts;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.yaf.sys.entity.OrgUnitEntity;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/11/12.
 */

public class CompanyListAdapter extends BaseQuickAdapter<OrgUnitEntity, BaseViewHolder> {
    public CompanyListAdapter() {
        super(R.layout.item_create_team);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrgUnitEntity item) {
        helper.setText(R.id.tv_company_name, item.getName());
        if (!TextUtils.isEmpty(item.getTelPhone())) {
            helper.setText(R.id.tv_tel, "电话：" + item.getTelPhone());
        } else {
            helper.setText(R.id.tv_tel, "电话：");
        }
        if (!TextUtils.isEmpty(item.getLicenseCode())) {
            helper.setText(R.id.tv_code, "组织机构代码：" + item.getLicenseCode());
        } else {
            helper.setText(R.id.tv_code, "组织机构代码：");
        }
        if (!TextUtils.isEmpty(item.getAreaCode())) {
            helper.setText(R.id.tv_address, "地址：" + Config.get().getAddressByCode(item.getAreaCode()) + item.getOfficeAddress());
        } else {
            helper.setText(R.id.tv_address, "地址：");
        }
        helper.addOnClickListener(R.id.tv_claim);
    }
}
