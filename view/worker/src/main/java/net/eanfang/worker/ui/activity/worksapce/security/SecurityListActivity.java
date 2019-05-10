package net.eanfang.worker.ui.activity.worksapce.security;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.fragment.SecurityFoucsFragment;
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


    private final int FILTRATE_TYPE_CODE = 101;

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
        setRightTitle("个人中心");
        mFragments.add(SecurityFoucsFragment.getInstance("关注"));
        mFragments.add(SecurityHotFragment.getInstance("热门"));

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpSecurityList.setAdapter(mAdapter);
        tlSecurityList.setViewPager(vpSecurityList, mTitles, this, mFragments);

        vpSecurityList.setCurrentItem(0);

        setRightTitleOnClickListener((v) -> {
            JumpItent.jump(SecurityListActivity.this, SecurityPersonalActivity.class);
        });
    }

    @OnClick(R.id.iv_create)
    public void onViewClicked() {
        JumpItent.jump(SecurityListActivity.this, SecurityCreateActivity.class, FILTRATE_TYPE_CODE);
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
        int currentTab = tlSecurityList.getCurrentTab();
        if (resultCode == RESULT_OK && requestCode == FILTRATE_TYPE_CODE) {
            if (currentTab == 0) {
                ((SecurityFoucsFragment) mFragments.get(currentTab)).refreshStatus();
            } else {
                ((SecurityHotFragment) mFragments.get(currentTab)).refreshStatus();
            }
        }
    }
}
