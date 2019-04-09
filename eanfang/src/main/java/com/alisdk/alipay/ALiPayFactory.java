package com.alisdk.alipay;

/**
 * created by wtt
 * at 2019/4/9
 * summary:
 */
public class ALiPayFactory {
    private static IALiPay mIALiPay;
    public static IALiPay createALiPay(){
        if(mIALiPay==null){
            mIALiPay=new ALiPayManager();
        }
        return mIALiPay;
    }
}
