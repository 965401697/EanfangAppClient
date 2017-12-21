package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.Config;
import net.eanfang.client.network.apiservice.NewApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.fragment.WorkInstallListFragment;
import net.eanfang.client.ui.model.WorkspaceInstallBean;
import net.eanfang.client.util.GetConstDataUtils;
import net.eanfang.client.util.JsonUtils;
import net.eanfang.client.util.QueryEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/12/21  13:57
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class InstallOrderActivity extends BaseActivity {
    @BindView(R.id.tl_work_list)
    SlidingTabLayout tlWorkList;
    @BindView(R.id.vp_work_list)
    ViewPager vpWorkList;
    public final List<String> allmTitles = Config.getConfig().getInstallStatus();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyPagerAdapter mAdapter;
    private WorkspaceInstallBean workspaceInstallBean;
    private String type;
    private static String titleBar;
    private WorkInstallListFragment currentFragment;
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
        type = getIntent().getStringExtra("type");
        setTitle(titleBar);
        setLeftBack();
        mTitles = new String[allmTitles.size()];
        allmTitles.toArray(mTitles);
        for (String title : mTitles) {
            mFragments.add(WorkInstallListFragment.getInstance(title, type));
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
                currentFragment = (WorkInstallListFragment) mFragments.get(position);
                currentFragment.onDataReceived();
                initData(1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        currentFragment = (WorkInstallListFragment) mFragments.get(0);
        initData(1);
    }
    private void initData(int page) {
        String status = null;
        if (!currentFragment.getmTitle().equals("全部")) {
            status = GetConstDataUtils.getInstallStatusByStr(currentFragment.getmTitle());
        }
        QueryEntry queryEntry = new QueryEntry();
        if ("0".equals(type)) {
            queryEntry.getEquals().put("ownerCompanyId", EanfangApplication.getApplication().getCompanyId() + "");
        } else if ("1".equals(type)) {
            queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        }
        if (!currentFragment.getmTitle().equals("全部")){
            queryEntry.getEquals().put("status", status);
        }
        queryEntry.setPage(page);
        queryEntry.setSize(5);
        EanfangHttp.post(NewApiService.GET_WORK_INSTALL_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkspaceInstallBean>(this, true, WorkspaceInstallBean.class, (bean) -> {
                            runOnUiThread(() -> {
                                workspaceInstallBean = bean;
                                currentFragment.onDataReceived();
                            });
                        })
//                {
//
//                    @Override
//                    public void onSuccess(final WorkTaskListBean bean) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                workTaskListBean = bean;
//                                currentFragment.onDataReceived();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        currentFragment.onDataReceived();
//                    }
//
//                    @Override
//                    public void onNoData(String message) {
//                        super.onNoData(message);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                WorkTaskListBean bean = new WorkTaskListBean();
//                                bean.setAll(new ArrayList<WorkTaskListBean.AllBean>());
//                                setWorkTaskListBean(bean);
//                                currentFragment.onDataReceived();
//                            }
//                        });
//                    }
//                }
                );

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
