package com.need.unknown;

import android.app.Application;
import android.support.annotation.NonNull;

import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.AppModule;
import com.need.unknown.injection.DaggerAppComponent;

public final class NeedApp extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @NonNull
    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}