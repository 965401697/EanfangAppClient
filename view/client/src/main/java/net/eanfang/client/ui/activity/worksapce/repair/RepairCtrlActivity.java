package net.eanfang.client.ui.activity.worksapce.repair;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.OrderListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/21  9:47
 * @email houzhongzhou@yeah.net
 * @desc 报修管控
 */

public class RepairCtrlActivity extends BaseClientActivity {
    private final List<String> mTitlesClient = GetConstDataUtils.getRepairStatus();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private String[] mTitles;
    public SlidingTabLayout tabLayout_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_repair_ctrl);
        super.onCreate(savedInstanceState);
        mTitles = new String[mTitlesClient.size()];
        mTitlesClient.toArray(mTitles);
        for (String title : mTitles) {
            mFragments.add(OrderListFragment.getInstance(title));
        }
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_2);
        tabLayout_2.setViewPager(vp, mTitles, this, mFragments);
        vp.setCurrentItem(0);

        setTitle("报修管控");
        setLeftBack();


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
