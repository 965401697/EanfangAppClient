package net.eanfang.client.viewmodel;

import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.databinding.ActivityAddStaffNextBinding;
import net.eanfang.client.databinding.FragmentSelectOrganizationBinding;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/8/20
 * @description 企业管理添加员工
 */
public class CompanySelectViewModle extends BaseViewModel {
    @Setter
    @Getter
    private FragmentSelectOrganizationBinding fragmentSelectOrganizationBinding;
    @Setter
    @Getter
    private ActivityAddStaffNextBinding activityAddStaffNextBinding;

}
