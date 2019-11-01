package com.need.unknown.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import com.need.unknown.NeedApp;

import dagger.Module;
import dagger.Provides;

@Module
public final class AppModule {
    @NonNull
    private final NeedApp mApp;

    public AppModule(@NonNull NeedApp app) {
        mApp = app;
    }

    @Provides
    public Context provideAppContext() {
        return mApp;
    }

    @Provides
    public NeedApp provideApp() {
        return mApp;
    }
}
