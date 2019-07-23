package net.eanfang.client.ui.activity.worksapce.repair;

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
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.config.Config;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.online.DividerItemDecoration;
import net.eanfang.client.ui.adapter.repair.RepairDeviceTypeLeftAdapter;
import net.eanfang.client.ui.adapter.repair.RepairDeviceTypeRightAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/5/4  15:55
 * @decision 选择设备类别
 * 已提取设备类别相关内容
 */
public class SelectDeviceTypeActivity extends BaseActivity {

    private static final int RESULT_DATACODE = 200;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.lv_deviceTypeLeft)
    ListView lvDeviceTypeLeft;
    @BindView(R.id.rv_deviceTypeRight)
    RecyclerView rvDeviceTypeRight;
    @BindView(R.id.tv_go)
    TextView tvGo;

    private int i = 0;
    private List<BaseDataEntity> mBaseOneDataList = new ArrayList<>();
    private List<BaseDataEntity> mBaseThreeDataList = new ArrayList<>();
    private List<BaseDataEntity> leftDataList = new ArrayList<>();
    private List<BaseDataEntity> rightDataList = new ArrayList<>();

    private List<BaseDataEntity> searchRightDataList = new ArrayList<>();

    private RepairDeviceTypeLeftAdapter deviceTypeLeftAdapter;
    private RepairDeviceTypeRightAdapter deviceTypeRightAdapter;

    private String mLeftId;
    /**
     * 搜索的adapter的点击事件 从集合取数据
     */
    private boolean mFlag = false;

    private GridLayoutManager gridLayoutManager;

    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_device_type);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();

    }


    /**
     * @date on 2018/5/4  15:57
     * @decision 初始化数据
     */
    private void initView() {
        setLeftBack();
        mType = getIntent().getIntExtra("type", 1);
        if (mType == 0) {
            setTitle("选择设备类型");
            lvDeviceTypeLeft.setVisibility(View.GONE);
        } else {
            tvGo.setVisibility(View.GONE);
            setTitle("设备类别");
        }
        gridLayoutManager = new GridLayoutManager(this, 3);
        rvDeviceTypeRight.setLayoutManager(gridLayoutManager);
        rvDeviceTypeRight.addItemDecoration(new DividerItemDecoration(this));
        deviceTypeRightAdapter = new RepairDeviceTypeRightAdapter();
        deviceTypeRightAdapter.bindToRecyclerView(rvDeviceTypeRight);

        mBaseOneDataList = Config.get().getBusinessList(1);
        mBaseThreeDataList = Config.get().getBusinessList(3);

        initData();
        initListener();
    }

    public List<BaseDataEntity> doSelectRightList(String code) {
        List mList = Stream.of(mBaseThreeDataList).filter(bean -> bean.getDataCode().contains(code)).toList();
        return mList;
    }

    private void initData() {
        // 左边类型List
        leftDataList = mBaseOneDataList;
        // 右边List
        rightDataList = doSelectRightList(mBaseOneDataList.get(0).getDataCode());
        deviceTypeLeftAdapter = new RepairDeviceTypeLeftAdapter(SelectDeviceTypeActivity.this, leftDataList);

        lvDeviceTypeLeft.setAdapter(deviceTypeLeftAdapter);
        deviceTypeLeftAdapter.setSelectedPosition(0);
        deviceTypeLeftAdapter.notifyDataSetChanged();
        deviceTypeRightAdapter.setNewData(rightDataList);

    }

    private void initListener() {
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
                    deviceTypeRightAdapter.setNewData(searchRightDataList);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rvDeviceTypeRight.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mType == 0) {
                    i++;
                    if (i % 2 == 1) {
                        deviceTypeRightAdapter.getViewByPosition(rvDeviceTypeRight, position, R.id.check_true_t).setVisibility(View.VISIBLE);
                    } else {
                        deviceTypeRightAdapter.getViewByPosition(rvDeviceTypeRight, position, R.id.check_true_t).setVisibility(View.GONE);
                    }
                } else {
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
            }
        });

        lvDeviceTypeLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mFlag = false;
                deviceTypeLeftAdapter.setSelectedPosition(i);
                deviceTypeLeftAdapter.notifyDataSetChanged();
                mLeftId = leftDataList.get(i).getDataCode();
                rightDataList = doSelectRightList(mLeftId);
                //切换置空
                rvDeviceTypeRight.scrollToPosition(0);
                etSearch.setText("");
            }
        });

        tvGo.setOnClickListener((v) -> {
            Intent intent = new Intent(SelectDeviceTypeActivity.this, FaultLibraryActivity.class);
            intent.putExtra("GZK", 3);
            startActivity(intent);
        });
    }

}
