package net.eanfang.client.network.apiservice;

/**
 * Created by Mr.hou
 *
 * @on 2017/10/9  9:54
 * @email houzhongzhou@yeah.net
 * @desc
 */

public interface DeviceFailureService extends BaseService {
    String BASE_URL_DEVICE_FAILURE = BASE_URL + "/deviceFailure";

    String GET_OPTION = BASE_URL_DEVICE_FAILURE + "/getOption";
}
