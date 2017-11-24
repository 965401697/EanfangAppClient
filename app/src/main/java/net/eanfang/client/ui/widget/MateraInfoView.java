package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.base.BaseDialog;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  15:59
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MateraInfoView extends BaseDialog {
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.tv_equipment)
    TextView tvEquipment;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;

    private Activity mContext;
    private String type;
    private String name;
    private String model;
    private String number;
    private String mark;

    public MateraInfoView(Activity context, boolean isfull, String type, String name, String model, String number, String mark) {
        super(context, isfull);
        this.mContext = context;
        this.type = type;
        this.name = name;
        this.model = model;
        this.number = number;
        this.mark = mark;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_material);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("耗用材料");
        tvBusiness.setText(type);
        tvEquipment.setText(name);
        tvModel.setText(model);
        etLocation.setText(number);
        etCode.setText(mark);
        llAdd.setVisibility(View.GONE);
        etLocation.setEnabled(false);
        etCode.setEnabled(false);
    }
}
