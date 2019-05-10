package net.eanfang.worker.ui.activity.worksapce;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.fragment.customservice.CompanyServiceFragment;
import net.eanfang.worker.ui.fragment.customservice.PersonalServiceFragment;

import java.util.ArrayList;

/**
* @date on 2018/5/3  10:23
* @author Guanluocang
* @decision  客服
*/
public class CustomerServiceActivity extends BaseActivity implements OnTabSelectListener {
    private final String[] mTitles = {
            "个人客户", "企业用户"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        initView();
    }

    private void initView() {
        setTitle("客服帮助");
        setLeftBack();

        mFragments.add(PersonalServiceFragment.getInstance());
        mFragments.add(CompanyServiceFragment.getInstance());
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
