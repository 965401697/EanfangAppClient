package net.eanfang.worker.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eanfang.model.sys.OrgEntity;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/19  14:31
 * @email houzhongzhou@yeah.net
 * @desc 一级
 */

public class ParenGroupAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<OrgEntity> mData;
    ViewHolder holder = null;

    class ViewHolder {
        private TextView title;
//        private ImageView imageView;
    }

    public ParenGroupAdapter(Context mContext, List<OrgEntity> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getGroupCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public OrgEntity getChild(int groupPosition, int childPosition) {
        return mData.get(groupPosition).getChildren().get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grandpa_item, null);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.tv_grandpa);
//            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_img);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        //区分箭头往上还是
//        if (isExpanded) {
//            holder.imageView.setImageResource(R.drawable.ic_down);
//        } else {
//            holder.imageView.setImageResource(R.drawable.ic_up);
//        }
        holder.title.setText(mData.get(groupPosition).getOrgName());
        return convertView;
    }

    @Override
    public View getChildView(final int parentPosition, final int childPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        final ExpandableListView childListView = getExpandableListView();
        //获取子菜单的数据
        final List<OrgEntity> childData = new ArrayList<OrgEntity>();
        final OrgEntity bean = getChild(parentPosition, childPosition);
        childData.add(bean);
        ChildGroupAdapter adapter = new ChildGroupAdapter(mContext, childData, parentPosition);
        childListView.setAdapter(adapter);

        /**
         * 点击最小级菜单，调用该方法
         * */
        childListView.setOnChildClickListener((arg0, arg1, groupIndex, childIndex, arg4) -> {
            if (mListener != null) {
                mListener.onclick(parentPosition, childPosition, childIndex);
                //点击三级菜单，跳转到编辑菜单界面
                Toast.makeText(mContext, "content 你点的位置是:  " + "parentPosition>>" + parentPosition +
                        "childPosition>>" + childPosition + "childIndex>>" + childIndex, Toast.LENGTH_SHORT).show();
            }
            return false;
        });
        /**
         *子ExpandableListView展开时，因为group只有一项，所以子ExpandableListView的总高度=
         * （子ExpandableListView的child数量 + 1 ）* 每一项的高度
         * */
        childListView.setOnGroupExpandListener(groupPosition -> {
            Log.e("xxx", groupPosition + "onGroupExpand>>");
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (bean.getStaff().size() + 1) * (int) mContext
                            .getResources().getDimension(R.dimen.parent_list_height));
            childListView.setLayoutParams(lp);
        });
        /**
         *子ExpandableListView关闭时，此时只剩下group这一项，
         * 所以子ExpandableListView的总高度即为一项的高度
         * */
        childListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.e("xxx", groupPosition + ">>onGroupCollapse");
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
                        .getResources().getDimension(R.dimen.parent_list_height));
                childListView.setLayoutParams(lp);
//                holder.upImg.setImageResource(R.drawable.up);
            }
        });
        /**
         * 在这里对二级菜单的点击事件进行操作
         */
        childListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int Position, long id) {
//                if(isClick){
//                    holder.mUpImg.setImageResource(R.drawable.dowm);
//                    isClick = false;
//                }else{
//                    holder.mUpImg.setImageResource(R.drawable.up);
//                    isClick = true;
//                }
                Log.e("Xxx", "恭喜你,点击了" + parentPosition + "childpos>>>" + childPosition);
                return false;
            }
        });
        return childListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public ExpandableListView getExpandableListView() {
        ExpandableListView mExpandableListView = new ExpandableListView(
                mContext);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
                .getResources().getDimension(
                        R.dimen.parent_list_height));
        mExpandableListView.setLayoutParams(lp);
//        // 取消group项的分割线
//        mExpandableListView.setDividerHeight(0);
//        // 取消child项的分割线
//        mExpandableListView.setChildDivider(null);
//        // 取消展开折叠的指示图标
//        mExpandableListView.setGroupIndicator(null);
        return mExpandableListView;
    }

    /**
     * 接口回调
     */
    public interface OnExpandClickListener {
        void onclick(int parentPosition, int childPosition, int childIndex);
    }

    OnExpandClickListener mListener;

    public void setOnChildListener(OnExpandClickListener listener) {
        this.mListener = listener;
    }
}
