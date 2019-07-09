package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DefendLogParentActivity extends BaseClientActivity {
    @BindView(R.id.tl_task_list)
    SlidingTabLayout tlTaskList;
    @BindView(R.id.tv_filtrate)
    TextView tvFiltrate;
    @BindView(R.id.vp_task_list)
    ViewPager vpTaskList;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"我创建的", "我接收的", "全部"};
    private MyPagerAdapter mAdapter;
    private final int FILTRATE_TYPE_CODE = 101;
    private final int REFRESH_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_defend_log_parent);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setTitle("布防日志");
        setLeftBack();

        setRightImageResId(R.mipmap.add);

        setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermKit.get().getProtectionCreatePrem()) {
                    return;
                }
                startActivityForResult(new Intent(DefendLogParentActivity.this, DefendLogWriteActivity.class), REFRESH_CODE);
            }
        });
        mFragments.add(DefendLogFragment.getInstance("我创建的", 1));
        mFragments.add(DefendLogFragment.getInstance("我接收的", 2));
        mFragments.add(DefendLogFragment.getInstance("全部", 0));

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpTaskList.setAdapter(mAdapter);
        tlTaskList.setViewPager(vpTaskList, mTitles, this, mFragments);

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

    @OnClick(R.id.tv_filtrate)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        if (vpTaskList.getCurrentItem() == 0) {// 我创建的
            bundle.putInt("type", 0);
        } else if (vpTaskList.getCurrentItem() == 1) {// 我处理的
            bundle.putInt("type", 1);
        }
        JumpItent.jump(DefendLogParentActivity.this, FilterDefendLogActivity.class, bundle, FILTRATE_TYPE_CODE);
    }

    /**
     * 筛选回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int currentTab = tlTaskList.getCurrentTab();
        if (resultCode == RESULT_OK && requestCode == FILTRATE_TYPE_CODE) {

            QueryEntry queryEntry = (QueryEntry) data.getSerializableExtra("query");
            if (queryEntry != null) {
                ((DefendLogFragment) mFragments.get(currentTab)).getTaskData(queryEntry);
            }
        } else if (resultCode == RESULT_OK && requestCode == DefendLogFragment.DETAIL_TASK_REQUSET_COOD) {
            ((DefendLogFragment) mFragments.get(currentTab)).refreshStatus();

        }
    }
}
