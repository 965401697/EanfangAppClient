package net.eanfang.worker.ui.activity.im;

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


@MessageTag(value = "app:customVideo", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeVideoMessage extends MessageContent {
    private static final String TAG = "CustomizeVideoMessage";
    private String mp4Path;
    private String imgUrl;

    public static final Creator<CustomizeVideoMessage> CREATOR = new Creator<CustomizeVideoMessage>() {
        public CustomizeVideoMessage createFromParcel(Parcel source) {
            return new CustomizeVideoMessage(source);
        }

        public CustomizeVideoMessage[] newArray(int size) {
            return new CustomizeVideoMessage[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("mp4Path", this.getMp4Path());
            jsonObj.put("imgUrl", this.getImgUrl());
        } catch (JSONException var4) {
            io.rong.common.RLog.e("CustomizeVideoMessage", "JSONException " + var4.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.getMp4Path());
        ParcelUtils.writeToParcel(dest, this.getImgUrl());


    }

    public CustomizeVideoMessage(Parcel in) {
        this.setMp4Path(ParcelUtils.readFromParcel(in));
        this.setImgUrl(ParcelUtils.readFromParcel(in));
    }

    public CustomizeVideoMessage() {

    }

    public String getMp4Path() {
        return mp4Path;
    }

    public void setMp4Path(String mp4Path) {
        this.mp4Path = mp4Path;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
