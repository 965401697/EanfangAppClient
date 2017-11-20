package net.eanfang.client.network.apiservice;

/**
 * Created by Mr.hou
 *
 * @on 2017/10/9  9:56
 * @email houzhongzhou@yeah.net
 * @desc
 */

public interface DeviceFailureSolutionService extends BaseService {
    String BASE_URL_DEVICE_FAILURE_SOLUTION = BASE_URL + "/deviceFailureSolution";

    String GET_OPTION = BASE_URL_DEVICE_FAILURE_SOLUTION + "/getOption";
}
