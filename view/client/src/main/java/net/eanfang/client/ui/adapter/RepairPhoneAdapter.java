package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.RepairPhoneCompanyBean;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;

import net.eanfang.client.R;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/8/16
 * @description
 */
public class RepairPhoneAdapter extends BaseQuickAdapter<RepairPhoneCompanyBean, BaseViewHolder> {

    public RepairPhoneAdapter() {
        super(R.layout.layout_repair_phone_company);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairPhoneCompanyBean item) {
        String mBizName = "";
        helper.setText(R.id.tv_companyName, item.getOwnerOrgUnit().getName());
        List<String> mBizList = item.getBizList();
        for (String string : mBizList) {
            mBizName += "  " + Config.get().getBaseNameByCode(string, Constant.SYS_TYPE);
        }
        helper.setText(R.id.tv_business, mBizName);
        helper.addOnClickListener(R.id.tv_call);
    }
}
