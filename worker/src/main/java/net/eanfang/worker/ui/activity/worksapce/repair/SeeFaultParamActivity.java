package net.eanfang.worker.ui.activity.worksapce.repair;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eanfang.ui.base.BaseActivity;
import com.yaf.base.entity.BughandleParamEntity;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_fault_param);
        ButterKnife.bind(this);
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
