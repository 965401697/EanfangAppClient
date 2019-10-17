package net.eanfang.client.ui.activity.leave_post.bean;

import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author liangkailun
 * Date ：2019-07-04
 * Describe :添加岗位上传
 */
@Getter
@Setter
public class LeavePostAddPostPostBean implements Serializable {
    private Long stationId;
    private Long companyId;
    private String stationName;
    private String stationArea;
    private String stationCode;
    private String stationAddress;
    private String stationPlaceCode;
    private String status;
    private int intervalLength;
    private long configId;
    private Ys7DevicesEntity deviceEntity;
    private List<ChargeStaffListBean> dutyUserList;
    private List<ChargeStaffListBean> chargeUserList;
    private NowUserBean nowUser;


    @NoArgsConstructor
    @Data
    public static class ChargeStaffListBean {
        private AccountEntity accountEntity;
        private String companyId;
        private int id;
        private String mobile;
        private String name;
        private int staffType;
        private int stationId;
        private int status;
        private String userId;
        /**
         * 布局类型
         */
        private int type = 1;
        /**
         * 标题
         */
        private String title;
    }

    @NoArgsConstructor
    @Data
    @Accessors(chain = true)
    public static class NowUserBean {
        private long userId;
        private long companyId;
    }
}
