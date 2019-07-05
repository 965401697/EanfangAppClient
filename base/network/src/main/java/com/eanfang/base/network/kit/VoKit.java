package com.eanfang.base.network.kit;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * Vo相关工具类
 *
 * @author jornl
 * @date 2019-07-01
 */
public class VoKit {

    private static final String ANDROIDX_DATABINDING_OBSERVABLE = "androidx.databinding.Observable";

    /**
     * vo 转 JSONObject
     *
     * @param vo vo
     * @return JSONObject
     */
    public static JSONObject vo2Json(Object vo) {
        JSONObject object = new JSONObject();
        Field[] fields = vo.getClass().getDeclaredFields();
        //遍历所有的字段
        for (Field field : fields) {
            //忽略掉transient
            if (field.toString().contains(" transient ")) {
                continue;
            }
            String fieldName = field.getName();
            Object val;
            if ((field.getType().toString().contains(ANDROIDX_DATABINDING_OBSERVABLE))) {
                Object obj = ReflectUtil.getFieldValue(vo, fieldName);
                val = ReflectUtil.invoke(obj, "get");
            } else {
                val = ReflectUtil.getFieldValue(vo, fieldName);
            }
            if (val != null) {
                //如果是空map  空list  空字符 则跳过参数
                if (val instanceof Map && ((Map) val).isEmpty()) {
                    continue;
                }
                if (val instanceof List && ((List) val).isEmpty()) {
                    continue;
                }
                if (StrUtil.isEmpty(val.toString())) {
                    continue;
                }
                object.put(fieldName, val);
            }
        }
        return object;
    }

    /**
     * vo 转 Entity
     *
     * @param vo  vo
     * @param <T> T
     * @return T
     */
    public static <T> T vo2Obj(Object vo) {
        JSONObject object = vo2Json(vo);
        return object.toJavaObject(new TypeToken<T>() {
        }.getType());
    }

    /**
     * JSONObject 转 Vo
     *
     * @param json json
     * @param vo   想要赋值的vo  允许将json属性赋值给一个已有的vo上 如果为空 则创建新的
     * @param <T>  T
     * @return T
     */
    public static <T> T json2Vo(JSONObject json, Object vo) {
        try {
            Object object;
            if (vo != null) {
                object = vo;
            } else {
                object = new TypeToken<T>() {
                }.getClazz().newInstance();
            }
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                //忽略掉transient
                if (field.toString().contains(" transient ")) {
                    continue;
                }

                String fieldName = field.getName();
                if (json.get(fieldName) != null) {
                    //获取viewModel的Observable数据类型
                    if ((field.getType().toString().contains(ANDROIDX_DATABINDING_OBSERVABLE))) {
                        Object obj = ReflectUtil.getFieldValue(object, fieldName);
                        if (obj == null) {
                            continue;
                        }
                        ReflectUtil.invoke(obj, "set", json.get(fieldName));
                    } else {
                        ReflectUtil.invoke(object, StrUtil.toCamelCase("set_" + fieldName), json.get(fieldName));
                    }
                }
            }
            return (T) object;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * entity 转 vo
     *
     * @param obj obj
     * @param <T> T
     * @return T
     */
    public static <T> T obj2Vo(Object obj) {
        return json2Vo(JSONObject.parseObject(JSON.toJSONString(obj)), null);
    }

    /**
     * entity 转 vo
     * 允许将entity属性赋值给一个已有的vo上
     *
     * @param obj obj
     * @param vo  vo
     * @param <T> <T>
     * @return T
     */
    public static <T> T obj2Vo(Object obj, Object vo) {
        return json2Vo(JSONObject.parseObject(JSON.toJSONString(obj)), vo);
    }


}
