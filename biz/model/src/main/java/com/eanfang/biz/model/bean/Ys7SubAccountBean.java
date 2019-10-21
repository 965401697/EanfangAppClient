package com.eanfang.biz.model.bean;

import com.eanfang.biz.model.entity.Ys7AccountParam;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Ys7SubAccountBean {
    private Map<Long, Ys7AccountParam> subAccountInfoList;
}
