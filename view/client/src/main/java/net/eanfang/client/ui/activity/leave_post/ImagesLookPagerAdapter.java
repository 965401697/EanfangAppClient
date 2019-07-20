package net.eanfang.client.ui.activity.leave_post;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.eanfang.BuildConfig;
import com.photopicker.photoview.PhotoView;

import java.util.ArrayList;

import lombok.NonNull;

public class ImagesLookPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> mList;

    public ImagesLookPagerAdapter(Context context, ArrayList<String> mList) {
        this.context = context;
        this.mList = mList;
    }
 
    @Override
    public int getCount() {
        return mList.size();
    }
 
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }
 
    private boolean isFull = false;
 
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        if (mList != null && position < mList.size()) {
            String resId = mList.get(position);
            if (resId != null) {
                PhotoView popupImages = new PhotoView(context);
 
                Glide.with(context).load(BuildConfig.OSS_SERVER + mList.get(position)).into(popupImages);
                popupImages.setOnClickListener(v -> {
                    if (!isFull){
                        isFull = true;
                        ImagesLook.getInstance().closeActionbar();
                    }else {
                        isFull = false;
                        ImagesLook.getInstance().showActionbar();
                    }
                });
 
                container.addView(popupImages);
                return popupImages;
            }
        }
        return null;
    }
 
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (object != null) {
            int count = container.getChildCount();
            for (int i = 0; i < count; i++) {
                View childView = container.getChildAt(i);
                if (childView == object) {
                    container.removeView(childView);
                    break;
                }
            }
        }
    }
}