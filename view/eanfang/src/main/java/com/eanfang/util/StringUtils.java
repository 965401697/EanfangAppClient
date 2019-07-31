package com.eanfang.util;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hutool.core.lang.Validator;

/**
 * @author wen
 * Created at 2017/3/2
 * @desc
 */
public class StringUtils {


    /**
     * 校验密码是否符合规格：6~18位必须包含字母和数字 可以输入特殊字符
     *
     * @param pwd
     * @return
     */
    public static boolean isPwdLegal(String pwd) {
        /**
         * ^(?![0-9]+$)(?![A-Z]+$)(?![a-z]+$).{6,18}
         * */
        String REGEX_PWD = "^(?=.*[a-z])(?=.*\\d)[\\s\\S]{5,18}$";
        if (TextUtils.isEmpty(pwd)) {
            return false;
        } else {
            return Pattern.matches(REGEX_PWD, pwd);
        }
    }

    /**
     * 截取url中 字段值
     */
    public static String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }

    public static void showKeyboard(Context context, View editText) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            }
        }, 200);
    }

    public static StringBuffer getSecurityId(String mContent) {
        StringBuffer mId = new StringBuffer();
        int mType = 1;
        Pattern p = Pattern.compile("\'(.*?)\'");
        Matcher m = p.matcher(mContent);
        while (m.find()) {
            if (mType == 1) {
                mId.append(m.group(1) + "");
            } else {
                mId.append("," + m.group(1));
            }
            mType++;

        }
        return mId;
    }


}