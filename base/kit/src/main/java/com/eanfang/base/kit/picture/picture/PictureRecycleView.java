package com.eanfang.base.kit.picture.picture;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.base.kit.BuildConfig;
import com.eanfang.base.kit.R;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.StrUtil;

public class PictureRecycleView extends RecyclerView {
    private Context context;
    private List<LocalMedia> mList;
    private int themeId;
    private GridImageAdapter adapter;
    private int type = 0;

    /**
     * 是否为添加图片模式
     * ture为Add模式
     * fale为Show模式
     */

    public PictureRecycleView(@NonNull Context context) {
        super(context);
        this.context = context;
        mList = new ArrayList<>();
    }

    public PictureRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mList = new ArrayList<>();
    }

    public PictureRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        mList = new ArrayList<>();
    }

    /**
     * 选择图片
     */
    public void addImagev(ImageListener imageListener) {
        setImageListener(imageListener);
        int width=(ScreenUtils.getScreenWidth(context)-100)/3;
        adapter = new GridImageAdapter(context, width, onAddPicClickListener);
        init();
    }

    /**
     * 展示图片
     */
    public void showImagev(List<LocalMedia> list, ImageListener imageListener) {
        setImageListener(imageListener);
        this.mList = list;
        int width=(ScreenUtils.getScreenWidth(context)-100)/3;
        adapter = new GridImageAdapter(context, width, false, onAddPicClick);
        init();
    }

    /**
     * 切换状态
     *
     * @param isShow
     * @param lis
     */
    public void isShow(boolean isShow, List<LocalMedia> lis) {
        adapter.isShow(isShow);
        setList(lis);
    }

    public void init() {
        themeId = R.style.picture_default_style;
        // FullyGridLayoutManager manager = new FullyGridLayoutManager(AddTroubleActivity.this, 3, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        setLayoutManager(manager);
        adapter.setList(mList);
        adapter.setSelectMax(3);
        adapter.setType(type);
        setAdapter(adapter);
        adapter.setOnItemClickListener(listener);
    }

    GridImageAdapter.OnItemClickListener listener = new GridImageAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, View v) {
            if (mList.size() > 0) {
                LocalMedia media = mList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片 可自定长按保存路径
                        //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                        PictureSelector.create((Activity) context).themeStyle(themeId).openExternalPreview(position, mList);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create((Activity) context).externalPictureVideo(media.getPath());
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create((Activity) context).externalPictureAudio(media.getPath());
                        break;
                }
            }
        }
    };

    public void setList(List<LocalMedia> list) {
        this.mList = list;
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    public List<LocalMedia> setData(String str) {
        return setData(str, 1);
    }

    private List<LocalMedia> setData(String str, int type) {
        List<LocalMedia> select = new ArrayList<>();
        if (StrUtil.isEmpty(str)) {
            return select;
        }

        String[] images = str.split(",");
        for (int i = 0; i < images.length; i++) {
            LocalMedia localMedi = new LocalMedia();
            localMedi.setPath(BuildConfig.OSS_SERVER + images[i]);
            localMedi.setPictureType(type + "");
            select.add(localMedi);
        }
        return select;
    }


    GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            SDKManager.getPicture().create((Activity) context)
                    .setSelectList(mList)
                    .takePhotos(list -> {
                        //选择图片成功之后的逻辑处理
                        mList = list;
                        setList(mList);
                        if (getImageListener() != null) {
                            getImageListener().imageData(list);
                        }
                    });
        }
    };
    GridImageAdapter.onAddPicClickListener onAddPicClick = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            SDKManager.getPicture().create((Activity) context).takePhoto(new IPictureCallBack() {
                @Override
                public void onSuccess(List<LocalMedia> list) {
                    //选择图片成功之后的逻辑处理
                    if (mList.size() < 3) {
                        mList.add(list.get(0));
                        setList(mList);
                        if (getImageListener() != null) {
                            getImageListener().imageData(mList);
                        }
                    }
                }
            });
        }
    };
    private ImageListener ImageListener;

    public interface ImageListener {
        void imageData(List<LocalMedia> list);
    }

    public ImageListener getImageListener() {
        return ImageListener;
    }

    public void setImageListener(ImageListener mImageListener) {
        this.ImageListener = mImageListener;
    }
}
