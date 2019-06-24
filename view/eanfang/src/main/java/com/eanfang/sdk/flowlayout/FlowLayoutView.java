package com.eanfang.sdk.flowlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eanfang.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

public class FlowLayoutView {
    private Context context;
    private List<String> list;
    private TagFlowLayout tagFlowLayout;

    public View init(Context context, List<String> list) {
        this.context=context;
        this.list = list;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_flow, null);
        tagFlowLayout = view.findViewById(R.id.tagflowlayout);
        tagFlowLayout.setAdapter(mModeAdapter);
        tagFlowLayout.setOnTagClickListener(onTagClickListener);
        tagFlowLayout.setOnSelectListener(onSelectListener);
        tagFlowLayout.getSelectedList();
        return view;
    }

    TagAdapter<String> mModeAdapter = new TagAdapter<String>(list) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_trouble_result_item, tagFlowLayout, false);
            tv.setText(s);
            return tv;
        }
    };

    TagFlowLayout.OnTagClickListener onTagClickListener=new TagFlowLayout.OnTagClickListener() {
        @Override
        public boolean onTagClick(View view, int position, FlowLayout parent) {
            Toast.makeText(context, list.get(position), Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    TagFlowLayout.OnSelectListener onSelectListener=new TagFlowLayout.OnSelectListener() {
        @Override
        public void onSelected(Set<Integer> selectPosSet) {
            Toast.makeText(context, "choose:" + selectPosSet.toString(), Toast.LENGTH_SHORT).show();
        }
    };
}
