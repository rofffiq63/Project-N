package com.need.unknown.injection;

import android.support.annotation.NonNull;

import com.need.unknown.interactor.ProfileInteractor;
import com.need.unknown.interactor.impl.ProfileInteractorImpl;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.ProfilePresenter;
import com.need.unknown.presenter.impl.ProfilePresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class ProfileViewModule {
    @Provides
    public ProfileInteractor provideInteractor() {
        return new ProfileInteractorImpl();
    }

    @Provides
    public PresenterFactory<ProfilePresenter> providePresenterFactory(@NonNull final ProfileInteractor interactor) {
        return new PresenterFactory<ProfilePresenter>() {
            @NonNull
            @Override
            public ProfilePresenter create() {
                return new ProfilePresenterImpl(interactor);
            }
        };
    }
}
