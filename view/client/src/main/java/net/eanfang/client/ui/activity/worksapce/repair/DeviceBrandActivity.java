package net.eanfang.client.ui.activity.worksapce.repair;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.config.Config;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.repair.DeviceBrandAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

/**
 * @author guanluocang
 * @data 2018/10/15
 * @description 选择设备品牌
 * 已提取设备品牌相关内容
 */

public class DeviceBrandActivity extends BaseActivity {

    @BindView(R.id.atv_text)
    EditText atvText;
    @BindView(R.id.rv_devicebrand)
    RecyclerView rvDevicebrand;


    private static final int MSG_SEARCH = 1;
    private static final int RESULT_ADDRESS_CALLBACK_CODE = 1002;

    private List<String> mDeviceBrandList = new ArrayList<>();

    // 设备编号
    private String busOneCode = "";

    private DeviceBrandAdapter deviceBrandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_device_brand);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setTitle("选择设备品牌");
        setLeftBack();
        busOneCode = getIntent().getStringExtra("busOneCode");
        mDeviceBrandList = Stream.of(Config.get().getModelList(2)).filter(bus -> bus.getDataCode().startsWith(busOneCode)).map(bus -> bus.getDataName()).toList();
        rvDevicebrand.setLayoutManager(new GridLayoutManager(this, 2));
        deviceBrandAdapter = new DeviceBrandAdapter(R.layout.layout_item_device_brand);
        deviceBrandAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        deviceBrandAdapter.setNewData(mDeviceBrandList);
        deviceBrandAdapter.bindToRecyclerView(rvDevicebrand);
    }

    private void initData() {

    }

    private void initListener() {

        deviceBrandAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("deviceBrandName", deviceBrandAdapter.getData().get(position));
                setResult(RESULT_ADDRESS_CALLBACK_CODE, intent);
                finishSelf();
            }
        });
        atvText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                //文字变动 ， 有未发出的搜索请求，应取消
                if (mHandler.hasMessages(MSG_SEARCH)) {
                    mHandler.removeMessages(MSG_SEARCH);
                }
                //如果为空 直接显示搜索历史
//                if (TextUtils.isEmpty(keyword)) {
                //showHistory();
//                } else {
                //否则延迟500ms开始搜索
                mHandler.sendEmptyMessageDelayed(MSG_SEARCH, 500); //自动搜索功能 删除
//                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String keyword = atvText.getText().toString().trim();
            if (!StrUtil.isEmpty(keyword)) {

                List<String> mFindList = Stream.of(mDeviceBrandList).filter(title -> title.contains(keyword)).toList();
                deviceBrandAdapter.setNewData(mFindList);
            }

        }
    };


}
