package net.eanfang.worker.viewmodle.tender;

import android.view.View;
import android.widget.PopupWindow;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderDs;
import com.eanfang.biz.rds.sys.repo.tender.TenderRepo;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityWorkerTenderControlBinding;
import net.eanfang.worker.databinding.FragmentTemplateItemListBinding;
import net.eanfang.worker.ui.activity.worksapce.tender.WorkerTenderControlActivity;
import net.eanfang.worker.ui.widget.TenderNoticePop;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/5/27
 * @description 招标用工
 */
public class TenderViewModle extends BaseViewModel {
    private TenderRepo tenderRepo;
    @Getter
    private MutableLiveData<PageBean<IfbOrderEntity>> tenderLiveData;
    @Getter
    private MutableLiveData<PageBean<TaskPublishEntity>> tenderFindLiveData;
    @Setter
    @Getter
    private ActivityWorkerTenderControlBinding workerTenderControlBinding;
    @Setter
    @Getter
    private FragmentTemplateItemListBinding templateItemListBinding;

    private TenderNoticePop popWindow;
    /**
     * notice over
     */
    private String mType = "notice";
    public int mIntType = 0;

    public QueryEntry mNoticeQueryEntry;
    public int mNoticePage = 1;

    public QueryEntry mFindQueryEntry;
    public int mFindPage = 1;


    public TenderViewModle() {
        tenderLiveData = new MutableLiveData<>();
        tenderFindLiveData = new MutableLiveData<>();
        tenderRepo = new TenderRepo(new TenderDs(this));
    }

    /**
     * 招标公告
     */
    public void doClickNotice() {
        if (workerTenderControlBinding.vpTenderList.getCurrentItem() != 0) {
            workerTenderControlBinding.vpTenderList.setCurrentItem(0);
            workerTenderControlBinding.ivCreate.setVisibility(View.GONE);
            doChangeItem(mIntType);
        } else {
            doSetPop();
        }
    }

    /**
     * 招标公告获取数据
     */
    public void doGetNoticeData(int status, int page) {
        if (mNoticeQueryEntry == null) {
            mNoticeQueryEntry = new QueryEntry();
        }
        this.mNoticePage = page;
        //正在公告 0   已过期 1
        mNoticeQueryEntry.getEquals().put("status", status + "");
        mNoticeQueryEntry.setPage(page);
        tenderRepo.doGetTenderNoticeList(mNoticeQueryEntry).observe(lifecycleOwner, tenderBean -> {
            tenderLiveData.setValue(tenderBean);
        });
    }

    /**
     * 用工找活
     */
    public void doClickFind() {
        if (workerTenderControlBinding.vpTenderList.getCurrentItem() != 1) {
            if (popWindow != null && popWindow.isShowing()) {
                popWindow.dismiss();
            }
            workerTenderControlBinding.vpTenderList.setCurrentItem(1);
            workerTenderControlBinding.ivCreate.setVisibility(View.VISIBLE);
            doChangeItem(2);
        }
    }

    /**
     * 用工作找活获取数据
     */
    public void doGetFindData(int page) {
        if (mFindQueryEntry == null) {
            mFindQueryEntry = new QueryEntry();
        }
        this.mFindPage = page;
        mFindQueryEntry.setPage(page);
        tenderRepo.doGetTenderFinderList(mFindQueryEntry).observe(lifecycleOwner, tenderBean -> {
            tenderFindLiveData.setValue(tenderBean);
        });
    }

    private void doChangeItem(int i) {
        //正在公告 0   已过期 1
        if (i == 0) {
            workerTenderControlBinding.ivTenderNotice.setImageResource(R.drawable.ic_tender_notice_pressed_down);
            workerTenderControlBinding.ivTenderFind.setImageResource(R.drawable.ic_tender_find);
        } else if (i == 1) {
            workerTenderControlBinding.ivTenderNotice.setImageResource(R.drawable.ic_tender_notice_pressed_down);
            workerTenderControlBinding.ivTenderFind.setImageResource(R.drawable.ic_tender_find);
        } else {
            workerTenderControlBinding.ivTenderNotice.setImageResource(R.drawable.ic_tender_notice);
            workerTenderControlBinding.ivTenderFind.setImageResource(R.drawable.ic_tender_find_pressed);
//            doGetFindData(mFindPage);
        }
    }

    private void doSetPop() {
        popWindow = new TenderNoticePop((WorkerTenderControlActivity) workerTenderControlBinding.getRoot().getContext(), mType, mOnClieckListener, workerTenderControlBinding.ivTenderNotice.getWidth());
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popWindow.backgroundAlpha((WorkerTenderControlActivity) workerTenderControlBinding.getRoot().getContext(), 1.0f);
            }
        });
        popWindow.doChange(mType);
        popWindow.showAsDropDown(workerTenderControlBinding.ivTenderNotice);
        popWindow.backgroundAlpha((WorkerTenderControlActivity) workerTenderControlBinding.getRoot().getContext(), 0.5f);
        workerTenderControlBinding.ivTenderNotice.setImageResource(R.drawable.ic_tender_notice_pressed_up);
        workerTenderControlBinding.ivTenderFind.setImageResource(R.drawable.ic_tender_find);
    }

    View.OnClickListener mOnClieckListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // 公告中
                case R.id.ll_noticing:
                    mType = "notice";
                    mIntType = 0;
                    doGetNoticeData(mIntType, mNoticePage);
                    doChangeItem(0);
                    popWindow.dismiss();
                    break;
                // 已过期
                case R.id.ll_overing:
                    mType = "over";
                    mIntType = 1;
                    doGetNoticeData(mIntType, mNoticePage);
                    doChangeItem(1);
                    popWindow.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

}
