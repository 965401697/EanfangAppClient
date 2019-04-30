package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.BughandleParamEntity;

import net.eanfang.worker.R;

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
        helper.setText(R.id.tv_param_name, item.getParamName());
        if (StringUtils.isValid(item.getParamValue())) {
            helper.setText(R.id.tv_param_value, item.getParamValue());
        }
    }
}
