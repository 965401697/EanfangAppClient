package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.BughandleParamEntity;
import com.yaf.base.entity.BughandleUseDeviceEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.LookMaterialAdapter;
import net.eanfang.client.ui.adapter.LookParamAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.MateraInfoView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  11:24
 * @email houzhongzhou@yeah.net
 * @desc 电话解决 故障明细
 */

public class PhoneLookTroubleDetailActivity extends BaseClientActivity /*implements View.OnClickListener */ {
    @BindView(R.id.tv_trouble_title)
    TextView tvTroubleTitle;
    @BindView(R.id.tv_device_no)
    TextView tvDeviceNo;
    @BindView(R.id.tv_device_location)
    TextView tvDeviceLocation;
    @BindView(R.id.et_trouble_desc)
    TextView etTroubleDesc;
    @BindView(R.id.et_trouble_point)
    TextView etTroublePoint;
    @BindView(R.id.et_trouble_reason)
    TextView etTroubleReason;
    @BindView(R.id.et_trouble_deal)
    TextView etTroubleDeal;
    @BindView(R.id.tv_repair_conclusion)
    TextView tvRepairConclusion;

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
        setContentView(R.layout.activity_phonetrouble_detail);
        ButterKnife.bind(this);
        initView();
        getData();
    }

    private void getData() {
        EanfangHttp.get(RepairApi.GET_BUGHANDLE_DETAIL_INFO)
                .params(Constant.ID, id)
                .execute(new EanfangCallback<BughandleDetailEntity>(this, true, BughandleDetailEntity.class, (bean) -> {
                    bughandleDetailEntity = bean;
                    initData(bean);
                }));

    }


    public void initData(BughandleDetailEntity bughandleDetailEntity) {

        if (bughandleDetailEntity.getFailureEntity().getBusinessThreeCode() != null) {
            tvTroubleTitle.setText(Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 3));
        }
//        //故障设备
//        if (StringUtils.isValid(bughandleDetailEntity.getInstrument())) {
//            tv_trouble_device.setText(bean.getInstrument());
//        }
//        //品牌型号
//        if (StringUtils.isValid(bean.getModelnum())) {
//            tv_brand_model.setText(bean.getModelnum());
//        }
//        //设备编号
//        if (StringUtils.isValid(bean.getEquipmentcode())) {
//            tv_device_no.setText(bean.getEquipmentcode());
//        }
        //故障位置
        if (StringUtils.isValid(bughandleDetailEntity.getFailureEntity().getBugPosition())) {
            tvDeviceLocation.setText(bughandleDetailEntity.getFailureEntity().getBugPosition());
        }
        //故障描述
        if (StringUtils.isValid(bughandleDetailEntity.getFailureEntity().getBugDescription())) {
            etTroubleDesc.setText(bughandleDetailEntity.getFailureEntity().getBugDescription());
        }
        //检查方法
        if (StringUtils.isValid(bughandleDetailEntity.getCheckProcess())) {
            etTroublePoint.setText(bughandleDetailEntity.getCheckProcess());
        }
        //故障原因
        if (StringUtils.isValid(bughandleDetailEntity.getCause())) {
            etTroubleReason.setText(bughandleDetailEntity.getCause());
        }
        //故障处理
        if (StringUtils.isValid(bughandleDetailEntity.getHandle())) {
            etTroubleDeal.setText(bughandleDetailEntity.getHandle());
        }
        //维修结论
        if (bughandleDetailEntity.getStatus() != null) {
            tvRepairConclusion.setText(GetConstDataUtils.getBugDetailList().get(bughandleDetailEntity.getStatus()));
        }
        if (bughandleDetailEntity.getFailureEntity().getIsMisinformation() != null) {
            tvRepairConclusion.setText(GetConstDataUtils.getRepairMisinformationList().get(bughandleDetailEntity.getFailureEntity().getIsMisinformation()));
        }


    }


    private void initView() {
        id = getIntent().getLongExtra(Constant.ID, 0);
        setLeftBack();
        setTitle("故障处理");
    }

}

