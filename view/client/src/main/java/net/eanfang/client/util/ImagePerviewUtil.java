package net.eanfang.client.util;

import android.content.Context;
import android.content.Intent;

import net.eanfang.client.ui.activity.MyBGAPhotoPickerPreviewActivity;

import java.util.ArrayList;


/**
 * Created by wen on 2017/6/18.
 */

public class ImagePerviewUtil {
    public static void perviewImage(Context context, ArrayList<String> images) {
        if (images == null || images.isEmpty()) {
            return;
        }
        Intent intent = MyBGAPhotoPickerPreviewActivity.newIntent(context, images.size(), images, images, 0, false);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void perviewImage(Context context, ArrayList<String> images, int currentPosition) {
        if (images == null || images.isEmpty()) {
            return;
        }
        Intent intent = MyBGAPhotoPickerPreviewActivity.newIntent(context, images.size(), images, images, currentPosition, false);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
