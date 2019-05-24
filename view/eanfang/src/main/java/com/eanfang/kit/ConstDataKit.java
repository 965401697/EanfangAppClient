package com.eanfang.kit;

import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.biz.model.bean.ConstAllBean;

/**
 * 常量数据工具类
 *
 * @author jornl
 * @date 2019年5月17日 11:16:03
 */
public class ConstDataKit {

    private static final String MD5_KEY = "CONST_DATA_MD5";
    private static final String CONST_DATA_ALL = "CONST_DATA_ALL";

    private ConstDataKit() {
    }

    public static ConstDataKit get() {
        return new ConstDataKit();
    }

    /**
     * 放
     *
     * @param bean bean
     */
    public void put(ConstAllBean bean) {
        CacheKit.get().put(MD5_KEY, bean.getMD5());
        CacheKit.get().put(CONST_DATA_ALL, bean.getData());

    }

    /**
     * 取 md5
     *
     * @return String
     */
    public String getMd5() {
        String md5 = CacheKit.get().getStr(MD5_KEY);
        return md5 != null ? md5 : "0";
    }

}
