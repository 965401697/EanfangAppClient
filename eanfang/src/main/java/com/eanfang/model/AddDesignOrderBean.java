package com.eanfang.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jornl on 2017/9/7.
 */
@Getter
@Setter
public class AddDesignOrderBean {

    /**
     * userName : 万二尔
     * zoneCode : 3.11.3
     * detailPlace : 河北廊坊
     * longitude : 325354
     * latitude : 589567
     * contactUser : 胡老二
     * contact_phone : 15847895422
     * revertTimeLimit : 0
     * businessOneCode : 1.2
     * predictTime : 1
     * budgetLimit : 1
     * remarkInfo : 备注信息啊
     */

    private String userName;
    private String zoneCode;
    private String detailPlace;
    private String longitude;
    private String latitude;
    private String contactUser;
    private String contact_phone;
    private int revertTimeLimit;
    private String businessOneCode;
    private int predictTime;
    private int budgetLimit;
    private String remarkInfo;
    private Long businessOneId;
    private Long zoneId;

}
