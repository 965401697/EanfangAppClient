package com.eanfang.util;

import com.eanfang.model.Message;

/**
 * Created by jornl on 2018/1/17.
 * 消息对象获取方法
 */

public class MessageUtil {

    /**
     * 支付成功 调用
     *
     * @return
     */
    public static Message paySuccess() {
        Message message = new Message();
        message.setTitle("支付成功");
        message.setMsgTitle("您的订单已支付成功");
        message.setMsgContent("稍后技师会和您取得联系,请保持电话畅通。");
        message.setTip("确定");
        message.setShowOkBtn(true);
        message.setShowLogo(true);
        return message;
    }

    public static Message payLatter() {
        Message message = new Message();
        message.setTitle("提交成功");
        message.setMsgTitle("您的订单已提交成功");
        message.setMsgContent("稍后技师会和您取得联系,请保持电话畅通。");
        message.setTip("");
        message.setTip("确定");
        message.setShowOkBtn(true);
        message.setShowLogo(true);
        return message;
    }

}
