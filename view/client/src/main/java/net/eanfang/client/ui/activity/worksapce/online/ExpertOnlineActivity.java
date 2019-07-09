package net.eanfang.client.ui.activity.worksapce.online;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.witget.BannerView;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 专家在线
 */
public class ExpertOnlineActivity extends BaseClientActivity {

    @BindView(R.id.bv_loop)
    BannerView bvLoop;
    @BindView(R.id.recycler_view_sys)
    RecyclerView recyclerViewSys;
    @BindView(R.id.recycler_common_fault)
    RecyclerView recyclerCommonFault;
    @BindView(R.id.tv_no_datas)
    TextView tvNoDatas;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    private CommonQuestionsAdapter commonQuestionsAdapter;
    //private List<CommonQuestionsBean.ListBean> list;
    //private CommentFaultSearchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_expert_online);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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
        //故障布局管理器
        recyclerCommonFault.setLayoutManager(new LinearLayoutManager(this));
        //3.给GridLayoutManager设置滑动的方向
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        SystemTypeAdapter systemTypeAdapter = new SystemTypeAdapter();
        //故障adapter
        //mAdapter = new CommentFaultSearchAdapter();
        commonQuestionsAdapter = new CommonQuestionsAdapter();
        commonQuestionsAdapter.bindToRecyclerView(recyclerCommonFault);
        //4.为recyclerView设置布局管理器
        recyclerViewSys.setLayoutManager(gridLayoutManager); //设置分割线
        recyclerViewSys.addItemDecoration(new DividerItemDecoration(this));
        systemTypeAdapter.bindToRecyclerView(recyclerViewSys);

        systemTypeAdapter.setNewData(Config.get().getBusinessList(1).subList(0, 4));
        //系统类别
        systemTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ExpertOnlineActivity.this, ExpertListActivity.class);
                intent.putExtra("brand", (Serializable) adapter.getData().get(position));
                startActivity(intent);
            }
        });
        commonQuestionsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ExpertOnlineActivity.this, FaultExplainActivity.class);
                intent.putExtra("QuestionIdZ", commonQuestionsAdapter.getData().get(position).getQuestionId());
                startActivity(intent);
            }
        });
        //故障数据
        getData();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            finish();
        }
        return super.onKeyDown(keyCode, event);
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
            case R.id.tv_search://跳转常见故障列表页面
                Intent i = new Intent(ExpertOnlineActivity.this, CommentFaultSearchActivity.class);
                startActivity(i);
                break;
            case R.id.rl_free_ask://免费提问x
                Intent intent = new Intent(ExpertOnlineActivity.this, FreeAskActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_find_expert://找专家
                SharedPreferences sp = getSharedPreferences("basis", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Intent intentExpert = new Intent(ExpertOnlineActivity.this, FindExpertActivity.class);
                startActivity(intentExpert);
                break;
        }
    }

    @OnClick({R.id.rl_haikang, R.id.rl_yushi, R.id.rl_hua, R.id.rl_sam, R.id.rl_iinsanli})
    public void onViewClickedSale(View view) {
        switch (view.getId()) {
            case R.id.rl_haikang:
                Intent intent = new Intent(ExpertOnlineActivity.this, ExpertListActivity.class);
                intent.putExtra("brand_code", "5.1.28");
                intent.putExtra("brand_name", "海康威视（Hikvion）");
                startActivity(intent);
                break;
            case R.id.rl_yushi:
                Intent intentUniview = new Intent(ExpertOnlineActivity.this, ExpertListActivity.class);
                intentUniview.putExtra("brand_code", "5.1.65");
                intentUniview.putExtra("brand_name", "宇视（Uniview）");
                startActivity(intentUniview);
                break;
            case R.id.rl_hua:
                Intent intentDahua = new Intent(ExpertOnlineActivity.this, ExpertListActivity.class);
                intentDahua.putExtra("brand_code", "5.1.17");
                intentDahua.putExtra("brand_name", "大华（Dahua）");
                startActivity(intentDahua);
                break;
            case R.id.rl_sam:
                Intent intentSamsung = new Intent(ExpertOnlineActivity.this, ExpertListActivity.class);
                intentSamsung.putExtra("brand_code", "5.1.45");
                intentSamsung.putExtra("brand_name", "三星（Samsung）");
                startActivity(intentSamsung);
                break;
            case R.id.rl_iinsanli:
                Intent intentSantachi = new Intent(ExpertOnlineActivity.this, ExpertListActivity.class);
                intentSantachi.putExtra("brand_code", "5.1.44");
                intentSantachi.putExtra("brand_name", "三立（Santachi）");
                startActivity(intentSantachi);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    //网络请求
    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(4);
        queryEntry.setPage(1);
        EanfangHttp.post(NewApiService.CommonQuestions)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<CommonQuestionsBean>(this, true, CommonQuestionsBean.class) {
                    @Override
                    public void onSuccess(CommonQuestionsBean bean) {
                        //list = bean.getList();
                        if (bean.getList() != null) {
                            tvNoDatas.setVisibility(View.GONE);
                            commonQuestionsAdapter.setNewData(bean.getList());
                        } else {
                            tvNoDatas.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNoData(String message) {

                    }

                    @Override
                    public void onCommitAgain() {

                    }
                });

    }


}
