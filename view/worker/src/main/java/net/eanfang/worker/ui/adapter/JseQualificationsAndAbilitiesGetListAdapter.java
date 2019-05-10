package net.eanfang.worker.ui.adapter;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.JsCapabilityTagListBean;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description 资质证书adapter
 */

public class JseQualificationsAndAbilitiesGetListAdapter extends BaseQuickAdapter<JsCapabilityTagListBean.ListBean, BaseViewHolder> {
    private boolean isDelete;

    public JseQualificationsAndAbilitiesGetListAdapter(boolean mDelete) {
        super(R.layout.layout_item_jse_get_list);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, JsCapabilityTagListBean.ListBean item) {
        CheckBox check = helper.getView(R.id.cb_check);
        check.setText(item.getDataName() + "");
        check.setChecked(item.isSelected());
        check.setOnCheckedChangeListener((compoundButton, b) -> item.setSelected(b));
    }
}
