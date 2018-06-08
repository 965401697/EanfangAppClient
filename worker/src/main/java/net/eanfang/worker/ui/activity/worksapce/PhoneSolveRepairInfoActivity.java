package net.eanfang.worker.ui.activity.worksapce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleConfirmEntity;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.FillTroubleDetailAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:35
 * @email houzhongzhou@yeah.net
 * @desc 电话解决
 */

public class PhoneSolveRepairInfoActivity extends BaseWorkerActivity {


    public static final int REQUEST_CODE_UPDATE_TROUBLE = 10001;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_4 = 4;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_4 = 104;
    private final Activity activity = this;
    @BindView(R.id.tv_add_fault)
    TextView tvAddFault;
    private RecyclerView rv_trouble;
    /*
     * 单据照片 (3张)
     */
    private BGASortableNinePhotoLayout snpl_form_photos;
    private TextView tv_commit;
    private List<BughandleDetailEntity> mDataList;
    private FillTroubleDetailAdapter quotationDetailAdapter;
    private HashMap<String, String> uploadMap = new HashMap<>();
    private String companyName;
    private Long companyId;

    // 地址
    private String mAddress = "";
    private String mAddressCode = "";

    /*
     * 遗留问题
     */
    private EditText et_remain_question;
    private BughandleConfirmEntity bughandleConfirmEntity;

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            Intent intent = new Intent(PhoneSolveRepairInfoActivity.this, PhoneSolveTroubleDetailActivity.class);
            intent.putExtra("id", bughandleConfirmEntity.getDetailEntityList().get(position).getBusRepairFailureId());
            intent.putExtra("bean", bughandleConfirmEntity.getDetailEntityList().get(position));
            intent.putExtra("confirmId", bughandleConfirmEntity.getId());
            intent.putExtra("position", position);
            intent.putExtra("companyName", companyName);
            startActivityForResult(intent, REQUEST_CODE_UPDATE_TROUBLE);
//            ((BaseViewHolder) rv_trouble.getChildViewHolder(rv_trouble.getChildAt(position))).setText(R.id.tv_detai_status, "");
        }
    };
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps_fill_repair_info);
        ButterKnife.bind(this);

        initView();
        initData();
        setListener();
        initNinePhoto();
    }

    private void initView() {
        setTitle("电话解决");
        setLeftBack();
        rv_trouble = (RecyclerView) findViewById(R.id.rv_trouble);
        et_remain_question = (EditText) findViewById(R.id.et_remain_question);
        snpl_form_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_form_photos);
        tv_commit = (TextView) findViewById(R.id.tv_commit);

    }

    public void setListener() {
        //添加故障
        tvAddFault.setOnClickListener(v -> {
            Intent intent = new Intent(PhoneSolveRepairInfoActivity.this, AddTroubleActivity.class);
            intent.putExtra("repaid", id);
            startActivityForResult(intent, 10003);
        });

        tv_commit.setOnClickListener(new MultiClickListener(this, this::checkInfo, () -> {
            new TrueFalseDialog(activity, "系统提示", "是否确定提交完工？", () -> {
                fillBean();
                submit();
            }).showDialog();
        }));

        rv_trouble.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_pic) {
                    ArrayList<String> picList = new ArrayList<String>();
                    String[] urls = mDataList.get(position).getFailureEntity().getPictures().split(",");
                    if (urls.length >= 1) {
                        picList.add(BuildConfig.OSS_SERVER + urls[0]);
                    }
                    if (urls.length >= 2) {
                        picList.add(BuildConfig.OSS_SERVER + urls[1]);
                    }
                    if (urls.length >= 3) {
                        picList.add(BuildConfig.OSS_SERVER + urls[2]);
                    }
                    if (picList.size() == 0) {
//                        showToast("当前没有图片");
                        return;
                    }
                    ImagePerviewUtil.perviewImage(PhoneSolveRepairInfoActivity.this, picList);
                }
            }
        });
    }

    private void initData() {
        id = getIntent().getLongExtra("orderId", 0);
        companyName = getIntent().getStringExtra("companyName");
        companyId = getIntent().getLongExtra("companyUid", 0);
        new Thread(() -> {
            // 获取经纬度
            LocationUtil.location(this, (location) -> {
                mAddress = location.getCity() + location.getDistrict();
                mAddressCode = Config.get().getAreaCodeByName(location.getCity(), location.getDistrict());
            });
        }).start();
        doHttpOrderDetail(id);
    }

    private void doHttpOrderDetail(Long id) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("repairId", id + "");
        EanfangHttp.post(RepairApi.POST_BUGHANDLE_PREPARE)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<BughandleConfirmEntity>(this, true, BughandleConfirmEntity.class, (bean) -> {

                    bughandleConfirmEntity = bean;
                    initAdapter();
                }));
    }

    private void initAdapter() {
        mDataList = bughandleConfirmEntity.getDetailEntityList();
        quotationDetailAdapter = new FillTroubleDetailAdapter(R.layout.layout_trouble_detail, mDataList);
        rv_trouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rv_trouble.setLayoutManager(new LinearLayoutManager(this));
        rv_trouble.setAdapter(quotationDetailAdapter);
        rv_trouble.addOnItemTouchListener(onItemClickListener);
        quotationDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.rl_item_detail:
                    Intent intent = new Intent(PhoneSolveRepairInfoActivity.this, QuotationDetailActivity.class);
                    intent.putExtra("data", mDataList.get(position));
                    startActivity(intent);
                    break;
                case R.id.tv_delete:
                    mDataList.remove(position);
                    quotationDetailAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        });
    }

    private void initNinePhoto() {
        snpl_form_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
    }

    private boolean checkInfo() {

        String remainQuestion = et_remain_question.getText().toString().trim();
        if (StringUtils.isEmpty(remainQuestion)) {
            showToast("请填写遗留问题");
            return false;
        }

        if (bughandleConfirmEntity.getDetailEntityList() != null) {
            //增加限制，需要先完善故障处理 在提交
            for (int i = 0; i < bughandleConfirmEntity.getDetailEntityList().size(); i++) {
                if (StringUtils.isEmpty(bughandleConfirmEntity.getDetailEntityList().get(i).getCheckProcess())) {
                    showToast("请完善第" + (i + 1) + "条故障处理明细");
                    return false;
                }
            }
        }
        return true;
    }

    private BughandleConfirmEntity fillBean() {
        bughandleConfirmEntity.setBusRepairOrderId(id);

//        bughandleConfirmEntity.setOverTime(GetDateUtils.getDate(tv_over_time.getText().toString().trim()));
//        bughandleConfirmEntity.setWorkHour(repairTime);
        bughandleConfirmEntity.setLeftoverProblem(et_remain_question.getText().toString().trim());
//        uploadMap.clear();
        //单据照片 （3张）
        String afterHandlePic = PhotoUtils.getPhotoUrl(snpl_form_photos, uploadMap, false);
        bughandleConfirmEntity.setInvoicesPictures(afterHandlePic);

        // 签退时间
        bughandleConfirmEntity.setSignOutTime(new Date(System.currentTimeMillis()));
        //签退地点
        bughandleConfirmEntity.setSignOutAddress(mAddress);
        bughandleConfirmEntity.setSignOutCode(mAddressCode);
        return bughandleConfirmEntity;
    }

    //提交完工
    private void submit() {
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        String requestJson = JSONObject.toJSONString(bughandleConfirmEntity);
                        doHttp(requestJson);
                    });
                }
            });
            return;
        }
        String requestJson = JSONObject.toJSONString(bughandleConfirmEntity);
        doHttp(requestJson);
    }

    /**
     * 完工网络请求
     */
    private void doHttp(String jsonString) {
        EanfangHttp.post(RepairApi.POST_REPAIR_FINISH_WORK)
                .upJson(jsonString)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    showToast("提交成功");
                    finishSelf();
                }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snpl_form_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snpl_form_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_UPDATE_TROUBLE:
                BughandleDetailEntity resultBean = (BughandleDetailEntity) data.getSerializableExtra("bean");
                if (resultBean == null) {
                    return;
                }
                int position = data.getIntExtra("position", 0);
                mDataList.remove(position);
                mDataList.add(resultBean);
                quotationDetailAdapter.notifyDataSetChanged();
                break;
            case 10003:
                if (data.getSerializableExtra("beans") == null) {
                    return;
                }
                RepairFailureEntity bugBean = (RepairFailureEntity) data.getSerializableExtra("beans");
                BughandleDetailEntity bughandleDetailEntity = new BughandleDetailEntity();
                bughandleDetailEntity.setBusRepairFailureId(bugBean.getId());
                bughandleDetailEntity.setFailureEntity(bugBean);
                mDataList.add(bughandleDetailEntity);
                quotationDetailAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            new TrueFalseDialog(this, "系统提示", "是否放弃完善故障处理？", () -> {
                finish();
            }).showDialog();
        }
        return false;
    }
}

