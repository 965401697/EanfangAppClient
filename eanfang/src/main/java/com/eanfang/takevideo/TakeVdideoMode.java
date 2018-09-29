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
}
