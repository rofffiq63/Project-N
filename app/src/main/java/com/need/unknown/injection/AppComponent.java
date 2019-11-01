package com.need.unknown.injection;

import android.content.Context;

import com.need.unknown.NeedApp;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context getAppContext();

    NeedApp getApp();
}