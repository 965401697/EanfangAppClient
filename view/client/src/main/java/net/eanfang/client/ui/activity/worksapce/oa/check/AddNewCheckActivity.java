package net.eanfang.client.ui.activity.worksapce.oa.check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupDetailBean;
import com.eanfang.model.Message;
import com.eanfang.model.TemplateBean;
import com.eanfang.model.WorkAddCheckBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
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
import com.yaf.base.entity.WorkInspectEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.client.ui.activity.worksapce.oa.OAPersonAdaptet;
import net.eanfang.client.ui.activity.worksapce.repair.SelectDeviceTypeActivity;
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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

/**
 * @author guanluocang
 * @data 2018/11/7
 * @description 添加设备点检
 */

public class AddNewCheckActivity extends BaseActivity implements SelectTimeDialogFragment.SelectTimeListener {
    /**
     * 公司名称
     */
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    /**
     * 部门名称
     */
    @BindView(R.id.tv_section_name)
    TextView tvSectionName;
    /**
     * 任务标题
     */
    @BindView(R.id.et_task_name)
    EditText etTaskName;
    // 整改期限
    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.ll_update_time)
    LinearLayout llUpdateTime;
    //添加检查明细
    @BindView(R.id.tv_add_task)
    TextView tvAddTask;
    @BindView(R.id.ll_add_detail)
    LinearLayout llAddDetail;
    // 添加明细
    @BindView(R.id.et_input_title)
    EditText etInputTitle;
    @BindView(R.id.et_input_address)
    EditText etInputAddress;
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    // 设备名称
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    // 添加图片
    @BindView(R.id.snpl_photos_work)
    BGASortableNinePhotoLayout snplPhotosWork;
    @BindView(R.id.rv_send_person)
    RecyclerView rvSendPerson;
    @BindView(R.id.rv_send_group)
    RecyclerView rvSendGroup;
    // 任务明细
    @BindView(R.id.rv_check)
    RecyclerView rvCheck;
    @BindView(R.id.iv_takevideo_check)
    SimpleDraweeView ivTakevideoCheck;
    @BindView(R.id.rl_thumbnail_check)
    RelativeLayout rlThumbnailCheck;


    private Map<String, String> mUploadMap = new HashMap<>();
    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;

    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    // 设备信息 RequestCode
    private static final int REQUEST_FAULTDEVICEINFO = 100;
    private static final int RESULT_DATACODE = 200;

    public static final int REQUEST_CODE_GROUP = 101;

    /**
     * 视频上传key
     */
    private String mUploadKey = "";
    /**
     * 视频路径
     */
    private String mVieoPath = "";

    private WorkAddCheckBean.WorkInspectDetailsBean detailsBean;
    private CheckAdapter checkAdapter;
    /**
     * 存储添加检查明细
     */
    private List<WorkAddCheckBean.WorkInspectDetailsBean> mCheckList;

    private boolean isTrue = false;
    /**
     * 人员选择器的标志位
     */
    public int mFlag;
    private OAPersonAdaptet groupAdaptet;
    private OAPersonAdaptet sendPersonAdapter;
    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();
    private List<TemplateBean.Preson> groupList = new ArrayList<>();
    private List<TemplateBean.Preson> whoList = new ArrayList<>();

    /**
     * 设备code 设备id
     */
    private String dataCode = "";

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            ToastUtil.get().showToast(AddNewCheckActivity.this, "发送成功");
            finishSelf();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_check);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        setTitle("新建任务");

        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否要保存
                giveUp();
            }
        });
        snplPhotosWork.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));

        /**
         * 发送给谁
         * */
        rvSendPerson.setLayoutManager(new GridLayoutManager(this, 5));
        sendPersonAdapter = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 2);
        rvSendPerson.setAdapter(sendPersonAdapter);

        /**
         * 发送到群聊
         * */
        rvSendGroup.setLayoutManager(new GridLayoutManager(this, 5));
        groupAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 4);
        rvSendGroup.setAdapter(groupAdaptet);


        String targetId = getIntent().getStringExtra("targetId");
        if (!TextUtils.isEmpty(targetId)) {
            getGroupDetail(targetId);
        }

    }

    private void initData() {
        tvCompanyName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
        tvSectionName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());
    }

    @OnClick({R.id.ll_update_time, R.id.tv_add_task, R.id.tv_sub, R.id.iv_title_voice, R.id.ll_select_device,
            R.id.iv_address_voice, R.id.iv_content_voice, R.id.tv_add_video, R.id.iv_takevideo_check,
            R.id.tv_complete_work, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_update_time:// 整改期限
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.tv_add_task:
                tvAddTask.setVisibility(View.INVISIBLE);
                llAddDetail.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_title_voice:// 标题
                inputVoice(etInputTitle);
                break;
            // 选择设备
            case R.id.ll_select_device:
                JumpItent.jump(AddNewCheckActivity.this, SelectDeviceTypeActivity.class, REQUEST_FAULTDEVICEINFO);
                break;
            case R.id.iv_address_voice:// 地址
                inputVoice(etInputAddress);
                break;
            case R.id.iv_content_voice:// 内容
                inputVoice(etInputContent);
                break;
            // 添加视频
            case R.id.tv_add_video:
                Bundle bundle_addvideo = new Bundle();
                bundle_addvideo.putString("videoPath", "addcheck_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JumpItent.jump(AddNewCheckActivity.this, TakeVideoActivity.class, bundle_addvideo);
                break;
            // 查看视频
            case R.id.iv_takevideo_check:
                Bundle bundle_takevideo = new Bundle();
                bundle_takevideo.putString("videoPath", mVieoPath);
                JumpItent.jump(AddNewCheckActivity.this, PlayVideoActivity.class, bundle_takevideo);
                break;
            case R.id.tv_complete_work:
                if (closeTaskWrite()) {
                    llAddDetail.setVisibility(View.GONE);

                } else {
                    if (addDataToWrok()) {
                        llAddDetail.setVisibility(View.GONE);
                    }
                }
                tvAddTask.setVisibility(View.VISIBLE);
                break;
            // 保存并添加下一条
            case R.id.tv_save:
                addDataToWrok();
                break;
            case R.id.tv_sub:
                submit();
                break;
            default:
                break;
        }
    }

    /**
     * 判断 值的有无
     */
    private boolean checkWorkInfo() {

        if (TextUtils.isEmpty(etInputTitle.getText().toString().trim())) {
            showToast("请填写任务标题");
            return false;
        }

        if (TextUtils.isEmpty(tvDeviceName.getText().toString().trim())) {

            showToast("请选择设备");
            return false;
        }
        if (TextUtils.isEmpty(etInputAddress.getText().toString().trim())) {

            showToast("请填写位置区域");
            return false;
        }
        if (TextUtils.isEmpty(etInputContent.getText().toString().trim())) {

            showToast("请填写检查内容");
            return false;
        }
        return true;
    }

    /**
     * 进行增加
     */
    private boolean addDataToWrok() {

        if (!checkWorkInfo()) {
            return false;
        }
        detailsBean = new WorkAddCheckBean.WorkInspectDetailsBean();

        //任务标题
        detailsBean.setTitle(etInputTitle.getText().toString().trim());

        //选择设备
        detailsBean.setBusinessThreeCode(dataCode);

        //位置区域
        detailsBean.setRegion(etInputAddress.getText().toString().trim());

        //检查内容
        detailsBean.setInfo(etInputContent.getText().toString().trim());

        String ursStr = PhotoUtils.getPhotoUrl("oa/workCheck/", snplPhotosWork, mUploadMap, true);
        detailsBean.setPictures(ursStr);
        // 短视频
        detailsBean.setMp4_path(mUploadKey);
        //设置布局类型
        detailsBean.setItemType(1);

        isTrue = true;//重置状态

        if (mUploadMap.size() != 0) {

            OSSUtils.initOSS(this).asyncPutImages(mUploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        if (isTrue) {
                            initTaskData(detailsBean);
                            isTrue = false;
                        }
                    });
                }

            });
        } else {
            initTaskData(detailsBean);
        }
        return true;
    }

    /**
     * 提交
     */
    private void submit() {

        String task_title = etTaskName.getText().toString().trim();
        if (TextUtils.isEmpty(task_title)) {
            showToast("请输入任务标题");
            return;
        }
        String task_time = tvUpdateTime.getText().toString().trim();
        if (TextUtils.isEmpty(task_time)) {
            showToast("请输入整改期限");
            return;
        }
        if (checkAdapter == null || checkAdapter.getData().size() == 0) {
            showToast("任务明细请最少填写一条");
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

        WorkAddCheckBean workAddCheckBean = new WorkAddCheckBean();

        if (whoList.size() == 0) {
            //工作协同默认值
            workAddCheckBean.setAssigneeUserId(EanfangApplication.get().getUserId());
            workAddCheckBean.setAssigneeOrgCode(EanfangApplication.get().getOrgCode());
        } else {
            //工作协同默认值
            workAddCheckBean.setAssigneeUserId(Long.parseLong(whoList.get(0).getUserId()));
            workAddCheckBean.setAssigneeOrgCode(whoList.get(0).getOrgCode());
        }

        workAddCheckBean.setCompanyName(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
        workAddCheckBean.setTitle(task_title);
        workAddCheckBean.setChangeDeadlineTime(task_time);

        workAddCheckBean.setWorkInspectDetails(checkAdapter.getData());

        doHttp(JSON.toJSONString(workAddCheckBean));

    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(NewApiService.ADD_WORK_CHECK)
                .upJson(jsonString)
                .execute(new EanfangCallback<WorkInspectEntity>(this, true, WorkInspectEntity.class, (bean) -> {
                    runOnUiThread(() -> {
                        Bundle bundle = new Bundle();
                        Message message = new Message();
                        message.setMsgContent("新建点检成功");
                        message.setTip("确定");
                        bundle.putSerializable("message", message);
                        EventBus.getDefault().post("addCheckSuccess");
                        JumpItent.jump(AddNewCheckActivity.this, StateChangeActivity.class, bundle);

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
                        if (bean.getWorkInspectDetails() != null && bean.getWorkInspectDetails().size() > 0 && !TextUtils.isEmpty(bean.getWorkInspectDetails().get(0).getPictures())) {
                            bundle.putString("picUrl", bean.getWorkInspectDetails().get(0).getPictures().split(",")[0]);
                        }
                        b.putString("creatTime", etTaskName.getText().toString().trim());
                        b.putString("workerName", EanfangApplication.get().getUser().getAccount().getRealName());
                        b.putString("status", "0");
                        b.putString("shareType", "5");

                        new SendContactUtils(b, handler, groupList, DialogUtil.createLoadingDialog(AddNewCheckActivity.this), "设备检点").send();


                    });


                }));

    }

    private void initTaskData(WorkAddCheckBean.WorkInspectDetailsBean bean) {
        if (mCheckList == null) {
            mCheckList = new ArrayList<>();
        }

        mCheckList.add(bean);

        if (checkAdapter == null) {
            rvCheck.setLayoutManager(new LinearLayoutManager(this));
            checkAdapter = new CheckAdapter(mCheckList, this);
            checkAdapter.bindToRecyclerView(rvCheck);
            checkAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    WorkAddCheckBean.WorkInspectDetailsBean b = (WorkAddCheckBean.WorkInspectDetailsBean) adapter.getData().get(position);
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
            checkAdapter.setNewData(mCheckList);
        }

        clearTaskData();//清空数据
    }

    private void clearTaskData() {
        // 检查内容
        etInputContent.setText("");
        // 任务标题
        etInputTitle.setText("");
        // 设备
        tvDeviceName.setText("");
        // 位置
        etInputAddress.setText("");
        mUploadMap.clear();
        snplPhotosWork.setData(new ArrayList<String>());

        mVieoPath = "";
        mUploadKey = "";
        ivTakevideoCheck.setVisibility(View.INVISIBLE);
        rlThumbnailCheck.setVisibility(View.GONE);
    }

    private void inputVoice(EditText editText) {
        PermissionUtils.get(this).getVoicePermission(() -> {
            RecognitionManager.getSingleton().startRecognitionWithDialog(AddNewCheckActivity.this, editText);
        });
    }

    private boolean closeTaskWrite() {
        return TextUtils.isEmpty(etInputContent.getText().toString().trim()) &&
                TextUtils.isEmpty(etInputTitle.getText().toString().trim()) &&
                TextUtils.isEmpty(tvDeviceName.getText().toString().trim()) &&
                TextUtils.isEmpty(etInputAddress.getText().toString().trim()) &&
                snplPhotosWork.getData().size() == 0 && TextUtils.isEmpty(mUploadKey);

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


    /**
     * 放弃新建点检
     */
    private void giveUp() {
        new TrueFalseDialog(this, "系统提示", "是否放弃新建设备点检？", () -> {
            InputMethodManager imm = (InputMethodManager) etTaskName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etTaskName.getApplicationWindowToken(), 0);
            }
            finish();
        }).showDialog();
    }

    /**
     * 时间选择回调
     */
    @Override
    public void getData(String time) {
        if (StringUtils.isEmpty(time) || " ".equals(time)) {
            tvUpdateTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            tvUpdateTime.setText(time);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_FAULTDEVICEINFO && resultCode == RESULT_DATACODE) {// 选择故障设备
            dataCode = data.getStringExtra("dataCode");
            String text = Config.get().getBusinessNameByCode(dataCode, 3);
            tvDeviceName.setText(text);
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GROUP) {
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

    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            rlThumbnailCheck.setVisibility(View.VISIBLE);
            mVieoPath = takeVdideoMode.getMImagePath();
            mUploadKey = takeVdideoMode.getMKey();
            if (!StringUtils.isEmpty(mVieoPath)) {

                if (ivTakevideoCheck.getVisibility() == View.INVISIBLE) {
                    ivTakevideoCheck.setVisibility(View.VISIBLE);
                }

                ivTakevideoCheck.setImageBitmap(PhotoUtils.getVideoThumbnail(mVieoPath, 100, 100, MINI_KIND));
            }

        }
    }

    /**
     * 人员选择
     */
    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {


//        if (presonList.size() > 0) {
        Set hashSet = new HashSet();
//            if (mFlag == 2) {
//                hashSet.addAll(sendPersonAdapter.getData());
//            } else
        if (mFlag == 4) {
            hashSet.addAll(groupAdaptet.getData());
            hashSet.addAll(presonList);
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

        if (mFlag == 2) {
            sendPersonAdapter.setNewData(whoList);
        } else if (mFlag == 4) {
            groupAdaptet.setNewData(groupList);
        }

//    }
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
}
