package com.eanfang.base.kit.picture.picture;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.base.kit.BuildConfig;
import com.eanfang.base.kit.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.StrUtil;

public class PictureInvoking {
    private Activity activity;
    private RecyclerView recyclerView;
    private List<LocalMedia> list;
    private GridImageAdapter adapter;
    private int themeId;
    private int type = 0;

    public void setType(int type) {
        this.type = type;
    }

    public PictureInvoking(Activity activity, RecyclerView recyclerView, List<LocalMedia> list) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.list = list;
    }

    public PictureInvoking(Activity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    public void initRecycle(int maxSelectNum, GridImageAdapter.onAddPicClickListener mOnAddPicClickListener) {
        adapter = new GridImageAdapter(activity, mOnAddPicClickListener);
        init(maxSelectNum);

    }

    public void initRecycle(int maxSelectNum, int size, GridImageAdapter.onAddPicClickListener mOnAddPicClickListener) {
        adapter = new GridImageAdapter(activity, size, mOnAddPicClickListener);
        init(maxSelectNum);
    }

    public void initRecycle(int maxSelectNum, int size, boolean isShow, GridImageAdapter.onAddPicClickListener mOnAddPicClickListener) {
        adapter = new GridImageAdapter(activity, size, isShow, mOnAddPicClickListener);
        init(maxSelectNum);
    }

    public void init(int maxSelectNum) {
        themeId = R.style.picture_default_style;
        // FullyGridLayoutManager manager = new FullyGridLayoutManager(AddTroubleActivity.this, 3, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager manager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        adapter.setList(list);
        adapter.setSelectMax(maxSelectNum);
        adapter.setType(type);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(listener);
    }

    GridImageAdapter.OnItemClickListener listener = new GridImageAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, View v) {
            if (list.size() > 0) {
                LocalMedia media = list.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片 可自定长按保存路径
                        //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                        PictureSelector.create(activity).themeStyle(themeId).openExternalPreview(position, list);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create(activity).externalPictureVideo(media.getPath());
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create(activity).externalPictureAudio(media.getPath());
                        break;
                }
            }
        }
    };

    public void setList(List<LocalMedia> list) {
        this.list = list;
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    public void isShow(boolean isShow) {
        adapter.isShow(isShow);
    }

    public void setData(String str, int type) {
        if (StrUtil.isEmpty(str)) {
            return;
        }
        list = new ArrayList<>();
        setImage(str, type);

    }

    public void setImage(String str, int type) {
        if (StrUtil.isEmpty(str)) {
            return;
        }
        String[] images = str.split(",");
        for (int i = 0; i < images.length; i++) {
            LocalMedia localMedi = new LocalMedia();
            localMedi.setPath(BuildConfig.OSS_SERVER + images[i]);
            localMedi.setPictureType(type + "");
            list.add(localMedi);
        }
        setList(list);
    }

}
