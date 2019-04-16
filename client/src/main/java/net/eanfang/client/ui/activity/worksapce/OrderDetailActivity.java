package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.eanfang.util.ViewFindUtils;
import com.eanfang.witget.SharePopWindow;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.im.SelectIMContactActivity;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.OrderDetailFragment;
import net.eanfang.client.ui.fragment.OrderProgressFragment;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by MrHou
 *
 * @on 2017/11/23  19:46
 * @email houzhongzhou@yeah.net
 * @desc
 */

/**
 * 工作台-已报修-订单详情
 * Created by Administrator on 2017/3/15.
 */

public class OrderDetailActivity extends BaseClientActivity implements OnTabSelectListener {
    private final String[] mTitles = {
            "订单详情", "订单状态"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private Long id;
    private String mOrderTime = "";
    private boolean isVisible;

    /**
     * 分享
     */
    private SharePopWindow sharePopWindow;
    private HashMap hashMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        //loading加载动画
        loadingDialog.show();
        id = getIntent().getLongExtra("id", 0);
        mOrderTime = getIntent().getStringExtra("orderTime");
        //分享按钮是是否隐藏
        isVisible = getIntent().getBooleanExtra("isVisible", false);
        sharePopWindow = new SharePopWindow(this, pictureSelectItemsOnClick);
        sharePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                sharePopWindow.backgroundAlpha(1.0f);
            }
        });
        mFragments.add(OrderDetailFragment.getInstance(id));
        mFragments.add(OrderProgressFragment.getInstance(id, mOrderTime));
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_4);
        tabLayout_2.setViewPager(vp, mTitles, this, mFragments);
        tabLayout_2.setOnTabSelectListener(this);
        vp.setCurrentItem(0);
        setTitle("订单详情");
        setLeftBack();
        //去掉loading
        loadingDialog.dismiss();

        hashMap = ((OrderDetailFragment) mFragments.get(0)).getHashMap();

        if (!isVisible) {
            setRightTitle("分享");
            setRightTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //分享聊天
                    sharePopWindow.showAtLocation(OrderDetailActivity.this.findViewById(R.id.ll_share), Gravity.BOTTOM, 0, 0);
                    sharePopWindow.backgroundAlpha(0.5f);
                }
            });
        }
    }

    View.OnClickListener pictureSelectItemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // 微信
                case R.id.tv_share_wx:
                    doShareWx();
                    break;
                // 通讯录
                case R.id.tv_share_contact:
                    doContact();
                    break;
                // 取消
                case R.id.btn_cancel:
                    sharePopWindow.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    public void doContact() {

        Intent intent = new Intent(OrderDetailActivity.this, SelectIMContactActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("id", (String) hashMap.get("id"));
        bundle.putString("orderNum", (String) hashMap.get("orderNum"));
        bundle.putString("picUrl", (String) hashMap.get("picUrl"));
        bundle.putString("creatTime", (String) hashMap.get("creatTime"));
        bundle.putString("workerName", (String) hashMap.get("workerName"));
        bundle.putString("status", (String) hashMap.get("status"));
        bundle.putString("shareType", (String) hashMap.get("shareType"));

        intent.putExtras(bundle);
        startActivity(intent);
        sharePopWindow.dismiss();
    }

    public void doShareWx() {

        Wechat.ShareParams sp = new Wechat.ShareParams();
        //微信分享网页的参数严格对照列表中微信分享网页的参数要求
        sp.setTitle("易安防");
        sp.setText("报修订单分享");
        sp.setUrl("https://wechat.eanfang.net/?from=1#/orderdetail/" + hashMap.get("id"));
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.d("ShareSDK", "onComplete ---->  分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getStackTrace().toString());
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getMessage());
                throwable.getMessage();
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.d("ShareSDK", "onCancel ---->  分享取消");
            }
        });
        // 执行图文分享
        wechat.share(sp);
        sharePopWindow.dismiss();
    }

    @Override
    public void onTabSelect(int position) {
    }

    @Override
    public void onTabReselect(int position) {
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];

        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}

