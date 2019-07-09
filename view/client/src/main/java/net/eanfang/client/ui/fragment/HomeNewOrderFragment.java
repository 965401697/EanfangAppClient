package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.biz.model.bean.HomeNewOrderBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.FragmentHomeNewOrderAdapter;

import butterknife.BindView;

/**
 * @author liangkailun
 * Date ：2019-06-18
 * Describe :首页最新订单
 */
public class HomeNewOrderFragment extends BaseFragment {
    @BindView(R.id.rec_home_new_order)
    RecyclerView mRecHomeNewOrder;
    private FragmentHomeNewOrderAdapter mFragmentHomeNewOrderAdapter;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home_new_order;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void onLazyLoad() {
        EanfangHttp.get(NewApiService.GET_PUSH_NEWS).execute(new EanfangCallback<HomeNewOrderBean>(getActivity(), false, HomeNewOrderBean.class, true, (list -> {
            Log.d("HomeNewOrderFragment", "initView: " + list);
            mFragmentHomeNewOrderAdapter.getData().clear();
            mFragmentHomeNewOrderAdapter.setNewData(list);
            mFragmentHomeNewOrderAdapter.loadMoreComplete();
        })));
    }

    @Override
    protected void initView() {
        mRecHomeNewOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecHomeNewOrder.setNestedScrollingEnabled(false);
                mFragmentHomeNewOrderAdapter = new FragmentHomeNewOrderAdapter();
        mFragmentHomeNewOrderAdapter.bindToRecyclerView(mRecHomeNewOrder);
    }

    @Override
    protected void setListener() {

    }
}
