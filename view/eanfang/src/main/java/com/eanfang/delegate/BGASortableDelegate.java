package com.eanfang.delegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.View;

import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.config.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

/**
 * @author jornl
 * @date 2017/10/11
 */

public class BGASortableDelegate implements BGASortableNinePhotoLayout.Delegate {

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

    public final void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Config.Cache.IMG_STORAGE_DIR);
        if (activity != null) {
            RxPerm.get(activity).cameraPerm((result) -> {
                BGAPhotoPickerActivity.IntentBuilder builder = new BGAPhotoPickerActivity.IntentBuilder(activity);
                Intent intent = builder.cameraFileDir(takePhotoDir)
                        .maxChooseCount(sortableNinePhotoLayout.getMaxItemCount() - sortableNinePhotoLayout.getItemCount())
                        .pauseOnScroll(false)
                        .selectedPhotos(models)
                        .build();
                activity.startActivityForResult(intent, codes.get(0));
            });
        }
    }

    public final void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        sortableNinePhotoLayout.removeItem(position);
    }

    public final void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        if (activity != null) {
            RxPerm.get(activity).cameraPerm((result) -> {
                BGAPhotoPickerPreviewActivity.IntentBuilder builder = new BGAPhotoPickerPreviewActivity.IntentBuilder(activity);
                Intent intent = builder.maxChooseCount(sortableNinePhotoLayout.getItemCount())
                        .currentPosition(position)
                        .isFromTakePhoto(false)
                        .previewPhotos(models)
                        .selectedPhotos(models)
                        .build();
                activity.startActivityForResult(intent, codes.get(1));
            });
        }
    }

    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

    }

}
