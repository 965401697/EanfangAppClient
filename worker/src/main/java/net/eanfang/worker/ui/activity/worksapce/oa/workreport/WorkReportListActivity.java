package net.eanfang.worker.ui.activity.worksapce.oa.workreport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eanfang.util.QueryEntry;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.fragment.WorkReportListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkReportListActivity extends BaseWorkerActivity {
    //    private static String titleBar;
//    public final List<String> allmTitles = GetConstDataUtils.getWorkReportStatus();
    @BindView(R.id.tl_work_list)
    SlidingTabLayout tlWorkList;
    @BindView(R.id.vp_work_list)
    ViewPager vpWorkList;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"我发出的", "我负责的", "全部"};
    private MyPagerAdapter mAdapter;
    private final int FILTRATE_TYPE_CODE = 101;
    //    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_report_list2);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("工作汇报");
        setLeftBack();
        setRightImageResId(R.mipmap.add);
        setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(WorkReportListActivity.this, CreationWorkReportActivity.class), FILTRATE_TYPE_CODE);
            }
        });

        mFragments.add(WorkReportListFragment.getInstance("我发出的", 1));
        mFragments.add(WorkReportListFragment.getInstance("我负责的", 2));
        mFragments.add(WorkReportListFragment.getInstance("全部", 0));

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpWorkList.setAdapter(mAdapter);
        tlWorkList.setViewPager(vpWorkList, mTitles, this, mFragments);

        vpWorkList.setCurrentItem(0);
        vpWorkList.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((WorkReportListFragment) mFragments.get(position)).setmQueryEntry(null);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.tv_filtrate)
    public void onViewClicked() {
        startActivityForResult(new Intent(this, FiltrateTypeActivity.class), FILTRATE_TYPE_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FILTRATE_TYPE_CODE) {

            int currentTab = tlWorkList.getCurrentTab();

            QueryEntry queryEntry = (QueryEntry) data.getSerializableExtra("query");
            if (queryEntry != null) {
                ((WorkReportListFragment) mFragments.get(currentTab)).getData(queryEntry);
            }
        }
    }
}
