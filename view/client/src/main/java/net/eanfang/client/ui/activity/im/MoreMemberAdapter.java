package net.eanfang.client.ui.activity.im;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;


/**
 * @Date: 2018/11/21 16:08
 * @Author: O u r
 * @QQ: 373946991
 * @Description: 全部群组成员的adapter
 */
public class MoreMemberAdapter extends BaseQuickAdapter<AccountEntity, BaseViewHolder> {

    private Context context;
    public MoreMemberAdapter(Context context,int layoutResId) {
        super(layoutResId);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountEntity item) {
        GlideUtil.intoImageView(context,BuildConfig.OSS_SERVER + item.getAvatar(),helper.getView(R.id.iv_friend_header));
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
