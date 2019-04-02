package net.eanfang.worker.ui.activity.worksapce.oa.check;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.model.WorkAddReportBean;
import com.eanfang.model.WorkTaskInfoBean;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.WorkInspectDetailEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by O u r on 2018/10/19.
 */

public class CheckDetailAdapter extends BaseMultiItemQuickAdapter<WorkInspectDetailEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private Activity mActivity;

    public CheckDetailAdapter(List<WorkInspectDetailEntity> data, Activity activity) {
        super(data);
        this.mActivity = activity;
        addItemType(WorkTaskInfoBean.WorkTaskDetailsBean.FOLD, R.layout.item_check_detail_normal);
        addItemType(WorkTaskInfoBean.WorkTaskDetailsBean.EXPAND, R.layout.item_check_detail_show);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkInspectDetailEntity item) {
        switch (helper.getItemViewType()) {
            case WorkAddReportBean.WorkReportDetailsBean.FOLD:
                helper.setText(R.id.tv_work_content, "明细标题：" + item.getTitle());
                helper.setText(R.id.tv_work_question, "检查内容：" + item.getInfo());
                if (mData.size() - 1 == helper.getAdapterPosition()) {
                    helper.getView(R.id.rl_show).setBackground(mActivity.getResources().getDrawable(R.drawable.shape_corner_bottom));
                }
                helper.addOnClickListener(R.id.rl_show);
                helper.addOnClickListener(R.id.tv_delete);
                break;
            case WorkAddReportBean.WorkReportDetailsBean.EXPAND:
                helper.setText(R.id.tv_check_title, "明细标题：" + item.getTitle());
                if (!TextUtils.isEmpty(item.getInfo())) {
                    helper.setText(R.id.tv_work_content, "检查内容：" + item.getInfo());
                } else {
                    helper.setText(R.id.tv_work_content, "无");

                }
                // 位置
                if (!TextUtils.isEmpty(item.getRegion())) {
                    helper.setText(R.id.tv_check_address, item.getRegion());
                } else {
                    helper.setText(R.id.tv_check_address, "无");

                }
                // 设备
                if (!TextUtils.isEmpty(item.getBusinessThreeCode())) {
                    helper.setText(R.id.tv_check_device, Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 1) + "-" +
                            Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 2) + "-" +
                            Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 3));
                } else {
                    helper.setText(R.id.tv_check_device, "无");

                }


                initPic(helper, item);


                if (TextUtils.isEmpty(item.getMp4Path())) {
                    helper.setText(R.id.tv_vodio, "小视频：无");
                    helper.getView(R.id.rl_thumbnail).setVisibility(View.GONE);
                } else {
                    helper.setText(R.id.tv_vodio, "小视频：");
                    helper.getView(R.id.rl_thumbnail).setVisibility(View.VISIBLE);
                    helper.setVisible(R.id.iv_takevideo_work, true);
                    ((SimpleDraweeView) helper.getView(R.id.iv_takevideo_work)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getMp4Path() + ".jpg"));
                    ((SimpleDraweeView) helper.getView(R.id.iv_takevideo_work)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle_takevideo = new Bundle();
                            bundle_takevideo.putString("videoPath", BuildConfig.OSS_SERVER + item.getMp4Path() + ".mp4");
                            JumpItent.jump(mActivity, PlayVideoActivity.class, bundle_takevideo);
                        }
                    });

                }
                helper.addOnClickListener(R.id.iv_pack);
                helper.addOnClickListener(R.id.tv_pack);
                break;
            default:
                break;
        }
    }


    private void initPic(BaseViewHolder helper, WorkInspectDetailEntity item) {
        if (!StringUtils.isEmpty(item.getPictures())) {
            String[] urls = item.getPictures().split(",");
            helper.setText(R.id.tv_adjunct, "");
            helper.setVisible(R.id.ll_pic, true);

            ArrayList<String> picList = new ArrayList<String>();
            for (int i = 0; i < urls.length; i++) {
                picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[i]));
            }
            if (urls.length >= 1) {

                ((SimpleDraweeView) helper.getView(R.id.iv_pic1)).setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                ((SimpleDraweeView) helper.getView(R.id.iv_pic1)).setVisibility(View.VISIBLE);
                ((SimpleDraweeView) helper.getView(R.id.iv_pic1)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagePerviewUtil.perviewImage(mContext, picList);
                    }
                });
            } else {
                ((SimpleDraweeView) helper.getView(R.id.iv_pic1)).setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                ((SimpleDraweeView) helper.getView(R.id.iv_pic2)).setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                ((SimpleDraweeView) helper.getView(R.id.iv_pic2)).setVisibility(View.VISIBLE);
                ((SimpleDraweeView) helper.getView(R.id.iv_pic2)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagePerviewUtil.perviewImage(mContext, picList);
                    }
                });
            } else {
                ((SimpleDraweeView) helper.getView(R.id.iv_pic2)).setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
                ((SimpleDraweeView) helper.getView(R.id.iv_pic3)).setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                ((SimpleDraweeView) helper.getView(R.id.iv_pic3)).setVisibility(View.VISIBLE);
                ((SimpleDraweeView) helper.getView(R.id.iv_pic3)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagePerviewUtil.perviewImage(mContext, picList);
                    }
                });
            } else {
                ((SimpleDraweeView) helper.getView(R.id.iv_pic3)).setVisibility(View.GONE);
            }
        } else {
            helper.setText(R.id.tv_adjunct, "照片：无");
            helper.setVisible(R.id.ll_pic, false);
        }
    }
}
