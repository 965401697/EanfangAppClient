package net.eanfang.client.ui.activity.im;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by O u r on 2018/7/2.
 */


@MessageTag(value = "app:custom", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeMessage extends MessageContent {
    private static final String TAG = "CustomizeMessage";
    private String orderId;
    private String status;
    private String orderNum;
    private String creatTime;
    private String creatReleaseTime;
    private String workerName;
    private String picUrl;
    private String shareType; // 1:报修订单 2：故障处理

    public static final Creator<CustomizeMessage> CREATOR = new Creator<CustomizeMessage>() {
        public CustomizeMessage createFromParcel(Parcel source) {
            return new CustomizeMessage(source);
        }

        public CustomizeMessage[] newArray(int size) {
            return new CustomizeMessage[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("orderId", this.getOrderId());
            jsonObj.put("status", this.getStatus());
            jsonObj.put("orderNum", this.getOrderNum());
            jsonObj.put("creatTime", this.getCreatTime());
            jsonObj.put("creatReleaseTime", this.getCreatReleaseTime());
            jsonObj.put("workerName", this.getWorkerName());
            jsonObj.put("picUrl", this.getPicUrl());
            jsonObj.put("shareType", this.getShareType());

        } catch (JSONException var4) {
            io.rong.common.RLog.e("TextMessage", "JSONException " + var4.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public CustomizeMessage() {

    }

    public CustomizeMessage(byte[] data) throws UnsupportedEncodingException {
        String jsonStr = null;

        jsonStr = new String(data, "UTF-8");
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("orderId")) {
                this.setOrderId(jsonObj.optString("orderId"));
            }
            if (jsonObj.has("status")) {
                this.setStatus(jsonObj.optString("status"));
            }
            if (jsonObj.has("orderNum")) {
                this.setOrderNum(jsonObj.optString("orderNum"));
            }
            if (jsonObj.has("creatTime")) {
                this.setCreatTime(jsonObj.optString("creatTime"));
            }
            if (jsonObj.has("workerName")) {
                this.setWorkerName(jsonObj.optString("workerName"));
            }
            if (jsonObj.has("picUrl")) {
                this.setPicUrl(jsonObj.optString("picUrl"));
            }
            if (jsonObj.has("shareType")) {
                this.setShareType(jsonObj.optString("shareType"));
            }
            if (jsonObj.has("creatReleaseTime")) {
                this.setCreatReleaseTime(jsonObj.optString("creatReleaseTime"));
            }

        } catch (JSONException var4) {
            io.rong.common.RLog.e("TextMessage", "JSONException " + var4.getMessage());
        }

    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.getOrderId());
        ParcelUtils.writeToParcel(dest, this.getStatus());
        ParcelUtils.writeToParcel(dest, this.getOrderNum());
        ParcelUtils.writeToParcel(dest, this.getCreatTime());
        ParcelUtils.writeToParcel(dest, this.getCreatReleaseTime());
        ParcelUtils.writeToParcel(dest, this.getWorkerName());
        ParcelUtils.writeToParcel(dest, this.getPicUrl());
        ParcelUtils.writeToParcel(dest, this.getShareType());

    }

    public CustomizeMessage(Parcel in) {

        this.setOrderId(ParcelUtils.readFromParcel(in));
        this.setStatus(ParcelUtils.readFromParcel(in));
        this.setOrderNum(ParcelUtils.readFromParcel(in));
        this.setCreatTime(ParcelUtils.readFromParcel(in));
        this.setCreatReleaseTime(ParcelUtils.readFromParcel(in));
        this.setWorkerName(ParcelUtils.readFromParcel(in));
        this.setPicUrl(ParcelUtils.readFromParcel(in));
        this.setShareType(ParcelUtils.readFromParcel(in));

    }

    public String getCreatReleaseTime() {
        return creatReleaseTime;
    }

    public void setCreatReleaseTime(String creatReleaseTime) {
        this.creatReleaseTime = creatReleaseTime;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }
}
