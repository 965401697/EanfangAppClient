package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.BughandleParamEntity;

import net.eanfang.client.R;

import java.util.ArrayList;

import cn.hutool.core.util.StrUtil;


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
        helper.setText(R.id.tv_param, item.getParamName());
        if (StrUtil.isNotBlank(item.getParamValue())) {
            helper.setText(R.id.et_param, item.getParamValue());
        }
    }
}
