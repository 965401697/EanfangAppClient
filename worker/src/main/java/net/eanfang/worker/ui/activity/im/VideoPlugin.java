package net.eanfang.worker.ui.activity.im;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import net.eanfang.worker.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

/**
 * Created by O u r on 2018/5/16.
 */

public class VideoPlugin implements IPluginModule {

    private Conversation.ConversationType conversationType;
    private String targetId;


    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.rc_ext_plugin_image_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return "视频文件";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {

        this.conversationType = rongExtension.getConversationType();
        this.targetId = rongExtension.getTargetId();
        Intent intent = new Intent(fragment.getActivity(), VideoSelectedActivity.class);
        intent.putExtra("targetId", targetId);
        intent.putExtra("conversationType", conversationType);
        rongExtension.startActivityForPluginResult(intent, 199, this);
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {
        Log.e("zzw", "i=" + i + "i1=" + i1);
    }


}
