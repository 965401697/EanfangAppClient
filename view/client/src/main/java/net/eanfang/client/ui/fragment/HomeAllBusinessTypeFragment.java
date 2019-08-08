package net.eanfang.client.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.biz.model.bean.BusinessTypeBean;
import com.eanfang.ui.base.BaseFragment;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.online.DividerItemDecoration;
import net.eanfang.client.ui.adapter.HomeAllBusinessTypeAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author liangkailun
 * Date ：2019-06-20
 * Describe :
 */
public class HomeAllBusinessTypeFragment extends BaseFragment {
    @BindView(R.id.rec_home_all_bussiness)
    RecyclerView mRecHomeAllBusiness;
    private String[] mNames = new String[]{
            "电视监控", "防盗报警", "公共报警", "停车场", "门禁一卡通", "可视对讲", "EAS", "公共广播"
    };
    private int[] mImgRes = new int[]{
            R.drawable.ic_business_type1, R.drawable.ic_business_type2, R.drawable.ic_business_type3, R.drawable.ic_business_type4,
            R.drawable.ic_business_type5, R.drawable.ic_business_type6, R.drawable.ic_business_type7, R.drawable.ic_business_type8
    };

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home_all_bussiness;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        ArrayList<BusinessTypeBean> businessTypeBeanArrayList = new ArrayList<>();
        for (int i = 0; i < mNames.length; i++) {
            BusinessTypeBean bean = new BusinessTypeBean();
            bean.setTypeName(mNames[i]);
            bean.setImgRes(mImgRes[i]);
            businessTypeBeanArrayList.add(bean);
        }
        mRecHomeAllBusiness.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecHomeAllBusiness.addItemDecoration(new DividerItemDecoration(getContext()));
        HomeAllBusinessTypeAdapter adapter = new HomeAllBusinessTypeAdapter();
        adapter.bindToRecyclerView(mRecHomeAllBusiness);
        adapter.setNewData(businessTypeBeanArrayList);
    }

    @Override
    protected void setListener() {

    }
}
