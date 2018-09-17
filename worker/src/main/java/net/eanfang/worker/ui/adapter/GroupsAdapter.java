package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.GroupsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;


/**
 * Created by O u r on 2018/4/18.
 */

public class GroupsAdapter extends BaseQuickAdapter<GroupsBean, BaseViewHolder> {

    private boolean isVisible;

    public GroupsAdapter(int layoutResId, boolean isVisible) {
        super(layoutResId);
        this.isVisible = isVisible;

    }

    @Override
    protected void convert(BaseViewHolder helper, GroupsBean item) {
        ((SimpleDraweeView) helper.getView(R.id.iv_friend_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getHeadPortrait()));
        helper.setText(R.id.tv_friend_name, item.getGroupName());

        if (isVisible) {
            helper.getView(R.id.cb_checked).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.cb_checked).setVisibility(View.GONE);
        }

        if (item.isChecked()) {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(false);
        }

        //根据position获取首字母作为目录catalog
        String catalog = item.getFirstLetter();

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (helper.getAdapterPosition() == getPositionForSection(catalog)) {
            helper.getView(R.id.tv_letter).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_letter, item.getFirstLetter().toUpperCase());
        } else {
            helper.getView(R.id.tv_letter).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.cb_checked);
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

