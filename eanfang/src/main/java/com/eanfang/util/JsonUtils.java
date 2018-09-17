package com.eanfang.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.List;

/**
 * 2018年8月10日 14:48:38
 * <p>
 * 请使用JSON原生转换
 */
@Deprecated
public class JsonUtils {

    /**
     * 对象转成 JSONObject
     *
     * @param object
     * @return
     * @author jornl --2017年9月27日下午1:51:54
     */
    public static JSONObject obj2JSON(Object object) {
        return str2JSON(obj2String(object));
    }

    /**
     * 对象转 Json
     *
     * @param object  对象
     * @param include 包含字段
     * @return json字符串
     * @author jornl --2017年9月27日下午1:34:51
     */
    public static JSONObject obj2JSON(Object object, List<String> include) {
        return str2JSON(obj2String(object, object.getClass(), include));
    }

    /**
     * 将数组转换成 JSONObject （默认key=bean）
     *
     * @param list    数组
     * @param include 包含字段
     * @return JSONObject（key=bean）
     * @author jornl --2017年9月27日下午2:47:35
     */
    public static JSONObject list2JSON(List<?> list, List<String> include) {
        JSONArray array = str2JSONArray(obj2String(list, list.get(0).getClass(), include));
        JSONObject object = new JSONObject();
        object.put("bean", array);
        return object;
    }

    /**
     * 对象转换成 Json字符串
     *
     * @param object 对象
     * @return json字符串
     * @author jornl --2017年9月27日上午11:46:29
     */
    public static String obj2String(Object object) {
        return JSON.toJSONString(object, SerializerFeature.BrowserCompatible);
    }

    /**
     * 对象转成 json 字符串
     *
     * @param object  对象
     * @param include 包含
     * @return
     * @author jornl --2017年9月27日下午2:08:59
     */
    public static String obj2String(Object object, Class<? extends Object> clazz, List<String> include) {
        String[] strArray = new String[include.size()];
        include.toArray(strArray);
        SimplePropertyPreFilter spPreFilter = new SimplePropertyPreFilter(clazz, strArray);
        return JSONObject.toJSONString(object, spPreFilter);
    }

    /**
     * json字符串转 对象
     *
     * @param jsonString
     * @return
     * @author jornl --2017年9月27日下午2:09:31
     */
    public static JSONObject str2JSON(String jsonString) {
        return JSON.parseObject(jsonString);
    }

    /**
     * 将 json字符串转成 JSONArray
     *
     * @param jsonString
     * @return
     * @author jornl --2017年9月27日下午2:43:27
     */
    public static JSONArray str2JSONArray(String jsonString) {
        return JSON.parseArray(jsonString);
    }

    /**
     * JSONObject 对象 转 Object对象
     *
     * @param json
     * @param clazz
     * @return Object
     * @author LiXu
     * @date 2017年10月12日 上午10:42:58
     */
    public static <T> T json2Obj(JSONObject json, Class<T> clazz) {
        String jsonstr = json.toString();
        T object = JSON.parseObject(jsonstr, clazz);
        return object;

    }

    /**
     * JSONObject 转 List JSONObject中（默认key=bean）
     *
     * @param json
     * @param clazz
     * @return (key = bean) bean的List
     * @author LiXu
     * @date 2017年10月12日 上午11:12:09
     */
    public static List<?> json2List(JSONObject json, Class<?> clazz) {
        JSONArray jsonarr = json.getJSONArray("bean");
        String jsonstr = jsonarr.toString();
        List<?> list = JSON.parseArray(jsonstr, clazz);
        return list;

    }

    /**
     * JSONArray 转 list
     *
     * @param jsonarr
     * @param clazz
     * @return List
     * @author LiXu
     * @date 2017年10月12日 上午11:33:37
     */
    public static List<?> jsonArray2List(JSONArray jsonarr, Class<?> clazz) {
        String jsonstr = jsonarr.toString();
        List<?> list = JSON.parseArray(jsonstr, clazz);
        return list;

    }

    /**
     * 数组转 JSONArray
     *
     * @param list
     * @return
     */
    public static JSONArray list2JSONArray(List<?> list) {
        JSONArray array = str2JSONArray(obj2String(list));
        return array;
    }

    /**
     * 将 list 转 JSONArray对象
     *
     * @param list    数组
     * @param include 包含字段
     * @return JSONArray
     * @author LiXu
     * @date 2017年10月12日 上午11:50:02
     */
    public static JSONArray list2JSONArray(List<?> list, List<String> include) {
        JSONArray array = str2JSONArray(obj2String(list, list.get(0).getClass(), include));
        return array;
    }

    /**
     * 将json字符串转成对象
     *
     * @param jsonString 字符串
     * @param clazz
     * @return Object
     * @author LiXu
     * @date 2017年10月12日 下午1:41:31
     */
    public static Object str2Obj(String jsonString, Class<?> clazz) {
        JSONObject json = str2JSON(jsonString);
        return json2Obj(json, clazz);
    }

    /**
     * 将json数组字符串转成list数组
     *
     * @param jsonString 数组字符串
     * @param clazz
     * @return list 数组
     * @author LiXu
     * @date 2017年10月12日 下午1:46:13
     */
    public static List<?> str2List(String jsonString, Class<?> clazz) {
        JSONArray jsonarr = str2JSONArray(jsonString);
        return jsonArray2List(jsonarr, clazz);
    }

}
