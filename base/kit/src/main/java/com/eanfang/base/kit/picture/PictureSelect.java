package com.eanfang.base.kit.picture;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;


import com.eanfang.base.kit.R;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * created by wtt
 * at 2019/4/18
 * summary:
 */
@Getter
@Setter
public class PictureSelect {

    /**
     * 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
     */
    public int chooseMode = PictureMimeType.ofImage();
    /**
     * 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
     */
    public int themeId = R.style.picture_default_style;
    /**
     * 最大图片选择数量
     */
    public int maxSelectNum = 3;

    /**
     * 最小选择数量
     */
    public int minSelectNum = 1;

    /**
     * 每行显示个数
     */
    public int imageSpanCount = 4;

    /**
     * 多选 or 单选
     * 多选 PictureConfig.MULTIPLE
     * 单选 PictureConfig.SINGLE
     */
    public int selectionMode = PictureConfig.SINGLE;

    /**
     * 是否可预览图片
     */
    public boolean preview_img = true;
    /**
     * 是否显示拍照按钮
     */
    public boolean isCamera = true;
    /**
     * 是否裁剪
     */
    public boolean crop = false;
    /**
     * 是否压缩
     */
    public boolean compress = false;
    /**
     * 同步true或异步false 压缩 默认同步
     */
    public boolean synOrAsy = true;
    /**
     * glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
     */
    public int widch = 160;
    public int height = 160;
    /**
     * 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
     */
    public int aspect_ratio_x = 0;
    public int aspect_ratio_y = 0;
    /**
     * 裁剪框是否可拖拽
     */
    public boolean freeStyleCropEnabled = false;
    /**
     * 是否传入已选图片
     */
    public List<LocalMedia> selectList = new ArrayList<>();
    public List<LocalMedia> videoList = new ArrayList<>();

    /**
     * 小于100kb的图片不压缩
     */
    public int imageSize = 100;
    /**
     * 拍照保存图片格式后缀,默认jpeg
     */
    public String imageFormat = PictureMimeType.PNG;
    /**
     * 单独拍照
     */
    public boolean openCamera = true;


    /**
     * 结果回调onActivityResult code
     */
    private int resultCode = PictureConfig.CHOOSE_REQUEST;
    private int RESULT_OK = -1;
    private IPictureCallBack iImageChooseCallBack;
    private static PictureSelect iImageChoose;
    private PictureSelector pictureSelector;

    public static PictureSelect getInstance() {
        if (iImageChoose == null) {
            iImageChoose = new PictureSelect();
        }
        return iImageChoose;
    }


    public void setiImageChooseCallBack(IPictureCallBack iImageChooseCallBack) {
        this.iImageChooseCallBack = iImageChooseCallBack;
    }

    public IPictureCallBack getiImageChooseCallBack() {
        return iImageChooseCallBack;
    }

    public PictureSelect create(Activity activity) {
        pictureSelector = PictureSelector.create(activity);
        return this;
    }

    public PictureSelect create(Fragment fragment) {
        pictureSelector = PictureSelector.create(fragment);
        return this;
    }

    /**
     * 单张图片选择
     *
     * @param onImageChooseCallBack
     */
    public void takePhoto(IPictureCallBack onImageChooseCallBack) {
        setSelectList(null);
        pictureSelect(PictureMimeType.ofImage(), false, PictureConfig.SINGLE, 1, onImageChooseCallBack);
    }

    /**
     * 多张图片选择
     *
     * @param onImageChooseCallBack
     */
    public void takePhotos(IPictureCallBack onImageChooseCallBack) {
        pictureSelect(PictureMimeType.ofImage(), false, PictureConfig.MULTIPLE, 3, onImageChooseCallBack);
    }

    /**
     * 选择视频
     *
     * @param onImageChooseCallBack
     */
    public void takeVideo(IPictureCallBack onImageChooseCallBack) {
        pictureSelect(PictureMimeType.ofVideo(), true, PictureConfig.SINGLE, 1, onImageChooseCallBack);
    }

    /**
     * 多选视频
     *
     * @param onImageChooseCallBack
     */
    public void takeVideos(IPictureCallBack onImageChooseCallBack) {
        pictureSelect(PictureMimeType.ofVideo(), false, PictureConfig.MULTIPLE, 3, onImageChooseCallBack);
    }

    private void pictureSelect(int chooseMode, boolean openCamera, int mode, int max, IPictureCallBack OnImageChooseCallBack) {
        setiImageChooseCallBack(OnImageChooseCallBack);
        setChooseMode(chooseMode);
        setOpenCamera(openCamera);
        setSelectionMode(mode);
        setMaxSelectNum(max);
        setCrop(true);
        imageChoose();
    }

    //自定义设置参数后调用
    public void imageChoose(IPictureCallBack OnImageChooseCallBack) {
        setiImageChooseCallBack(OnImageChooseCallBack);
        imageChoose();
    }

    private void imageChoose() {
        PictureSelectionModel pictureSelectionModel;
        if (!openCamera) {
            pictureSelectionModel = pictureSelector.openGallery(chooseMode);
        } else {
            pictureSelectionModel = pictureSelector.openCamera(chooseMode);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    pictureChoose(data);
                    break;
                default:
                    break;
            }
        }
    }

    private void pictureChoose(Intent data) {
        if (chooseMode == PictureMimeType.ofVideo()) {
            videoList = PictureSelector.obtainMultipleResult(data);
            if (videoList != null && videoList.size() > 0) {
                if (getiImageChooseCallBack() != null) {
                    getiImageChooseCallBack().onSuccess(videoList);
                }
            }
            return;
        }
        selectList = PictureSelector.obtainMultipleResult(data);
        if (selectList != null && selectList.size() > 0) {
            if (getiImageChooseCallBack() != null) {
                getiImageChooseCallBack().onSuccess(selectList);
            }
        }
    }

    public PictureSelect setChooseMode(int chooseMode) {
        this.chooseMode = chooseMode;
        return this;
    }

    public PictureSelect setThemeId(int themeId) {
        this.themeId = themeId;
        return this;
    }

    public PictureSelect setMaxSelectNum(int maxSelectNum) {
        this.maxSelectNum = maxSelectNum;
        return this;
    }

    public PictureSelect setMinSelectNum(int minSelectNum) {
        this.minSelectNum = minSelectNum;
        return this;
    }

    public PictureSelect setImageSpanCount(int imageSpanCount) {
        this.imageSpanCount = imageSpanCount;
        return this;
    }

    public PictureSelect setSelectionMode(int selectionMode) {
        this.selectionMode = selectionMode;
        return this;
    }

    public PictureSelect setPreview_img(boolean preview_img) {
        this.preview_img = preview_img;
        return this;
    }

    public PictureSelect setCamera(boolean camera) {
        isCamera = camera;
        return this;
    }

    public PictureSelect setCrop(boolean crop) {
        this.crop = crop;
        return this;
    }

    public PictureSelect setCompress(boolean compress) {
        this.compress = compress;
        return this;
    }

    public PictureSelect setSynOrAsy(boolean synOrAsy) {
        this.synOrAsy = synOrAsy;
        return this;
    }

    public PictureSelect setWidch(int widch) {
        this.widch = widch;
        return this;
    }

    public PictureSelect setHeight(int height) {
        this.height = height;
        return this;
    }

    public PictureSelect setAspect_ratio_x(int aspect_ratio_x) {
        this.aspect_ratio_x = aspect_ratio_x;
        return this;
    }

    public PictureSelect setAspect_ratio_y(int aspect_ratio_y) {
        this.aspect_ratio_y = aspect_ratio_y;
        return this;
    }

    public PictureSelect setFreeStyleCropEnabled(boolean freeStyleCropEnabled) {
        this.freeStyleCropEnabled = freeStyleCropEnabled;
        return this;
    }

    public PictureSelect setSelectList(List<LocalMedia> selectList) {
        this.selectList = selectList;
        return this;
    }

    public PictureSelect setImageSize(int imageSize) {
        this.imageSize = imageSize;
        return this;
    }

    public PictureSelect setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
        return this;
    }

    public PictureSelect setOpenCamera(boolean openCamera) {
        this.openCamera = openCamera;
        return this;
    }
}
