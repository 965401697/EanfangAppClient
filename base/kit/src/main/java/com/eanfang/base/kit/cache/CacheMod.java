package com.eanfang.base.kit.cache;

/**
 * CacheMod
 *
 * @author jornl
 * @date 2019年7月21日
 */
public enum CacheMod {
    /**
     * 只缓存内存
     */
    Memory,
    /**
     * 缓存内存和磁盘 （默认）
     */
    Disk,
    /**
     * 缓存内存 和  SharedPreference
     */
    SharePref,
    /**
     * 缓存 内存、磁盘、SharedPreference
     */
    All
}
