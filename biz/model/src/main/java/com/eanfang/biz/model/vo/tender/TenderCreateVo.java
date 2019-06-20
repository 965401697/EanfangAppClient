package com.eanfang.biz.model.vo.tender;

import androidx.databinding.ObservableField;

import com.eanfang.biz.model.vo.BaseVo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/6/3
 * @description 用工找活 创建
 */
@Getter()
@Accessors(chain = true)
public class TenderCreateVo extends BaseVo implements Serializable {
    /**
     * 联系人==发布人：
     */
    private ObservableField<String> contacts = new ObservableField<>();
    /**
     * 联系人电话
     */
    private ObservableField<String> contactsPhone = new ObservableField<>();
    /**
     * 任务发布单位名==用工单位：
     */
    private ObservableField<String> publishCompanyName = new ObservableField<>();
    /**
     * 项目单位名
     */
    private ObservableField<String> projectCompanyName = new ObservableField<>();
    /**
     * 详细地址==项目地址
     */
    private ObservableField<String> province = new ObservableField<>();
    /**
     * 收获城市
     */
    private ObservableField<String> city = new ObservableField<>();
    /**
     * 收获区县
     */
    private ObservableField<String> county = new ObservableField<>();
    private ObservableField<String> detailPlace = new ObservableField<>();
    /**
     * 区（县）编码(基础数据表)
     */
    private ObservableField<String> zoneCode = new ObservableField<>();
    /**
     * 区（县）ID
     */
    private ObservableField<Integer> zoneId = new ObservableField<>();
    /**
     * 经度
     */
    private ObservableField<String> longitude = new ObservableField<>();
    /**
     * 纬度
     */
    private ObservableField<String> latitude = new ObservableField<>();
    /**
     * 一级业务类型==业务类型：
     */
    private ObservableField<String> businessOneCode = new ObservableField<>();
    /**
     * 系统类别--用工发布
     */
    private ObservableField<String> systemType = new ObservableField<>();
    /**
     * 结束时间
     */
    private ObservableField<String> endTime = new ObservableField<>();
    /**
     * 预计工期（0当天完工，1三天左右，2一周左右，3一个月左右，4三个月左右，5六个月左右，6一年左右，7一年以上）
     */
    private ObservableField<String> predicttime = new ObservableField<>();
    /**
     * 项目预算
     */
    private ObservableField<String> budget = new ObservableField<>();
    /**
     * 预算单位
     */
    private ObservableField<String> budgetUnit = new ObservableField<>();
    /**
     * 需求描述==环境描述
     */
    private ObservableField<String> description = new ObservableField<>();
    /**
     * 用工要求
     */
    private ObservableField<String> laborRequirements = new ObservableField<>();
    /**
     * 图片地址（多个地址用逗号分割）
     */
    private ObservableField<String> pictures = new ObservableField<>();

}
