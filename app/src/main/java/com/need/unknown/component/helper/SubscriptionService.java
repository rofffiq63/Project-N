package com.need.unknown.component.helper;

import android.support.annotation.Nullable;

import com.need.unknown.component.model.DataUser;
import com.need.unknown.component.model.ResponseNetworkError;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SubscriptionService {

    private final EndPointService mEndpointService;

    public SubscriptionService(@Nullable EndPointService endPointService) {
        mEndpointService = endPointService;
    }

    public Subscription checkUserExist(final GetCallbackResponse<DataUser> callbackResponse, String phoneNumber) {
        return mEndpointService.checkUserExist(phoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DataUser>>() {
                    @Override
                    public Observable<? extends DataUser> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<DataUser>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callbackResponse.onError(new ResponseNetworkError(e));
                    }

                    @Override
                    public void onNext(DataUser dataUser) {
                        callbackResponse.onSuccess(dataUser);
                    }
                });
    }

    public interface GetCallbackResponse<T> {
        void onSuccess(T response);

        void onError(ResponseNetworkError networkError);
    }
}
