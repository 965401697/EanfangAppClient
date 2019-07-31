package net.eanfang.client.ui.activity.im;

import android.content.Context;
import android.net.Uri;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.entity.SysGroupUserEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/5/10.
 */

public class ShutupMberAdapter extends BaseQuickAdapter<SysGroupUserEntity, BaseViewHolder> {
    private Context context;

    public ShutupMberAdapter(Context context, int layoutResId) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SysGroupUserEntity item) {

        if (item.getStatus() == 0) {
            ((RadioButton) helper.getView(R.id.rb_checked)).setChecked(false);
        } else {
            ((RadioButton) helper.getView(R.id.rb_checked)).setChecked(true);
        }
        GlideUtil.intoImageView(context, Uri.parse(BuildConfig.OSS_SERVER + item.getAccountEntity().getAvatar()), helper.getView(R.id.iv_friend_header));
        helper.setText(R.id.tv_friend_name, item.getAccountEntity().getNickName());
        helper.addOnClickListener(R.id.rb_checked);
    }
}

