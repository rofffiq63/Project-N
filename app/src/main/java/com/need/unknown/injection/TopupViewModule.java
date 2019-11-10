package com.need.unknown.injection;

import android.support.annotation.NonNull;

import com.need.unknown.interactor.TopupInteractor;
import com.need.unknown.interactor.impl.TopupInteractorImpl;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.TopupPresenter;
import com.need.unknown.presenter.impl.TopupPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class TopupViewModule {
    @Provides
    public TopupInteractor provideInteractor() {
        return new TopupInteractorImpl();
    }

    @Provides
    public PresenterFactory<TopupPresenter> providePresenterFactory(@NonNull final TopupInteractor interactor) {
        return new PresenterFactory<TopupPresenter>() {
            @NonNull
            @Override
            public TopupPresenter create() {
                return new TopupPresenterImpl(interactor);
            }
        };
    }
}
