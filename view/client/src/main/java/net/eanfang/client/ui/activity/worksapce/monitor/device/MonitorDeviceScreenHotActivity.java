package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.bean.security.SecurityCreateBean;
import com.eanfang.bean.security.SecurityListBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.contentsafe.ContentDefaultAuditing;
import com.eanfang.util.contentsafe.ContentSecurityAuditUtil;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityMonitorDeviceScreenHotBinding;
import net.eanfang.client.ui.activity.im.SelectIMContactActivity;
import net.eanfang.client.ui.activity.worksapce.security.SecurityDetailActivity;

import java.util.HashMap;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @author guanluocang
 * @data 2019/9/29  17:09
 * @description 截图
 */

public class MonitorDeviceScreenHotActivity extends BaseActivity {


    private ActivityMonitorDeviceScreenHotBinding monitorDeviceScreenHotBinding;

    private SecurityCreateBean securityCreateBean = new SecurityCreateBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDeviceScreenHotBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_screen_hot);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("视频截图");
        setLeftBack(true);
        GlideUtil.intoImageView(MonitorDeviceScreenHotActivity.this, getIntent().getStringExtra("imagePath"), monitorDeviceScreenHotBinding.ivScreenHot);
        monitorDeviceScreenHotBinding.tvEditReport.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("imagePath", getIntent().getStringExtra("imagePath"));
            bundle.putString("shopName", getIntent().getStringExtra("shopName"));
            JumpItent.jump(this, MonitorDeviceReportActivity.class, bundle);
        });
        monitorDeviceScreenHotBinding.tvScreenHotWeChat.setOnClickListener((v) -> {
            if (isWeixinAvilible()) {
                doShareWx();
            }
        });
        monitorDeviceScreenHotBinding.tvScreenHotContact.setOnClickListener((v) -> {
            doContact();
        });

        monitorDeviceScreenHotBinding.tvScreenHotCircle.setOnClickListener((v) -> {
            ContentSecurityAuditUtil.getInstance().toAuditing("", new ContentDefaultAuditing(MonitorDeviceScreenHotActivity.this) {
                @Override
                public void auditingSuccess() {
                    securityCreateBean.setSpcContent("实时监控");
                    securityCreateBean.setSpcImg(getIntent().getStringExtra("imagePath"));
                    doSubmit();
                }
            });
        });
    }

    public void doSubmit() {
        String imgKey = "monitor/report/" + StrUtil.uuid() + ".png";
        securityCreateBean.setSpcImg(imgKey);
        SDKManager.ossKit(MonitorDeviceScreenHotActivity.this).asyncPutImage(imgKey, getIntent().getStringExtra("imagePath"), (isSuccess) -> {
        });
        EanfangHttp.post(NewApiService.SERCURITY_CREATE)
                .upJson(JSONObject.toJSONString(securityCreateBean))
                .execute(new EanfangCallback<SecurityListBean.ListBean>(MonitorDeviceScreenHotActivity.this, true, SecurityListBean.ListBean.class, bean -> {
                    Bundle bundle = new Bundle();
                    bundle.putLong("spcId", bean.getSpcId());
                    JumpItent.jump(MonitorDeviceScreenHotActivity.this, SecurityDetailActivity.class, bundle);
                    showToast("分享成功");
                }));
    }

    public void doShareWx() {

        Wechat.ShareParams sp = new Wechat.ShareParams();
        //微信分享网页的参数严格对照列表中微信分享网页的参数要求
        sp.setImagePath(getIntent().getStringExtra("imagePath"));
        sp.setShareType(Platform.SHARE_IMAGE);
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
    }

    public void doContact() {

        Intent intent = new Intent(MonitorDeviceScreenHotActivity.this, SelectIMContactActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("id", StrUtil.uuid());
        bundle.putString("orderNum", ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());
        bundle.putString("picUrl", getIntent().getStringExtra("imagePath"));
        bundle.putString("creatTime", "实时监控");
        bundle.putString("workerName", ClientApplication.get().getLoginBean().getAccount().getRealName());
        bundle.putString("status", "0");
        bundle.putString("shareType", "11");
        bundle.putString("creatReleaseTime", DateUtil.now());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private boolean isWeixinAvilible() {
        // 获取packagemanager
        final PackageManager packageManager = this.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if ("com.tencent.mm".equals(pn)) {
                    return true;
                }
            }
        }
        //  没有安装微信的
        showToast("您的手机没有安装微信");
        return false;
    }
}
