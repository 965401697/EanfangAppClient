package net.eanfang.client.ui.activity.worksapce.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.fragment.SecurityFoucsFragment;
import net.eanfang.client.ui.fragment.SecurityHotFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;

import static net.eanfang.client.ui.fragment.SecurityFoucsFragment.REFRESH_ITEM;

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
    private String[] mTitles = {"热门", "关注"};
    private MyPagerAdapter mAdapter;

    private final int REQUEST_LIST = 1021;
    private final int FILTRATE_TYPE_CODE = 101;

    private int mSecurityNum;
    private QBadgeView qBadgeViewMaintain = new QBadgeView(ClientApplication.get().getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_security_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setTitle("安防圈");
        setLeftBack();
        setRightTitle("我的");
        setRightImageResId(R.mipmap.ic_security_right);
        mFragments.add(SecurityHotFragment.getInstance("热门"));
        mFragments.add(SecurityFoucsFragment.getInstance("关注"));
        mSecurityNum = getIntent().getIntExtra("mSecurityNum", 0);

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpSecurityList.setAdapter(mAdapter);
        tlSecurityList.setViewPager(vpSecurityList, mTitles, this, mFragments);

        vpSecurityList.setCurrentItem(0);

        setRightTitleOnClickListener((v) -> {
            JumpItent.jump(SecurityListActivity.this, SecurityPersonalActivity.class, REQUEST_LIST);
        });

        qBadgeViewMaintain.bindTarget(findViewById(R.id.tv_right))
                .setBadgeNumber(mSecurityNum)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(3, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 6, true)
                .setBadgeTextSize(10, true);
        qBadgeViewMaintain.setBadgeNumber(mSecurityNum);
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
                ((SecurityHotFragment) mFragments.get(currentTab)).refreshStatus();
            } else {
                ((SecurityFoucsFragment) mFragments.get(currentTab)).refreshStatus();
            }

        } else if (resultCode == RESULT_OK && requestCode == REFRESH_ITEM) {
            if (currentTab == 0) {
                ((SecurityHotFragment) mFragments.get(currentTab)).refreshItemStatus(data);
            } else {
                ((SecurityFoucsFragment) mFragments.get(currentTab)).refreshItemStatus(data);
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_LIST) {
            // list 回来更新数量
            mSecurityNum = data.getIntExtra("mSecurityNum", 0);
            qBadgeViewMaintain.setBadgeNumber(mSecurityNum);
        }
    }


}
