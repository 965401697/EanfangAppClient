package net.eanfang.worker.ui.fragment.customservice;

import android.os.Bundle;

import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;

import net.eanfang.worker.R;

/**
 * @author Guanluocang
 * @date on 2018/4/23  17:27
 * @decision 企业用户
 */
public class CompanyServiceFragment extends BaseFragment {

    public static CompanyServiceFragment getInstance() {
        CompanyServiceFragment companyServiceFragment = new CompanyServiceFragment();
        return companyServiceFragment;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_company;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setListener() {
        findViewById(R.id.tv_server_phone).setOnClickListener((view) -> CallUtils.call(getContext(), "400-890-9280"));
    }

}
