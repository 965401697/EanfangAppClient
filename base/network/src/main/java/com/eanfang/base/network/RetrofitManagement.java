package com.eanfang.base.network;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.base.network.config.HttpCode;
import com.eanfang.base.network.converter.FastJsonConverterFactory;
import com.eanfang.base.network.event.BaseActionEvent;
import com.eanfang.base.network.exception.AccountInvalidException;
import com.eanfang.base.network.exception.ParameterInvalidException;
import com.eanfang.base.network.exception.RequestFastException;
import com.eanfang.base.network.exception.RequestFromException;
import com.eanfang.base.network.exception.ServerResultException;
import com.eanfang.base.network.exception.TokenInvalidException;
import com.eanfang.base.network.holder.ContextHolder;
import com.eanfang.base.network.interceptor.FilterInterceptor;
import com.eanfang.base.network.interceptor.HeaderInterceptor;
import com.eanfang.base.network.interceptor.HttpInterceptor;
import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.base.network.model.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import leavesc.hello.monitor.MonitorInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static io.reactivex.plugins.RxJavaPlugins.onError;


/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public enum RetrofitManagement {

    INSTANCE;

    public static RetrofitManagement getInstance() {
        return INSTANCE;
    }

    private static final long READ_TIMEOUT = 6000;

    private static final long WRITE_TIMEOUT = 6000;

    private static final long CONNECT_TIMEOUT = 6000;

    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    private List<Interceptor> interceptorList;

    private boolean log = true;

    private Retrofit createRetrofit(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new FilterInterceptor())
                .addInterceptor(new HttpInterceptor())
                .retryOnConnectionFailure(true);
        if (interceptorList != null) {
            for (Interceptor interceptor : interceptorList) {
                builder.addInterceptor(interceptor);
            }
        }
        if (log) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
            builder.addInterceptor(new MonitorInterceptor(ContextHolder.getContext()));
        }
        OkHttpClient client = builder.build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                //网络请求返回值的json解析器配置，可自行扩展
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> ObservableTransformer<BaseResponseBody<T>, Object> applySchedulers(MutableLiveData<BaseActionEvent> actionLiveData) {
        return observable -> observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> {
                    switch (result.getCode()) {
                        case HttpCode.CODE_REQUEST_FAST:
                            actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.REQUEST_FAST));
                            throw new RequestFastException();
                        case HttpCode.CODE_SUCCESS:
                            actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.SUCCESS));
                            return createData(new Optional(result.getData()));
                        case HttpCode.CODE_UNKNOWN:
                            actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.SERVER_ERROR));
                            break;
                        case HttpCode.CODE_TOKEN_INVALID:
                            actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.TOKEN_ERROR));
                            throw new TokenInvalidException();
                        case HttpCode.CODE_ACCOUNT_INVALID:
                            throw new AccountInvalidException();
                        case HttpCode.CODE_PERMISSION_INVALID:
                            actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.PERMISSION_ERROR));
                            break;
                        case HttpCode.CODE_PARAMETER_INVALID:
                            actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.PARAM_ERROR));
                            throw new ParameterInvalidException();
                        case HttpCode.CODE_FROM_INVALID:
                            throw new RequestFromException();
                        case HttpCode.CODE_RESULT_INVALID:
                            actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.EMPTY_DATA));
                            break;
                        default:
                            throw new ServerResultException(result.getCode(), result.getMessage());
                    }
                    return createData(new Optional(null));
                });
    }

    private <T> Observable<T> createData(T t) {
        return Observable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    public <T> T getService(Class<T> clz, String host) {
        T value;
        if (serviceMap.containsKey(host)) {
            Object obj = serviceMap.get(host);
            if (obj == null) {
                value = createRetrofit(host).create(clz);
                serviceMap.put(host, value);
            } else {
                value = (T) obj;
            }
        } else {
            value = createRetrofit(host).create(clz);
            serviceMap.put(host, value);
        }
        return value;
    }

    public void setLog(boolean log) {
        this.log = log;
    }

    public void addInterceptor(List<Interceptor> interceptorList) {
        if (this.interceptorList == null) {
            this.interceptorList = new ArrayList<>();
        }
        if (interceptorList != null && interceptorList.size() > 0) {
            this.interceptorList.addAll(interceptorList);
        }
    }

}