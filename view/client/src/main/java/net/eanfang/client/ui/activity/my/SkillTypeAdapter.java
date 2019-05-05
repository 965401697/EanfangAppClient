package net.eanfang.client.ui.activity.my;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.sys.BaseDataEntity;

import net.eanfang.client.R;

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


        if (item.isCheck()) {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(item.isCheck());
        } else {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(item.isCheck());
        }

        if (mFlag != 0) {
            helper.getView(R.id.cb_check).setClickable(false);
            helper.getView(R.id.cb_check).setEnabled(false);
        } else {
            helper.getView(R.id.cb_check).setClickable(true);
            helper.getView(R.id.cb_check).setEnabled(true);

            helper.getView(R.id.cb_check).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (((CheckBox) v).isChecked()) {
                        item.setCheck(true);
                        if (item.getDataType() == 1) {
                            if (unSCheckedId.contains(item.getDataId())) {
                                unSCheckedId.remove(item.getDataId());
                            } else {
                                scheckedId.add(item.getDataId());
                            }
                        } else {
                            if (unbCheckedId.contains(item.getDataId())) {
                                unbCheckedId.remove(item.getDataId());
                            } else {

                                bcheckedId.add(item.getDataId());
                            }
                        }
                    } else {
                        item.setCheck(false);
                        if (item.getDataType() == 1) {
                            if (scheckedId.contains(item.getDataId())) {
                                scheckedId.remove(item.getDataId());
                            } else {
                                unSCheckedId.add(item.getDataId());
                            }
                        } else {
                            if (bcheckedId.contains(item.getDataId())) {
                                bcheckedId.remove(item.getDataId());
                            } else {
                                unbCheckedId.add(item.getDataId());
                            }

                        }
                    }
                }
            });
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

