package net.eanfang.client.ui.activity.worksapce.oa.check;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.DateKit;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.biz.model.entity.WorkInspectDetailEntity;
import com.eanfang.biz.model.entity.WorkInspectEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.im.SelectIMContactActivity;
import net.eanfang.client.ui.activity.worksapce.maintenance.MaintenanceTeamAdapter;
import net.eanfang.client.util.ImagePerviewUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;

/**
 * @author guanluocang
 * @data 2018/11/9
 * @description 查看设备点检详情 需要处理的
 */
public class DealWithFirstActivity extends BaseActivity {
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_task)
    TextView tvTask;
    @BindView(R.id.rv_task_list)
    RecyclerView rvTaskList;
    @BindView(R.id.tv_check_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.tv_deal_content)
    TextView tvDealContent;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_pic1)
    ImageView ivPic1;
    @BindView(R.id.iv_pic2)
    ImageView ivPic2;
    @BindView(R.id.iv_pic3)
    ImageView ivPic3;
    @BindView(R.id.ll_pic)
    LinearLayout llPic;
    @BindView(R.id.iv_takevideo_work)
    ImageView ivTakevideoWork;
    @BindView(R.id.rl_thumbnail)
    RelativeLayout rlThumbnail;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.tv_adjunct)
    TextView tvAdjunct;
    @BindView(R.id.tv_vodio)
    TextView tvVodio;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_pass)
    TextView tvPass;
    @BindView(R.id.ll_dealwith)
    LinearLayout llDealwith;
    @BindView(R.id.ll_deal_with)
    LinearLayout llDealWith;


    private long mId;
    private WorkInspectEntity mBean;

    /**
     * 订单状态
     */
    private int mOrderStatus = 100;

    /**
     * 协同人员
     */
    private MaintenanceTeamAdapter teamAdapter;
    List<TemplateBean.Preson> mTeamPersonList = new ArrayList<>();

    List<WorkInspectDetailEntity> workInspectDetailEntities = new ArrayList<>();
    private CheckDetailAdapter taskDeatilAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_deal_with_first);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setTitle("检查任务详情");
        setLeftBack();
        setRightTitle("分享");

        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享聊天

                Intent intent = new Intent(DealWithFirstActivity.this, SelectIMContactActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", String.valueOf(mBean.getId()));
                bundle.putString("orderNum", mBean.getCompanyName());
                if (mBean.getWorkInspectDetails() != null && !TextUtils.isEmpty(mBean.getWorkInspectDetails().get(0).getPictures())) {
                    bundle.putString("picUrl", mBean.getWorkInspectDetails().get(0).getPictures().split(",")[0]);
                }
                bundle.putString("creatTime", mBean.getTitle());
                bundle.putString("workerName", mBean.getCreateUser().getAccountEntity().getRealName());
                bundle.putString("status", "0");
                bundle.putString("shareType", "5");

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finishSelf();
            }
        });

        mId = getIntent().getLongExtra("id", 0);
        mOrderStatus = getIntent().getIntExtra("status", 100);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        teamAdapter = new MaintenanceTeamAdapter();
        teamAdapter.bindToRecyclerView(recyclerView);

        taskDeatilAdapter = new CheckDetailAdapter(workInspectDetailEntities, DealWithFirstActivity.this);
        rvTaskList.setLayoutManager(new LinearLayoutManager(this));
        taskDeatilAdapter.bindToRecyclerView(rvTaskList);
        getData();
    }

    private void getData() {
        EanfangHttp.get(NewApiService.GET_WORK_CHECK_INFO)
                .tag(this)
                .params("id", mId)
                .execute(new EanfangCallback<WorkInspectEntity>(this, true, WorkInspectEntity.class, (bean) -> {
                            mBean = bean;
                            //头像
                            GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + bean.getAssigneeUser().getAccountEntity().getAvatar()), ivHeader);
                            tvCompany.setText(bean.getCompanyName());
                            tvDate.setText(bean.getCreateTime());
                            tvTime.setText(bean.getChangeDeadlineTime());
                            tvTitle.setText(bean.getTitle());
                            tvName.setText("接收人" + bean.getAssigneeUser().getAccountEntity().getRealName());
                            tvWeek.setText(DateKit.get(bean.getCreateTime()).weekName());
                            // 是否显示认领
                            if (bean.getAssigneeUserId().equals(ClientApplication.get().getUserId()) && mOrderStatus == 0) {
                                tvSub.setVisibility(View.VISIBLE);
                            } else {
                                tvSub.setVisibility(View.GONE);
                            }
                            // 是否显示驳回 通过
                            if (bean.getCreateUserId().equals(ClientApplication.get().getUserId()) && mOrderStatus == 1) {
                                llDealwith.setVisibility(View.VISIBLE);
                            } else {
                                llDealwith.setVisibility(View.GONE);
                            }
                            if (bean.getWorkInpectDetailDispose() != null) {
                                llDealWith.setVisibility(View.VISIBLE);
                                tvDealContent.setText(bean.getWorkInpectDetailDispose().getDisposeInfo());
                                tvRemark.setText(bean.getWorkInpectDetailDispose().getRemarkInfo());
                                //协同人员
                                if (!StrUtil.isEmpty(bean.getWorkInpectDetailDispose().getCollaborativeUser())) {
                                    setTeam(bean.getWorkInpectDetailDispose().getCollaborativeUser());
                                    recyclerView.setVisibility(View.VISIBLE);
                                } else {
                                    tvPerson.setText("协同人员：无");
                                    recyclerView.setVisibility(View.GONE);
                                }
                                // mp4 地址
                                if (!StrUtil.isEmpty(bean.getWorkInpectDetailDispose().getMp4Path())) {
                                    setMp4(bean.getWorkInpectDetailDispose().getMp4Path());
                                    rlThumbnail.setVisibility(View.VISIBLE);
                                } else {
                                    tvVodio.setText("小视频：无");
                                    rlThumbnail.setVisibility(View.GONE);
                                }
                                // 附件
                                if (!StrUtil.isEmpty(bean.getWorkInpectDetailDispose().getPictures())) {
                                    setPhoto(bean.getWorkInpectDetailDispose().getPictures());
                                } else {
                                    tvAdjunct.setText("照片：无");
                                }
                            } else {
                                llDealWith.setVisibility(View.GONE);
                            }


                            for (int i = 0; i < bean.getWorkInspectDetails().size(); i++) {
                                WorkInspectDetailEntity workInspectDetailEntity = bean.getWorkInspectDetails().get(i);
                                workInspectDetailEntity.setItemType(1);
                            }
                            if (bean.getWorkInspectDetails().size() == 0) {
                                tvTask.setVisibility(View.VISIBLE);
                            } else {
                                initAdapter(bean);
                            }

                        })
                );
    }


    private void initAdapter(WorkInspectEntity workCheckInfoBean) {
        taskDeatilAdapter.setNewData(workCheckInfoBean.getWorkInspectDetails());
        taskDeatilAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                WorkInspectDetailEntity b = (WorkInspectDetailEntity) adapter.getData().get(position);
                if (view.getId() == R.id.rl_show) {
                    b.setItemType(2);
                    adapter.notifyItemChanged(position);
                } else if (view.getId() == R.id.iv_pack || view.getId() == R.id.tv_pack) {
                    b.setItemType(1);
                    adapter.notifyItemChanged(position);
                }
            }
        });
    }

    /**
     * 协同人员
     */
    public void setTeam(String team) {
        String[] info = team.split(",");
        if (info.length > 0) {
            //多条
            for (int i = 0; i < info.length; i++) {
                String s = info[i];
                String headPortrait = s.split("-")[0];
                String name = s.split("-")[1];

                TemplateBean.Preson preson = new TemplateBean.Preson();
                preson.setProtraivat(headPortrait);
                preson.setName(name);
                mTeamPersonList.add(preson);
            }
        } else {
            //一条
            String headPortrait = team.split("-")[0];
            String name = team.split("-")[1];

            TemplateBean.Preson preson = new TemplateBean.Preson();
            preson.setProtraivat(headPortrait);
            preson.setName(name);
            mTeamPersonList.add(preson);

        }
        teamAdapter.setNewData(mTeamPersonList);
    }

    /**
     * MP4
     */
    public void setMp4(String mp4Path) {
        GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + mp4Path + ".jpg"), ivTakevideoWork);
        ivTakevideoWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle_takevideo = new Bundle();
                bundle_takevideo.putString("videoPath", BuildConfig.OSS_SERVER + mp4Path + ".mp4");
                JumpItent.jump(DealWithFirstActivity.this, PlayVideoActivity.class, bundle_takevideo);
            }
        });

    }

    /**
     * 附件照片
     */
    public void setPhoto(String photoPath) {
        String[] urls = photoPath.split(",");
        ArrayList<String> picList = new ArrayList<String>();
        if (urls.length >= 1) {
            GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + Uri.parse(urls[0]), ivPic1);
            ivPic1.setVisibility(View.VISIBLE);
            ivPic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    picList.clear();
                    picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                    ImagePerviewUtil.perviewImage(DealWithFirstActivity.this, picList);
                }
            });
        } else {
            ivPic1.setVisibility(View.GONE);
        }

        if (urls.length >= 2) {
            GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + Uri.parse(urls[1]), ivPic2);
            ivPic2.setVisibility(View.VISIBLE);
            ivPic2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    picList.clear();
                    picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                    ImagePerviewUtil.perviewImage(DealWithFirstActivity.this, picList);
                }
            });
        } else {
            ivPic2.setVisibility(View.GONE);
        }
        if (urls.length >= 3) {
            GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + Uri.parse(urls[2]), ivPic3);
            ivPic3.setVisibility(View.VISIBLE);
            ivPic3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    picList.clear();
                    picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                    ImagePerviewUtil.perviewImage(DealWithFirstActivity.this, picList);
                }
            });
        } else {
            ivPic3.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_sub, R.id.tv_cancle, R.id.tv_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sub:
                // 处理详情
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", mBean);
                JumpItent.jump(DealWithFirstActivity.this, AddDealwithInfoActivity.class, bundle);
                break;
            // 审核驳回
            case R.id.tv_cancle:
                dealWith(1);
                break;
            // 审核通过
            case R.id.tv_pass:
                dealWith(2);
                break;
            default:
                break;
        }
    }

    /**
     * 通过 驳回
     */
    private void dealWith(int status) {
        EanfangHttp.post(NewApiService.GET_WORK_CHCEK_ADUIT)
                .params("status", status)
                .params("id", mBean.getWorkInpectDetailDispose().getId())
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    showToast("操作成功");
                    EventBus.getDefault().post("addCheckSuccess");
                    setResult(RESULT_OK);
                    finishSelf();
                }));
    }

    /**
     * 监听 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(RESULT_OK);
            finishSelf();
        }
        return false;
    }
}
