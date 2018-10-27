package net.eanfang.worker.ui.activity.my.certification;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by O u r on 2018/10/25.
 */

public class SkillTypeAdapter extends BaseQuickAdapter<BaseDataEntity, BaseViewHolder> {

    private List<CheckBox> checkBoxList = new ArrayList<>();

    public SkillTypeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseDataEntity item) {
        ((CheckBox) helper.getView(R.id.cb_check)).setText(item.getDataName());

        ((CheckBox) helper.getView(R.id.cb_check)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxList.add(((CheckBox) helper.getView(R.id.cb_check)));
                    item.setCheck(true);
                } else {
                    item.setCheck(false);
                    checkBoxList.remove((CheckBox) helper.getView(R.id.cb_check));
                }
            }
        });


    }

    public List<CheckBox> getCheckBoxList() {
        return checkBoxList;
    }
}

