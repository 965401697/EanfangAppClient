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
import com.eanfang.biz.model.bean.WorkAddCheckBean;
import com.eanfang.biz.model.bean.WorkAddReportBean;
import com.eanfang.biz.model.bean.WorkTaskInfoBean;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.StrUtil;

/**
 * Created by O u r on 2018/10/19.
 */

public class CheckAdapter extends BaseMultiItemQuickAdapter<WorkAddCheckBean.WorkInspectDetailsBean, BaseViewHolder> {


    private Activity mActivity;
    private List<WorkAddCheckBean.WorkInspectDetailsBean> mData;

    public CheckAdapter(List<WorkAddCheckBean.WorkInspectDetailsBean> data, Activity activity) {
        super(data);
        this.mActivity = activity;
        this.mData = data;
        addItemType(WorkTaskInfoBean.WorkTaskDetailsBean.FOLD, R.layout.item_add_check_normal);
        addItemType(WorkTaskInfoBean.WorkTaskDetailsBean.EXPAND, R.layout.item_add_check_show);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkAddCheckBean.WorkInspectDetailsBean item) {
        switch (helper.getItemViewType()) {
            case WorkAddReportBean.WorkReportDetailsBean.FOLD:
                helper.setText(R.id.tv_title, "明细：" + (helper.getAdapterPosition() + 1));
                helper.setText(R.id.tv_work_content, "任务标题：" + item.getTitle());
                if (mData.size() - 1 == helper.getAdapterPosition()) {

                    helper.getView(R.id.rl_show).setBackground(mActivity.getResources().getDrawable(R.drawable.shape_corner_bottom));
                    helper.setVisible(R.id.v_line, false);
                } else {
                    helper.setVisible(R.id.v_line, true);
                }
                helper.addOnClickListener(R.id.rl_show);
                helper.addOnClickListener(R.id.tv_delete);
                break;
            case WorkAddReportBean.WorkReportDetailsBean.EXPAND:
                helper.setText(R.id.tv_title, "明细：" + (helper.getAdapterPosition() + 1));
                helper.setText(R.id.tv_work_content, item.getTitle());
                // 检查内容
                if (!TextUtils.isEmpty(item.getInfo())) {
                    helper.setText(R.id.tv_work_content, item.getInfo());
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


                if (TextUtils.isEmpty(item.getMp4_path())) {
                    helper.setText(R.id.tv_vodio, "小视频：无");
                    helper.getView(R.id.rl_thumbnail).setVisibility(View.GONE);
                } else {
                    helper.setText(R.id.tv_vodio, "小视频：");
                    helper.getView(R.id.rl_thumbnail).setVisibility(View.VISIBLE);
                    helper.setVisible(R.id.iv_takevideo_work, true);
                    GlideUtil.intoImageView(mActivity,Uri.parse(BuildConfig.OSS_SERVER + item.getMp4_path() + ".jpg"),helper.getView(R.id.iv_takevideo_work));
                    helper.getView(R.id.iv_takevideo_work).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle_takevideo = new Bundle();
                            bundle_takevideo.putString("videoPath", BuildConfig.OSS_SERVER + item.getMp4_path() + ".mp4");
                            JumpItent.jump(mActivity, PlayVideoActivity.class, bundle_takevideo);
                        }
                    });

                }

                helper.addOnClickListener(R.id.tv_pack);
                helper.addOnClickListener(R.id.tv_delete);
                break;
            default:
                break;
        }
    }


    private void initPic(BaseViewHolder helper, WorkAddCheckBean.WorkInspectDetailsBean item) {
        if (!StrUtil.isEmpty(item.getPictures())) {
            String[] urls = item.getPictures().split(",");
            helper.setText(R.id.tv_adjunct, "照片：");
            helper.setVisible(R.id.ll_pic, true);

            ArrayList<String> picList = new ArrayList<String>();

            if (urls.length >= 1) {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[0]),helper.getView(R.id.iv_pic1));
                helper.getView(R.id.iv_pic1).setVisibility(View.VISIBLE);
                helper.getView(R.id.iv_pic1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picList.clear();
                        picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                        ImagePerviewUtil.perviewImage(mContext, picList);
                    }
                });
            } else {
                helper.getView(R.id.iv_pic1).setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[1]),helper.getView(R.id.iv_pic2));

                helper.getView(R.id.iv_pic2).setVisibility(View.VISIBLE);
                helper.getView(R.id.iv_pic2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picList.clear();
                        picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                        ImagePerviewUtil.perviewImage(mContext, picList);
                    }
                });
            } else {
                helper.getView(R.id.iv_pic2).setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[2]),helper.getView(R.id.iv_pic3));
                helper.getView(R.id.iv_pic3).setVisibility(View.VISIBLE);
                helper.getView(R.id.iv_pic3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picList.clear();
                        picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                        ImagePerviewUtil.perviewImage(mContext, picList);
                    }
                });
            } else {
                helper.getView(R.id.iv_pic3).setVisibility(View.GONE);
            }
        } else {
            helper.setText(R.id.tv_adjunct, "照片：无");
            helper.setVisible(R.id.ll_pic, false);
        }
    }
}
