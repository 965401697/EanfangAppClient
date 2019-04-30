package net.eanfang.worker.ui.activity.my.specialist;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by Our on 2019/1/15.
 */

public class BrandAdapter extends BaseQuickAdapter<BaseDataEntity, BaseViewHolder> {

    private List<BaseDataEntity> mList;

    public BrandAdapter() {
        super(R.layout.item_specialist_brand);
    }


    @Override
    protected void convert(BaseViewHolder helper, BaseDataEntity item) {

        helper.setText(R.id.tv_brand_name, item.getDataName());

        if (item.isCheck()) {
            helper.setChecked(R.id.cb_checked, true);
        } else {
            helper.setChecked(R.id.cb_checked, false);
        }
        if (mList.contains(item)) {
            helper.getView(R.id.cb_checked).setEnabled(false);
            // TODO: 2019/1/21 待优化   代码的先后执行顺序来保证
            helper.setChecked(R.id.cb_checked, true);
        } else {
            helper.getView(R.id.cb_checked).setEnabled(true);
        }

        helper.addOnClickListener(R.id.cb_checked);

    }

    public void setCheckedData(List<BaseDataEntity> list) {
        this.mList = list;
    }
}
