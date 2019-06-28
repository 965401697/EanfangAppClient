package net.eanfang.worker.ui.activity.worksapce.tender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityTenderPersonControlBinding;
import net.eanfang.worker.ui.fragment.tender.TenderMyBidFragment;
import net.eanfang.worker.ui.fragment.tender.TenderMyReleaseFragment;
import net.eanfang.worker.viewmodle.tender.TenderPersonControlViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/6/22
 * @description 招标个人中心
 */

public class TenderPersonControlActivity extends BaseActivity {

    private final int FILTRATE_TYPE_CODE = 10010;
    private String[] mTitles = {"我发布的", "我投标的"};
    @Setter
    @Accessors(chain = true)
    private TenderPersonControlViewModle tenderPersonControlViewModle;
    private ActivityTenderPersonControlBinding tenderPersonControlBinding;

    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tenderPersonControlBinding = DataBindingUtil.setContentView(this, R.layout.activity_tender_person_control);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setTitle("我的用工大厅");
        setLeftBack(true);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mTitles);
        mAdapter.getFragments().add(TenderMyReleaseFragment.newInstance(tenderPersonControlViewModle));
        mAdapter.getFragments().add(TenderMyBidFragment.newInstance(tenderPersonControlViewModle));
        tenderPersonControlBinding.vpTenderList.setCurrentItem(0);
        tenderPersonControlBinding.tlPerosonalList.setViewPager(tenderPersonControlBinding.vpTenderList, mTitles, this, mAdapter.getFragments());
        tenderPersonControlBinding.tvFiltrate.setOnClickListener((V) -> {
            Bundle bundle = new Bundle();
            if (tenderPersonControlBinding.vpTenderList.getCurrentItem() == 0) {
                bundle.putInt("type", 2);
            } else if (tenderPersonControlBinding.vpTenderList.getCurrentItem() == 1) {
                bundle.putInt("type", 3);
            }
            bundle.putBoolean("isPersonal", true);
            JumpItent.jump(TenderPersonControlActivity.this, FilterTenderActivity.class, bundle, FILTRATE_TYPE_CODE);
        });

    }

    @Override
    protected ViewModel initViewModel() {
        tenderPersonControlViewModle = LViewModelProviders.of(this, TenderPersonControlViewModle.class);
        tenderPersonControlBinding.setTenderPersonalControVm(tenderPersonControlViewModle);
        tenderPersonControlViewModle.setTenderPersonControlBinding(tenderPersonControlBinding);
        return tenderPersonControlViewModle;
    }

    /**
     * 筛选回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int currentTab = tenderPersonControlBinding.vpTenderList.getCurrentItem();
        if (resultCode == RESULT_OK && requestCode == FILTRATE_TYPE_CODE) {
            QueryEntry queryEntry = (QueryEntry) data.getSerializableExtra("query");
            if (queryEntry != null) {
                if (currentTab == 0) {
                    ((TenderMyReleaseFragment) mAdapter.getFragments().get(currentTab)).getTenderData(queryEntry);
                } else {
                    ((TenderMyBidFragment) mAdapter.getFragments().get(currentTab)).getTenderData(queryEntry);
                }
            }
        }
    }
}
