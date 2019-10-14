package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.biz.model.entity.OrgEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.SwitchCompanyListAdapter;

import java.util.List;

import cn.qqtheme.framework.util.ScreenUtils;

/**
 * @author guanluocang
 * @data 2019/9/20  17:45
 * @description 实时监控选择公司
 */


public class MonitorSelectCompanyView extends PopupWindow {
    RecyclerView revCompanyList;
    private Activity mContext;
    private setCheckItemCompany itemCompany;
    private SwitchCompanyListAdapter adapter;

    public interface setCheckItemCompany {
        void getItemName(String name, Long companyId);
    }

    public MonitorSelectCompanyView(Activity context, List<OrgEntity> mList, setCheckItemCompany itemCompany) {
        super(context);
        this.mContext = context;
        this.itemCompany = itemCompany;
        View view = LayoutInflater.from(context).inflate(R.layout.view_company_list, null);
        revCompanyList = view.findViewById(R.id.rev_company_list);
        adapter = new SwitchCompanyListAdapter();
        adapter.bindToRecyclerView(revCompanyList);
        revCompanyList.setLayoutManager(new LinearLayoutManager(mContext));
        setBackgroundDrawable(new ColorDrawable(0x70000000));
        int width = (ScreenUtils.widthPixels(context));
        int height = (ScreenUtils.heightPixels(context) * 3 / 4);
        setWidth(width);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        initAdapter(mList);
    }

    private void initAdapter(List<OrgEntity> beanList) {
        backgroundAlpha(0.7f);

        adapter.setNewData(beanList);
        revCompanyList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                SwitchCompany(beanList, position, beanList.get(position).getOrgId());
            }
        });
    }

    /**
     * @param companyid company
     */
    private void SwitchCompany(List<OrgEntity> beanList, int position, Long companyid) {
        dismiss();
        itemCompany.getItemName(beanList.get(position).getOrgName(), companyid);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        //0.0-1.0+
        lp.alpha = bgAlpha;
        mContext.getWindow().setAttributes(lp);
    }

}
