package net.eanfang.client.ui.activity.im;

import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.VideoBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;


/**
 * Created by O u r on 2018/5/16.
 */

public class VideoItemAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {
    public VideoItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {

        GlideUtil.intoImageView(mContext,Uri.parse("file://" + item.getPath()),helper.getView(R.id.iv_video));

        if (item.getFlag() == 0) {
            helper.getView(R.id.rl_obscuration).setVisibility(View.INVISIBLE);
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(false);
        } else {
            helper.getView(R.id.rl_obscuration).setVisibility(View.VISIBLE);
            ((CheckBox) helper.getView(R.id.cb_checked)).setChecked(true);
        }
        helper.addOnClickListener(R.id.cb_checked);
    }
}
