package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBrandActivity extends BaseWorkerActivity {

    @BindView(R.id.et_brand)
    EditText etBrand;
    @BindView(R.id.recycler_view_brand)
    RecyclerView recyclerViewBrand;
    private List<BaseDataEntity> mDeviceBrandList = new ArrayList<>();
    private List<BaseDataEntity> mSearchDeviceBrandList = new ArrayList<>();
    private List<BaseDataEntity> mCheckedDeviceBrandList = new ArrayList<>();
    private BrandAdapter mBrandAdapter;

    private List<BaseDataEntity> mSeletedBrandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_brand);
        ButterKnife.bind(this);
        setTitle("添加熟悉品牌");
        setLeftBack();

        mDeviceBrandList = (List<BaseDataEntity>) getIntent().getSerializableExtra("data");


        mSeletedBrandList = (ArrayList<BaseDataEntity>) getIntent().getSerializableExtra("list");

//        for (BaseDataEntity s : mSeletedBrandList) {
//            for (BaseDataEntity d : mDeviceBrandList) {
//                    if(s.getDataCode().equals(d.getDataCode())){
//                        d.setCheck(true);
//                    }else {
//                        d.setCheck(false);
//                    }
//            }
//        }

        initViews();
    }

    private void initViews() {
        recyclerViewBrand.setLayoutManager(new LinearLayoutManager(this));
        mBrandAdapter = new BrandAdapter();
        if (mSeletedBrandList != null) {
            mBrandAdapter.setCheckedData(mSeletedBrandList);
        }
        mBrandAdapter.bindToRecyclerView(recyclerViewBrand);
        mBrandAdapter.setNewData(mDeviceBrandList);

        etBrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mSearchDeviceBrandList = Stream.of(mDeviceBrandList).filter(data -> data.getDataName().contains(s.toString())).toList();
                    mBrandAdapter.setNewData(mSearchDeviceBrandList);
                } else {
                    mBrandAdapter.setNewData(mDeviceBrandList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBrandAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.cb_checked) {
                    BaseDataEntity dataEntity = (BaseDataEntity) adapter.getData().get(position);
                    if (dataEntity.isCheck()) {
                        dataEntity.setCheck(false);
                        adapter.notifyItemChanged(position);
                        mCheckedDeviceBrandList.remove(dataEntity);
                    } else {
                        dataEntity.setCheck(true);
                        adapter.notifyItemChanged(position);
                        mCheckedDeviceBrandList.add(dataEntity);
                    }
                }
            }
        });
    }


    @OnClick(R.id.tv_go)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra("list", (Serializable) mCheckedDeviceBrandList);
        setResult(RESULT_OK, intent);
        finishSelf();
    }
}
