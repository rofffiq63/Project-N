package com.need.unknown.presenter.impl;

import android.support.annotation.NonNull;

import com.need.unknown.presenter.RegisterPresenter;
import com.need.unknown.view.RegisterView;
import com.need.unknown.interactor.RegisterInteractor;

import javax.inject.Inject;

public final class RegisterPresenterImpl extends BasePresenterImpl<RegisterView> implements RegisterPresenter {
    /**
     * The interactor
     */
    @NonNull
    private final RegisterInteractor mInteractor;

    // The view is available using the mView variable

    @Inject
    public RegisterPresenterImpl(@NonNull RegisterInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onStart(boolean viewCreated) {
        super.onStart(viewCreated);

        // Your code here. Your view is available using mView and will not be null until next onStop()
    }

    @Override
    public void onStop() {
        // Your code here, mView will be null after this method until next onStart()

        super.onStop();
    }

    @Override
    public void onPresenterDestroyed() {
        /*
         * Your code here. After this method, your presenter (and view) will be completely destroyed
         * so make sure to cancel any HTTP call or database connection
         */

        super.onPresenterDestroyed();
    }
}