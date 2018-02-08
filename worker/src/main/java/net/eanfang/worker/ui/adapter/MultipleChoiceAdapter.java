package net.eanfang.worker.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/26  11:12
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MultipleChoiceAdapter extends RecyclerView.Adapter<MultipleChoiceAdapter.InternalViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<BaseDataEntity> data;
    private OnItemClickListener onItemClickListener;

    public MultipleChoiceAdapter(Context context, List<BaseDataEntity> data) {
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    public String getItem(int position) {
        return data.get(position).getDataName();
    }

    @Override
    public InternalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InternalViewHolder(layoutInflater.inflate(R.layout.item_sys_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(InternalViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position, getItemId(position));
            }
            if (data.get(position).isCheck()) {
                holder.checkBox.setImageResource(R.mipmap.ic_checked);
            } else {
                holder.checkBox.setImageResource(R.mipmap.ic_uncheck);
            }

        });
        if (data.get(position).isCheck()) {
            holder.checkBox.setImageResource(R.mipmap.ic_checked);
        } else {
            holder.checkBox.setImageResource(R.mipmap.ic_uncheck);
        }

        holder.tvTitle.setText(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getDataId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(@NonNull OnItemClickListener listener) {
        onItemClickListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, long id);
    }

    class InternalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkBox)
        ImageView checkBox;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        InternalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

