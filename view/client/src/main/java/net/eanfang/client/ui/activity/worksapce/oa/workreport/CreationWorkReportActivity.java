package net.eanfang.client.ui.activity.worksapce.oa.workreport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.loading.LoadKit;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.bean.Message;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.biz.model.bean.WorkAddReportBean;
import com.eanfang.biz.model.bean.WorkReportInfoBean;
import com.eanfang.biz.model.bean.device.User;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.util.SendContactUtils;

import org.greenrobot.eventbus.EventBus;
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
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import cn.hutool.core.util.StrUtil;

/**
 * 创建工作汇报
 */
public class CreationWorkReportActivity extends BaseClientActivity {
    //===============================================================完成工作
    @BindView(R.id.et_task_name)
    TextView etTaskName;
    @BindView(R.id.rv_complete_work)
    RecyclerView rvCompleteWork;

    @BindView(R.id.et_input_content)
    EditText etInputContent;
    @BindView(R.id.et_input_legacy)
    EditText etInputLegacy;
    @BindView(R.id.et_input_reason)
    EditText etInputReason;
    @BindView(R.id.et_input_handle)
    EditText etInputHandle;
    @BindView(R.id.rv_team_work)
    RecyclerView rvTeamWork;
    //    @BindView(R.id.tv_addViedeo_work)
//    TextView tvAddViedeoWork;
    @BindView(R.id.snpl_photos_work)
    BGASortableNinePhotoLayout snplPhotosWork;
    @BindView(R.id.iv_takevideo_work)
    ImageView ivTakevideoWork;
    @BindView(R.id.rl_thumbnail_work)
    RelativeLayout rlThumbnailWork;
    @BindView(R.id.tv_complete_work)
    TextView tvCompleteWork;
    @BindView(R.id.tv_cancle_work)
    TextView tvCancleWork;
    @BindView(R.id.ll_complete_work)
    LinearLayout llCompleteWork;
    @BindView(R.id.rv_send_who)
    RecyclerView rvSendWho;
    @BindView(R.id.rv_send_group)
    RecyclerView rvSendGroup;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.tv_add_complete)
    TextView tvAddComplete;
    @BindView(R.id.ll_work)
    LinearLayout llWork;
    @BindView(R.id.tv_add_find)
    TextView tvAddFind;
    @BindView(R.id.tv_add_plan)
    TextView tvAddPlan;


    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();
    private Map<String, String> mUploadMap = new HashMap<>();

    private boolean isTrue = true;
    /**
     * 视频上传key
     */
    private String mUploadKey = "";
    /**
     * 视频路径
     */
    private String mVieoPath = "";
    private List<WorkAddReportBean.WorkReportDetailsBean> mWorkDataList;
    private WorKReportAdapter mWorkCompleteAdapter;
    private OAPersonAdaptet oaPersonAdaptet;

    public static final int REQUEST_CODE_GROUP = 101;
    //==============================================================================发现问题
    @BindView(R.id.et_input_content_question)
    EditText etInputContentQuestion;
    @BindView(R.id.rv_team_question_work)
    RecyclerView rvTeamQuestionWork;
    @BindView(R.id.rv_find_question)
    RecyclerView rvFindQuestion;
    @BindView(R.id.et_input_handle_question)
    EditText etInputHandleQuestion;
    @BindView(R.id.snpl_photos_question)
    BGASortableNinePhotoLayout snplPhotosQuestion;
    @BindView(R.id.iv_takevideo_question)
    ImageView ivTakevideoQuestion;
    @BindView(R.id.rl_thumbnail_question)
    RelativeLayout rlThumbnailQuestion;
    @BindView(R.id.ll_find_question)
    LinearLayout llFindQuestion;
    private OAPersonAdaptet oaPersonQuestionAdaptet;

    private List<WorkAddReportBean.WorkReportDetailsBean> mQuestionDataList;
    private FindQuestionReportAdapter mQuestionAdapter;
    /**
     * 视频上传key
     */
    private String mQuestionUploadKey = "";
    /**
     * 视频路径
     */
    private String mQuestionVieoPath = "";

    private List<WorkAddReportBean.WorkReportDetailsBean> mPlanDataList;
    private PlanReportAdapter mPlanReportAdapter;


    //========================================================后续计划
    @BindView(R.id.report_plan_list)
    RecyclerView reportPlanList;
    @BindView(R.id.et_input_content_plan)
    EditText etInputContentPlan;
    @BindView(R.id.et_input_legacy_plan)
    EditText etInputLegacyPlan;
    @BindView(R.id.et_input_reason_plan)
    EditText etInputReasonPlan;
    @BindView(R.id.rv_team_plan)
    RecyclerView rvTeamPlan;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.snpl_photos_plan)
    BGASortableNinePhotoLayout snplPhotosPlan;
    //    @BindView(R.id.tv_addViedeo_plan)
//    TextView tvAddViedeoPlan;
    @BindView(R.id.iv_takevideo_plan)
    ImageView ivTakevideoPlan;
    @BindView(R.id.rl_thumbnail_plan)
    RelativeLayout rlThumbnailPlan;
    @BindView(R.id.ll_report_plan)
    LinearLayout llReportPlan;
    /**
     * 视频上传key
     */
    private String mPlanUploadKey = "";
    /**
     * 视频路径
     */
    private String mPlanVieoPath = "";

    private OAPersonAdaptet whoPlanAdaptet;
    private OAPersonAdaptet groupAdaptet;
    private OAPersonAdaptet planAdaptet;
    //=========================================================通用
    public int mFlag;//人员选择器的标志位
    private int mVidioFlag;//点击小视频的标志位
    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_2 = 2;


    private static final int REQUEST_CODE_CHOOSE_PHOTO_3 = 3;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_2 = 102;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_3 = 103;


    private List<TemplateBean.Preson> groupList = new ArrayList<>();
    private List<TemplateBean.Preson> whoList = new ArrayList<>();


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            ToastUtil.get().showToast(CreationWorkReportActivity.this, "发送成功");
            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_creation_work_report);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("新建汇报");
        setLeftBack(v -> giveUp());

        initViews();
    }

    private void initViews() {

        rvTeamWork.setLayoutManager(new GridLayoutManager(this, 5));
        rvTeamQuestionWork.setLayoutManager(new GridLayoutManager(this, 5));
        rvTeamPlan.setLayoutManager(new GridLayoutManager(this, 5));
        rvSendWho.setLayoutManager(new GridLayoutManager(this, 5));
        rvSendGroup.setLayoutManager(new GridLayoutManager(this, 5));

        oaPersonAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 1);
        oaPersonQuestionAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 2);
        whoPlanAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 3);
        groupAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 4);
        planAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 5);

        rvTeamWork.setAdapter(oaPersonAdaptet);
        rvTeamQuestionWork.setAdapter(oaPersonQuestionAdaptet);
        rvSendWho.setAdapter(whoPlanAdaptet);
        rvSendGroup.setAdapter(groupAdaptet);
        rvTeamPlan.setAdapter(planAdaptet);

        snplPhotosWork.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snplPhotosQuestion.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snplPhotosPlan.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));


        String targetId = getIntent().getStringExtra("targetId");
        String conversationType = getIntent().getStringExtra("conversationType");
        if (!TextUtils.isEmpty(targetId)) {
//        if (!TextUtils.isEmpty(targetId) && Conversation.ConversationType.GROUP.getName().toUpperCase().equals(conversationType.toUpperCase())) {
            getGroupDetail(targetId);
        }
//        else {
//            getPresonDetail(targetId);
//        }
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

    private void getPresonDetail(String id) {

        EanfangHttp.get(UserApi.POST_USER_INFO + id)
                .execute(new EanfangCallback<User>(this, false, User.class, (bean) -> {
                    TemplateBean.Preson preson = new TemplateBean.Preson();
                    preson.setName(bean.getNickName());
                    preson.setProtraivat(bean.getAvatar());
                    preson.setId(bean.getAccId());
                    preson.setUserId(bean.getIdCard());
                    whoList.add(preson);
                    whoPlanAdaptet.setNewData(whoList);
                }));
    }

    public void setFlag(int flag) {
        this.mFlag = flag;
    }

    /**
     * 完成工作
     *
     * @param view
     */
    @OnClick({R.id.ll_report_type, R.id.tv_add_complete, R.id.iv_content_voice, R.id.iv_question_voice, R.id.iv_reason_voice, R.id.iv_handle_voice, R.id.iv_takevideo_work, R.id.tv_add_video, R.id.tv_complete_work, R.id.tv_cancle_work})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_report_type:
                PickerSelectUtil.singleTextPicker(this, "", etTaskName, GetConstDataUtils.getWorkReportTypeList());
                break;
            case R.id.tv_add_complete:
                llCompleteWork.setVisibility(View.VISIBLE);
                tvAddComplete.setVisibility(View.INVISIBLE);
//                llWork.setBackground(getResources().getDrawable(R.drawable.shape_corner_top));
                break;
            case R.id.iv_content_voice:
                inputVoice(etInputContent);
                break;
            case R.id.iv_question_voice:
                inputVoice(etInputLegacy);
                break;
            case R.id.iv_reason_voice:
                inputVoice(etInputReason);
                break;
            case R.id.iv_handle_voice:
                inputVoice(etInputHandle);
                break;
            case R.id.tv_add_video:
                mVidioFlag = 1;
                Bundle bundle_addvideo = new Bundle();
                bundle_addvideo.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JumpItent.jump(CreationWorkReportActivity.this, TakeVideoActivity.class, bundle_addvideo);
                break;
            // 查看视频
            case R.id.iv_takevideo_work:
                Bundle bundle_takevideo = new Bundle();
                bundle_takevideo.putString("videoPath", mVieoPath);
                JumpItent.jump(CreationWorkReportActivity.this, PlayVideoActivity.class, bundle_takevideo);
                break;
            case R.id.tv_complete_work:
                if (closeWorkWrite()) {
                    llCompleteWork.setVisibility(View.GONE);

                } else {
                    if (addDataToWrok()) {
                        llCompleteWork.setVisibility(View.GONE);
                    }
                }
                tvAddComplete.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_cancle_work:
                //保存 并 下一条
                addDataToWrok();
                scrollView.scrollTo(0, etInputContent.getScrollY());
                break;
        }
    }

    private boolean closeWorkWrite() {
        return TextUtils.isEmpty(etInputContent.getText().toString().trim()) && TextUtils.isEmpty(etInputLegacy.getText().toString().trim()) && TextUtils.isEmpty(etInputReason.getText().toString().trim())
                && TextUtils.isEmpty(etInputHandle.getText().toString().trim())
                && oaPersonAdaptet.getData().size() == 0 && snplPhotosWork.getData().size() == 0 && TextUtils.isEmpty(mUploadKey);

    }

    private void clearWorkData() {
        etInputContent.setText("");
        etInputLegacy.setText("");
        etInputReason.setText("");
        etInputHandle.setText("");
        oaPersonAdaptet.getData().clear();
        oaPersonAdaptet.notifyDataSetChanged();
        mUploadMap.clear();
        snplPhotosWork.setData(new ArrayList<String>());

        mVieoPath = "";
        mUploadKey = "";
        ivTakevideoWork.setVisibility(View.INVISIBLE);
        rlThumbnailWork.setVisibility(View.GONE);
    }

    private boolean addDataToWrok() {

        if (!checkWorkInfo(etInputContent)) {
            return false;
        }

        WorkAddReportBean.WorkReportDetailsBean workBean = new WorkAddReportBean.WorkReportDetailsBean();

        StringBuffer stringBuffer = new StringBuffer();

        for (int j = 0; j < oaPersonAdaptet.getData().size(); j++) {
            TemplateBean.Preson preson = oaPersonAdaptet.getData().get(j);
            if (j == oaPersonAdaptet.getData().size() - 1) {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName());
            } else {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName() + ",");
            }
        }

        workBean.setType(0);

        //工作内容
        workBean.setField1(etInputContent.getText().toString().trim());

        //同事协同
        workBean.setField2(stringBuffer.toString());

        //遗留问题
        workBean.setField3(etInputLegacy.getText().toString().trim());

        //未完成原因
        workBean.setField4(etInputReason.getText().toString().trim());

        //处理
        workBean.setField5(etInputHandle.getText().toString().trim());
        String ursStr = PhotoUtils.getPhotoUrl("oa/report/", snplPhotosWork, mUploadMap, true);
        workBean.setPictures(ursStr);
        // 短视频
        workBean.setMp4_path(mUploadKey);
        //设置布局类型
        workBean.setItemType(1);

        isTrue = true;//重置状态

        if (mUploadMap.size() != 0) {

            SDKManager.ossKit(this).asyncPutImages(mUploadMap, (isSuccess) -> {
                runOnUiThread(() -> {
                    if (isTrue) {
                        subWorkData(workBean);
                        isTrue = false;
                    }
                });
            });
        } else {
            subWorkData(workBean);
        }
        return true;
    }

    private void subWorkData(WorkAddReportBean.WorkReportDetailsBean bean) {
        if (mWorkDataList == null) {
            mWorkDataList = new ArrayList<>();
        }

        mWorkDataList.add(bean);

        if (mWorkCompleteAdapter == null) {
            rvCompleteWork.setLayoutManager(new LinearLayoutManager(this));
            mWorkCompleteAdapter = new WorKReportAdapter(mWorkDataList, this);
            mWorkCompleteAdapter.bindToRecyclerView(rvCompleteWork);
            mWorkCompleteAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                WorkAddReportBean.WorkReportDetailsBean b = (WorkAddReportBean.WorkReportDetailsBean) adapter.getData().get(position);
                if (view.getId() == R.id.rl_show) {
                    b.setItemType(2);
                    adapter.notifyItemChanged(position);
                } else if (view.getId() == R.id.tv_delete) {
//                    mWorkDataList.remove(position);
                    adapter.remove(position);


                } else if (view.getId() == R.id.tv_pack) {
                    b.setItemType(1);
                    adapter.notifyItemChanged(position);
                }
            });
        } else {
            mWorkCompleteAdapter.setNewData(mWorkDataList);
        }

        clearWorkData();//清空数据
    }

    private void clearQuestionData() {
        etInputContentQuestion.setText("");
        etInputHandleQuestion.setText("");

        oaPersonQuestionAdaptet.getData().clear();
        oaPersonQuestionAdaptet.notifyDataSetChanged();
        mUploadMap.clear();
        snplPhotosQuestion.setData(new ArrayList<String>());

        mQuestionVieoPath = "";
        mQuestionUploadKey = "";
        ivTakevideoQuestion.setVisibility(View.INVISIBLE);
        rlThumbnailQuestion.setVisibility(View.GONE);
    }

    private boolean closeQuestionWrite() {
        return TextUtils.isEmpty(etInputContentQuestion.getText().toString().trim()) && TextUtils.isEmpty(etInputHandleQuestion.getText().toString().trim())

                && oaPersonQuestionAdaptet.getData().size() == 0 && snplPhotosQuestion.getData().size() == 0 && TextUtils.isEmpty(mQuestionUploadKey);

    }


    private boolean checkWorkInfo(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {

            showToast("请填写工作内容");
            return false;
        }

        return true;
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
                snplPhotosWork.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snplPhotosQuestion.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snplPhotosPlan.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;

            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplPhotosWork.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_2:
                snplPhotosQuestion.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_3:
                snplPhotosPlan.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            default:
                break;
        }
    }


    //============================================================================================================发现问题
    @OnClick({R.id.tv_add_find, R.id.iv_content_voice_question, R.id.iv_question_voice_question, R.id.tv_sure_find_question, R.id.tv_add_video_question, R.id.iv_takevideo_question, R.id.tv_save_find_question})
    public void onViewQuestionClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_find:
                llFindQuestion.setVisibility(View.VISIBLE);
                tvAddFind.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_content_voice_question:
                inputVoice(etInputContentQuestion);
                break;
            case R.id.iv_question_voice_question:
                inputVoice(etInputHandleQuestion);
                break;
            case R.id.tv_add_video_question:
                mVidioFlag = 2;
                Bundle bundle_addvideo = new Bundle();
                bundle_addvideo.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JumpItent.jump(CreationWorkReportActivity.this, TakeVideoActivity.class, bundle_addvideo);
                break;
            // 查看视频
            case R.id.iv_takevideo_question:
                Bundle bundle_takevideo = new Bundle();
                bundle_takevideo.putString("videoPath", mQuestionVieoPath);
                JumpItent.jump(CreationWorkReportActivity.this, PlayVideoActivity.class, bundle_takevideo);
                break;
            case R.id.tv_sure_find_question:

                if (closeQuestionWrite()) {
                    llFindQuestion.setVisibility(View.GONE);

                } else {

                    if (addQuestionData()) {
                        llFindQuestion.setVisibility(View.GONE);
                    }
                }
                tvAddFind.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_save_find_question:
                addQuestionData();
                scrollView.scrollTo(0, etInputContentQuestion.getScrollY() + 100);
                break;
        }
    }

    private boolean addQuestionData() {

        if (!checkWorkInfo(etInputContentQuestion)) {
            return false;
        }

        WorkAddReportBean.WorkReportDetailsBean questionBean = new WorkAddReportBean.WorkReportDetailsBean();

        StringBuffer stringBuffer = new StringBuffer();

        for (int j = 0; j < oaPersonQuestionAdaptet.getData().size(); j++) {
            TemplateBean.Preson preson = oaPersonQuestionAdaptet.getData().get(j);
            if (j == oaPersonQuestionAdaptet.getData().size() - 1) {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName());
            } else {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName() + ",");
            }
        }

        questionBean.setType(1);
        //工作内容
        questionBean.setField1(etInputContentQuestion.getText().toString().trim());
        //同事协同
        questionBean.setField2(stringBuffer.toString());
        //处理
        questionBean.setField3(etInputHandleQuestion.getText().toString().trim());
        String ursStr = PhotoUtils.getPhotoUrl("oa/findquestion/", snplPhotosQuestion, mUploadMap, true);
        questionBean.setPictures(ursStr);
        // 短视频
        questionBean.setMp4_path(mQuestionUploadKey);
        questionBean.setItemType(1);


        isTrue = true;//重置状态

        if (mUploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(mUploadMap, (isSuccess) -> {
                runOnUiThread(() -> {
                    if (isTrue) {
                        subQuestionData(questionBean);
                        isTrue = false;
                    }
                });
            });
        } else {
            subQuestionData(questionBean);
        }
        return true;
    }

    private void subQuestionData(WorkAddReportBean.WorkReportDetailsBean bean) {
        if (mQuestionDataList == null) {
            mQuestionDataList = new ArrayList<>();
        }

        mQuestionDataList.add(bean);

        if (mQuestionAdapter == null) {
            rvFindQuestion.setLayoutManager(new LinearLayoutManager(this));
            mQuestionAdapter = new FindQuestionReportAdapter(mQuestionDataList, this);
            mQuestionAdapter.bindToRecyclerView(rvFindQuestion);
            mQuestionAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                WorkAddReportBean.WorkReportDetailsBean b = (WorkAddReportBean.WorkReportDetailsBean) adapter.getData().get(position);
                if (view.getId() == R.id.rl_show) {
                    b.setItemType(2);
                    adapter.notifyItemChanged(position);
                } else if (view.getId() == R.id.tv_delete) {
//                    mQuestionDataList.remove(position);
                    adapter.remove(position);
                } else if (view.getId() == R.id.tv_pack) {
                    b.setItemType(1);
                    adapter.notifyItemChanged(position);
                }
            });
        } else {
            mQuestionAdapter.setNewData(mQuestionDataList);
        }

        clearQuestionData();//清空数据
    }

    //======================================================================================================================后续计划


    @OnClick({R.id.tv_add_plan, R.id.iv_content_voice_plan, R.id.iv_question_voice_plan, R.id.iv_reason_voice_plan, R.id.ll_time, R.id.tv_add_video_plan, R.id.iv_takevideo_plan, R.id.tv_complete_plan, R.id.tv_cancle_plan})
    public void onPlanViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_plan:
                llReportPlan.setVisibility(View.VISIBLE);
                tvAddPlan.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_content_voice_plan:
                inputVoice(etInputContentPlan);
                break;
            case R.id.iv_question_voice_plan:
                inputVoice(etInputLegacyPlan);
                break;
            case R.id.iv_reason_voice_plan:
                inputVoice(etInputReasonPlan);
                break;
            case R.id.ll_time:
                PickerSelectUtil.onUpYearMonthDayPicker(this, tvTime);
                break;
            case R.id.tv_add_video_plan:
                mVidioFlag = 3;
                Bundle bundle_addvideo = new Bundle();
                bundle_addvideo.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JumpItent.jump(CreationWorkReportActivity.this, TakeVideoActivity.class, bundle_addvideo);
                break;
            // 查看视频
            case R.id.iv_takevideo_plan:
                Bundle bundle_takevideo = new Bundle();
                bundle_takevideo.putString("videoPath", mPlanVieoPath);
                JumpItent.jump(CreationWorkReportActivity.this, PlayVideoActivity.class, bundle_takevideo);
                break;
            case R.id.tv_complete_plan:

                if (closePlanWrite()) {
                    llReportPlan.setVisibility(View.GONE);

                } else {
                    if (setPlanData()) {
                        llReportPlan.setVisibility(View.GONE);
                    }
                }
                tvAddPlan.setVisibility(View.VISIBLE);

                break;
            case R.id.tv_cancle_plan:
                setPlanData();
                scrollView.scrollTo(0, etInputContentPlan.getScrollY());
                break;
        }
    }

    private boolean setPlanData() {

        if (!checkWorkInfo(etInputContentPlan)) {
            return false;
        }

        WorkAddReportBean.WorkReportDetailsBean planBean = new WorkAddReportBean.WorkReportDetailsBean();

        planBean.setType(2);

        //工作内容

        planBean.setField1(etInputContentPlan.getText().toString().trim());

        //同事协同

        planBean.setField3(etInputLegacyPlan.getText().toString().trim());

        //遗留问题

        planBean.setField4(etInputReasonPlan.getText().toString().trim());


        StringBuffer stringBuffer = new StringBuffer();

        for (int j = 0; j < planAdaptet.getData().size(); j++) {
            TemplateBean.Preson preson = planAdaptet.getData().get(j);
            if (j == planAdaptet.getData().size() - 1) {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName());
            } else {
                stringBuffer.append(preson.getProtraivat() + "-" + preson.getName() + ",");
            }
        }

        planBean.setField2(stringBuffer.toString());

        //年限

        planBean.setField5(tvTime.getText().toString().trim());
        String ursStr = PhotoUtils.getPhotoUrl("oa/report/", snplPhotosPlan, mUploadMap, true);
        planBean.setPictures(ursStr);
        // 短视频
        planBean.setMp4_path(mPlanUploadKey);

        planBean.setItemType(1);

        isTrue = true;//重置状态

        if (mUploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(mUploadMap, (isSuccess) -> {
                runOnUiThread(() -> {
                    if (isTrue) {
                        subPlanData(planBean);
                        isTrue = false;
                    }
                });
            });
        } else {
            subPlanData(planBean);
        }
        return true;
    }

    private void subPlanData(WorkAddReportBean.WorkReportDetailsBean bean) {
        if (mPlanDataList == null) {
            mPlanDataList = new ArrayList<>();
        }

        mPlanDataList.add(bean);

        if (mPlanReportAdapter == null) {
            reportPlanList.setLayoutManager(new LinearLayoutManager(this));
            mPlanReportAdapter = new PlanReportAdapter(mPlanDataList, this);
            mPlanReportAdapter.bindToRecyclerView(reportPlanList);
            mPlanReportAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                WorkAddReportBean.WorkReportDetailsBean b = (WorkAddReportBean.WorkReportDetailsBean) adapter.getData().get(position);
                if (view.getId() == R.id.rl_show) {
                    b.setItemType(2);
                    adapter.notifyItemChanged(position);
                } else if (view.getId() == R.id.tv_delete) {
//                    mPlanDataList.remove(position);
                    adapter.remove(position);
                } else if (view.getId() == R.id.tv_pack) {
                    b.setItemType(1);
                    adapter.notifyItemChanged(position);
                }
            });
        } else {
            mPlanReportAdapter.setNewData(mPlanDataList);
        }

        clearPlanData();//清空数据
    }

    private void clearPlanData() {
        etInputContentPlan.setText("");
        etInputLegacyPlan.setText("");
        etInputReasonPlan.setText("");

        planAdaptet.getData().clear();
        planAdaptet.notifyDataSetChanged();
        mUploadMap.clear();
        snplPhotosPlan.setData(new ArrayList<String>());
        tvTime.setText("");
        mPlanUploadKey = "";
        mPlanVieoPath = "";
        ivTakevideoPlan.setVisibility(View.INVISIBLE);
        rlThumbnailPlan.setVisibility(View.GONE);
    }


    private boolean closePlanWrite() {
        return TextUtils.isEmpty(etInputContentPlan.getText().toString().trim()) && TextUtils.isEmpty(etInputLegacyPlan.getText().toString().trim()) && TextUtils.isEmpty(etInputReasonPlan.getText().toString().trim())

                && planAdaptet.getData().size() == 0 && snplPhotosPlan.getData().size() == 0 && TextUtils.isEmpty(mPlanVieoPath);

    }


    private void sub() {

        WorkAddReportBean bean = new WorkAddReportBean();
        List<WorkAddReportBean.WorkReportDetailsBean> beanList = new ArrayList<>();
        //汇报类型
        if (TextUtils.isEmpty(etTaskName.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请选择汇报类型");
            return;
        }


        if (mWorkCompleteAdapter == null || mWorkCompleteAdapter.getData().size() < 3) {
            showToast("完成工作请最少填写三条");
            return;
        }

        if (mQuestionAdapter == null || mQuestionAdapter.getData().size() == 0) {
            showToast("发现问题请最少填写一条");
            return;
        }

        if (mPlanReportAdapter == null || mPlanReportAdapter.getData().size() < 3) {
            showToast("后续计划请最少填写三条");
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

        bean.setType(GetConstDataUtils.getWorkReportTypeList().indexOf(etTaskName.getText().toString().trim()));

        if (whoList.size() == 0) {
            //工作协同默认值
            bean.setAssigneeUserId(ClientApplication.get().getUserId());
            bean.setAssigneeOrgCode(ClientApplication.get().getOrgCode());
        } else {
            //工作协同默认值
            bean.setAssigneeUserId(Long.parseLong(whoList.get(0).getUserId()));
            bean.setAssigneeOrgCode(whoList.get(0).getOrgCode());
        }


        beanList.addAll(mWorkCompleteAdapter.getData());
        if (mQuestionAdapter != null && mQuestionAdapter.getData().size() > 0) {
            beanList.addAll(mQuestionAdapter.getData());
        }
        beanList.addAll(mPlanReportAdapter.getData());
        bean.setWorkReportDetails(beanList);

        doHttp(JSON.toJSONString(bean));
    }

    //========================================================通用============================================================

    @OnClick(R.id.tv_sub)
    public void onViewClicked() {
        sub();
    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(NewApiService.ADD_WORK_REPORT)
                .upJson(jsonString)
                .execute(new EanfangCallback<WorkReportInfoBean>(this, true, WorkReportInfoBean.class, (bean) -> {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(CreationWorkReportActivity.this, StateChangeActivity.class);
                        Bundle bundle = new Bundle();
                        Message message = new Message();
//                        message.setTitle("汇报发送成功");
//                        message.setMsgTitle("汇报发送成功\r\n您的工作汇报已发送成功");
                        message.setMsgContent("汇报发送成功\n" +
                                "您的工作汇报已发送成功\r\n您可以随时通过我的汇报查看");
                        message.setShowOkBtn(true);
                        message.setShowLogo(true);
                        message.setTip("确定");
                        bundle.putSerializable("message", message);
                        EventBus.getDefault().post("addReportSuccess");
                        intent.putExtras(bundle);
                        startActivity(intent);


                        //分享
                        if (whoList.size() == 0 && groupList.size() == 0) {
                            finishSelf();
                            return;
                        }

                        if (groupList.size() > 0) {


                            Set hashSet = new HashSet();
                            hashSet.addAll(groupAdaptet.getData());
                            hashSet.addAll(whoPlanAdaptet.getData());

                            if (groupList.size() > 0) {
                                groupList.clear();
                            }

                            groupList.addAll(hashSet);
                        } else {
                            groupList.addAll(whoList);
                        }

                        Bundle b = new Bundle();

                        b.putString("id", String.valueOf(bean.getId()));
                        b.putString("orderNum", ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());
                        if (bean.getWorkReportDetails() != null && bean.getWorkReportDetails().size() > 0 && !TextUtils.isEmpty(bean.getWorkReportDetails().get(0).getPictures())) {
                            bundle.putString("picUrl", bean.getWorkReportDetails().get(0).getPictures().split(",")[0]);
                        }
                        b.putString("creatTime", String.valueOf(GetConstDataUtils.getWorkReportTypeList().indexOf(etTaskName.getText().toString().trim())));
                        b.putString("workerName", ClientApplication.get().getLoginBean().getAccount().getRealName());
                        b.putString("status", "0");
                        b.putString("shareType", "3");
                        b.putString("creatReleaseTime", bean.getCreateTime());

                        new SendContactUtils(b, handler, groupList, LoadKit.dialog(CreationWorkReportActivity.this), "工作汇报").send();


                    });
                }));
    }


    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            switch (mVidioFlag) {
                case 1:
                    vidio(takeVdideoMode, this.mUploadKey, this.mVieoPath, ivTakevideoWork, rlThumbnailWork);
                    break;
                case 2:
                    vidio(takeVdideoMode, this.mQuestionUploadKey, this.mQuestionVieoPath, ivTakevideoQuestion, rlThumbnailQuestion);
                    break;
                case 3:
                    vidio(takeVdideoMode, this.mPlanUploadKey, this.mPlanVieoPath, ivTakevideoPlan, rlThumbnailPlan);
                    break;
            }
        }
    }


    private void vidio(TakeVdideoMode takeVdideoMode, String key, String path, ImageView view, RelativeLayout relativeLayout) {

        path = takeVdideoMode.getMImagePath();
        key = takeVdideoMode.getMKey();
        if (mVidioFlag == 1) {
            this.mUploadKey = key;
            this.mVieoPath = path;
        } else if (mVidioFlag == 2) {
            this.mQuestionUploadKey = key;
            this.mQuestionVieoPath = path;
        } else if (mVidioFlag == 3) {
            this.mPlanUploadKey = key;
            this.mPlanVieoPath = path;
        }
        if (!StrUtil.isEmpty(path)) {
            if (view.getVisibility() == View.INVISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
            view.setImageBitmap(PhotoUtils.getVideoBitmap(path));
        }
        relativeLayout.setVisibility(View.VISIBLE);
    }


    private void inputVoice(EditText editText) {
        RxPerm.get(this).voicePerm((isSuccess) -> {
            RecognitionManager.getSingleton().startRecognitionWithDialog(CreationWorkReportActivity.this, editText);
        });
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

//        if (presonList.size() > 0) {


        Set hashSet = new HashSet();
//            if (mFlag == 1) {
//                hashSet.addAll(oaPersonAdaptet.getData());
//            } else if (mFlag == 2) {
//                hashSet.addAll(oaPersonQuestionAdaptet.getData());
//            } else
//        if (mFlag == 3) {
//            hashSet.addAll(whoPlanAdaptet.getData());
//        } else
        if (mFlag == 4) {
            hashSet.addAll(groupAdaptet.getData());
            hashSet.addAll(presonList);
        }
//            } else if (mFlag == 5) {
//                hashSet.addAll(planAdaptet.getData());
//            }

//            hashSet.addAll(presonList);
        if (mFlag == 3) {
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
            oaPersonAdaptet.setNewData(presonList);
        } else if (mFlag == 2) {
            oaPersonQuestionAdaptet.setNewData(presonList);
        } else if (mFlag == 3) {
            whoPlanAdaptet.setNewData(whoList);
        } else if (mFlag == 4) {
            groupAdaptet.setNewData(groupList);
        } else if (mFlag == 5) {
            planAdaptet.setNewData(presonList);
        }

//        }


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
        new TrueFalseDialog(this, "系统提示", "是否放弃工作汇报？", () -> {
            //软盘关闭 聊天界面进入的话 软盘和操作板叠加
            InputMethodManager imm = (InputMethodManager) etTaskName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etTaskName.getApplicationWindowToken(), 0);
            }


            finish();
        }).showDialog();
    }

}
