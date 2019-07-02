package com.eanfang.sdk.picture;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.camera.util.ScreenSizeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.R;
import com.eanfang.util.GlideUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;

import java.util.List;

public class PictureAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> {
    private Context context;
    private int themeId;
    private List<LocalMedia> list;

    public PictureAdapter(Context context, int layoutResId, @Nullable List<LocalMedia> data) {
        super(layoutResId, data);
        this.context = context;
        this.list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalMedia item) {
        //初始化参数
        themeId = R.style.picture_default_style;
        int width= (ScreenSizeUtil.getScreenWidth()-120)/3;
        helper.getView(R.id.ll_del).setVisibility(View.GONE);
        TextView tvDuration = helper.getView(R.id.tv_duration);
        RelativeLayout relativeLayout = helper.getView(R.id.relative);
        RelativeLayout rl=helper.getView(R.id.rl);
        rl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ImageView imageView = helper.getView(R.id.fiv);
        RelativeLayout.LayoutParams imgeParams= (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        imgeParams.width=width;
        imgeParams.height=width;
        params.width=width;
        params.height=width;
        params.topMargin=10;
        params.bottomMargin=10;
        //图片展示
        GlideUtil.intoImageView(context, item.getPath(), imageView);
        //判断是否是视频或音频，相关页面展示
        int pictureType = PictureMimeType.isPictureType(item.getPictureType());
        long duration = item.getDuration();
        tvDuration.setVisibility(pictureType == PictureConfig.TYPE_VIDEO
                ? View.VISIBLE : View.GONE);
        int mimeType = item.getMimeType();
        if (mimeType == PictureMimeType.ofAudio()) {
            tvDuration.setVisibility(View.VISIBLE);
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.picture_audio);
            StringUtils.modifyTextViewDrawable(tvDuration, drawable, 0);
        } else {
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.video_icon);
            StringUtils.modifyTextViewDrawable(helper.getView(R.id.tv_duration), drawable, 0);
        }
        tvDuration.setText(DateUtils.timeParse(duration));
        if (mimeType == PictureMimeType.ofAudio()) {
            imageView.setImageResource(R.drawable.audio_placeholder);
        } else {
            GlideUtil.intoImageView(context, item.getPath(), imageView);
        }
        //点击查看大图

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pictureType = item.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片 可自定长按保存路径
                        //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                        PictureSelector.create((Activity) context).themeStyle(themeId).openExternalPreview(helper.getLayoutPosition(), list);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create((Activity) context).externalPictureVideo(item.getPath());
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create((Activity) context).externalPictureAudio(item.getPath());
                        break;
                    default:
                        PictureSelector.create((Activity) context).themeStyle(themeId).openExternalPreview(helper.getLayoutPosition(), list);
                        break;
                }
            }
        });
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
    }

}
