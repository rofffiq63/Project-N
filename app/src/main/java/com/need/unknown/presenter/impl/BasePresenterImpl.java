package com.need.unknown.presenter.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.need.unknown.NeedApp;
import com.need.unknown.component.helper.Network;
import com.need.unknown.component.helper.SessionManager;
import com.need.unknown.component.helper.SubscriptionService;
import com.need.unknown.component.model.DataUser;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.presenter.BasePresenter;
import com.need.unknown.view.BaseView;

import rx.subscriptions.CompositeSubscription;

/**
 * Abstract presenter implementation that contains base implementation for other presenters.
 * Subclasses must call super for all {@link BasePresenter} method overriding.
 */
public abstract class BasePresenterImpl<V> implements BasePresenter<V> {
    /**
     * The view
     */
    @Nullable
    protected V mView;
    private NeedApp mApp;
    private Gson gson;
    private Context context;
    CompositeSubscription subscriptions;
    SessionManager sessionManager;
    DataUser userData;
    Network network;
    SubscriptionService subscriptionService;

    @Override
    public void onViewAttached(@NonNull V view, NeedApp needApp) {
        AppComponent appComponent = needApp.getAppComponent();
        mApp = needApp;
        mView = view;
        gson = new GsonBuilder().setPrettyPrinting().create();

        //        File cacheFile = new File(context.getCacheDir(), "responses");
        context = appComponent.getAppContext();
        subscriptions = new CompositeSubscription();
        sessionManager = appComponent.getSessionManager();
//        databaseManager = appComponent.getDatabase();
        userData = sessionManager.getUserData();

        network = appComponent.getNetwork();
        network.attachView((BaseView) mView);
        subscriptionService = network.builder(mApp, sessionManager.getTokenUser(false)).build();

        if (subscriptionService == null)
            ((BaseView) mView).onFailure("Failure");

        ((BaseView) mView).presenterReady(userData);
    }


    @Override
    public void onStart(boolean viewCreated) {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onViewDetached() {
        mView = null;
    }

    @Override
    public void onPresenterDestroyed() {

    }

    @Override
    public void getProfile(BaseView baseView, boolean fromCache) {
        baseView.onSuccess(new DataUser());
    }
}
