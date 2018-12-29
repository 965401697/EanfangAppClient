package net.eanfang.client.ui.activity.worksapce.openshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.defendlog.FilterDefendLogActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpenShopLogParentActivity extends BaseClientActivity {

    @BindView(R.id.tl_task_list)
    SlidingTabLayout tlTaskList;
    @BindView(R.id.tv_filtrate)
    TextView tvFiltrate;
    @BindView(R.id.vp_task_list)
    ViewPager vpTaskList;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"我创建的", "我接收的", "全部"};
    private MyPagerAdapter mAdapter;
    private final int FILTRATE_TYPE_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop_log_parent);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("开店日志");
        setLeftBack();
        setRightImageResId(R.mipmap.add);

        setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermKit.get().getOpenShopCreatePrem()) {
                    return;
                }
                startActivityForResult(new Intent(OpenShopLogParentActivity.this, OpenShopLogWriteActivity.class), FILTRATE_TYPE_CODE);
            }
        });

        mFragments.add(OpenShopLogFragment.getInstance("我创建的", 1));
        mFragments.add(OpenShopLogFragment.getInstance("我接收的", 2));
        mFragments.add(OpenShopLogFragment.getInstance("全部", 0));

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpTaskList.setAdapter(mAdapter);
        tlTaskList.setViewPager(vpTaskList, mTitles, this, mFragments);

        vpTaskList.setCurrentItem(0);
    }

    @OnClick(R.id.tv_filtrate)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        if (vpTaskList.getCurrentItem() == 0) {// 我创建的
            bundle.putInt("type", 0);
        } else if (vpTaskList.getCurrentItem() == 1) {// 我接收的
            bundle.putInt("type", 1);
        }
        JumpItent.jump(OpenShopLogParentActivity.this, FilterDefendLogActivity.class, bundle, FILTRATE_TYPE_CODE);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    /**
     * 筛选回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int currentTab = tlTaskList.getCurrentTab();
        if (resultCode == RESULT_OK && requestCode == FILTRATE_TYPE_CODE) {

            QueryEntry queryEntry = (QueryEntry) data.getSerializableExtra("query");
            if (queryEntry != null) {
                ((OpenShopLogFragment) mFragments.get(currentTab)).getTaskData(queryEntry);
            }
        } else if (resultCode == RESULT_OK && requestCode == OpenShopLogFragment.DETAIL_TASK_REQUSET_COOD) {
            ((OpenShopLogFragment) mFragments.get(currentTab)).refreshStatus();

        }
    }
}
