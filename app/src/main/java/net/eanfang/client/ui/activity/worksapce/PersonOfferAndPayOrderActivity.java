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
import net.eanfang.client.config.Constant;
import net.eanfang.client.network.apiservice.NewApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.fragment.PersonOfferAndPayListFragment;
import net.eanfang.client.ui.model.PayOrderListBean;
import net.eanfang.client.util.JsonUtils;
import net.eanfang.client.util.QueryEntry;

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

public class PersonOfferAndPayOrderActivity extends BaseActivity {
    @BindView(R.id.tl_work_list)
    SlidingTabLayout tlWorkList;
    @BindView(R.id.vp_work_list)
    ViewPager vpWorkList;

    public final List<String> allmTitles = Config.getConfig().getConstBean().getQuoteOrderConstant().get(Constant.CUST_STATUS);
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyPagerAdapter mAdapter;
    private PersonOfferAndPayListFragment currentFragment;
    private PayOrderListBean workReportListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_report_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("订单明细");
        setLeftBack();
        setLeftBack();

        mTitles = new String[allmTitles.size()];
        allmTitles.toArray(mTitles);
        for (String title : mTitles) {
            mFragments.add(PersonOfferAndPayListFragment.getInstance(title));
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
                currentFragment = (PersonOfferAndPayListFragment) mFragments.get(position);
                currentFragment.onDataReceived();
                initData(1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        currentFragment = (PersonOfferAndPayListFragment) mFragments.get(0);
        initData(1);

    }

    private void initData(int page) {
        int status = Config.getConfig().getConstBean().getQuoteOrderConstant().get(Constant.CUST_STATUS).indexOf(currentFragment.getmTitle());

        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("assigneeUserId", EanfangApplication.getApplication().getUserId() + "");
        queryEntry.getEquals().put("status", status + "");

        queryEntry.setPage(page);
        queryEntry.setSize(5);

        EanfangHttp.post(NewApiService.QUOTE_ORDER_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<PayOrderListBean>(this, true, PayOrderListBean.class, (bean) -> {
                    runOnUiThread(() -> {
                        workReportListBean = bean;
                        currentFragment.onDataReceived();
                    });
                }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(1);
    }

    public PayOrderListBean getWorkReportListBean() {
        return workReportListBean;
    }

    public void setWorkReportListBean(PayOrderListBean workReportListBean) {
        this.workReportListBean = workReportListBean;
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
