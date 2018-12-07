package net.eanfang.worker.ui.activity.worksapce.oa.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupDetailBean;
import com.eanfang.model.Message;
import com.eanfang.model.TemplateBean;
import com.eanfang.model.WorkTaskBean;
import com.eanfang.model.WorkTaskInfoBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.DialogUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.workreport.OAPersonAdaptet;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.util.SendContactUtils;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

public class TaskAssignmentCreationActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_section_name)
    TextView tvSectionName;
    @BindView(R.id.et_task_name)
    EditText etTaskName;
    @BindView(R.id.tv_add_task)
    TextView tvAddTask;
    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    @BindView(R.id.et_input_title)
    EditText etInputTitle;
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    @BindView(R.id.et_input_target)
    EditText etInputTarget;
    @BindView(R.id.et_input_stander)
    EditText etInputStander;
    @BindView(R.id.rv_team_work)
    RecyclerView rvTeamWork;
    @BindView(R.id.iv_takevideo_work)
    SimpleDraweeView ivTakevideoWork;
    @BindView(R.id.rl_thumbnail_work)
    RelativeLayout rlThumbnailWork;
    @BindView(R.id.snpl_photos_work)
    BGASortableNinePhotoLayout snplPhotosWork;
    @BindView(R.id.tv_add_video)
    ImageView tvAddVideo;
    @BindView(R.id.ll_add_task)
    LinearLayout llAddTask;
    @BindView(R.id.rv_send_person)
    RecyclerView rvSendPerson;
    @BindView(R.id.rv_send_group)
    RecyclerView rvSendGroup;
    private TaskAdapter mTaskAdapter;
    private List<WorkTaskBean.WorkTaskDetailsBean> mTaskDataList;
    private OAPersonAdaptet oaAddPersonAdaptet;

    private Map<String, String> mUploadMap = new HashMap<>();

    /**
     * 视频上传key
     */
    private String mUploadKey = "";
    /**
     * 视频路径
     */
    private String mVieoPath = "";

    private boolean isTrue = false;
    public int mFlag;//人员选择器的标志位
    private OAPersonAdaptet groupAdaptet;
    private OAPersonAdaptet sendPersonAdapter;

    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();
    private List<TemplateBean.Preson> groupList = new ArrayList<>();
    private List<TemplateBean.Preson> whoList = new ArrayList<>();

    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;

    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;

    public static final int REQUEST_CODE_GROUP = 101;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            ToastUtil.get().showToast(TaskAssignmentCreationActivity.this, "发送成功");
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assignment_creation_activitu);
        ButterKnife.bind(this);
        setTitle("新建任务");
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否要保存
                giveUp();
            }
        });
        initViews();
    }

    private void initViews() {
        tvCompanyName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
        tvSectionName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());

        rvTeamWork.setLayoutManager(new GridLayoutManager(this, 5));
        oaAddPersonAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 1);
        rvTeamWork.setAdapter(oaAddPersonAdaptet);

        rvSendPerson.setLayoutManager(new GridLayoutManager(this, 5));
        sendPersonAdapter = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 2);
        rvSendPerson.setAdapter(sendPersonAdapter);

        rvSendGroup.setLayoutManager(new GridLayoutManager(this, 5));
        groupAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 4);
        rvSendGroup.setAdapter(groupAdaptet);

        snplPhotosWork.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));

        String targetId = getIntent().getStringExtra("targetId");
        if (!TextUtils.isEmpty(targetId)) {
            getGroupDetail(targetId);
        }
    }

    private void getGroupDetail(String targetId) {

        EanfangHttp.post(UserApi.POST_GROUP_DETAIL_RY)
                .params("ryGroupId", targetId)
                .execute(new EanfangCallback<GroupDetailBean>(this, true, GroupDetailBean.class, (bean) -> {

                    TemplateBean.Preson preson = new TemplateBean.Preson();
                    preson.setName(bean.getGroup().getGroupName());
                    preson.setProtraivat(bean.getGroup().getHeadPortrait());
                    preson.setId(bean.getGroup().getRcloudGroupId());
                    groupList.add(preson);
                    groupAdaptet.setNewData(groupList);
                }));
    }

    @OnClick({R.id.tv_add_task, R.id.iv_title_voice, R.id.iv_content_voice, R.id.iv_target_voice, R.id.iv_stander_voice, R.id.tv_add_video, R.id.iv_takevideo_work, R.id.tv_complete, R.id.tv_save, R.id.tv_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_task:
                tvAddTask.setVisibility(View.INVISIBLE);
                llAddTask.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_title_voice:
                inputVoice(etInputTitle);
                break;
            case R.id.iv_content_voice:
                inputVoice(etInputContent);
                break;
            case R.id.iv_target_voice:
                inputVoice(etInputTarget);
                break;
            case R.id.iv_stander_voice:
                inputVoice(etInputStander);
                break;
            case R.id.tv_add_video:
                Bundle bundle_addvideo = new Bundle();
                bundle_addvideo.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JumpItent.jump(TaskAssignmentCreationActivity.this, TakeVideoActivity.class, bundle_addvideo);
                break;
            // 查看视频
            case R.id.iv_takevideo_work:
                Bundle bundle_takevideo = new Bundle();
                bundle_takevideo.putString("videoPath", mVieoPath);
                JumpItent.jump(TaskAssignmentCreationActivity.this, PlayVideoActivity.class, bundle_takevideo);
                break;
            case R.id.tv_complete:
                llAddTask.setVisibility(View.GONE);
                if (closeTaskWrite()) {
                    llAddTask.setVisibility(View.GONE);

                } else {
                    if (addDataToWrok()) llAddTask.setVisibility(View.GONE);
                }
                tvAddTask.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_save:
                //保存 并 下一条
                addDataToWrok();
                break;
            case R.id.tv_sub:
                submit();
                break;
        }
    }

    private boolean addDataToWrok() {

        if (!checkWorkInfo()) return false;

        WorkTaskBean.WorkTaskDetailsBean taskDetailsBean = new WorkTaskBean.WorkTaskDetailsBean();

        StringBuffer stringBuffer = new StringBuffer();

        for (int j = 0; j < oaAddPersonAdaptet.getData().size(); j++) {
            TemplateBean.Preson preson = oaAddPersonAdaptet.getData().get(j);
            if (j == oaAddPersonAdaptet.getData().size() - 1) {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName());
            } else {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName() + ",");
            }
        }

        //任务标题
        taskDetailsBean.setTitle(etInputTitle.getText().toString().trim());

        //任务内容
        taskDetailsBean.setInfo(etInputContent.getText().toString().trim());

        //任务目的
        taskDetailsBean.setPurpose(etInputTarget.getText().toString().trim());

        //任务标准
        taskDetailsBean.setCriterion(etInputStander.getText().toString().trim());

        //同事协同
        taskDetailsBean.setJoinPerson(stringBuffer.toString());

        String ursStr = PhotoUtils.getPhotoUrl("oa/task/", snplPhotosWork, mUploadMap, true);
        taskDetailsBean.setPictures(ursStr);
        // 短视频
        taskDetailsBean.setMp4_path(mUploadKey);
        //设置布局类型
        taskDetailsBean.setItemType(1);

        isTrue = true;//重置状态

        if (mUploadMap.size() != 0) {


            OSSUtils.initOSS(this).asyncPutImages(mUploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        if (isTrue) {
                            initTaskData(taskDetailsBean);
                            isTrue = false;
                        }
                    });
                }

            });
        } else {
            initTaskData(taskDetailsBean);
        }
        return true;
    }

    private void initTaskData(WorkTaskBean.WorkTaskDetailsBean bean) {
        if (mTaskDataList == null) {
            mTaskDataList = new ArrayList<>();
        }

        mTaskDataList.add(bean);

        if (mTaskAdapter == null) {
            rvTask.setLayoutManager(new LinearLayoutManager(this));
            mTaskAdapter = new TaskAdapter(mTaskDataList, this);
            mTaskAdapter.bindToRecyclerView(rvTask);
            mTaskAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    WorkTaskBean.WorkTaskDetailsBean b = (WorkTaskBean.WorkTaskDetailsBean) adapter.getData().get(position);
                    if (view.getId() == R.id.rl_show) {
                        b.setItemType(2);
                        adapter.notifyItemChanged(position);
                    } else if (view.getId() == R.id.tv_delete) {
                        adapter.remove(position);
                    } else if (view.getId() == R.id.tv_pack) {
                        b.setItemType(1);
                        adapter.notifyItemChanged(position);
                    }
                }
            });
        } else {
            mTaskAdapter.setNewData(mTaskDataList);
        }

        clearTaskData();//清空数据
    }

    private void clearTaskData() {
        etInputContent.setText("");
        etInputTitle.setText("");
        etInputStander.setText("");
        etInputTarget.setText("");
        oaAddPersonAdaptet.getData().clear();
        oaAddPersonAdaptet.notifyDataSetChanged();
        mUploadMap.clear();
        snplPhotosWork.setData(new ArrayList<String>());

        mVieoPath = "";
        mUploadKey = "";
        ivTakevideoWork.setVisibility(View.INVISIBLE);
        rlThumbnailWork.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GROUP) {
            TemplateBean.Preson preson = (TemplateBean.Preson) data.getSerializableExtra("bean");

            groupList.clear();
            groupList.addAll(groupAdaptet.getData());

            if (groupAdaptet.getData().size() > 0) {


                if (!groupAdaptet.getData().contains(preson)) {
                    groupList.add(preson);
                    groupAdaptet.setNewData(groupList);
                }
            } else {
                groupList.add(preson);
                groupAdaptet.setNewData(groupList);
            }
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_1:
                snplPhotosWork.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;


            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplPhotosWork.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            default:
                break;
        }
    }

    private boolean closeTaskWrite() {
        if (TextUtils.isEmpty(etInputContent.getText().toString().trim()) && TextUtils.isEmpty(etInputTitle.getText().toString().trim()) && TextUtils.isEmpty(etInputTarget.getText().toString().trim())
                && TextUtils.isEmpty(etInputStander.getText().toString().trim())
                && oaAddPersonAdaptet.getData().size() == 0 && snplPhotosWork.getData().size() == 0 && TextUtils.isEmpty(mUploadKey)) {

            return true;
        }

        return false;
    }

    private void submit() {

        String task_title = etTaskName.getText().toString().trim();
        if (TextUtils.isEmpty(task_title)) {
            showToast("请输入任务标题");
            return;
        }
        if (mTaskAdapter == null || mTaskAdapter.getData().size() == 0) {
            showToast("任务明细请最少填写一条");
            ;
            return;
        }

        if (whoList.size() == 0) {
            //工作协同默认值
            showToast("请选择发送给谁");
            return;
        }

        if (groupList.size() == 0) {
            //工作协同默认值
            showToast("请选择发送群聊");
            return;
        }

        WorkTaskBean workTaskBean = new WorkTaskBean();

        if (whoList.size() == 0) {
            //工作协同默认值
            workTaskBean.setAssigneeUserId(EanfangApplication.get().getUserId());
            workTaskBean.setAssigneeOrgCode(EanfangApplication.get().getOrgCode());
        } else {
            //工作协同默认值
            workTaskBean.setAssigneeUserId(Long.parseLong(whoList.get(0).getUserId()));
            workTaskBean.setAssigneeOrgCode(whoList.get(0).getOrgCode());
        }

        workTaskBean.setTitle(task_title);

        workTaskBean.setWorkTaskDetails(mTaskAdapter.getData());

        doHttp(JSON.toJSONString(workTaskBean));


    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(NewApiService.ADD_WORK_TASK)
                .upJson(jsonString)
                .execute(new EanfangCallback<WorkTaskInfoBean>(this, true, WorkTaskInfoBean.class, (bean) -> {
                    runOnUiThread(() -> {
                        Bundle bundle = new Bundle();
                        Message message = new Message();
                        message.setMsgContent("任务指派成功");
                        message.setTip("确定");
                        bundle.putSerializable("message", message);
                        JumpItent.jump(TaskAssignmentCreationActivity.this, StateChangeActivity.class, bundle);

                        //分享
                        //分享
                        if (whoList.size() == 0 && groupList.size() == 0) {
                            finishSelf();
                            return;
                        }

                        if (groupList.size() > 0) {


                            Set hashSet = new HashSet();
                            hashSet.addAll(groupAdaptet.getData());
                            hashSet.addAll(sendPersonAdapter.getData());

                            if (groupList.size() > 0) {
                                groupList.clear();
                            }

                            groupList.addAll(hashSet);
                        } else {
                            groupList.addAll(whoList);
                        }


                        Bundle b = new Bundle();

                        b.putString("id", String.valueOf(bean.getId()));
                        b.putString("orderNum", tvSectionName.getText().toString().trim());
                        if (bean.getWorkTaskDetails() != null && bean.getWorkTaskDetails().size() > 0 && !TextUtils.isEmpty(bean.getWorkTaskDetails().get(0).getPictures())) {
                            bundle.putString("picUrl", bean.getWorkTaskDetails().get(0).getPictures().split(",")[0]);
                        }
                        b.putString("creatTime", etTaskName.getText().toString().trim());
                        b.putString("workerName", EanfangApplication.get().getUser().getAccount().getRealName());
                        b.putString("status", "0");
                        b.putString("shareType", "4");

                        new SendContactUtils(b, handler, groupList, DialogUtil.createLoadingDialog(TaskAssignmentCreationActivity.this), "布置任务").send();


                    });


                }));

    }


    private boolean checkWorkInfo() {

        if (TextUtils.isEmpty(etInputTitle.getText().toString().trim())) {

            showToast("请填写任务明细标题");
            return false;
        }
        if (TextUtils.isEmpty(etInputContent.getText().toString().trim())) {

            showToast("请填写任务内容");
            return false;
        }
        return true;
    }


    private void inputVoice(EditText editText) {
        PermissionUtils.get(this).getVoicePermission(() -> {
            RecognitionManager.getSingleton().startRecognitionWithDialog(TaskAssignmentCreationActivity.this, editText);
        });
    }


    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            rlThumbnailWork.setVisibility(View.VISIBLE);
            mVieoPath = takeVdideoMode.getMImagePath();
            mUploadKey = takeVdideoMode.getMKey();
            if (!StringUtils.isEmpty(mVieoPath)) {

                if (ivTakevideoWork.getVisibility() == View.INVISIBLE) {
                    ivTakevideoWork.setVisibility(View.VISIBLE);
                }

                ivTakevideoWork.setImageBitmap(PhotoUtils.getVideoThumbnail(mVieoPath, 100, 100, MINI_KIND));
            }

        }
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

//        if (presonList.size() > 0) {
        Set hashSet = new HashSet();
//            if (mFlag == 1) {
//                hashSet.addAll(oaAddPersonAdaptet.getData());
//            } else
//                if (mFlag == 2) {
//                hashSet.addAll(sendPersonAdapter.getData());
//            } else
        if (mFlag == 4) {
            hashSet.addAll(groupAdaptet.getData());
        }

//            hashSet.addAll(presonList);
        if (mFlag == 2) {
            whoList.clear();
            whoList.addAll(presonList);
        } else if (mFlag == 4) {
            groupList.clear();
            groupList.addAll(hashSet);
        }
//            else {
//                newPresonList.clear();
//                newPresonList.addAll(hashSet);
//            }

        if (mFlag == 1) {
            oaAddPersonAdaptet.setNewData(presonList);
        } else if (mFlag == 2) {
            sendPersonAdapter.setNewData(whoList);
        } else if (mFlag == 4) {
            groupAdaptet.setNewData(groupList);
        }

//        }

    }


    public void setFlag(int flag) {
        this.mFlag = flag;
    }

    /**
     * 监听 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            giveUp();
        }
        return false;
    }

    /**
     * 放弃新建汇报
     */
    private void giveUp() {
        new TrueFalseDialog(this, "系统提示", "是否放弃布置任务？", () -> {
            //软盘关闭 聊天界面进入的话 软盘和操作板叠加
            InputMethodManager imm = (InputMethodManager) etTaskName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etTaskName.getApplicationWindowToken(), 0);
            }
            finish();
        }).showDialog();
    }
}
