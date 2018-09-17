package net.eanfang.client.ui.adapter.repair;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.client.R;

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
        SimpleDraweeView ivPic;
        ivPic = helper.getView(R.id.iv_rightIcon);
        ivPic.setImageURI(Uri.parse(com.eanfang.BuildConfig.OSS_SERVER  + item.getRemarkInfo()));
    }
}
