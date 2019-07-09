package net.eanfang.client.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
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
import net.eanfang.client.ui.fragment.login.PasswordFragment;
import net.eanfang.client.ui.fragment.login.VerifyFragment;

import java.util.ArrayList;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  11:56
 * @email houzhongzhou@yeah.net
 * @desc 登录
 */

/**
 * 废弃
 * 2019-04-30 15:26:04
 */
@Deprecated
public class LoginActivity extends BaseActivity implements OnTabSelectListener {

    private final String[] mTitles = {
            "短信快捷登录", "账号密码登录"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_eanfang);
        super.onCreate(savedInstanceState);
        initView();
        setTitle("登录");
        setLeftGone();
    }

    private void initView() {
        mFragments.add(VerifyFragment.getInstance());
        mFragments.add(PasswordFragment.getInstance());
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp_service);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.slidingtablayout);
        tabLayout_2.setViewPager(vp, mTitles, this, mFragments);
        tabLayout_2.setOnTabSelectListener(this);
        vp.setCurrentItem(0);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //处理返回按钮被按下
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //退出登录
            //Intent intent = new Intent(LoginActivity.this.getPackageName() + ".ExitListenerReceiver");
            //LoginActivity.this.sendBroadcast(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 滑动复写方法
     */
    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }


    /**
     * viewpager Adapter
     */
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
