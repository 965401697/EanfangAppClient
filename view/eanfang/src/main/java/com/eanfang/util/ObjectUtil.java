package com.eanfang.util;


import android.content.Context;
import android.content.pm.ActivityInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author wen
 *         Created at 2017/3/2
 * @desc
 */
public class ObjectUtil {
    /**
     * 对象转换字符串
     *
     * @param object
     * @return
     * @throws IOException
     */
    public static String object2String(Object object) throws IOException {
        if (object == null) {
            throw new NullPointerException("object can not be null");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        String result = Base64.encode(baos.toByteArray());
        result = DES.encryptDES(result, "43215768");
        return result;
    }

    /**
     * string转换对象
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Object string2Object(String str) throws Exception {
        if (str == null || str.equals("")) {
            throw new NullPointerException("str can not be null ");
        }
        str = DES.decryptDES(str, "43215768");
        byte[] data = Base64.decode(str);
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object object = ois.readObject();
        return object;
    }

    public static int getScreenRotation(Context context, int orientation) {
        int screenRotation = 0;
        boolean isPortraitScreen = context.getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        if (orientation >= 315 || orientation < 45) {
            screenRotation = isPortraitScreen ? 0 : 90;
        } else if (orientation >= 45 && orientation < 135) {
            screenRotation = isPortraitScreen ? 90 : 180;
        } else if (orientation >= 135 && orientation < 225) {
            screenRotation = isPortraitScreen ? 180 : 270;
        } else if (orientation >= 225 && orientation < 315) {
            screenRotation = isPortraitScreen ? 270 : 0;
        }
        return screenRotation;
    }
}
