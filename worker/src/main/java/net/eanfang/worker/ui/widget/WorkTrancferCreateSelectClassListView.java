package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;


import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.worktransfer.WorkTransferStatusAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/8/7  15:13
 * @decision 创建交接班 选择班次
 */
public class WorkTrancferCreateSelectClassListView extends BaseDialog {
    @BindView(R.id.rev_company_list)
    RecyclerView revCompanyList;
    private Activity mContext;
    private setCheckItemCompany itemCompany;
    private String mStatus = "";
    private List<String> mClassList = new ArrayList<>();

    public WorkTrancferCreateSelectClassListView(Activity context, String status, setCheckItemCompany itemCompany) {
        super(context);
        this.mContext = context;
        this.mStatus = status;
        this.itemCompany = itemCompany;

    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_company_list);
        ButterKnife.bind(this);
        getCompanyAllList();
    }

    /**
     * Get the list of Companies
     */
    private void getCompanyAllList() {
        // 创建班次
        if (mStatus.equals("class")) {
            mClassList = GetConstDataUtils.getWorkTransferCreateClass();
        } else if (mStatus.equals("status")) {// 创建详情状态
            mClassList = GetConstDataUtils.getWokrTransferDetailStatus();
        }
        WorkTransferStatusAdapter adapter = new WorkTransferStatusAdapter();
        revCompanyList.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        revCompanyList.setLayoutManager(new LinearLayoutManager(mContext));
        revCompanyList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String className = "";
                if (mClassList.get(position) != null) {
                    className = mClassList.get(position);
                }
                itemCompany.getItemName(className);
                dismiss();
            }
        });
        adapter.setNewData(mClassList);
        adapter.bindToRecyclerView(revCompanyList);
    }


    public interface setCheckItemCompany {
        void getItemName(String name);
    }

}
