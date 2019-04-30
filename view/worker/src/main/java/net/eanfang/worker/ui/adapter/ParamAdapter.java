package net.eanfang.worker.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.BughandleParamEntity;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 报装公司详情的adapter
 * Created by Administrator on 2017/3/15.
 */

public class ParamAdapter extends BaseQuickAdapter<BughandleParamEntity, BaseViewHolder> {

    public ParamAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final BughandleParamEntity item) {
        helper.setText(R.id.tv_param, item.getParamName());
        if (StringUtils.isValid(item.getParamValue())) {
            helper.setText(R.id.et_param, item.getParamValue());
        }
        EditText et_param = helper.getView(R.id.et_param);
        et_param.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setParamValue(s.toString());
//                if (!StringUtils.isEmpty(s.toString())) {
//
//                } else {
//                    item.setParamName(null);
//                }
            }
        });
    }
}
