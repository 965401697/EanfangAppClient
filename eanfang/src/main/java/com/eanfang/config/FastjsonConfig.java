package com.eanfang.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;

/**
 * Created by MrHou
 *
 * @on 2017/12/7  20:44
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class FastjsonConfig {
    public static SerializeConfig config;

    static {
        FastJsonConfig fjc = new FastJsonConfig();
        //1、序列化重点
        //指定date类型自动格式化
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
        //禁止循环引用解决$ref输出引用
        fjc.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        //解决超大Long到浏览器无法处理
        fjc.setSerializerFeatures(SerializerFeature.BrowserCompatible);
        //解决日期输出到客户端为数字
        fjc.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat);

        config = fjc.getSerializeConfig();

    }
}
