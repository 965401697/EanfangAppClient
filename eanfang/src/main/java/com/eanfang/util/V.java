package com.eanfang.util;

/**
 * Created by xudingbing on 2018/3/29.
 * 无异常获得对象取值
 * 使用方法：
 * String value   =  V.v(()->entityA.entityB.entityC.entityD.getValue())
 * EntityE entity =  V.v(()->entityA.entityB.getValue()+entityC.entityD.getValue())
 * 表达式可以是任意内容，确保无异常抛出
 */
import java.util.function.Supplier;

public class V {
    public static <T> T v(Supplier<T> express) {
        try {
            return express.get();
        }catch(Exception e) {
        }
        return null;
    }
}
