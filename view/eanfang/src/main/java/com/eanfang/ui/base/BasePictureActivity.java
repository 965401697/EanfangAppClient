package com.eanfang.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.base.kit.picture.PictureManager;
import com.eanfang.base.widget.customview.CircleImageView;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.List;
public class BasePictureActivity extends BaseActivity {

    private AlertDialog.Builder builder;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PictureManager.Builder().create().onActivityResult(requestCode, resultCode, data);
    }



    public void takePhoto(Activity activity, int resultCode, IPictureCallBack onImageChooseCallBack) {
        initDialogs(activity,PictureConfig.SINGLE,1,onImageChooseCallBack);
    }
    public void takePhotos(Activity activity,int resultCode,IPictureCallBack onImageChooseCallBack){
        initDialogs(activity,PictureConfig.MULTIPLE,3,onImageChooseCallBack);
    }

    private void initDialogs(Context context,int mode,int max, IPictureCallBack onImageChooseCallBack) {
        if (!((Activity) context).isFinishing()) {
            builder = new AlertDialog.Builder(this)
                    .setTitle("选择图片：")
                    .setItems(new String[]{"相机", "图库"}, new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 1:
                                    pictureSelect(false,mode,max,onImageChooseCallBack);
                                    break;
                                case 0:
                                    pictureSelect(true,mode,max,onImageChooseCallBack);
                                    break;
                                default:
                                    break;
                            }

                        }
                    });
            builder.create().show();
        }
    }
    private void pictureSelect(boolean openCamera,int mode, int max,IPictureCallBack OnImageChooseCallBack) {
        PictureManager.Builder()
                .setChooseMode(PictureMimeType.ofImage())
                .setOpenCamera(openCamera)
                .setSelectionMode(mode)
                .setMaxSelectNum(max)
                .setCrop(true)
                .create()
                .photoChoose(this, new IPictureCallBack() {
                    @Override
                    public void onSuccess(List<LocalMedia> list) {
                        if (list != null && list.size() > 0) {
                            if (OnImageChooseCallBack != null) {
                                OnImageChooseCallBack.onSuccess(list);
                            }
                        }
                    }
                });
    }

    public void videoSelect(boolean openCamera,IPictureCallBack OnImageChooseCallBack) {
        PictureManager.Builder()
                .setChooseMode(PictureMimeType.ofVideo())
                .setOpenCamera(openCamera)
                .setSelectionMode(PictureConfig.SINGLE)
                .setMaxSelectNum(1)
                .setCrop(true)
                .create()
                .photoChoose(this, new IPictureCallBack() {
                    @Override
                    public void onSuccess(List<LocalMedia> list) {
                        if (list != null && list.size() > 0) {
                            if (OnImageChooseCallBack != null) {
                                OnImageChooseCallBack.onSuccess(list);
                            }
                        }
                    }
                });
    }
    public void headViewSize(CircleImageView circleImageView, int size) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) circleImageView.getLayoutParams();
        layoutParams.width = size;
        layoutParams.height = size;
    }

}
