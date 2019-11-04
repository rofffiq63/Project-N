package com.need.unknown.presenter.impl;

import android.support.annotation.NonNull;

import com.need.unknown.presenter.HistoryPresenter;
import com.need.unknown.view.HistoryView;
import com.need.unknown.interactor.HistoryInteractor;

import javax.inject.Inject;

public final class HistoryPresenterImpl extends BasePresenterImpl<HistoryView> implements HistoryPresenter {
    /**
     * The interactor
     */
    @NonNull
    private final HistoryInteractor mInteractor;

    // The view is available using the mView variable

    @Inject
    public HistoryPresenterImpl(@NonNull HistoryInteractor interactor) {
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