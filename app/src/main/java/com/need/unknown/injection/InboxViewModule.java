package com.need.unknown.injection;

import android.support.annotation.NonNull;

import com.need.unknown.interactor.InboxInteractor;
import com.need.unknown.interactor.impl.InboxInteractorImpl;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.InboxPresenter;
import com.need.unknown.presenter.impl.InboxPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class InboxViewModule {
    @Provides
    public InboxInteractor provideInteractor() {
        return new InboxInteractorImpl();
    }

    @Provides
    public PresenterFactory<InboxPresenter> providePresenterFactory(@NonNull final InboxInteractor interactor) {
        return new PresenterFactory<InboxPresenter>() {
            @NonNull
            @Override
            public InboxPresenter create() {
                return new InboxPresenterImpl(interactor);
            }
        };
    }
}
