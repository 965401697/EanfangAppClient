package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.JsCapabilityTagBean;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description 资质证书adapter
 */

public class JseQualificationsAndAbilitiesAdapter extends BaseQuickAdapter<JsCapabilityTagBean.ListBean, BaseViewHolder> {
    private boolean isDelete;

    public JseQualificationsAndAbilitiesAdapter(boolean mDelete) {
        super(R.layout.layout_item_jse);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, JsCapabilityTagBean.ListBean item) {
        helper.setText(R.id.a_tv, item.getDataName()+"");

    }
}
