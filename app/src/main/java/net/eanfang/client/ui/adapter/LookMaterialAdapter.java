package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.ui.model.repair.BughandleUseDeviceEntity;

import java.util.List;


/**
 * Created by wen on 2017/4/23.
 */

public class LookMaterialAdapter extends BaseQuickAdapter<BughandleUseDeviceEntity, BaseViewHolder> {
    public LookMaterialAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BughandleUseDeviceEntity item) {
        helper.setText(R.id.tv_detail_name, helper.getAdapterPosition() + 1 + "." + item.getModelCode() + "-" + item.getDeviceName());
        helper.addOnClickListener(R.id.tv_delete);

        //客户端隐藏删除按钮
        if (BuildConfig.APP_TYPE == 0) {
            helper.setVisible(R.id.tv_delete, false);
        }

    }
}
