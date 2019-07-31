package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.config.Config;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.biz.model.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.NewOrderScreenAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liangkailun
 * Date ：2019-05-29
 * Describe :筛选页面
 */
public class NewOrderScreenActivity extends BaseActivity {
    @BindView(R.id.tv_choose_area)
    TextView mTvChooseArea;
    @BindView(R.id.rec_system_type)
    RecyclerView mRecSystemType;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    private NewOrderScreenAdapter mNewOrderScreenAdapter;
    ArrayList<String> placeCode;
    /**
     * 获取系统类别
     */
    List<BaseDataEntity> systemTypeList = Config.get().getBusinessList(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_order_screen);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        intView();
    }

    private void intView() {
        QueryEntry queryEntry = new QueryEntry();
        Map<String, List<String>> mList = new HashMap<>(2);
        placeCode = new ArrayList<>();
        List<String> bussinCode = new ArrayList<>();
        setLeftBack();
        setTitle("最新订单筛选");
        mTvChooseArea.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChooseAreaActivity.class);
            intent.putExtra("chooseCode", placeCode);
            startActivityForResult(intent, 0);
        });
        mBtnOk.setOnClickListener(v -> {
            mList.put("placeCode", placeCode);
            mList.put("bussinessOneCode", bussinCode);
            queryEntry.setIsIn(mList);
            Map<String, List<String>> likeMap = new HashMap<String, List<String>>();
            startActivity(new Intent(this, NewOrderActivity.class).putExtra("query", JSON.toJSONString(queryEntry)));
        });
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecSystemType.setLayoutManager(manager);
        mNewOrderScreenAdapter = new NewOrderScreenAdapter();
        mNewOrderScreenAdapter.bindToRecyclerView(mRecSystemType);
        mNewOrderScreenAdapter.setNewData(systemTypeList);
        mNewOrderScreenAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            view.setSelected(!view.isSelected());
            String code = ((BaseDataEntity) adapter.getItem(position)).getDataCode();
            if (view.isSelected()) {
                bussinCode.add(code);
            } else {
                bussinCode.remove(code);
            }
        });
        mTvChooseArea.setText(getString(R.string.text_new_order_screen_choose_area, placeCode.size()));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            placeCode = data.getStringArrayListExtra("areaCodeList");
            int chooseSize = data.getIntExtra("cityChooseSize", 0);
            mTvChooseArea.setText(getString(R.string.text_new_order_screen_choose_area, chooseSize));
        }
    }
}
