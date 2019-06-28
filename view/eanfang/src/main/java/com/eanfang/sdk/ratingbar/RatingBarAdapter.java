package com.eanfang.sdk.ratingbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.R;
import com.eanfang.base.network.config.HttpConfig;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RatingBarAdapter extends BaseQuickAdapter<RatingBarBean, BaseViewHolder> {
    private Context context;
    public RatingBarAdapter(Context context,int layoutResId, @Nullable List<RatingBarBean> data) {
        super(layoutResId, data);
        this.context=context;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, RatingBarBean item) {
        helper.setText(R.id.tvcontent,item.getTvContent());
        MaterialRatingBar ratingBar= helper.getView(R.id.rb_star);
        ratingBar.setRating(item.getRatingNum());
//        ratingBar.setIsIndicator(item.isIndicator());
        switch (HttpConfig.get().getApp()) {
            case 0:
               ColorStateList colorStateList=context.getResources().getColorStateList(R.color.preson_leave_manager);
                ratingBar.setProgressTintList(colorStateList);
                break;
            case 1:
                 ColorStateList colorStateList1=context.getResources().getColorStateList(R.color.color_worker_data_item_title);
                ratingBar.setProgressTintList(colorStateList1);
                break;
        }

        ratingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
               if(getmOnRatingChangeListene()!=null){
                   getmOnRatingChangeListene().onRatingChanged(ratingBar,rating,helper.getLayoutPosition());
               }
            }
        });

    }
    OnRatingChangeListene mOnRatingChangeListene;
    public interface OnRatingChangeListene{
        void onRatingChanged(MaterialRatingBar ratingBar, float rating,int position);
    }

    public OnRatingChangeListene getmOnRatingChangeListene() {
        return mOnRatingChangeListene;
    }

    public void setmOnRatingChangeListene(OnRatingChangeListene mOnRatingChangeListene) {
        this.mOnRatingChangeListene = mOnRatingChangeListene;
    }
}
