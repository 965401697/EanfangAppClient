package net.eanfang.client.ui.activity.worksapce.online;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;


/**
 * Created by Our on 2019/1/23.
 */

public class SystemTypeAdapter extends BaseQuickAdapter<BaseDataEntity, BaseViewHolder> {

    private int[] mPicArray = {R.mipmap.icon_sys_type_tv, R.mipmap.icon_sys_type_police, R.mipmap.icon_sys_type_guard, R.mipmap.icon_sys_type_talk
            , R.mipmap.icon_sys_type_boradcast, R.mipmap.icon_sys_type_park, R.mipmap.eas, R.mipmap.icon_sys_type_tv
    };

    public SystemTypeAdapter() {
        super(R.layout.item_system_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseDataEntity item) {
        GlideUtil.intoImageView(mContext,mPicArray[helper.getAdapterPosition()],helper.getView(R.id.im_sys_pic));
        helper.setText(R.id.tv_sys_name, item.getDataName());
    }
}
