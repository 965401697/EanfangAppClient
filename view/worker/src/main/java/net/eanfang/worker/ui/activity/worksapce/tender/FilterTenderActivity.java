package net.eanfang.worker.ui.activity.worksapce.tender;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
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
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.util.ToastUtil;
import com.picker.DoubleDatePickerDialog;
import com.picker.common.util.DateUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityFilterTenderBinding;
import net.eanfang.worker.ui.activity.SelectAreaActivity;
import net.eanfang.worker.ui.activity.my.certification.AddSkillCertificafeActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    /**
     * 业务类型
     */
    List<BaseDataEntity> businessTypeList = Config.get().getServiceList(1);

    private ActivityFilterTenderBinding mFilterTenderBinding;

    private final static int REQUEST_SELECT_ADDRESS = 1001;
    private List<String> projectArea;

    /**
     * 招标公告 不显示订单类型
     */
    private int mType = 100;

    /**
     * 我发布的 我投标的
     */
    private boolean isPerson = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFilterTenderBinding = DataBindingUtil.setContentView(this, R.layout.activity_filter_tender);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected void initView() {
        setTitle("筛选");
        setLeftBack(true);
        mType = getIntent().getIntExtra("type", 100);
        isPerson = getIntent().getBooleanExtra("isPersonal", false);
        for (BaseDataEntity s : systemTypeList) {
            s.setCheck(false);
        }
        for (BaseDataEntity b : businessTypeList) {
            b.setCheck(false);
        }

        /**
         * 0 招标公告
         * 1 用工找活
         * */
        if (mType == 0) {
            mFilterTenderBinding.llOrderType.setVisibility(View.GONE);
        } else {
            mFilterTenderBinding.llOrderType.setVisibility(View.VISIBLE);
        }
        if (isPerson) {
            mFilterTenderBinding.rlSelectTime.setVisibility(View.VISIBLE);
            mFilterTenderBinding.rlSelectaddress.setVisibility(View.GONE);
        } else {
            mFilterTenderBinding.rlSelectTime.setVisibility(View.GONE);
            mFilterTenderBinding.rlSelectaddress.setVisibility(View.VISIBLE);
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
        mFilterTenderBinding.tagOrderType.setAdapter(new TagAdapter<BaseDataEntity>(businessTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, BaseDataEntity mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(FilterTenderActivity.this).inflate(R.layout.layout_trouble_result_item, mFilterTenderBinding.tagOrderType, false);
                tv.setText(mrepairResult.getDataName());
                return tv;
            }
        });

        mFilterTenderBinding.tagOrderType.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                businessTypeList.get(position).setCheck(!businessTypeList.get(position).isCheck());
                return true;
            }
        });

        mFilterTenderBinding.rlSelectaddress.setOnClickListener((v) -> {
            JumpItent.jump(this, SelectAreaActivity.class, REQUEST_SELECT_ADDRESS);
        });
        mFilterTenderBinding.tvSure.setOnClickListener((v) -> {
            sub();
        });
        mFilterTenderBinding.rlSelectTime.setOnClickListener((v) -> {
            doSelectTime();
        });

    }

    private void sub() {
        QueryEntry queryEntry = new QueryEntry();
        List<String> checkList = Stream.of(systemTypeList).filter(beans -> beans.isCheck()).map(BaseDataEntity::getDataCode).toList();
        List<String> checkOrderList = Stream.of(businessTypeList).filter(beans -> beans.isCheck()).map(BaseDataEntity::getDataCode).toList();

        /**
         * 0 招标公告
         * 1 用工找活
         * 2 我的发布
         * 3 我的投标
         * */
        switch (mType) {
            case 0:
                if (checkList != null && checkList.size() > 0) {
                    queryEntry.getIsIn().put("businessOneCode", checkList);
                }
                if (projectArea != null && projectArea.size() > 0) {
                    queryEntry.getIsIn().put("projectArea", projectArea);
                }
                break;
            case 1:
                if (checkList != null && checkList.size() > 0) {
                    queryEntry.getIsIn().put("systemType", checkList);
                }
                if (checkOrderList != null && checkOrderList.size() > 0) {
                    queryEntry.getIsIn().put("businessOneCode", checkOrderList);
                }
                if (projectArea != null && projectArea.size() > 0) {
                    queryEntry.getIsIn().put("zoneCode", projectArea);
                }
                break;
            case 2:
                if (!StringUtils.isEmpty(mFilterTenderBinding.tvSelectTime.getText().toString())) {
                    queryEntry.getGtEquals().put("createTime", (new SimpleDateFormat("yyyy-MM-dd").format(GetDateUtils.getTimeStamp(mFilterTenderBinding.tvSelectTime.getText().toString().trim().split("～")[0], "yyyy-MM-dd"))));
                    queryEntry.getLtEquals().put("createTime", (new SimpleDateFormat("yyyy-MM-dd").format(GetDateUtils.getTimeStamp(mFilterTenderBinding.tvSelectTime.getText().toString().trim().split("～")[1], "yyyy-MM-dd"))));
                }
                if (checkList != null && checkList.size() > 0) {
                    queryEntry.getIsIn().put("systemType", checkList);
                }
                if (checkOrderList != null && checkOrderList.size() > 0) {
                    queryEntry.getIsIn().put("businessOneCode", checkOrderList);
                }
                break;
            case 3:
                if (!StringUtils.isEmpty(mFilterTenderBinding.tvSelectTime.getText().toString())) {
                    queryEntry.getGtEquals().put("startDate", (new SimpleDateFormat("yyyy-MM-dd").format(GetDateUtils.getTimeStamp(mFilterTenderBinding.tvSelectTime.getText().toString().trim().split("～")[0], "yyyy-MM-dd"))));
                    queryEntry.getLtEquals().put("endDate", (new SimpleDateFormat("yyyy-MM-dd").format(GetDateUtils.getTimeStamp(mFilterTenderBinding.tvSelectTime.getText().toString().trim().split("～")[1], "yyyy-MM-dd"))));
                }
                if (checkList != null && checkList.size() > 0) {
                    queryEntry.getIsIn().put("systemType", checkList);
                }
                if (checkOrderList != null && checkOrderList.size() > 0) {
                    queryEntry.getIsIn().put("businessOneCode", checkOrderList);
                }
                break;
            default:
                break;
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

    @SuppressLint("DefaultLocale")
    private void doSelectTime() {
        Calendar c = Calendar.getInstance();
        // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
        new DoubleDatePickerDialog(FilterTenderActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth) {
                String textString = String.format("%d-%d-%d～%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                String startTime = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                String endTime = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);
                if (GetDateUtils.getTimeStamp(startTime, "yyyy-MM-dd") > GetDateUtils.getTimeStamp(endTime, "yyyy-MM-dd")) {
                    ToastUtil.get().showToast(FilterTenderActivity.this, "开始时间不能大于结束时间");
                    return;
                }
                mFilterTenderBinding.tvSelectTime.setText(textString);
            }

            @Override
            public void cancle() {

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();

    }


}
