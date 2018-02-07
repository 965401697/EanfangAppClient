package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/22  14:32
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class CreateTeamView extends BaseDialog {

    @BindView(R.id.et_input_company)
    EditText etInputCompany;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private Activity mContext;


    public CreateTeamView(Activity context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_create_team);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvCancle.setOnClickListener(v -> dismiss());
        tvConfirm.setOnClickListener(v -> createCompany());
    }

    private void createCompany() {
        EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_ADD)
                .params("name", etInputCompany.getText().toString().trim())
                .execute(new EanfangCallback<JSONObject>(mContext, true, JSONObject.class, (bean) -> {
                    dismiss();
                }));
    }
}