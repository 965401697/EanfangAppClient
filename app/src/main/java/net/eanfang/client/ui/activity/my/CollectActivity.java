package net.eanfang.client.ui.activity.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.FragmentAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.fragment.CollectCompanyListFragment;
import net.eanfang.client.ui.fragment.CollectWorkerListFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/17  15:45
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class CollectActivity extends BaseActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sliding_tab)
    TabLayout slidingTab;
    @BindView(R.id.vp)
    ViewPager vpEvaluate;
    private CollectWorkerListFragment workerListFragment;
    private CollectCompanyListFragment companyListFragment;
    private List<Fragment> list_frag;
    private List<String> list_title;
    private String[] title = {"技师", "公司"};
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("收藏");
        workerListFragment = new CollectWorkerListFragment();
        companyListFragment = new CollectCompanyListFragment();
        list_frag = new ArrayList<>();
        list_frag.add(workerListFragment);
        list_frag.add(companyListFragment);
        slidingTab.setTabMode(TabLayout.MODE_FIXED);
        list_title = new ArrayList<>();
        Collections.addAll(list_title, title);
        slidingTab.addTab(slidingTab.newTab().setText(list_title.get(0)));
        slidingTab.addTab(slidingTab.newTab().setText(list_title.get(1)));
        adapter = new FragmentAdapter(getSupportFragmentManager(), list_frag, list_title);

        //viewpager加载adapter
        vpEvaluate.setAdapter(adapter);
        //tablayout加载viewpager
        slidingTab.setupWithViewPager(vpEvaluate);

    }
}
