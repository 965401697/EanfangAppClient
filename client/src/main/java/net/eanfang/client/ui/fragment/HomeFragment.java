package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.V;
import com.eanfang.witget.BannerView;
import com.eanfang.witget.RollTextView;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.CameraActivity;
import net.eanfang.client.ui.activity.worksapce.DesignActivity;
import net.eanfang.client.ui.activity.worksapce.InstallActivity;
import net.eanfang.client.ui.activity.worksapce.RepairActivity;
import net.eanfang.client.ui.activity.worksapce.WebActivity;
import net.eanfang.client.ui.widget.SignCtrlView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
        tvHomeTitle = (TextView) findViewById(R.id.tv_homeTitle);
        initIconClick();
        initCount();
        initLoopView();
        initRollTextView();

    }


    /**
     * 工作按钮
     */
    private void initIconClick() {
        //我要报修
        findViewById(R.id.tv_reparir).setOnClickListener((v) -> {
            RepairActivity.jumpToActivity(getActivity());
        });
        //我要报装
        findViewById(R.id.tv_install).setOnClickListener((v) -> {
            InstallActivity.jumpActivity(getActivity());
        });
        //免费设计
        findViewById(R.id.tv_design).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), DesignActivity.class));
        });
        //开锁
        findViewById(R.id.tv_unlock).setOnClickListener(v -> showToast("暂缓开通"));
        //实时监控
        findViewById(R.id.tv_monitor).setOnClickListener(v -> showToast("暂缓开通"));
        //客服
        findViewById(R.id.tv_service).setOnClickListener(v -> showToast("暂缓开通"));


        //签到
//        findViewById(R.id.ll_sign).setOnClickListener((v) -> {
//            new SignCtrlView(getActivity()).show();
//        });
    }

    /**
     * 统计
     */
    private void initCount() {
//        findViewById(R.id.rl_today_repair).setOnClickListener(v -> jumpWebview());
//        findViewById(R.id.rl_free_install).setOnClickListener(v -> jumpWebview());
//        findViewById(R.id.rl_free_design).setOnClickListener(v -> jumpWebview());
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
        ;
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

    @Override
    protected void setListener() {
        findViewById(R.id.iv_camera).setOnClickListener(v -> startActivity(new Intent(getActivity(), CameraActivity.class)));
        findViewById(R.id.iv_scan).setOnClickListener(v -> showToast("暂缓开通"));
    }
}
