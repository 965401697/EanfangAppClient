package net.eanfang.client.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.annimon.stream.Stream;

import net.eanfang.client.ui.model.BusinessOne;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by jornl on 2017/8/29.
 */

public class ConfigUtils {

    private static Properties properties;

    /**
     * 初始化 配置读取器
     *
     * @param context
     */
    public static void initProperties(Context context) {
        if (properties == null) {
            try {
                properties = new Properties();
                InputStream inputStream = context.getAssets().open("appConfig.properties");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                properties.load(bufferedReader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取配置文件内容
     *
     * @param context
     * @param key
     * @return
     */
    public static String getProperties(Context context, String key) {
        initProperties(context);
        return properties.get(key).toString();
    }

    /**
     * 获得keyValue 集合
     *
     * @param str
     * @return
     */
    private static Map<String, String> getKeyValueMap(String str) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isEmpty(str)) {
            return map;
        }
        Stream.of(str.split(",")).map(c -> c.split(":")).forEach(c -> {
            map.put(c[0], c[1]);
        });
        return map;
    }

    /**
     * 读取业务类型
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static List<BusinessOne> getBusinessOne() {
        List<BusinessOne> resultList = new ArrayList<>();
        if (properties == null) {
            return null;
        }
        String businessStr = properties.getProperty("businessOne");
        Stream.of(getKeyValueMap(businessStr)).sorted((o1, o2) -> {
            return o1.getKey().compareTo(o2.getKey());
        }).forEach(c -> {
            BusinessOne one = new BusinessOne();
            one.setCode(c.getKey());
            one.setName(c.getValue());
            resultList.add(one);
        });
        return resultList;
    }

    private static List<String> getValueList(String name) {
        List<String> resultList = new ArrayList<>();
        if (properties == null) {
            return null;
        }
        String businessStr = properties.getProperty(name);
        Stream.of(getKeyValueMap(businessStr)).sorted((o1, o2) -> {
            return o1.getKey().compareTo(o2.getKey());

        }).forEach(c -> {
            resultList.add(c.getValue());
        });
        return resultList;
    }


    /**
     * 获得报修单状态
     *
     * @return
     */
    public static List<String> getRepairStatus() {
        return getValueList("repairStatus");
    }

    /**
     * 工作任务 已读/未读
     *
     * @return
     */
    public static List<String> getTaskReadStatus() {
        return getValueList("taskReadStatus");
    }

    /**
     * 工作任务 已读/未读
     *
     * @return
     */
    public static List<String> getInstallStatus() {
        return getValueList("installStatus");
    }

    /**
     * 工作检查状态
     *
     * @return
     */
    public static List<String> getCheckReadStatus() {
        return getValueList("checkReanStatus");
    }


    /**
     * 免费设计 订单状态
     *
     * @return
     */
    public static List<String> getDesignOrderStatus() {
        return getValueList("designOrderStatus");
    }

    /**
     * 设备参数类型
     *
     * @return
     */
    public static List<String> getRepairConslusion() {
        return getValueList("repairConslusion");
    }

    public static List<String>getRepairSelectWorkers(){
        return getValueList("repairSelectWorkers");
    }

}
