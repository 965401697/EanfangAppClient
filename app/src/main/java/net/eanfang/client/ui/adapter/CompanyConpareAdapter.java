package net.eanfang.client.ui.adapter;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.SelectCompanyBean;
import net.eanfang.client.util.PerviewUtil;
import net.eanfang.client.util.StringUtils;

import java.util.List;



/**
 * 公司对比的adapter
 * Created by Administrator on 2017/3/15.
 */

public class CompanyConpareAdapter extends BaseMultiItemQuickAdapter<SelectCompanyBean.All1Bean, BaseViewHolder> {
    public CompanyConpareAdapter(List data) {
        super(data);
        addItemType(SelectCompanyBean.All1Bean.FIRST, R.layout.item_company_compare_first);
        addItemType(SelectCompanyBean.All1Bean.CONTENT, R.layout.item_company_compare);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SelectCompanyBean.All1Bean item) {
        switch (helper.getItemViewType()) {
            case SelectCompanyBean.All1Bean.FIRST:
                break;
            case SelectCompanyBean.All1Bean.CONTENT:
                int areaNum = item.getCompanyserviceplacelist().size();

                helper.setText(R.id.tv_company_name, item.getCompanyname())
                        .setText(R.id.tv_money, item.getRegistermoney())
                        .setText(R.id.tv_year, item.getWorkyear())
                        .setText(R.id.tv_level, item.getWorklevel())
                        .setText(R.id.tv_service_type, item.getBusinesstypestr())
                        .setText(R.id.tv_business_type, item.getServicetypestr())
                        .setText(R.id.tv_area, areaNum + "个");
                if (!StringUtils.isEmpty(item.getZizhipic1())) {
                    Uri licensePic1 = Uri.parse(item.getZizhipic1());
                    SimpleDraweeView iv_license = helper.getView(R.id.iv_license);
                    iv_license.setImageURI(licensePic1);
                    iv_license.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PerviewUtil.perviewImageUitl(item.getZizhipic1(), mContext);
                        }
                    });
                }
                if (!StringUtils.isEmpty(item.getHonorpic1())) {
                    final Uri honorPic1 = Uri.parse(item.getHonorpic1());
                    SimpleDraweeView iv_honor = helper.getView(R.id.iv_honor);
                    iv_honor.setImageURI(honorPic1);
                    iv_honor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PerviewUtil.perviewImageUitl(item.getHonorpic1(), mContext);
                        }
                    });
                }
                helper.addOnClickListener(R.id.tv_delete);

                break;
            default:
                break;
        }
    }

}
