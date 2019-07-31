package com.eanfang.sdk.equipment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.sdk.equipment.adapter.EquipmentParamAdapter;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.biz.model.entity.CustDeviceParamEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipmentParameterActivity extends BaseActivity {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_equipment_parameter);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("设备参数");
        setLeftBack();

        Bundle bundle = getIntent().getExtras();
        List<CustDeviceParamEntity> list = (List<CustDeviceParamEntity>) bundle.getSerializable("params");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EquipmentParamAdapter adapter = new EquipmentParamAdapter();
        adapter.bindToRecyclerView(recyclerView);
        if (list != null) {
            adapter.setNewData(list);
        }
    }
}
