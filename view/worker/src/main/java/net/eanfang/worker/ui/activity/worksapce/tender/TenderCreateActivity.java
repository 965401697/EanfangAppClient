package net.eanfang.worker.ui.activity.worksapce.tender;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.BuildConfig;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityTenderCreateBinding;
import net.eanfang.worker.viewmodle.tender.TenderCreateViewModle;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/6/12
 * @description 用工发布
 */

public class TenderCreateActivity extends BaseActivity implements SelectTimeDialogFragment.SelectTimeListener {


    private static int REQUSERT_ADDRESS_CODE = 100;

    private ActivityTenderCreateBinding mTenderCreateBinding;
    @Setter
    @Accessors(chain = true)
    private TenderCreateViewModle tenderCreateViewModle;

    private TaskPublishEntity mReleasetaskPublishEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mTenderCreateBinding = DataBindingUtil.setContentView(this, R.layout.activity_tender_create);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("用工发布");
        setLeftBack(true);
        initListener();
        mReleasetaskPublishEntity = (TaskPublishEntity) getIntent().getSerializableExtra("agaginRelase");
        GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + BaseApplication.get().getAccount().getAvatar()), mTenderCreateBinding.ivHeader);
        mTenderCreateBinding.tvName.setText(BaseApplication.get().getAccount().getRealName());
        mTenderCreateBinding.tvCompany.setText(BaseApplication.get().getCompanyEntity().getOrgName());
        if (mReleasetaskPublishEntity != null) {
            tenderCreateViewModle.doReleaseAgain(mReleasetaskPublishEntity);
        }
    }

    private void initListener() {
        /**
         * 选择地区
         * */
        mTenderCreateBinding.rlSelectAddress.setOnClickListener((v) -> {
            JumpItent.jump(this, SelectAddressActivity.class, REQUSERT_ADDRESS_CODE);
        });
        /**
         * 选择时间
         * */
        mTenderCreateBinding.rlSelectTime.setOnClickListener((v) -> {
            new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
        });
    }


    @Override
    protected ViewModel initViewModel() {
        tenderCreateViewModle = LViewModelProviders.of(this, TenderCreateViewModle.class);
        mTenderCreateBinding.setTenderCreateViewModle(tenderCreateViewModle);
        tenderCreateViewModle.setMTenderCreateBinding(mTenderCreateBinding);
        tenderCreateViewModle.getCreateTenderLiveData().observe(this, this::doCreateFinish);
        return tenderCreateViewModle;
    }

    private void doCreateFinish(TaskPublishEntity taskPublishEntityPageBean) {
        finish();
    }


    /**
     * 地图选址 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode == REQUSERT_ADDRESS_CODE) {
            SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
            Log.e("address", item.toString());
            tenderCreateViewModle.lat = item.getLatitude().toString();
            tenderCreateViewModle.lon = item.getLongitude().toString();
            tenderCreateViewModle.province = item.getProvince();
            tenderCreateViewModle.city = item.getCity();
            tenderCreateViewModle.contry = item.getAddress();
            tenderCreateViewModle.detailPlace = item.getName();
            mTenderCreateBinding.tvProjectAddress.setText(item.getProvince() + item.getCity() + item.getAddress());
        }
    }


    @Override
    public void getData(String time) {
        if (StringUtils.isEmpty(time) || "".equals(time)) {
            mTenderCreateBinding.tvStartTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            mTenderCreateBinding.tvStartTime.setText(time);
        }
    }

}
