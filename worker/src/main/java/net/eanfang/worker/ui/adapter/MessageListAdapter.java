package net.eanfang.worker.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.model.NoticeEntity;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 消息的adapter
 * Created by Administrator on 2017/3/15.
 */

public class MessageListAdapter extends BaseQuickAdapter<NoticeEntity, BaseViewHolder> {

    public Config config;
    public GetConstDataUtils constDataUtils;

    public MessageListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        config = Config.get(EanfangApplication.get().getApplicationContext());
        constDataUtils = GetConstDataUtils.get(config);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeEntity item) {

        helper.setText(R.id.tv_titles, constDataUtils.getNoticeTypeList().get(item.getNoticeType()));
        helper.setText(R.id.tv_content, item.getContent());
        if (item.getStatus() == 0) {
            helper.setText(R.id.tv_status, "未读");
            helper.setVisible(R.id.iv_header, true);
        } else if (item.getStatus() == 1) {
            helper.setText(R.id.tv_status, "已读");
            helper.setTextColor(R.id.tv_status, Color.parseColor("#0000ff"));
            helper.setVisible(R.id.iv_header, false);
        }

    }
}
