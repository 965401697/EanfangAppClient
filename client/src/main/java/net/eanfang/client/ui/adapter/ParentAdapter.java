package net.eanfang.client.ui.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.application.EanfangApplication;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;

import java.util.List;


/**
 * 合作公司的adapter
 * Created by Administrator on 2017/3/15.
 */

public class ParentAdapter extends BaseQuickAdapter<OrgEntity, BaseViewHolder> {

    public ParentAdapter() {
//    public ParentAdapter(List data) {
        super(R.layout.item_group_adapter);

    }

    private int selectedPosition = -1; //选中位置

    public View mView;

    private OnFristItemView mOnFristItemView;

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }


    @Override
    protected void convert(BaseViewHolder helper, OrgEntity item) {
        // 设置
        ImageView imageView = helper.getView(R.id.iv_setting);
        // 认证图标
        ImageView ivVerify = helper.getView(R.id.iv_verify);
        // 公司头像
        SimpleDraweeView ivCompanyHead = helper.getView(R.id.iv_company_logo);

        RelativeLayout rl_father = helper.getView(R.id.rel_company);
        LinearLayout ll_show = helper.getView(R.id.ll_show);
        TextView rel = helper.getView(R.id.tv_company_name);
        helper.addOnClickListener(R.id.tv_org);
        helper.addOnClickListener(R.id.tv_child_company);
        helper.addOnClickListener(R.id.tv_outside_company);
        helper.addOnClickListener(R.id.ll_part_company);
        helper.addOnClickListener(R.id.iv_setting);
        helper.setText(R.id.tv_company_name, item.getOrgName());
        imageView.setImageResource(R.mipmap.ic_contact_setting);
        imageView.setTag(false);
        rl_father.setTag(false);
        helper.addOnClickListener(R.id.tv_auth_status);

        ivCompanyHead.setImageURI(Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + item.getOrgUnitEntity().getLogoPic()));
        if (item.getVerifyStatus() == 0) {
            helper.setText(R.id.tv_auth_status, "未认证");
        } else if (item.getVerifyStatus() == 1) {
            helper.setText(R.id.tv_auth_status, "认证中");
        } else if (item.getVerifyStatus() == 2) {
            helper.setText(R.id.tv_auth_status, "查看");
            ivVerify.setImageResource(R.mipmap.ic_contact_authentication);
        } else if (item.getVerifyStatus() == 3) {
            helper.setText(R.id.tv_auth_status, "重新认证");
        } else if (item.getVerifyStatus() == 4) {
            helper.setText(R.id.tv_auth_status, "已禁用");
        } else if (item.getVerifyStatus() == 5) {
            helper.setText(R.id.tv_auth_status, "已删除");
        }
        if (helper.getAdapterPosition() == 0) {
            ll_show.setVisibility(View.VISIBLE);
            item.setFlag(true);
        } else {
            item.setFlag(false);
        }
        if (helper.getLayoutPosition() == 0) {
//            mView = ll_show;
            mOnFristItemView.setOnFristItemView(ll_show);
        }

        if (item.isFlag()) {
            ll_show.setVisibility(View.VISIBLE);
        } else {
            ll_show.setVisibility(View.GONE);
        }

//        rl_father.setOnClickListener(v -> {
//            boolean flag = (boolean) rl_father.getTag();//未被点击过
//            if (!flag) {
//                if (selectedPosition == helper.getAdapterPosition()) {
//                    ll_show.setVisibility(View.VISIBLE);
//                    rl_father.setTag(true);
//                } else {
//                    ll_show.setVisibility(View.GONE);
//                    rl_father.setTag(false);
//                }
//            } else {
//                if (selectedPosition == helper.getAdapterPosition()) {
//                    ll_show.setVisibility(View.GONE);
//                    rl_father.setTag(false);
//                } else {
//                    ll_show.setVisibility(View.VISIBLE);
//                    rl_father.setTag(true);
//                }
//            }
//        });
        //当点击时先进行判断
//        rel.setOnClickListener(v -> {
//            boolean flag = (boolean) imageView.getTag();
//            //未被点击过
//            if (!flag) {
//                imageView.setImageResource(R.drawable.contend_ic_management_default);
//                ll_show.setVisibility(View.VISIBLE);
//                imageView.setTag(true);
//            } else {
//                imageView.setImageResource(R.drawable.contend_ic_management_default);
//                ll_show.setVisibility(View.GONE);
//                imageView.setTag(false);
//            }
//        });
    }

    public interface OnFristItemView {
        public void setOnFristItemView(View view);
    }

    public void setOnFristItemView(OnFristItemView onFristItemView) {
        this.mOnFristItemView = onFristItemView;
//        onFristItemView.setOnFristItemView(mView);
    }
}
