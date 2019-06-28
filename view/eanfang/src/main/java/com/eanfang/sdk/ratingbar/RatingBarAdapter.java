package com.eanfang.sdk.ratingbar;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.R;
import com.eanfang.base.network.config.HttpConfig;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RatingBarAdapter extends BaseQuickAdapter<RatingBarBean, BaseViewHolder> {
    public RatingBarAdapter(int layoutResId, @Nullable List<RatingBarBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, RatingBarBean item) {
        helper.setText(R.id.tvcontent,item.getTvContent());
        MaterialRatingBar ratingBar= helper.getView(R.id.rb_star);
        ratingBar.setNumStars(item.getStartNum());
        switch (HttpConfig.get().getApp()) {
            case 0:
                ratingBar.setProgressTintList(ColorStateList.valueOf(R.color.preson_leave_manager));
                break;
            case 1:
                ratingBar.setProgressTintList(ColorStateList.valueOf(R.color.color_worker_data_item_title));
                break;
        }

    }
}
