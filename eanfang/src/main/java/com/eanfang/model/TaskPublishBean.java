package com.eanfang.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务发布
 * Created by yaosheng on 2017/6/6.
 */
@Getter
@Setter
public class TaskPublishBean implements Serializable {


    /**
     * contacts : 旭神
     * contactsPhone : 15940525612
     * publishCompanyName : 北京法案视
     * projectCompanyName : 双井家乐福
     * zoneCode : 1.1
     * detailPlace : 北京褡裢坡
     * longitude : 15642
     * latitude : 8942532
     * type : 0
     * toDoorTime : 1990-11-03
     * predicttime : 0
     * budget : 0
     * businessOneCode : 1.2
     * description : 需要快速安装
     * pictures : 这里有图片，我们不一样，有啥不一样
     */

    private String contacts;
    private String contactsPhone;
    private String publishCompanyName;
    private String projectCompanyName;
    private String zoneCode;
    private String detailPlace;
    private String longitude;
    private String latitude;
    private int type;
    private String toDoorTime;
    private int predicttime;
    private int budget;
    private String businessOneCode;
    private String description;
    private String pictures;
    private Long zone_id;
    private Long business_one_id;


}
