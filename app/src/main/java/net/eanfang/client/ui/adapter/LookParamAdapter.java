package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.WorkspaceDetailBean;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;



/**
 * 报装公司详情的adapter
 * Created by Administrator on 2017/3/15.
 */

public class LookParamAdapter extends BaseQuickAdapter<WorkspaceDetailBean.BughandledetaillistBean.BughandledetailparamBean, BaseViewHolder> {

    public LookParamAdapter(int layoutResId, ArrayList data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, final WorkspaceDetailBean.BughandledetaillistBean.BughandledetailparamBean item) {
        helper.setText(R.id.tv_param,item.getName());
        if (StringUtils.isValid(item.getValue()))
            helper.setText(R.id.et_param,item.getValue());
    }
}
