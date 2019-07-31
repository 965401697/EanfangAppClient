package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.biz.model.entity.NoticePushEntity;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/10/15.
 */

public class OfficialAdapter extends BaseQuickAdapter<NoticePushEntity, BaseViewHolder> {
    public OfficialAdapter() {
        super(R.layout.item_official_msg);
    }


    @Override
    protected void convert(BaseViewHolder helper, NoticePushEntity item) {
        if (item.getStatus() == 0) {
            ((TextView) helper.getView(R.id.tv_title)).setTextColor(helper.getConvertView().getResources().getColor(R.color.color_client_neworder));
            ((TextView) helper.getView(R.id.tv_read)).setTextColor(helper.getConvertView().getResources().getColor(R.color.color_client_neworder));
        } else {
            ((TextView) helper.getView(R.id.tv_title)).setTextColor(helper.getConvertView().getResources().getColor(R.color.roll_content));
            ((TextView) helper.getView(R.id.tv_read)).setTextColor(helper.getConvertView().getResources().getColor(R.color.roll_content));
        }
        helper.setText(R.id.tv_title, item.getNoticeTitle());
        helper.setText(R.id.tv_desc, item.getNoticeDescribe());
        GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + item.getPicture()),helper.getView(R.id.iv_pic));
    }
}
