package net.eanfang.worker.ui.activity.worksapce.worktransfer;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.flyco.tablayout.SlidingTabLayout;


import net.eanfang.worker.R;
import net.eanfang.worker.ui.fragment.worktransfer.WorkTransferFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/7/27  16:11
 * @decision 交接班列表
 */
public class WorkTransferListActivity extends BaseActivity {

    @BindView(R.id.sl_worktransfer_list)
    SlidingTabLayout slTabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    public final List<String> allmTitles = GetConstDataUtils.getWorkTransfer();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyPagerAdapter mAdapter;

    private WorkTransferFragment workTransferFragment;

    // 创建  收到 标示
    private String mType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_work_transfer_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setLeftBack();
        mType = getIntent().getStringExtra("title");
        setTitle(mType);

        mTitles = new String[allmTitles.size()];
        allmTitles.toArray(mTitles);
        for (String title : mTitles) {
//            mFragments.add(WorkTransferFragment.getInstance(title, mType));
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mAdapter);
        slTabLayout.setViewPager(viewpager, mTitles, this, mFragments);
        viewpager.setCurrentItem(0);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                workTransferFragment = (WorkTransferFragment) mFragments.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        workTransferFragment = (WorkTransferFragment) mFragments.get(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        workTransferFragment.onActivityResult(requestCode, resultCode, data);
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
