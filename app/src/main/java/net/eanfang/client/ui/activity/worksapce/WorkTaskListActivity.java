package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.okgo.model.HttpParams;

import net.eanfang.client.R;
import net.eanfang.client.config.Config;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.fragment.WorkTaskListFragment;
import net.eanfang.client.ui.model.WorkTaskListBean;
import net.eanfang.client.util.GetConstDataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  17:33
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkTaskListActivity extends BaseActivity{

    @BindView(R.id.tl_work_list)
    SlidingTabLayout tlWorkList;
    @BindView(R.id.vp_work_list)
    ViewPager vpWorkList;
    public final List<String> allmTitles = Config.getConfig().getTaskReadStatus();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyPagerAdapter mAdapter;
    private WorkTaskListFragment currentFragment;
    private static String titleBar;
    private WorkTaskListBean workTaskListBean;
    private String type;

    public WorkTaskListBean getWorkTaskListBean() {
        return workTaskListBean;
    }

    public void setWorkTaskListBean(WorkTaskListBean workTaskListBean) {
        this.workTaskListBean = workTaskListBean;
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
        type = getIntent().getStringExtra("type");
        setTitle(titleBar);
        setLeftBack();
        mTitles = new String[allmTitles.size()];
        allmTitles.toArray(mTitles);
        for (String title : mTitles) {
            mFragments.add(WorkTaskListFragment.getInstance(title, type));
        }


        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpWorkList.setAdapter(mAdapter);
        tlWorkList.setViewPager(vpWorkList, mTitles, this, mFragments);
        vpWorkList.setCurrentItem(0);

        vpWorkList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentFragment = (WorkTaskListFragment) mFragments.get(position);
                currentFragment.onDataReceived();
                initData(1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        currentFragment = (WorkTaskListFragment) mFragments.get(0);
        initData(1);
    }

    private void initData(int page) {
        String status = "";
        if (!currentFragment.getmTitle().equals("全部")) {
            status = GetConstDataUtils.getTaskReadStatusByStr(currentFragment.getmTitle());
        }
        HttpParams params = new HttpParams();
        params.put("page", page);
        params.put("rows", 10);
        params.put("type", type);
        params.put("status", status);
        EanfangHttp.get(ApiService.GET_WORK_TASK_LIST)
                .tag(this)
                .params(params)
                .execute(new EanfangCallback<WorkTaskListBean>(this, true) {

                    @Override
                    public void onSuccess(final WorkTaskListBean bean) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                workTaskListBean = bean;
                                currentFragment.onDataReceived();
                            }
                        });
                    }

                    @Override
                    public void onError(String message) {
                        currentFragment.onDataReceived();
                    }

                    @Override
                    public void onNoData(String message) {
                        super.onNoData(message);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WorkTaskListBean bean = new WorkTaskListBean();
                                bean.setAll(new ArrayList<WorkTaskListBean.AllBean>());
                                setWorkTaskListBean(bean);
                                currentFragment.onDataReceived();
                            }
                        });
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(1);
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
