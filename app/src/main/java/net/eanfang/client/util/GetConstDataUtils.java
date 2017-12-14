package net.eanfang.client.util;


import net.eanfang.client.config.Config;
import net.eanfang.client.config.EanfangConst;

import java.util.List;



public class GetConstDataUtils {

    /**
     * 根据 是 否 的编码 获取 文字
     *
     * @param code (0、1)
     * @return
     */
    public static String getTrueOrFalseByCode(String code) {
        if ("0".equals(code)) {
            return EanfangConst.EANFANG_FALSE_STR;
        } else if ("1".equals(code)) {
            return EanfangConst.EANFANG_TRUE_STR;
        }
        return "";

    }

    /**
     * 根据业务类型的编号 获取业务类型的名称
     *
     * @param code (A、B、C、I、P、V、)
     * @return
     */
//    public static String getBugOneNameByCode(String code) {
////        Optional<BusinessOne> one = Stream.of(Config.getConfig().getBusinessOneList()).filter(bus -> bus.getCode().equals(code)).findFirst();
////        if (one.isPresent()) {
////            return one.get().getName();
////        }
////        return "";
//    }

    /**
     * 根据业务类型的名称 获取业务类型的编码
     *
     * @param name (电视监控、防盗报警)
     * @return
     */
//    public static String getBugOneCodeByName(String name) {
//        Optional<BusinessOne> one = Stream.of(Config.getConfig().getBusinessOneList()).filter(bus -> bus.getName().equals(name)).findFirst();
//        if (one.isPresent()) {
//            return one.get().getName();
//        }
//        return "";
//    }

    /**
     * 根据维修结论的编号 获取维修结论的名称
     *
     * @param code (0、1、2）
     * @return
     */
    public static String getRepairConclusionByCode(String code) {
        if ("0".equals(code)) {
            return EanfangConst.REPAIR_CONCLUSION_NAME_0;
        } else if ("1".equals(code)) {
            return EanfangConst.REPAIR_CONCLUSION_NAME_1;
        } else if ("2".equals(code)) {
            return EanfangConst.REPAIR_CONCLUSION_NAME_2;
        }
        return "";
    }

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
