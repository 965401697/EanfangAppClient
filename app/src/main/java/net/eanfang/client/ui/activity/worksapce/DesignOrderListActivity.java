package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import net.eanfang.client.ui.fragment.DesignOrderListFragment;
import net.eanfang.client.ui.model.DesignOrderListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  19:59
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class DesignOrderListActivity extends BaseActivity {

    @BindView(R.id.tl_design_list)
    SlidingTabLayout tlDesignList;

    @BindView(R.id.vp_design_list)
    ViewPager vpDesignList;

    public final List<String> allmTitles = Config.getConfig().getDesignOrderStatus();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private static String titleBar;
    private String type;
    private DesignOrderListBean designOrderListBean;
    private DesignOrderListFragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_list);
        ButterKnife.bind(this);
        initView();
        initData(1);
    }

    private void initView() {
        titleBar = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        setTitle(titleBar);
        setLeftBack();

        mTitles = new String[allmTitles.size()];
        allmTitles.toArray(mTitles);
        for (String title : mTitles) {
            mFragments.add(DesignOrderListFragment.getInstance(title, type));
        }

        vpDesignList.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        });
        tlDesignList.setViewPager(vpDesignList, mTitles, this, mFragments);
        vpDesignList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentFragment = (DesignOrderListFragment) mFragments.get(position);
                currentFragment.onDataReceived();
                initData(1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpDesignList.setCurrentItem(0);
        currentFragment = (DesignOrderListFragment) mFragments.get(0);

    }

    private void initData(int page) {
        String status = "";
        if (!currentFragment.getmTitle().equals("全部")) {
            status = allmTitles.indexOf(currentFragment.getmTitle()) + "";
            //  status =  GetConstDataUtils.getCheckReadStatusByStr(currentFragment.getmTitle());
        }
        HttpParams params = new HttpParams();
        params.put("page", page);
        params.put("rows", 10);
        params.put("type", type);
        params.put("status", status);

        doHttp(params);
    }

    private void doHttp(HttpParams params) {
        EanfangHttp.get(ApiService.GET_DESIGN_ORDER_LIST)
                .tag(this)
                .params(params)
                .execute(new EanfangCallback<DesignOrderListBean>(this, true) {
                    @Override
                    public void onSuccess(final DesignOrderListBean bean) {
                        runOnUiThread(() -> {
                            setDesignOrderListBean(bean);
                            currentFragment.onDataReceived();
                        });
                    }

                    @Override
                    public void onError(String message) {
                        currentFragment.onDataReceived();
                    }

                    @Override
                    public void onNoData(String message) {
                        super.onNoData(message);
                        runOnUiThread(() -> {
                            DesignOrderListBean bean = new DesignOrderListBean();
                            bean.setAll(new ArrayList<DesignOrderListBean.AllBean>());
                            setDesignOrderListBean(bean);
                            currentFragment.onDataReceived();
                        });
                    }
                });

    }


    public DesignOrderListBean getDesignOrderListBean() {
        return designOrderListBean;
    }

    public void setDesignOrderListBean(DesignOrderListBean designOrderListBean) {
        this.designOrderListBean = designOrderListBean;
    }
}

