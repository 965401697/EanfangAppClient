package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.CustomeApplication;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CheckSignPermission;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.WorkerEntity;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.MainActivity;
import net.eanfang.worker.ui.activity.worksapce.CustomerServiceActivity;
import net.eanfang.worker.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.WebActivity;
import net.eanfang.worker.ui.widget.CompanyListView;
import net.eanfang.worker.ui.widget.InstallCtrlView;
import net.eanfang.worker.ui.widget.MaintainCtrlView;
import net.eanfang.worker.ui.widget.PayOrderListCtrlView;
import net.eanfang.worker.ui.widget.ReportCtrlView;
import net.eanfang.worker.ui.widget.SignCtrlView;
import net.eanfang.worker.ui.widget.TakePubCtrlView;
import net.eanfang.worker.ui.widget.TaskCtrlView;
import net.eanfang.worker.ui.widget.TaskPubCtrlView;
import net.eanfang.worker.ui.widget.WorkCheckCtrlView;
import net.eanfang.worker.ui.widget.WorkSpaceSelectMapPopWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.eanfang.util.V.v;


/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 工作台
 */
public class WorkspaceFragment extends BaseFragment {

    private TextView tvCompanyName;
    private SimpleDraweeView iv_company_logo;

    //选择地图Pop
    private WorkSpaceSelectMapPopWindow selectMapPopWindow;
    private Double longitude;// 经度
    private Double latitude;//纬度
    private String companyName = "";//所在城市
    private String mSeachRequest = "五金店";

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_workspace;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        tvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        String companyName = EanfangApplication.getApplication().getUser()
                .getAccount().getDefaultUser().getCompanyEntity().getOrgName();
        if ("个人".equals(companyName)) {
            tvCompanyName.setText(companyName + "(点击切换公司)");
        } else {
            tvCompanyName.setText(companyName);
        }
        iv_company_logo = findViewById(R.id.iv_company_logo);
        setLogpic();
        // 选择地图pop
        selectMapPopWindow = new WorkSpaceSelectMapPopWindow(getActivity(), selectMapListener);
        selectMapPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                selectMapPopWindow.backgroundAlpha(1.0f);
            }
        });
        // 获取定位
        getLocation();
    }

    // 获取当前定位
    private void getLocation() {
        LocationUtil.location((MainActivity) getActivity(), (location) -> {
            LoginBean user = EanfangApplication.getApplication().getUser();
            if (user == null || StringUtils.isEmpty(user.getToken())) {
                return;
            }
            longitude = location.getLongitude();// 116
            latitude = location.getLatitude();// 39
            companyName = location.getCity();


        });
    }

    private void setLogpic() {
        List<OrgEntity> orgUnitEntityList = new ArrayList<>(EanfangApplication.getApplication().getUser().getAccount().getBelongCompanys());
        Long defaultOrgid = EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId();
        List<String> defaultPic = Stream.of(orgUnitEntityList).filter(bean -> bean.getOrgUnitEntity() != null
                && bean.getOrgUnitEntity().getLogoPic() != null
                && bean.getOrgUnitEntity().getOrgId().equals(defaultOrgid)).map(be -> v(() -> be.getOrgUnitEntity().getLogoPic())).toList();
        String imgUrl = v(() -> defaultPic.get(0));
        if (!StringUtils.isEmpty(imgUrl)) {
            iv_company_logo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + imgUrl));
        }
    }

    @Override
    protected void setListener() {

        progressCtrl();
        helpTools();
        teamWork();

//        //遗留故障
//        findViewById(R.id.ll_leave_bug).setOnClickListener((v) -> {
//            new LeaveBugView(getActivity(), true).show();
//        });
// 客服
        findViewById(R.id.iv_service).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CustomerServiceActivity.class));
        });
        //切换公司
        findViewById(R.id.ll_switch_company).setOnClickListener(v -> {
            new CompanyListView(getActivity(), (name, url) -> {
                if ("个人".equals(name)) {
                    tvCompanyName.setText(name + "(点击切换公司)");
                } else {
                    tvCompanyName.setText(name);
                }
                if (url != null) {
                    iv_company_logo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + url));
                }
            }).show();
        });
    }

    /**
     * 过程管控
     */
    private void progressCtrl() {
        findViewById(R.id.tv_work_service).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CustomerServiceActivity.class));
        });

    }

    private void helpTools() {
        //相机
        findViewById(R.id.tv_work_camera).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CameraActivity.class));
        });
        //天猫安防
        findViewById(R.id.tv_work_tm).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", "https://list.tmall.com/search_product.htm?q=%B0%B2%B7%C0&type=p&vmarket=&spm=875.7931836%2FB.a2227oh.d100&from=mallfp..pc_1_searchbutton")
                    .putExtra("title", "天猫安防"));
        });
        //京东安防
        findViewById(R.id.tv_work_jd).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", "https://list.jd.com/list.html?cat=670,716,7374")
                    .putExtra("title", "京东安防"));
        });
        // 五金店
        findViewById(R.id.tv_work_hardwareStore).setOnClickListener((v) -> {
            selectMapPopWindow.showAtLocation(findViewById(R.id.ll_workspace), Gravity.BOTTOM, 0, 0);
            selectMapPopWindow.backgroundAlpha(0.5f);
        });
    }

    /**
     * 协同办公
     */
    private void teamWork() {
        //签到
        findViewById(R.id.tv_sign).setOnClickListener((v) -> {

            // 检查有无权限
            List<String> ss = new ArrayList<>();
            if (CheckSignPermission.isCheckSign(CustomeApplication.get().getUser().getPerms())) {
                new SignCtrlView(getActivity()).show();
            } else {
                showToast("暂无权限");
            }
        });
        //工作汇报
        findViewById(R.id.tv_work_report).setOnClickListener((v) -> {
            new ReportCtrlView(getActivity(), true).show();
        });

        //布置任务
        findViewById(R.id.tv_work_task).setOnClickListener((v) -> {
            new TaskCtrlView(getActivity(), true).show();
        });
        //设备点检
        findViewById(R.id.tv_work_inspect).setOnClickListener((v) -> {
            new WorkCheckCtrlView(getActivity(), true).show();
        });
    }

    /**
     * 判断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    View.OnClickListener selectMapListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_gaodeMap:// 高德地图
                    doOpenMap("Gaode");
                    selectMapPopWindow.dismiss();
                    break;
                case R.id.btn_baiduMap: // 百度地图
                    doOpenMap("Baidu");
                    selectMapPopWindow.dismiss();
                    break;
                case R.id.btn_cancel:
                    selectMapPopWindow.dismiss();
                    break;

            }
        }
    };

    // 打开 第三方 地图
    public void doOpenMap(String name) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri = null;
        // 判断是否安装高德地图
        if ("Gaode".equals(name)) {
            if (isInstallByread("com.autonavi.minimap")) {
                intent.setPackage("com.autonavi.minimap");
                uri = Uri.parse("androidamap://poi?sourceApplication=softname&keywords=" + mSeachRequest + "&dev=0");
            } else {
                getActivity().startActivity(new Intent(getActivity(), WebActivity.class)
                        .putExtra("url", "http://uri.amap.com/search?keyword=" + mSeachRequest + "&center=" + longitude + "," + latitude + "&src=mypage")
                        .putExtra("title", "高德地图"));
            }
        } else if ("Baidu".equals(name)) {
            if (isInstallByread("com.baidu.BaiduMap")) {
                uri = Uri.parse("baidumap://map/place/search?query=" + mSeachRequest + "&location=" + latitude + "，" + longitude);
            } else {
                getActivity().startActivity(new Intent(getActivity(), WebActivity.class)
                        .putExtra("url", "http://api.map.baidu.com/place/search?query=" + mSeachRequest + "&location=" + latitude + "," + longitude + "&output=html&src=" + companyName + "|易安防")
                        .putExtra("title", "百度地图"));
            }
        }

        intent.setData(uri);
        startActivity(intent); //启动调用


    }

}
