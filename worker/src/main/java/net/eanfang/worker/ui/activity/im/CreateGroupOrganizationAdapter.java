package net.eanfang.worker.ui.activity.im;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.TemplateBean;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by O u r on 2018/11/15.
 */

public class CreateGroupOrganizationAdapter extends BaseQuickAdapter<TemplateBean, BaseViewHolder> {

    //子adapter的集合
    private List<OrganizationPersonAdapter> mAdapterList;
    private List<RecyclerView> mRecyclerViewList;
    private List<TemplateBean.Preson> mSeletePersonList = new ArrayList<>();
    private OrganizationPersonAdapter mAdapter;

    public CreateGroupOrganizationAdapter(int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean item) {
        helper.setText(R.id.tv_company_name, item.getOrgName() + "(" + item.getPresons().size() + ")");


        if (item.getPresons().size() > 0) {

            if (mAdapterList == null) mAdapterList = new ArrayList<>();
            if (mRecyclerViewList == null) mRecyclerViewList = new ArrayList<>();

            helper.setVisible(R.id.cb_all_checked, true);
            RecyclerView recyclerView = helper.getView(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            mAdapter = new OrganizationPersonAdapter(1);
            mAdapter.bindToRecyclerView(recyclerView);
            mAdapter.setNewData(item.getPresons());

            helper.addOnClickListener(R.id.cb_all_checked);
            helper.addOnClickListener(R.id.rl_parent);

            mAdapterList.add(mAdapter);
            mRecyclerViewList.add(recyclerView);

            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    TemplateBean.Preson preson = (TemplateBean.Preson) adapter.getData().get(position);

                    preson.setChecked(!preson.isChecked());

                    if (preson.isChecked()) {
                        mSeletePersonList.add(preson);
                    } else {
                        mSeletePersonList.remove(preson);
                    }

                    adapter.notifyItemChanged(position);
                }
            });

        } else {
            helper.getView(R.id.recycler_view).setVisibility(View.GONE);
            helper.setVisible(R.id.cb_all_checked, false);
        }

        if (item.isChecked()) {
            ((CheckBox) helper.getView(R.id.cb_all_checked)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_all_checked)).setChecked(false);
        }
    }

    /**
     * 全选 全不选对外提供的方法
     *
     * @param position
     * @param isCheckAll
     */
    public void checkedAll(int position, boolean isCheckAll) {
        TemplateBean bean = (TemplateBean) getData().get(position);
        bean.setChecked(isCheckAll);

        for (TemplateBean.Preson preson : bean.getPresons()) {
            preson.setChecked(isCheckAll);
        }
        if (isCheckAll) {
            mSeletePersonList.addAll(bean.getPresons());
        } else {
            mSeletePersonList.removeAll(bean.getPresons());
        }

        mAdapterList.get(position).notifyItemChanged(position);
        notifyItemChanged(position);
    }

    /**
     * @return 返回选中的集合
     */
    public List<TemplateBean.Preson> getSeletePerson() {
        return mSeletePersonList;
    }

    /**
     * 显示和影藏
     *
     * @param position
     */
    public void isShow(int position , ImageView view) {
        //先判断adapter是不是有元素
        if (mAdapterList != null && mAdapterList.get(position).getData().size() > 0) {
            if (mRecyclerViewList.get(position).getVisibility() == View.VISIBLE) {
                mRecyclerViewList.get(position).setVisibility(View.GONE);
                view.setImageDrawable(view.getContext().getResources().getDrawable(com.eanfang.R.drawable.ic_two_open));
            } else {
                mRecyclerViewList.get(position).setVisibility(View.VISIBLE);
                view.setImageDrawable(view.getContext().getResources().getDrawable(com.eanfang.R.drawable.ic_two_close));
            }
        }
    }

}
