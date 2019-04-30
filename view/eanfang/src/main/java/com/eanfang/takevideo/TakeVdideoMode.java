package com.eanfang.takevideo;

import java.io.Serializable;

public class TakeVdideoMode implements Serializable {
    private String mImagePath;
    private String mKey;
    private String mType;// 技师端一个页面多处上传视频 进行区分

    public TakeVdideoMode(String mImagePath, String mKey, String mType) {
        this.mImagePath = mImagePath;
        this.mKey = mKey;
        this.mType = mType;
    }

    public TakeVdideoMode() {
    }

    public String getMImagePath() {
        return this.mImagePath;
    }

    public String getMKey() {
        return this.mKey;
    }

    public String getMType() {
        return this.mType;
    }

    public void setMImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    public void setMKey(String mKey) {
        this.mKey = mKey;
    }

    public void setMType(String mType) {
        this.mType = mType;
    }
}
