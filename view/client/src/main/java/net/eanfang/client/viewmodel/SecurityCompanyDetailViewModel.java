package net.eanfang.client.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.SecurityCompanyDetailBean;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.SecurityCompanyDetailDs;
import com.eanfang.biz.rds.sys.repo.SecurityCompanyDetailRepo;

import net.eanfang.client.databinding.ActivitySecurityCompanyDetailBinding;
import net.eanfang.client.databinding.FragmentGloryBinding;
import net.eanfang.client.databinding.FragmentMoreAbilityBinding;
import net.eanfang.client.databinding.FragmentServiceBinding;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/8/23
 * @description
 */
public class SecurityCompanyDetailViewModel extends BaseViewModel {

    @Setter
    @Getter
    private ActivitySecurityCompanyDetailBinding securityCompanyDetailBinding;
    @Setter
    @Getter
    private FragmentServiceBinding fragmentServiceBinding;
    @Setter
    @Getter
    private FragmentGloryBinding fragmentGloryBinding;
    @Setter
    @Getter
    private FragmentMoreAbilityBinding fragmentMoreAbilityBinding;

    private SecurityCompanyDetailRepo securityCompanyDetailRepo;

    @Getter
    private MutableLiveData<SecurityCompanyDetailBean> securityCompanyDetailBeanMutableLiveData;



    public SecurityCompanyDetailViewModel() {
        securityCompanyDetailBeanMutableLiveData = new MutableLiveData<>();
        securityCompanyDetailRepo = new SecurityCompanyDetailRepo(new SecurityCompanyDetailDs(this));
    }

    public void initGlory(String mOrgId) {


        securityCompanyDetailRepo.doGetCompanyDetail(mOrgId).observe(lifecycleOwner, companyDetailBean -> {

        });
    }

    public void getData(String mOrgId) {
        securityCompanyDetailRepo.doGetCompanyDetail(mOrgId).observe(lifecycleOwner, companyDetailBean -> {
            if (companyDetailBean == null) {
                return;
            }
            securityCompanyDetailBeanMutableLiveData.setValue(companyDetailBean);
        });
    }

    public void initMoreAbility(String mOrgId) {

    }


}

