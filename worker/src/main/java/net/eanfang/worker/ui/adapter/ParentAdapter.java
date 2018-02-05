package net.eanfang.worker.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 合作公司的adapter
 * Created by Administrator on 2017/3/15.
 */

public class ParentAdapter extends BaseQuickAdapter<OrgEntity, BaseViewHolder> {


    public ParentAdapter(List data) {
        super(R.layout.item_group_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrgEntity item) {
        ImageView imageView = helper.getView(R.id.iv_img);
        LinearLayout ll_show = helper.getView(R.id.ll_show);
        TextView rel = helper.getView(R.id.tv_company_name);
        helper.addOnClickListener(R.id.tv_org);
        helper.addOnClickListener(R.id.tv_child_company);
        helper.addOnClickListener(R.id.tv_outside_company);
        helper.setText(R.id.tv_company_name, item.getOrgName());
        imageView.setImageResource(R.drawable.ic_down);
        imageView.setTag(false);

        if (item.getIsVerify() == 0) {
            helper.setVisible(R.id.tv_auth_status, true);
            helper.setText(R.id.tv_auth_status, "待认证");
            helper.addOnClickListener(R.id.tv_auth_status);
        } else {
            helper.setVisible(R.id.tv_auth_status, false);
        }
        //当点击时先进行判断

        rel.setOnClickListener(v -> {
            boolean flag = (boolean) imageView.getTag();
            //未被点击过
            if (!flag) {
                imageView.setImageResource(R.drawable.ic_up);
                ll_show.setVisibility(View.VISIBLE);
                imageView.setTag(true);
            } else {
                imageView.setImageResource(R.drawable.ic_down);
                ll_show.setVisibility(View.GONE);
                imageView.setTag(false);
            }
        });

    }
}
