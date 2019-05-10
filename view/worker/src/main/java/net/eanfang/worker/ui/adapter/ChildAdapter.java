package net.eanfang.worker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.eanfang.model.sys.BaseDataEntity;

import net.eanfang.worker.R;

import java.util.List;


/**
 * Created by MrHou
 *
 * @on 2018/1/19  15:54
 * @email houzhongzhou@yeah.net
 * @desc 子布局
 */

public class ChildAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<BaseDataEntity> mDatas;
    int mPosition;
    private LayoutInflater mInflate;

    public ChildAdapter(Context mContext, List<BaseDataEntity> mDatas, int mPosition) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mPosition = mPosition;
        this.mInflate = LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDatas.get(groupPosition).getChildren() != null
                ? mDatas.get(groupPosition).getChildren().size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDatas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDatas.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mDatas.get(groupPosition).getDataId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mDatas.get(groupPosition).getChildren().get(childPosition).getDataId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 主菜单布局
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflate.inflate(R.layout.item_expand_lv_third, parent, false);
            holder.tv = convertView.findViewById(R.id.tv);
            holder.cb = convertView.findViewById(R.id.cb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ViewHolder finalHolder = holder;
        holder.cb.setOnClickListener(v -> {
            boolean isChecked = finalHolder.cb.isChecked();
            mDatas.get(groupPosition).setCheck(isChecked);
            for (int i = 0; i < mDatas.get(groupPosition).getChildren().size(); i++) {
                BaseDataEntity thirdModel = mDatas.get(groupPosition).getChildren().get(i);
                thirdModel.setCheck(isChecked);
            }
            notifyDataSetChanged();
        });
        holder.cb.setChecked(mDatas.get(groupPosition).isCheck());

        holder.tv.setText(mDatas.get(groupPosition).getDataName());
        return convertView;
    }

    class ViewHolder {
        TextView tv;
        CheckBox cb;
    }

    /**
     * 子菜单布局
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ChildHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.item_expand_lv_third, null);
            holder = new ChildHolder();
            holder.tv = view.findViewById(R.id.tv);
            holder.cb = view.findViewById(R.id.cb);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        holder.tv.setText(mDatas.get(groupPosition).getChildren().get(childPosition).getDataName());
        final ChildHolder thirdHolder = holder;
        thirdHolder.cb.setOnClickListener(v -> {
            boolean isChecked = thirdHolder.cb.isChecked();
            mDatas.get(groupPosition).getChildren().get(childPosition).setCheck(isChecked);
        });
        thirdHolder.cb.setChecked(mDatas.get(groupPosition).getChildren().get(childPosition).isCheck());
        return view;
    }

    class ChildHolder {
        TextView tv;
        CheckBox cb;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
