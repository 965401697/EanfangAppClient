package net.eanfang.worker.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.ChildAdapter;
import net.eanfang.worker.ui.widget.CustomExpandableListView;

import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/27  13:37
 * @email houzhongzhou@yeah.net
 * @desc 父布局
 */

public class GroupAdapter extends BaseExpandableListAdapter {
    private List<BaseDataEntity> mListData;
    private LayoutInflater mInflate;
    private Context context;
    public int firstPostion;
    public boolean isAuth = false;

    public GroupAdapter(Context context, List<BaseDataEntity> mListData) {
        this.mListData = mListData;
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return mListData != null ? mListData.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListData.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListData.get(groupPosition);
    }

    @Override
    public BaseDataEntity getChild(int groupPosition, int childPosition) {
        return mListData.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mListData.get(groupPosition).getDataId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mListData.get(groupPosition).getChildren().get(childPosition).getDataId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 第一级菜单适配器布局
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        firstPostion = groupPosition;
        FirstHolder holder = null;
        if (convertView == null) {
            holder = new FirstHolder();
            convertView = mInflate.inflate(R.layout.item_expand_lv_first, parent, false);
            holder.tv = convertView.findViewById(R.id.tv);
            holder.cb = convertView.findViewById(R.id.cb);
            convertView.setTag(holder);
        } else {
            holder = (FirstHolder) convertView.getTag();
        }
        holder.tv.setText(mListData.get(groupPosition).getDataName());

        final FirstHolder finalHolder = holder;
        if (isAuth) {
            finalHolder.cb.setEnabled(false);
        } else {
            finalHolder.cb.setOnClickListener(v -> {
                boolean isChecked = finalHolder.cb.isChecked();
                mListData.get(groupPosition).setCheck(isChecked);
                for (int i = 0; i < mListData.get(groupPosition).getChildren().size(); i++) {
                    BaseDataEntity secondModel = mListData.get(groupPosition).getChildren().get(i);
                    secondModel.setCheck(isChecked);
                    for (int j = 0; j < secondModel.getChildren().size(); j++) {
                        BaseDataEntity thirdModel = secondModel.getChildren().get(j);
                        thirdModel.setCheck(isChecked);
                    }
                }
                notifyDataSetChanged();
            });
        }
        finalHolder.cb.setChecked(mListData.get(groupPosition).isCheck());

        return convertView;
    }

    /**
     * 第二级菜单式适配
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CustomExpandableListView lv = ((CustomExpandableListView) convertView);
        if (convertView == null) {
            lv = new CustomExpandableListView(context);
        }

        ChildAdapter secondAdapter = new ChildAdapter(context, mListData.get(groupPosition).getChildren(), groupPosition);
        lv.setAdapter(secondAdapter);
        return lv;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class FirstHolder {
        TextView tv;
        CheckBox cb;
    }


}
