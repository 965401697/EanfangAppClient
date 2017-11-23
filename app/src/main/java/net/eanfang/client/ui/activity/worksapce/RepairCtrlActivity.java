package net.eanfang.client.ui.activity.worksapce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.config.Config;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.fragment.OrderListFragment;
import net.eanfang.client.ui.model.RepairedOrderBean;
import net.eanfang.client.util.GetConstDataUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/21  9:47
 * @email houzhongzhou@yeah.net
 * @desc 报修管控
 */

public class RepairCtrlActivity extends BaseActivity{
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private final List<String> mTitlesWorker = Config.getConfig().getRepairStatusWorker();
    private final List<String> mTitlesClient = Config.getConfig().getRepairStatusClient();

    private MyPagerAdapter mAdapter;
    private RepairedOrderBean repairedOrderBean;
    private String[] mTitles;
    private OrderListFragment currentFragment;

    public static void jumpActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,RepairCtrlActivity.class);
        ((BaseActivity) context).startAnimActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_ctrl);

        if (BuildConfig.APP_TYPE == 0) {
            mTitles = new String[mTitlesClient.size()];
            mTitlesClient.toArray(mTitles);

        } else {
            mTitles = new String[mTitlesWorker.size()];
            mTitlesWorker.toArray(mTitles);
        }

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
                initData(1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setTitle("报修管控");
        setLeftBack();
        currentFragment = (OrderListFragment) mFragments.get(0);
        initData(1);

    }


    public void initData(int page) {
        // loadingDialog.show();
        //2017年8月3日 lin
        JSONObject jsonObject = new JSONObject();
        try {
            if (!currentFragment.getTitle().equals("全部")) {
                String status = GetConstDataUtils.getRepairStatusByStr(currentFragment.getTitle());
                jsonObject.put("status", status);
            }
            if (!jsonObject.has("status")) {
                jsonObject.put("status", "");
            }
            jsonObject.put("page", page);
            jsonObject.put("rows", 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // new CommonRequestProtocol("/repairlist", jsonObject.toString(), 100008, this).execute();
        EanfangHttp.post(ApiService.REPAIR_LIST)
                .params("json", jsonObject.toString())
                .execute(new EanfangCallback<RepairedOrderBean>(this, true) {
                    @Override
                    public void onSuccess(final RepairedOrderBean bean) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                repairedOrderBean = bean;
                                currentFragment.onDataReceived();
                            }
                        });
                    }

                    @Override
                    public void onNoData(String message) {
                        super.onNoData(message);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RepairedOrderBean bean = new RepairedOrderBean();
                                bean.setAll(new ArrayList<RepairedOrderBean.AllBean>());
                                setBean(bean);
                                currentFragment.onDataReceived();
                            }
                        });
                    }

                    @Override
                    public void onError(String message) {
                        //重新加载 页面
                        currentFragment.onDataReceived();
                    }
                });

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
        initData(1);
    }
}
