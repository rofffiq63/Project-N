package com.need.unknown.presenter.impl;

import android.support.annotation.NonNull;

import com.need.unknown.presenter.CustomerPresenter;
import com.need.unknown.view.CustomerView;
import com.need.unknown.interactor.CustomerInteractor;

import javax.inject.Inject;

public final class CustomerPresenterImpl extends BasePresenterImpl<CustomerView> implements CustomerPresenter {
    /**
     * The interactor
     */
    @NonNull
    private final CustomerInteractor mInteractor;

    // The view is available using the mView variable

    @Inject
    public CustomerPresenterImpl(@NonNull CustomerInteractor interactor) {
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