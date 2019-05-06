package com.eanfang.kit.imagechoose;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * created by wtt
 * at 2019/4/18
 * summary:
 */
public interface IImageChooseCallBack {
    void onSuccess(List<LocalMedia> list);
}
