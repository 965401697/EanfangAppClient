package com.eanfang.sdk.ratingbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.R;

import java.util.List;

public class RatingBarView {

    public View init(Context context, List<RatingBarBean> list){
        View view= LayoutInflater.from(context).inflate(R.layout.layout_ratingbar,null);
        RecyclerView recyclerView=view.findViewById(R.id.recycleview);
        LinearLayoutManager manager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        RatingBarAdapter adapter=new RatingBarAdapter(R.layout.item_ratingbar,list);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
