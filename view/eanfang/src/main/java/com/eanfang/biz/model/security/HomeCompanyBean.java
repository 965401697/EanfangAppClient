package com.eanfang.biz.model.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liangkailun
 * Date ：2019-06-20
 * Describe :首页安防公司找技师
 */
@Setter
@Getter
public class HomeCompanyBean {

    /**
     * 地址
     */
    private String areaCode;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片
     */
    private String logoPic;
    /**
     * 设计数量
     */
    private int designCount;
    /**
     * 好评量
     */
    private int goodRate;
    /**
     * 安装数量
     */
    private int installCount;
    /**
     * 修理数量
     */
    private int repairCount;
    /**
     * 等级
     */
    private int level;
    /**
     * 从业年限
     */
    private int practitionerYears;
    /**
     * 认证状态
     */
    private int status;
    /**
     *页面类型 0：安防公司 1：找技师
     */
    private int pageType;

    /**
     * 跳转详情页需要的id 找技师是workerId
     */
    private int id;

    /**
     * userId
     */
    private String companyUserId;

}
