package net.eanfang.worker.ui.adapter.repair;

import android.net.Uri;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.BaseDataEntity;


import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by admin on 2018/5/4.
 */

public class RepairDeviceTypeRightAdapter extends BaseQuickAdapter<BaseDataEntity, BaseViewHolder> {
    public RepairDeviceTypeRightAdapter(int layoutResId, List<BaseDataEntity> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, BaseDataEntity item) {
        helper.setText(R.id.tv_rightName, item.getDataName());
        ImageView ivPic;
        ivPic = helper.getView(R.id.iv_rightIcon);
        ivPic.setImageURI(Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + item.getRemarkInfo()));
        helper.setVisible(R.id.check_true_t,false);
    }
}
