package net.eanfang.client.ui.activity.worksapce.oa.workreport;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.TemplateBean;
import com.eanfang.model.WorkAddReportBean;
import com.eanfang.model.WorkReportInfoBean;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.maintenance.MaintenanceTeamAdapter;
import net.eanfang.client.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by O u r on 2018/10/19.
 */

public class FindQuestionDetailAdapter extends BaseMultiItemQuickAdapter<WorkReportInfoBean.WorkReportDetailsBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private Context mComtext;

    public FindQuestionDetailAdapter(List<WorkReportInfoBean.WorkReportDetailsBean> data, Context context) {
        super(data);
        this.mComtext = context;
        addItemType(WorkAddReportBean.WorkReportDetailsBean.FOLD, R.layout.item_template_work_report_detail_normal);
        addItemType(WorkAddReportBean.WorkReportDetailsBean.EXPAND, R.layout.item_template_question_report_detail_show);
        addItemType(WorkAddReportBean.WorkReportDetailsBean.FOLD, R.layout.item_template_work_report_detail_normaltwo);
    }


    @Override
    protected void convert(BaseViewHolder helper, WorkReportInfoBean.WorkReportDetailsBean item) {
        switch (helper.getItemViewType()) {
            case WorkAddReportBean.WorkReportDetailsBean.FOLD:
                helper.setText(R.id.tv_title, "问题：" + (helper.getAdapterPosition() + 1));
                //helper.setTextColor(R.id.tv_work_content, Color.parseColor("#F00000"));

                helper.setText(R.id.tv_work_content_text, item.getField1());
                helper.setTextColor(R.id.tv_work_content_text, Color.parseColor("#FF4A4A"));

                if (!TextUtils.isEmpty(item.getField3())) {
                    helper.setText(R.id.tv_work_question, "处理措施：" + item.getField3());
                } else {
                    helper.setText(R.id.tv_work_question, "处理措施：无");

                }
                helper.addOnClickListener(R.id.rl_show);
//                helper.addOnClickListener(R.id.tv_delete);
                break;
            case WorkAddReportBean.WorkReportDetailsBean.EXPAND:
                helper.setText(R.id.tv_title, "问题：" + (helper.getAdapterPosition() + 1));
                helper.setText(R.id.tv_work_content, item.getField1());
                helper.setTextColor(R.id.tv_work_content, Color.parseColor("#FF4A4A"));
                if (!TextUtils.isEmpty(item.getField3())) {
                    helper.setText(R.id.tv_work_question, item.getField3());
                } else {
                    helper.setText(R.id.tv_work_question, "无");

                }

                if (!TextUtils.isEmpty(item.getField2()) && !item.getField2().contains("(") && item.getField2().contains("-")) {
                    helper.setVisible(R.id.recycler_view, true);
                    helper.setText(R.id.tv_person, "责任人：");
                    String[] info = item.getField2().split(",");

                    LinearLayoutManager manager = new LinearLayoutManager(helper.getConvertView().getContext());
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    RecyclerView recyclerView = helper.getView(R.id.recycler_view);

                    recyclerView.setLayoutManager(manager);

                    MaintenanceTeamAdapter teamAdapter = new MaintenanceTeamAdapter();
                    teamAdapter.bindToRecyclerView(recyclerView);

                    List<TemplateBean.Preson> list = new ArrayList<>();

                    if (info.length > 0) {
                        //多条
                        for (int i = 0; i < info.length; i++) {
                            String s = info[i];
                            String headPortrait = s.split("-")[0];
                            String name = s.split("-")[1];

                            TemplateBean.Preson preson = new TemplateBean.Preson();
                            preson.setProtraivat(headPortrait);
                            preson.setName(name);
                            list.add(preson);
                        }
                    } else {
                        //一条
                        String headPortrait = item.getField2().split("-")[0];
                        String name = item.getField2().split("-")[1];

                        TemplateBean.Preson preson = new TemplateBean.Preson();
                        preson.setProtraivat(headPortrait);
                        preson.setName(name);
                        list.add(preson);

                    }

                    teamAdapter.setNewData(list);

                } else {
                    helper.setVisible(R.id.recycler_view, false);
                    helper.setText(R.id.tv_person, "责任人：无");

                }

                initPic(helper, item);


                if (TextUtils.isEmpty(item.getMp4_path())) {
                    helper.setText(R.id.tv_vodio, "小视频：无");
                    helper.getView(R.id.rl_thumbnail).setVisibility(View.GONE);
                } else {
                    helper.setText(R.id.tv_vodio, "小视频：");
                    helper.getView(R.id.rl_thumbnail).setVisibility(View.VISIBLE);
                    ((SimpleDraweeView) helper.getView(R.id.iv_takevideo_work)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getMp4_path() + ".jpg"));
                    helper.getView(R.id.iv_takevideo_work).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle_takevideo = new Bundle();
                            bundle_takevideo.putString("videoPath", BuildConfig.OSS_SERVER + item.getMp4_path() + ".mp4");
                            JumpItent.jump((Activity) mComtext, PlayVideoActivity.class, bundle_takevideo);
                        }
                    });

                }
                helper.addOnClickListener(R.id.iv_pack);
                break;
            default:
                break;
        }
    }


    private void initPic(BaseViewHolder helper, WorkReportInfoBean.WorkReportDetailsBean item) {
        if (!StringUtils.isEmpty(item.getPictures())) {
            String[] urls = item.getPictures().split(",");
            helper.setText(R.id.tv_adjunct, "照片：");
            helper.setVisible(R.id.ll_pic, true);

            ArrayList<String> picList = new ArrayList<String>();
            for (int i = 0; i < urls.length; i++) {
                picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[i]));
            }
            if (urls.length >= 1) {

                ((SimpleDraweeView) helper.getView(R.id.iv_pic1)).setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                helper.getView(R.id.iv_pic1).setVisibility(View.VISIBLE);
                helper.getView(R.id.iv_pic1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagePerviewUtil.perviewImage(mContext, picList);
                    }
                });
            } else {
                helper.getView(R.id.iv_pic1).setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                ((SimpleDraweeView) helper.getView(R.id.iv_pic2)).setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                helper.getView(R.id.iv_pic2).setVisibility(View.VISIBLE);
                helper.getView(R.id.iv_pic2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagePerviewUtil.perviewImage(mContext, picList);
                    }
                });
            } else {
                helper.getView(R.id.iv_pic2).setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
                ((SimpleDraweeView) helper.getView(R.id.iv_pic3)).setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                helper.getView(R.id.iv_pic3).setVisibility(View.VISIBLE);
                helper.getView(R.id.iv_pic3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
