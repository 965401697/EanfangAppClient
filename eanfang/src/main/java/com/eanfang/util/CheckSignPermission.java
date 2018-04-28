package com.eanfang.util;

import java.util.List;

/**
 * @author Guanluocang
 * @date on 2018/4/28  18:14
 * @decision 检查用户有无签到权限
 */

public class CheckSignPermission {
    public static boolean isCheckSign(List<String> permissinList) {
        if (permissinList.size() != 0 || permissinList != null) {
            for (int i = 0; i < permissinList.size(); i++)
                if ("top:sign:userlist".equals(permissinList.get(i))) {
                    return true;
                }
        }
        return false;
    }

}
