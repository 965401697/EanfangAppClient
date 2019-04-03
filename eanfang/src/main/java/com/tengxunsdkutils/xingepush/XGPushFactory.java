package com.tengxunsdkutils.xingepush;

/**
 * created by wtt
 * at 2019/4/3
 * summary:
 */
public class XGPushFactory {
    private static IXGPush mIXGPush;
    public static IXGPush createXGPushManagerConfig(){
        if(mIXGPush==null){
            mIXGPush=new XGPushManagerConfig();
        }
        return mIXGPush;
    }

}
