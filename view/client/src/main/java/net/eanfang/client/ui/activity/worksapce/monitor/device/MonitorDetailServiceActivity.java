package net.eanfang.client.ui.activity.worksapce.monitor.device;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.util.CallUtils;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorDetailServiceBinding;

/**
 * @author guanluocang
 * @data 2019/10/14  15:12
 * @description 增值服务
 */

public class MonitorDetailServiceActivity extends BaseActivity {

    private ActivityMonitorDetailServiceBinding monitorDetailServiceBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorDetailServiceBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_detail_service);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        String mType = getIntent().getStringExtra("type");
        if (mType.equals("leave")) {
            setTitle("脱岗检测");
            monitorDetailServiceBinding.ivPic.setImageResource(R.mipmap.ic_monitor_device_face_leave);
            monitorDetailServiceBinding.tvText.setText("其基于计算机识别技术，通过配合现场摄像头，自动识别监控室脱岗行为。因此，此算法可确保场所处在24小时的监控下，极大程度上保证了人、员及财产安全。");
            monitorDetailServiceBinding.llClound.setVisibility(View.GONE);
        } else if (mType.equals("face")) {
            setTitle("人脸识别");
            monitorDetailServiceBinding.ivPic.setImageResource(R.mipmap.ic_monitor_device_face_service);
            monitorDetailServiceBinding.tvText.setText("提供包括人脸检测与分析、五官定位、人脸比对、人脸验证、活体检测等多种功能，为使用者提供高性能高可用的人脸识别服务。 可应用于智慧零售、智慧社区、智慧楼宇、在线身份认证等多种应用场景，充分满足各行业客户的人脸属性识别 及用户身份确认等需求。 ");
            monitorDetailServiceBinding.llClound.setVisibility(View.GONE);
        } else {
            setTitle("云存储");
            monitorDetailServiceBinding.ivPic.setImageResource(R.mipmap.ic_monitor_device_face_clound);
            monitorDetailServiceBinding.llClound.setVisibility(View.VISIBLE);
        }
        monitorDetailServiceBinding.tvContact.setOnClickListener((V) -> {
            CallUtils.call(MonitorDetailServiceActivity.this, "400-8909-280");
        });
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}
