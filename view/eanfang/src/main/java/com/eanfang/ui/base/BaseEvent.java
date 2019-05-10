package com.eanfang.ui.base;


import com.eanfang.util.GetDateUtils;

import java.util.Date;


/**
 * @author wen
 *         Created at 2017/3/11
 * @desc Eventbus数据封装
 */
public class BaseEvent {

    private int eventId;
    private String msg;
    private String tag;
    private Object object;
    private Date createDate;

    public BaseEvent() {
        createDate = GetDateUtils.getDateNow();
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
