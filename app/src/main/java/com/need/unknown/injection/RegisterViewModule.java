package com.need.unknown.injection;

import android.support.annotation.NonNull;

import com.need.unknown.interactor.RegisterInteractor;
import com.need.unknown.interactor.impl.RegisterInteractorImpl;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.RegisterPresenter;
import com.need.unknown.presenter.impl.RegisterPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class RegisterViewModule {
    @Provides
    public RegisterInteractor provideInteractor() {
        return new RegisterInteractorImpl();
    }

    @Provides
    public PresenterFactory<RegisterPresenter> providePresenterFactory(@NonNull final RegisterInteractor interactor) {
        return new PresenterFactory<RegisterPresenter>() {
            @NonNull
            @Override
            public RegisterPresenter create() {
                return new RegisterPresenterImpl(interactor);
            }
        };
    }
}
