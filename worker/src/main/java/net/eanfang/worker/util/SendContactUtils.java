package net.eanfang.worker.util;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.camera.util.ToastUtil;
import com.eanfang.model.TemplateBean;

import net.eanfang.worker.ui.activity.im.CustomizeMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * Created by O u r on 2018/8/1.
 */

public class SendContactUtils {


    private Bundle bundle;

    private Handler handler;

    private List<TemplateBean.Preson> newPresonList;

    private Dialog dialog;

    private String mPush;


    public SendContactUtils(Bundle bundle, Handler handler, List<TemplateBean.Preson> newPresonList, Dialog dialog, String push) {
        this.bundle = bundle;
        this.handler = handler;
        this.newPresonList = newPresonList;
        this.dialog = dialog;
        this.mPush = push;
    }


    public void send() {
        //发送分享的群组
        handler.post(runnable);//立马发送
    }

    /**
     * 循环发送
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情，这里再次调用此Runnable对象，以实现每一秒实现一次的定时器操作
            if (!dialog.isShowing()) {
                dialog.show();
            }
            if (newPresonList.size() > 5) {

                handler.postDelayed(runnable, 1500);
                List<TemplateBean.Preson> newRongIdLists = new ArrayList<>();

                newRongIdLists.addAll(newPresonList);
                List<TemplateBean.Preson> newLists = newPresonList.subList(0, 5);

                for (TemplateBean.Preson preson : newLists) {
                    newRongIdLists.remove(preson);
                    sendCheckedMsg(preson.getId());
                }
                newPresonList = newRongIdLists;

            } else if (newPresonList.size() <= 5) {
                for (TemplateBean.Preson preson : newPresonList) {
                    sendCheckedMsg(preson.getId());
                }
                handler.sendEmptyMessage(1);
                handler.removeCallbacks(runnable);
                dialog.dismiss();
            }

        }
    };

    /**
     * 发送分享
     *
     * @param id
     */
    private void sendCheckedMsg(String id) {
        if (bundle != null) {
            String shareType = bundle.getString("shareType");//区分消息的类型
            Conversation.ConversationType conversationType;

            CustomizeMessage customizeMessage = new CustomizeMessage();
            customizeMessage.setPicUrl(bundle.getString("picUrl"));
            customizeMessage.setWorkerName(bundle.getString("workerName"));
            customizeMessage.setCreatTime(bundle.getString("creatTime"));
            customizeMessage.setOrderNum(bundle.getString("orderNum"));
            customizeMessage.setOrderId(bundle.getString("id"));
            customizeMessage.setStatus(bundle.getString("status"));
            customizeMessage.setShareType(bundle.getString("shareType"));
            customizeMessage.setCreatReleaseTime(bundle.getString("creatReleaseTime"));
            if (isInteger(id)) {
                conversationType = Conversation.ConversationType.PRIVATE;
            } else {
                conversationType = Conversation.ConversationType.GROUP;
            }
            //说明是一个空消息
            if (TextUtils.isEmpty(customizeMessage.getShareType())) {
                ToastUtil.getInstance().showShort("发送失败");
                return;
            }
            Message message = RongIM.getInstance().sendMessage(conversationType, id, customizeMessage, mPush, mPush, new RongIMClient.SendMessageCallback() {
                @Override
                public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                    ToastUtil.getInstance().showShort("发送失败");
                }

                @Override
                public void onSuccess(Integer integer) {

                    ToastUtil.getInstance().showShort("发送成功");
                }
            });
        }
    }


    /**
     * 判断字符串是不是数字
     *
     * @param str
     * @return
     */

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
