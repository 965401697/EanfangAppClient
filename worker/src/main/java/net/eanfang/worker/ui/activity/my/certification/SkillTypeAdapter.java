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


    private List<Integer> scheckedId = new ArrayList<>();
    private List<Integer> unSCheckedId = new ArrayList<>();

    private List<Integer> bcheckedId = new ArrayList<>();
    private List<Integer> unbCheckedId = new ArrayList<>();

    private int mFlag;

    public SkillTypeAdapter(int layoutResId) {
        super(layoutResId);
    }

    public SkillTypeAdapter(int layoutResId, int flag) {
        super(layoutResId);
        this.mFlag = flag;
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseDataEntity item) {
        ((CheckBox) helper.getView(R.id.cb_check)).setText(item.getDataName());

        ((CheckBox) helper.getView(R.id.cb_check)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item.setCheck(true);
                    if (item.getDataType() == 1) {
                        scheckedId.add(item.getDataId());
                    } else {
                        bcheckedId.add(item.getDataId());
                    }
                } else {
                    item.setCheck(false);
                    if (item.getDataType() == 1) {
                        unSCheckedId.add(item.getDataId());
                    } else {
                        unbCheckedId.add(item.getDataId());
                    }
                }
            }
        });

        if (item.isCheck()) {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(item.isCheck());
        } else {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(item.isCheck());
        }

        if (mFlag != 0) {
            ((CheckBox) helper.getView(R.id.cb_check)).setClickable(false);
        } else {
            ((CheckBox) helper.getView(R.id.cb_check)).setClickable(true);
        }
    }

    public List<Integer> getScheckedId() {
        return scheckedId;
    }

    public List<Integer> getUnSCheckedId() {
        return unSCheckedId;
    }

    public List<Integer> getBcheckedId() {
        return bcheckedId;
    }

    public List<Integer> getUnbCheckedId() {
        return unbCheckedId;
    }
}

