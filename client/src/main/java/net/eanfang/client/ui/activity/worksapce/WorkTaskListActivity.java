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
import com.eanfang.model.WorkTaskListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.WorkTaskListFragment;

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

public class WorkTaskListActivity extends BaseClientActivity {

    private static String titleBar;
    public final List<String> allmTitles = GetConstDataUtils.getWorkTaskStatus();
    @BindView(R.id.tl_work_list)
    SlidingTabLayout tlWorkList;
    @BindView(R.id.vp_work_list)
    ViewPager vpWorkList;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyPagerAdapter mAdapter;
    private WorkTaskListFragment currentFragment;
    private WorkTaskListBean workTaskListBean;
    private int dataType;

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
        dataType = getIntent().getIntExtra("type", 0);
        setTitle(titleBar);
        setLeftBack();
        mTitles = new String[allmTitles.size()];
        allmTitles.toArray(mTitles);
        for (String title : mTitles) {
            mFragments.add(WorkTaskListFragment.getInstance(title, dataType));
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

        QueryEntry queryEntry = new QueryEntry();
        if (!Constant.ALL.equals(currentFragment.getmTitle())) {
            String status = GetConstDataUtils.getWorkTaskStatus().indexOf(currentFragment.getmTitle()) + "";
            queryEntry.getEquals().put(Constant.STATUS, status);
        }
        if (Constant.COMPANY_DATA_CODE == dataType) {
            queryEntry.getEquals().put(Constant.CREATE_COMPANY_ID, EanfangApplication.getApplication().getCompanyId() + "");
        } else if (Constant.CREATE_DATA_CODE == dataType) {
            queryEntry.getEquals().put(Constant.CREATE_USER_ID, EanfangApplication.getApplication().getUserId() + "");
        } else if (Constant.ASSIGNEE_DATA_CODE == dataType) {
            queryEntry.getEquals().put(Constant.ASSIGNEE_USER_ID, EanfangApplication.getApplication().getUserId() + "");
        }


        queryEntry.setPage(page);
        queryEntry.setSize(5);
//        HttpParams params = new HttpParams();
//        params.put("page", page);
//        params.put("rows", 10);
//        params.put("type", type);
//        params.put("status", status);
        EanfangHttp.post(NewApiService.GET_WORK_TASK_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkTaskListBean>(this, true, WorkTaskListBean.class, (bean) -> {
                            runOnUiThread(() -> {
                                workTaskListBean = bean;
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
