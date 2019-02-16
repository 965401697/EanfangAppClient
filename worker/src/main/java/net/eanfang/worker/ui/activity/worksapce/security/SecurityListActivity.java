package net.eanfang.worker.ui.activity.worksapce.security;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.fragment.SecurituFoucsFragment;
import net.eanfang.worker.ui.fragment.SecurityHotFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/2/12
 * @description 安防圈 列表
 */

public class SecurityListActivity extends BaseActivity {

    @BindView(R.id.tl_security_list)
    SlidingTabLayout tlSecurityList;
    @BindView(R.id.vp_security_list)
    ViewPager vpSecurityList;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"关注", "热门"};
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("安防圈");
        setLeftBack();

        mFragments.add(SecurituFoucsFragment.getInstance("关注"));
        mFragments.add(SecurityHotFragment.getInstance("热门"));

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpSecurityList.setAdapter(mAdapter);
        tlSecurityList.setViewPager(vpSecurityList, mTitles, this, mFragments);

        vpSecurityList.setCurrentItem(0);
    }

    @OnClick(R.id.iv_create)
    public void onViewClicked() {
        JumpItent.jump(SecurityListActivity.this, SecurityCreateActivity.class);
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
