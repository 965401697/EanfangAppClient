package net.eanfang.client.ui.adapter.repair;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.reapair.RepairPersonalInfoEntity;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/4/22
 * @description 报修个人信息
 */

public class RepairPersonalInfoAdapter extends BaseQuickAdapter<RepairPersonalInfoEntity.ListBean, BaseViewHolder> {
    public RepairPersonalInfoAdapter() {
        super(R.layout.layout_repair_personal_info);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairPersonalInfoEntity.ListBean item) {
        //姓名
        helper.setText(R.id.tv_name, item.getName());
        // 性别0女1男
        String sex = item.getGender() == 0 ? "(女士)" : "(先生)";
        helper.setText(R.id.tv_sex, sex);
        // 0不默认，1默认
        helper.setVisible(R.id.tv_default, item.getIsDefault() == 0 ? false : true);
        helper.setChecked(R.id.cb_default, item.getIsDefault() == 0 ? false : true);
        // 电话
        helper.setText(R.id.tv_phone, item.getPhone());
        // 单位
        helper.setText(R.id.tv_home_type, "[" + item.getSelectAddress() + "]");
        helper.setText(R.id.tv_home_address, item.getConmpanyName());
        // 地址
        helper.setText(R.id.tv_address, item.getAddress());
        helper.addOnClickListener(R.id.cb_default);
        helper.addOnClickListener(R.id.tv_delete);
        helper.addOnClickListener(R.id.tv_edit);
    }
}
