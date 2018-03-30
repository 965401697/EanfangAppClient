package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.DesignOrderListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.DesignOrderListFragment;

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

public class DesignOrderListActivity extends BaseClientActivity {

    private static String titleBar;
    public  List<String> allmTitles;
    @BindView(R.id.tl_design_list)
    SlidingTabLayout tlDesignList;
    @BindView(R.id.vp_design_list)
    ViewPager vpDesignList;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private int dataType;
    private DesignOrderListBean designOrderListBean;
    private DesignOrderListFragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_list);
        ButterKnife.bind(this);
        initView();
        initData(1);
        allmTitles = constDataUtils.getDesignStatus();
    }

    private void initView() {
        titleBar = getIntent().getStringExtra("title");
        dataType = getIntent().getIntExtra("type", 0);
        setTitle(titleBar);
        setLeftBack();

        mTitles = new String[allmTitles.size()];
        allmTitles.toArray(mTitles);
        for (String title : mTitles) {
            mFragments.add(DesignOrderListFragment.getInstance(title, dataType));
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
        /*我创建的   createUserId=(当前登录人userID)
        已提交status=0     已处理
        status=1  已完成status=2
        本公司的 createCompanyId=(当前登录人公司ID)  */
        QueryEntry queryEntry = new QueryEntry();
        if (!Constant.ALL.equals(currentFragment.getmTitle())) {
            String status = allmTitles.indexOf(currentFragment.getmTitle()) + "";
            queryEntry.getEquals().put(Constant.STATUS, status);
        }
        if (Constant.COMPANY_DATA_CODE == dataType) {
            queryEntry.getEquals().put(Constant.CREATE_COMPANY_ID, EanfangApplication.getApplication().getCompanyId() + "");
        } else if (Constant.CREATE_DATA_CODE == dataType) {
            queryEntry.getEquals().put(Constant.CREATE_USER_ID, EanfangApplication.getApplication().getUserId() + "");
        }
        queryEntry.setPage(page);
        queryEntry.setSize(5);
        EanfangHttp.post(NewApiService.GET_WORK_DESIGN_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<DesignOrderListBean>(this, true, DesignOrderListBean.class, (bean) -> {
                    runOnUiThread(() -> {
                        setDesignOrderListBean(bean);
                        currentFragment.onDataReceived();
                    });
                }));
    }


    public DesignOrderListBean getDesignOrderListBean() {
        return designOrderListBean;
    }

    public void setDesignOrderListBean(DesignOrderListBean designOrderListBean) {
        this.designOrderListBean = designOrderListBean;
    }
}

