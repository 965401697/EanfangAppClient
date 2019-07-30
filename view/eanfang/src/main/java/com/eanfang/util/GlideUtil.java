package com.eanfang.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eanfang.R;

public class GlideUtil {

    public static void intoImageView(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).apply(getOptions()).into(imageView);
    }

    public static void intoImageView(Context context, Uri uri, ImageView imageView, int roundingRadius) {
        Glide.with(context).load(uri).apply(getOptions(roundingRadius)).into(imageView);
    }

    public static void intoImageView(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).apply(getOptions()).into(imageView);
    }

    public static void intoImageView(Context context, String path, ImageView imageView, int roundingRadius) {
        Glide.with(context).load(path).apply(getOptions(roundingRadius)).into(imageView);
    }

    public static void intoImageView(Context context, Bitmap path, ImageView imageView) {
        Glide.with(context).load(path).apply(getOptions()).into(imageView);
    }

    public static void intoImageView(Context context, Bitmap path, ImageView imageView, int roundingRadius) {
        Glide.with(context).load(path).apply(getOptions(roundingRadius)).into(imageView);
    }

    public static void intoImageView(Context context, int path, ImageView imageView) {

        Glide.with(context).load(path).apply(getOptions()).into(imageView);
    }

    public static void intoImageView(Context context, int path, ImageView imageView, int roundingRadius) {

        Glide.with(context).load(path).apply(getOptions(roundingRadius)).into(imageView);
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
