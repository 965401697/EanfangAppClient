package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.FriendListBean;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/4/12.
 */

public class FriendsAdapter extends BaseQuickAdapter<FriendListBean, BaseViewHolder> {

    public FriendsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendListBean item) {
        ((ImageView)helper.getView(R.id.iv_friend_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getAvatar()));
        helper.setText(R.id.tv_friend_name,item.getNickName());
    }
}
