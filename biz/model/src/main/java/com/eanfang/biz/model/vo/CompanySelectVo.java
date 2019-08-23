package com.eanfang.biz.model.vo;

import androidx.databinding.ObservableField;

import java.util.ArrayList;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/8/22
 * @description 添加人员vo
 */
@Getter()
@Accessors(chain = true)
public class CompanySelectVo {

    private ObservableField<Object> organiaztionBean = new ObservableField<>();
    private ArrayList<String> roleIdList = new ArrayList<>();
    private ArrayList<String> roleNameList = new ArrayList<>();
}
