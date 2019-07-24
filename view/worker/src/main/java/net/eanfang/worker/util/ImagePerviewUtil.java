package net.eanfang.worker.util;

import android.content.Context;
import android.content.Intent;

import net.eanfang.worker.ui.activity.MyBGAPhotoPickerPreviewActivity;

import java.util.ArrayList;


/**
 * Created by wen on 2017/6/18.
 */

public class ImagePerviewUtil {
    public static void perviewImage(Context context, ArrayList<String> images) {
        Intent intent = MyBGAPhotoPickerPreviewActivity.newIntent(context, images.size(), images, images, 0, false);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void perviewImage(Context context, ArrayList<String> images, int currentPosition) {
        Intent intent = MyBGAPhotoPickerPreviewActivity.newIntent(context, images.size(), images, images, currentPosition, false);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
