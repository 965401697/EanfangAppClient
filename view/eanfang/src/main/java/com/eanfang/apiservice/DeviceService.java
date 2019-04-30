package com.eanfang.apiservice;

/**
 * Created by Mr.hou
 *
 * @on 2017/10/9  9:51
 * @email houzhongzhou@yeah.net
 * @desc 设备接口
 */

public interface DeviceService extends BaseService {
    String BASE_URL_DEVICE = BASE_URL + "/device";

    /**
     * 添加故障 设备选择项
     */
    String GET_OPTION = BASE_URL_DEVICE + "/getOption";
}
