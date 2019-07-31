package net.eanfang.worker.ui.activity.im;

import android.net.Uri;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.entity.SysGroupUserEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/5/10.
 */

public class ShutupMberAdapter extends BaseQuickAdapter<SysGroupUserEntity, BaseViewHolder> {
    public ShutupMberAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SysGroupUserEntity item) {

        if (item.getStatus() == 0) {
            ((RadioButton) helper.getView(R.id.rb_checked)).setChecked(false);
        } else {
            ((RadioButton) helper.getView(R.id.rb_checked)).setChecked(true);
        }
        GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + item.getAccountEntity().getAvatar()),helper.getView(R.id.iv_friend_header));
        helper.setText(R.id.tv_friend_name, item.getAccountEntity().getNickName());
        helper.addOnClickListener(R.id.rb_checked);
    }
}

