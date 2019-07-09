package com.eanfang.ui.activity;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.ui.adapter.InviteDetailPagerAdapter;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.fragment.InviteDetailFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liangkailun
 * Date ：2019/5/13
 * Describe :邀请明细
 */
public class InviteDetailActivity extends BaseActivity {
    public static final String EXTRA_STATUS = "extraStatus";
    @BindView(R2.id.tab_title)
    SlidingTabLayout mTabTitle;
    @BindView(R2.id.vp_my_invite_detail)
    ViewPager mVpInviteDetail;
    private String[] mTitles = new String[]{"已获得", "已提现", "已失效"};
    private InviteDetailPagerAdapter mInvitePagerAdapter;
    private int mStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_detail);
        ButterKnife.bind(this);
        mStatus = getIntent().getIntExtra(EXTRA_STATUS, 0);
        intView();
    }

    private void intView() {
        setLeftBack();
        setTitle("奖励明细");
        ArrayList<InviteDetailFragment> inviteDetailFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            inviteDetailFragments.add(InviteDetailFragment.getInstance(i));
        }
        //只加载一个fragment
        mVpInviteDetail.setOffscreenPageLimit(1);
        mInvitePagerAdapter = new InviteDetailPagerAdapter(getSupportFragmentManager(), inviteDetailFragments);
        mVpInviteDetail.setAdapter(mInvitePagerAdapter);
        mTabTitle.setViewPager(mVpInviteDetail, mTitles);
        mVpInviteDetail.setCurrentItem(mStatus);
    }


}
