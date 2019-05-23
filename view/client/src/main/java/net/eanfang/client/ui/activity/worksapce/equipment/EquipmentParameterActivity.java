package net.eanfang.client.ui.activity.worksapce.equipment;

import android.os.Bundle;

import com.yaf.base.entity.CustDeviceParamEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
//已提取
public class EquipmentParameterActivity extends BaseClientActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_parameter);
        ButterKnife.bind(this);
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
