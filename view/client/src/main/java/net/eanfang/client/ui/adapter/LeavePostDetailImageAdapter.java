package net.eanfang.client.ui.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :报警详情截图
 */
public class LeavePostDetailImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public LeavePostDetailImageAdapter() {
        super(R.layout.item_leave_post_detail_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideUtil.intoImageView(mContext, BuildConfig.OSS_SERVER  + item, helper.getView(R.id.img_leave_post_detail_img));
    }


}
