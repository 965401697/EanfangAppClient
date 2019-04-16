package net.eanfang.worker.ui.activity.my;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.fragment.evaluate.EvaluateFragment;
import net.eanfang.worker.ui.fragment.evaluate.GiveEvaluateFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/17  15:31
 * @email houzhongzhou@yeah.net
 * @desc 评价
 */

public class EvaluateActivity extends BaseWorkerActivity implements OnTabSelectListener {
    private final String[] mTitlesWorker = {
            "收到的评价", "给客户的评价"
    };
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mFragments.add(EvaluateFragment.getInstance());
        mFragments.add(GiveEvaluateFragment.getInstance());
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_4);

        tabLayout_2.setViewPager(vp, mTitlesWorker, this, mFragments);
        tabLayout_2.setOnTabSelectListener(this);
        vp.setCurrentItem(0);

        setTitle("评价");
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
            return mTitlesWorker[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
