package net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail.faultdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Optional;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.biz.model.entity.BughandleDetailEntity;
import com.eanfang.biz.model.entity.BughandleParamEntity;
import com.eanfang.biz.model.entity.BughandleUseDeviceEntity;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.repair.SeeFaultParamActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.SeeTroubleDetailPhotoActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.material.SeeMaterialActivity;
import net.eanfang.worker.ui.adapter.MaterialAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

import static com.eanfang.base.kit.V.v;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  11:05
 * @email houzhongzhou@yeah.net
 * @desc 电话未解决 故障明细
 */

public class LookTroubleDetailActivity extends BaseWorkerActivity {
    public static final String TAG = LookTroubleDetailActivity.class.getSimpleName();


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

    //位置编号
    @BindView(R.id.tv_location_num)
    TextView tvLocationNum;

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
    @BindView(R.id.rg_yes)
    RadioButton rgYes;
    @BindView(R.id.rg_no)
    RadioButton rgNo;
    @BindView(R.id.rg_misreport)
    RadioGroup rgMisreport;
    // 查看照片
    @BindView(R.id.rl_add_device_picture)
    RelativeLayout rlAddDevicePicture;
    // 维修结果
    @BindView(R.id.tag_repair_result)
    TagFlowLayout tagRepairResult;
    private TagAdapter<String> mResultAdapter;
    // 修复方式
    @BindView(R.id.tag_repair_result_two)
    TagFlowLayout tagRepairResultTwo;
    private TagAdapter<String> mModeAdapter;


    private RecyclerView rcy_consumable;
    private List<BughandleParamEntity> mDataList = new ArrayList<>();
    private List<BughandleUseDeviceEntity> mDataList_2 = new ArrayList<>();

    private MaterialAdapter materialAdapter;
    private BughandleDetailEntity bughandleDetailEntity;
    /**
     * 2017年8月1日
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
        initListener();
    }

    private void initView() {
        id = getIntent().getLongExtra("id", 0);
        setTitle("故障明细");
        setLeftBack();
        rcy_consumable = findViewById(R.id.rcy_consumable);
        rcy_consumable.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcy_consumable.setLayoutManager(new LinearLayoutManager(this));
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

        /**
         * 设备参数
         * */

        if (mDataList_2.size() != 0) {
            materialAdapter.bindToRecyclerView(rcy_consumable);
        } else {
            //无数据时隐藏
            rcy_consumable.setVisibility(View.GONE);
        }

        if (bughandleDetailEntity.getFailureEntity() != null) {
            if (StrUtil.isNotBlank(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode())) {
                String bugOne = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 1);
                String bugTwo = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 2);
                String bugThree = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 3);
                tvTroubleTitle.setText(bugThree);
            } else {
                tvTroubleTitle.setText("");
            }

            tvDeviceNo.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getDeviceNo()).orElse(""));

            tvDeviceLocation.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugPosition()).orElse(""));

            tvLocationNum.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getLocationNumber()).orElse(""));

            //故障描述
            tvTroubleDesc.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugDescription()).orElse(""));
        }
        tvTroublePoint.setText(Optional.ofNullable(bughandleDetailEntity.getCheckProcess()).orElse(""));
        tvTroubleReason.setText(Optional.ofNullable(bughandleDetailEntity.getCause()).orElse(""));
        tvTroubleDeal.setText(Optional.ofNullable(bughandleDetailEntity.getHandle()).orElse(""));
        tvTroubleUseAdvace.setText(Optional.ofNullable(bughandleDetailEntity.getUseAdvice()).orElse(""));
//        tvRepairMisinformation.setText(GetConstDataUtils.getRepairMisinformationList().get(bughandleDetailEntity.getFailureEntity().getIsMisinformation()));
        // (0：否，1：是)
        if (bughandleDetailEntity.getFailureEntity().getIsMisinformation() == 0) {
            rgNo.setChecked(true);
            rgYes.setChecked(false);
        } else {
            rgNo.setChecked(false);
            rgYes.setChecked(true);

        }
        if (bughandleDetailEntity.getStatus() != null && bughandleDetailEntity.getStatusTwo() != null) {
            //添加维修结论
            addRepairResult(GetConstDataUtils.getBugDetailList(), v(bughandleDetailEntity::getStatus));
            addRepairResultMode(GetConstDataUtils.getBugDetailTwoList(v(bughandleDetailEntity::getStatus)), v(bughandleDetailEntity::getStatusTwo));
        }

    }


    private void initAdapter() {
        materialAdapter = new MaterialAdapter(R.layout.layout_item_add_material);
        materialAdapter.setNewData(mDataList_2);
        rcy_consumable.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bughandleUseDeviceEntity", mDataList_2.get(position));
                JumpItent.jump(LookTroubleDetailActivity.this, SeeMaterialActivity.class, bundle);
            }
        });
    }

    private void initListener() {
        // 设备参数
        rlAddDeviceParam.setOnClickListener((v) -> {
            if (mDataList.size() <= 0) {
                showToast("暂无参数");
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("faultParam", (Serializable) mDataList);
                JumpItent.jump(LookTroubleDetailActivity.this, SeeFaultParamActivity.class, bundle);
            }

        });
        rlAddDevicePicture.setOnClickListener((v) -> {
            if (bughandleDetailEntity != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bughandleDetailEntity", bughandleDetailEntity);
                JumpItent.jump(LookTroubleDetailActivity.this, SeeTroubleDetailPhotoActivity.class, bundle);
            }

        });
    }

    public void addRepairResult(List<String> stringList, int status) {

        tagRepairResult.setAdapter(mResultAdapter = new TagAdapter<String>(stringList) {
            @Override
            public View getView(FlowLayout parent, int position, String mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(LookTroubleDetailActivity.this).inflate(R.layout.layout_trouble_result_item, tagRepairResult, false);
                tv.setText(mrepairResult);
                return tv;
            }
        });
        mResultAdapter.setSelectedList(status);
        tagRepairResult.setClickable(false);
        tagRepairResult.setEnabled(false);
    }

    public void addRepairResultMode(List<String> stringList, int status) {
        if (tagRepairResultTwo.getSelectedList() != null && tagRepairResultTwo.getSelectedList().size() > 0) {
            tagRepairResultTwo.getSelectedList().clear();
            if (tagRepairResultTwo.getAdapter() != null) {
                tagRepairResultTwo.getAdapter().notifyDataChanged();
            }
        }
        tagRepairResultTwo.setAdapter(mModeAdapter = new TagAdapter<String>(stringList) {
            @Override
            public View getView(FlowLayout parent, int position, String mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(LookTroubleDetailActivity.this).inflate(R.layout.layout_trouble_result_item, tagRepairResultTwo, false);
                tv.setText(mrepairResult);
                return tv;
            }
        });
        mModeAdapter.setSelectedList(status);
        tagRepairResultTwo.setClickable(false);
        tagRepairResultTwo.setEnabled(false);
    }

}

