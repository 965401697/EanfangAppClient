package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.biz.model.bean.ZjZgBean;
import com.eanfang.biz.model.entity.BughandleDetailEntity;
import com.eanfang.biz.model.entity.BughandleParamEntity;
import com.eanfang.biz.model.entity.BughandleUseDeviceEntity;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.LookMaterialAdapter;
import net.eanfang.client.ui.adapter.LookParamAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.MateraInfoView;

import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  11:05
 * @email houzhongzhou@yeah.net
 * @desc 电话未解决
 */

public class LookTroubleDetailActivity extends BaseClientActivity /*implements View.OnClickListener */ {
    public static final String TAG = LookTroubleDetailActivity.class.getSimpleName();


    @BindView(R.id.tv_repair_misinformation)
    TextView tvRepairMisinformation;
    @BindView(R.id.rl_troublePhoto)
    RelativeLayout rlTroublePhoto;
    @BindView(R.id.tv_trouble_title)
    TextView tvTroubleTitle;
    // 耗用材料
    @BindView(R.id.rcy_consumable)
    RecyclerView rcyConsumable;
    // 使用建议
    @BindView(R.id.tv_trouble_use_advice)
    TextView tvTroubleUseAdvice;

    private TextView tv_device_no;

    private TextView tv_device_location;

    //位置编号
    @BindView(R.id.tv_location_num)
    TextView tvLocationNum;

    private TextView et_trouble_desc;
    private TextView et_trouble_point;
    private TextView et_trouble_reason;
    private TextView et_trouble_deal;
    private TextView tv_use_goods;//耗用材料


    private List<BughandleParamEntity> mDataList;
    private List<BughandleUseDeviceEntity> mDataList_2;
    private LookParamAdapter paramAdapter;


    private LookMaterialAdapter materialAdapter;
    private BughandleDetailEntity bughandleDetailEntity;
    /**
     * 故障明细的id
     */
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_look_trouble_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        getData();
    }


    private void getData() {
        EanfangHttp.get(RepairApi.GET_BUGHANDLE_DETAIL_INFO)
                .params("id", id)
                .execute(new EanfangCallback<BughandleDetailEntity>(this, true, BughandleDetailEntity.class, (bean) -> {
                    bughandleDetailEntity = bean;
                    initData(bean);
                }));

    }

    public void initData(BughandleDetailEntity bughandleDetailEntity) {
        mDataList = bughandleDetailEntity.getParamEntityList();
        mDataList_2 = bughandleDetailEntity.getUseDeviceEntityList();
        initAdapter();

        if (mDataList_2.size() != 0) {
            rcyConsumable.setAdapter(materialAdapter);
        } else {
            //无数据时隐藏
            rcyConsumable.setVisibility(View.GONE);
            tv_use_goods.setVisibility(View.GONE);
        }

        if (bughandleDetailEntity.getFailureEntity() != null) {
            if (StrUtil.isNotBlank(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode())) {
                String bugOne = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 1);
                String bugTwo = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 2);
                String bugThree = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 3);
                tvTroubleTitle.setText(bugOne + "-" + bugTwo + "-" + bugThree);
            } else {
                tvTroubleTitle.setText("");
            }
            // 故障设备
//            tv_trouble_device.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getDeviceName()).orElse(""));
            // 品牌型号
//            tv_brand_model.setText(Optional.ofNullable(Config.get().getModelNameByCode(bughandleDetailEntity.getFailureEntity().getModelCode(), 2)).orElse(""));

            tv_device_no.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getDeviceNo()).orElse(""));

            tv_device_location.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugPosition()).orElse(""));

            tvLocationNum.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getLocationNumber()).orElse(""));

            et_trouble_desc.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugDescription()).orElse(""));
        }
        et_trouble_point.setText(Optional.ofNullable(bughandleDetailEntity.getCheckProcess()).orElse(""));
        et_trouble_reason.setText(Optional.ofNullable(bughandleDetailEntity.getCause()).orElse(""));
        et_trouble_deal.setText(Optional.ofNullable(bughandleDetailEntity.getHandle()).orElse(""));
        tvRepairMisinformation.setText(GetConstDataUtils.getRepairMisinformationList().get(bughandleDetailEntity.getFailureEntity().getIsMisinformation()));
        // 维修结论
        if (bughandleDetailEntity.getStatus() != null) {
//            tv_repair_conclusion.setText(Optional.ofNullable(GetConstDataUtils.getBugDetailList().get(bughandleDetailEntity.getStatus())).orElse(""));
        }
        // 使用建议
        if (bughandleDetailEntity.getUseAdvice() != null) {
            tvTroubleUseAdvice.setText(bughandleDetailEntity.getUseAdvice());
        }
    }


    private void initView() {
        id = getIntent().getLongExtra(Constant.ID, 0);
        //add by hou on 2017.8.2
        tv_use_goods = findViewById(R.id.tv_use_goods);
        tv_device_no = findViewById(R.id.tv_device_no);
        tv_device_location = findViewById(R.id.tv_device_location);
        et_trouble_desc = findViewById(R.id.et_trouble_desc);
        et_trouble_point = findViewById(R.id.et_trouble_point);
        et_trouble_reason = findViewById(R.id.et_trouble_reason);
        et_trouble_deal = findViewById(R.id.et_trouble_deal);
        supprotToolbar();
        setTitle("故障明细");
    }

    private void initAdapter() {
        materialAdapter = new LookMaterialAdapter(R.layout.item_quotation_detail, mDataList_2);
        rcyConsumable.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcyConsumable.setLayoutManager(new LinearLayoutManager(this));
        rcyConsumable.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new MateraInfoView(LookTroubleDetailActivity.this, true,
                        mDataList_2.get(position)).show();
            }
        });


//        paramAdapter = new LookParamAdapter(R.layout.item_look_parm, (ArrayList) mDataList);
//        rcy_parameter.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));
//        rcy_parameter.setLayoutManager(new LinearLayoutManager(this));
//        rcy_parameter.setAdapter(paramAdapter);
    }


    @OnClick(R.id.rl_troublePhoto)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        JumpItent.jump(LookTroubleDetailActivity.this, TroubleDetailLookPhotoActivity.class, bundle);
    }
}

