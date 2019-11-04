package com.need.unknown.presenter;

import com.need.unknown.view.CheckPhoneNumberView;

public interface CheckPhoneNumberPresenter extends BasePresenter<CheckPhoneNumberView> {

    void checkUserExist(String phoneNumber);
}