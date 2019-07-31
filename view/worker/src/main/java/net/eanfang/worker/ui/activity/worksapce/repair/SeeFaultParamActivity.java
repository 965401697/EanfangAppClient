package net.eanfang.worker.ui.activity.worksapce.repair;

import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.biz.model.entity.BughandleParamEntity;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.LookParamAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/6/11  13:29
 * @decision 查看设备参数
 */
public class SeeFaultParamActivity extends BaseActivity {

    @BindView(R.id.rv_param)
    RecyclerView rvParam;


    private List<BughandleParamEntity> mFaultParamList = new ArrayList<>();

    private LookParamAdapter paramAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_see_fault_param);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setTitle("设备参数");
        setLeftBack();

        mFaultParamList = (List<BughandleParamEntity>) getIntent().getSerializableExtra("faultParam");

    }

    private void initData() {
        paramAdapter = new LookParamAdapter(R.layout.item_look_parm, (ArrayList) mFaultParamList);
        rvParam.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvParam.setLayoutManager(new LinearLayoutManager(this));
        rvParam.setAdapter(paramAdapter);
    }

}
