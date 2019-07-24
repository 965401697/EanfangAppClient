package net.eanfang.client.ui.adapter.repair;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

/**
 * @author 管罗苍
 * @date 2018/5/4
 */

public class RepairDeviceTypeRightAdapter extends BaseQuickAdapter<BaseDataEntity, BaseViewHolder> {

    public RepairDeviceTypeRightAdapter() {
        super(R.layout.layout_repair_device_right);
    }


    @Override
    protected void convert(BaseViewHolder helper, BaseDataEntity item) {
        helper.setText(R.id.tv_rightName, item.getDataName());

        GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + item.getRemarkInfo()), helper.getView(R.id.iv_rightIcon));
        helper.setVisible(R.id.check_true_t, false);

    }


}
