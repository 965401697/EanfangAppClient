package net.eanfang.worker.ui.activity.worksapce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFriendActivity extends BaseWorkerActivity implements View.OnClickListener {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_friend_header)
    ImageView ivFriendHeader;
    @BindView(R.id.tv_friend_name)
    TextView tvFriendName;
    @BindView(R.id.ll_friend)
    LinearLayout llFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
//                    EanfangHttp.get("").execute(new EanfangCallback(AddFriendActivity.this,true,new EanfangCallback<String>()));


                    llFriend.setVisibility(View.VISIBLE);
                    llFriend.setOnClickListener(AddFriendActivity.this);
//                    ivFriendHeader
                    tvFriendName.setText("");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
