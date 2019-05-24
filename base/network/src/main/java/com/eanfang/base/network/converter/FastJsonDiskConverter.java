package com.eanfang.base.network.converter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.zchu.rxcache.diskconverter.IDiskConverter;
import com.zchu.rxcache.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * RxCache 的FastJson解析器
 */
public class FastJsonDiskConverter implements IDiskConverter {
    @Override
    public <T> T load(InputStream source, Type type) {
        try {
            return JSONObject.parseObject(source, type, (Feature) null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.close(source);
        }
        return null;
    }

    @Override
    public boolean writer(OutputStream sink, Object data) {
        try {
            String json = JSONObject.toJSONString(data);
            byte[] bytes = json.getBytes();
            sink.write(bytes, 0, bytes.length);
            sink.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.close(sink);
        }
        return false;
    }
}
