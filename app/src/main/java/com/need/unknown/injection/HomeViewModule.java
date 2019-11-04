package com.need.unknown.injection;

import android.support.annotation.NonNull;

import com.need.unknown.interactor.HomeInteractor;
import com.need.unknown.interactor.impl.HomeInteractorImpl;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.HomePresenter;
import com.need.unknown.presenter.impl.HomePresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class HomeViewModule {
    @Provides
    public HomeInteractor provideInteractor() {
        return new HomeInteractorImpl();
    }

    @Provides
    public PresenterFactory<HomePresenter> providePresenterFactory(@NonNull final HomeInteractor interactor) {
        return new PresenterFactory<HomePresenter>() {
            @NonNull
            @Override
            public HomePresenter create() {
                return new HomePresenterImpl(interactor);
            }
        };
    }
}
