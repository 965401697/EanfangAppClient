package net.eanfang.client.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.MessageListBean;

import java.util.List;



/**
 * 消息的adapter
 * Created by Administrator on 2017/3/15.
 */

public class MessageListAdapter extends BaseQuickAdapter<MessageListBean.RowsBean, BaseViewHolder> {
    public MessageListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean.RowsBean item) {

        helper.setText(R.id.tv_title, item.getTitle());
        ImageView iv_header = helper.getView(R.id.iv_header);
        if (item.getContent().equals("title")) {
            helper.setTextColor(R.id.tv_title, Color.BLUE);
            helper.setVisible(R.id.tv_message, false);
            iv_header.setVisibility(View.INVISIBLE);
            return;
        } else {
            helper.setTextColor(R.id.tv_title, Color.parseColor("#393939"));
            helper.setVisible(R.id.tv_message, true);
            iv_header.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_message, item.getContent());

        switch (item.getType()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                iv_header.setImageResource(R.mipmap.weixiu);
                break;
            case 8:
            case 9:
                iv_header.setImageResource(R.mipmap.baojia);
                break;
            case 10:
                iv_header.setImageResource(R.mipmap.anzhuang);
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                iv_header.setImageResource(R.mipmap.fabao);
                break;
            case 16:
                iv_header.setImageResource(R.mipmap.denglu);
                break;
            case 17:
                iv_header.setImageResource(R.mipmap.tongguo);
                break;
            case 18:
                iv_header.setImageResource(R.mipmap.gongsi);
                break;
            case 19:
                iv_header.setImageResource(R.mipmap.weitongguo);
                break;
            case 20:
            case 21:
            case 22:
                iv_header.setImageResource(R.mipmap.hezuo);
                break;
            default:
                break;
        }

    }
}
