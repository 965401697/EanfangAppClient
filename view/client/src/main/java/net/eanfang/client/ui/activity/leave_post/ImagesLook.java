package net.eanfang.client.ui.activity.leave_post;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import net.eanfang.client.R;

import java.util.ArrayList;

public class ImagesLook {
    private TextView popupTvPageNum;
    private PopupWindow popupWindow;
    private TextView txtRight;
    private int position;
    private ArrayList<String> mList;
    private View rootVew;
    private RelativeLayout popupRl;
    private ViewPager popupViewPager;
    private ImagesLookPagerAdapter adapter;

    public static ImagesLook getInstance() {
        return ImagesLook.MenuUtilHolder.INSTANCE;
    }

    /**
     * 弹起 popupWindow
     * 通过路径显示
     *
     * @param context context
     * @param parent  parent
     */
    public void show(Context context, View parent, final ArrayList<String> mList, final int position) {
        this.mList = mList;
        this.position = position;
        createView(context);
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAtLocation(parent, Gravity.TOP, 0, 60);
        }
    }

    /**
     * 创建 popupWindow 内容
     *
     * @param context context
     */
    @SuppressLint("InflateParams")
    private void createView(final Context context) {
        rootVew = LayoutInflater.from(context).inflate(R.layout.layout_popup_image, null);
        popupWindow = new PopupWindow(rootVew,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //设置为失去焦点 方便监听返回键的监听
        popupWindow.setFocusable(false);
        popupWindow.setClippingEnabled(false);
        // 如果想要popupWindow 遮挡住状态栏可以加上这句代码
//        popupWindow.setClippingEnabled(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        initLayout(context);
    }

    /**
     * 初始化 view
     */
    @SuppressLint("SetTextI18n")
    private void initLayout(Context context) {
        popupViewPager = rootVew.findViewById(R.id.popupViewPager);
        popupTvPageNum = rootVew.findViewById(R.id.popupTvPageNum);
        popupRl = rootVew.findViewById(R.id.titles_bar);
//        txtRight = rootVew.findViewById(R.id.txtRight);
        ImageView imgBack = rootVew.findViewById(R.id.iv_left);
        TextView txtTitle = rootVew.findViewById(R.id.tv_title);
        txtTitle.setText("查看图片");

        adapter = new ImagesLookPagerAdapter(context, mList);

        popupViewPager.setAdapter(adapter);

        int page = position + 1;
        popupTvPageNum.setText(page + "/" + mList.size());
        popupViewPager.setCurrentItem(position);

        popupViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int i) {
                int page = i + 1;
                popupTvPageNum.setText(page + "/" + mList.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(v -> close());

    }

    /**
     * 关闭popupWindow
     */

    private void close() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * 显示右上角删除按钮
     */
    public ArrayList<Integer> showDelete(boolean isShow) {
        final ArrayList<Integer> integers = new ArrayList<>();
        if (isShow) {
            txtRight.setVisibility(View.VISIBLE);
            txtRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(popupViewPager.getCurrentItem());
                    adapter.notifyDataSetChanged();
                    popupViewPager.setAdapter(adapter);
                    integers.add(popupViewPager.getCurrentItem());
                }
            });
        } else {
            txtRight.setVisibility(View.GONE);
        }
        return integers;
    }


    /**
     * 显示标题栏
     */
    public void showActionbar() {
        popupRl.setVisibility(View.VISIBLE);
    }

    /**
     * 关闭标题栏
     */
    public void closeActionbar() {
        popupRl.setVisibility(View.GONE);
    }

    private static class MenuUtilHolder {
        @SuppressLint("StaticFieldLeak")
        static ImagesLook INSTANCE = new ImagesLook();
    }
}