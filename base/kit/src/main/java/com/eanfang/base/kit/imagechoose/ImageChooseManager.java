package com.eanfang.base.kit.imagechoose;

/**
 * created by wtt
 * at 2019/4/18
 * summary:
 */
public class ImageChooseManager {
    private  static Builder builder;
    public static Builder Builder(){
        if(builder==null){
            builder=new Builder();
        }
        return builder;
    }
}
