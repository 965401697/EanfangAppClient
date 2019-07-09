package net.eanfang.client.ui.activity.worksapce.security;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.security.SecurityReceiveFragment;
import net.eanfang.client.ui.fragment.security.SecuritySendFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2019/3/25
 * @description 安防圈评论列表 我收到的 我评论的
 */

public class SecurityCommentListActivity extends BaseActivity implements OnTabSelectListener {

    private final String[] mCommentsTitle = {
            "我收到的", "我评论的"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_security_comment_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mFragments.add(SecurityReceiveFragment.getInstance("我收到的"));
        mFragments.add(SecuritySendFragment.getInstance("我评论的"));
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp_security_comment);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.slidingtablayout_security_comment);

        tabLayout_2.setViewPager(vp, mCommentsTitle, this, mFragments);
        tabLayout_2.setOnTabSelectListener(this);
        vp.setCurrentItem(0);

        setTitle("评论");
        setLeftBack();
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

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
            return mCommentsTitle[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
