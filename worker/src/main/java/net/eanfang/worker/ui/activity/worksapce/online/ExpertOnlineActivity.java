package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.config.Config;
import com.eanfang.witget.BannerView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 专家在线
 */
public class ExpertOnlineActivity extends BaseWorkerActivity {

    @BindView(R.id.bv_loop)
    BannerView bvLoop;
    @BindView(R.id.recycler_view_sys)
    RecyclerView recyclerViewSys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_online);
        ButterKnife.bind(this);
        setTitle("专家在线");
        setLeftBack();

        initLoopView();
        initViews();
    }

    private void initViews() {

        RelativeLayout title_top = findViewById(R.id.title_top);
        TextView t = title_top.findViewById(R.id.tv_title_desc);
        t.setText("厂商售后");
        title_top.findViewById(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ExpertOnlineActivity.this, ManufacturerAfterSaleActivity.class));
            }
        });
        RelativeLayout title_center = findViewById(R.id.title_center);
        TextView tv_title = title_center.findViewById(R.id.tv_title_desc);
        tv_title.setText("系统类别");
        title_center.findViewById(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpertOnlineActivity.this, SystemTypeActivity.class));
            }
        });
        RelativeLayout title_bottom = findViewById(R.id.title_bottom);
        TextView title = title_bottom.findViewById(R.id.tv_title_desc);
        title.setText("常见故障");
        title_bottom.findViewById(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpertOnlineActivity.this, CommentFaultSearchActivity.class));
            }
        });

        //2.声名gridview布局方式  第二个参数代表是3列的网格视图 (垂直滑动的情况下, 如果是水平滑动就代表3行)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ExpertOnlineActivity.this, 2);
        //3.给GridLayoutManager设置滑动的方向
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        SystemTypeAdapter systemTypeAdapter = new SystemTypeAdapter();
        //4.为recyclerView设置布局管理器
        recyclerViewSys.setLayoutManager(gridLayoutManager); //设置分割线
        recyclerViewSys.addItemDecoration(new DividerItemDecoration(this));
        systemTypeAdapter.bindToRecyclerView(recyclerViewSys);

        systemTypeAdapter.setNewData(Config.get().getBusinessList(1).subList(0, 4));

        systemTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    /**
     * 初始化轮播控件
     */
    private void initLoopView() {

        int[] images = {R.mipmap.ic_worker_banner_1, R.mipmap.ic_worker_banner_2, R.mipmap.ic_worker_banner_3, R.mipmap.ic_worker_banner_4, R.mipmap.ic_worker_banner_5};
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(images[i]);
            viewList.add(image);
        }
        bvLoop.startLoop(true);
        bvLoop.setViewList(viewList);
    }

    @OnClick({R.id.tv_search, R.id.rl_free_ask, R.id.rl_find_expert})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                Intent i = new Intent(ExpertOnlineActivity.this, CommentFaultSearchActivity.class);
                startActivity(i);
                break;
            case R.id.rl_free_ask:
                Intent intent = new Intent(ExpertOnlineActivity.this, FreeAskActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_find_expert:
                Intent in = new Intent(ExpertOnlineActivity.this, ExpertListActivity.class);
                startActivity(in);
                break;
        }
    }

    @OnClick({R.id.rl_haikang, R.id.rl_yushi, R.id.rl_hua, R.id.rl_sam, R.id.rl_iinsanli})
    public void onViewClickedSale(View view) {
        switch (view.getId()) {
            case R.id.rl_haikang:
                break;
            case R.id.rl_yushi:
                break;
            case R.id.rl_hua:
                break;
            case R.id.rl_sam:
                break;
            case R.id.rl_iinsanli:
                break;
        }
    }
}
