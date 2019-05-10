package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.sys.OrgEntity;
import com.eanfang.model.sys.UserEntity;

import net.eanfang.client.R;

import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/22  10:35
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class StaffAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    public StaffAdapter(List data) {
        super(R.layout.son_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        if (item instanceof UserEntity) {
            if (((UserEntity) item).getAccountEntity().getRealName() != null) {
                helper.setText(R.id.tv_son, ((UserEntity) item).getAccountEntity().getRealName());
                String first_name = ((UserEntity) item).getAccountEntity().getRealName();
                if (first_name.length() == 1) {
                    helper.setText(R.id.tv_first_name, first_name);
                } else if (first_name.length() == 2) {
                    helper.setText(R.id.tv_first_name, first_name);
                } else if (first_name.length() == 3) {
                    first_name = first_name.substring(1, 2);
                    helper.setText(R.id.tv_first_name, first_name);
                } else if (first_name.length() == 4) {
                    first_name = first_name.substring(2, 3);
                    helper.setText(R.id.tv_first_name, first_name);
                }
            }else {
                helper.setVisible(R.id.ll_person, false);
            }

        }else if (item instanceof OrgEntity){
            helper.setVisible(R.id.ll_person, false);
        }
        helper.addOnClickListener(R.id.ll_person);

    }


}
