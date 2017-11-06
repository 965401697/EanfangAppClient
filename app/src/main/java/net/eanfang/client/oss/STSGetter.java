package net.eanfang.client.oss;

import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;

import net.eanfang.client.network.apiservice.OssApi;
import net.eanfang.client.network.request.EanfangHttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2015/12/9 0009.
 * 重载OSSFederationCredentialProvider生成自己的获取STS的功能
 */
public class STSGetter extends OSSFederationCredentialProvider {


    public OSSFederationToken getFederationToken() {
        String stsJson;
        try {
            Response response = EanfangHttp.get(OssApi.STS_URL).execute();
            if (response.isSuccessful()) {
                stsJson = response.body().string();

                JSONObject stsJsonObj = new JSONObject(stsJson);
                JSONObject jsonObjs = stsJsonObj.optJSONObject("data");
                String ak = jsonObjs.getString("AccessKeyId");
                String sk = jsonObjs.getString("AccessKeySecret");
                String token = jsonObjs.getString("Security");
                String expiration = jsonObjs.getString("Expiration");
                return new OSSFederationToken(ak, sk, token, expiration);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

}
