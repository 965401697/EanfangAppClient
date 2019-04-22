package com.eanfang.imagechoose;


import com.eanfang.R;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

/**
 * created by wtt
 * at 2019/4/19
 * summary:
 */
public class Builder {
    /**
     * 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
     */
    private int chooseMode = PictureMimeType.ofImage();
    /**
     * 多选 or 单选
     * 多选 PictureConfig.MULTIPLE
     * 单选 PictureConfig.SINGLE
     */
    private  int selectionMode = PictureConfig.SINGLE;
    /**
     * 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
     */
    private int themeId = R.style.picture_default_style;
    /**
     * 最大图片选择数量
     */
    private int maxSelectNum = 9;
    /**
     * 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
     */
    private int aspect_ratio_x = 0;
    private int aspect_ratio_y = 0;
    /**
     * 是否裁剪
     */
    private  boolean crop = false;
    /**
     * 是否压缩
     */
    private boolean compress = false;
    /**
     * 裁剪框是否可拖拽
     */
    private boolean freeStyleCropEnabled = false;
    /**
     * 拍照保存图片格式后缀,默认jpeg
     */
    private String imageFormat=PictureMimeType.PNG;

    /**
     * 单独拍照
     */
    private boolean openCamera=true;

    public boolean isOpenCamera() {
        return openCamera;
    }

    public Builder setOpenCamera(boolean openCamera) {
        this.openCamera = openCamera;
        return this;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public Builder setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
        return this;
    }

    public Builder setMaxSelectNum(int maxSelectNum) {
        this.maxSelectNum = maxSelectNum;
        return this;
    }

    public Builder setThemeId(int themeId) {
        this.themeId = themeId;
        return this;
    }

    public Builder setAspect_ratio_x_y(int aspect_ratio_x, int aspect_ratio_y) {
        this.aspect_ratio_x = aspect_ratio_x;
        this.aspect_ratio_y = aspect_ratio_y;
        return this;
    }

    public Builder setChooseMode(int chooseMode) {
        this.chooseMode = chooseMode;
        return this;
    }

    public Builder setSelectionMode(int selectionMode) {
        this.selectionMode = selectionMode;
        return this;
    }

    public Builder setCrop(boolean crop) {
        this.crop = crop;
        return this;
    }

    public Builder setCompress(boolean compress) {
        this.compress = compress;
        return this;
    }

    public Builder setFreeStyleCropEnabled(boolean freeStyleCropEnabled) {
        this.freeStyleCropEnabled = freeStyleCropEnabled;
        return this;
    }

    public IImageChoose create(){
        return  ImageChoose.getInstance(this);
    }

    public int getChooseMode() {
        return chooseMode;
    }

    public int getSelectionMode() {
        return selectionMode;
    }

    public int getThemeId() {
        return themeId;
    }

    public int getMaxSelectNum() {
        return maxSelectNum;
    }

    public int getAspect_ratio_x() {
        return aspect_ratio_x;
    }

    public int getAspect_ratio_y() {
        return aspect_ratio_y;
    }

    public boolean isCrop() {
        return crop;
    }

    public boolean isCompress() {
        return compress;
    }

    public boolean isFreeStyleCropEnabled() {
        return freeStyleCropEnabled;
    }
}
