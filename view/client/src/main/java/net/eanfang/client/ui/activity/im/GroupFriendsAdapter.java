package net.eanfang.client.ui.activity.im;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.entity.SysGroupUserEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/5/10.
 */

public class GroupFriendsAdapter extends BaseQuickAdapter<SysGroupUserEntity, BaseViewHolder> {
    private Context context;

    public GroupFriendsAdapter(Context context, int layoutResId) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SysGroupUserEntity item) {
        helper.getView(R.id.cb_checked).setVisibility(View.VISIBLE);
        helper.getView(R.id.tv_letter).setVisibility(View.GONE);

        if (item.getFlag() == 0) {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(false);
        } else {
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(true);
        }
        GlideUtil.intoImageView(context, BuildConfig.OSS_SERVER + item.getAccountEntity().getAvatar(), helper.getView(R.id.iv_friend_header));
        helper.setText(R.id.tv_friend_name, item.getAccountEntity().getNickName());
        helper.addOnClickListener(R.id.cb_checked);
    }
}
