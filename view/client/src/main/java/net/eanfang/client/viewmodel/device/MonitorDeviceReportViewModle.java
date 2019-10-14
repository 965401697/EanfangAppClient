package net.eanfang.client.viewmodel.device;

import android.app.Activity;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.base.kit.SDKManager;
import com.eanfang.biz.model.vo.MonitorReportVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;
import com.eanfang.util.PickerSelectUtil;

import net.eanfang.client.databinding.ActivityMonitorDeviceReportBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/10/12
 * @description 汇报
 */
public class MonitorDeviceReportViewModle extends BaseViewModel {

    @Setter
    private ActivityMonitorDeviceReportBinding monitorDeviceReportBinding;

    private MonitorRepo monitorRepo;
    private MonitorReportVo monitroReportVo;

    @Getter
    private MutableLiveData<MonitorReportVo> monitorReportVoMutableLiveData;

    public HashMap<String, String> mDealWithList = new HashMap<>();
    public HashMap<String, String> mCopyList = new HashMap<>();
    public HashMap<String, String> mGroupList = new HashMap<>();
    public String mImagePath;

    public MonitorDeviceReportViewModle() {
        monitorRepo = new MonitorRepo(new MonitorDs(this));
        monitroReportVo = new MonitorReportVo();
        monitorReportVoMutableLiveData = new MutableLiveData<>();
    }

    /**
     * 获取常用语
     */
    public void doGetCommonLanguage() {
        List<String> mList = new ArrayList<>();
        mList.add("图像出现异常");
        mList.add("人员脱岗");
        mList.add("设备出现异常");
        mList.add("画面模糊不清");
        PickerSelectUtil.singleTextPicker((Activity) monitorDeviceReportBinding.getRoot().getContext(), "", monitorDeviceReportBinding.etSign, mList);
    }

    /**
     * 发送汇报
     */
    public void doSendReport() {
        String mSign = monitorDeviceReportBinding.etSign.getText().toString().trim();
        if (TextUtils.isEmpty(mSign)) {
            showToast("请填写标注");
            return;
        }
        if (mDealWithList.size() <= 0) {
            showToast("请选择发送人");
            return;
        }
        if (mCopyList.size() <= 0) {
            showToast("请选择抄送人");
            return;
        }
        if (mGroupList.size() <= 0) {
            showToast("请选择群组");
            return;
        }
        String imgKey = "monitor/report/" + StrUtil.uuid() + ".png";
        SDKManager.ossKit((Activity) monitorDeviceReportBinding.getRoot().getContext()).asyncPutImage(imgKey, mImagePath, (isSuccess) -> {
        });
        monitroReportVo.getReportPic().set(imgKey);
        monitroReportVo.getReportRemarks().set(mSign);
        monitroReportVo.getHandleUserList().set(mDealWithList);
        monitroReportVo.getCopyUserList().set(mCopyList);
//        monitroReportVo.getSendGroupList().set(mGroupList);
        monitorRepo.doCreateDeviceReport(monitroReportVo).observe(lifecycleOwner, createSuccess -> {
            monitorReportVoMutableLiveData.setValue(createSuccess);
        });
    }

}
