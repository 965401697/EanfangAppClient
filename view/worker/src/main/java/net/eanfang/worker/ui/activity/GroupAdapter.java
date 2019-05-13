package net.eanfang.worker.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.model.sys.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.ChildAdapter;
import net.eanfang.worker.ui.interfaces.AreaCheckChangeListener;
import net.eanfang.worker.ui.widget.CustomExpandableListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private AreaCheckChangeListener mListener;
    private Map<Integer, FirstHolder> mTextViews;

    public GroupAdapter(Context context, List<BaseDataEntity> mListData) {
        this.mListData = mListData;
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
        mTextViews = new HashMap<>();
    }

    @Override
    public int getGroupCount() {
        return mListData != null ? mListData.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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
            holder.tv = ((TextView) convertView.findViewById(R.id.tv));
            holder.tv_cb = ((CheckBox) convertView.findViewById(R.id.tv_cb));
            holder.img_area = ((ImageView) convertView.findViewById(R.id.img_area));
            convertView.setTag(holder);
        } else {
            holder = (FirstHolder) convertView.getTag();
        }
        final FirstHolder finalHolder = holder;
        mTextViews.put(groupPosition, holder);
        if (isAuth) {
            holder.tv_cb.setEnabled(false);
            holder.tv_cb.setClickable(false);
        } else {
            holder.tv_cb.setOnClickListener(v -> {
                boolean isChecked = finalHolder.tv_cb.isChecked();
                finalHolder.tv_cb.setText(isChecked ? "取消全选" : "全选");
                mListData.get(groupPosition).setCheck(isChecked);
                if (mListData.get(groupPosition).getChildren() != null) {
                    for (int i = 0; i < mListData.get(groupPosition).getChildren().size(); i++) {
                        BaseDataEntity secondModel = mListData.get(groupPosition).getChildren().get(i);
                        secondModel.setCheck(isChecked);
                        for (int j = 0; j < secondModel.getChildren().size(); j++) {
                            BaseDataEntity thirdModel = secondModel.getChildren().get(j);
                            thirdModel.setCheck(isChecked);
                        }
                    }
                }
                mListener.onCheckAreaChange(groupPosition, -1, -1, isChecked);
                notifyDataSetChanged();
            });
        }
        finalHolder.tv_cb.setChecked(mListData.get(groupPosition).isCheck());
        holder.img_area.setSelected(isExpanded);
        holder.tv_cb.setChecked(mListData.get(groupPosition).isCheck());
        holder.tv.setText(mListData.get(groupPosition).getDataName());
//        if (mListData.get(groupPosition) != null) {
        mListener.onCheckAreaChange(groupPosition, -1, -1, mListData.get(groupPosition).isCheck());
//        }
//        holder.tv.setText(mListData.get(groupPosition).getDataName() + "(" + mListData.get(groupPosition).getCheckChildren() + "/" + mListData.get(groupPosition).getChildrenSize() + ")");

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
            lv.setGroupIndicator(null);
            lv.setDivider(null);
        }

        ChildAdapter secondAdapter = new ChildAdapter(context, mListData.get(groupPosition).getChildren(), groupPosition, isAuth);
        lv.setAdapter(secondAdapter);
        secondAdapter.setListener(mListener);
        return lv;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setListener(AreaCheckChangeListener listener) {
        this.mListener = listener;
    }

    public class FirstHolder {
        public TextView tv;
        public CheckBox tv_cb;
        public ImageView img_area;
    }

    public FirstHolder getChangeTextView(int position) {
        if (mTextViews.keySet().contains(position)) {
            return mTextViews.get(position);
        }
        return null;
    }
}
