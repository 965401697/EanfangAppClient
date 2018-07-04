package net.eanfang.client.ui.activity.im;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import io.rong.push.common.RLog;

/**
 * Created by O u r on 2018/7/2.
 */


@MessageTag(value = "app:custom", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeMessage extends MessageContent {
    private static final String TAG = "rrrr";
    private String content;
    protected String extra;
    public static final Creator<CustomizeMessage> CREATOR = new Creator<CustomizeMessage>() {
        public CustomizeMessage createFromParcel(Parcel source) {
            return new CustomizeMessage(source);
        }

        public CustomizeMessage[] newArray(int size) {
            return new CustomizeMessage[size];
        }
    };

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", this.getEmotion(this.getContent()));
            if (!TextUtils.isEmpty(this.getExtra())) {
                jsonObj.put("extra", this.getExtra());
            }

            if (this.getJSONUserInfo() != null) {
                jsonObj.putOpt("user", this.getJSONUserInfo());
            }

            if (this.getJsonMentionInfo() != null) {
                jsonObj.putOpt("mentionedInfo", this.getJsonMentionInfo());
            }
        } catch (JSONException var4) {
            io.rong.common.RLog.e("TextMessage", "JSONException " + var4.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    private String getEmotion(String content) {
        Pattern pattern = Pattern.compile("\\[/u([0-9A-Fa-f]+)\\]");
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            int inthex = Integer.parseInt(matcher.group(1), 16);
            matcher.appendReplacement(sb, String.valueOf(Character.toChars(inthex)));
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    protected CustomizeMessage() {
    }

    public static CustomizeMessage obtain(String text) {
        CustomizeMessage model = new CustomizeMessage();
        model.setContent(text);
        return model;
    }

    public CustomizeMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("content")) {
                this.setContent(jsonObj.optString("content"));
            }

            if (jsonObj.has("extra")) {
                this.setExtra(jsonObj.optString("extra"));
            }

            if (jsonObj.has("user")) {
                this.setUserInfo(this.parseJsonToUserInfo(jsonObj.getJSONObject("user")));
            }

            if (jsonObj.has("mentionedInfo")) {
                this.setMentionedInfo(this.parseJsonToMentionInfo(jsonObj.getJSONObject("mentionedInfo")));
            }
        } catch (JSONException var4) {
            io.rong.common.RLog.e("TextMessage", "JSONException " + var4.getMessage());
        }

    }

    public void setContent(String content) {
        this.content = content;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.getExtra());
        ParcelUtils.writeToParcel(dest, this.content);
        ParcelUtils.writeToParcel(dest, this.getUserInfo());
        ParcelUtils.writeToParcel(dest, this.getMentionedInfo());
    }

    public CustomizeMessage(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setContent(ParcelUtils.readFromParcel(in));
        this.setUserInfo((UserInfo) ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo((MentionedInfo) ParcelUtils.readFromParcel(in, MentionedInfo.class));
    }

    public CustomizeMessage(String content) {
        this.setContent(content);
    }

    public String getContent() {
        return this.content;
    }

//    public List<String> getSearchableWord() {
//        List<String> words = new ArrayList();
//        words.add(this.content);
//        return words;
//    }
}
