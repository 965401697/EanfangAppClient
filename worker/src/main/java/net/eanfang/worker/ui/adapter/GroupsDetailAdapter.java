package net.eanfang.worker.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.model.GroupDetailBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import java.util.ArrayList;

/**
 * Created by O u r on 2018/4/18.
 */

//public class GroupsDetailAdapter extends BaseQuickAdapter<GroupDetailBean.ListBean, BaseViewHolder> {
//
//    private ArrayList<GroupDetailBean.ListBean> mList;
//    private boolean mIsOwn;//是不是群主
//
//    public GroupsDetailAdapter(int layoutResId, ArrayList<GroupDetailBean.ListBean> list, boolean isOwn) {
//        super(layoutResId);
//        this.mList = list;
//        this.mIsOwn = isOwn;
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, GroupDetailBean.ListBean item) {
//        if (item.getAccountEntity() != null) {
//            ((SimpleDraweeView) helper.getView(R.id.iv_icon)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getAccountEntity().getAvatar()));
//        }
//        if (getData().size() - 1 == helper.getAdapterPosition()) {
//
//            if (!mIsOwn) {
//                ((SimpleDraweeView) helper.getView(R.id.iv_icon)).setVisibility(View.GONE);
//                return;
//            } else {
//                ((SimpleDraweeView) helper.getView(R.id.iv_icon)).setImageResource(R.mipmap.ic_btn_deleteperson);
//                helper.setText(R.id.tv_name, "");
//            }
//        } else if (getData().size() - 2 == helper.getAdapterPosition()) {
//
//            ((SimpleDraweeView) helper.getView(R.id.iv_icon)).setImageResource(R.mipmap.ic_btn_addperson);
//            helper.setText(R.id.tv_name, "");
//
//        } else {
//            if (item.getAccountEntity() != null)
//                helper.setText(R.id.tv_name, item.getAccountEntity().getNickName());
//        }
//
//    }
//
//}

public class GroupsDetailAdapter extends BaseAdapter {

    private ArrayList<GroupDetailBean.ListBean> mList;
    private boolean mIsOwn;//是不是群主
    private Context mContext;

    public GroupsDetailAdapter(Context context, ArrayList<GroupDetailBean.ListBean> list, boolean isOwn) {

        this.mList = list;
        this.mIsOwn = isOwn;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
            holder = new ViewHolder();

            holder.iv_icon = (SimpleDraweeView) convertView.findViewById(R.id.iv_icon);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mList.get(position).getAccountEntity() != null) {
            holder.iv_icon.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + mList.get(position).getAccountEntity().getAvatar()));
        }

        if (mList.size() - 1 == position && mIsOwn) {

            holder.iv_icon.setVisibility(View.VISIBLE);
            holder.iv_icon.setImageResource(R.mipmap.ic_btn_deleteperson);
            holder.tv_name.setText("");

        }

        if (mIsOwn) {
            if (mList.size() - 2 == position) {

                holder.iv_icon.setImageResource(R.mipmap.ic_btn_addperson);
                holder.tv_name.setText("");

            }
        } else {
            if (mList.size() - 1 == position) {

                holder.iv_icon.setImageResource(R.mipmap.ic_btn_addperson);
                holder.tv_name.setText("");

            }
        }


        if (mList.get(position).getAccountEntity() != null) {
            holder.tv_name.setText(mList.get(position).getAccountEntity().getNickName());
        }

        return convertView;

    }
}

class ViewHolder {
    SimpleDraweeView iv_icon;
    TextView tv_name;
}

