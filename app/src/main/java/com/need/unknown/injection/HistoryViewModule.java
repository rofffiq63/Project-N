package com.need.unknown.injection;

import android.support.annotation.NonNull;

import com.need.unknown.interactor.HistoryInteractor;
import com.need.unknown.interactor.impl.HistoryInteractorImpl;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.HistoryPresenter;
import com.need.unknown.presenter.impl.HistoryPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class HistoryViewModule {
    @Provides
    public HistoryInteractor provideInteractor() {
        return new HistoryInteractorImpl();
    }

    @Provides
    public PresenterFactory<HistoryPresenter> providePresenterFactory(@NonNull final HistoryInteractor interactor) {
        return new PresenterFactory<HistoryPresenter>() {
            @NonNull
            @Override
            public HistoryPresenter create() {
                return new HistoryPresenterImpl(interactor);
            }
        };
    }
}
