package com.need.unknown.presenter.impl;

import android.support.annotation.NonNull;

import com.need.unknown.presenter.InboxPresenter;
import com.need.unknown.view.InboxView;
import com.need.unknown.interactor.InboxInteractor;

import javax.inject.Inject;

public final class InboxPresenterImpl extends BasePresenterImpl<InboxView> implements InboxPresenter {
    /**
     * The interactor
     */
    @NonNull
    private final InboxInteractor mInteractor;

    // The view is available using the mView variable

    @Inject
    public InboxPresenterImpl(@NonNull InboxInteractor interactor) {
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