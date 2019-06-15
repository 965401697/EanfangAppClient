package net.eanfang.worker.ui.activity.worksapce.tender;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.annimon.stream.Stream;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.config.Config;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityFilterTenderBinding;
import net.eanfang.worker.ui.activity.SelectAreaActivity;

import java.util.List;

import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/1/9
 * @description 筛选招标信息
 */

public class FilterTenderActivity extends BaseActivity {

    /**
     * 获取系统类别
     */
    List<BaseDataEntity> systemTypeList = Config.get().getBusinessList(1);
    private String mCode = "";

    private ActivityFilterTenderBinding mFilterTenderBinding;

    private final static int REQUEST_SELECT_ADDRESS = 1001;
    private List<String> projectArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFilterTenderBinding = DataBindingUtil.setContentView(this, R.layout.activity_filter_tender);
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected void initView() {
        setTitle("筛选");
        setLeftBack(true);
        for (BaseDataEntity s : systemTypeList) {
            s.setCheck(false);
        }

        mFilterTenderBinding.tagSystemType.setAdapter(new TagAdapter<BaseDataEntity>(systemTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, BaseDataEntity mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(FilterTenderActivity.this).inflate(R.layout.layout_trouble_result_item, mFilterTenderBinding.tagSystemType, false);
                tv.setText(mrepairResult.getDataName());
                return tv;
            }
        });

        mFilterTenderBinding.tagSystemType.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                systemTypeList.get(position).setCheck(!systemTypeList.get(position).isCheck());
                return true;
            }
        });

        mFilterTenderBinding.rlSelectaddress.setOnClickListener((v) -> {
            JumpItent.jump(this, SelectAreaActivity.class, REQUEST_SELECT_ADDRESS);
        });
        mFilterTenderBinding.tvSure.setOnClickListener((v) -> {
            sub();
        });

    }

    private void sub() {
        QueryEntry queryEntry = new QueryEntry();
        List<String> checkList = Stream.of(systemTypeList).filter(beans -> beans.isCheck()).map(BaseDataEntity::getDataCode).toList();
        if (checkList != null && checkList.size() > 0) {
            queryEntry.getIsIn().put("businessOneCode", checkList);
        }
        if (projectArea != null && projectArea.size() > 0) {
            queryEntry.getIsIn().put("projectArea", projectArea);
        }

        Intent intent = new Intent();
        if (queryEntry != null) {
            intent.putExtra("query", queryEntry);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_ADDRESS) {
            projectArea = data.getStringArrayListExtra("projectArea");
            mFilterTenderBinding.tvAddressSelect.setText("共选择了" + projectArea.size() + "地区");

        }
    }

}
