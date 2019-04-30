package net.eanfang.worker.ui.widget;

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
import com.picker.common.util.ScreenUtils;

import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by admin on 2018/5/15.
 */

public class SelectCompanyPop extends PopupWindow {
    private Activity mContext;
    private ListView mLvCompany;
    private List<String> mTimeList;

    public SelectCompanyPop(Activity context, List<String> list, AdapterView.OnItemClickListener listener) {
        this.mContext = context;
        this.mTimeList = list;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pop_select_company, null);

        mLvCompany = view.findViewById(R.id.lv_company);
        MyAdapter adapter = new MyAdapter(context, list);
        mLvCompany.setAdapter(adapter);
        mLvCompany.setOnItemClickListener(listener);
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
            tvName.setText(list.get(i));
            return view;
        }
    }


    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0+
        mContext.getWindow().setAttributes(lp);
    }
}
