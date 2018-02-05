package com.eanfang.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  10:18
 * @email houzhongzhou@yeah.net
 * @desc
 */

@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    private String title;
    private String msgTitle;
    private String msgContent;
    private String msgHelp;
    private boolean isShowLogo;
    private boolean isShowOkBtn;
    private String tip;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgHelp() {
        return msgHelp;
    }

    public void setMsgHelp(String msgHelp) {
        this.msgHelp = msgHelp;
    }

    public boolean isShowLogo() {
        return isShowLogo;
    }

    public void setShowLogo(boolean showLogo) {
        isShowLogo = showLogo;
    }

    public boolean isShowOkBtn() {
        return isShowOkBtn;
    }

    public void setShowOkBtn(boolean showOkBtn) {
        isShowOkBtn = showOkBtn;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", msgTitle='" + msgTitle + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", msgHelp='" + msgHelp + '\'' +
                ", isShowLogo=" + isShowLogo +
                ", isShowOkBtn=" + isShowOkBtn +
                ", tip='" + tip + '\'' +
                '}';
    }
}

