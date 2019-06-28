package com.eanfang.delegate;

import android.os.Environment;
import android.view.View;

import com.eanfang.http.EanfangCallback;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.base.kit.rx.RxPerm;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.AnswerListWithQuestionBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jornl
 * @date 2017/10/11
 */

public class BGASortableDelegate implements
        BGASortableNinePhotoLayout.Delegate {

    public static final int REQUEST_CODE_CHOOSE_PHOTO = 9801;
    public static final int REQUEST_CODE_PHOTO_PREVIEW = 9902;
    private BaseActivity activity;
    private BaseActivityWithTakePhoto activityWithTakePhoto;

    private List<Integer> codes;


    public BGASortableDelegate(BaseActivity activity, Integer chooseCode, Integer previewCode) {
        this.activity = activity;
        this.codes = Arrays.asList(chooseCode, previewCode);
    }

    public BGASortableDelegate(BaseActivityWithTakePhoto activity, Integer chooseCode, Integer previewCode) {
        this.activityWithTakePhoto = activity;
        this.codes = Arrays.asList(chooseCode, previewCode);
    }

    public BGASortableDelegate(BaseActivity activity) {
        this.activity = activity;
        this.codes = Arrays.asList(REQUEST_CODE_CHOOSE_PHOTO, REQUEST_CODE_PHOTO_PREVIEW);
    }

    public BGASortableDelegate(BaseActivityWithTakePhoto activity) {
        this.activityWithTakePhoto = activity;
        this.codes = Arrays.asList(REQUEST_CODE_CHOOSE_PHOTO, REQUEST_CODE_PHOTO_PREVIEW);
    }



    @Override
    public final void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "EanfangPhotoData");
        if (activity != null) {
            RxPerm.get(activity).cameraPerm((isSuccess)->{
                activity.startActivityForResult(BGAPhotoPickerActivity.newIntent(activity, takePhotoDir, sortableNinePhotoLayout.getMaxItemCount() - sortableNinePhotoLayout.getItemCount(), null, false), codes.get(0));
            });
        } else {
            RxPerm.get(activity).cameraPerm((isSuccess)->{
                activityWithTakePhoto.startActivityForResult(BGAPhotoPickerActivity.newIntent(activityWithTakePhoto, takePhotoDir, sortableNinePhotoLayout.getMaxItemCount() - sortableNinePhotoLayout.getItemCount(), null, false), codes.get(0));
            });
        }
    }

    @Override
    public final void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        sortableNinePhotoLayout.removeItem(position);
    }

    @Override
    public final void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        if (activity != null) {
            RxPerm.get(activity).cameraPerm((isSuccess)->{
                activity.startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(activity, sortableNinePhotoLayout.getItemCount(), models, models, position, false), codes.get(1));
            });
        } else {
            RxPerm.get(activity).cameraPerm((isSuccess)->{
                activityWithTakePhoto.startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(activityWithTakePhoto, sortableNinePhotoLayout.getItemCount(), models, models, position, false), codes.get(1));
            });
        }
    }

}
