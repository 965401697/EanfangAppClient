package com.eanfang.biz.model.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-06-19
 * Describe :最新订单网络请求
 */
@NoArgsConstructor
@Data
public class HomeNewOrderBean {

    /**
     * date : 2019-06-17
     * sysName : 维修
     * mobile : 180*****545
     * name : 王*笑
     * placeName : 新疆维吾尔自治区乌鲁木齐市天山区
     * content : 通过易安防进行了一次免费勘察
     */

    private String date;
    private String sysName;
    private String mobile;
    private String name;
    private String placeName;
    private String content;
}
