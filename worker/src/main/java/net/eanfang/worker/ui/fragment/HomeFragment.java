package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.CustomeApplication;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ClientData;
import com.eanfang.model.NoticeEntity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CheckSignPermission;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.witget.BannerView;
import com.eanfang.witget.RollTextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.worksapce.datastatistics.DataStaticsticsListActivity;
import net.eanfang.worker.ui.activity.worksapce.datastatistics.DataStatisticsActivity;
import net.eanfang.worker.ui.activity.worksapce.design.DesignActivity;
import net.eanfang.worker.ui.activity.worksapce.maintenance.MaintenanceActivity;
import net.eanfang.worker.ui.activity.worksapce.maintenance.MaintenanceListActivity;
import net.eanfang.worker.ui.activity.worksapce.maintenance.MaintenanceListFragment;
import net.eanfang.worker.ui.activity.worksapce.repair.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.WebActivity;
import net.eanfang.worker.ui.activity.worksapce.scancode.ScanCodeActivity;
import net.eanfang.worker.ui.adapter.HomeDataAdapter;
import net.eanfang.worker.ui.widget.InstallCtrlView;
import net.eanfang.worker.ui.widget.MaintainCtrlView;
import net.eanfang.worker.ui.widget.PayOrderListCtrlView;
import net.eanfang.worker.ui.widget.SignCtrlView;
import net.eanfang.worker.ui.widget.TakePubCtrlView;
import net.eanfang.worker.ui.widget.TaskPubCtrlView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.eanfang.util.V.v;


/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 首页
 */

public class HomeFragment extends BaseFragment {
    private BannerView bannerView;
    //头部标题
    private TextView tvHomeTitle;
    private RollTextView rollTextView;

    private RecyclerView rvData;
    private List<ClientData> clientDataList = new ArrayList<>();
    private HomeDataAdapter homeDataAdapter;

    private RelativeLayout rlAllData;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        rvData = (RecyclerView) findViewById(R.id.rv_data);

        rlAllData = (RelativeLayout) findViewById(R.id.rl_allData);
        initIconClick();
        initLoopView();
        //设置布局样式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvData.setLayoutManager(linearLayoutManager);
        initCount();
        initFalseData();
        doHttpNews();

    }

    @Override
    public void onResume() {
        super.onResume();
        tvHomeTitle = (TextView) findViewById(R.id.tv_homeTitle);
        String orgName = v(() -> (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName()));
        if (("个人").equals(orgName)) {
            tvHomeTitle.setText("易安防");
        } else {
            tvHomeTitle.setText(orgName);
        }

    }

    /**
     * 工作按钮
     */
    private void initIconClick() {
        //报修订单
        findViewById(R.id.tv_reparir_order).setOnClickListener((v) -> {
            if (workerApprove()) {
                startActivity(new Intent(getActivity(), RepairCtrlActivity.class));
            }
        });
        //报装订单
        findViewById(R.id.tv_install_order).setOnClickListener((v) -> {
            if (workerApprove()) {
                new InstallCtrlView(getActivity(), true).show();
            }
        });
        //设计订单
        findViewById(R.id.tv_design_order).setOnClickListener((v) -> {
            if (workerApprove()) {
                JumpItent.jump(getActivity(), DesignActivity.class);
            }
        });
        //维保订单
        findViewById(R.id.tv_maintain_order).setOnClickListener((v) -> {
            if (workerApprove()) {
//                new MaintainCtrlView(getActivity(), true).show();
                startActivity(new Intent(getActivity(), MaintenanceActivity.class));
            }
        });
        //项目发包
        findViewById(R.id.tv_project_send).setOnClickListener((v) -> {
            if (workerApprove()) {
                new TaskPubCtrlView(getActivity(), true).show();
            }
        });
        //项目接包
        findViewById(R.id.tv_project_receive).setOnClickListener((v) -> {
            if (workerApprove()) {
                new TakePubCtrlView(getActivity(), true).show();
            }
        });
        //内部报价
        findViewById(R.id.tv_inside_price).setOnClickListener((v) -> {
            if (workerApprove()) {
                new PayOrderListCtrlView(getActivity(), true).show();
            }
        });
        //签到
        findViewById(R.id.tv_sign).setOnClickListener((v) -> {
            if (workerApprove()) {
                // 检查有无权限
                List<String> ss = new ArrayList<>();
                if (CheckSignPermission.isCheckSign(CustomeApplication.get().getUser().getPerms())) {
                    new SignCtrlView(getActivity()).show();
                } else {
                    showToast("暂无权限");
                }
            }
        });
        //扫描二维码
        findViewById(R.id.iv_scan).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), ScanCodeActivity.class).putExtra("from", EanfangConst.QR_CLIENT));
        });

    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_camera).setOnClickListener(v -> startActivity(new Intent(getActivity(), CameraActivity.class)));
    }

    /**
     * 统计
     */
    private void initCount() {
        rlAllData.setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), DataStaticsticsListActivity.class));
        });
        rvData.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), DataStatisticsActivity.class));
//                jumpWebview();
            }
        });
    }

    private void jumpWebview() {
        boolean isHave = EanfangApplication.getApplication().getUser().getPerms().contains("top:statistics:count");
        if (isHave == true) {
            String token = EanfangApplication.getApplication().getUser().getToken();
            startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", "http:/worker.eanfang.net:8099/#/totalPhone?token=" + token)
                    .putExtra("title", "数据统计"));
        } else {
            showToast("您没有权限");
        }
    }

    /**
     * 初始化轮播控件
     */
    private void initLoopView() {
        bannerView = findViewById(R.id.bv_loop);
        int[] images = {R.mipmap.ic_worker_banner_1, R.mipmap.ic_worker_banner_2, R.mipmap.ic_worker_banner_3, R.mipmap.ic_worker_banner_4, R.mipmap.ic_worker_banner_5};
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView image = new ImageView(getActivity());
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(images[i]);
            viewList.add(image);
        }
        bannerView.startLoop(true);
        bannerView.setViewList(viewList);
    }

    /**
     * 初始化rolltext显示的文本
     */
    private void initRollTextView(List<NoticeEntity> list) {
        rollTextView = findViewById(R.id.home_recommand_ad_text);
        List<View> views = new ArrayList<>();
        List<String> data = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        String repairStr = "通过易安防接到了一个报修订单。";
        String installStr = "通过易安防完成了一次报装订单。";
        String quoteStr = "通过易安防进行了一次报价。";
        String applyStr = "通过易安防接到了一个大工程。";
        String designStr = "通过易安防接到了一个设计订单。";

        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                NoticeEntity noticeEntity = list.get(i);
                if (noticeEntity.getNoticeType() == 12) {
                    data.add(repairStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 27) {
                    data.add(installStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 29) {
                    data.add(quoteStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 33) {
                    data.add(applyStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 37) {
                    data.add(designStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                }
                String realName = noticeEntity.getReciveAccEntity().getRealName();
                StringBuilder showName = new StringBuilder();
                if (realName.length() >= 1) {
                    showName.append(realName.charAt(0));
                }
                if (realName.length() >= 2) {
                    showName.append("*");
                }
                if (realName.length() >= 3) {
                    showName.append(realName.charAt(2));
                }
                titleList.add(showName + "先生");
            }

        }


        for (int i = 0; i < data.size(); i++) {
            View view = View.inflate(getContext(), R.layout.rolltext_item, null);
            TextView content = (TextView) view.findViewById(R.id.tv_roll_item_text);
            TextView title = (TextView) view.findViewById(R.id.tv_roll_item_title);
            title.setText(titleList.get(i).toString());
            content.setText(data.get(i).toString());
            views.add(view);
        }
        rollTextView.setViews(views);
        rollTextView.setOnItemClickListener((position, view) -> {
//            showToast("暂无可点");
        });
    }

    public void doHttpNews() {

        EanfangHttp.get(NewApiService.GET_PUSH_NEWS).execute(new EanfangCallback<NoticeEntity>(getActivity(), false, NoticeEntity.class, true, (list -> {
            initRollTextView(list);
        })));


    }

    private void initFalseData() {
        clientDataList = new ArrayList<>();
        ClientData clientDataOne = new ClientData();
        clientDataOne.setType(1);
        clientDataOne.setTotal(23);
        clientDataOne.setAdded(5);
        clientDataOne.setStatusOne(16);
        clientDataOne.setStatusTwo(2);
        clientDataOne.setStatusThree(1);
        clientDataList.add(clientDataOne);
        ClientData clientDataTwo = new ClientData();
        clientDataTwo.setType(2);
        clientDataTwo.setTotal(16);
        clientDataTwo.setAdded(7);
        clientDataTwo.setStatusOne(18);
        clientDataTwo.setStatusTwo(9);
        clientDataTwo.setStatusThree(4);
        clientDataList.add(clientDataTwo);
        ClientData clientDataThree = new ClientData();
        clientDataThree.setType(3);
        clientDataThree.setTotal(37);
        clientDataThree.setAdded(3);
        clientDataThree.setStatusOne(12);
        clientDataThree.setStatusTwo(3);
        clientDataThree.setStatusThree(6);
        clientDataList.add(clientDataThree);
        ClientData clientDataFour = new ClientData();
        clientDataFour.setType(4);
        clientDataFour.setTotal(27);
        clientDataFour.setAdded(7);
        clientDataFour.setStatusOne(13);
        clientDataFour.setStatusTwo(6);
        clientDataFour.setStatusThree(8);
        clientDataList.add(clientDataFour);

        homeDataAdapter = new HomeDataAdapter(R.layout.layout_home_data, clientDataList);
        rvData.setAdapter(homeDataAdapter);
    }
}
