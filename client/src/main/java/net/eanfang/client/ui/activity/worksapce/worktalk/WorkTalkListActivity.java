package net.eanfang.client.ui.activity.worksapce.worktalk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.worktalk.WorkTalkListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/7/11  16:38
 * @decision 面谈员工 列表
 */
public class WorkTalkListActivity extends BaseActivity {

    @BindView(R.id.sl_worktalk_list)
    SlidingTabLayout slTabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    public final List<String> allmTitles = GetConstDataUtils.getWorkTalkStatus();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyPagerAdapter mAdapter;

    private WorkTalkListFragment workTalkListFragment;

    // 创建  收到 标示
    private String mType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrok_talk_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        mType = getIntent().getStringExtra("title");
        setTitle(mType);

        mTitles = new String[allmTitles.size()];
        allmTitles.toArray(mTitles);
        for (String title : mTitles) {
//            mFragments.add(WorkTalkListFragment.getInstance(title, mType));
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mAdapter);
        slTabLayout.setViewPager(viewpager, mTitles, this, mFragments);
        viewpager.setCurrentItem(0);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                workTalkListFragment = (WorkTalkListFragment) mFragments.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        workTalkListFragment = (WorkTalkListFragment) mFragments.get(0);
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
