package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;

import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

/**
 * Created by Our on 2019/1/23.
 */

public class ManufacturerAfterSaleAdapter extends BaseQuickAdapter<BaseDataEntity, BaseViewHolder> {


    public ManufacturerAfterSaleAdapter() {
        super(R.layout.item_manufacturer_after_sale);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseDataEntity item) {
        GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + item.getRemarkInfo()),helper.getView(R.id.im_manufacturer_pic));
        //helper.setText(R.id.name_T,item.getDataName());

    }
}
