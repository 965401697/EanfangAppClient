package com.eanfang.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yaosheng on 2017/4/27.
 */
@Setter
@Getter
public class InstallOrderConfirmBean implements Serializable {


    /**
     * clientCompanyName : 安防公司
     * longitude : 3243
     * latitude : 12313
     * zone : 3.11.1.5
     * detailPlace : 褡裢坡地铁站
     * connector : 张天赐
     * connectorPhone : 154823364853
     * revertTimeLimit : 2
     * businessOneCode : 1.1
     * predictTime : 1
     * budget : 1
     * description : 需要安装10个防盗报警
     */

    private String clientCompanyName;
    private String longitude;
    private String latitude;
    private String zone;
    private String detailPlace;
    private String connector;
    private String connectorPhone;
    private int revertTimeLimit;
    private String businessOneCode;
    private int predictTime;
    private int budget;
    private String description;
    private Long businessOneId;
    private Long zoneId;


}


