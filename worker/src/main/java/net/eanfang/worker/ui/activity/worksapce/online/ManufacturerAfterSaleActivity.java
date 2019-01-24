package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.config.Config;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ManufacturerAfterSaleActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacturer_after_sale);
        ButterKnife.bind(this);
        setTitle("厂商售后");
        setLeftBack();
        initView();
    }

    private void initView() {
        //2.声名gridview布局方式  第二个参数代表是3列的网格视图 (垂直滑动的情况下, 如果是水平滑动就代表3行)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ManufacturerAfterSaleActivity.this, 2);
        //3.给GridLayoutManager设置滑动的方向
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        ManufacturerAfterSaleAdapter manufacturerAfterSaleAdapter = new ManufacturerAfterSaleAdapter();
        //4.为recyclerView设置布局管理器
        recyclerView.setLayoutManager(gridLayoutManager); //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        manufacturerAfterSaleAdapter.bindToRecyclerView(recyclerView);

        manufacturerAfterSaleAdapter.setNewData(Stream.of(Config.get().getModelList(2)).toList());

        manufacturerAfterSaleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ManufacturerAfterSaleActivity.this, ExpertListActivity.class);
                intent.putExtra("brand", (Serializable) adapter.getData().get(position));
                startActivity(intent);
            }
        });
    }
}
