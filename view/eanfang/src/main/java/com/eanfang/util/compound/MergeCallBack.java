package com.eanfang.util.compound;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by O u r on 2018/5/14.
 */

public interface MergeCallBack {
    //合成的回调
    Bitmap merge(List<Bitmap> bitmapArray, Context context, ImageView imageView);
}
