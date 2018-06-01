package net.eanfang.client.ui.adapter;

import android.net.Uri;
import android.view.View;

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

        //根据position获取首字母作为目录catalog
        String catalog = item.getFirstLetter();

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (helper.getAdapterPosition() == getPositionForSection(catalog)) {
            helper.getView(R.id.tv_letter).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_letter, item.getFirstLetter().toUpperCase());
        } else {
            helper.getView(R.id.tv_letter).setVisibility(View.GONE);
        }
    }

    /**
     * 获取catalog首次出现位置
     */
    public int getPositionForSection(String catalog) {
        for (int i = 0; i < getData().size(); i++) {
            String sortStr = getData().get(i).getFirstLetter();
            if (sortStr != null) {
                if (catalog.equalsIgnoreCase(sortStr)) {
                    return i;
                }
            }
        }
        return -1;
    }
}

