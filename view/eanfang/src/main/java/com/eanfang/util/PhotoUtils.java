package com.eanfang.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import com.eanfang.BuildConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 照片相关的 工具类
 * Created by jornl on 2017/10/11.
 */

public class PhotoUtils {

    /**
     * 获取 BGA控件中的 图片信息
     *
     * @param uploadType
     * @param mPhotosSnpl
     * @param uploadMap
     * @param clean       是否清除map 重新计算
     * @return
     */
    public static String getPhotoUrl(String uploadType, BGASortableNinePhotoLayout mPhotosSnpl, Map<String, String> uploadMap, boolean clean) {
        if (uploadMap == null) {
            uploadMap = new HashMap<>();
        }
        if (clean) {
            uploadMap.clear();
        }
//        List<String> urls = new ArrayList<>();
        StringBuilder urls = new StringBuilder();

        for (int i = 0; i < mPhotosSnpl.getData().size(); i++) {
            String path = mPhotosSnpl.getData().get(i);
            String object = uploadType + UuidUtil.getUUID() + ".png";
            if (path == null || path.length() <= 0) {
                continue;
            } else if (path.startsWith(BuildConfig.OSS_SERVER)) {
                urls.append(path.replace(BuildConfig.OSS_SERVER, ""));
            } else {
                uploadMap.put(object, path);
                urls.append(object);
            }
            if (i < mPhotosSnpl.getData().size() - 1) {
                urls.append(",");
            }
//            urls.add(BuildConfig.OSS_SERVER + object);
        }
        return urls.toString();
    }

    public static String getPhotoUrl(String uploadType, List<LocalMedia> selectList, Map<String, String> uploadMap, boolean clean) {
        if (uploadMap == null) {
            uploadMap = new HashMap<>();
        }
        if (clean) {
            uploadMap.clear();
        }
//        List<String> urls = new ArrayList<>();
        StringBuilder urls = new StringBuilder();

        for (int i = 0; i <selectList.size(); i++) {
            String path =selectList.get(i).getPath();
            String object = uploadType + UuidUtil.getUUID() + ".png";
            if (path == null || path.length() <= 0) {
                continue;
            } else if (path.startsWith(BuildConfig.OSS_SERVER)) {
                urls.append(path.replace(BuildConfig.OSS_SERVER, ""));
            } else {
                uploadMap.put(object, path);
                urls.append(object);
            }
            if (i < selectList.size()- 1) {
                urls.append(",");
            }
//            urls.add(BuildConfig.OSS_SERVER + object);
        }
        return urls.toString();
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images(Video).Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);// videoPath 本地视频的路径
        bitmap = media.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        // 获取视频的缩略图
//        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind); //調用ThumbnailUtils類的靜態方法createVideoThumbnail獲取視頻的截圖；
//        if(bitmap!= null){
//            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
//                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//調用ThumbnailUtils類的靜態方法extractThumbnail將原圖片（即上方截取的圖片）轉化為指定大小；
//        }
        return bitmap;
    }

}
