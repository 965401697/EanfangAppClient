package com.eanfang.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.eanfang.base.BaseApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/26  15:12
 * @email houzhongzhou@yeah.net
 */

public class BitmapUtil {

    private static Context mContext;
    /**
     * 图片存储目录
     */
    private static String BASE_DCIM = null;

    /**
     * 通过图片路径获取Bitmap
     *
     * @param imgPath
     * @return
     */
    public static Bitmap getBitmap(String imgPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        newOpts.inSampleSize = 2;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }


//    /**
//     * 保存Bitmap到指定路径
//     *
//     * @param bitmap
//     * @param outPath
//     * @throws FileNotFoundException
//     */
//    public static void saveImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
//        try {
//            compressByQuality(bitmap, outPath, 1024 * 3);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static boolean saveLubanImage(Context context, Bitmap bitmap, String outPath) throws FileNotFoundException {
        try {
            mContext = context;
            compressByQuality(bitmap, outPath, 1024 * 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

//    public static int getBitmapBytes(Bitmap bitmap) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {     //API 19
//            return bitmap.getAllocationByteCount();
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
//            return bitmap.getByteCount();
//        }
//        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
//    }


//    /**
//     * 尺寸压缩,按指定分辨率压缩
//     *
//     * @param imgPath
//     * @param pixelW
//     * @param pixelH
//     * @return
//     */
//    public static Bitmap compressByResolution(String imgPath, float pixelW, float pixelH) {
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
//        newOpts.inJustDecodeBounds = true;
//        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
//        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
//        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//        // 想要缩放的目标尺寸
//        float height = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
//        float width = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
//
//        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;//be=1表示不缩放
//        if (w > h && w > height) {//如果宽度大的话根据宽度固定大小缩放
//            be = (int) (w / height);
//        } else if (w < h && h > width) {//如果高度高的话根据宽度固定大小缩放
//            be = (int) (h / width);
//        }
//        if (be <= 0) {
//            be = 1;
//        }
//        newOpts.inSampleSize = be;//设置缩放比例
//        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
//        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
//        return bitmap;
//    }

//    /**
//     * 尺寸压缩Bitmap,按指定分辨率压缩(先对Bitmap进行质量压缩，然后再尺寸压缩)
//     * 一般用于缩略图
//     *
//     * @param image
//     * @param pixelW
//     * @param pixelH
//     * @return
//     */
//    public static Bitmap compressByResolution(Bitmap image, float pixelW, float pixelH) {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
//        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
//            os.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
//        }
//        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
//        newOpts.inJustDecodeBounds = true;
//        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
//        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
//        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//        float height = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
//        float width = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
//        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;//be=1表示不缩放
//        if (w > h && w > width) {//如果宽度大的话根据宽度固定大小缩放
//            be = (int) (w / width);
//        } else if (w < h && h > height) {//如果高度高的话根据宽度固定大小缩放
//            be = (int) (h / height);
//        }
//        if (be <= 0) {
//            be = 1;
//        }
//        newOpts.inSampleSize = be;//设置缩放比例
//        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
//        is = new ByteArrayInputStream(os.toByteArray());
//        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
//        return bitmap;
//    }

//    /**
//     * 通过Bitmap生成缩略图，并保存图片（按分辨率压缩）
//     *
//     * @param image
//     * @param outPath
//     * @param pixelW
//     * @param pixelH
//     * @throws FileNotFoundException
//     */
//    public static void compressByResolution(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
//        Bitmap bitmap = compressByResolution(image, pixelW, pixelH);
//        saveImage(bitmap, outPath);
//    }
//
//    /**
//     * 通过图片路径生成缩略图，并保存图片（按分辨率压缩）
//     *
//     * @param imgPath
//     * @param outPath
//     * @param pixelW
//     * @param pixelH
//     * @param needsDelete 是否删除原图
//     * @throws FileNotFoundException
//     */
//    public static void compressByResolution(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
//        Bitmap bitmap = compressByResolution(imgPath, pixelW, pixelH);
//        saveImage(bitmap, outPath);
//        if (needsDelete) {
//            File file = new File(imgPath);
//            if (file.exists()) {
//                file.delete();
//            }
//        }
//
//    }

    /**
     * 质量压缩Bitmap，指定保存路径，
     *
     * @param image
     * @param outPath
     * @param maxSize 压缩图片大小（Kb）
     * @throws IOException
     */
    public static void compressByQuality(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        int options = 90;
        // 循环判断如果压缩后图片是否大于100kb maxSize,大于继续压
        while (os.toByteArray().length / 1024 > maxSize) {
            os.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
            options -= 10;
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(os.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        saveBitmaps(bitmap, outPath);
    }

    /**
     * 质量压缩，指定保存路径，
     *
     * @param imgPath     图片路径
     * @param outPath     保存路径
     * @param maxSize     压缩图片大小（Kb）
     * @param needsDelete 是否删除原图片
     * @throws IOException
     */
    public static void compressByQuality(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressByQuality(getBitmap(imgPath), outPath, maxSize);
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }


//    /**
//     * 获取旋转后图片
//     *
//     * @param path
//     * @param bitmap
//     * @return
//     */
//    public static Bitmap getAfterBitmap(String path, Bitmap bitmap) {
//        int degree = getPhotoDegree(path);
//        return rotateBitmap(bitmap, degree);
//    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int getPhotoDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private static void saveBitmaps(Bitmap bm, String filePath) {
        BASE_DCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        String mFileName = DateUtil.today() + DateUtil.currentSeconds() + ".png";
        if (Build.BRAND.equals("Xiaomi")) {
            BASE_DCIM = BASE_DCIM + "/Camera/";
        } else {  // Meizu 、Oppo
            BASE_DCIM = BASE_DCIM + "/DCIM";
        }
        if (!FileUtil.exist(BASE_DCIM)) {
            FileUtil.mkdir(BASE_DCIM);
        }
        // 旋转
        Bitmap mBitmap = getRotateBM(bm, filePath);
        File mOldFile = new File(filePath);
        if (mOldFile.exists()) {
            mOldFile.delete();
            mContext.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{filePath});
        }
        File mFile = new File(BASE_DCIM + mFileName);
        try {
            if (mFile.exists()) {
                mFile.delete();
            }
            mFile.createNewFile();
            // 声明输出流
            FileOutputStream outStream = new FileOutputStream(mFile);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 90, outStream);
            outStream.flush();
            outStream.close();
            if (!mBitmap.isRecycled()) {
                mBitmap.recycle();
            }
            if (bm != null && !bm.isRecycled()) {
                bm.recycle();
            }
            // 其次把文件插入到系统图库
            String path = mFile.getAbsolutePath();
            try {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, path);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

                Uri uri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//                MediaStore.Images.Media.insertImage(mContext.getContentResolver(), path, mFile.getName(), null);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                Uri uri = Uri.fromFile(mFile);
                intent.setData(uri);
                BaseApplication.get().sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Bitmap getRotateBM(Bitmap bm, String path) {
        int degree = getPhotoDegree(path);
        if (degree != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            Bitmap mBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            return mBitmap;
        } else {
            return bm;
        }

    }

//    /**
//     * 旋转图片
//     *
//     * @param angle
//     * @param bitmap
//     * @return Bitmap
//     */
//    public static Bitmap rotateBitmap(Bitmap bitmap, int angle) {
//        //旋转图片 动作
//        Matrix matrix = new Matrix();
//        matrix.postRotate(angle);
//        // 创建新的图片
//        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
//                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        return resizedBitmap;
//    }

//    public static byte[] bitmap2Bytes(Bitmap bm) {
//        if (bm == null) {
//            return null;
//        }
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        return baos.toByteArray();
//    }
//
//    public static Bitmap bytes2Bitmap(byte[] bytes) {
//        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//    }

//
//    /**
//     * Drawable → Bitmap
//     */
//    public static Bitmap drawable2Bitmap(Drawable drawable) {
//        if (drawable == null) {
//            return null;
//        }
//        // 取 drawable 的长宽
//        int w = drawable.getIntrinsicWidth();
//        int h = drawable.getIntrinsicHeight();
//        // 取 drawable 的颜色格式
//        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
//        // 建立对应 bitmap
//        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
//        // 建立对应 bitmap 的画布
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, w, h);
//        // 把 drawable 内容画到画布中
//        drawable.draw(canvas);
//        return bitmap;
//    }
//
//    /*
//     * Bitmap → Drawable
//     */
//    @SuppressWarnings("deprecation")
//    public static Drawable bitmap2Drawable(Bitmap bm) {
//        if (bm == null) {
//            return null;
//        }
//        BitmapDrawable bd = new BitmapDrawable(bm);
//        bd.setTargetDensity(bm.getDensity());
//        return new BitmapDrawable(bm);
//    }
}
