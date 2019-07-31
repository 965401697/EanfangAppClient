package com.eanfang.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eanfang.R;

public class GlideUtil {

    private static String TAG = "GlideUtil";

    public GlideUtil() {
    }

    private static class GlideLoadUtilsHolder {
        private final static GlideUtil INSTANCE = new GlideUtil();
    }

    public static GlideUtil getInstance() {
        return GlideLoadUtilsHolder.INSTANCE;
    }

    public static void intoImageView(Context context, Uri uri, ImageView imageView) {
        if (context != null) {
            Glide.with(context).load(uri).apply(getOptions()).into(imageView);
        } else {
            Log.e(TAG, "context is null");
        }
    }

    public static void intoImageView(Context context, Uri uri, ImageView imageView, int roundingRadius) {
        if (context != null) {
            Glide.with(context).load(uri).apply(getOptions(roundingRadius)).into(imageView);
        } else {
            Log.e(TAG, "context is null");
        }
    }

    public static void intoImageView(Context context, String path, ImageView imageView) {
        if (context != null) {
            Glide.with(context).load(path).apply(getOptions()).into(imageView);
        } else {
            Log.e(TAG, "context is null");
        }
    }

    public static void intoImageView(Context context, String path, ImageView imageView, int roundingRadius) {
        if (context != null) {
            Glide.with(context).load(path).apply(getOptions(roundingRadius)).into(imageView);
        } else {
            Log.e(TAG, "context is null");
        }
    }

    public static void intoImageView(Context context, Bitmap path, ImageView imageView) {
        if (context != null) {
            Glide.with(context).load(path).apply(getOptions()).into(imageView);
        } else {
            Log.e(TAG, "context is null");
        }
    }

    public static void intoImageView(Context context, Bitmap path, ImageView imageView, int roundingRadius) {
        if (context != null) {
            Glide.with(context).load(path).apply(getOptions(roundingRadius)).into(imageView);
        } else {
            Log.e(TAG, "context is null");
        }
    }

    public static void intoImageView(Context context, int path, ImageView imageView) {
        if (context != null) {
            Glide.with(context).load(path).apply(getOptions()).into(imageView);
        } else {
            Log.e(TAG, "context is null");
        }
    }

    public static void intoImageView(Context context, int path, ImageView imageView, int roundingRadius) {
        if (context != null) {
            Glide.with(context).load(path).apply(getOptions(roundingRadius)).into(imageView);
        } else {
            Log.e(TAG, "context is null");
        }
    }

    private static RequestOptions getOptions() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .fitCenter()
                .dontAnimate()
                .error(R.mipmap.ic_nodata);
        return options;
    }

    private static RequestOptions getOptions(int roundingRadius) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_nodata)
                .error(R.mipmap.ic_nodata)
                .bitmapTransform(new RoundedCorners(roundingRadius));
        return options;
    }
}
