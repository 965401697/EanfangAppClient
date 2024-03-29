package net.eanfang.worker.util;

import android.content.Context;

import com.eanfang.util.StringUtils;

import java.util.ArrayList;

import cn.hutool.core.util.StrUtil;

/**
 * Created by Mr.hou
 *
 * @on 2017/8/11  17:27
 * @email houzhongzhou@yeah.net
 * @desc 点击图片时放大
 */

public class PerviewUtil {
    public static void perviewImageUitl(String url, Context activity) {
        ArrayList<String> images = new ArrayList<String>();
        if (StrUtil.isEmpty(url)) {
            return;
        }
        images.add(url);
        ImagePerviewUtil.perviewImage(activity, images);
    }
}
