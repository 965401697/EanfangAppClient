package com.eanfang.base.kit;

import com.annimon.stream.function.Supplier;

/**
 * 无异常获得对象取值
 * 使用方法举例：
 * import static com.eanfang.base.kit.V.v;
 * EntityE entity =  v(()->entityA.entityB.entityC.entityD)
 * String value   =  v(()->entityA.entityB.getValue()+entityC.entityD.getValue())
 * 表达式可以是任意内容，确保无异常抛出
 *
 * @author jornl
 * @date 2019-07-05
 */
public final class V {
    public static <T> T v(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception ignored) {
        }
        return null;
    }

}
