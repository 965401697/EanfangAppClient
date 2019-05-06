package com.eanfang.kit.sdk.alisdk.alioss.utils;


import android.content.Context;

import com.annimon.stream.function.Consumer;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class LubanUtils {

    public static void compress(Context context, List<String> urls, Consumer start, Consumer success, Consumer fail) {
        String path = context.getFilesDir().getAbsolutePath() + "/tmp/img";
        FileUtils.initDirectory(path);
        Luban.with(context)
                .load(urls)                                   // 传人要压缩的图片列表
                .ignoreBy(50)                                  // 忽略不压缩图片的大小
                .setTargetDir(path)                             // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        if (start != null) {
                            start.accept(null);
                        }
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (success != null) {
                            success.accept(file.getAbsolutePath());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (fail != null) {
                            fail.accept(e);
                        }
                    }
                }).launch();    //启动压缩

    }

    public static void compress(Context context, String url, Consumer<String> success) {
        compress(context, Arrays.asList(url), null, success, null);
    }

    public static void compress(Context context, String url, Consumer<String> success, Consumer<Throwable> fail) {
        compress(context, Arrays.asList(url), null, success, fail);
    }
}
