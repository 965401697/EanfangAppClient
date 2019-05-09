package net.eanfang.client.ui.activity.worksapce.openshop;

import android.os.Bundle;

import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenShopLogActivity extends BaseClientActivity {
    @BindView(R.id.tl_shop_log)
    SlidingTabLayout tlShopLog;
    @BindView(R.id.vp_shop_log)
    ViewPager vpShopLog;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private static String titleBar;
    private int dataType;
    private MyPagerAdapter mAdapter;
    private OpenShopLogFragment currentFragment;
    private String[] mTitles = {"未读日志", "已读日志", "全部日志"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop_log);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBar = getIntent().getStringExtra("title");
        dataType = getIntent().getIntExtra("type", 0);
        setTitle(titleBar);
        setLeftBack();

        mFragments.add(OpenShopLogFragment.getInstance("未读日志", dataType));
        mFragments.add(OpenShopLogFragment.getInstance("已读日志", dataType));
        mFragments.add(OpenShopLogFragment.getInstance("全部日志", dataType));


        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpShopLog.setAdapter(mAdapter);
        tlShopLog.setViewPager(vpShopLog, mTitles, this, mFragments);
        vpShopLog.setCurrentItem(0);

        vpShopLog.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentFragment = (OpenShopLogFragment) mFragments.get(position);

 
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
