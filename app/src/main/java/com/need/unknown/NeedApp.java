package com.need.unknown;

import android.app.Application;
import android.support.annotation.NonNull;

import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.AppModule;
import com.need.unknown.injection.DaggerAppComponent;

public final class NeedApp extends Application {
    public String signature;
    private AppComponent mAppComponent;
    private String mode;

    @Override
    public void onCreate() {
        super.onCreate();
        setMode(BuildConfig.BASESTAGINGURL);
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @NonNull
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}