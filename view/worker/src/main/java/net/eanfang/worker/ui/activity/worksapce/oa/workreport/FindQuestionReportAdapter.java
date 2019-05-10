package net.eanfang.worker.ui.activity.worksapce.oa.workreport;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.TemplateBean;
import com.eanfang.model.WorkAddReportBean;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.maintenance.MaintenanceTeamAdapter;
import net.eanfang.worker.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by O u r on 2018/10/19.
 */

public class FindQuestionReportAdapter extends BaseMultiItemQuickAdapter<WorkAddReportBean.WorkReportDetailsBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private Context mComtext;

    public FindQuestionReportAdapter(List<WorkAddReportBean.WorkReportDetailsBean> data, Context context) {
        super(data);
        this.mComtext = context;
        addItemType(WorkAddReportBean.WorkReportDetailsBean.FOLD, R.layout.item_template_question_report_normal);
        addItemType(WorkAddReportBean.WorkReportDetailsBean.EXPAND, R.layout.item_template_question_report_show);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkAddReportBean.WorkReportDetailsBean item) {
        switch (helper.getItemViewType()) {
            case WorkAddReportBean.WorkReportDetailsBean.FOLD:
                helper.setText(R.id.tv_title, "问题：" + (helper.getAdapterPosition() + 1));
                helper.setText(R.id.tv_work_content, "工作内容：" + item.getField1());
                if (!TextUtils.isEmpty(item.getField3())) {
                    helper.setText(R.id.tv_work_question, "处理措施：" + item.getField3());
                } else {
                    helper.setText(R.id.tv_work_question, "处理措施：无");

                }
                if (mData.size() - 1 == helper.getAdapterPosition()) {

                    helper.getView(R.id.rl_show).setBackground(mComtext.getResources().getDrawable(R.drawable.shape_corner_bottom));
                    helper.setVisible(R.id.v_line, false);
                } else {
                    helper.setVisible(R.id.v_line, true);
                }
                helper.addOnClickListener(R.id.rl_show);
                helper.addOnClickListener(R.id.tv_delete);
                break;
            case WorkAddReportBean.WorkReportDetailsBean.EXPAND:
                helper.setText(R.id.tv_title, "问题：" + (helper.getAdapterPosition() + 1));
                helper.setText(R.id.tv_work_content, item.getField1());
                if (!TextUtils.isEmpty(item.getField3())) {
                    helper.setText(R.id.tv_work_question, item.getField3());
                } else {
                    helper.setText(R.id.tv_work_question, "无");

                }

                if (!TextUtils.isEmpty(item.getField2())) {
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
                    helper.setVisible(R.id.iv_takevideo_work, true);
                    ((SimpleDraweeView) helper.getView(R.id.iv_takevideo_work)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getMp4_path() + ".jpg"));
                    helper.getView(R.id.iv_takevideo_work).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle_takevideo = new Bundle();
                            bundle_takevideo.putString("videoPath", BuildConfig.OSS_SERVER + item.getMp4_path() + ".mp4");
                            JumpItent.jump((CreationWorkReportActivity) mComtext, PlayVideoActivity.class, bundle_takevideo);
                        }
                    });

                }

                helper.addOnClickListener(R.id.tv_pack);
                helper.addOnClickListener(R.id.tv_delete);
                break;
        }
    }


    private void initPic(BaseViewHolder helper, WorkAddReportBean.WorkReportDetailsBean item) {
        if (!StringUtils.isEmpty(item.getPictures())) {
            String[] urls = item.getPictures().split(",");
            helper.setText(R.id.tv_adjunct, "照片：");
            helper.setVisible(R.id.ll_pic, true);

            ArrayList<String> picList = new ArrayList<String>();

            if (urls.length >= 1) {

                ((SimpleDraweeView) helper.getView(R.id.iv_pic1)).setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
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
                ((SimpleDraweeView) helper.getView(R.id.iv_pic2)).setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
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
                ((SimpleDraweeView) helper.getView(R.id.iv_pic3)).setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
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
