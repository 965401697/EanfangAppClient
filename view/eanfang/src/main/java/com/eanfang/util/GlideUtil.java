package com.eanfang.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

public class GlideUtil {

    public static void intoImageView(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).into(imageView);
    }

    public static void intoImageView(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
    public static  void intoImageView(Context context, Bitmap path, ImageView imageView){
        Glide.with(context).load(path).into(imageView);
    }
}
