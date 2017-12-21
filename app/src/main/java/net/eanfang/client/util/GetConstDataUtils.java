package net.eanfang.client.util;


import net.eanfang.client.config.Config;

import java.util.List;



public class GetConstDataUtils {


    /**
     * 根据 文本 获取保修状态
     *
     * @param str
     * @return
     */
    public static String getRepairStatusByStr(String str) {
        List<String> repairStatus = Config.getConfig().getRepairStatus();
        for (int i = 0; i < repairStatus.size(); i++) {
            if (repairStatus.get(i).equals(str)) {
                return String.valueOf(i);
            }
        }
        return "";
    }

    /**
     * 根据 status 获取对应的文本
     *
     * @param status
     * @return
     */
    public static String getRepairStatusByStatus(Integer status) {
        return Config.getConfig().getRepairStatus().get(status);

    }

    /**
     * 根据 文本 获取读取状态
     *
     * @param str
     * @return
     */
    public static String getTaskReadStatusByStr(String str) {
        List<String> repairStatus = Config.getConfig().getTaskReadStatus();
        for (int i = 0; i < repairStatus.size(); i++) {
            if (repairStatus.get(i).equals(str)) {
                return String.valueOf(i);
            }
        }
        return "";
    }
    /**
     * 根据 文本 获取读取状态
     *
     * @param str
     * @return
     */
    public static String getInstallStatusByStr(String str) {
        List<String> repairStatus = Config.getConfig().getInstallStatus();
        for (int i = 0; i < repairStatus.size(); i++) {
            if (repairStatus.get(i).equals(str)) {
                return String.valueOf(i);
            }
        }
        return "";
    }

    /**
     * 根据 文本 获取读取状态
     *
     * @param str
     * @return
     */
    public static String getCheckReadStatusByStr(String str) {
        List<String> repairStatus = Config.getConfig().getCheckReadStatus();
        for (int i = 0; i < repairStatus.size(); i++) {
            if (repairStatus.get(i).equals(str)) {
                return String.valueOf(i);
            }
        }
        return "";
    }
}
