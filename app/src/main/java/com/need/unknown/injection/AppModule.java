package com.need.unknown.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import com.need.unknown.NeedApp;
import com.need.unknown.component.helper.Network;
import com.need.unknown.component.helper.SessionManager;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public final class AppModule {
    @NonNull
    private final NeedApp mApp;

    @NonNull
    private SessionManager sessionManager;

    @NonNull
    private CompositeSubscription subscriptions;

    @NonNull
    private Network network;

    public AppModule(@NonNull NeedApp app) {
        mApp = app;
        sessionManager = new SessionManager(app);
//        databaseManager = new DatabaseManager(app, sessionManager);
        subscriptions = new CompositeSubscription();
        network = new Network();
    }

    @Provides
    public Context provideAppContext() {
        return mApp;
    }

    @Provides
    public NeedApp provideApp() {
        return mApp;
    }

    @Provides
    public SessionManager provideSession() {
        return sessionManager;
    }

    @Provides
    public CompositeSubscription provideSubscription() {
        return subscriptions;
    }

    @Provides
    public Network provideSubscriptionService() {
        return network;
    }

}
