package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.PermKit;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.sign.SignActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/3/23  14:57
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class SignCtrlView extends BaseDialog {
    @BindView(R.id.ll_signin)
    LinearLayout llSignin;
    @BindView(R.id.ll_signout)
    LinearLayout llSignout;
    private Activity mContext;

    public SignCtrlView(Activity context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_sign);
        ButterKnife.bind(this);

        llSignin.setOnClickListener(v -> jumpSign("签到", 0));
        llSignout.setOnClickListener(v -> jumpSign("签退", 1));
    }

    private void jumpSign(String title, int status) {
        if (status == 0) {
            if (!PermKit.get().getSignInCreatePrem()) {
                return;
            }

        } else {
            if (!PermKit.get().getSignOutCreatePrem()) {
                return;
            }
        }
        RxPerm.get(mContext).locationPerm((isSuccess) -> {
            Intent intent = new Intent(mContext, SignActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("status", status);
            mContext.startActivity(intent);
            dismiss();
        });

    }
}
