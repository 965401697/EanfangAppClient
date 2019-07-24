package net.eanfang.client.ui.fragment;

import android.content.Intent;
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
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.online.DividerItemDecoration;
import net.eanfang.client.ui.activity.worksapce.repair.QuickRepairActivity;
import net.eanfang.client.ui.adapter.HomeAllBrandAdapter;

import java.util.ArrayList;

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
        if (adapter.getData().size() <= 0) {
            QueryEntry queryEntry = new QueryEntry();
            EanfangHttp.post(NewApiService.HOME_ALL_BRAND).upJson(JSON.toJSONString(queryEntry)).execute(new EanfangCallback<AllBrandBean>(getActivity(), true, AllBrandBean.class, bean -> {
                if (bean == null || bean.getList() == null || bean.getList().size() == 0) {
                    return;
                }
                adapter.getData().clear();
                ArrayList<AllBrandBean.ListBean> allBrands = new ArrayList<>();
                for (AllBrandBean.ListBean listBean : bean.getList()) {
                    if (!StringUtils.isEmpty(listBean.getRemarkInfo())) {
                        allBrands.add(listBean);
                        if (allBrands.size() == 8) {
                            break;
                        }
                    }
                }
                adapter.setNewData(allBrands);
                adapter.loadMoreComplete();
            }));
        }
    }

    @Override
    protected void initView() {
        mRecHomeAllBrand.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecHomeAllBrand.addItemDecoration(new DividerItemDecoration(getContext()));
        adapter = new HomeAllBrandAdapter();
        adapter.bindToRecyclerView(mRecHomeAllBrand);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            AllBrandBean.ListBean bean = (AllBrandBean.ListBean) adapter.getData().get(position);
            Intent intent = new Intent(getActivity(), QuickRepairActivity.class);
            intent.putExtra("deviceBrandName", bean.getDataName());
            intent.putExtra("dataCode", bean.getDataCode());
            startActivity(intent);
        });
    }

    @Override
    protected void setListener() {

    }
}
