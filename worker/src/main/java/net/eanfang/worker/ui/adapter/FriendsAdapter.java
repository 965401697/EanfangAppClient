package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.FriendListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/4/12.
 */

public class FriendsAdapter extends BaseQuickAdapter<FriendListBean, BaseViewHolder> {
    private int flag;

    public FriendsAdapter(int layoutResId, int flag) {
        super(layoutResId);
        this.flag = flag;
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendListBean item) {

        if (flag == 0 ) {
            helper.getView(R.id.cb_checked).setVisibility(View.INVISIBLE);
        } else {
            helper.getView(R.id.cb_checked).setVisibility(View.VISIBLE);
        }

        if (item.getFlag() == 0) {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(false);
        } else {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(true);
        }

        ((SimpleDraweeView) helper.getView(R.id.iv_friend_header)).setImageURI(BuildConfig.OSS_SERVER + item.getAvatar());
        helper.setText(R.id.tv_friend_name, item.getNickName());
        helper.addOnClickListener(R.id.cb_checked);
    }
}
