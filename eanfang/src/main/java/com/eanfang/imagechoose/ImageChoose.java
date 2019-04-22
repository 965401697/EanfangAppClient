package com.eanfang.imagechoose;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.eanfang.R;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wtt
 * at 2019/4/18
 * summary:
 */
public class ImageChoose implements IImageChoose {

    /**
     * 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
     */
    private int chooseMode = PictureMimeType.ofImage();
    /**
     * 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
     */
    private int themeId = R.style.picture_default_style;
    /**
     * 最大图片选择数量
     */
    private int maxSelectNum = 9;

    /**
     * 最小选择数量
     */
    private int minSelectNum = 1;

    /**
     * 每行显示个数
     */
    private int imageSpanCount = 4;

    /**
     * 多选 or 单选
     * 多选 PictureConfig.MULTIPLE
     * 单选 PictureConfig.SINGLE
     */
    private int selectionMode = PictureConfig.SINGLE;

    /**
     * 是否可预览图片
     */
    private boolean preview_img = true;
    /**
     * 是否显示拍照按钮
     */
    private boolean isCamera = true;
    /**
     * 是否裁剪
     */
    private boolean crop = false;
    /**
     * 是否压缩
     */
    private boolean compress = false;
    /**
     * 同步true或异步false 压缩 默认同步
     */
    private boolean synOrAsy = true;
    /**
     * glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
     */
    private int widch = 160;
    private int height = 160;
    /**
     * 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
     */
    private int aspect_ratio_x = 0;
    private int aspect_ratio_y = 0;
    /**
     * 裁剪框是否可拖拽
     */
    private boolean freeStyleCropEnabled = false;
    /**
     * 是否传入已选图片
     */
    private List<LocalMedia> selectList = new ArrayList<>();

    /**
     * 小于100kb的图片不压缩
     */
    private int imageSize = 100;
    /**
     * 拍照保存图片格式后缀,默认jpeg
     */
    private String imageFormat=PictureMimeType.PNG;
    /**
     * 单独拍照
     */
    private boolean openCamera=true;
    /**
     * 结果回调onActivityResult code
     */
    private int resultCode = PictureConfig.CHOOSE_REQUEST;
    private int RESULT_OK = -1;
    private IImageChooseCallBack iImageChooseCallBack;
    private static IImageChoose iImageChoose;

    public ImageChoose(Builder builder) {
        this.chooseMode = builder.getChooseMode();
        this.selectionMode = builder.getSelectionMode();
        this.maxSelectNum = builder.getMaxSelectNum();
        this.compress = builder.isCompress();
        this.crop = builder.isCrop();
        this.aspect_ratio_x = builder.getAspect_ratio_x();
        this.aspect_ratio_y = builder.getAspect_ratio_y();
        this.freeStyleCropEnabled = builder.isFreeStyleCropEnabled();
        this.themeId = builder.getThemeId();
        this.imageFormat=builder.getImageFormat();
        this.openCamera=builder.isOpenCamera();
    }

    public static IImageChoose getInstance(Builder builder) {
        if (iImageChoose == null) {
            iImageChoose = new ImageChoose(builder);
        }
        return iImageChoose;
    }

    public void setiImageChooseCallBack(IImageChooseCallBack iImageChooseCallBack) {
        this.iImageChooseCallBack = iImageChooseCallBack;
    }

    public IImageChooseCallBack getiImageChooseCallBack() {
        return iImageChooseCallBack;
    }

    private void imageChoose(Activity activity,IImageChooseCallBack iImageChooseCallBack){
        setiImageChooseCallBack(iImageChooseCallBack);
        PictureSelector pictureSelector=   PictureSelector.create(activity);
        imageChoose(pictureSelector);
    }

    private void imageChoose(Fragment fragment,IImageChooseCallBack iImageChooseCallBack){
        setiImageChooseCallBack(iImageChooseCallBack);
        PictureSelector pictureSelector=   PictureSelector.create(fragment);
        imageChoose(pictureSelector);
    }
    private void imageChoose(PictureSelector pictureSelector){
        PictureSelectionModel pictureSelectionModel;
        if(!openCamera){
            pictureSelectionModel=pictureSelector.openGallery(chooseMode);
        }else{
            pictureSelectionModel=  pictureSelector.openCamera(chooseMode);
        }

        pictureSelectionModel
                .theme(themeId)
                .maxSelectNum(maxSelectNum)
                .minSelectNum(minSelectNum)
                .imageSpanCount(imageSpanCount)
                .selectionMode(selectionMode)
                .previewImage(preview_img)
                .isCamera(isCamera)
                .imageFormat(imageFormat)
                .enableCrop(crop)
                .compress(compress)
                .synOrAsy(synOrAsy)
                .glideOverride(widch, height)
                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)
                .freeStyleCropEnabled(freeStyleCropEnabled)
                .selectionMedia(selectList)
                .minimumCompressSize(imageSize)
                .forResult(resultCode);
    }

    @Override
    public void photoChoose(Activity activity, IImageChooseCallBack iImageChooseCallBack) {
        imageChoose(activity, iImageChooseCallBack);
    }

    @Override
    public void photoChoose(Fragment fragment, IImageChooseCallBack iImageChooseCallBack) {
        imageChoose(fragment, iImageChooseCallBack);
    }

    /**
     * 例如 LocalMedia 里面返回三种path
     * 1.media.getPath(); 为原图path
     * 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
     * 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
     * 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() > 0) {
                        if (getiImageChooseCallBack() != null) {
                            getiImageChooseCallBack().onSuccess(selectList);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


}
