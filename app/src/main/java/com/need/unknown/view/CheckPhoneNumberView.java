package com.need.unknown.view;

import android.support.annotation.UiThread;

@UiThread
public interface CheckPhoneNumberView extends BaseView {

    void goToRegister();

    void goToLogin();
}