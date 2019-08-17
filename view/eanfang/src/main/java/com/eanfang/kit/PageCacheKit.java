package com.eanfang.kit;

import android.content.Context;

import com.annimon.stream.function.Consumer;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.rx.RxDialog;
import com.eanfang.biz.model.vo.BaseVo;


/**
 * 页面数据缓存工具类
 *
 * @author jornl
 * @date 2019年8月17日
 */
public class PageCacheKit {


    /**
     * 保存页面vo对象
     *
     * @param context  context
     * @param vo       vo
     * @param consumer consumer
     */
    public static <T extends BaseVo> void save(Context context, T vo, Consumer<Boolean> consumer) {
        new RxDialog(context)
                .setTitle("提示")
                .setMessage("是否放弃填写？")
                .setPositiveText("是")
                .setNegativeText("否")
                .dialogToObservable()
                .subscribe((code) -> {
                    if (code.equals(RxDialog.POSITIVE)) {
                        CacheKit.get().put(context.getClass().getName() + "_" + vo.getClass().getName(), vo);
                        consumer.accept(true);
                    } else {
                        consumer.accept(false);
                    }
                });
    }

    /**
     * 获取页面缓存的数据
     *
     * @param context  context
     * @param clazz    clazz
     * @param consumer consumer
     */
    public static <T extends BaseVo> void get(Context context, Class<T> clazz, Consumer<T> consumer) {
        BaseVo vo = CacheKit.get().get(context.getClass().getName() + "_" + clazz.getName(), clazz);
        if (vo == null) {
            consumer.accept(null);
            return;
        }
        new RxDialog(context)
                .setTitle("提示")
                .setMessage("是否加载上次填写的数据？")
                .setPositiveText("是")
                .setNegativeText("否")
//                .setNeutralText("不使用")
                .dialogToObservable()
                .subscribe((code) -> {
                    if (code.equals(RxDialog.POSITIVE)) {
                        consumer.accept((T) vo);
                    } else {
                        consumer.accept(null);
                    }
                });
    }

    /**
     * 清除 数据
     *
     * @param context context
     * @param clazz   clazz
     * @param <T>     <T>
     */
    public static <T extends BaseVo> void clean(Context context, Class<T> clazz) {
        CacheKit.get().remove(context.getClass().getName() + "_" + clazz.getName());
    }
}
