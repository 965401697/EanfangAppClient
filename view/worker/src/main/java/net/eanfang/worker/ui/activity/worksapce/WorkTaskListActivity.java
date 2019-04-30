package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.util.GetConstDataUtils;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.fragment.WorkTaskListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  17:33
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkTaskListActivity extends BaseWorkerActivity {

    private static String titleBar;
    public final List<String> allmTitles = GetConstDataUtils.getWorkTaskStatus();
    @BindView(R.id.tl_work_list)
    SlidingTabLayout tlWorkList;
    @BindView(R.id.vp_work_list)
    ViewPager vpWorkList;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyPagerAdapter mAdapter;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBar = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        setTitle(titleBar);
        setLeftBack();
        mTitles = new String[allmTitles.size()];
        allmTitles.toArray(mTitles);
        for (String title : mTitles) {
            mFragments.add(WorkTaskListFragment.getInstance(title, type));
        }


        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpWorkList.setAdapter(mAdapter);
        tlWorkList.setViewPager(vpWorkList, mTitles, this, mFragments);
        vpWorkList.setCurrentItem(0);

        vpWorkList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
}
