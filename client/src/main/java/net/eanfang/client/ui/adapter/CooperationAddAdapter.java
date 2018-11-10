package net.eanfang.client.ui.adapter;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by O u r on 2018/6/12.
 */

public class CooperationAddAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<CheckBox> checkBoxList = new ArrayList<>();

    public CooperationAddAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ((CheckBox) helper.getView(R.id.cb_check)).setText(item);

        ((CheckBox) helper.getView(R.id.cb_check)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxList.add(((CheckBox) helper.getView(R.id.cb_check)));
                } else {
                    checkBoxList.remove((CheckBox) helper.getView(R.id.cb_check));
                }
            }
        });


    }

    public List<CheckBox> getCheckBoxList() {
        return checkBoxList;
    }
}
