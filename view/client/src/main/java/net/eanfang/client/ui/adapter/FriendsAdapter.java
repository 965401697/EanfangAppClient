package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.FriendListBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;


/**
 * Created by O u r on 2018/4/12.
 */

public class FriendsAdapter extends BaseQuickAdapter<FriendListBean, BaseViewHolder> {
    private int flag;//多选框的隐藏和显示

    public FriendsAdapter(int layoutResId, int flag) {
        super(layoutResId);
        this.flag = flag;
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendListBean item) {

        if (flag == 0) {
            helper.getView(R.id.cb_checked).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.cb_checked).setVisibility(View.VISIBLE);
        }

        if (item.getFlag() == 0) {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(false);
        } else {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(true);
        }

        GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + item.getAvatar(),helper.getView(R.id.iv_friend_header));

        //根据position获取首字母作为目录catalog
        String catalog = item.getFirstLetter();

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (helper.getAdapterPosition() == getPositionForSection(catalog)) {
            helper.getView(R.id.tv_letter).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_letter, item.getFirstLetter().toUpperCase());
        } else {
            helper.getView(R.id.tv_letter).setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_friend_name, item.getNickName());
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
