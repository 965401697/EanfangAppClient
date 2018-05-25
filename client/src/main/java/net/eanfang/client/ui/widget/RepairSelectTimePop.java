package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.eanfang.util.ViewHolder;
import com.eanfang.witget.DataSelectPopWindow;
import com.picker.common.util.ScreenUtils;

import net.eanfang.client.R;

import java.util.List;

/**
 * Created by admin on 2018/5/15.
 */

public class RepairSelectTimePop extends PopupWindow {
    private Activity mContext;
    private ListView mLvType;
    private List<String> mTimeList;
    private TextView mTitle;

    public RepairSelectTimePop(Activity context, List<String> list, AdapterView.OnItemClickListener listener) {
        this.mContext = context;
        this.mTimeList = list;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pop_select, null);

        mLvType = (ListView) view.findViewById(R.id.lv_type);
        mTitle = view.findViewById(R.id.tv_title);
        MyAdapter adapter = new MyAdapter(context, list);
        mLvType.setAdapter(adapter);
        mLvType.setOnItemClickListener(listener);
        mTitle.setText("请选择到达时限");
        setBackgroundDrawable(new ColorDrawable(0x70000000));

//        int width = (int) (ScreenUtils.widthPixels(context) * 0.9);
        int height = (int) (ScreenUtils.heightPixels(context) * 0.4);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        update();
    }

    class MyAdapter extends BaseAdapter {

        private Context context;
        private List<String> list;

        public MyAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.layout_data_select_item, viewGroup, false);
            }
            TextView tvName = ViewHolder.get(view, R.id.tv_typeName);
            tvName.setText(list.get(i).toString());
            return view;
        }
    }


    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0+
        mContext.getWindow().setAttributes(lp);
    }
}
