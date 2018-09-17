package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DefendLogActivity extends BaseClientActivity {

    @BindView(R.id.tl_defend_log)
    SlidingTabLayout tlDefendLog;
    @BindView(R.id.vp_defend_log)
    ViewPager vpDefendLog;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private static String titleBar;
    private int dataType;
    private DefendLogActivity.MyPagerAdapter mAdapter;
//    private OpenShopLogFragment currentFragment;
    private String[] mTitles = {"未读日志", "已读日志", "全部日志"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defend_log);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBar = getIntent().getStringExtra("title");
        dataType = getIntent().getIntExtra("type", 0);
        setTitle(titleBar);
        setLeftBack();

        mFragments.add(DefendLogFragment.getInstance("未读日志", dataType));
        mFragments.add(DefendLogFragment.getInstance("已读日志", dataType));
        mFragments.add(DefendLogFragment.getInstance("全部日志", dataType));


        mAdapter = new DefendLogActivity.MyPagerAdapter(getSupportFragmentManager());
        vpDefendLog.setAdapter(mAdapter);
        tlDefendLog.setViewPager(vpDefendLog, mTitles, this, mFragments);
        vpDefendLog.setCurrentItem(0);

        vpDefendLog.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                currentFragment = (OpenShopLogFragment) mFragments.get(position);


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
