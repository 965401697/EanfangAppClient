package net.eanfang.worker.ui.activity.worksapce.tender;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.widget.TextView;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.fragment.tender.WorkTenderFragment;
import net.eanfang.worker.ui.fragment.worktalk.WorkTalkListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/1/9
 * @description 招标信息
 */

public class WorkerTenderControlActivity extends BaseActivity {

    @BindView(R.id.tl_tender_list)
    SlidingTabLayout tlTenderList;
    @BindView(R.id.tv_filtrate)
    TextView tvFiltrate;
    @BindView(R.id.vp_tender_list)
    ViewPager vpTenderList;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"正在公告", "已过期", "全部"};
    private MyPagerAdapter mAdapter;
    private final int FILTRATE_TYPE_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_tender_control);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("招标信息");
        setLeftBack();

        mFragments.add(WorkTenderFragment.getInstance("正在公告", 0));
        mFragments.add(WorkTenderFragment.getInstance("已过期", 1));
        mFragments.add(WorkTenderFragment.getInstance("全部", 2));

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpTenderList.setAdapter(mAdapter);
        tlTenderList.setViewPager(vpTenderList, mTitles, this, mFragments);

        vpTenderList.setCurrentItem(0);
    }

    @OnClick(R.id.tv_filtrate)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        if (vpTenderList.getCurrentItem() == 0) {// 我创建的
            bundle.putInt("type", 0);
        } else if (vpTenderList.getCurrentItem() == 1) {// 我接收的
            bundle.putInt("type", 1);
        }
        JumpItent.jump(WorkerTenderControlActivity.this, FilterTenderActivity.class, bundle, FILTRATE_TYPE_CODE);
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

    /**
     * 筛选回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int currentTab = tlTenderList.getCurrentTab();
        if (resultCode == RESULT_OK && requestCode == FILTRATE_TYPE_CODE) {

            QueryEntry queryEntry = (QueryEntry) data.getSerializableExtra("query");
            if (queryEntry != null) {
                ((WorkTenderFragment) mFragments.get(currentTab)).getTenderData(queryEntry);
            }
        } else if (resultCode == RESULT_OK && requestCode == WorkTalkListFragment.DETAIL_TASK_REQUSET_COOD) {
//            ((WorkTenderFragment) mFragments.get(currentTab)).refreshStatus();

        }
    }

}
