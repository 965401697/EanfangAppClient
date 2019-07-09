package com.eanfang.biz.model.vo;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;

/**
 * Created by yaosheng on 2017/4/27.
 */
@Getter
public class   InstallOrderConfirmVo extends BaseVo implements Serializable {
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

    private ObservableField<String> clientCompanyName = new ObservableField<>();
    private ObservableField<String> longitude = new ObservableField<>();
    private ObservableField<String> latitude = new ObservableField<>();
    private ObservableField<String> zone = new ObservableField<>();
    private ObservableField<String> detailPlace = new ObservableField<>();
    private ObservableField<String> connector = new ObservableField<>();
    private ObservableField<String> connectorPhone = new ObservableField<>();
    private ObservableField<String> revertTimeLimit = new ObservableField<>();
    private ObservableField<String> businessOneCode = new ObservableField<>();
    private ObservableField<String> predictTime = new ObservableField<>();
    private ObservableField<String> budget = new ObservableField<>();
    private ObservableField<String> description = new ObservableField<>();
    private ObservableField<String> businessOneId = new ObservableField<>();
    private ObservableField<String> zoneId = new ObservableField<>();
    private ObservableField<String> img = new ObservableField<>();


}


