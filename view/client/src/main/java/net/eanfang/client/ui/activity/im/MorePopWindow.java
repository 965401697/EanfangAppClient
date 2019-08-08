package net.eanfang.client.ui.activity.im;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.contacts.CreatTeamActivity;
import net.eanfang.client.ui.widget.StartMessageView;


/**
 * Created by O u r on 2018/5/2.
 */

public class MorePopWindow extends PopupWindow {
    private final int CREAT_TEAM_CODE = 49;

    @SuppressLint("InflateParams")
    public MorePopWindow(final Activity context, boolean isVisable) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.popu_add, null);

        // 设置SelectPicPopupWindow的View
        this.setContentView(content);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);


        RelativeLayout re_addfriends = content.findViewById(R.id.re_addfriends);
        RelativeLayout re_chatroom = content.findViewById(R.id.re_chatroom);
        RelativeLayout reCreateTeam = content.findViewById(R.id.re_create_team);
        if (isVisable) {
            re_addfriends.setVisibility(View.GONE);
            re_chatroom.setVisibility(View.GONE);
            reCreateTeam.setVisibility(View.VISIBLE);

        }
        RelativeLayout re_scanner = content.findViewById(R.id.re_scanner);
        RelativeLayout re_group = content.findViewById(R.id.re_group);
        re_addfriends.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                if (PermKit.get().getIsOwn()) {
//                    ToastUtil.get().showToast(context,"暂无权限访问，请创建或加入企业后再试。");
//                    return;
//                }

                Intent intent = new Intent(new Intent(context, NewSelectIMContactActivity.class));
//                Intent intent = new Intent(new Intent(context, SelectedFriendsActivity.class));
                intent.putExtra("flag", 1);
                context.startActivity(intent);
                MorePopWindow.this.dismiss();
            }

        });
        re_chatroom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new StartMessageView(context, true).show();
                MorePopWindow.this.dismiss();
                MorePopWindow.this.dismiss();
            }

        });
        re_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(context, AddFriendActivity.class));
                intent.putExtra("add_friend", "add_friend");
                context.startActivity(intent);
                MorePopWindow.this.dismiss();
            }
        });

        re_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AddFriendActivity.class));
                MorePopWindow.this.dismiss();
            }
        });
        reCreateTeam.setOnClickListener(view -> context.startActivityForResult(new Intent(context, CreatTeamActivity.class), CREAT_TEAM_CODE));

    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }

}
