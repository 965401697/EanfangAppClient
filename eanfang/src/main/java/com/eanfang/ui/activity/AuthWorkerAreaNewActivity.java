package com.eanfang.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.annimon.stream.Stream;
import com.baozi.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.config.Config;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.items.AreaItem;
import com.eanfang.ui.items.OrgSelectGroupMultipleItem;
import com.eanfang.ui.items.OrgSelectGroupSingleItem;
import com.yaf.sys.entity.BaseDataEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthWorkerAreaNewActivity extends BaseActivity {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    List<BaseDataEntity> areaListBean = Config.get().getRegionList(1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_worker_area);
        ButterKnife.bind(this);



        //获得全部 地区数据
        List<BaseDataEntity> allAreaList = new ArrayList<>(Config.get().getRegionList());
        for (int i = 0; i < areaListBean.size(); i++) {
            BaseDataEntity provinceEntity = areaListBean.get(i);
            //处理当前省下的所有市
            List<BaseDataEntity> cityList = Stream.of(allAreaList).filter(bean -> bean.getParentId() != null && bean.getParentId().intValue() == provinceEntity.getDataId()).toList();
            //查询出来后，移除，以增加效率
            allAreaList.removeAll(cityList);
            for (int j = 0; j < cityList.size(); j++) {
                BaseDataEntity cityEntity = cityList.get(j);
                //处理当前市下所有区县
                List<BaseDataEntity> countyList = Stream.of(allAreaList).filter(bean -> bean.getParentId() != null && bean.getParentId().intValue() == cityEntity.getDataId()).toList();
                //查询出来后，移除，以增加效率
                allAreaList.removeAll(countyList);
                cityList.get(j).setChildren(countyList);
            }
            areaListBean.get(i).setChildren(cityList);
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        TreeRecyclerAdapter mTreeRecyclerAdapter = new TreeRecyclerAdapter();

        mTreeRecyclerAdapter.setDatas(ItemHelperFactory.createTreeItemList(areaListBean, AreaItem.class, null));

        recyclerView.setAdapter(mTreeRecyclerAdapter);
    }
}
