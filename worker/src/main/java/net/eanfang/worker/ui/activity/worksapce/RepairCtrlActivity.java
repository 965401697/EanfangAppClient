package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.RepairedOrderBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.fragment.OrderListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/21  9:47
 * @email houzhongzhou@yeah.net
 * @desc 报修管控
 */

public class RepairCtrlActivity extends BaseWorkerActivity {
    private final List<String> mTitlesWorker = constDataUtils.getRepairStatus();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private RepairedOrderBean repairedOrderBean;
    private String[] mTitles;
    private OrderListFragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_ctrl);

        mTitles = new String[mTitlesWorker.size()];
        mTitlesWorker.toArray(mTitles);


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
        if (!"全部".equals(currentFragment.getTitle())) {
            status = constDataUtils.getRepairStatus().indexOf(currentFragment.getTitle()) + "";
            queryEntry.getEquals().put("status", status);
        }
        queryEntry.setPage(1);
        queryEntry.setSize(10);

        EanfangHttp.post(RepairApi.GET_REPAIR_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<RepairedOrderBean>(this, true, RepairedOrderBean.class)
                         {
                             @Override
                             public void onSuccess(final RepairedOrderBean bean) {
                                 runOnUiThread(() -> {
                                     repairedOrderBean = bean;
                                     currentFragment.onDataReceived();
                                 });
                             }

                             @Override
                             public void onNoData(String message) {
                                 super.onNoData(message);
                                 runOnUiThread(() -> {
                                     RepairedOrderBean bean = new RepairedOrderBean();
                                     bean.setList(new ArrayList<>());
                                     setBean(bean);
                                     currentFragment.onDataReceived();
                                 });
                             }

                             @Override
                             public void onError(String message) {
                                 //重新加载 页面
                                 currentFragment.onDataReceived();
                             }
                         }
                );

    }


    public RepairedOrderBean getBean() {
        return repairedOrderBean;
    }


    public synchronized void setBean(RepairedOrderBean bean) {
        this.repairedOrderBean = bean;
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
