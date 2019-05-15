package net.eanfang.client.ui.activity.worksapce.install;

import android.os.Bundle;

import com.eanfang.biz.model.WorkspaceInstallBean;
import com.eanfang.util.GetConstDataUtils;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.WorkInstallListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/12/21  13:57
 * @email houzhongzhou@yeah.net
 * @desc 报装管控列表
 */

public class InstallOrderActivity extends BaseClientActivity {
    private String titleBar;
    public final List<String> allmTitles = GetConstDataUtils.getInstallStatus();
    @BindView(R.id.tl_work_list)
    SlidingTabLayout tlWorkList;
    @BindView(R.id.vp_work_list)
    ViewPager vpWorkList;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyPagerAdapter mAdapter;
    private WorkspaceInstallBean workspaceInstallBean;
    private int dataType;


    public WorkspaceInstallBean getWorkspaceInstallBean() {
        return workspaceInstallBean;
    }

    public void setWorkspaceInstallBean(WorkspaceInstallBean workspaceInstallBean) {
        this.workspaceInstallBean = workspaceInstallBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);
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
            mFragments.add(WorkInstallListFragment.getInstance(title, dataType));
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpWorkList.setAdapter(mAdapter);
        tlWorkList.setViewPager(vpWorkList, mTitles, this, mFragments);
        vpWorkList.setCurrentItem(0);
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
