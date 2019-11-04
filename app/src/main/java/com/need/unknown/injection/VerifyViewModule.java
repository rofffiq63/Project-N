package com.need.unknown.injection;

import android.support.annotation.NonNull;

import com.need.unknown.interactor.VerifyInteractor;
import com.need.unknown.interactor.impl.VerifyInteractorImpl;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.VerifyPresenter;
import com.need.unknown.presenter.impl.VerifyPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class VerifyViewModule {
    @Provides
    public VerifyInteractor provideInteractor() {
        return new VerifyInteractorImpl();
    }

    @Provides
    public PresenterFactory<VerifyPresenter> providePresenterFactory(@NonNull final VerifyInteractor interactor) {
        return new PresenterFactory<VerifyPresenter>() {
            @NonNull
            @Override
            public VerifyPresenter create() {
                return new VerifyPresenterImpl(interactor);
            }
        };
    }
}
