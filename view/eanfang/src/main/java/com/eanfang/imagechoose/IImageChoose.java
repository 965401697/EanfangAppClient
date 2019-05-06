package com.eanfang.imagechoose;

import android.app.Activity;
import android.content.Intent;
import androidx.fragment.app.Fragment;

/**
 * created by wtt
 * at 2019/4/18
 * summary:
 */
public interface IImageChoose {
    void photoChoose(Activity activity, IImageChooseCallBack iImageChooseCallBack);
    void photoChoose(Fragment fragment, IImageChooseCallBack iImageChooseCallBack);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
