package com.eanfang.sdk.equipment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.config.Config;
import com.eanfang.sdk.equipment.adapter.RepairDeviceTypeLeftAdapter;
import com.eanfang.sdk.equipment.adapter.RepairDeviceTypeRightAdapter;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/5/4  15:55
 * @decision 选择设备类别
 */
public class SelectDeviceTypeActivity extends BaseActivity implements
        SwipyRefreshLayout.OnRefreshListener {

    private static final int RESULT_DATACODE = 200;
    @BindView(R2.id.et_search)
    EditText etSearch;
    @BindView(R2.id.lv_deviceTypeLeft)
    ListView lvDeviceTypeLeft;
    @BindView(R2.id.rv_deviceTypeRight)
    RecyclerView rvDeviceTypeRight;
    @BindView(R2.id.tv_noInfo)
    TextView tvNoInfo;
    @BindView(R2.id.tv_go)
    TextView tvGo;
    private int i = 0;
    private List<BaseDataEntity> leftDataList = new ArrayList<>();
    private List<BaseDataEntity> rightDataList = new ArrayList<>();

    private List<BaseDataEntity> searchRightDataList = new ArrayList<>();

    private RepairDeviceTypeLeftAdapter deviceTypeLeftAdapter;
    private RepairDeviceTypeRightAdapter deviceTypeRightAdapter;

    private Integer mLeftId = 0;
    private boolean mFlag = false;//搜索的adapter的点击事件 从集合取数据
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_device_type);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
            tvGo.setVisibility(View.GONE);
            initView();
            initData();
            initListener();

    }


    /**
     * @date on 2018/5/4  15:57
     * @decision 初始化数据
     */
    private void initView() {
        setLeftBack();
        setTitle("设备类别");

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (rightDataList != null) {
                    mFlag = true;
                    searchRightDataList.clear();
                    for (BaseDataEntity data : rightDataList) {
                        if (data.getDataName().contains(s.toString())) {
                            searchRightDataList.add(data);
                        }
                    }
                    deviceTypeRightAdapter = new RepairDeviceTypeRightAdapter(R.layout.layout_repair_device_right, searchRightDataList);
                    rvDeviceTypeRight.setAdapter(deviceTypeRightAdapter);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initData() {

        gridLayoutManager = new GridLayoutManager(this, 3);
        rvDeviceTypeRight.setLayoutManager(gridLayoutManager);
        rvDeviceTypeRight.setNestedScrollingEnabled(false);


        // 左边类型List
        leftDataList = Config.get().getBusinessList(1);
        // 右边List
        rightDataList = doSelectRightList(leftDataList.get(0).getDataId());
        deviceTypeLeftAdapter = new RepairDeviceTypeLeftAdapter(SelectDeviceTypeActivity.this, leftDataList);
        lvDeviceTypeLeft.setAdapter(deviceTypeLeftAdapter);
        deviceTypeLeftAdapter.setSelectedPosition(0);
        deviceTypeLeftAdapter.notifyDataSetChanged();
        getData();

    }

    private void initListener() {
        rvDeviceTypeRight.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                if (!mFlag) {
                    intent.putExtra("dataCode", rightDataList.get(position).getDataCode());
                    String businessOneCode = Config.get().getBaseCodeByLevel(rightDataList.get(position).getDataCode(), 1);
                    intent.putExtra("businessOneCode", businessOneCode);
                } else {
                    intent.putExtra("dataCode", searchRightDataList.get(position).getDataCode());
                    String businessOneCode = Config.get().getBaseCodeByLevel(searchRightDataList.get(position).getDataCode(), 1);
                    intent.putExtra("businessOneCode", businessOneCode);
                }
                setResult(RESULT_DATACODE, intent);
                finishSelf();
            }
        });

        lvDeviceTypeLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mFlag = false;
                deviceTypeLeftAdapter.setSelectedPosition(i);
                deviceTypeLeftAdapter.notifyDataSetChanged();
                mLeftId = leftDataList.get(i).getDataId();
                rightDataList = doSelectRightList(mLeftId);
                //切换置空
                etSearch.setText("");
                getData();
            }
        });
    }

    @Override
    public void onRefresh(int index) {
        doSelectRightList(mLeftId);
    }

    @Override
    public void onLoad(int index) {

    }

    public void getData() {

        deviceTypeRightAdapter = new RepairDeviceTypeRightAdapter(R.layout.layout_repair_device_right, rightDataList);
        rvDeviceTypeRight.setAdapter(deviceTypeRightAdapter);
    }

    public List<BaseDataEntity> doSelectRightList(Integer id) {
        List<BaseDataEntity> middleList = Stream.of(Config.get().getBusinessList(2)).filter(bean -> bean.getParentId().intValue() == id).toList();
        List rightList = Stream.of(Config.get().getBusinessList(3))
                .filter(bean -> Stream.of(middleList).map(middle -> middle.getDataId()).toList().contains(bean.getParentId().intValue())).toList();
        return rightList;
    }

}
