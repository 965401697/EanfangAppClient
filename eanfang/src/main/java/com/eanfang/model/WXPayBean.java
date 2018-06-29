package com.eanfang.model;

import java.io.Serializable;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  10:15
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WXPayBean implements Serializable {


    /**
     * appid : wx11d1a11a2f79200a
     * mchId : 1416487802
     * nonceStr : 03DHv5b8NKkjOzAM
     * prepayId : wx281759408679680eddc693133264836461
     * resultCode : SUCCESS
     * returnCode : SUCCESS
     * returnMsg : OK
     * sign : 4EBB37DDF0D4F8E11F0C3BFAB8FA4FC9
     * tradeType : APP
     * xmlString : <xml><return_code><![CDATA[SUCCESS]]></return_code> <return_msg><![CDATA[OK]]></return_msg> <appid><![CDATA[wx11d1a11a2f79200a]]></appid> <mch_id><![CDATA[1416487802]]></mch_id> <nonce_str><![CDATA[03DHv5b8NKkjOzAM]]></nonce_str> <sign><![CDATA[4EBB37DDF0D4F8E11F0C3BFAB8FA4FC9]]></sign> <result_code><![CDATA[SUCCESS]]></result_code> <prepay_id><![CDATA[wx281759408679680eddc693133264836461]]></prepay_id> <trade_type><![CDATA[APP]]></trade_type> </xml>
     */

    private String appid;
    private String mchId;
    private String nonceStr;
    private String prepayId;
    private String resultCode;
    private String returnCode;
    private String returnMsg;
    private String sign;
    private String tradeType;
    private String xmlString;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getXmlString() {
        return xmlString;
    }

    public void setXmlString(String xmlString) {
        this.xmlString = xmlString;
    }
}

