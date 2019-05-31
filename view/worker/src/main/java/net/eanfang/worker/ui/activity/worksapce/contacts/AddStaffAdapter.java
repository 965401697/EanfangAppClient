package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.FriendListBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/8/8.
 */

public class AddStaffAdapter extends BaseQuickAdapter<FriendListBean, BaseViewHolder> {
    private int mFlag;

    public AddStaffAdapter(int layoutResId, int flag) {
        super(layoutResId);
        this.mFlag = flag;
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendListBean item) {
        if (!TextUtils.isEmpty(item.getAvatar()) && item.getAvatar().length() > 0) {
            GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + item.getAvatar(),helper.getView(R.id.iv_friend_header));
            helper.setVisible(R.id.iv_friend_title, false);
            helper.getView(R.id.iv_friend_header).setVisibility(View.VISIBLE);
        } else {
            helper.setVisible(R.id.iv_friend_title, true);
            helper.setText(R.id.iv_friend_title, item.getRealName().length() > 2 ?
                    item.getRealName().substring(item.getRealName().length() - 2) :
                    item.getRealName());
            helper.getView(R.id.iv_friend_header).setVisibility(View.INVISIBLE);
        }
        if (mFlag == 1) {
            if (TextUtils.isEmpty(item.getNickName())) {
                helper.setVisible(R.id.tv_friend_nike, false);
            } else {
                helper.setVisible(R.id.tv_friend_nike, true);
                helper.setText(R.id.tv_friend_nike, "昵称：" + item.getNickName());
            }
        } else {
            helper.setVisible(R.id.tv_friend_nike, false);
        }

        helper.setText(R.id.tv_friend_phone, item.getMobile());
        if (mFlag == 1) {
            helper.setText(R.id.tv_friend_name, item.getRealName());
        } else {
            helper.setText(R.id.tv_friend_name, item.getNickName());
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
        helper.addOnClickListener(R.id.tv_add);
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
