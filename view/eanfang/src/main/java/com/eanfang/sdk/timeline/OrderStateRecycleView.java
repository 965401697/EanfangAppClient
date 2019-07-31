package com.eanfang.sdk.timeline;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.R;
import com.eanfang.biz.model.bean.OrderProgressBean;

import java.util.ArrayList;
import java.util.List;

public class OrderStateRecycleView extends RecyclerView {
    private Context context;
    private List<OrderProgressBean> list;
    private OrderStateAdapter adapter;

    public OrderStateRecycleView(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OrderStateRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public OrderStateRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public void init() {
        list=new ArrayList<>();
        setLayoutManager(new LinearLayoutManager(context));
         adapter=new OrderStateAdapter(R.layout.layout_orderstate,list);
        setAdapter(adapter);
    }
    public void setData(List<OrderProgressBean> list){
        this.list=list;
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
    }
}
