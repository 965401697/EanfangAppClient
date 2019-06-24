package net.eanfang.worker.ui.activity.worksapce.tender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import android.net.Uri;
import android.os.Bundle;

import com.eanfang.BuildConfig;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.BaseApplication;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityTenderCommitBinding;
import net.eanfang.worker.viewmodle.tender.TenderCommitViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

import static com.eanfang.base.network.event.BaseActionEvent.EMPTY_DATA;

/**
 * @author guanluocang
 * @data 2019/6/21
 * @description 我要报价
 */

public class TenderCommitActivity extends BaseActivity {

    @Setter
    @Accessors(chain = true)
    private TenderCommitViewModle tenderCommitViewModle;

    private ActivityTenderCommitBinding tenderCommitBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tenderCommitBinding = DataBindingUtil.setContentView(this, R.layout.activity_tender_commit);
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("我要报价");
        setLeftBack(true);
        tenderCommitViewModle.mPublishId = getIntent().getLongExtra("publishId", 0);
        GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + BaseApplication.get().getAccount().getAvatar()), tenderCommitBinding.ivHeader);
        tenderCommitBinding.tvName.setText(BaseApplication.get().getAccount().getRealName());
        tenderCommitBinding.tvCompany.setText(BaseApplication.get().getCompanyEntity().getOrgName());
    }

    private void initListener() {
        tenderCommitViewModle.getActionLiveData().observe(this, (v) -> {
            if (v.getAction() == EMPTY_DATA) {
                //TODO
                finish();
            }
        });
    }

    @Override
    protected ViewModel initViewModel() {
        tenderCommitViewModle = LViewModelProviders.of(this, TenderCommitViewModle.class);
        tenderCommitBinding.setTenderCommitVm(tenderCommitViewModle);
        tenderCommitViewModle.setTenderCommitBinding(tenderCommitBinding);
        return tenderCommitViewModle;
    }
}
