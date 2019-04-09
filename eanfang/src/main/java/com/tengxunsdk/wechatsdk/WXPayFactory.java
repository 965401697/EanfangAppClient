package com.tengxunsdk.wechatsdk;

/**
 * created by wtt
 * at 2019/4/8
 * summary:
 */
public class WXPayFactory {
    private static IWXPay mIWXPay;
    public static IWXPay createWXPay(){
        if(mIWXPay==null){
            mIWXPay=new WXPayManager();
        }
        return mIWXPay;
    }
}
