package net.eanfang.worker.ui.activity.worksapce.tender;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.config.Config;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/1/9
 * @description 筛选招标信息
 */

public class FilterTenderActivity extends BaseActivity implements SelectTimeDialogFragment.SelectTimeListener {

    @BindView(R.id.tag_system_type)
    TagFlowLayout tagSystemType;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    private TextView mCurrentText;

    /**
     * 获取系统类别
     */
    List<BaseDataEntity> systemTypeList = Config.get().getBusinessList(1);
    private String mCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_tender);
        ButterKnife.bind(this);
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

        tagSystemType.setAdapter(new TagAdapter<BaseDataEntity>(systemTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, BaseDataEntity mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(FilterTenderActivity.this).inflate(R.layout.layout_trouble_result_item, tagSystemType, false);
                tv.setText(mrepairResult.getDataName());
                return tv;
            }
        });

        tagSystemType.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mCode = systemTypeList.get(position).getDataCode();
//                systemTypeList.get(position).setCheck(!systemTypeList.get(position).isCheck());
                return true;
            }
        });

    }

    @OnClick({R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                sub();
                break;
            default:
                break;
        }
    }

    private void sub() {

        QueryEntry queryEntry = new QueryEntry();
        if (!StringUtils.isEmpty(mCode)) {
            queryEntry.getEquals().put("businessOneCode", mCode + "");
        }

        Intent intent = new Intent();
        if (queryEntry != null) {
            intent.putExtra("query", queryEntry);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 时间选择回调
     */
    @Override
    public void getData(String time) {
        if (StringUtils.isEmpty(time) || " ".equals(time)) {
            mCurrentText.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            mCurrentText.setText(time);
        }
    }
}
