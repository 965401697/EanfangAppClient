package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.GroupsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/4/18.
 */

public class GroupsAdapter extends BaseQuickAdapter<GroupsBean, BaseViewHolder> {

    public GroupsAdapter(int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, GroupsBean item) {
        ((SimpleDraweeView) helper.getView(R.id.iv_friend_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getHeadPortrait()));
        helper.setText(R.id.tv_friend_name, item.getGroupName());
    }
}

