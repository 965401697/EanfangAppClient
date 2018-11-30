package net.eanfang.client.ui.activity.worksapce.oa.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.WorkTaskListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskAssignmentListActivity extends BaseClientActivity {


    @BindView(R.id.tl_task_list)
    SlidingTabLayout tlTaskList;
    @BindView(R.id.vp_task_list)
    ViewPager vpTaskList;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"我发出的", "我负责的", "全部"};
    private MyPagerAdapter mAdapter;
    private final int FILTRATE_TYPE_CODE = 101;
    private final int REFRESH_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assignment_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("布置任务");
        setLeftBack();
        setRightImageResId(R.mipmap.add);
        setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermKit.get().getWorkTaskCreatePrem()) return;
                startActivityForResult(new Intent(TaskAssignmentListActivity.this, TaskAssignmentCreationActivity.class), REFRESH_CODE);
            }
        });

        mFragments.add(WorkTaskListFragment.getInstance("我发出的", "1"));
        mFragments.add(WorkTaskListFragment.getInstance("我负责的", "2"));
        mFragments.add(WorkTaskListFragment.getInstance("全部", "0"));

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpTaskList.setAdapter(mAdapter);
        tlTaskList.setViewPager(vpTaskList, mTitles, this, mFragments);

        vpTaskList.setCurrentItem(0);
    }

    @OnClick(R.id.tv_filtrate)
    public void onViewClicked() {
        startActivityForResult(new Intent(this, FiltrateTaskActivity.class), FILTRATE_TYPE_CODE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int currentTab = tlTaskList.getCurrentTab();
        if (resultCode == RESULT_OK && requestCode == FILTRATE_TYPE_CODE) {


            QueryEntry queryEntry = (QueryEntry) data.getSerializableExtra("query");
            if (queryEntry != null) {
                ((WorkTaskListFragment) mFragments.get(currentTab)).getTaskData(queryEntry);
            }
        } else if (resultCode == RESULT_OK && requestCode == WorkTaskListFragment.DETAIL_TASK_REQUSET_COOD) {
            if (currentTab == 1)
                ((WorkTaskListFragment) mFragments.get(currentTab)).refreshStatus();
        }
    }
}

