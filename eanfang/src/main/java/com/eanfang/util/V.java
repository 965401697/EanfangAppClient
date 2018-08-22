package com.eanfang.util;

/**
 * Created by xudingbing on 2018/3/29.
 * 无异常获得对象取值
 * 使用方法举例：
 * import static com.eanfang.util.V.v;
 * EntityE entity =  v(()->entityA.entityB.entityC.entityD)
 * String value   =  v(()->entityA.entityB.getValue()+entityC.entityD.getValue())
 * 表达式可以是任意内容，确保无异常抛出
 */

public final class V {
    public static <T> T v(IV<T> express) {
        try {
            return express.eval();
        } catch (Exception e) {
        }
        return null;
    }

    public interface IV<T> {
        T eval();
    }
}
