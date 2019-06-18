package net.eanfang.worker.ui.activity.worksapce.tender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityTenderCreateBinding;
import net.eanfang.worker.ui.activity.worksapce.design.DesignActivity;
import net.eanfang.worker.viewmodle.tender.TenderCreateViewModle;
import net.eanfang.worker.viewmodle.tender.TenderViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/6/12
 * @description 用工发布
 */

public class TenderCreateActivity extends BaseActivity {


    private static int REQUSERT_ADDRESS_CODE = 100;

    private ActivityTenderCreateBinding mTenderCreateBinding;
    @Setter
    @Accessors(chain = true)
    private TenderCreateViewModle tenderCreateViewModle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mTenderCreateBinding = DataBindingUtil.setContentView(this, R.layout.activity_tender_create);
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("用工发布");
        setLeftBack(true);

        /**
         * 选择地区
         * */
        mTenderCreateBinding.rlSelectAddress.setOnClickListener((v) -> {
            JumpItent.jump(this, SelectAddressActivity.class, REQUSERT_ADDRESS_CODE);
        });
    }

    @Override
    protected ViewModel initViewModel() {
        tenderCreateViewModle = LViewModelProviders.of(this, TenderCreateViewModle.class);
        mTenderCreateBinding.setTenderCreateViewModle(tenderCreateViewModle);
        tenderCreateViewModle.setMTenderCreateBinding(mTenderCreateBinding);
        return tenderCreateViewModle;
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
            tenderCreateViewModle.lon = item.getLatitude().toString();
            tenderCreateViewModle.lon = item.getLongitude().toString();
            tenderCreateViewModle.province = item.getProvince();
            tenderCreateViewModle.city = item.getCity();
            tenderCreateViewModle.contry = item.getAddress();
            mTenderCreateBinding.tvProjectAddress.setText(item.getProvince() + item.getCity() + item.getAddress());

        }
    }
}
