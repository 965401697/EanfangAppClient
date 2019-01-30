package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AllMessageBean;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.activity.kpbs.KPBSActivity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.PermKit;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.MainActivity;
import net.eanfang.worker.ui.activity.worksapce.CustomerServiceActivity;
import net.eanfang.worker.ui.activity.worksapce.FaultRecordListActivity;
import net.eanfang.worker.ui.activity.worksapce.WebActivity;
import net.eanfang.worker.ui.activity.worksapce.equipment.EquipmentListActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.check.CheckListActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.task.TaskAssignmentListActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.workreport.WorkReportListActivity;
import net.eanfang.worker.ui.activity.worksapce.worktalk.WorkTalkControlActivity;
import net.eanfang.worker.ui.activity.worksapce.worktransfer.WorkTransferControlActivity;
import net.eanfang.worker.ui.widget.CompanyListView;
import net.eanfang.worker.ui.widget.SignCtrlView;
import net.eanfang.worker.ui.widget.WorkSpaceSelectMapPopWindow;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

import static com.eanfang.util.V.v;


/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 工作台
 */
public class WorkspaceFragment extends BaseFragment {

    /**
     * 箭头
     */
    private ImageView mIvDownIcon;
    private TextView tvCompanyName;
    private SimpleDraweeView iv_company_logo;

    /**
     * /选择地图Pop
     */
    private WorkSpaceSelectMapPopWindow selectMapPopWindow;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    //所在城市
    private String companyName = "";
    private String mSeachRequest = "五金店";
    /**
     * 消息气泡
     */
    private QBadgeView qBadgeViewReport = new QBadgeView(EanfangApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewTask = new QBadgeView(EanfangApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewInspect = new QBadgeView(EanfangApplication.get().getApplicationContext());
    private int mReport = 0;
    private int mTask = 0;
    private int mInspect = 0;
    /**
     * 切换公司 pop
     */
    private CompanyListView selectCompanyPop;
    List<OrgEntity> mList = new ArrayList<>();

    private RotateAnimation rotate;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_workspace;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    public void onResume() {
        super.onResume();
        doHttpOrderNums();
        String companyName = EanfangApplication.getApplication().getUser()
                .getAccount().getDefaultUser().getCompanyEntity().getOrgName();
        if ("个人".equals(companyName)) {
            tvCompanyName.setText(companyName + "(点击切换公司)");
        } else {
            tvCompanyName.setText(companyName);
        }
    }

    @Override
    protected void initView() {
        tvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        mIvDownIcon = (ImageView) findViewById(R.id.iv_down_icon);

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
        // 汇报
        qBadgeViewReport.bindTarget(findViewById(R.id.tv_work_report))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
        // 任务
        qBadgeViewTask.bindTarget(findViewById(R.id.tv_work_task))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
        //检查
        qBadgeViewInspect.bindTarget(findViewById(R.id.tv_work_inspect))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
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
            doChangeCompany();
        });
    }

    /**
     * 切换公司
     */
    private void doChangeCompany() {

        EanfangHttp.post(NewApiService.GET_COMPANY_ALL_LIST)
                .params("accId", EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getAccId() + "")
                // 公司类型（单位类型0平台总公司1城市平台公司2企事业单位3安防公司）
                .params("orgType", "3")
                .execute(new EanfangCallback<OrgEntity>(getActivity(), false, OrgEntity.class, true, bean -> {
                    mList = bean;
                    if (mList == null || mList.size() <= 0) {
                        showToast("暂无安防公司");
                        return;
                    }
                    rotate = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF,
                            0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(300);
                    rotate.setFillAfter(true);
                    mIvDownIcon.startAnimation(rotate);
                    selectCompanyPop = new CompanyListView(getActivity(), mList, ((name, url) -> {
                        if ("个人".equals(name)) {
                            tvCompanyName.setText(name);
                        } else {
                            tvCompanyName.setText(name);
                        }
                        if (url != null) {
                            iv_company_logo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + url));
                        } else {
                            iv_company_logo.setImageURI("");
                        }
                        selectCompanyPop.dismiss();
                        doHttpOrderNums();
                    }));
                    selectCompanyPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            selectCompanyPop.backgroundAlpha(1.0f);
                            mIvDownIcon.clearAnimation();
                        }
                    });
                    selectCompanyPop.showAsDropDown(findViewById(R.id.ll_company_top));
                }));
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
        //码率
        findViewById(R.id.tv_work_calculate).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), KPBSActivity.class));
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
        //行业知识
        findViewById(R.id.tv_work_knowledge).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), WebActivity.class)
                        .putExtra("url", "https://news.eanfang.net/")
                        .putExtra("title", "行业知识")));
    }

    /**
     * 协同办公
     */
    private void teamWork() {
        //签到
        findViewById(R.id.tv_sign).setOnClickListener((v) -> {
            if (workerApprove()) {
                // 检查有无权限
                List<String> ss = new ArrayList<>();
                new SignCtrlView(getActivity()).show();
            }
        });
        //工作汇报
        findViewById(R.id.tv_work_report).setOnClickListener((v) -> {
//            new ReportCtrlView(getActivity(), true).show();
//            Intent intent = new Intent(getActivity(), ReportParentActivity.class);
//            startActivity(intent);

            if (!PermKit.get().getWorkReportListPrem()) return;
            Intent intent = new Intent(getActivity(), WorkReportListActivity.class);
//            intent.putExtra("title", title);
//            intent.putExtra("type", type);
            startActivity(intent);
        });

        //布置任务
        findViewById(R.id.tv_work_task).setOnClickListener((v) -> {
//            new TaskCtrlView(getActivity(), true).show();
//            Intent intent = new Intent(getActivity(), TaskParentActivity.class);
            if (!PermKit.get().getWorkTaskListPrem()) return;
            Intent intent = new Intent(getActivity(), TaskAssignmentListActivity.class);
            startActivity(intent);
        });

        //设备点检
        findViewById(R.id.tv_work_inspect).setOnClickListener((v) -> {
//            new WorkCheckCtrlView(getActivity(), true).show();
//            Intent intent = new Intent(getActivity(), CheckParentActivity.class);
            if (!PermKit.get().getWorkInspectListPrem()) return;
            Intent intent = new Intent(getActivity(), CheckListActivity.class);
            startActivity(intent);
        });


        //故障记录
        findViewById(R.id.tv_work_fault).setOnClickListener((v) -> {
            if (PermKit.get().getFailureListPerm()) {
                if (workerApprove()) {
                    Intent intent = new Intent(getActivity(), FaultRecordListActivity.class);
                    startActivity(intent);
                }
            }
        });

        //设备库
        findViewById(R.id.tv_work_library).setOnClickListener((v) -> {
            if (!PermKit.get().getExchangeListPrem()) return;
            if (workerApprove()) {
                Intent intent = new Intent(getActivity(), EquipmentListActivity.class);
                startActivity(intent);
            }
        });
        //面谈员工
        findViewById(R.id.tv_work_talk).setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), WorkTalkControlActivity.class);
            startActivity(intent);
        });

        //交接班
        findViewById(R.id.tv_work_transfer).setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), WorkTransferControlActivity.class);
            startActivity(intent);
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

    /**
     * 获取订单数量
     */
    private void doHttpOrderNums() {
        EanfangHttp.get(UserApi.ALL_MESSAGE).execute(new EanfangCallback<AllMessageBean>(getActivity(), false, AllMessageBean.class, (bean -> {
            doSetOrderNums(bean);
        })));
    }

    public void doSetOrderNums(AllMessageBean bean) {
        // 汇报
        if (bean.getReport() > 0) {
            mReport = bean.getReport();
        } else {
            mReport = 0;
        }
        qBadgeViewReport.setBadgeNumber(mReport);
        // 任务
        if (bean.getTask() > 0) {
            mTask = bean.getTask();
        } else {
            mTask = 0;
        }
        qBadgeViewTask.setBadgeNumber(mTask);
        //检查
        if (bean.getInspect() > 0) {
            mInspect = bean.getInspect();
        } else {
            mInspect = 0;
        }
        qBadgeViewInspect.setBadgeNumber(mInspect);
        /**
         * 底部红点更新
         * */
        EventBus.getDefault().post(bean);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
