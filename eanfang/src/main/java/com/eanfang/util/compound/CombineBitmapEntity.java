package com.eanfang.util.compound;

/**
 * Created by O u r on 2018/5/14.
 */

public class CombineBitmapEntity {
    public float x;
    public float y;
    public float width;
    public float height;
    public static int devide = 1;
    public int index = -1;

    @Override
    public String toString() {
        return "MyBitmap [x=" + x + ", y=" + y + ", width=" + width
                + ", height=" + height + ", devide=" + devide + ", index="
                + index + "]";
    }
}
