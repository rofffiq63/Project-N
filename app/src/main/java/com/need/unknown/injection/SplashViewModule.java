package com.need.unknown.injection;

import android.support.annotation.NonNull;

import com.need.unknown.interactor.SplashInteractor;
import com.need.unknown.interactor.impl.SplashInteractorImpl;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.SplashPresenter;
import com.need.unknown.presenter.impl.SplashPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class SplashViewModule {
    @Provides
    public SplashInteractor provideInteractor() {
        return new SplashInteractorImpl();
    }

    @Provides
    public PresenterFactory<SplashPresenter> providePresenterFactory(@NonNull final SplashInteractor interactor) {
        return new PresenterFactory<SplashPresenter>() {
            @NonNull
            @Override
            public SplashPresenter create() {
                return new SplashPresenterImpl(interactor);
            }
        };
    }
}
