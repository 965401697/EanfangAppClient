package net.eanfang.worker.ui.activity.worksapce;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.MainActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class SystemMessageActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_iocn)
    ImageView ivIocn;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);
        ButterKnife.bind(this);
        setTitle("系统消息");
        setLeftBack();
        userId = getIntent().getStringExtra("userId");
        String content = getIntent().getStringExtra("content");
        tvMsg.setText("想添加您为好友");
        tvName.setText(userId);

        int unreadCount = RongIM.getInstance().getUnreadCount(Conversation.ConversationType.SYSTEM, userId);
        if (unreadCount == 0) {
            btnAccept.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.btn_accept)
    public void onViewClicked() {
        //同意被添加好友
        EanfangHttp.post(UserApi.POST_ACCEPT_FRIEND)
                .params("ids", userId)
                .execute(new EanfangCallback<org.json.JSONObject>(SystemMessageActivity.this, true, org.json.JSONObject.class, (bean) -> {


                    RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.SYSTEM, userId,
                            new RongIMClient.ResultCallback<Boolean>() {
                                @Override
                                public void onSuccess(Boolean aBoolean) {
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                }
                            });
                    RongIM.getInstance().startConversation(SystemMessageActivity.this, Conversation.ConversationType.PRIVATE, userId, "占位专用");

                    SystemMessageActivity.this.finish();

                }));
    }

//    private void DialogShow(String userId) {
//        // TODO: 2018/4/16 userid 可能是多个
//        AlertDialog dialog = new AlertDialog.Builder(this)
////                .setIcon(R.mipmap.icon)//设置标题的图片
//                .setTitle("消息通知")//设置对话框的标题
//                .setMessage("有人添加您为好友？")//设置对话框的内容
//                //设置对话框的按钮
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //拒接被添加好友
//                        EanfangHttp.post(UserApi.POST_REFUSE_FRIEND)
//                                .params("ids", userId)
//                                .execute(new EanfangCallback<JSONObject>(MainActivity.this, true, org.json.JSONObject.class, (bean) -> {
//                                }));
//                        dialog.dismiss();
//                    }
//                })
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //同意被添加好友
//                        EanfangHttp.post(UserApi.POST_ACCEPT_FRIEND)
//                                .params("ids", userId)
//                                .execute(new EanfangCallback<org.json.JSONObject>(MainActivity.this, true, org.json.JSONObject.class, (bean) -> {
//                                }));
//                        dialog.dismiss();
//                    }
//                }).create();
//        dialog.show();
//    }
}
