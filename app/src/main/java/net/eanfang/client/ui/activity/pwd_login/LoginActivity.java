//package net.eanfang.client.ui.activity.pwd_login;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import net.eanfang.client.R;
//import net.eanfang.client.ui.base.BaseActivity;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//
///**
// * Created by MrHou
// *
// * @on 2017/11/9  10:29
// * @email houzhongzhou@yeah.net
// * @desc 登录
// */
//
//public class LoginActivity extends BaseActivity {
//    public static final String TAG = LoginActivity.class.getSimpleName();
//    private Context context = this;
//
//    @BindView(R.id.et_user_accont)
//    EditText etUserAccont;
//    @BindView(R.id.et_user_pwd)
//    EditText etUserPwd;
//    @BindView(R.id.btn_login)
//    Button btnLogin;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        ButterKnife.bind(this);
//        initView();
//    }
//
//
//    /**
//     * 初始化操作
//     */
//    private void initView() {
//        setTitle("登录");
//
//    }
//
//    /**
//     * 找回密码
//     */
//    public void findPassword(View view) {
//        startActivity(new Intent(LoginActivity.this, FindPasswordActivity.class));
//    }
//
//    /**
//     * 注册
//     */
//    public void register(View view) {
//        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//    }
//}
