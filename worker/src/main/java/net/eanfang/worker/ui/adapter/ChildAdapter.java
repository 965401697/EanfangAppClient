package net.eanfang.worker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.interfaces.AreaCheckChangeListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private AreaCheckChangeListener mListener;
    private Map<Integer, ViewHolder> mTextViews;

    public ChildAdapter(Context mContext, List<BaseDataEntity> mDatas, int mPosition) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mPosition = mPosition;
        this.mInflate = LayoutInflater.from(mContext);
        mTextViews = new HashMap<>();
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
            convertView = mInflate.inflate(R.layout.item_expand_lv_second, parent, false);
            holder.tv = ((TextView) convertView.findViewById(R.id.tv));
            holder.cb = ((CheckBox) convertView.findViewById(R.id.cb));
            holder.cb_img = ((CheckBox) convertView.findViewById(R.id.cb_img));
            holder.img_area = ((ImageView) convertView.findViewById(R.id.img_area));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        mTextViews.put(mPosition * 1000 + groupPosition, holder);
        List<BaseDataEntity> entities = mDatas.get(groupPosition).getChildren();
        ViewHolder finalHolder = holder;
        holder.cb.setOnClickListener(v -> {
            boolean isChecked = finalHolder.cb.isChecked();
            if (entities.size() > 0) {
                finalHolder.cb.setText(isChecked ? "取消全选" : "全选");
                finalHolder.cb.setVisibility(View.VISIBLE);
                finalHolder.cb_img.setVisibility(View.GONE);
            } else {
                finalHolder.cb.setVisibility(View.GONE);
                finalHolder.cb_img.setVisibility(View.VISIBLE);
                finalHolder.cb_img.setChecked(isChecked);
            }
            mDatas.get(groupPosition).setCheck(isChecked);
            for (int i = 0; i < entities.size(); i++) {
                BaseDataEntity thirdModel = entities.get(i);
                thirdModel.setCheck(isChecked);
            }
            mListener.onCheckAreaChange(mPosition, groupPosition, -1, isChecked);
            notifyDataSetChanged();
        });
        holder.tv.setText(mDatas.get(groupPosition).getDataName());
        if (entities.size() > 0) {
            holder.img_area.setVisibility(View.VISIBLE);
            holder.cb.setChecked(mDatas.get(groupPosition).isCheck());
            holder.img_area.setSelected(isExpanded);
            holder.cb.setText(mDatas.get(groupPosition).isCheck() ? "取消全选" : "全选");
            holder.cb.setVisibility(View.VISIBLE);
            holder.cb_img.setVisibility(View.GONE);
        } else {
            holder.img_area.setVisibility(View.INVISIBLE);
            holder.cb_img.setVisibility(View.VISIBLE);
            holder.cb.setVisibility(View.GONE);
            holder.cb_img.setChecked(mDatas.get(groupPosition).isCheck());
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv;
        CheckBox cb;
        CheckBox cb_img;
        ImageView img_area;

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
            holder.tv = ((TextView) view.findViewById(R.id.tv));
            holder.cb = ((CheckBox) view.findViewById(R.id.cb));
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        holder.tv.setText(mDatas.get(groupPosition).getChildren().get(childPosition).getDataName());
        final ChildHolder thirdHolder = holder;
        thirdHolder.cb.setOnClickListener(v -> {
            boolean isChecked = thirdHolder.cb.isChecked();
            mDatas.get(groupPosition).getChildren().get(childPosition).setCheck(isChecked);
            int checkSize = 0;
            for (BaseDataEntity entity : mDatas.get(groupPosition).getChildren()) {
                if (entity.isCheck()) {
                    checkSize++;
                }
            }
            if (checkSize == mDatas.get(groupPosition).getChildren().size()) {
                mTextViews.get(mPosition * 1000 + groupPosition).cb.setText("取消全选");
                notifyDataSetChanged();
            } else {
                mTextViews.get(mPosition * 1000 + groupPosition).cb.setText("全选");
            }
            mListener.onCheckAreaChange(mPosition, groupPosition, childPosition, isChecked);
        });
        thirdHolder.cb.setChecked(mDatas.get(groupPosition).getChildren().get(childPosition).isCheck());
        return view;
    }

    class ChildHolder {
        TextView tv;
        CheckBox cb;
    }

    public void setListener(AreaCheckChangeListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
