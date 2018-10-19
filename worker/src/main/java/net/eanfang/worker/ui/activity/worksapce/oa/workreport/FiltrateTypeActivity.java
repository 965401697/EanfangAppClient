package net.eanfang.worker.ui.activity.worksapce.oa.workreport;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eanfang.model.TemplateBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.CooperationAddAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FiltrateTypeActivity extends BaseWorkerActivity {


    @BindView(R.id.recycler_type)
    RecyclerView recyclerType;
    @BindView(R.id.recycler_status)
    RecyclerView recyclerStatus;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    private CooperationAddAdapter typeAdapter;
    private CooperationAddAdapter statusAdapter;

    List<String> mTypeList = new ArrayList<>();
    List<String> mStatusList = new ArrayList<>();
    private OAPersonAdaptet oaPersonAdaptet;

    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrate_type);
        ButterKnife.bind(this);
        setTitle("工作汇报筛选");
        setLeftBack();

        initViews();
    }

    private void initViews() {

        mTypeList = GetConstDataUtils.getWorkReportTypeList();
        mStatusList.add("待阅");
        mStatusList.add("已阅");
        mStatusList.add("已删除");


        recyclerType.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerStatus.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        typeAdapter = new CooperationAddAdapter(R.layout.item_cooperation_add);
        statusAdapter = new CooperationAddAdapter(R.layout.item_cooperation_add);

        oaPersonAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>());
        recyclerView.setAdapter(oaPersonAdaptet);

        typeAdapter.bindToRecyclerView(recyclerType);
        statusAdapter.bindToRecyclerView(recyclerStatus);

        typeAdapter.setNewData(mTypeList);
        statusAdapter.setNewData(mStatusList);

    }

    @OnClick({R.id.ll_start, R.id.ll_end, R.id.tv_cancle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_start:
                break;
            case R.id.ll_end:
                break;
            case R.id.tv_cancle:
                break;
            case R.id.tv_sure:
                break;
        }
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (presonList.size() > 0) {


            Set hashSet = new HashSet();
            hashSet.addAll(oaPersonAdaptet.getData());
            hashSet.addAll(presonList);

            if (newPresonList.size() > 0) {
                newPresonList.clear();
            }
            newPresonList.addAll(hashSet);
            oaPersonAdaptet.setNewData(newPresonList);


        }
    }
}
