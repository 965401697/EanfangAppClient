package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.im.SelectIMContactActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.fragment.OrderDetailFragment;
import net.eanfang.worker.ui.fragment.OrderProgressFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MrHou
 *
 * @on 2017/11/23  19:46
 * @email houzhongzhou@yeah.net
 * @desc
 */

/**
 * 工作台-已报修-订单详情
 * Created by Administrator on 2017/3/15.
 */

public class OrderDetailActivity extends BaseWorkerActivity implements OnTabSelectListener {
    private final String[] mTitles = {
            "订单详情", "订单状态"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private Long id;
    private String mOrderTime = "";
    private boolean isVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        //loading加载动画
        loadingDialog.show();

        id = getIntent().getLongExtra("id", 0);
        mOrderTime = getIntent().getStringExtra("orderTime");

        //分享按钮是是否隐藏
        isVisible = getIntent().getBooleanExtra("isVisible", false);

        mFragments.add(OrderDetailFragment.getInstance(id));
        mFragments.add(OrderProgressFragment.getInstance(id, mOrderTime));
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_4);
        tabLayout_2.setViewPager(vp, mTitles, this, mFragments);
        tabLayout_2.setOnTabSelectListener(this);
        vp.setCurrentItem(0);
        setTitle("订单详情");
        setLeftBack();
        //去掉loading
        loadingDialog.dismiss();

        if (!isVisible) {
            setRightTitle("分享");
            setRightTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //分享聊天

                    HashMap hashMap = ((OrderDetailFragment) mFragments.get(0)).getHashMap();

                    Intent intent = new Intent(OrderDetailActivity.this, SelectIMContactActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("id", (String) hashMap.get("id"));
                    bundle.putString("orderNum", (String) hashMap.get("orderNum"));
                    bundle.putString("picUrl", (String) hashMap.get("picUrl"));
                    bundle.putString("creatTime", (String) hashMap.get("creatTime"));
                    bundle.putString("workerName", (String) hashMap.get("workerName"));
                    bundle.putString("status", (String) hashMap.get("status"));
                    bundle.putString("shareType", (String) hashMap.get("shareType"));

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }


    @Override
    public void onTabSelect(int position) {
    }

    @Override
    public void onTabReselect(int position) {
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

