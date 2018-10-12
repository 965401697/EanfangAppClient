package com.eanfang.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MrHou
 *
 * @on 2017/12/19  15:26
 * @email houzhongzhou@yeah.net
 * @desc 检查明细新增
 */
@Getter
@Setter
public class AddWorkInspectDetailBean implements Serializable {

    /**
     * sysWorkInspectDetailId : 941492980165455874
     * disposeInfo : 这个工作检查应该这样处理。。。
     * remarkInfo : 这是备注信息
     * pictures : 多个图片地铁
     */

    private long oaWorkInspectDetailId;
    private String disposeInfo;
    private String remarkInfo;
    private String pictures;
    private Long oaWorkInspectId;

}
