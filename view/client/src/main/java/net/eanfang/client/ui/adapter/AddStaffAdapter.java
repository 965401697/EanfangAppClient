package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.biz.model.FriendListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/5/31.
 */

public class AddStaffAdapter extends BaseQuickAdapter<FriendListBean, BaseViewHolder> {
    public AddStaffAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendListBean item) {
        ((SimpleDraweeView) helper.getView(R.id.iv_user_header)).setImageURI(BuildConfig.OSS_SERVER + item.getAvatar());
        helper.setText(R.id.tv_name_phone, item.getNickName() + "(" + item.getMobile() + ")");
        helper.setText(R.id.tv_address, Config.get().getAddressByCode(item.getAreaCode()) + item.getAddress());
    }
}
