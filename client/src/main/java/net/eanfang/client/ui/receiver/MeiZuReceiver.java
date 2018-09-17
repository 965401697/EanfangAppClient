package net.eanfang.client.ui.receiver;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.ui.base.voice.SynthesizerPresenter;
import com.eanfang.util.StringUtils;
import com.eanfang.util.Var;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/13$  18:18$
 */
public class MeiZuReceiver extends MzPushMessageReceiver {
    @Override
    public void onRegister(Context context, String s) {
        Log.e("meizu", "魅族注册onRegister" + s);
    }

    @Override
    public void onMessage(Context context, String s) {
        Log.e("meizu", "魅族注册onMessage" + s);
        Var.get("MainActivity.initMessageCount").setVar(Var.get("MainActivity.initMessageCount").getVar() + 1);
        Var.get("ContactListFragment.messageCount").setVar(Var.get("ContactListFragment.messageCount").getVar() + 1);

        JSONObject jsonObject = JSON.parseObject(s);
        if (!StringUtils.isEmpty(jsonObject.toJSONString())) {
            System.err.println("---------------------jsonObject:" + jsonObject.toJSONString());
            if (jsonObject.containsKey("audio") && !StringUtils.isEmpty(jsonObject.getString("audio"))) {
                SynthesizerPresenter.getInstance().start(jsonObject.getString("audio"));
            }
        }
    }

    @Override
    public void onUnRegister(Context context, boolean b) {
        Log.e("meizu", "魅族注册onUnRegister" + b);
    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
        Log.e("meizu", "魅族注册onPushStatus" + pushSwitchStatus.getMessage().toString());
    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {

    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {

    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {

    }
}
