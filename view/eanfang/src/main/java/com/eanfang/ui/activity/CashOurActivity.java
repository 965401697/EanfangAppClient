package com.eanfang.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.BaseApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liangkailun
 * Date ：2019-05-17
 * Describe :提取页面
 */
public class CashOurActivity extends BaseActivity {
    public static final String EXTRA_MONEY = "extraMoney";
    @BindView(R2.id.tv_invite_money)
    TextView mTvInviteMoney;
    @BindView(R2.id.rb_weiXin)
    RadioButton mRbWeiXin;
    @BindView(R2.id.rb_zhiFuBao)
    RadioButton mRbZhiFuBao;
    @BindView(R2.id.et_code)
    EditText mEtCode;
    @BindView(R2.id.et_realName)
    EditText mEtRealName;
    @BindView(R2.id.et_phoneNumber)
    EditText mEtPhoneNumber;
    @BindView(R2.id.btn_extract)
    Button mBtnExtract;
    @BindView(R2.id.tv_unable_extract)
    TextView mTvUnableExtract;

    private int mExtractMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cash_our);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mExtractMoney = getIntent().getIntExtra(EXTRA_MONEY, 0);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("提现");
        setRadioChoose(true);
        if (mExtractMoney < 20) {
            mBtnExtract.setClickable(false);
            mBtnExtract.setEnabled(false);
            mTvUnableExtract.setVisibility(View.VISIBLE);
            mBtnExtract.getBackground().setAlpha(80);
        }
        mTvInviteMoney.setText("¥ " + mExtractMoney);
        mBtnExtract.setOnClickListener(v -> {
            if (checkDateFail()) {
                showToast("信息填写有误，请确认！");
            } else {
                extractData();
            }

        });
        mRbWeiXin.setOnCheckedChangeListener((buttonView, isChecked) -> setRadioChoose(isChecked));
        mRbZhiFuBao.setOnCheckedChangeListener((buttonView, isChecked) -> setRadioChoose(!isChecked));
    }

    private void setRadioChoose(boolean isWinXin) {
        mRbWeiXin.setChecked(isWinXin);
        mRbZhiFuBao.setChecked(!isWinXin);
    }

    private void extractData() {
        JSONObject object = new JSONObject();
        try {
            object.put("accId", BaseApplication.get().getAccId());
            object.put("realName", mEtRealName.getText());
            object.put("payType", "0");
            object.put("accountNo", mEtCode.getText());
            object.put("accountPhone", mEtPhoneNumber.getText());
            object.put("withdrawalMoney", mExtractMoney * 100);
            object.put("withdrawalStatus", mRbWeiXin.isChecked() ? "0" : "1");
            object.put("moneyType", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EanfangHttp.post(NewApiService.INSERT_WITHDRAWALS_RECORD)
                .upJson(object.toString())
                .execute(new EanfangCallback(this, true, JSONObject.class, bean -> {
                    startActivity(new Intent(CashOurActivity.this, ExtractSuccessActivity.class));
                }));
    }

    private boolean checkDateFail() {
        return StringUtils.isEmpty(mEtCode.getText())
                || StringUtils.isEmpty(mEtRealName.getText())
                || StringUtils.isEmpty(mEtPhoneNumber.getText());
    }
}
