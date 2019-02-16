package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.yaf.sys.entity.OrgUnitEntity;

import net.eanfang.worker.R;


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

        if (item.getIsclaim() == 0) {
            //可以认领
            ((View) helper.getView(R.id.tv_claim)).setBackground(((View) helper.getView(R.id.tv_claim)).getResources().getDrawable(R.drawable.bg_ll_btn));
        } else {
            ((View) helper.getView(R.id.tv_claim)).setBackground(((View) helper.getView(R.id.tv_claim)).getResources().getDrawable(R.drawable.bg_ll_gray_btn));
        }

        helper.addOnClickListener(R.id.tv_claim);
    }
}
