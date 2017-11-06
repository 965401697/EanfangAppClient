package com.eanfang.util;

import java.util.UUID;

/**
 *生成随机UUID
 * Created by Administrator on 2017/3/20.
 */

public class UuidUtil {
    /**
     * 生成随机UUID
     * @return
     */
    public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }
}
