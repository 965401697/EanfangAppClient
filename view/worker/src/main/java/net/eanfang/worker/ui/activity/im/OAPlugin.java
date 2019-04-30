package net.eanfang.worker.ui.activity.im;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Log;

import com.eanfang.util.PermKit;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

/**
 * Created by O u r on 2018/10/25.
 */

public class OAPlugin implements IPluginModule {

    private Conversation.ConversationType conversationType;
    private String targetId;


    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.rc_ext_plugin_oa_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return "OA办公";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {

        if (PermKit.get().getWorkReportListPrem()) {
            this.conversationType = rongExtension.getConversationType();
            this.targetId = rongExtension.getTargetId();
            if (conversationType.getName().toUpperCase().equals(Conversation.ConversationType.GROUP.getName().toUpperCase())) {
                Intent intent = new Intent(fragment.getActivity(), OAListActivity.class);
                intent.putExtra("targetId", targetId);
                intent.putExtra("conversationType", conversationType.getName());
                rongExtension.startActivityForPluginResult(intent, 198, this);
            } else {
                ToastUtil.get().showToast(fragment.getContext(), "请从群聊点击进去");
            }
        }
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {
        Log.e("zzw", "i=" + i + "i1=" + i1);
    }
}
