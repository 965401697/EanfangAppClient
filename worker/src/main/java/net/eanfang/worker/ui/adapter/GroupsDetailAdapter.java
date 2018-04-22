package net.eanfang.worker.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.GroupsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/4/18.
 */

public class GroupsDetailAdapter extends BaseQuickAdapter<FriendListBean, BaseViewHolder> {

    public GroupsDetailAdapter(int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, FriendListBean item) {
        ((SimpleDraweeView) helper.getView(R.id.iv_icon)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getAvatar()));
        if (getData().size() - 2 == helper.getAdapterPosition()) {
            ((SimpleDraweeView) helper.getView(R.id.iv_icon)).setImageResource(R.mipmap.ic_btn_deleteperson);
        } else if (getData().size() - 1 == helper.getAdapterPosition()) {
            ((SimpleDraweeView) helper.getView(R.id.iv_icon)).setImageResource(R.mipmap.ic_btn_addperson);
        } else {

            helper.setText(R.id.tv_name, item.getNickName());
        }

    }
}

