package net.eanfang.client.ui.activity.im;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.eanfang.client.R;

import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.SingleChoiceAdapter;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * Created by O u r on 2018/7/2.
 */
@ProviderTag(messageContent = CustomizeMessage.class)
public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomizeMessage> {

    class ViewHolder {
        TextView message;
    }

    @Override
    public void bindView(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
        } else {
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
        }
        holder.message.setText("看看行不行");
//        AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
    }

    @Override
    public Spannable getContentSummary(CustomizeMessage customizeMessage) {
        return new SpannableString("这是一条自定义消息CustomizeMessage");
    }

    @Override
    public void onItemClick(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_staff, null);
        ViewHolder holder = new ViewHolder();
        holder.message = (TextView) view.findViewById(R.id.tv_address);
        view.setTag(holder);
        return view;
    }
}
