package com.eanfang.biz.rds.base;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.base.kit.utils.TypeToken;
import com.eanfang.base.network.RetrofitManagement;
import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.base.network.callback.RequestMultiplyCallback;
import com.eanfang.base.network.config.HttpConfig;
import com.eanfang.base.network.event.BaseActionEvent;
import com.eanfang.base.network.model.BaseResponseBody;
import com.zchu.rxcache.RxCache;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public abstract class BaseRemoteDataSource {

    private CompositeDisposable compositeDisposable;

    private BaseViewModel baseViewModel;


    public BaseRemoteDataSource(BaseViewModel baseViewModel) {
        this.compositeDisposable = new CompositeDisposable();
        this.baseViewModel = baseViewModel;
    }


    MutableLiveData<BaseActionEvent> errorLiveData() {
        return baseViewModel.getErrorLiveData();
    }

    void showError(boolean isShow) {
        baseViewModel.setShowError(isShow);
    }

    protected <T> T getService(Class<T> clz) {
        return RetrofitManagement.getInstance().getService(clz, HttpConfig.get().getApiUrl());
    }

    protected <T> T getService(Class<T> clz, String host) {
        return RetrofitManagement.getInstance().getService(clz, host);
    }

    private <T> ObservableTransformer<BaseResponseBody<T>, Object> applySchedulers(Class<T> clazz) {
        return RetrofitManagement.getInstance().applySchedulers(baseViewModel.getActionLiveData(), baseViewModel.getErrorLiveData(), clazz);
    }

    protected <T> void execute(Observable observable, RequestCallback<T> callback) {
        execute(observable, new BaseSubscriber<>(callback), true, null);
    }

    protected <T> void execute(Observable observable, RequestCallback<T> callback, Class<T> clazz) {
        execute(observable, new BaseSubscriber<>(callback).setCallbackClazz(clazz), true, null);
    }


    protected <T> void execute(Observable observable, RequestCallback<T> callback, CacheModel cacheModel) {
        execute(observable, new BaseSubscriber<>(callback), true, cacheModel);
    }

    protected <T> void execute(Observable observable, RequestMultiplyCallback<T> callback) {
        execute(observable, new BaseSubscriber<>(callback), true, null);
    }

    public void executeWithoutDismiss(Observable observable, BaseSubscriber observer) {
        execute(observable, observer, false, null);
    }

    private <T> void execute(Observable observable, BaseSubscriber observer, boolean isDismiss, CacheModel cacheModel) {
        Disposable disposable = (Disposable) observable
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxCache.getDefault().<BaseResponseBody<T>>transformObservable(cacheModel != null ? cacheModel.getKey() : "", new TypeToken<T>() {
                    //默认优先使用网络
                }.getType(), cacheModel != null ? cacheModel.getCacheStrategy() : CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseResponseBody<T>>())
                .compose(applySchedulers(observer.getCallbackClazz()))
                .compose(isDismiss ? loadingTransformer() : loadingTransformerWithoutDismiss())
                .subscribeWith(observer);
        addDisposable(disposable);
    }

    private void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    private void startLoading() {
        if (baseViewModel != null) {
            baseViewModel.startLoading();
        }
    }

    private void dismissLoading() {
        if (baseViewModel != null) {
            baseViewModel.dismissLoading();
        }
    }

    private <T> ObservableTransformer<T, T> loadingTransformer() {
        return observable -> observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> startLoading())
                .doFinally(this::dismissLoading);
    }

    private <T> ObservableTransformer<T, T> loadSirTransformer() {
        return observable -> observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(this::dismissLoading);
    }

    private <T> ObservableTransformer<T, T> loadingTransformerWithoutDismiss() {
        return observable -> observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> startLoading());
    }

}