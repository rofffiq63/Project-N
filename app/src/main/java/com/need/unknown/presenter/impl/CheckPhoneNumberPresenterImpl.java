package com.need.unknown.presenter.impl;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.need.unknown.component.helper.SubscriptionService;
import com.need.unknown.component.model.DataUser;
import com.need.unknown.component.model.ResponseNetworkError;
import com.need.unknown.presenter.CheckPhoneNumberPresenter;
import com.need.unknown.view.CheckPhoneNumberView;
import com.need.unknown.interactor.CheckPhoneNumberInteractor;

import javax.inject.Inject;

public final class CheckPhoneNumberPresenterImpl extends BasePresenterImpl<CheckPhoneNumberView> implements CheckPhoneNumberPresenter {
    /**
     * The interactor
     */
    @NonNull
    private final CheckPhoneNumberInteractor mInteractor;

    // The view is available using the mView variable

    @Inject
    public CheckPhoneNumberPresenterImpl(@NonNull CheckPhoneNumberInteractor interactor) {
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

    @Override
    public void checkUserExist(String phoneNumber) {
        try {
            subscriptions.add(subscriptionService.checkUserExist(new SubscriptionService.GetCallbackResponse<DataUser>() {
                @Override
                public void onSuccess(DataUser response) {

                }

                @Override
                public void onError(ResponseNetworkError networkError) {
                    if (networkError.getResponse().getRawResponse() != null) {
                        DataUser dataUser = new Gson().fromJson(networkError.getResponse().getRawResponse(), DataUser.class);
                        if (dataUser != null) {
                            if (!dataUser.isIs_registered()) {
                                mView.goToRegister();
                            } else mView.goToLogin();
                        }
                        return;
                    }
                }
            }, phoneNumber));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}