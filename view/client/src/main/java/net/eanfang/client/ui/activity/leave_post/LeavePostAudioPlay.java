package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostAudioPlayBinding;

/**
 * @author liangkailun
 * Date ：2019-07-11
 * Describe :
 */
public class LeavePostAudioPlay extends BaseActivity {
    private ActivityLeavePostAudioPlayBinding mBinding;
    private PlayerEZVIZ playerEZVIZ;
    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_audio_play);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        String deviceSerial = getIntent().getStringExtra("deviceSerial");
        int channelNo = getIntent().getIntExtra("channelNo", 0);
        setLeftBack(true);
        setTitle("图像查岗");
        playerEZVIZ = new PlayerEZVIZ(mBinding.surfaceview,new Handler());
        playerEZVIZ.live(deviceSerial, channelNo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerEZVIZ != null) {
            playerEZVIZ.stopLive();
        }
    }
}
