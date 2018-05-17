package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.OrderDetailFragment;
import net.eanfang.client.ui.fragment.OrderProgressFragment;

import java.util.ArrayList;

/**
 * Created by MrHou
 *
 * @on 2017/11/23  19:46
 * @email houzhongzhou@yeah.net
 * @desc
 */

/**
 * 工作台-已报修-订单详情
 * Created by Administrator on 2017/3/15.
 */

public class OrderDetailActivity extends BaseClientActivity implements OnTabSelectListener {
    private final String[] mTitles = {
            "订单详情", "订单状态"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private Long id;
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        //loading加载动画
        loadingDialog.show();
        id = getIntent().getLongExtra("id", 0);
        title = getIntent().getStringExtra("title");

        mFragments.add(OrderDetailFragment.getInstance(id, title));
        mFragments.add(OrderProgressFragment.getInstance(id));
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_4);
        tabLayout_2.setViewPager(vp, mTitles, this, mFragments);
        tabLayout_2.setOnTabSelectListener(this);
        vp.setCurrentItem(0);
        setTitle("订单详情");
        setLeftBack();
        //去掉loading
        loadingDialog.dismiss();
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
            return mTitles[position];

        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}

