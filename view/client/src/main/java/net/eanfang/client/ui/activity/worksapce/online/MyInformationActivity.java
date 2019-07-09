package net.eanfang.client.ui.activity.worksapce.online;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GlideUtil;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.worktransfer.MyInfoAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.OfferAndPayListFragment1;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyInformationActivity extends BaseClientActivity {
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_fen)
    TextView userFen;
    @BindView(R.id.user_look)
    TextView userLook;
    @BindView(R.id.user_good)
    TextView userGood;
    @BindView(R.id.tl_task_zyr)
    SlidingTabLayout tlTaskZyr;
    @BindView(R.id.vp_task_list)
    ViewPager vpTaskList;
    @BindView(R.id.iv_company_logo)
    CircleImageView ivCompanyLogo;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"点赞记录", "历史记录"};
    private MyPagerAdapter mPagerAdapter;
    private MyInfoAdapter mAdapter;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_information);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("我的回答");
        initView();
        //mAdapter = new MyInfoAdapter();
        //mAdapter.bindToRecyclerView(rvList);
        getData();


    }

    private void getData() {
        EanfangHttp.post(NewApiService.My_Info_LIST)
                .execute(new EanfangCallback<MyInfoBean>(MyInformationActivity.this, true, MyInfoBean.class) {
                             @Override
                             public void onSuccess(MyInfoBean bean) {
                                 userName.setText(bean.getAccount().getRealName());
                                 userGood.setText(bean.getLikeCount() + "");
                                 userFen.setText(bean.getFollowee() + "");
                                 userLook.setText(bean.getFollowers() + "");
                                 GlideUtil.intoImageView(MyInformationActivity.this,Uri.parse(BuildConfig.OSS_SERVER +
                                         bean.getAccount().getAvatar()),ivCompanyLogo);
                                 //mAdapter.getData().clear();
                                 //mAdapter.setNewData(bean.getList());
                                 // mSwipeRefreshLayout.setRefreshing(false);
                                 //mAdapter.loadMoreComplete()
//                            if (bean.getList().size() < 10) {
//                                mAdapter.loadMoreEnd();
//                            }
//
//                            if (bean.getList().size() > 0) {
//                                mTvNoData.setVisibility(View.GONE);
//                            } else {
//                                mTvNoData.setVisibility(View.VISIBLE);
//                            }


                             }

                             @Override
                             public void onError(String message) {
                                 super.onError(message);
                                 Log.i("ZYr", message);
                             }

                             @Override
                             public void onNoData(String message) {
//                        mSwipeRefreshLayout.setRefreshing(false);
//                        mAdapter.loadMoreEnd();//没有数据了
//                        if (mAdapter.getData().size() == 0) {
//                            mTvNoData.setVisibility(View.VISIBLE);
//                        } else {
//                            mTvNoData.setVisibility(View.GONE);
//                        }

                             }

                             @Override
                             public void onCommitAgain() {
                                 //mSwipeRefreshLayout.setRefreshing(false);
                             }
                         }

                );
    }

    private void initView() {
        setLeftBack();
        for (String title : mTitles) {
            mFragments.add(OfferAndPayListFragment1.getInstance(title, type));
        }

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpTaskList.setAdapter(mPagerAdapter);
        tlTaskZyr.setViewPager(vpTaskList, mTitles, this, mFragments);

        vpTaskList.setCurrentItem(0);
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
