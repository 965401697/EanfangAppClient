package net.eanfang.client.ui.activity.im;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.TemplateBean;

import net.eanfang.client.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by O u r on 2018/11/15.
 */

public class CreateGroupOrganizationAdapter extends BaseQuickAdapter<TemplateBean, BaseViewHolder> {

    private Map<Integer, OrganizationPersonAdapter> organizationPersonAdapterMap;
    //子adapter的集合
    private Map<Integer, RecyclerView> recyclerViewMap;

    private List<TemplateBean.Preson> mSeletePersonList = new ArrayList<>();
    private OrganizationPersonAdapter mAdapter;

    private SetAutoCheckedParentListener mSetAutoCheckedParentListener;

    public CreateGroupOrganizationAdapter(int layoutResId, SetAutoCheckedParentListener setAutoCheckedParentListener) {
        super(layoutResId);
        this.mSetAutoCheckedParentListener = setAutoCheckedParentListener;

    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean item) {


        if (item.getPresons().size() > 0) {
            helper.setVisible(R.id.tv_company_name, true);
            helper.setText(R.id.tv_company_name, item.getOrgName() + "(" + item.getPresons().size() + ")");

            if (organizationPersonAdapterMap == null) {
                organizationPersonAdapterMap = new HashMap<Integer, OrganizationPersonAdapter>();
            }
            if (recyclerViewMap == null) {
                recyclerViewMap = new HashMap<Integer, RecyclerView>();
            }

            helper.setVisible(R.id.cb_all_checked, true);
            RecyclerView recyclerView = helper.getView(R.id.recycler_view);
            recyclerView.setTag(helper.getAdapterPosition());
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            mAdapter = new OrganizationPersonAdapter(1);
            mAdapter.bindToRecyclerView(recyclerView);
            mAdapter.setNewData(item.getPresons());

            helper.addOnClickListener(R.id.cb_all_checked);
            helper.addOnClickListener(R.id.rl_parent);


            organizationPersonAdapterMap.put(helper.getAdapterPosition(), mAdapter);
            recyclerViewMap.put(helper.getAdapterPosition(), recyclerView);

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
                    //设置外层的adapter的选择状态
                    // TODO: 2018/11/19 可以进行 更进一步的优化
                    mSetAutoCheckedParentListener.autoCheckedParentListener(CreateGroupOrganizationAdapter.this, helper.getAdapterPosition(), item.getPresons());


                    adapter.notifyItemChanged(position);
                }
            });

        } else {
            helper.setVisible(R.id.tv_company_name, false);
            helper.getView(R.id.recycler_view).setVisibility(View.GONE);
            helper.setVisible(R.id.cb_all_checked, false);
        }

        if (mSeletePersonList.containsAll(item.getPresons())) {
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
        TemplateBean bean = getData().get(position);
        bean.setChecked(isCheckAll);

        for (TemplateBean.Preson preson : bean.getPresons()) {
            preson.setChecked(isCheckAll);
        }
        if (isCheckAll) {
            for (TemplateBean.Preson p:bean.getPresons()){
                if(!mSeletePersonList.contains(p)){
                    mSeletePersonList.add(p);
                }
            }
        } else {
            mSeletePersonList.removeAll(bean.getPresons());
        }

        organizationPersonAdapterMap.get(position).notifyItemChanged(position);
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
    public void isShow(int position, ImageView view) {
        //先判断adapter是不是有元素
        if (organizationPersonAdapterMap != null && organizationPersonAdapterMap.get(position).getData().size() > 0) {
            if (recyclerViewMap.get(position).getVisibility() == View.VISIBLE) {
                recyclerViewMap.get(position).setVisibility(View.GONE);
                view.setImageDrawable(view.getContext().getResources().getDrawable(com.eanfang.R.drawable.ic_two_close));
            } else {
                recyclerViewMap.get(position).setVisibility(View.VISIBLE);
                view.setImageDrawable(view.getContext().getResources().getDrawable(com.eanfang.R.drawable.ic_two_open));
            }
        }
    }

    /**
     * 子item的全选和不全选的 parent iten listenter
     */
    public interface SetAutoCheckedParentListener {
        void autoCheckedParentListener(CreateGroupOrganizationAdapter adapter, int position, List<TemplateBean.Preson> list);
    }

}
