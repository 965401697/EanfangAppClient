package net.eanfang.client.ui.activity.leave_post.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liangkailun
 * Date ：2019-06-28
 * Describe :脱岗详情bean
 */
@Getter
@Setter
public class LeavePostDetailBean {
    /**
     * 设备名称
     */
    private String name;
    /**
     * 监测图片
     */
    private String img;
    /**
     * 所在区域
     */
    private String areaCode;

    private String serialNum;
    /**
     * 所在位置
     */
    private String position;

    /**
     * 状态 0:不可用 1:启用 2:删除
     */
    private int status;

    /**
     * 是否占用 0:未占用 1:已占用
     */
    private int use;

    /**
     * 数量
     */
    private int count;

    /**
     * 报警时间
     */
    private String time;

    /**
     * 页面类型 判断展示数据
     * 0 地区岗位 1 设备监测 2历史记录数据
     */
    private int pageType;
}
