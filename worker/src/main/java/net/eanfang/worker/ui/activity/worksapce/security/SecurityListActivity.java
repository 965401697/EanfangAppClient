package net.eanfang.worker.ui.activity.worksapce.security;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;

import com.eanfang.application.EanfangApplication;
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
import q.rorbin.badgeview.QBadgeView;

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

    private QBadgeView qBadgeViewMaintain = new QBadgeView(EanfangApplication.get().getApplicationContext());

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
        setRightTitle("我的");
        setRightImageResId(R.mipmap.ic_security_right);
        mFragments.add(SecurituFoucsFragment.getInstance("关注"));
        mFragments.add(SecurityHotFragment.getInstance("热门"));

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpSecurityList.setAdapter(mAdapter);
        tlSecurityList.setViewPager(vpSecurityList, mTitles, this, mFragments);

        vpSecurityList.setCurrentItem(0);

        setRightTitleOnClickListener((v) -> {
            JumpItent.jump(SecurityListActivity.this, SecurityPersonalActivity.class);
        });

        qBadgeViewMaintain.bindTarget(findViewById(R.id.tv_right))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(3, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 6, true)
                .setBadgeTextSize(11, true);
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
                ((SecurituFoucsFragment) mFragments.get(currentTab)).refreshStatus();
            } else {
                ((SecurityHotFragment) mFragments.get(currentTab)).refreshStatus();
            }
        }
    }

    public void doRefreshMessage(int mMessageCount) {
        qBadgeViewMaintain.setBadgeNumber(mMessageCount);
    }
}
