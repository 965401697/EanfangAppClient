package com.eanfang.base.kit.picture;

/**
 * created by wtt
 * at 2019/4/18
 * summary:
 */
public class PictureManager {
    private  static Builder builder;
    public static Builder Builder(){
        if(builder==null){
            builder=new Builder();
        }
        return builder;
    }
}
