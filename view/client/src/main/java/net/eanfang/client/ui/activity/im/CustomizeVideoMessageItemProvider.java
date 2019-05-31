package net.eanfang.client.ui.activity.im;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eanfang.BuildConfig;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

/**
 * Created by O u r on 2018/7/2.
 */
@ProviderTag(messageContent = CustomizeVideoMessage.class)
public class CustomizeVideoMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomizeVideoMessage> {

    class ViewHolder {
        RelativeLayout rlPlay;
        ImageView ivVedio;
    }

    @Override
    public void bindView(View view, int i, CustomizeVideoMessage customizeVedioMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();
        GlideUtil.intoImageView(ClientApplication.get().getApplicationContext(),Uri.parse(BuildConfig.OSS_SERVER + customizeVedioMessage.getImgUrl()),holder.ivVedio);
    }

    @Override
    public Spannable getContentSummary(CustomizeVideoMessage customizeVedioMessage) {

        return new SpannableString("小视频(快去查看吧!)");

    }

    @Override
    public void onItemClick(View view, int i, CustomizeVideoMessage customizeVedioMessage, UIMessage uiMessage) {
        Intent intent = new Intent(view.getContext(), PlayVideoActivity.class);
        Bundle bundle_takevideo = new Bundle();
        bundle_takevideo.putString("videoPath", customizeVedioMessage.getMp4Path());
        intent.putExtras(bundle_takevideo);
        view.getContext().startActivity(intent);
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_custom_vedio_message, null);
        ViewHolder holder = new ViewHolder();
        holder.rlPlay = view.findViewById(R.id.rl_play);
        holder.ivVedio = view.findViewById(R.id.iv_video);

        view.setTag(holder);
        return view;
    }
}
