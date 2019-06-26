package com.eanfang.sdk.ratingbar;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.R;

import java.util.ArrayList;
import java.util.List;

public class RatingBarRecycleView extends RecyclerView {
    private Context context;
    private List<RatingBarBean> list;
    private RatingBarAdapter adapter;

    public RatingBarRecycleView(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RatingBarRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public RatingBarRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public void init() {
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(context);
        setLayoutManager(manager);
        adapter = new RatingBarAdapter(context, R.layout.item_ratingbar, list);
        setAdapter(adapter);
    }

    /**
     * 设置数据
     * @param list
     */
    public void setData(List<RatingBarBean> list) {
        this.list = list;
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * Ratingbar改变监听
     * @param mOnRatingChangeListene
     */
    public void setOnRatingChangeListener(RatingBarAdapter.OnRatingChangeListene mOnRatingChangeListene) {
        adapter.setmOnRatingChangeListene(mOnRatingChangeListene);
    }
}
