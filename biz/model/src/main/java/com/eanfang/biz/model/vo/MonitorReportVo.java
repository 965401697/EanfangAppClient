package com.eanfang.biz.model.vo;

import androidx.databinding.ObservableField;

import java.io.Serializable;
import java.util.HashMap;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/10/13
 * @description
 */

@Getter()
@Accessors(chain = true)
public class MonitorReportVo extends BaseVo implements Serializable {

    private ObservableField<String> reportRemarks = new ObservableField<>();
    private ObservableField<String> reportPic = new ObservableField<>();
    private ObservableField<HashMap<String, String>> handleUserList = new ObservableField<>();
    private ObservableField<HashMap<String, String>> copyUserList = new ObservableField<>();
//    private ObservableField<HashMap<String, String>> sendGroupList = new ObservableField<>();
}
