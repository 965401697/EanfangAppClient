package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/16  14:39
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class InputNameAndValueView extends BaseDialog {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_value)
    EditText etValue;
    @BindView(R.id.btn_sure)
    Button btnSure;
    private onBtnClick click;

    public InputNameAndValueView(Activity context, onBtnClick click) {
        super(context);
        this.click = click;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_name_value);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        btnSure.setOnClickListener((v) -> {
            click.onClick(etName.getText().toString().trim(), etValue.getText().toString().trim());
            dismiss();
        });
    }

    public interface onBtnClick {
        void onClick(String name, String value);
    }
}
