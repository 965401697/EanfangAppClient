package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.config.Config;
import net.eanfang.client.network.apiservice.RepairApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.fragment.OrderListFragment;
import net.eanfang.client.ui.model.RepairedOrderBean;
import net.eanfang.client.util.GetConstDataUtils;
import net.eanfang.client.util.JsonUtils;
import net.eanfang.client.util.QueryEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/21  9:47
 * @email houzhongzhou@yeah.net
 * @desc 报修管控
 */

public class RepairCtrlActivity extends BaseActivity {
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private final List<String> mTitlesClient = Config.getConfig().getRepairStatusClient();

    private MyPagerAdapter mAdapter;
    private RepairedOrderBean repairedOrderBean;
    private String[] mTitles;
    private OrderListFragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_ctrl);

        mTitles = new String[mTitlesClient.size()];
        mTitlesClient.toArray(mTitles);
        for (String title : mTitles) {
            mFragments.add(OrderListFragment.getInstance(title));
        }
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_2);
        tabLayout_2.setViewPager(vp, mTitles, this, mFragments);

        vp.setCurrentItem(0);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentFragment = (OrderListFragment) mFragments.get(position);
                currentFragment.onDataReceived();
                initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setTitle("报修管控");
        setLeftBack();
        currentFragment = (OrderListFragment) mFragments.get(0);
        initData();

    }


    public void initData() {
        String status = null;
        QueryEntry queryEntry = new QueryEntry();
        if (!currentFragment.getTitle().equals("全部")) {
            status = GetConstDataUtils.getRepairStatusByStr(currentFragment.getTitle());
            queryEntry.getEquals().put("status", status);
        }
        queryEntry.setPage(1);
        queryEntry.setSize(10);

        EanfangHttp.post(RepairApi.GET_REPAIR_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<RepairedOrderBean>(this, true,RepairedOrderBean.class,(bean)->{
                    repairedOrderBean = bean;
                    currentFragment.onDataReceived();
                })
//                {
//                    @Override
//                    public void onSuccess(final RepairedOrderBean bean) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                repairedOrderBean = bean;
//                                currentFragment.onDataReceived();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onNoData(String message) {
//                        super.onNoData(message);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                RepairedOrderBean bean = new RepairedOrderBean();
//                                bean.setAll(new ArrayList<RepairedOrderBean.AllBean>());
//                                setBean(bean);
//                                currentFragment.onDataReceived();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        //重新加载 页面
//                        currentFragment.onDataReceived();
//                    }
//                }
                );

    }


    public RepairedOrderBean getBean() {
        return repairedOrderBean;
    }


    public synchronized void setBean(RepairedOrderBean bean) {
        this.repairedOrderBean = bean;
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


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
