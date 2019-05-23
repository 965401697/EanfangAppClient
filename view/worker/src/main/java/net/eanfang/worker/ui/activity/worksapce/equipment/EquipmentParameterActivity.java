package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yaf.base.entity.CustDeviceParamEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 已提取相关内容
 */
public class EquipmentParameterActivity extends BaseWorkerActivity {


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
