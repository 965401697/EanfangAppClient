package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.worker.R;

import java.util.List;

/**
 * 我的-评价的adapter
 * Created by Administrator on 2017/3/15.
 */
public class ExternalStaffAdapter extends BaseQuickAdapter<UserEntity, BaseViewHolder> {
    public ExternalStaffAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserEntity item) {
        helper.setText(R.id.tv_detail_name, item.getAccountEntity().getRealName());

    }
}
