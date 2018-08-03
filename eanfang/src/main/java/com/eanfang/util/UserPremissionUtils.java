package com.eanfang.util;

import com.eanfang.application.EanfangApplication;

import java.util.List;

/**
 * Created by O u r on 2018/8/3.
 */

public class UserPremissionUtils {

    private static UserPremissionUtils mUserPremissionUtils;

    private static List<String> premissionList = EanfangApplication.get().getUser().getPerms();

    public static UserPremissionUtils getInstance() {
        if (mUserPremissionUtils == null) {
            mUserPremissionUtils = new UserPremissionUtils();
        }
        return mUserPremissionUtils;
    }
}
