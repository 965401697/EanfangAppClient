package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.config.Config;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.CooperationAddAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 技能资质
 */
public class SkillTypeActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.ll_limit)
    LinearLayout llLimit;
    @BindView(R.id.tv_ability)
    TextView tvAbility;
    @BindView(R.id.ll_ability)
    LinearLayout llAbility;
    @BindView(R.id.recycler_view_classfiy)
    RecyclerView recyclerViewBusiness;
    @BindView(R.id.recycler_view_kind)
    RecyclerView recyclerViewOs;

    private CooperationAddAdapter businessCooperationAddAdapter;
    private CooperationAddAdapter osCooperationAddAdapter;

    List<String> mBusinessList = new ArrayList<>();
    List<String> mOsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_type);
        ButterKnife.bind(this);
        setTitle("技能资质");
        setLeftBack();

        initViews();
    }

    private void initViews() {

        List<BaseDataEntity> businessList = Config.get().getBusinessList(1);
        mOsList = GetConstDataUtils.getCooperationTypeList();

        for (BaseDataEntity business : businessList) {
            mBusinessList.add(business.getDataName());
        }


        recyclerViewBusiness.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewOs.setLayoutManager(new GridLayoutManager(this, 3));

        businessCooperationAddAdapter = new CooperationAddAdapter(R.layout.item_cooperation_add);
        osCooperationAddAdapter = new CooperationAddAdapter(R.layout.item_cooperation_add);

        businessCooperationAddAdapter.bindToRecyclerView(recyclerViewBusiness);
        osCooperationAddAdapter.bindToRecyclerView(recyclerViewOs);

        businessCooperationAddAdapter.setNewData(mOsList);
        osCooperationAddAdapter.setNewData(mBusinessList);
    }

    @OnClick(R.id.tv_go)
    public void onViewClicked() {
        startAnimActivity(new Intent(this, SkillAreaActivity.class));
    }

    @OnClick({R.id.ll_limit, R.id.ll_ability})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_limit:
                PickerSelectUtil.singleTextPicker(this, "", tvLimit, GetConstDataUtils.getWorkingYearList());
                break;
            case R.id.ll_ability:
                PickerSelectUtil.singleTextPicker(this, "", tvAbility, GetConstDataUtils.getWorkingLevelList());
                break;
        }
    }
}
