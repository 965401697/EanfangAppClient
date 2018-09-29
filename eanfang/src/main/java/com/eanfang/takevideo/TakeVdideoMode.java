package com.eanfang.takevideo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TakeVdideoMode implements Serializable {
    private String mImagePath;
    private String mKey;
    private String mType;// 技师端一个页面多处上传视频 进行区分
}
