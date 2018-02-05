package com.eanfang.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author hou
 *         Created at 2017/3/2
 * @desc
 */
public class AssetUtil {

    static AssetUtil util = new AssetUtil();
    Context mContext;

    private AssetUtil() {
    }

    public static AssetUtil get() {
        if (util == null) {
            util = new AssetUtil();
        }
        return util;
    }

    public void init(Context context) {
        mContext = context;
    }

    public Bitmap getImageFromAsset(String name) {
        try {
            InputStream in = mContext.getAssets().open("cars/carlogo/" + name + ".png");
            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
