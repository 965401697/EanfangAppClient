package net.eanfang.worker.ui.adapter.repair;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.BughandleParamEntity;

import net.eanfang.worker.R;

import java.util.List;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/6/6$  13:53$
 */
public class AddParamAdapter extends BaseQuickAdapter<BughandleParamEntity, BaseViewHolder> {

    public AddParamAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final BughandleParamEntity item) {
        helper.setText(R.id.tv_param_name, item.getParamName());
        if (StringUtils.isValid(item.getParamValue())) {
            helper.setText(R.id.tv_param_value, item.getParamValue());
        }
        helper.addOnClickListener(R.id.iv_param_delete);
    }
}
