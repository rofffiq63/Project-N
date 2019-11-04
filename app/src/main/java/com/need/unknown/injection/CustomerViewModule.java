package com.need.unknown.injection;

import android.support.annotation.NonNull;

import com.need.unknown.interactor.CustomerInteractor;
import com.need.unknown.interactor.impl.CustomerInteractorImpl;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.CustomerPresenter;
import com.need.unknown.presenter.impl.CustomerPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class CustomerViewModule {
    @Provides
    public CustomerInteractor provideInteractor() {
        return new CustomerInteractorImpl();
    }

    @Provides
    public PresenterFactory<CustomerPresenter> providePresenterFactory(@NonNull final CustomerInteractor interactor) {
        return new PresenterFactory<CustomerPresenter>() {
            @NonNull
            @Override
            public CustomerPresenter create() {
                return new CustomerPresenterImpl(interactor);
            }
        };
    }
}
