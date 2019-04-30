package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.eanfang.util.GetDateUtils;
import com.yaf.base.entity.ProtectionLogEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.im.SelectIMContactActivity;

/**
 * Created by O u r on 2018/7/26.
 */

public class DefendMorePopWindow extends PopupWindow {

    @SuppressLint("InflateParams")
    public DefendMorePopWindow(final Activity context, ProtectionLogEntity bean, boolean isVisable) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.popu_defend_more, null);

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


        RelativeLayout rl_detail = content.findViewById(R.id.rl_detail);
        RelativeLayout rl_share = content.findViewById(R.id.rl_share);

        if (isVisable) {
            rl_share.setVisibility(View.GONE);
        }

        rl_detail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DefendLogDetailDetailActivity.class).putExtra("id", String.valueOf(bean.getId())));
                DefendMorePopWindow.this.dismiss();
            }

        });
        rl_share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                share(context, bean);
                DefendMorePopWindow.this.dismiss();
            }

        });
    }


    private void share(Activity activity, ProtectionLogEntity bean) {

        //分享聊天

        Intent intent = new Intent(activity, SelectIMContactActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("id", String.valueOf(bean.getId()));
        bundle.putString("orderNum", bean.getOrderNumber());
        bundle.putString("creatTime", GetDateUtils.dateToDateTimeStringForChinse(bean.getCreateTime()));
        bundle.putString("workerName", bean.getAssigneeUser().getAccountEntity().getRealName());
        bundle.putString("status", String.valueOf(bean.getStatus()));
        bundle.putString("shareType", "9");

        intent.putExtras(bundle);
        activity.startActivity(intent);


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

