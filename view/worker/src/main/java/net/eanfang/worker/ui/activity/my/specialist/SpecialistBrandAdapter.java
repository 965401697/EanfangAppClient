package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.biz.model.entity.BaseDataEntity;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Our on 2019/1/15.
 */

public class SpecialistBrandAdapter extends RecyclerView.Adapter<SpecialistBrandAdapter.ViewHolder> {

    private Context mContext;

    private List<BaseDataEntity> mDataList = new ArrayList<>();

    private onCheckClickListener monCheckClickListener;

    public SpecialistBrandAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_brand, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemCount() == (position + 1)) {
            holder.tvName.setBackground(mContext.getResources().getDrawable(R.mipmap.add_brand));
//            holder.ivSub.setVisibility(View.GONE);
            holder.tvName.setText("");
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    monCheckClickListener.onAddClickListener();
                }
            });
        } else {
            BaseDataEntity data = mDataList.get(position);
            holder.tvName.setText(data.getDataName());
//            holder.ivSub.setVisibility(View.VISIBLE);
            holder.tvName.setBackground(mContext.getResources().getDrawable(R.drawable.shape_add_brand_checked));
            holder.tvName.setOnClickListener(null);
//            holder.ivSub.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    monCheckClickListener.onSubClickListener(data);
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size() + 1;
    }


    public void setNewData(List<BaseDataEntity> list) {
        if (mDataList.size() > 0) {
            mDataList.clear();
        }
        mDataList.addAll(list);
        this.notifyDataSetChanged();
    }

    public void setData(List<BaseDataEntity> list) {
        mDataList.addAll(list);
        this.notifyDataSetChanged();
    }

    public List<BaseDataEntity> getData() {
        return this.mDataList;
    }

    public void add(BaseDataEntity brand) {
        mDataList.add(brand);
        this.notifyDataSetChanged();
    }


    public void remove(BaseDataEntity brand) {
        mDataList.remove(brand);
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
//        public ImageView ivSub;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
//            ivSub = itemView.findViewById(R.id.iv_sub);
        }
    }


    public void setOnCheckClickListener(onCheckClickListener monCheckClickListener) {
        this.monCheckClickListener = monCheckClickListener;
    }

    public interface onCheckClickListener {
        void onAddClickListener();

        void onSubClickListener(BaseDataEntity data);
    }
}
