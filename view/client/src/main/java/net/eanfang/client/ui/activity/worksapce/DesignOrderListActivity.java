package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;

import com.eanfang.util.GetConstDataUtils;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.DesignOrderListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  19:59
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class DesignOrderListActivity extends BaseClientActivity {

    private static String titleBar;
    public final List<String> allmTitles = GetConstDataUtils.getDesignStatus();
    @BindView(R.id.tl_design_list)
    SlidingTabLayout tlDesignList;
    @BindView(R.id.vp_design_list)
    ViewPager vpDesignList;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private int dataType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBar = getIntent().getStringExtra("title");
        dataType = getIntent().getIntExtra("type", 0);
        setTitle(titleBar);
        setLeftBack();

        mTitles = new String[allmTitles.size()];
        allmTitles.toArray(mTitles);
        for (String title : mTitles) {
            mFragments.add(DesignOrderListFragment.getInstance(title, dataType));
        }
        vpDesignList.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        });
        tlDesignList.setViewPager(vpDesignList, mTitles, this, mFragments);
        vpDesignList.setCurrentItem(0);


    }
}

