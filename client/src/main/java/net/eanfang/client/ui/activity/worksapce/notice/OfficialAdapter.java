package net.eanfang.client.ui.activity.worksapce.notice;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.NoticePushEntity;

import net.eanfang.client.R;


/**
 * Created by O u r on 2018/10/15.
 */

public class OfficialAdapter extends BaseQuickAdapter<NoticePushEntity, BaseViewHolder> {
    public OfficialAdapter() {
        super(R.layout.item_official_msg);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticePushEntity item) {
        helper.setText(R.id.tv_title, item.getNoticeTitle());
        ((SimpleDraweeView) helper.getView(R.id.iv_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getPicture()));
        helper.setText(R.id.tv_desc, item.getNoticeDescribe());
    }
}
