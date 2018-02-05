package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaf.sys.entity.OrgEntity;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/22  10:35
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class ConstactsAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    public ConstactsAdapter(List data) {
        super(R.layout.item_group_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        if (item instanceof OrgEntity) {
            if (((OrgEntity) item).getOrgName()!=null){
                helper.setText(R.id.tv_company_name, ((OrgEntity) item).getOrgName());
                helper.setVisible(R.id.iv_img, false);
            }else {
                helper.setVisible(R.id.rel_company, false);
            }

        }else if (item instanceof UserEntity){
            helper.setVisible(R.id.rel_company, false);
        }
        helper.addOnClickListener(R.id.rel_company);

    }
}
