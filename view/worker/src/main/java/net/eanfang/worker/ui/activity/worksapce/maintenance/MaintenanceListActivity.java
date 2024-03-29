package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;

import com.annimon.stream.Stream;
import com.eanfang.ui.base.BaseEvent;
import com.eanfang.util.GetConstDataUtils;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaintenanceListActivity extends BaseWorkerActivity {

    @BindView(R.id.tl_2)
    SlidingTabLayout tl2;
    @BindView(R.id.vp)
    ViewPager vp;

    public final List<String> allmTitles = GetConstDataUtils.getMaintainStatusList();
    private String[] mTitles;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private MaintenanceListFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_maintenance_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        int type = getIntent().getIntExtra("type", 0);

        //剔除 待执行 订单取消
        List tempList = Stream.of(allmTitles).filter(title -> !"待执行".equals(title) && !"订单取消".equals(title)).toList();
        mTitles = new String[tempList.size()];
        tempList.toArray(mTitles);

        for (String title : mTitles) {
            mFragments.add(MaintenanceListFragment.getInstance(type, title));
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        vp.setCurrentItem(0);
        tl2.setViewPager(vp, mTitles, this, mFragments);
        currentFragment = (MaintenanceListFragment) mFragments.get(0);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentFragment = (MaintenanceListFragment) mFragments.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setTitle("维保订单");
        setLeftBack();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceived(BaseEvent baseEvent) {
        currentFragment.getmAdapter().remove(currentFragment.getCurrentPosition());
        Log.e("zzw", "刷新");
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
