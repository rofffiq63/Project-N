package com.need.unknown.injection;

import android.support.annotation.NonNull;

import com.need.unknown.interactor.CheckPhoneNumberInteractor;
import com.need.unknown.interactor.impl.CheckPhoneNumberInteractorImpl;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.CheckPhoneNumberPresenter;
import com.need.unknown.presenter.impl.CheckPhoneNumberPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class CheckPhoneNumberViewModule {
    @Provides
    public CheckPhoneNumberInteractor provideInteractor() {
        return new CheckPhoneNumberInteractorImpl();
    }

    @Provides
    public PresenterFactory<CheckPhoneNumberPresenter> providePresenterFactory(@NonNull final CheckPhoneNumberInteractor interactor) {
        return new PresenterFactory<CheckPhoneNumberPresenter>() {
            @NonNull
            @Override
            public CheckPhoneNumberPresenter create() {
                return new CheckPhoneNumberPresenterImpl(interactor);
            }
        };
    }
}
