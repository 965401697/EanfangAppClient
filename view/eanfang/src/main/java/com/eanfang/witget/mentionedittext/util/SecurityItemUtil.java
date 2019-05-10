package com.eanfang.witget.mentionedittext.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author guanluocang
 * @data 2019/4/10
 * @description
 */

public class SecurityItemUtil {

    private String startTag = "&nbsp;<user id='";
    private String endTag = "</user>&nbsp;";
    private String atName = "";

    public SecurityItemUtil() {
    }

    private static class SecurityItemUtilHolder {
        private static SecurityItemUtil securityItemUtil = new SecurityItemUtil();
    }

    public static SecurityItemUtil getInstance() {
        return SecurityItemUtilHolder.securityItemUtil;
    }

    public String doJonint(HashMap hashMap) {
        atName = "";
        Iterator iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            atName += startTag + entry.getKey() + "'>@" + entry.getValue() + endTag;
        }
        return atName;
    }

}
