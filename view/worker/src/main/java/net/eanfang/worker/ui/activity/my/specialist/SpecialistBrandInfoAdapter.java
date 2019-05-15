package net.eanfang.worker.ui.activity.my.specialist;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.BaseDataEntity;

import net.eanfang.worker.R;

/**
 * Created by Our on 2019/1/18.
 */

public class SpecialistBrandInfoAdapter extends BaseQuickAdapter<BaseDataEntity, BaseViewHolder> {
    public SpecialistBrandInfoAdapter() {
        super(R.layout.item_add_brand);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseDataEntity item) {
        helper.setText(R.id.tv_name, item.getDataName());
//        helper.getView(R.id.iv_sub).setVisibility(View.GONE);
        helper.getView(R.id.tv_name).setBackground(mContext.getResources().getDrawable(R.drawable.shape_add_brand_checked));
    }
}
