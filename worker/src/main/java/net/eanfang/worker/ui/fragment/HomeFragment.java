package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.witget.BannerView;
import com.eanfang.witget.RollTextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.worksapce.QuotationActivity;
import net.eanfang.worker.ui.activity.worksapce.TakeTaskListActivity;
import net.eanfang.worker.ui.activity.worksapce.TaskPublishActivity;
import net.eanfang.worker.ui.activity.worksapce.WebActivity;
import net.eanfang.worker.ui.widget.SignCtrlView;

import java.util.ArrayList;
import java.util.List;


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
    private LinearLayout ll_assign_packpage, ll_accept_packpage, ll_tools_store, ll_quote_add, ll_sign;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        initIconClick();
        initLoopView();
        initRollTextView();
        initCount();
    }

    /**
     * 工作按钮
     */
    private void initIconClick() {
        ll_assign_packpage = findViewById(R.id.ll_assign_packpage);
        ll_accept_packpage = findViewById(R.id.ll_accept_packpage);
        ll_tools_store = findViewById(R.id.ll_tools_store);
        ll_quote_add = findViewById(R.id.ll_quote_add);
        ll_sign = findViewById(R.id.ll_sign);
        ll_assign_packpage.setOnClickListener(v -> startActivity(new Intent(getActivity(), TaskPublishActivity.class)));
        ll_accept_packpage.setOnClickListener(v -> startActivity(new Intent(getActivity(), TakeTaskListActivity.class)));
        ll_tools_store.setOnClickListener(v -> showToast("本店暂未开业"));
        ll_quote_add.setOnClickListener(v -> startActivity(new Intent(getActivity(), QuotationActivity.class)));
        ll_sign.setOnClickListener((v) -> {
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
        findViewById(R.id.rl_today_repair).setOnClickListener(v -> jumpWebview());
        findViewById(R.id.rl_free_install).setOnClickListener(v -> jumpWebview());
        findViewById(R.id.rl_free_design).setOnClickListener(v -> jumpWebview());
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
        List<View> views = new ArrayList<>();
        data.add("我是李白asd;faldfjaj;jf;adj;fja;d;j;a;adasdjfl;a;afhdalkfhahklahlfhlalaf");
        data.add("我是杜甫");
        data.add("我是秦始皇");
        data.add("我是天猫");
        data.add("我是京东");
        for (int i = 0; i < data.size(); i++) {
            View view = View.inflate(getContext(), R.layout.rolltext_item, null);
            TextView rolltext = (TextView) view.findViewById(R.id.roll_item_text);
            rolltext.setText(data.get(i).toString());
            views.add(view);
        }
        rollTextView.setViews(views);
        rollTextView.setOnItemClickListener((position, view) -> {
            showToast("暂无可点");
        });
    }

}
