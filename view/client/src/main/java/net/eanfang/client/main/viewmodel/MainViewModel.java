package net.eanfang.client.main.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.BaseDataBean;
import com.eanfang.biz.model.bean.ConstAllBean;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.bean.RongTokenBean;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MainDs;
import com.eanfang.biz.rds.sys.ds.impl.RongDs;
import com.eanfang.biz.rds.sys.repo.MainRepo;
import com.eanfang.biz.rds.sys.repo.RongRepo;

import lombok.Getter;

/**
 * MainViewModel
 *
 * @author jornl
 * @date 2019-05-16 18:50:29
 */
public class MainViewModel extends BaseViewModel {

    @Getter
    private MutableLiveData<RongTokenBean> tokenLiveData;
    @Getter
    private MutableLiveData<BaseDataBean> baseDataLiveData;
    @Getter
    private MutableLiveData<ConstAllBean> constDataLiveData;
    private MainRepo mainRepo;
    private RongRepo rongRepo;

    public MainViewModel() {
        tokenLiveData = new MutableLiveData<>();
        mainRepo = new MainRepo(new MainDs(this));
        rongRepo = new RongRepo(new RongDs(this));
    }

    /**
     * 获取融云token
     *
     * @param userId userId
     */
    public void getRongToken(Long userId) {
        rongRepo.getRongToken(userId).observe(lifecycleOwner, tokenLiveData::setValue);
    }

    /**
     * 获取基础数据
     *
     * @param md5
     */
    public void getBaseData(String md5) {
        mainRepo.getBaseData(md5).observe(lifecycleOwner, baseDataLiveData::setValue);
    }

    /**
     * 获取常量数据
     *
     * @param md5
     */
    public void getConstData(String md5) {
        mainRepo.getConstData(md5).observe(lifecycleOwner, constDataLiveData::setValue);
    }

    /**
     * 获取群详情
     *
     * @param id     群id
     * @param isUser 是否加载成员
     * @return GroupDetailBean
     */
    public MutableLiveData<GroupDetailBean> getGroupDetail(String id, boolean isUser) {
        MutableLiveData<GroupDetailBean> groupLiveData = new MutableLiveData<>();
        rongRepo.getGroupDetail(id, isUser).observe(lifecycleOwner, groupLiveData::setValue);
        return groupLiveData;
    }

    /**
     * 获取账号详情
     *
     * @param id id
     * @return AccountEntity
     */
    public MutableLiveData<AccountEntity> getAccountInfo(String id) {
        MutableLiveData<AccountEntity> accountLiveData = new MutableLiveData<>();
        mainRepo.getAccountInfo(id).observe(lifecycleOwner, accountLiveData::setValue);
        return accountLiveData;
    }
}
