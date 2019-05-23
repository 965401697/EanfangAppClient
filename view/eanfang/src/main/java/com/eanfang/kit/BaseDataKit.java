package com.eanfang.kit;

import com.annimon.stream.Stream;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.biz.model.bean.BaseDataBean;

/**
 * 基础数据工具类
 *
 * @author jornl
 * @date 2019年5月17日 11:16:03
 */
public class BaseDataKit {
    //md5 key
    private static final String MD5_KEY = "BASE_DATA_MD5";
    //系统类别 key
    private static final String BASE_DATA_BUSINESS = "BASE_DATA_BUSINESS";
    //业务类型
    private static final String BASE_DATA_SERVICE = "BASE_DATA_SERVICE";
    //地区
    private static final String BASE_DATA_REGION = "BASE_DATA_REGION";
    //行业
    private static final String BASE_DATA_INDUSTRY = "BASE_DATA_INDUSTRY";
    //品牌
    private static final String BASE_DATA_MODEL = "BASE_DATA_MODEL";
    //擅长设备
    private static final String BASE_DATA_DEVICE = "BASE_DATA_DEVICE";
    //施工组织能力
    private static final String BASE_DATA_ABILITY = "BASE_DATA_ABILITY";
    //工具机械能力
    private static final String BASE_DATA_TOOL = "BASE_DATA_TOOL";
    //技师能力标签
    private static final String BASE_DATA_TAG = "BASE_DATA_TAG";


    private BaseDataKit() {
    }

    public static BaseDataKit get() {
        return new BaseDataKit();
    }

    /**
     * 放
     *
     * @param bean bean
     */
    public void put(BaseDataBean bean) {
        CacheKit.get().put(MD5_KEY, bean.getMD5());
        CacheKit.get().put(BASE_DATA_BUSINESS, Stream.of(bean.getData()).filter(data -> data.getDataType().equals(1)));
        CacheKit.get().put(BASE_DATA_SERVICE, Stream.of(bean.getData()).filter(data -> data.getDataType().equals(2)));
        CacheKit.get().put(BASE_DATA_REGION, Stream.of(bean.getData()).filter(data -> data.getDataType().equals(2)));
        CacheKit.get().put(BASE_DATA_INDUSTRY, Stream.of(bean.getData()).filter(data -> data.getDataType().equals(2)));
        CacheKit.get().put(BASE_DATA_MODEL, Stream.of(bean.getData()).filter(data -> data.getDataType().equals(2)));
        CacheKit.get().put(BASE_DATA_DEVICE, Stream.of(bean.getData()).filter(data -> data.getDataType().equals(2)));
        CacheKit.get().put(BASE_DATA_ABILITY, Stream.of(bean.getData()).filter(data -> data.getDataType().equals(2)));
        CacheKit.get().put(BASE_DATA_TOOL, Stream.of(bean.getData()).filter(data -> data.getDataType().equals(2)));
        CacheKit.get().put(BASE_DATA_TAG, Stream.of(bean.getData()).filter(data -> data.getDataType().equals(2)));
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
