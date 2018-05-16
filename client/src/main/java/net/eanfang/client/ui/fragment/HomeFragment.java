package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.model.ClientData;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;
import com.eanfang.witget.BannerView;
import com.eanfang.witget.RollTextView;
import com.project.eanfang.zxing.android.CaptureActivity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.CameraActivity;
import net.eanfang.client.ui.activity.worksapce.CustomerServiceActivity;
import net.eanfang.client.ui.activity.worksapce.datastatistics.DataStaticsticsListActivity;
import net.eanfang.client.ui.activity.worksapce.datastatistics.DataStatisticsActivity;
import net.eanfang.client.ui.activity.worksapce.WebActivity;
import net.eanfang.client.ui.activity.worksapce.repair.RepairTypeActivity;
import net.eanfang.client.ui.adapter.HomeDataAdapter;
import net.eanfang.client.ui.widget.DesignCtrlView;
import net.eanfang.client.ui.widget.InstallCtrlView;
import net.eanfang.client.ui.widget.RepairCtrlView;

import java.util.ArrayList;
import java.util.List;

import static com.eanfang.util.V.v;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 首页
 */

public class HomeFragment extends BaseFragment {
    private BannerView bannerView;

    private RollTextView rollTextView;

    //头部标题
    private TextView tvHomeTitle;

    private RecyclerView rvData;
    private List<ClientData> clientDataList = new ArrayList<>();
    private HomeDataAdapter homeDataAdapter;
    private RelativeLayout rlAllData;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    public void onResume() {
        super.onResume();
        String orgName = v(() -> (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName()));
        if (("个人").equals(orgName)) {
            tvHomeTitle.setText("易安防");
        } else {
            tvHomeTitle.setText(orgName);
        }
    }

    @Override
    protected void initView() {
        rvData = (RecyclerView) findViewById(R.id.rv_data);
        tvHomeTitle = (TextView) findViewById(R.id.tv_homeTitle);
        rlAllData = (RelativeLayout) findViewById(R.id.rl_allData);
        initIconClick();
        initLoopView();
        initRollTextView();
        //设置布局样式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvData.setLayoutManager(linearLayoutManager);
        initCount();
        initFalseData();
    }

    private void initFalseData() {
        ClientData clientDataOne = new ClientData();
        clientDataOne.setType(1);
        clientDataOne.setTotal(23);
        clientDataOne.setAdded(5);
        clientDataOne.setStatusOne(16);
        clientDataOne.setStatusTwo(2);
        clientDataOne.setStatusThree(1);
        clientDataOne.setStatusFour(14);
        clientDataList.add(clientDataOne);
        ClientData clientDataTwo = new ClientData();
        clientDataTwo.setType(2);
        clientDataTwo.setTotal(0);
        clientDataTwo.setAdded(7);
        clientDataTwo.setStatusOne(18);
        clientDataTwo.setStatusTwo(9);
        clientDataTwo.setStatusThree(4);
        clientDataTwo.setStatusFour(2);
        clientDataList.add(clientDataTwo);
        ClientData clientDataThree = new ClientData();
        clientDataThree.setType(3);
        clientDataThree.setTotal(0);
        clientDataThree.setAdded(3);
        clientDataThree.setStatusOne(12);
        clientDataThree.setStatusTwo(3);
        clientDataThree.setStatusThree(6);
        clientDataThree.setStatusFour(8);
        clientDataList.add(clientDataThree);
        ClientData clientDataFour = new ClientData();
        clientDataFour.setType(4);
        clientDataFour.setTotal(27);
        clientDataFour.setAdded(7);
        clientDataFour.setStatusOne(13);
        clientDataFour.setStatusTwo(6);
        clientDataFour.setStatusThree(8);
        clientDataFour.setStatusFour(0);
        clientDataList.add(clientDataFour);

        homeDataAdapter = new HomeDataAdapter(R.layout.layout_home_data, clientDataList);
        rvData.setAdapter(homeDataAdapter);
    }


    /**
     * 工作按钮
     */
    private void initIconClick() {
        //我要报修
        findViewById(R.id.tv_reparir).setOnClickListener((v) -> {
            JumpItent.jump(getActivity(), RepairTypeActivity.class);
        });
        //我要报装
        findViewById(R.id.tv_install).setOnClickListener((v) -> {
            new InstallCtrlView(getActivity(), true).show();
        });
        //免费设计
        findViewById(R.id.tv_design).setOnClickListener((v) -> {
            new DesignCtrlView(getActivity(), true).show();
        });
        //开锁
        findViewById(R.id.tv_unlock).setOnClickListener(v -> showToast("暂缓开通"));
        //实时监控
        findViewById(R.id.tv_monitor).setOnClickListener(v -> showToast("暂缓开通"));
        //客服
        findViewById(R.id.tv_service).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CustomerServiceActivity.class));
        });
        //扫描二维码
        findViewById(R.id.iv_scan).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CaptureActivity.class).putExtra("from", EanfangConst.QR_CLIENT));
        });


        //签到
//        findViewById(R.id.ll_sign).setOnClickListener((v) -> {
//            new SignCtrlView(getActivity()).show();
//        });
    }

    /**
     * 统计
     */
    private void initCount() {
        rlAllData.setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), DataStaticsticsListActivity.class));
        });
        rvData.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), DataStatisticsActivity.class));
//                jumpWebview() ;
            }
        });
    }

    private void jumpWebview() {
        boolean isHave = EanfangApplication.getApplication().getUser().getPerms().contains("top:statistics:count");
        if (isHave == true) {
            String token = EanfangApplication.getApplication().getUser().getToken();
            startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", "http://client.eanfang.net:8099/#/totalPhone?token=" + token)
                    .putExtra("title", "数据统计"));
        } else {
            showToast("您还没有权限");
        }
    }

    /**
     * 初始化轮播控件
     */
    private void initLoopView() {
        bannerView = findViewById(R.id.bv_loop);
        int[] images = {R.mipmap.ic_client_banner, R.mipmap.ic_client_banner, R.mipmap.ic_client_banner, R.mipmap.ic_client_banner};
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView image = new ImageView(getActivity());
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(images[i]);
            viewList.add(image);
        }
        bannerView.startLoop(true);
        bannerView.setViewList(viewList);
    }

    /**
     * 初始化rolltext显示的文本
     */
    private void initRollTextView() {
        rollTextView = findViewById(R.id.home_recommand_ad_text);
        List<String> data = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        List<View> views = new ArrayList<>();
        data.add("通过易安防进行了报修。");
        data.add("通过易安防接到了一个报修单。");
        data.add("通过易安防进行了报修。");
        data.add("通过易安防接了一个报修单。");
        data.add("通过易安防接到了一个报装单。");
        data.add("通过易安防进行了报装。");
        data.add("通过易安防接到了一个免费设计。");
        data.add("通过易安防接到了一个报修。");
        data.add("通过易安防进行了免费设计。");
        data.add("通过易安防接到了一个报装。");
        data.add("通过易安防进行了免费设计。");
        data.add("通过易安防接到了一个免费设计。");
        data.add("通过易安防接到了一个报修。");

        titleList.add("孙*");
        titleList.add("张技师");
        titleList.add("李*");
        titleList.add("李技师");
        titleList.add("刘技师");
        titleList.add("刘**");
        titleList.add("孙技师");
        titleList.add("吴技师");
        titleList.add("刘**");
        titleList.add("张技师");
        titleList.add("张*");
        titleList.add("梁技师");
        titleList.add("邓技师");
        for (int i = 0; i < data.size(); i++) {
            View view = View.inflate(getContext(), R.layout.rolltext_item, null);
            TextView content = (TextView) view.findViewById(R.id.tv_roll_item_text);
            TextView title = (TextView) view.findViewById(R.id.tv_roll_item_title);
            title.setText(titleList.get(i).toString());
            content.setText(data.get(i).toString());
            views.add(view);
        }
        rollTextView.setViews(views);
        rollTextView.setOnItemClickListener((position, view) -> {
            showToast("暂无可点");
        });
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_camera).setOnClickListener(v -> startActivity(new Intent(getActivity(), CameraActivity.class)));
    }
}
