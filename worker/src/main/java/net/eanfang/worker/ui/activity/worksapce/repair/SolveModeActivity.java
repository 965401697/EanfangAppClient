package net.eanfang.worker.ui.activity.worksapce.repair;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.widget.FillAppointmentInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/6/1  16:09
 * @decision 解决方式
 */
public class SolveModeActivity extends BaseActivity {

    @BindView(R.id.tv_solvePhone)
    TextView tvSolvePhone;
    @BindView(R.id.tv_solveAppoint)
    TextView tvSolveAppoint;
    private Long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_mode);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setLeftBack();
        setTitle("解决方式");
        orderId = getIntent().getLongExtra("orderId", 0);
    }

    private void initListener() {
    }

    @OnClick({R.id.tv_solvePhone, R.id.tv_solveAppoint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 电话解决   new FillAppointmentInfoView(getActivity(), true, item.getId()).show();
            case R.id.tv_solvePhone:
                doHttp(1, null);
//                JumpItent.jump(mContext, );
                break;
            // 预约上门
            case R.id.tv_solveAppoint:
                new FillAppointmentInfoView(SolveModeActivity.this, true, orderId).show();
                break;
        }
    }

    /**
     * 带点筛选
     */
    private void doHttp(int solve, String bookTime) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("orderId", orderId + "");
        queryEntry.getEquals().put("solve", solve + "");
        queryEntry.getEquals().put("bookTime", bookTime);
        EanfangHttp.post(RepairApi.POST_FLOW_SCREENING)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<JSONObject>(SolveModeActivity.this, true, JSONObject.class, (bean) -> {
                    showToast("预约成功");
                    finishSelf();
                }));

    }
}
