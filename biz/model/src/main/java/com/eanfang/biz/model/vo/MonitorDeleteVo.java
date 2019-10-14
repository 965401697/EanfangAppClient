package com.eanfang.biz.model.vo;

import androidx.databinding.ObservableField;

import com.eanfang.biz.model.entity.Ys7DevicesEntity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/9/25
 * @description
 */

@Getter()
@Accessors(chain = true)
public class MonitorDeleteVo extends BaseVo implements Serializable {
    private ObservableField<String> groupId = new ObservableField<>();
    private ObservableField<String> configId = new ObservableField<>();
    private ObservableField<List<Ys7DevicesEntity>> deviceList = new ObservableField<List<Ys7DevicesEntity>>();

}
