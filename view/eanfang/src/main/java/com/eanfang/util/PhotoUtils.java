package com.eanfang.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import com.eanfang.BuildConfig;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.base.network.holder.ContextHolder;
import com.eanfang.config.Config;
import com.luck.picture.lib.entity.LocalMedia;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;

/**
 * 照片相关的 工具类
 * Created by jornl on 2017/10/11.
 */
public class PhotoUtils {

    /**
     * 获取 BGA控件中的 图片信息
     *
     * @param uploadType  前缀
     * @param mPhotosSnpl BGASortableNinePhotoLayout
     * @param uploadMap   uploadMap
     * @param clean       是否清除map 重新计算
     * @return String
     */
    public static String getPhotoUrl(String uploadType, BGASortableNinePhotoLayout mPhotosSnpl, Map<String, String> uploadMap, boolean clean) {
        if (uploadMap == null) {
            uploadMap = new HashMap<>();
        }
        if (clean) {
            uploadMap.clear();
        }
        StringBuilder urls = new StringBuilder();

        for (int i = 0; i < mPhotosSnpl.getData().size(); i++) {
            String path = mPhotosSnpl.getData().get(i);
            String object = uploadType + StrUtil.uuid() + ".png";
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
        }
        return urls.toString();
    }

    /**
     * 获取  List<LocalMedia> 中的 图片信息
     *
     * @param uploadType 前缀
     * @param selectList selectList
     * @param uploadMap  uploadMap
     * @param clean      是否清除map 重新计算
     * @return String
     */
    public static String getPhotoUrl(String uploadType, List<LocalMedia> selectList, Map<String, String> uploadMap, boolean clean) {
        if (uploadMap == null) {
            uploadMap = new HashMap<>();
        }
        if (clean) {
            uploadMap.clear();
        }
        StringBuilder urls = new StringBuilder();

        for (int i = 0; i < selectList.size(); i++) {
            String path = selectList.get(i).getPath();
            String object = uploadType + StrUtil.uuid() + ".png";
            if (path == null || path.length() <= 0) {
                continue;
            } else if (path.startsWith(BuildConfig.OSS_SERVER)) {
                urls.append(path.replace(BuildConfig.OSS_SERVER, ""));
            } else {
                uploadMap.put(object, path);
                urls.append(object);
            }
            if (i < selectList.size() - 1) {
                urls.append(",");
            }
        }
        return urls.toString();
    }

    /**
     * 获取视频的缩略图 Bitmap
     *
     * @param videoPath 视频的路径
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoBitmap(String videoPath) {
        Bitmap bitmap;
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);// videoPath 本地视频的路径

        bitmap = media.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);

        File file = new File(Config.Cache.VIDEO_IMG_DIR + StrUtil.uuid() + ".jpg");
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取视频的缩略图
//        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind); //調用ThumbnailUtils類的靜態方法createVideoThumbnail獲取視頻的截圖；
//        if(bitmap!= null){
//            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
//                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//調用ThumbnailUtils類的靜態方法extractThumbnail將原圖片（即上方截取的圖片）轉化為指定大小；
//        }
        return bitmap;
    }

    /**
     * 直接获取视频的缩略图 Bitmap
     *
     * @param videoPath videoPath
     * @return File
     */
    public static File getVideoFile(String videoPath) {
        Bitmap bitmap = getVideoBitmap(videoPath);
        return bitmapToFile(bitmap);
    }

    /**
     * Bitmap 转 file
     *
     * @param bitmap bitmap
     * @return File
     */
    public static File bitmapToFile(Bitmap bitmap) {
        File file = new File(Config.Cache.VIDEO_IMG_DIR + StrUtil.uuid() + ".jpg");
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 从oss服务器上下载图片到本地
     *
     * @param ossKey ossKey
     * @return File
     */
    public static File downloadImg(String ossKey) {
        String url = BuildConfig.OSS_SERVER + ossKey;
        File file = new File(Config.Cache.IMG_STORAGE_DIR + StrUtil.uuid() + ".jpg");
        HttpUtil.downloadFile(url, file);
        return file;
    }
}
