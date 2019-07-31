package com.eanfang.biz.model.bean;

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
     * package : Sign=WXPay
     * appid : wx11d1a11a2f79200a
     * sign : 35A918335B246D83F4F31D29A0BF1E03D59298242AD66DA7B1F4290AD071C5AD
     * partnerid : 1416487802
     * prepayid : wx06183500920638bb9d198cdb0012624597
     * noncestr : cU2QyKu0xKITye5I
     * timestamp : 1530873302
     */


    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;



    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

