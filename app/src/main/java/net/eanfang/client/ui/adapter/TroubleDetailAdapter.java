package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.ui.model.WorkspaceDetailBean;
import net.eanfang.client.util.StringUtils;

import java.util.List;



/**
 * Created by wen on 2017/4/23.
 */

public class TroubleDetailAdapter extends BaseQuickAdapter<WorkspaceDetailBean.BughandledetaillistBean, BaseViewHolder> {
    public TroubleDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkspaceDetailBean.BughandledetaillistBean item) {
        WorkspaceDetailBean.BughandledetaillistBean.DetailBean detailBean = item.getDetail();
        helper.setText(R.id.tv_detail_name, detailBean.getTitle());
        if (StringUtils.isEmpty(detailBean.getCheckprocess())) {
            helper.setVisible(R.id.tv_detai_status, true);
            helper.setText(R.id.tv_detai_status, "待完善");
        } else {
            helper.setVisible(R.id.tv_detai_status, false);
            helper.setVisible(R.id.tv_delete, false);
        }
        //客户端 隐藏按钮
        if (BuildConfig.APP_TYPE == 0) {
            helper.setVisible(R.id.tv_delete, false);
        }

    }
}
