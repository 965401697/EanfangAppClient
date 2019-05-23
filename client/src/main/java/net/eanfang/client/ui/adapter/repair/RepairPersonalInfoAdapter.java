package net.eanfang.client.ui.adapter.repair;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.reapair.RepairPersonalInfoEntity;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guanluocang
 * @data 2019/4/22
 * @description 报修个人信息
 */

public class RepairPersonalInfoAdapter extends BaseQuickAdapter<RepairPersonalInfoEntity.ListBean, BaseViewHolder> {
    private List<Integer> isCheck = new ArrayList<>();

    public RepairPersonalInfoAdapter() {
        super(R.layout.layout_repair_personal_info);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairPersonalInfoEntity.ListBean item) {
        CheckBox cbDefault = helper.getView(R.id.cb_default);
        //姓名
        helper.setText(R.id.tv_name, item.getName());
        // 性别0女1男
        String sex = item.getGender() == 0 ? "(女士)" : "(先生)";
        helper.setText(R.id.tv_sex, sex);
        // 0不默认，1默认
        if (item.getIsDefault() == 0) {
            helper.setVisible(R.id.tv_default, false);
            helper.setChecked(R.id.cb_default, false);
            helper.addOnClickListener(R.id.cb_default);
            cbDefault.setEnabled(true);
        } else {
            helper.setVisible(R.id.tv_default, true);
            helper.setChecked(R.id.cb_default, true);
            cbDefault.setEnabled(false);
        }
        // 电话
        helper.setText(R.id.tv_phone, item.getPhone());
        // 单位
        if (!StringUtils.isEmpty(item.getSelectAddress())) {
            helper.setText(R.id.tv_home_type, "[" + item.getSelectAddress() + "]");
        }
        helper.setText(R.id.tv_home_address, item.getConmpanyName());
        // 地址
        helper.setText(R.id.tv_address, item.getAddress());
        helper.addOnClickListener(R.id.tv_delete);
        helper.addOnClickListener(R.id.tv_edit);
    }
}
