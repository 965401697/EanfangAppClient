package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.config.Config;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ManufacturerAfterSaleActivity extends BaseWorkerActivity implements View.OnClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.zhangwie)
    NestedScrollView zhangwie;
    private GridLayoutManager gridLayoutManager;
    private ManufacturerAfterSaleAdapter manufacturerAfterSaleAdapter;
    private static final int RESULT_DATACODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_manufacturer_after_sale);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int find = intent.getIntExtra("find", 0);

        //2.声名gridview布局方式  第二个参数代表是3列的网格视图 (垂直滑动的情况下, 如果是水平滑动就代表3行)
        gridLayoutManager = new GridLayoutManager(ManufacturerAfterSaleActivity.this, 2);
        //3.给GridLayoutManager设置滑动的方向
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        manufacturerAfterSaleAdapter = new ManufacturerAfterSaleAdapter();
        if (find == 2) {
            setTitle("选择品牌厂商");
            //4.为recyclerView设置布局管理器
            recyclerView.setLayoutManager(gridLayoutManager); //设置分割线
            recyclerView.addItemDecoration(new DividerItemDecoration(this));
            manufacturerAfterSaleAdapter.bindToRecyclerView(recyclerView);

            manufacturerAfterSaleAdapter.setNewData(Stream.of(Config.get().getModelList(2)).toList());
            manufacturerAfterSaleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    SharedPreferences sp = getSharedPreferences("basis", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("DataName",manufacturerAfterSaleAdapter.getData().get(position).getDataName());
                    edit.putInt("DataId",manufacturerAfterSaleAdapter.getData().get(position).getDataId());
                    edit.commit();
                    finish();
                }
            });
        } else {
            setTitle("厂商售后");
            setLeftBack();
            initView();
        }

    }

    private void initView() {

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

    @Override
    public void onClick(View v) {

    }
}
