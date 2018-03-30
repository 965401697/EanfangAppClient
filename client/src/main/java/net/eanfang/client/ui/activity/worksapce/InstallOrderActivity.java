package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkspaceInstallBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.WorkInstallListFragment;

import java.util.ArrayList;
import java.util.List;

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
    private static String titleBar;
    public List<String> allmTitles;
    @BindView(R.id.tl_work_list)
    SlidingTabLayout tlWorkList;
    @BindView(R.id.vp_work_list)
    ViewPager vpWorkList;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyPagerAdapter mAdapter;
    private WorkspaceInstallBean workspaceInstallBean;
    private int dataType;
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
        allmTitles = constDataUtils.getInstallStatus();

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

        vpWorkList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentFragment = (WorkInstallListFragment) mFragments.get(position);
                currentFragment.onDataReceived();
                initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        currentFragment = (WorkInstallListFragment) mFragments.get(0);
        initData();
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        if (!Constant.ALL.equals(currentFragment.getmTitle())) {
            String status = allmTitles.indexOf(currentFragment.getmTitle()) + "";
            queryEntry.getEquals().put(Constant.STATUS, status);
        }

        if (Constant.COMPANY_DATA_CODE == dataType) {
            queryEntry.getEquals().put(Constant.OWNER_COMPANY_ID, EanfangApplication.getApplication().getCompanyId() + "");
        } else if (Constant.CREATE_DATA_CODE == dataType) {
            queryEntry.getEquals().put(Constant.CREATE_USER_ID, EanfangApplication.getApplication().getUserId() + "");
        }
        queryEntry.setPage(1);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.GET_WORK_INSTALL_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkspaceInstallBean>(this, true, WorkspaceInstallBean.class) {
                    @Override
                    public void onSuccess(final WorkspaceInstallBean bean) {
                        super.onSuccess(bean);
                        runOnUiThread(() -> {
                            workspaceInstallBean = bean;
                            currentFragment.onDataReceived();
                        });
                    }

                    @Override
                    public void onNoData(String message) {
                        super.onNoData(message);
                        runOnUiThread(() -> {
                            WorkspaceInstallBean bean = new WorkspaceInstallBean();
                            bean.setList(new ArrayList<>());
                            setWorkspaceInstallBean(bean);
                            currentFragment.onDataReceived();
                        });
                    }
                });


    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
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
