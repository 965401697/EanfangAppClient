package net.eanfang.worker.ui.activity.worksapce;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.eanfang.witget.CustomRadioGroup;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.BughandleParamEntity;
import com.yaf.base.entity.BughandleUseDeviceEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.repair.SeeFaultParamActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.material.SeeMaterialActivity;
import net.eanfang.worker.ui.adapter.LookMaterialAdapter;
import net.eanfang.worker.ui.adapter.LookParamAdapter;
import net.eanfang.worker.ui.adapter.MaterialAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.widget.MateraInfoView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.util.V.v;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  11:24
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PhoneLookTroubleDetailActivity extends BaseWorkerActivity {
    //    public static final String TAG = PhoneLookTroubleDetailActivity.class.getSimpleName();

    // 故障描述
    @BindView(R.id.tv_trouble_desc)
    TextView tvTroubleDesc;
    // 设备名称
    @BindView(R.id.tv_trouble_title)
    TextView tvTroubleTitle;
    // 设备编号
    @BindView(R.id.tv_device_no)
    TextView tvDeviceNo;
    // 设备位置
    @BindView(R.id.tv_device_location)
    TextView tvDeviceLocation;
    //设备参数
    @BindView(R.id.rl_add_device_param)
    RelativeLayout rlAddDeviceParam;
    // 原因判断
    @BindView(R.id.tv_trouble_reason)
    TextView tvTroubleReason;
    //过程方法
    @BindView(R.id.tv_trouble_point)
    TextView tvTroublePoint;
    // 处理措施
    @BindView(R.id.tv_trouble_deal)
    TextView tvTroubleDeal;
    // 使用建议
    @BindView(R.id.tv_trouble_useAdvace)
    TextView tvTroubleUseAdvace;
    @BindView(R.id.rcy_consumable)
    RecyclerView rcyConsumable;
    @BindView(R.id.rg_yes)
    RadioButton rgYes;
    @BindView(R.id.rg_no)
    RadioButton rgNo;
    @BindView(R.id.rg_misreport)
    RadioGroup rgMisreport;

    // 维修结果
    @BindView(R.id.rg_repairResultOne)
    CustomRadioGroup rgRepairResultOne;
    // 修复方式
    @BindView(R.id.rg_repairResultTwo)
    CustomRadioGroup rgRepairResultTwo;

    private List<BughandleParamEntity> mDataList;
    private List<BughandleUseDeviceEntity> mDataList_2;
    private MaterialAdapter materialAdapter;
    private BughandleDetailEntity bughandleDetailEntity;
    /**
     * 2017年8月1日
     * 故障明细的id
     */
    private Integer detailId;
    private Long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_look_trouble_detail);
        setContentView(R.layout.activity_ps_look_trouble_detail);
        ButterKnife.bind(this);
        initView();
        getData();
        initListener();
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
        }


        if (bughandleDetailEntity.getFailureEntity() != null) {
            if (StringUtils.isValid(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode())) {
                String bugOne = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 1);
                String bugTwo = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 2);
                String bugThree = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 3);
                tvTroubleTitle.setText(bugThree);
            } else {
                tvTroubleTitle.setText("");
            }

            tvDeviceLocation.setText(v(() -> (Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugPosition()).orElse(""))));
            //故障描述
            tvTroubleDesc.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugDescription()).orElse(""));
            // 设备编号
            tvDeviceNo.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getDeviceNo()).orElse(""));
            tvTroublePoint.setText(Optional.ofNullable(bughandleDetailEntity.getCheckProcess()).orElse(""));
            tvTroubleReason.setText(Optional.ofNullable(bughandleDetailEntity.getCause()).orElse(""));
            tvTroubleDeal.setText(Optional.ofNullable(bughandleDetailEntity.getHandle()).orElse(""));
            tvTroubleUseAdvace.setText(Optional.ofNullable(bughandleDetailEntity.getUseAdvice()).orElse(""));
            // (0：否，1：是)
            if (bughandleDetailEntity.getFailureEntity().getIsMisinformation() == 0) {
                rgNo.setChecked(true);
                rgYes.setChecked(false);
            } else {
                rgNo.setChecked(false);
                rgYes.setChecked(true);

            }
        }
        //故障位置
        if (StringUtils.isValid(bughandleDetailEntity.getFailureEntity().getBugPosition())) {
            tvDeviceLocation.setText(bughandleDetailEntity.getFailureEntity().getBugPosition());
        }
        //维修结论
        if (bughandleDetailEntity.getStatus() != null) {
//            tv_repair_conclusion.setText(GetConstDataUtils.getBugDetailList().get(bughandleDetailEntity.getStatus()));
        }

        //添加维修结论
        addView(PhoneLookTroubleDetailActivity.this, rgRepairResultOne, GetConstDataUtils.getBugDetailList(), bughandleDetailEntity.getStatus());
        //添加维修结论
        addView(PhoneLookTroubleDetailActivity.this, rgRepairResultTwo, GetConstDataUtils.getBugDetailTwoList(bughandleDetailEntity.getStatusTwo()), bughandleDetailEntity.getStatusTwo());

    }


    private void initView() {
        id = getIntent().getLongExtra("id", 0);
        supprotToolbar();
        setTitle("故障明细");
        rcyConsumable.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcyConsumable.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initAdapter() {
        materialAdapter = new MaterialAdapter(R.layout.item_quotation_detail, mDataList_2);

        rcyConsumable.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bughandleUseDeviceEntity", mDataList_2.get(position));
                JumpItent.jump(PhoneLookTroubleDetailActivity.this, SeeMaterialActivity.class, bundle);
            }
        });

        rcyConsumable.setAdapter(materialAdapter);
    }

    private void initListener() {
        // 设备参数
        rlAddDeviceParam.setOnClickListener((v) -> {
            if (mDataList.size() <= 0) {
                showToast("暂无参数");
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("faultParam", (Serializable) mDataList);
                JumpItent.jump(PhoneLookTroubleDetailActivity.this, SeeFaultParamActivity.class, bundle);
            }
        });
    }

    /**
     * 动态添加维修结果
     */
    public static void addView(final Context context, CustomRadioGroup
            parent, List<String> list, int flag) {
        parent.removeAllViews();
        RadioGroup.LayoutParams pa = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < list.size(); i++) {
            final RadioButton radioButton = new RadioButton(context);
            pa.setMargins(22, 22, 22, 30);
            radioButton.setLayoutParams(pa);
            radioButton.setText(list.get(i));
            radioButton.setTag(i);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextSize(12);
            radioButton.setPadding(20, 20, 20, 20);
            radioButton.setButtonDrawable(null);
            if (flag == i) {
                radioButton.setChecked(true);
            }
            radioButton.setClickable(false);
            radioButton.setEnabled(false);
            radioButton.setTextColor(R.drawable.select_camera_text_back);
            radioButton.setBackgroundResource(R.drawable.select_camera_back);
            parent.addView(radioButton);
        }
    }

}

