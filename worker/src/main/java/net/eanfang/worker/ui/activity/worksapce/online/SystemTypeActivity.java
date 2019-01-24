package net.eanfang.worker.ui.activity.worksapce.online;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.config.Config;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemTypeActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_type);
        ButterKnife.bind(this);
        setTitle("系统类别");
        setLeftBack();
        initView();
    }

    private void initView() {
        //2.声名gridview布局方式  第二个参数代表是3列的网格视图 (垂直滑动的情况下, 如果是水平滑动就代表3行)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SystemTypeActivity.this, 2);
        //3.给GridLayoutManager设置滑动的方向
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        SystemTypeAdapter systemTypeAdapter = new SystemTypeAdapter();
        //4.为recyclerView设置布局管理器
        recyclerView.setLayoutManager(gridLayoutManager); //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        systemTypeAdapter.bindToRecyclerView(recyclerView);

        systemTypeAdapter.setNewData(Config.get().getBusinessList(1));

        systemTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    //电视监控
                    case 0:
                        break;
                    //防盗报警
                    case 1:
                        break;
                    //门禁 -卡通
                    case 2:
                        break;
                    //可是对讲
                    case 3:
                        break;
                    //公共广播
                    case 4:
                        break;
                    //停车场
                    case 5:
                        break;
                    //eas
                    case 6:
                        break;
                    //  其他
                    case 7:
                        break;
                }
            }
        });
    }
}
