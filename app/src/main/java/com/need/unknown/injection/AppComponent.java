package com.need.unknown.injection;

import android.content.Context;

import com.need.unknown.NeedApp;
import com.need.unknown.component.helper.Network;
import com.need.unknown.component.helper.SessionManager;

import javax.inject.Singleton;

import dagger.Component;
import rx.subscriptions.CompositeSubscription;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context getAppContext();

    NeedApp getApp();

    SessionManager getSessionManager();

    Network getNetwork();

    CompositeSubscription getSubscription();
}