package com.eanfang.biz.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: yaf
 * * @description: 脱岗监测实时监控等功能权限
 * * @author: yorkz
 * * @create: 2019-10-21 13:54
 **/
@Getter
@Setter
public class Ys7AccountParam {
    private String subAccountToken;
    private Boolean stationDetect;
    private Boolean realTime;
    private Boolean faceDetect;
}
