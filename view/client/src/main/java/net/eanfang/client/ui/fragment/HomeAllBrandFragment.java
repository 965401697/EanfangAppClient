package net.eanfang.client.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.biz.model.bean.AllBrandBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.online.DividerItemDecoration;
import net.eanfang.client.ui.adapter.HomeAllBrandAdapter;

import butterknife.BindView;

/**
 * @author liangkailun
 * Date ：2019-06-20
 * Describe :全部品牌
 */
public class HomeAllBrandFragment extends BaseFragment {

    @BindView(R.id.rec_home_all_brand)
    RecyclerView mRecHomeAllBrand;
    private HomeAllBrandAdapter adapter;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home_all_brand;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void onLazyLoad() {
        QueryEntry queryEntry = new QueryEntry();
        EanfangHttp.post(NewApiService.HOME_ALL_BRAND).upJson(JSON.toJSONString(queryEntry)).execute(new EanfangCallback<AllBrandBean>(getActivity(), true, AllBrandBean.class, bean -> {
            adapter.getData().clear();
            adapter.setNewData(bean.getList());
            adapter.loadMoreComplete();
        }));
    }

    @Override
    protected void initView() {
        mRecHomeAllBrand.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecHomeAllBrand.addItemDecoration(new DividerItemDecoration(getContext()));
        adapter = new HomeAllBrandAdapter();
        adapter.bindToRecyclerView(mRecHomeAllBrand);
    }

    @Override
    protected void setListener() {

    }
}
