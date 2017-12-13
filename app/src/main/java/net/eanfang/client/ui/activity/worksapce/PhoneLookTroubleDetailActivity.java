package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.delegate.BGASortableDelegate;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.LookMaterialAdapter;
import net.eanfang.client.ui.adapter.LookParamAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.FillRepairInfoBean;
import net.eanfang.client.ui.model.WorkspaceDetailBean;
import net.eanfang.client.ui.widget.MateraInfoView;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  11:24
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PhoneLookTroubleDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = PhoneLookTroubleDetailActivity.class.getSimpleName();

    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_2 = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_3 = 3;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_4 = 4;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_5 = 5;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_6 = 6;

    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = -1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_2 = -2;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_3 = -3;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_4 = -4;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_5 = -5;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_6 = -6;

    private TextView tv_trouble_device;
    private RelativeLayout rl_trouble_device;
    private TextView tv_brand_model;
    private RelativeLayout rl_brand_model;
    private TextView tv_device_no;
    private RelativeLayout rl_device_no;
    private TextView tv_device_location;
    private RelativeLayout rl_device_location;
    private TextView tv_add;
    private RecyclerView rcy_parameter;
    private TextView et_trouble_desc;
    private LinearLayout ll_trouble_desc;
    private TextView et_trouble_point;
    private LinearLayout ll_trouble_point;
    private TextView et_trouble_reason;
    private LinearLayout ll_trouble_reason;
    private TextView et_trouble_deal;
    private LinearLayout ll_trouble_deal, ll_gone_c;
    private TextView tv_use_goods;//耗用材料
    /**
     * 故障表象 （3张）
     */
    private BGASortableNinePhotoLayout snpl_moment_add_photos;
    /**
     * 工具及蓝布 （3张）
     */
    private BGASortableNinePhotoLayout snpl_monitor_add_photos;
    /**
     * 故障点照片 （3张）
     */
    private BGASortableNinePhotoLayout snpl_tools_package_add_photos;
    /**
     * 处理后现场 （3张）
     */
    private BGASortableNinePhotoLayout snpl_after_processing_locale;
    /**
     * 设备回装 （3张）
     */
    private BGASortableNinePhotoLayout snpl_machine_fit_back;
    /**
     * 故障恢复后表象 （3张）
     */
    private BGASortableNinePhotoLayout snpl_failure_recover_phenomena;

    private TextView tv_add_consumable;
    private RecyclerView rcy_consumable;
    private Button btn_add_trouble;
    private TextView tv_trouble_title;
    private OptionsPickerView pvOptions;
    private List<String> options = new ArrayList<>();
    private List<WorkspaceDetailBean.BughandledetaillistBean.BughandledetailparamBean> mDataList;
    private List<WorkspaceDetailBean.BughandledetaillistBean.BughandledetailmaterialBean> mDataList_2;
    private LookParamAdapter paramAdapter;
    private HashMap<String, String> uploadMap;
    //2017年7月21日
    /**
     * 维修结果
     */
    private TextView tv_repair_conclusion;
    /**
     * 故障表象 （3张）
     */
    private ArrayList<String> picList1;
    /**
     * 工具及蓝布 （3张）
     */
    private ArrayList<String> picList2;
    /**
     * 故障点照片 （3张）
     */
    private ArrayList<String> picList3;
    //2017年7月21日
    /**
     * 处理后现场 （3张）
     */
    private ArrayList<String> picList4;
    /**
     * 设备回装 （3张）
     */
    private ArrayList<String> picList5;
    /**
     * 故障恢复后表象 （3张）
     */
    private ArrayList<String> picList6;
    private int position;
    private LookMaterialAdapter materialAdapter;
    private WorkspaceDetailBean.BughandledetaillistBean.DetailBean bean;
    private RelativeLayout rel_parame, rel_result;
    /**
     * 2017年8月1日
     * 故障明细的id
     */
    private Integer detailId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_trouble_detail);
        initView();
        rcy_parameter.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcy_parameter.setLayoutManager(new LinearLayoutManager(this));
        rcy_parameter.setAdapter(paramAdapter);
        getData();
    }

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList(WorkspaceDetailBean.BughandledetaillistBean.DetailBean bean) {
        uploadMap = new HashMap<>();
        picList1 = new ArrayList<>();
        picList2 = new ArrayList<>();
        picList3 = new ArrayList<>();
        //2017年7月21日
        picList4 = new ArrayList<>();
        picList5 = new ArrayList<>();
        picList6 = new ArrayList<>();
        //修改小bug 图片读取问题
        if (StringUtils.isValid(bean.getPic1())) {
            picList1.add(bean.getPic1());
        }
        if (StringUtils.isValid(bean.getPic2())) {
            picList1.add(bean.getPic2());
        }
        if (StringUtils.isValid(bean.getPic3())) {
            picList1.add(bean.getPic3());
        }
        if (StringUtils.isValid(bean.getPic4())) {
            picList2.add(bean.getPic4());
        }
        if (StringUtils.isValid(bean.getPic5())) {
            picList2.add(bean.getPic5());
        }
        if (StringUtils.isValid(bean.getPic6())) {
            picList2.add(bean.getPic6());
        }
        if (StringUtils.isValid(bean.getPic7())) {
            picList3.add(bean.getPic7());
        }
        if (StringUtils.isValid(bean.getPic8())) {
            picList3.add(bean.getPic8());
        }
        if (StringUtils.isValid(bean.getPic9())) {
            picList3.add(bean.getPic9());
        }
        //2017年7月21日
        if (StringUtils.isValid(bean.getPic10())) {
            picList4.add(bean.getPic10());
        }
        if (StringUtils.isValid(bean.getPic11())) {
            picList4.add(bean.getPic11());
        }
        if (StringUtils.isValid(bean.getPic12())) {
            picList4.add(bean.getPic12());
        }
        if (StringUtils.isValid(bean.getPic13())) {
            picList5.add(bean.getPic13());
        }
        if (StringUtils.isValid(bean.getPic14())) {
            picList5.add(bean.getPic14());
        }
        if (StringUtils.isValid(bean.getPic15())) {
            picList5.add(bean.getPic15());
        }
        if (StringUtils.isValid(bean.getPic16())) {
            picList6.add(bean.getPic16());
        }
        if (StringUtils.isValid(bean.getPic17())) {
            picList6.add(bean.getPic17());
        }
        if (StringUtils.isValid(bean.getPic18())) {
            picList6.add(bean.getPic18());
        }
    }

    private void getData() {
        WorkspaceDetailBean.BughandledetaillistBean detail = null;
        Intent intent = getIntent();
        detailId = intent.getIntExtra("detailId", 0);
        if (detailId == 0) {
            detail = (WorkspaceDetailBean.BughandledetaillistBean) intent.getSerializableExtra("bean");
            initData(detail);

        } else {
            JSONObject object = new JSONObject();
            try {
                object.put("id", detailId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            EanfangHttp.get(ApiService.BUG_DETAIL_INFO)
                    .tag(this)
                    .params("json", object.toString())
                    .execute(new EanfangCallback<WorkspaceDetailBean>(this, true) {
                        @Override
                        public void onSuccess(WorkspaceDetailBean bean) {
                            super.onSuccess(bean);
                            WorkspaceDetailBean.BughandledetaillistBean detail = (WorkspaceDetailBean.BughandledetaillistBean) bean.getBughandledetaillist();
                            initData(detail);
                        }

                        @Override
                        public void onFail(Integer code, String message, JSONObject jsonObject) {
                            super.onFail(code, message, jsonObject);
                            showToast(message);
                        }
                    });
        }
    }


    public void initData(WorkspaceDetailBean.BughandledetaillistBean detail) {
        bean = detail.getDetail();
        position = getIntent().getIntExtra("position", 0);
        mDataList = detail.getBughandledetailparam();
        paramAdapter = new LookParamAdapter(R.layout.item_look_parm, (ArrayList) mDataList);
        mDataList_2 = detail.getBughandledetailmaterial();
        initImgUrlList(bean);
        initNinePhoto();
        initAdapter();

        if (mDataList.size() != 0) {
            rcy_parameter.setAdapter(paramAdapter);
        } else {
            rcy_parameter.setVisibility(View.GONE);
            rel_parame.setVisibility(View.GONE);
        }
        if (mDataList_2.size() != 0) {
            rcy_consumable.setAdapter(materialAdapter);
        } else {
            //无数据时隐藏
            rcy_consumable.setVisibility(View.GONE);
            tv_use_goods.setVisibility(View.GONE);
        }


        if (bean.getTitle() != null) {
            tv_trouble_title.setText(bean.getTitle());
        }
        //故障设备
        if (StringUtils.isValid(bean.getInstrument())) {
            tv_trouble_device.setText(bean.getInstrument());
        }
        //品牌型号
        if (StringUtils.isValid(bean.getModelnum())) {
            tv_brand_model.setText(bean.getModelnum());
        }
        //设备编号
        if (StringUtils.isValid(bean.getEquipmentcode())) {
            tv_device_no.setText(bean.getEquipmentcode());
        }
        //故障位置
        if (StringUtils.isValid(bean.getEquipmentposition())) {
            tv_device_location.setText(bean.getEquipmentposition());
        }
        //故障描述
        if (StringUtils.isValid(bean.getDescription())) {
            et_trouble_desc.setText(bean.getDescription());
        }
        //检查方法
        if (StringUtils.isValid(bean.getCheckprocess())) {
            et_trouble_point.setText(bean.getCheckprocess());
        }
        //故障原因
        if (StringUtils.isValid(bean.getCause())) {
            et_trouble_reason.setText(bean.getCause());
        }
        //故障处理
        if (StringUtils.isValid(bean.getHandle())) {
            et_trouble_deal.setText(bean.getHandle());
        }
        //维修结论
        if (StringUtils.isValid(bean.getRepairconclusion())) {
            tv_repair_conclusion.setText(bean.getRepairconclusion());
        }
    }


    private void initView() {
        ll_gone_c = (LinearLayout) findViewById(R.id.ll_gone_c);
        ll_gone_c.setVisibility(View.GONE);
        rel_parame = (RelativeLayout) findViewById(R.id.rel_parame);
        //add by hou on 2017.8.2
        tv_use_goods = (TextView) findViewById(R.id.tv_use_goods);

        rel_result = (RelativeLayout) findViewById(R.id.rel_result);
        tv_trouble_device = (TextView) findViewById(R.id.tv_trouble_device);
        rl_trouble_device = (RelativeLayout) findViewById(R.id.rl_trouble_device);
        tv_brand_model = (TextView) findViewById(R.id.tv_brand_model);
        rl_brand_model = (RelativeLayout) findViewById(R.id.rl_brand_model);
        tv_device_no = (TextView) findViewById(R.id.tv_device_no);
        rl_device_no = (RelativeLayout) findViewById(R.id.rl_device_no);
        tv_device_location = (TextView) findViewById(R.id.tv_device_location);
        rl_device_location = (RelativeLayout) findViewById(R.id.rl_device_location);
        tv_add = (TextView) findViewById(R.id.tv_add);
        rcy_parameter = (RecyclerView) findViewById(R.id.rcy_parameter);
        et_trouble_desc = (TextView) findViewById(R.id.et_trouble_desc);
        ll_trouble_desc = (LinearLayout) findViewById(R.id.ll_trouble_desc);
        et_trouble_point = (TextView) findViewById(R.id.et_trouble_point);
        ll_trouble_point = (LinearLayout) findViewById(R.id.ll_trouble_point);
        et_trouble_reason = (TextView) findViewById(R.id.et_trouble_reason);
        ll_trouble_reason = (LinearLayout) findViewById(R.id.ll_trouble_reason);
        et_trouble_deal = (TextView) findViewById(R.id.et_trouble_deal);
        ll_trouble_deal = (LinearLayout) findViewById(R.id.ll_trouble_deal);
        //2017年7月20日
        //维修结论
        tv_repair_conclusion = (TextView) findViewById(R.id.tv_repair_conclusion);
        snpl_moment_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_moment_add_photos);
        snpl_monitor_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_monitor_add_photos);
        snpl_tools_package_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_tools_package_add_photos);
        //2017年7月21日
        snpl_after_processing_locale = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_after_processing_locale);
        snpl_machine_fit_back = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_machine_fit_back);
        snpl_failure_recover_phenomena = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_failure_recover_phenomena);

        tv_add_consumable = (TextView) findViewById(R.id.tv_add_consumable);
        rcy_consumable = (RecyclerView) findViewById(R.id.rcy_consumable);


        btn_add_trouble = (Button) findViewById(R.id.btn_add_trouble);

        btn_add_trouble.setOnClickListener(this);
        tv_trouble_title = (TextView) findViewById(R.id.tv_trouble_title);
        tv_trouble_title.setOnClickListener(this);

        tv_add.setVisibility(View.INVISIBLE);
        tv_add_consumable.setVisibility(View.INVISIBLE);
        btn_add_trouble.setVisibility(View.INVISIBLE);
        supprotToolbar();
        setTitle("故障明细");
    }

    private void initAdapter() {
        materialAdapter = new LookMaterialAdapter(R.layout.item_quotation_detail, mDataList_2);
        rcy_consumable.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcy_consumable.setLayoutManager(new LinearLayoutManager(this));


        rcy_consumable.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new MateraInfoView(PhoneLookTroubleDetailActivity.this, true,
                        mDataList_2.get(position).getEquipmenttype(),
                        mDataList_2.get(position).getEquipmentname()
                        , mDataList_2.get(position).getEquipmentmodel(),
                        mDataList_2.get(position).getNum()
                        , mDataList_2.get(position).getMemo()
                ).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
    }

    private void initNinePhoto() {
        snpl_moment_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snpl_monitor_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snpl_tools_package_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        snpl_after_processing_locale.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
        snpl_machine_fit_back.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_5, REQUEST_CODE_PHOTO_PREVIEW_5));
        snpl_failure_recover_phenomena.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_6, REQUEST_CODE_PHOTO_PREVIEW_6));

        snpl_moment_add_photos.setData(picList1);
        snpl_monitor_add_photos.setData(picList2);
        snpl_tools_package_add_photos.setData(picList3);
        //2017年7月21日
        snpl_after_processing_locale.setData(picList4);
        snpl_machine_fit_back.setData(picList5);
        snpl_failure_recover_phenomena.setData(picList6);

        snpl_moment_add_photos.setEditable(false);
        snpl_monitor_add_photos.setEditable(false);
        snpl_tools_package_add_photos.setEditable(false);
        //2017年7月21日
        snpl_after_processing_locale.setEditable(false);
        snpl_machine_fit_back.setEditable(false);
        snpl_failure_recover_phenomena.setEditable(false);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_1:
                snpl_moment_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snpl_monitor_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snpl_tools_package_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            //2017年7月21日
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snpl_after_processing_locale.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_5:
                snpl_machine_fit_back.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_6:
                snpl_failure_recover_phenomena.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snpl_moment_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_2:
                snpl_monitor_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_3:
                snpl_tools_package_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snpl_after_processing_locale.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_5:
                snpl_machine_fit_back.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_6:
                snpl_failure_recover_phenomena.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case 10009:
                FillRepairInfoBean.BughandledetaillistBean.BughandledetailmateriallistBean bugBean = (FillRepairInfoBean.BughandledetaillistBean.BughandledetailmateriallistBean) data.getSerializableExtra("bean");
//                bean.getBughandledetailmateriallist().add(bugBean);
                materialAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}

