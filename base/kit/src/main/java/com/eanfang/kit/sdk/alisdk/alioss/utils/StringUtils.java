package com.eanfang.kit.sdk.alisdk.alioss.utils;

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

import cn.hutool.core.util.StrUtil;

/**
 * @author wen
 *         Created at 2017/3/2
 * @desc
 */
public class StringUtils {
    public static boolean isValid(String str) {
        return str != null && str.trim().length() > 0;
    }

    /**
     * 返回字符串的16进制形式
     */
    public static String toHexString(String str) {
        if (str == null || str.length() == 0) {
            return "(null)";
        }
        StringBuilder sb = new StringBuilder();
        byte[] bytes = str.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            String temp = Integer.toHexString(bytes[i]);
            if (temp.length() == 1) {
                sb.append("0");
            }
            sb.append(temp + " ");
        }
        return sb.toString().trim().toUpperCase();
    }

    /*
     * 通过 Key从Bundle中获取相对应的整型值
	 */
    public static int getIntResFormBundle(Bundle bundle, String param) {
        int res = 0;
        try {
            res = Integer.valueOf(bundle.get(param).toString());
        } catch (Exception e) {
            //e.printStackTrace();
//            LogUtils.printStackTrace(e);
            res = 0;
        }
        return res;
    }


    /**
     * 返回金钱格式字符串
     *
     * @param price 金额
     * @return 00.00 格式
     */
    public static String getMoneyFormat(float price) {
        //构造方法的字符格式这里如果小数不足2位,会以0补足.
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        //format 返回的是字符串
        return decimalFormat.format(price);
    }

    //将钱保留2位小数
    public static String getMoneyFromDouble(double moneyFen, int scale) {
        BigDecimal bigDecimal = new BigDecimal(moneyFen);
        return getMoneyFormat(bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue());
    }

    /*
     * 通过 Key从Bundle中获取相对应的String值
	 */
    public static String getStringResFormBundle(Bundle bundle, String param) {
        String res = "";
        try {
            String str = bundle.get(param).toString();
            if (isEmpty(str)) {
                res = "";
            } else {
                res = str;
            }
        } catch (Exception e) {
//            LogUtils.printStackTrace(e);
            res = "";
        }
        return res;
    }

    /**
     * 字符串是否为空：包括null和""
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
//        return TextUtils.isEmpty(str);
        return StrUtil.isEmpty(str);
    }

    /*
         * 根据ID获取相应String内容
         */
    public static String getStringByKey(Context context, int keyid, Object... object) {
        String res = "";
        try {
            res = context.getResources().getString(keyid, object);
        } catch (Exception e) {
            //e.printStackTrace();
//            LogUtils.printStackTrace(e);
            res = "";
        }
        return res;
    }

    /*
     * 根据ID获取相应String内容然后按Html显示 ,
     */
    public static String getHtmlStringByKey(Context context, int keyid, Object... object) {
        String res = "";
        try {
            res = getStringByKey(context, keyid, object);
            res = Html.fromHtml(res).toString();
        } catch (Exception e) {
            //e.printStackTrace();
//            LogUtils.printStackTrace(e);
            res = "";
        }
        return res;
    }

    public static String getEditTextText(EditText et) {
        String res = "";
        if (et != null) {
            res = et.getText().toString().trim();
        }
        return res;
    }

    public static String getTextViewText(TextView et) {
        String res = "";
        if (et != null) {
            res = et.getText().toString().trim();
        }
        return res;
    }

    public static String getString(Context context, int res) {
        return context.getResources().getString(res);
    }

    /**
     * 校验手机号
     *
     * @param mobiles .
     * @return .
     */
    public static boolean isMobileString(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

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
     * 通过高德导航路径规划给的高速的名字得到高速的代码
     *
     * @param str G22XXX高速
     * @return G22
     */
    public static String getRoadCode(String str) {
        String code = "";
        for (int i = 0; i < str.length(); i++) {
            String one = str.substring(i, i + 1);
            char ar = one.charAt(0);
            if (ar >= '0' && ar <= '9') {
                //是数字
                code = code + ar;
            } else if (i > 0) {
                return "G" + code;
            }
        }
        return "G" + code;
    }

    /**
     * 得到文字在字符串中的位置
     *
     * @param string
     * @param code
     * @return
     */
    public static int getStringIndex(String string, String code) {
        return string.indexOf(code);
    }

    public static void main(String[] args) {
        System.out.println("isvalid:" + isValid("bac83aad1e1e3315b814160de69467d0PGFn05GfO4Bj2gcKyH4jcCfOS + CIkfDv0ZNNjBJ7TqM ="));
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

}