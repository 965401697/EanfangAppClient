package net.eanfang.worker.ui.fragment.customservice;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eanfang.ui.base.BaseFragment;

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

    }

}
