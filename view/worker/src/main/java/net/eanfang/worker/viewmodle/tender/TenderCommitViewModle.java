package net.eanfang.worker.viewmodle.tender;

import android.app.Activity;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;

import com.annimon.stream.Stream;
import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.entity.tender.TaskApplyEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.model.vo.tender.TenderCommitVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderDs;
import com.eanfang.biz.rds.sys.repo.tender.TenderRepo;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.databinding.ActivityTenderCommitBinding;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/6/22
 * @description 我要报价
 */
public class TenderCommitViewModle extends BaseViewModel {
    private TenderRepo tenderRepo;
    @Setter
    @Getter
    private ActivityTenderCommitBinding tenderCommitBinding;

    @Getter
    private MutableLiveData<TaskPublishEntity> createTenderLiveData;

    private TenderCommitVo tenderCommitVo;
    public Long mPublishId;

    public TenderCommitViewModle() {
        tenderRepo = new TenderRepo(new TenderDs(this));
        createTenderLiveData = new MutableLiveData<>();
        tenderCommitVo = new TenderCommitVo();
    }

    /**
     * 预算单位
     */
    public void doSelectBudgetUnit() {
        PickerSelectUtil.singleTextPicker((Activity) tenderCommitBinding.getRoot().getContext(), "", tenderCommitBinding.tvBudgetUnit,
                Stream.of(GetConstDataUtils.getTenderBudgetUnit()).map(bus -> bus).toList());

    }

    /**
     * 用工要求
     */
    public void doInputPlan() {
        doInput(tenderCommitBinding.etPlan);
    }

    private void doInput(EditText editText) {
        RecognitionManager.getSingleton().startRecognitionWithDialog(tenderCommitBinding.getRoot().getContext(), editText);
    }

    /**
     * 完善资料
     */
    public boolean doCheckInfo() {
        if (StringUtils.isEmpty(tenderCommitBinding.tvBudget.getText())) {
            showToast("请填写预期预算");
            return false;
        }

        if (StringUtils.isEmpty(tenderCommitBinding.etPlan.getText())) {
            showToast("请填写施工方案");
            return false;
        }
        doSubmit();
        return true;
    }

    /**
     * 提交报价
     */
    public void doSubmit() {
        tenderCommitVo.getShopTaskPublishId().set(mPublishId + "");
        tenderCommitVo.getApplyContacts().set(BaseApplication.get().getAccount().getRealName());
        tenderCommitVo.getApplyConstactsPhone().set(BaseApplication.get().getAccount().getMobile());
        tenderCommitVo.getApplyCompanyName().set(BaseApplication.get().getCompanyEntity().getOrgName());
        tenderCommitVo.getProjectQuote().set(tenderCommitBinding.tvBudget.getText().toString().trim());
        tenderCommitVo.getBudgetUnit().set(tenderCommitBinding.tvBudgetUnit.getText().toString().trim());
        tenderCommitVo.getDescription().set(tenderCommitBinding.etPlan.getText().toString().trim());

        tenderRepo.doSetCommitTender(tenderCommitVo).observe(lifecycleOwner, tenderBean -> {
            createTenderLiveData.setValue(tenderBean);
        });
//        tenderCommitVo.getPictures().set();
    }

    /**
     * 再次报价
     * @param mTaskApplyEntity
     */
    public void doSetAgainData(TaskApplyEntity mTaskApplyEntity) {
        mPublishId = mTaskApplyEntity.getShopTaskPublishId();
        tenderCommitBinding.tvBudget.setText(mTaskApplyEntity.getProjectQuote() + "");
        tenderCommitBinding.tvBudgetUnit.setText(mTaskApplyEntity.getBudgetUnit());
        tenderCommitBinding.etPlan.setText(mTaskApplyEntity.getDescription());
    }
}
