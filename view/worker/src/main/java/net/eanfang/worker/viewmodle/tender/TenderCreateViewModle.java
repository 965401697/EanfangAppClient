package net.eanfang.worker.viewmodle.tender;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderFindDs;
import com.eanfang.biz.rds.sys.repo.tender.TenderFindRepo;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.config.EanfangConst;

import net.eanfang.worker.databinding.ActivityTenderCreateBinding;
import net.eanfang.worker.databinding.ActivityWorkerTenderControlBinding;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/6/13
 * @description 创建用工
 */
public class TenderCreateViewModle extends BaseViewModel {

    /**
     * 经度
     */
    public String lon;
    /**
     * 纬度
     */
    public String lat;
    /**
     * 省份
     */
    public String province;
    /**
     * 城市
     */
    public String city;
    /**
     * 区县
     */
    public String contry;

    /**
     * 详细地址
     * */
    public String detailPlace;
    private TenderFindRepo tenderFindRepo;
    @Getter
    private MutableLiveData<PageBean<TaskPublishEntity>> tenderFindLiveData;

    @Setter
    @Getter
    private ActivityTenderCreateBinding mTenderCreateBinding;

    private TaskPublishEntity taskPublishEntity;

    public TenderCreateViewModle() {
        tenderFindLiveData = new MutableLiveData<>();
        tenderFindRepo = new TenderFindRepo(new TenderFindDs(this));
    }

    /**
     * 发布用工
     */
    private void doCommitTender() {

        taskPublishEntity = new TaskPublishEntity();
        taskPublishEntity.setContacts(BaseApplication.get().getUser().getAccountEntity().getRealName());
        taskPublishEntity.setPublishCompanyName(BaseApplication.get().getUser().getAccountEntity().getRealName());
        taskPublishEntity.setLatitude(lat);
        taskPublishEntity.setLongitude(lon);
        taskPublishEntity.setCity(city);
        taskPublishEntity.setProvince(province);
        taskPublishEntity.setCounty(contry);
        taskPublishEntity.setZoneCode(Config.get().getAreaCodeByName(city, contry));
        taskPublishEntity.setZoneCode(Config.get().getBaseIdByCode(taskPublishEntity.getZoneCode(), 3, Constant.AREA) + "");
//        taskPublishEntity.setBusinessOneCode();
//        taskPublishEntity.setSystemType();
//        taskPublishEntity.setDetailPlace();

    }
}
