package com.eanfang.util.compound;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.eanfang.BuildConfig;
import com.eanfang.model.GroupDetailBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by O u r on 2018/5/15.
 * bitmap 生成帮助类
 */

public class CompoundHelper {


    private static final int CPU_COUNT = 4;
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1; //corePoolSize为CPU数加1
    private static final int MAX_POOL_SIZE = 2 * CPU_COUNT + 1; //maxPoolSize为2倍的CPU数加1
    private static final long KEEP_ALIVE = 5L; //存活时间为5s
    private static final Executor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private Bitmap mBitmap;
    private FileOutputStream out;
    private int num = 0;
    private List<Bitmap> bitmapList = new ArrayList<>();
    private static CompoundHelper compoundHelper = null;

    public CompoundHelper() {
    }

    public static CompoundHelper getInstance() {
        if (compoundHelper == null) {
            compoundHelper = new CompoundHelper();
        }
        return compoundHelper;
    }


    public void sendBitmap(Context context, Handler handler, GroupDetailBean bean) {

        if (bitmapList.size() > 0) bitmapList.clear();

        num = bean.getList().size();

        for (GroupDetailBean.ListBean b : bean.getList()) {

            //生成图片
            Glide.with(context).load(BuildConfig.OSS_SERVER + b.getAccountEntity().getAvatar()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                    synchronized (this) {
                        if (resource != null)
                            bitmapList.add(resource);
                        num--;
                        if (num == 0) {

                            Runnable loadBitmapTask = new Runnable() {

                                @Override
                                public void run() {

                                    mBitmap = CombineBitmapTools.combimeBitmap(context, 100, 100, bitmapList);

                                    File file = new File(Environment.getExternalStorageDirectory() + "/RY");
                                    if (!file.exists()) file.mkdir();
                                    File f = new File(Environment.getExternalStorageDirectory() + "/RY/" + System.currentTimeMillis() + ".jpg");
                                    try {
                                        out = new FileOutputStream(f);
                                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        out.flush();
                                        out.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    String path = "file://" + f.getAbsolutePath();

                                    Message msg = handler.obtainMessage();
                                    msg.obj = path;
                                    handler.sendMessage(msg);


                                }
                            };
                            threadPoolExecutor.execute(loadBitmapTask);
                        }
                    }
                }
            });

        }
    }


    public void sendBitmap(Context context, Handler handler, List<String> list) {

        if (bitmapList.size() > 0) bitmapList.clear();

        num = list.size();

        for (String b : list) {

            //生成图片
            Glide.with(context).load(BuildConfig.OSS_SERVER + b).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                    synchronized (this) {
                        if (resource != null)
                            bitmapList.add(resource);
                        num--;
                        if (num == 0) {

                            Runnable loadBitmapTask = new Runnable() {

                                @Override
                                public void run() {

                                    mBitmap = CombineBitmapTools.combimeBitmap(context, 100, 100, bitmapList);

                                    File file = new File(Environment.getExternalStorageDirectory() + "/RY");
                                    if (!file.exists()) file.mkdir();
                                    File f = new File(Environment.getExternalStorageDirectory() + "/RY/" + System.currentTimeMillis() + ".jpg");
                                    try {
                                        out = new FileOutputStream(f);
                                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        out.flush();
                                        out.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    String path = f.getAbsolutePath();

                                    Message msg = handler.obtainMessage();
                                    msg.obj = path;
                                    handler.sendMessage(msg);

                                }
                            };
                            threadPoolExecutor.execute(loadBitmapTask);
                        }
                    }
                }
            });

        }
    }

}
