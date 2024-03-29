package net.eanfang.worker.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.biz.model.entity.SysGroupUserEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

import java.util.ArrayList;

/**
 * Created by O u r on 2018/4/18.
 */
public class GroupsDetailAdapter extends BaseAdapter {

    private ArrayList<SysGroupUserEntity> mList;
    private boolean mIsOwn;//是不是群主
    private Context mContext;

    public GroupsDetailAdapter(Context context, ArrayList<SysGroupUserEntity> list, boolean isOwn) {

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

            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mList.get(position).getAccountEntity() != null) {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + mList.get(position).getAccountEntity().getAvatar()),holder.iv_icon);
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
    CircleImageView iv_icon;
    TextView tv_name;
}

