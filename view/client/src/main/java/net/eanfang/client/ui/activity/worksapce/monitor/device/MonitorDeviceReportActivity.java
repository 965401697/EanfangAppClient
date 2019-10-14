package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.loading.LoadKit;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.biz.model.vo.MonitorReportVo;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityMonitorDeviceReportBinding;
import net.eanfang.client.ui.activity.worksapce.oa.workreport.OAPersonAdaptet;
import net.eanfang.client.util.SendContactUtils;
import net.eanfang.client.viewmodel.device.MonitorDeviceReportViewModle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author guanluocang
 * @data 2019/9/29  15:35
 * @description 编辑汇报
 */

public class MonitorDeviceReportActivity extends BaseActivity {

    private final int REQUEST_CODE_GROUP = 101;
    private ActivityMonitorDeviceReportBinding monitorDeviceReportBinding;
    private MonitorDeviceReportViewModle monitorDeviceReportViewModle;

    /**
     * 处理人
     */
    private OAPersonAdaptet mDealeWithAdapter;
    private List<TemplateBean.Preson> mDealeWithList = new ArrayList<>();
    /**
     * 抄送人
     */
    private OAPersonAdaptet mCopyAdapter;
    private List<TemplateBean.Preson> mCopyList = new ArrayList<>();
    /**
     * 群组
     */
    private OAPersonAdaptet mGroupAdapter;
    private List<TemplateBean.Preson> mGroupList = new ArrayList<>();
    /**
     * 人员选择器的标志位
     */
    public int mFlag;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDeviceReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_device_report);
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("编辑汇报");
        setLeftBack(true);
        init();
        monitorDeviceReportViewModle.mImagePath = getIntent().getStringExtra("imagePath");
        monitorDeviceReportBinding.tvAddressName.setText("店名：" + getIntent().getStringExtra("shopName"));
        monitorDeviceReportBinding.tvTime.setText(DateUtil.now());
        GlideUtil.intoImageView(MonitorDeviceReportActivity.this, getIntent().getStringExtra("imagePath"), monitorDeviceReportBinding.ivThumbnail);
    }

    @Override
    protected ViewModel initViewModel() {
        monitorDeviceReportViewModle = LViewModelProviders.of(this, MonitorDeviceReportViewModle.class);
        monitorDeviceReportViewModle.setMonitorDeviceReportBinding(monitorDeviceReportBinding);
        monitorDeviceReportBinding.setViewModle(monitorDeviceReportViewModle);
        monitorDeviceReportViewModle.getMonitorReportVoMutableLiveData().observe(this, this::doEditSuccess);
        return monitorDeviceReportViewModle;
    }

    private void doEditSuccess(MonitorReportVo monitorReportVo) {
        showToast("发送成功");
        if (mGroupList.size() > 0) {
            Set hashSet = new HashSet();
            hashSet.addAll(mDealeWithAdapter.getData());
            hashSet.addAll(mCopyAdapter.getData());
            hashSet.addAll(mGroupAdapter.getData());

            if (mGroupList.size() > 0) {
                mGroupList.clear();
            }
            mGroupList.addAll(hashSet);
        } else {
            mGroupList.addAll(mDealeWithList);
            mGroupList.addAll(mCopyList);
        }
        Bundle bundle = new Bundle();
        bundle.putString("id", StrUtil.uuid());
        bundle.putString("orderNum", ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());
        bundle.putString("picUrl", getIntent().getStringExtra("imagePath"));
        bundle.putString("creatTime", "实时监控");
        bundle.putString("workerName", ClientApplication.get().getLoginBean().getAccount().getRealName());
        bundle.putString("status", "0");
        bundle.putString("shareType", "11");
        bundle.putString("creatReleaseTime", DateUtil.now());

        new SendContactUtils(bundle, handler, mGroupList, LoadKit.dialog(MonitorDeviceReportActivity.this), "实时监控").send();

    }


    /**
     * 初始化
     */
    public void init() {
        monitorDeviceReportBinding.rvDealWith.setLayoutManager(new GridLayoutManager(monitorDeviceReportBinding.getRoot().getContext(), 5));
        monitorDeviceReportBinding.rvCopy.setLayoutManager(new GridLayoutManager(monitorDeviceReportBinding.getRoot().getContext(), 5));
        monitorDeviceReportBinding.rvSendGroup.setLayoutManager(new GridLayoutManager(monitorDeviceReportBinding.getRoot().getContext(), 5));

        mDealeWithAdapter = new OAPersonAdaptet(monitorDeviceReportBinding.getRoot().getContext(), new ArrayList<TemplateBean.Preson>(), 2);
        mCopyAdapter = new OAPersonAdaptet(monitorDeviceReportBinding.getRoot().getContext(), new ArrayList<TemplateBean.Preson>(), 3);
        mGroupAdapter = new OAPersonAdaptet(monitorDeviceReportBinding.getRoot().getContext(), new ArrayList<TemplateBean.Preson>(), 4);
        monitorDeviceReportBinding.rvDealWith.setAdapter(mDealeWithAdapter);
        monitorDeviceReportBinding.rvCopy.setAdapter(mCopyAdapter);
        monitorDeviceReportBinding.rvSendGroup.setAdapter(mGroupAdapter);
    }

    public void setFlag(int flag) {
        this.mFlag = flag;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GROUP) {
            TemplateBean.Preson preson = (TemplateBean.Preson) data.getSerializableExtra("bean");
            mGroupList.clear();
            mGroupList.addAll(mGroupAdapter.getData());

            if (mGroupAdapter.getData().size() > 0) {
                if (!mGroupAdapter.getData().contains(preson)) {
                    mGroupList.add(preson);
                    mGroupAdapter.setNewData(mGroupList);
                }
            } else {
                mGroupList.add(preson);
                mGroupAdapter.setNewData(mGroupList);
            }
            return;
        }
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        Set hashSet = new HashSet();
        if (mFlag == 4) {
            hashSet.addAll(mGroupAdapter.getData());
            hashSet.addAll(presonList);
        }
        if (mFlag == 2) {
            mDealeWithList.clear();
            mDealeWithList.addAll(presonList);
        } else if (mFlag == 3) {
            mCopyList.clear();
            mCopyList.addAll(presonList);
        } else if (mFlag == 4) {
            mGroupList.clear();
            mGroupList.addAll(hashSet);
        }
        if (mFlag == 2) {
            mDealeWithAdapter.setNewData(mDealeWithList);
            for (TemplateBean.Preson preson : mDealeWithList) {
                monitorDeviceReportViewModle.mDealWithList.put("userId", preson.getUserId());
            }
        } else if (mFlag == 3) {
            mCopyAdapter.setNewData(mCopyList);
            for (TemplateBean.Preson preson : mCopyList) {
                monitorDeviceReportViewModle.mCopyList.put("userId", preson.getUserId());
            }
        } else if (mFlag == 4) {
            mGroupAdapter.setNewData(mGroupList);
            for (TemplateBean.Preson preson : mGroupList) {
                monitorDeviceReportViewModle.mGroupList.put("groupId", preson.getId());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
