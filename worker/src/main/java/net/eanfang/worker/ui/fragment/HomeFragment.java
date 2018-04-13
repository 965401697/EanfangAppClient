package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.application.EanfangApplication;
import com.eanfang.model.ClientData;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.witget.BannerView;
import com.eanfang.witget.RollTextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.worksapce.QuotationActivity;
import net.eanfang.worker.ui.activity.worksapce.TakeTaskListActivity;
import net.eanfang.worker.ui.activity.worksapce.TaskPublishActivity;
import net.eanfang.worker.ui.activity.worksapce.WebActivity;
import net.eanfang.worker.ui.adapter.HomeDataAdapter;
import net.eanfang.worker.ui.widget.SignCtrlView;

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
    //头部标题
    private TextView tvHomeTitle;
    private RollTextView rollTextView;

    private RecyclerView rvData;
    private List<ClientData> clientDataList = new ArrayList<>();
    private HomeDataAdapter homeDataAdapter;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        rvData = (RecyclerView) findViewById(R.id.rv_data);
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

    @Override
    public void onResume() {
        super.onResume();
        tvHomeTitle = (TextView) findViewById(R.id.tv_homeTitle);
        String orgName = v(() -> (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName()));
        if (("个人").equals(orgName)) {
            tvHomeTitle.setText("易安防");
        } else {
            tvHomeTitle.setText(orgName);
        }
    }

    /**
     * 工作按钮
     */
    private void initIconClick() {
        //报修订单
        findViewById(R.id.tv_reparir_order).setOnClickListener(v -> startActivity(new Intent(getActivity(), TaskPublishActivity.class)));
        //报装订单
        findViewById(R.id.tv_install_order).setOnClickListener(v -> startActivity(new Intent(getActivity(), TaskPublishActivity.class)));
        //设计订单
        findViewById(R.id.tv_design_order).setOnClickListener(v -> startActivity(new Intent(getActivity(), TaskPublishActivity.class)));
        //维保订单
        findViewById(R.id.tv_maintain_order).setOnClickListener(v -> startActivity(new Intent(getActivity(), TaskPublishActivity.class)));
        //发包
        findViewById(R.id.tv_project_send).setOnClickListener(v -> startActivity(new Intent(getActivity(), TaskPublishActivity.class)));
        //接包
        findViewById(R.id.tv_project_receive).setOnClickListener(v -> startActivity(new Intent(getActivity(), TakeTaskListActivity.class)));
        //内部报价
        findViewById(R.id.tv_inside_price).setOnClickListener(v -> startActivity(new Intent(getActivity(), QuotationActivity.class)));
        //签到
        findViewById(R.id.tv_sign).setOnClickListener((v) -> {
            new SignCtrlView(getActivity()).show();
        });
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_camera).setOnClickListener(v -> startActivity(new Intent(getActivity(), CameraActivity.class)));
        findViewById(R.id.iv_scan).setOnClickListener(v -> showToast("稍等一下，我还没准备好"));
    }

    /**
     * 统计
     */
    private void initCount() {
        rvData.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                jumpWebview();
            }
        });
    }

    private void jumpWebview() {
        boolean isHave = EanfangApplication.getApplication().getUser().getPerms().contains("top:statistics:count");
        if (isHave == true) {
            String token = EanfangApplication.getApplication().getUser().getToken();
            startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", "http:/worker.eanfang.net:8099/#/totalPhone?token=" + token)
                    .putExtra("title", "数据统计"));
        } else {
            showToast("您没有权限");
        }
    }

    /**
     * 初始化轮播控件
     */
    private void initLoopView() {
        bannerView = findViewById(R.id.bv_loop);
        int[] images = {R.drawable.banner, R.drawable.banner, R.drawable.banner, R.drawable.banner};
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
        data.add("出席会议的有易安防老大王兴军");
        data.add("出席会议的有易安防总经理吴建华");
        data.add("出席会议的有易安防技术大牛徐定兵");
        data.add("出席会议的有易安防产品经理郭林");
        data.add("出席会议的有没头衔的我，因为太帅，人称帅侯");

        titleList.add("王兴军");
        titleList.add("吴建华");
        titleList.add("徐定兵");
        titleList.add("郭林");
        titleList.add("帅侯");
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
        clientDataTwo.setTotal(16);
        clientDataTwo.setAdded(7);
        clientDataTwo.setStatusOne(18);
        clientDataTwo.setStatusTwo(9);
        clientDataTwo.setStatusThree(4);
        clientDataTwo.setStatusFour(2);
        clientDataList.add(clientDataTwo);
        ClientData clientDataThree = new ClientData();
        clientDataThree.setType(3);
        clientDataThree.setTotal(37);
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

        homeDataAdapter = new HomeDataAdapter(clientDataList);
        rvData.setAdapter(homeDataAdapter);
    }
}
