package com.need.unknown.view;

import android.support.annotation.UiThread;

import com.need.unknown.component.model.DataUser;
import com.need.unknown.component.model.ResponseNetworkError;

@UiThread
public interface BaseView {

    void onProgress();

    <T> void onSuccess(T object);

    void onFailure(ResponseNetworkError networError);

    void onFailure(String message);

    void updateNetwork(long totalBytesRead, long contentLength, boolean b);

    void presenterReady(DataUser userData);
}
