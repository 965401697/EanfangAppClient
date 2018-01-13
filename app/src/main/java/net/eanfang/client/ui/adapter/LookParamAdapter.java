package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.repair.BughandleParamEntity;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;



/**
 * 报装公司详情的adapter
 * Created by Administrator on 2017/3/15.
 */

public class LookParamAdapter extends BaseQuickAdapter<BughandleParamEntity, BaseViewHolder> {

    public LookParamAdapter(int layoutResId, ArrayList data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, final BughandleParamEntity item) {
        helper.setText(R.id.tv_param,item.getParamName());
        if (StringUtils.isValid(item.getParamValue())) {
            helper.setText(R.id.et_param, item.getParamValue());
        }
    }
}
