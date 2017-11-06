package com.eanfang.delegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.View;

import com.eanfang.BuildConfig;
import com.eanfang.util.UuidUtil;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jornl on 2017/10/11.
 */

public class BGASortableDelegate implements
        BGASortableNinePhotoLayout.Delegate {

    public static final int REQUEST_CODE_CHOOSE_PHOTO = 9801;
    public static final int REQUEST_CODE_PHOTO_PREVIEW = 9902;
    private Activity activity;

    private List<Integer> codes;


    public BGASortableDelegate(Activity activity, Integer chooseCode, Integer previewCode) {
        this.activity = activity;
        this.codes = Arrays.asList(chooseCode, previewCode);
    }

    public BGASortableDelegate(Activity activity) {
        this.activity = activity;
        this.codes = Arrays.asList(REQUEST_CODE_CHOOSE_PHOTO, REQUEST_CODE_PHOTO_PREVIEW);
    }


    @Override
    public final void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "EanfangPhotoData");
        activity.startActivityForResult(BGAPhotoPickerActivity.newIntent(activity, takePhotoDir, sortableNinePhotoLayout.getMaxItemCount() - sortableNinePhotoLayout.getItemCount(), null, false), codes.get(0));
    }

    @Override
    public final void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        sortableNinePhotoLayout.removeItem(position);
    }

    @Override
    public final void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        activity.startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(activity, sortableNinePhotoLayout.getItemCount(), models, models, position, false), codes.get(1));
    }

}
