package net.eanfang.worker.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;

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

        setContentView(R.layout.activity_solve_mode);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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
                doPhoneSolve();
//                JumpItent.jump(mContext, );
                break;
            // 预约上门
            case R.id.tv_solveAppoint:
                Bundle bundle = new Bundle();
                bundle.putLong("orderId", orderId);
                JumpItent.jump(SolveModeActivity.this, RepairAppointTimeActivity.class, bundle, RepairCtrlActivity.REFREST_ITEM);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RepairCtrlActivity.REFREST_ITEM) {
            setResult(RESULT_OK);
            finishSelf();
        }
    }

    private void doPhoneSolve() {
        new TrueFalseDialog(this, "系统提示", "是否确认电话解决？", () -> {
            doHttp(1, null);
        }).showDialog();
    }

    /**
     * 带点筛选
     */
    private void doHttp(int solve, String bookTime) {
//        QueryEntry queryEntry = new QueryEntry();
//        queryEntry.getEquals().put("orderId", orderId + "");
//        queryEntry.getEquals().put("solve", solve + "");
//        queryEntry.getEquals().put("bookTime", bookTime);
        EanfangHttp.post(RepairApi.POST_FLOW_SCREENING)
                .params("orderId", orderId + "")
                .params("solve", solve + "")
                .params("bookTime", bookTime)
                .execute(new EanfangCallback<JSONObject>(SolveModeActivity.this, true, JSONObject.class, (bean) -> {
                    showToast("电话解决成功");
                    setResult(RESULT_OK);
                    finishSelf();
                }));

    }
}
