package net.eanfang.worker.ui.receiver;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.camera.util.LogUtil;
import com.eanfang.ui.base.voice.SynthesizerPresenter;
import com.eanfang.util.StringUtils;
import com.eanfang.util.Var;
import com.huawei.hms.support.api.push.PushReceiver;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 描述：华为推送receiver
 *
 * @author Guanluocang
 * @date on 2018/7/5$  17:39$
 */
public class HuaWeiReceiver extends PushReceiver {

    public static final String LogTag = "HuaWeiReceiver";

    @Override
    public void onEvent(Context context, Event arg1, Bundle arg2) {
        super.onEvent(context, arg1, arg2);
        LogUtil.e(LogTag, "onEvent" + arg1 + " Bundle " + arg2);
    }

    @Override
    public boolean onPushMsg(Context context, byte[] arg1, Bundle arg2) {
        LogUtil.e(LogTag, "BundleonPushMsg" + new String(arg1) + " Bundle " + arg2);
        return super.onPushMsg(context, arg1, arg2);
    }

    @Override
    public void onPushMsg(Context context, byte[] arg1, String arg2) {
        LogUtil.e(LogTag, "StringonPushMsg" + new String(arg1) + " arg2 " + arg2);
        super.onPushMsg(context, arg1, arg2);
        Var.get("MainActivity.initMessageCount").setVar(Var.get("MainActivity.initMessageCount").getVar() + 1);
        Var.get("ContactListFragment.messageCount").setVar(Var.get("ContactListFragment.messageCount").getVar() + 1);

        JSONObject jsonObject = JSON.parseObject(arg2);
        if (!StringUtils.isEmpty(jsonObject.toJSONString())) {
            System.err.println("---------------------jsonObject:" + jsonObject.toJSONString());
            if (jsonObject.containsKey("audio") && !StringUtils.isEmpty(jsonObject.getString("audio"))) {
                SynthesizerPresenter.getInstance().start(jsonObject.getString("audio"));
            }
        }
    }

    @Override
    public void onPushState(Context context, boolean arg1) {

        LogUtil.e(LogTag, "onPushState" + arg1);
        super.onPushState(context, arg1);
    }

    @Override
    public void onToken(Context context, String arg1, Bundle arg2) {
        super.onToken(context, arg1, arg2);
        LogUtil.e(LogTag, " BundleonToken" + arg1 + "bundke " + arg2);
    }

    @Override
    public void onToken(Context context, String arg1) {
        super.onToken(context, arg1);
        LogUtil.e(LogTag, " StringonToken" + arg1);
    }


    private void writeToFile(String conrent) {
        String SDPATH = Environment.getExternalStorageDirectory() + "/huawei.txt";
        try {
            FileWriter fileWriter = new FileWriter(SDPATH, true);

            fileWriter.write(conrent + "\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
