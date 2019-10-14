package com.eanfang.biz.model.vo;

import androidx.databinding.ObservableField;

import java.io.Serializable;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/9/24
 * @description 实时监控分组修改
 */
@Getter()
@Accessors(chain = true)
public class MonitorUpdataVo extends BaseVo implements Serializable {
    private ObservableField<String> parentGroupId = new ObservableField<>();
    private ObservableField<String> groupName = new ObservableField<>();
    private ObservableField<String> parentGroupName = new ObservableField<>();
    private ObservableField<String> groupId = new ObservableField<>();
    private ObservableField<String> deviceId = new ObservableField<>();
    private ObservableField<String> deviceName = new ObservableField<>();
}
