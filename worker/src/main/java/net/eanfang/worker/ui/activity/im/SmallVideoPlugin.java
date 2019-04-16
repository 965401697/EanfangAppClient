package net.eanfang.worker.ui.activity.im;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import com.eanfang.takevideo.TakeVideoActivity;

import net.eanfang.worker.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

/**
 * Created by O u r on 2018/11/16.
 */

public class SmallVideoPlugin implements IPluginModule {

    private Conversation.ConversationType conversationType;
    private String targetId;


    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.rc_ext_plugin_small_video_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return "小视频";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {

        this.conversationType = rongExtension.getConversationType();
        this.targetId = rongExtension.getTargetId();
        Intent intent = new Intent(fragment.getActivity(), TakeVideoActivity.class);

        Bundle bundle_addvideo = new Bundle();
        bundle_addvideo.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        intent.putExtras(bundle_addvideo);
        intent.putExtra("targetId", targetId);
        intent.putExtra("conversationType", conversationType);

        rongExtension.startActivityForPluginResult(intent, 199, this);
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
