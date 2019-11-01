package com.need.unknown.injection;

import android.support.annotation.NonNull;

import com.need.unknown.interactor.MainInteractor;
import com.need.unknown.interactor.impl.MainInteractorImpl;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.MainPresenter;
import com.need.unknown.presenter.impl.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class MainViewModule {
    @Provides
    public MainInteractor provideInteractor() {
        return new MainInteractorImpl();
    }

    @Provides
    public PresenterFactory<MainPresenter> providePresenterFactory(@NonNull final MainInteractor interactor) {
        return new PresenterFactory<MainPresenter>() {
            @NonNull
            @Override
            public MainPresenter create() {
                return new MainPresenterImpl(interactor);
            }
        };
    }
}
