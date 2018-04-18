package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.GroupsBean;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/4/18.
 */

public class GroupsAdapter extends BaseQuickAdapter<GroupsBean, BaseViewHolder> {

    public GroupsAdapter(int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, GroupsBean item) {
//        ((ImageView) helper.getView(R.id.iv_friend_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getAvatar()));
        helper.setText(R.id.tv_friend_name, item.getGroupName());
    }
}

