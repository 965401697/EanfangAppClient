package com.eanfang.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by admin on 2018/4/11.
 */
@Getter
@Setter
public class ClientData {
    private int type;// 类型  报修 报装
    private int total;//总数
    private int added;//新增
    private int statusOne;//自定义类型
    private int statusTwo;//自定义类型
    private int statusThree;//自定义类型
    private int statusFour;//自定义类型
}
