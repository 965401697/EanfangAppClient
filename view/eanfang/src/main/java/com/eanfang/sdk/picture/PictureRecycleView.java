package com.eanfang.sdk.picture;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.base.kit.picture.PictureSelect;
import com.eanfang.util.StringUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

public class PictureRecycleView extends RecyclerView {
    private Context context;
    private List<LocalMedia> list;
    private PictureAdapter adapter;

    public PictureRecycleView(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PictureRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PictureRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public void init() {
        list = new ArrayList<>();
        setLayoutManager(new GridLayoutManager(context, 3));
        adapter = new PictureAdapter(context, R.layout.gv_filter_image, list);
        setAdapter(adapter);
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
        adapter.setList(list);
        adapter.setNewData(list);
    }

    public void setData(String str, int type) {
        if(StringUtils.isEmpty(str)){
            return;
        }
        String[] images = str.split(",");
        for (int i = 0; i < images.length; i++) {
            LocalMedia localMedi = new LocalMedia();
            localMedi.setPath(BuildConfig.OSS_SERVER +images[i]+".jpg");
            localMedi.setPictureType(type+"");
            list.add(localMedi);
        }
        setList(list);

    }

}
