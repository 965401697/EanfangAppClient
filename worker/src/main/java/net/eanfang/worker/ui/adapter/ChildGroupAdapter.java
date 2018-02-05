package net.eanfang.worker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.yaf.sys.entity.OrgEntity;

import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/19  14:32
 * @email houzhongzhou@yeah.net
 * @desc 三级
 */

public class ChildGroupAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<OrgEntity> mDatas;
    int mPosition;

    public ChildGroupAdapter(Context mContext, List<OrgEntity> mDatas, int mPosition) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mPosition = mPosition;
    }

    @Override
    public int getGroupCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDatas.get(groupPosition).getStaff() != null
                ? mDatas.get(groupPosition).getStaff().size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDatas.get(groupPosition).getChildren().get(groupPosition).getStaff().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDatas.get(groupPosition).getChildren().get(groupPosition).getStaff().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.father_item, null);
            holder = new ViewHolder();
            holder.mChildGroupTV = (TextView) view.findViewById(R.id.tv_father);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mChildGroupTV.setText(mDatas.get(groupPosition).getOrgName());
        return view;
    }

    class ViewHolder {

        private TextView mChildGroupTV;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ChildHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.son_item, null);
            holder = new ChildHolder();
            holder.childChildTV = (TextView) view.findViewById(R.id.tv_son);
            holder.firstName = (TextView) view.findViewById(R.id.tv_first_name);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }

        String first_name = mDatas.get(groupPosition).getStaff().get(childPosition).getAccountEntity().getRealName();
        if (first_name.length() == 2) {
            holder.firstName.setText(first_name);
        } else if (first_name.length() == 3) {
            first_name = first_name.substring(1, 2);
            holder.firstName.setText(first_name);
        } else if (first_name.length() == 4) {
            first_name = first_name.substring(2, 3);
            holder.firstName.setText(first_name);
        }
        holder.childChildTV.setText(mDatas.get(groupPosition).getStaff().get(childPosition).getAccountEntity().getRealName());
        return view;
    }

    class ChildHolder {

        private TextView childChildTV, firstName;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
