package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.PickerSelectUtil;
import com.yaf.base.entity.LogDetailsEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DefendLogItemWriteAndDetailActivity extends BaseClientActivity implements View.OnClickListener {

    @BindView(R.id.et_defend_code)
    EditText etDefendCode;
    @BindView(R.id.et_defend_position)
    EditText etDefendPosition;
    @BindView(R.id.et_defend_next)
    EditText etDefendNext;
    @BindView(R.id.tv_reason)
    TextView tvDefendReason;
    @BindView(R.id.tv_cause)
    TextView tvCause;
    @BindView(R.id.ev_defend_desc)
    EditText evDefendDesc;
    @BindView(R.id.ll_comit)
    Button llComit;
    @BindView(R.id.ll_cause)
    LinearLayout llCause;

    private int mPosition;
    private LogDetailsEntity dataBean;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_defend_log_item_write);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
//        (0-旁路,1-闯防,2-误报)
        title = getIntent().getStringExtra("title");
        mPosition = getIntent().getIntExtra("position", 0);
        dataBean = (LogDetailsEntity) getIntent().getSerializableExtra("bean");
        setTitle(title + "详情");

        tvCause.setText(title + "报警原因");

        setLeftBack();
        initViews();
    }

    private void initViews() {
        if (dataBean != null) {

            setFlaseFocusable(etDefendCode);
            setFlaseFocusable(etDefendPosition);
            setFlaseFocusable(etDefendNext);
            setFlaseFocusable(evDefendDesc);

            etDefendCode.setText(dataBean.getSlipNum());
            etDefendPosition.setText(dataBean.getPlayLocaltion());
            etDefendNext.setText(dataBean.getAlarmNum() + "");

            if (mPosition == 1) {
                tvDefendReason.setText(GetConstDataUtils.getThroughCause().get(dataBean.getAlarmReason()));
            } else if (mPosition == 2) {
                tvDefendReason.setText(GetConstDataUtils.getFlaseCause().get(dataBean.getAlarmReason()));
            } else {
                tvDefendReason.setText(GetConstDataUtils.getBypassCause().get(dataBean.getAlarmReason()));
            }
            evDefendDesc.setText(dataBean.getNoteInfo());
            llComit.setVisibility(View.GONE);

        } else {
            llComit.setOnClickListener(this);
            llCause.setOnClickListener(this);
        }
    }

    /**
     * 取消焦点
     *
     * @param et
     */
    private void setFlaseFocusable(EditText et) {
        et.setFocusable(false);
        et.setFocusableInTouchMode(false);
        et.setClickable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_cause:
                if (mPosition == 1) {
                    PickerSelectUtil.singleTextPicker(this, "", tvDefendReason, GetConstDataUtils.getThroughCause());
                } else if (mPosition == 2) {
                    PickerSelectUtil.singleTextPicker(this, "", tvDefendReason, GetConstDataUtils.getFlaseCause());
                } else {
                    PickerSelectUtil.singleTextPicker(this, "", tvDefendReason, GetConstDataUtils.getBypassCause());
                }
                break;
            case R.id.ll_comit:

                if (!checkInfo()) {
                    return;
                }


                LogDetailsEntity bean = new LogDetailsEntity();
                bean.setNoteInfo(evDefendDesc.getText().toString());
                bean.setPlayLocaltion(etDefendPosition.getText().toString());

                if (mPosition == 1) {
                    bean.setAlarmReason(GetConstDataUtils.getThroughCause().indexOf(tvDefendReason.getText().toString().trim()));
                } else if (mPosition == 2) {
                    bean.setAlarmReason(GetConstDataUtils.getFlaseCause().indexOf(tvDefendReason.getText().toString().trim()));
                } else {
                    bean.setAlarmReason(GetConstDataUtils.getBypassCause().indexOf(tvDefendReason.getText().toString().trim()));
                }

                bean.setSlipNum(etDefendCode.getText().toString().trim());
                bean.setCreateUserId(ClientApplication.get().getUserId());
                bean.setLogType(mPosition);
                bean.setAlarmNum(Integer.parseInt(etDefendNext.getText().toString()));
                Intent intent = new Intent();
                intent.putExtra("bean", bean);
                setResult(RESULT_OK, intent);
                finishSelf();
                break;
        }
    }

    private boolean checkInfo() {
        if (TextUtils.isEmpty(etDefendCode.getText().toString().trim())) {
            showToast("防区编号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(etDefendPosition.getText().toString().trim())) {
            showToast("防区位置不能为空");
            return false;
        }
        if (TextUtils.isEmpty(etDefendNext.getText().toString().trim())) {
            showToast("报警次数不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvDefendReason.getText().toString().trim())) {
            showToast("报警原因不能为空");
            return false;
        }
        if (TextUtils.isEmpty(evDefendDesc.getText().toString().trim())) {
            showToast("备注不能为空");
            return false;
        }

        return true;
    }

    @OnClick(R.id.iv_project_voice)
    public void onViewClicked() {
         RxPerm.get(this).voicePerm((isSuccess)->{
            RecognitionManager.getSingleton().startRecognitionWithDialog(DefendLogItemWriteAndDetailActivity.this, evDefendDesc);
        });
    }
}
