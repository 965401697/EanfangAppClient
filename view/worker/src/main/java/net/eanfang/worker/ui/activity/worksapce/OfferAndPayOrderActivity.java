package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.biz.model.PayOrderListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.fragment.OfferAndPayListFragment1;

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

public class OfferAndPayOrderActivity extends BaseActivity {
    @BindView(R.id.tl_work_list)
    SlidingTabLayout tlWorkList;
    @BindView(R.id.vp_work_list)
    ViewPager vpWorkList;

    public final List<String> allmTitles = GetConstDataUtils.getQuoteStatus();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyPagerAdapter mAdapter;
    private OfferAndPayListFragment1 currentFragment;
    private static String titleBar;
    private PayOrderListBean workReportListBean;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_report_list);
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
            mFragments.add(OfferAndPayListFragment1.getInstance(title, type));
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
                currentFragment = (OfferAndPayListFragment1) mFragments.get(position);
//                currentFragment.onDataReceived();
//                initData(1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        currentFragment = (OfferAndPayListFragment) mFragments.get(0);
//        initData(1);

    }

//    private void initData(int page) {
//        int status = GetConstDataUtils.getQuoteStatus().indexOf(currentFragment.getmTitle());
//
//        QueryEntry queryEntry = new QueryEntry();
//        if ("1".equals(type)) {
//            queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
//        } else if ("2".equals(type)) {
//            queryEntry.getEquals().put("assigneeCompanyId", EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId() + "");
//        }
//        queryEntry.getEquals().put("status", status + "");
//
//        queryEntry.setPage(page);
//        queryEntry.setSize(5);
//
//        EanfangHttp.post(NewApiService.QUOTE_ORDER_LIST)
//                .upJson(JsonUtils.obj2String(queryEntry))
//                .execute(new EanfangCallback<PayOrderListBean>(this, true, PayOrderListBean.class, (bean) -> {
//                    runOnUiThread(() -> {
//                        workReportListBean = bean;
//                        setWorkReportListBean(bean);
////                        currentFragment.onDataReceived();
//                    });
//                }));
//
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
////        initData(1);
//    }
//
//    public PayOrderListBean getWorkReportListBean() {
//        return workReportListBean;
//    }
//
//    public void setWorkReportListBean(PayOrderListBean workReportListBean) {
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
