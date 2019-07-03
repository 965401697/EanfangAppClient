package net.eanfang.worker.ui.activity.worksapce.tender;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.BuildConfig;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.biz.model.Message;
import com.eanfang.biz.model.entity.tender.TaskApplyEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.sdk.picture.GridImageAdapter;
import com.eanfang.sdk.picture.PictureInvoking;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityTenderCommitBinding;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.worker.viewmodle.tender.TenderCommitViewModle;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

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

    private TaskApplyEntity mTaskApplyEntity;

    /**
     * 选择图片
     */
    private List<LocalMedia> mPicList = new ArrayList<>();
    private PictureInvoking mPicInvoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tenderCommitBinding = DataBindingUtil.setContentView(this, R.layout.activity_tender_commit);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ViewModel initViewModel() {
        tenderCommitViewModle = LViewModelProviders.of(this, TenderCommitViewModle.class);
        tenderCommitBinding.setTenderCommitVm(tenderCommitViewModle);
        tenderCommitViewModle.setTenderCommitBinding(tenderCommitBinding);
        return tenderCommitViewModle;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("我要报价");
        setLeftBack(true);
        tenderCommitViewModle.mPublishId = getIntent().getLongExtra("publishId", 0);
        mTaskApplyEntity = (TaskApplyEntity) getIntent().getSerializableExtra("bidDetail");
        GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + BaseApplication.get().getAccount().getAvatar()), tenderCommitBinding.ivHeader);
        tenderCommitBinding.tvName.setText(BaseApplication.get().getAccount().getRealName());
        tenderCommitBinding.tvCompany.setText(BaseApplication.get().getCompanyEntity().getOrgName());
        if (BaseApplication.get().getAccount().getRealVerify() == 0) {
            tenderCommitBinding.ivVerifyStatus.setVisibility(View.VISIBLE);
        } else {
            tenderCommitBinding.ivVerifyStatus.setVisibility(View.GONE);
        }
        if (mTaskApplyEntity != null) {
            tenderCommitViewModle.doSetAgainData(mTaskApplyEntity);
        }
        initListener();
        mPicInvoke = new PictureInvoking((TenderCommitActivity) tenderCommitBinding.getRoot().getContext(), tenderCommitBinding.rvSelectPic, mPicList);
        mPicInvoke.initRecycle(3, onAddPicClickListener);
    }

    private void initListener() {
        tenderCommitViewModle.getCreateTenderLiveData().observe(this, this::getCommonData);
    }


    private void getCommonData(TaskPublishEntity taskPublishEntity) {
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setMsgContent(" 恭喜您，您的报价申请提交成功， 请耐心等待用工单位的回复。 ");
        message.setTip("确定");
        bundle.putSerializable("message", message);
        JumpItent.jump(this, StateChangeActivity.class, bundle);
        finish();
    }

    GridImageAdapter.onAddPicClickListener onAddPicClickListener = () -> {
        SDKManager.getPicture().create((TenderCommitActivity) tenderCommitBinding.getRoot().getContext()).takePhotos(new IPictureCallBack() {
            @Override
            public void onSuccess(List<LocalMedia> list) {
                mPicList = list;
                mPicInvoke.setList(mPicList);
                String mImages = PhotoUtils.getPhotoUrl("biz/tender/", mPicList, tenderCommitViewModle.uploadMap, true);
                tenderCommitBinding.getTenderCommitVo().getPictures().set(mImages);
            }
        });
    };

}
