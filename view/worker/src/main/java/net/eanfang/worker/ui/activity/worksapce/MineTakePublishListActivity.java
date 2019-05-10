package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.model.MineTaskListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.fragment.MineTakePublishListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/12/25  16:01
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MineTakePublishListActivity extends BaseActivity {
    @BindView(R.id.tl_work_list)
    SlidingTabLayout tlWorkList;
    @BindView(R.id.vp_work_list)
    ViewPager vpWorkList;

    public final List<String> allmTitles = GetConstDataUtils.getTaskPublishStatus();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyPagerAdapter mAdapter;
    private MineTakePublishListFragment currentFragment;
    private static String titleBar;
    private MineTaskListBean workReportListBean;
    private int dataType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_report_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBar = getIntent().getStringExtra("title");
        dataType = getIntent().getIntExtra("type", 0);
        setTitle(titleBar);
        setLeftBack();
        allmTitles.remove("全部");
        mTitles = new String[allmTitles.size()];
        allmTitles.toArray(mTitles);
        for (String title : mTitles) {
            mFragments.add(MineTakePublishListFragment.getInstance(title, dataType));
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpWorkList.setAdapter(mAdapter);
        tlWorkList.setViewPager(vpWorkList, mTitles, this, mFragments);
        vpWorkList.setCurrentItem(0);

//        vpWorkList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                currentFragment = (MineTakePublishListFragment) mFragments.get(position);
//                currentFragment.onDataReceived();
//                initData(1);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        currentFragment = (MineTakePublishListFragment) mFragments.get(0);
//        initData(1);

    }

//    private void initData(int page) {
//        int status = GetConstDataUtils.getTaskPublishStatus().indexOf(currentFragment.getmTitle());
//
//        QueryEntry queryEntry = new QueryEntry();
//        if (Constant.CREATE_DATA_CODE == (dataType)) {
//            queryEntry.getEquals().put("assigneeUserId", EanfangApplication.getApplication().getUserId() + "");
//        } else if (Constant.ASSIGNEE_DATA_CODE == (dataType)) {
//            queryEntry.getEquals().put("assigneeCompanyId", EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId() + "");
//        }
//        queryEntry.getEquals().put("status", status + "");
//
//        queryEntry.setPage(page);
//        queryEntry.setSize(5);
//
//        EanfangHttp.post(NewApiService.TASK_APPLY_PUBLISH_LIST)
//                .upJson(JsonUtils.obj2String(queryEntry))
//                .execute(new EanfangCallback<MineTaskListBean>(this, true, MineTaskListBean.class, (bean) -> {
//                    runOnUiThread(() -> {
//                        workReportListBean = bean;
//                        currentFragment.onDataReceived();
//                    });
//                }));
//
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        initData(1);
//    }

//    public MineTaskListBean getWorkReportListBean() {
//        return workReportListBean;
//    }
//
//    public void setWorkReportListBean(MineTaskListBean workReportListBean) {
//        this.workReportListBean = workReportListBean;
//    }

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
