package com.eanfang.base.kit.picture;

import android.app.Activity;
import android.content.Intent;
import androidx.fragment.app.Fragment;

/**
 * created by wtt
 * at 2019/4/18
 * summary:
 */
public interface IPicture {
    void photoChoose(Activity activity, IPictureCallBack iImageChooseCallBack);
    void photoChoose(Fragment fragment, IPictureCallBack iImageChooseCallBack);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
